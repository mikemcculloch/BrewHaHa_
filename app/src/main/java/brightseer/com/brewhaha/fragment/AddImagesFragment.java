package brightseer.com.brewhaha.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.adapter.BeerMyRecipeRecycler;
import brightseer.com.brewhaha.adapter.RecyclerItemClickListener;
import brightseer.com.brewhaha.objects.Image;
import brightseer.com.brewhaha.repository.JsonToObject;

/**
 * Created by Michael McCulloch on 3/22/2015.
 */
public class AddImagesFragment extends BaseFragment implements View.OnClickListener {
    private View rootView, progessBarDialogView;
    private android.support.design.widget.FloatingActionButton image_fab;
    private BeerMyRecipeRecycler adapter;
    private RecyclerView my_images_recycle_view;
    private List<Image> imageList = new Vector<>();
    public final int CAMERA_CAPTURE = 1;
    public final int RESULT_LOAD_IMAGE = 1;
    final int PIC_CROP = 2;
    private Uri picUri;
    private int menuType = 1, deletePosition = 0, selectedImagePk = 0;
    private ProgressBar uploadProgressBar;
    private AlertDialog progessDialog;
    public Future<JsonArray> ionLoadImages, ionImageUpload;
    public Future<String> ionDeleteImage;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _fContext = getActivity();
        initGoogleAnalytics(this.getClass().getSimpleName());
        Bundle bundle = this.getArguments();
        contentItemPk = bundle.getString(Constants.exContentItemPk);
        contentToken = bundle.getString(Constants.spContentToken);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_images, container, false);
        initRecycler();
        createProgessDialog();
        initViews();
        load();
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (ionLoadImages != null) {
            ionLoadImages.cancel();
        }
        if (ionImageUpload != null) {
            ionImageUpload.cancel();
        }
        if (ionDeleteImage != null) {
            ionDeleteImage.cancel();
        }
    }

    @Override
    public void onClick(View v) {

    }

    public void initViews() {
        image_fab = (android.support.design.widget.FloatingActionButton) rootView.findViewById(R.id.image_fab);
        uploadProgressBar = (ProgressBar) progessBarDialogView.findViewById(R.id.uploadProgressBar);
    }

    public void initRecycler() {
        int screenOrientation = getResources().getConfiguration().orientation;
        my_images_recycle_view = (RecyclerView) rootView.findViewById(R.id.my_images_recycle_view);
        my_images_recycle_view.setHasFixedSize(true);

        int spanCount = 4;
        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            spanCount = 2;
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        my_images_recycle_view.setLayoutManager(layoutManager);

        List<Image> placeHolder = new Vector<>();
        adapter = new BeerMyRecipeRecycler(placeHolder, AddImagesFragment.this, 5);

        my_images_recycle_view.setAdapter(adapter);
//        my_images_recycle_view.setItemAnimator(new SlideScaleInOutRightItemAnimator(my_images_recycle_view));

        my_images_recycle_view.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity().getBaseContext(), my_images_recycle_view, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            showImage(imageList.get(position));
                        } catch (Exception e) {
                            if (BuildConfig.DEBUG) {
                                Log.e(Constants.LOG, e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        selectedImagePk = imageList.get(position).getImagePk();
                        deletePosition = position;
                        menuType = 2;
                        registerForContextMenu(view);
                        getActivity().openContextMenu(view);
                        view.setLongClickable(false);

                    }
                })
        );
    }

    public void load() {
        String url = Constants.wcfGetRecipeImages + contentToken;
        ionLoadImages = Ion.with(_fContext)
                .load(url)
                .setHeader("Cache-Control", "No-Cache")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                                 @Override
                                 public void onCompleted(Exception e, JsonArray result) {
                                     try {
                                         List<Image> images = JsonToObject.JsonToImageList(result);
                                         for (Image item : images) {
                                             imageList.add(item);
                                             adapter.add(item, imageList.size() - 1);
                                         }
                                         addFabListener();
//                                         dialogProgress.dismiss();
                                     } catch (Exception ex) {
                                         if (BuildConfig.DEBUG) {
                                             Log.e(Constants.LOG, ex.getMessage());
                                         }
//                                         dialogProgress.dismiss();
                                     }
                                 }
                             }

                );
    }

    public void addFabListener() {
        try {
            image_fab.setVisibility(View.VISIBLE);
            image_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuType = 1;
                    registerForContextMenu(view);
                    getActivity().openContextMenu(view);
                    view.setLongClickable(false);
                }
            });

            EnableDisableFab();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE) {
                picUri = data.getData(); //master version of file
                CropOption();
            } else if (requestCode == PIC_CROP) {
                try {
                    String imagePath = getPath(data.getData());
                    if (!TextUtils.isEmpty(imagePath)) {
                        picUri = data.getData();//cropped version of file
                    } else {
                        Snackbar.make(rootView.findViewById(R.id.coordinatorLayout), "Sorry! This device or version doesn't currently support image cropping", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                    uploadImage(getPath(picUri));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void performCrop() {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 500);
            cropIntent.putExtra("outputY", 500);
            cropIntent.putExtra("return-data", true);
            cropIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, anfe.getMessage());
            }

            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public String getPath(Uri uri) {
        try {
            String filePath = "";
            if (uri != null) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                filePath = cursor.getString(columnIndex);
                cursor.close();
                return filePath;
            } else
                return "";

        } catch (Exception e) {
            String message = e.getMessage();
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
            return "";
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        contextMenu = menu;

        if (menuType == 1) {
            menu.setHeaderTitle("Upload Images from");
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_device_beer_capture, menu);
        }
        if (menuType == 2) {
//            menu.setHeaderTitle("Upload Images from");
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_image_delete, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.capture_beer_menu_button:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(takePictureIntent, CAMERA_CAPTURE);
                break;
            case R.id.device_beer_menu_button:
                Intent getPicture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getPicture.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(getPicture, RESULT_LOAD_IMAGE);
                break;
            case R.id.menu_image_button_delete:

                if (imageList.size() > 1) {
                    DeleteImage();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Sorry! You must have at least 1 image",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void CropOption() {
        DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    dialog.dismiss();
                    performCrop();
                } catch (Exception e) {
                    if (BuildConfig.DEBUG) {
                        Log.e(Constants.LOG, e.getMessage());
                    }
                }
            }
        };
        DialogInterface.OnClickListener negativeClick = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                uploadImage(getPath(picUri));
            }
        };

        AlertDialogYesNo(getActivity(), "Would You Like to Crop your image?", "Yes", "No", positiveClick, negativeClick);
    }

    public void AlertDialogYesNo(Context mContext, String title, String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener positiveClick, DialogInterface.OnClickListener negativeClick) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(positiveButtonText, positiveClick)
                .setNegativeButton(negativeButtonText, negativeClick);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void uploadImage(String imagePath) {
        try {
            File imgFile = new File(imagePath);
            progessDialog.show();
            String url = Constants.wcfUploadContentImages + contentToken;
            ionImageUpload = Ion.with(_fContext)
                    .load(url)
                    .uploadProgressBar(uploadProgressBar)
                    .setTimeout(60 * 60 * 1000)
                    .setMultipartFile("uploading", "image/jpeg", imgFile)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
                            try {
                                if (result != null) {
                                    List<Image> images = JsonToObject.JsonToImageList(result);
                                    for (Image item : images) {
                                        imageList.add(item);
                                        adapter.add(item, imageList.size() - 1);
                                    }
                                    progessDialog.dismiss();
                                    EnableDisableFab();
                                }
                            } catch (Exception ex) {
                                if (BuildConfig.DEBUG) {
                                    Log.e(Constants.LOG, ex.getMessage());
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    private void EnableDisableFab() {
        if (imageList.size() >= 4) {
            image_fab.setVisibility(View.GONE);
        } else {
            image_fab.setVisibility(View.VISIBLE);
        }
    }

    public void showImage(Image image) {
        final Dialog builder = new Dialog(_fContext);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView displayImageView = new ImageView(_fContext);
        displayImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        Ion.with(displayImageView)
                .placeholder(R.mipmap.ic_beercap)
                .load(image.getImageUrl());


        builder.addContentView(displayImageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    private void DeleteImage() {
//        LoadDialog(_fContext, false, true);
        String url = Constants.wcfRemoveContentImage + contentToken + "/" + selectedImagePk;
        ionDeleteImage = Ion.with(_fContext.getApplicationContext())
                .load(url)
                .addHeader("Content-Type", "application/json")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String s) {
                        adapter.remove(deletePosition);
                        imageList.remove(deletePosition);
//                        dialogProgress.dismiss();
                        resetDialogValues();
                        EnableDisableFab();

                    }
                });
    }

    private void resetDialogValues() {
        menuType = 1;
        deletePosition = 0;
        selectedImagePk = 0;
    }

    public void createProgessDialog() {
        LayoutInflater factory = LayoutInflater.from(_fContext);
        progessBarDialogView = factory.inflate(
                R.layout.dialog_progress_bar, null);
        progessDialog = new AlertDialog.Builder(_fContext).create();
        progessDialog.setCancelable(false);
        progessDialog.setView(progessBarDialogView);
    }
}
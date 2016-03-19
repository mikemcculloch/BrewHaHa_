package brightseer.com.brewhaha;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.Transform;
import com.koushikdutta.ion.future.ImageViewFuture;
import com.makeramen.RoundedDrawable;

import java.io.File;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.adapter.CommentAdapter;
import brightseer.com.brewhaha.helper.ToastAdListener;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.objects.Comment;
import brightseer.com.brewhaha.objects.RecipeImage;
import brightseer.com.brewhaha.objects.UserProfile;
import brightseer.com.brewhaha.repository.JsonToObject;

/**
 * Created by mccul_000 on 11/25/2014.
 */
public class BaseActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    String url;
    public String userToken = "na";
    final int CONTEXT_MENU_DOWNLOAD = 1;
    public View selectedView = null;
    protected ContextMenu contextMenu;
    public Dialog dialogProgress;
    public AdView mAdView;
    public Comment _Comment;
    public List<Comment> comments = new Vector<>();
    public int comment_count = 0;
    public Activity _Activity;
    public Context _mContext;
    public CommentAdapter commentAdapter;
    public Vibrator myVib;
    public int commentEditPk = 0;
    public final int CAMERA_CAPTURE = 1;
    public final int RESULT_LOAD_IMAGE = 1;
    public int cornerRadius = 200;
    public Future<JsonArray> loading;
    public Future<JsonObject> loadingObj;
    public ImageViewFuture imageFuture;
    public boolean tabletSize;
    private ShareActionProvider mShareActionProvider;
    public String contentToken;
    public String contentItemPk = "";

    public static final int RC_SIGN_IN = 0;
    public static final int PLUS_ONE_REQUEST_CODE = 0;
    public GoogleApiClient mGoogleApiClient;
    public boolean mIntentInProgress;
    public int adapterPosition;

    Transform trans = new Transform() {
        boolean isOval = false;

        @Override
        public Bitmap transform(Bitmap bitmap) {
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, cornerRadius, cornerRadius, false);
            Bitmap transformed = RoundedDrawable.fromBitmap(scaled).setScaleType(ImageView.ScaleType.CENTER_CROP).setCornerRadius(cornerRadius).setOval(isOval).toBitmap();
            if (!bitmap.equals(scaled)) bitmap.recycle();
            if (!scaled.equals(transformed)) bitmap.recycle();

            return transformed;
        }

        @Override
        public String key() {
            return "rounded_radius_" + cornerRadius + "_oval_" + isOval;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initGooglePlus();
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        tabletSize = getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
//        Share Menu
        MenuItem menuItem = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager SManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            android.support.v7.widget.SearchView searchViewAction =
                    (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search_main).getActionView();
            searchViewAction.setSearchableInfo(SManager.getSearchableInfo(getComponentName()));
            searchViewAction.setIconifiedByDefault(false);
            searchViewAction.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });

            return super.onCreateOptionsMenu(menu);
        }

        if (BrewSharedPrefs.getIsUserLoggedIn()) {
            menu.addSubMenu(0, 10, Menu.NONE, "Settings");
        }

        MenuItem action_menu_options = menu.findItem(R.id.action_menu_options);
        if (mGoogleApiClient.isConnected()) {
            action_menu_options.setVisible(true);
        }

//        MenuItem downloadMenu = menu.findItem(R.id.menu_item_download);
        /*clickedView = getCurrentFocus();
        if (v.getId() == R.id.grid) {
            AdapterView.AdapterContextMenuInfo info =
                    (AdapterView.AdapterContextMenuInfo) menuInfo;
            int thePosition = info.position;
            View newView = info.targetView;
            ImageView imageView = (ImageView) newView.findViewById(R.id.gridview_image_view);
            clickedView = imageView;
        }*/
//        downloadMenu.setActionView()

//        Floating menu dont delete
//        SubMenu submenu = menu.addSubMenu(0, Menu.FIRST, Menu.NONE, "Preferences");
//        submenu.add(0, 10, Menu.NONE, "Settings");
//        submenu.add(0, 15, Menu.NONE, "Get Last 10 Packets");
//        submenu.add(0, 20, Menu.NONE, "Get Last 20 Packets");
//        getMenuInflater().inflate(R.menu.main, submenu);


        return true;
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent getSettingsIntent = new Intent(getApplicationContext(), SettingsMain.class);
//        getSettingsIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        switch (item.getItemId()) {
            case R.id.action_search_main:
                return true;
//            case R.id.action_settings_main:
//                startActivityForResult(getSettingsIntent, RESULT_OK);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(BaseActivity.this);
//                    ActivityCompat.startActivityForResult(BaseActivity.this, getSettingsIntent, RESULT_OK, options.toBundle());
//                } else {
//                    startActivityForResult(getSettingsIntent, RESULT_OK);
//                }
//
//                return true;
            case R.id.menu_item_share:
                return true;
//            case 10:
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(BaseActivity.this);
//                    ActivityCompat.startActivityForResult(BaseActivity.this, getSettingsIntent, RESULT_OK, options.toBundle());
//                } else {
//                    startActivityForResult(getSettingsIntent, RESULT_OK);
//                }
//                ;
//                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

//            case R.id.menu_item_download:
//                Utilities.saveImage(selectedView);
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initGooglePlus() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Plus.API)
//                .addScope(new Scope(Scopes.PROFILE))
                    .addScope(Plus.SCOPE_PLUS_LOGIN)
//                .addScope(Plus.SCOPE_PLUS_PROFILE)
                    .build();
        }
    }

    public void signOut() {
        BrewSharedPrefs.clearAllPrefs();
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            //Revoke
//            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }
    }

    private Intent getShareDefaultIntent() {
        Uri.Builder b = Uri.parse(Constants.urlBrewHahaContent + BrewSharedPrefs.getCurrentContentTitle().replace(" ", "-")).buildUpon();
        url = b.build().toString();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Your Brew");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Found it on BREWhaha: " + Html.fromHtml(url));

//        shareIntent.putExtra(Intent.EXTRA_STREAM, b.toString());
//        shareIntent.putExtra(Intent.EXTRA_TEXT, "Found it on BREWhaha: " + Html.fromHtml(url) );
//        shareIntent.putExtra(Intent.EXTRA_STREAM, url);
        return shareIntent;
    }

    public void setShareIntent() {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(getShareDefaultIntent());
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case CONTEXT_MENU_DOWNLOAD:
                Utilities.saveImage(selectedView);
                break;
            case R.id.comment_menu_button_edit:
                EditComment(selectedView, _Comment);
                break;
            case R.id.comment_menu_button_delete:
                DeleteComment(selectedView, _Comment);
                break;
            case R.id.capture_menu_button:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(takePictureIntent, CAMERA_CAPTURE);
                break;
            case R.id.device_menu_button:
                Intent getPicture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getPicture.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);


                startActivityForResult(getPicture, RESULT_LOAD_IMAGE);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onContextMenuClosed(Menu menu) {
        super.onContextMenuClosed(menu);
    }

    private void EditComment(View v, Comment comment) {
        String body = comment.getBody();
        LinearLayout footer = (LinearLayout) _Activity.findViewById(R.id.footer);
        footer.setVisibility(View.VISIBLE);

        EditText recipe_comment_add_edit_text_view = (EditText) _Activity.findViewById(R.id.recipe_comment_add_edit_text_view);
        recipe_comment_add_edit_text_view.setText(body);
        recipe_comment_add_edit_text_view.setSelection(body.length());
        recipe_comment_add_edit_text_view.requestFocus();

        ImageView recipe_comment_add_image_view = (ImageView) _Activity.findViewById(R.id.recipe_comment_add_image_view);
        recipe_comment_add_image_view.setImageResource(R.drawable.ic_mode_edit_black_24dp);
        commentEditPk = comment.getCommentPk();
    }

    private void DeleteComment(View v, final Comment comment) {

        DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    commentAdapter.remove(comment);
                    comments.remove(comment);
                    comment_count = comment_count - 1;
                    if (comment_count <= 6) {
                        Button recipe_comments_load_more = (Button) _Activity.findViewById(R.id.recipe_comments_load_more);
                        recipe_comments_load_more.setVisibility(View.GONE);
                    }

                    dialog.dismiss();
                    RecipeActivity.setListViewHeightBasedOnChildren(((RecipeActivity) _Activity).comments_list_view);
                    Ion.with(_Activity.getApplicationContext())
                            .load(Constants.wcfRemoveComment + comment.getCommentPk())
                            .addHeader("Content-Type", "application/json")
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String s) {

                                }
                            });
                } catch (Exception e) {
                    if (BuildConfig.DEBUG) {
                        Log.e(Constants.LOG, e.getMessage());
                    }
                }
            }
        };
        DialogInterface.OnClickListener negativeClick = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                commentEditPk = 0;
                dialog.dismiss();
            }
        };
        AlertDialogYesNo(_Activity, "Delete Comment", "Are You Sure", "Yes", "No", positiveClick, negativeClick);
    }

    public void initAdMob() {
        mAdView = (AdView) findViewById(R.id.adView);

        if (BuildConfig.FLAVOR == Constants.flavorFull) {
            mAdView.setVisibility(View.GONE);
            return;
        }
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice(getResources().getText(R.string.deviceIdMike).toString())
//                .addTestDevice(getResources().getText(R.string.deviceIds4).toString())
//                .addTestDevice(getText(R.string.deviceIdMikeTablet).toString())
//                .addTestDevice(getText(R.string.deviceElyseS3).toString())
                .build();
        mAdView.setAdListener(new ToastAdListener(this));
        mAdView.loadAd(adRequest);
    }

    public void initGoogleAnalytics(String path) {
        Tracker t = ((BrewApplication) getApplication()).getTracker(
                BrewApplication.TrackerName.APP_TRACKER);
        t.setScreenName(path);
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    public void eventGoogleAnalytics(String categoryId, String actionId, String labelId) {
        Tracker t = ((BrewApplication) getApplication()).getTracker(
                BrewApplication.TrackerName.APP_TRACKER);
        t.send(new HitBuilders.EventBuilder()
                .setCategory(categoryId)
                .setAction(actionId)
                .setLabel(labelId)
                .build());
    }

    public void eventGoogleAnalytics(String categoryId, String actionId, String labelId, int value) {
        Tracker t = ((BrewApplication) getApplication()).getTracker(
                BrewApplication.TrackerName.APP_TRACKER);
        t.send(new HitBuilders.EventBuilder()
                .setCategory(categoryId)
                .setAction(actionId)
                .setLabel(labelId)
                .setValue(value)
                .build());
    }

    private void exceptionGoogleAnalytics(String exceptionDescription, String exceptionLocation, boolean fatal) {
        Tracker t = ((BrewApplication) getApplication()).getTracker(
                BrewApplication.TrackerName.APP_TRACKER);
        t.send(new HitBuilders.ExceptionBuilder()
                .setDescription(exceptionDescription + ":" + exceptionLocation)
                .setFatal(fatal).build());
    }

    public void initAdvertisingIdCollection() {
        Tracker t = ((BrewApplication) getApplication()).getTracker(
                BrewApplication.TrackerName.APP_TRACKER);
        t.enableAdvertisingIdCollection(true);
    }

    public void LoadDialog(Context mContext, boolean dimBackground, boolean cancelable) {
        dialogProgress = new Dialog(mContext);
        dialogProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogProgress.setContentView(R.layout.dialog_loading);
        dialogProgress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogProgress.setCancelable(cancelable);

        if (!dimBackground) {
            dialogProgress.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        dialogProgress.show();
    }

    public void AlertDialog(Context mContext, String Title, String Body, String NegativeButtonText) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        // set title
        alertDialogBuilder.setTitle(Title);

        // set dialog message
        alertDialogBuilder
                .setMessage(Body)
                .setCancelable(false)
//                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//
//                    }
//                })
                .setNegativeButton(NegativeButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void AlertDialogYesNo(Context mContext, String title, String body, String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener positiveClick, DialogInterface.OnClickListener negativeClick) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(body)
                .setCancelable(false)
                .setPositiveButton(positiveButtonText, positiveClick)
                .setNegativeButton(negativeButtonText, negativeClick);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private DialogInterface.OnClickListener negativeClick = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
        }
    };

    public void AlertLoginPrompt(Context mContext, String title, String body, String loginText, String negativeText) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(body)
                .setCancelable(false)
                .setPositiveButton(loginText, loginClick)
//                .setNeutralButton(signUpText, signUpClick)
                .setNegativeButton(negativeText, negativeClick);


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Ion.getDefault(_mContext).dump();
        Ion.getDefault(_mContext).configure().getResponseCache().clear();
        if (loading != null) {
            loading.cancel();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (loading != null) {
            loading.cancel();
        }

        if (dialogProgress != null)
            dialogProgress.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loading != null) {
            loading.cancel();
        }
        Ion.getDefault(_mContext).dump();
        Ion.getDefault(_mContext).configure().getResponseCache().clear();

//        long teset = Runtime.getRuntime().totalMemory();
        trans = null;

        try {
//            trimCache(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    public void setupTransistion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);


            //set the transition
            Transition ts = new android.transition.Explode();
            ts.setDuration(300);
            getWindow().setSharedElementEnterTransition(ts);
            getWindow().setSharedElementExitTransition(ts);
            getWindow().setSharedElementsUseOverlay(false);


//            getWindow().setAllowEnterTransitionOverlap(false); --meh
//            getWindow().setAllowReturnTransitionOverlap(false); --meh
//            getWindow().setEnterTransition(ts);
            //set an exit transition so it is activated when the current activity exits
            getWindow().setExitTransition(ts);

            Slide transitionEnter = new Slide();
            transitionEnter.setSlideEdge(Gravity.TOP);
            transitionEnter.setDuration(300);
            Window currentW = getWindow();
            currentW.setEnterTransition(transitionEnter);
            currentW.setExitTransition(transitionEnter);

            getWindow().setAllowEnterTransitionOverlap(true);
            getWindow().setAllowReturnTransitionOverlap(true);
        }
    }

    public void setupTransistionSlide() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);


            //set the transition
//            Transition ts = new android.transition.Explode();
//            ts.setDuration(300);
//            getWindow().setSharedElementEnterTransition(ts);
//            getWindow().setSharedElementExitTransition(ts);



//            getWindow().setAllowEnterTransitionOverlap(false); --meh
//            getWindow().setAllowReturnTransitionOverlap(false); --meh
//            getWindow().setEnterTransition(ts);
            //set an exit transition so it is activated when the current activity exits
//            getWindow().setExitTransition(ts);
//
//            Slide transitionEnter = new Slide();
//            transitionEnter.setSlideEdge(Gravity.TOP);
//            transitionEnter.setDuration(300);
//            Window currentW = getWindow();
//            currentW.setEnterTransition(transitionEnter);
//            currentW.setExitTransition(transitionEnter);


            Slide slide = new Slide(Gravity.BOTTOM);
//            slide.addTarget(R.id.root);
            slide.setInterpolator(
                    AnimationUtils.loadInterpolator(this,
                            android.R.interpolator.linear_out_slow_in));
            slide.setDuration(500);

            getWindow().setEnterTransition(slide);
            getWindow().setExitTransition(slide);
            getWindow().setSharedElementsUseOverlay(false);
            getWindow().setAllowEnterTransitionOverlap(true);
            getWindow().setAllowReturnTransitionOverlap(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(adapterPosition);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            finish();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
//        String test = "test";
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress && result.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(result.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private DialogInterface.OnClickListener loginClick = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (BrewSharedPrefs.getIsUserLoggedIn()) {
            initGooglePlus();
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    public void PlusAccountSetup() {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("googlePlusId", 0);
            json.addProperty("fullName", BrewSharedPrefs.getFullName());
            json.addProperty("emailAddress", BrewSharedPrefs.getEmailAddress());
            json.addProperty("userImageUrl", BrewSharedPrefs.getUserProfileImageUrl());
            json.addProperty("plusProfile", BrewSharedPrefs.getGoolgePlusProfileUrl());

            String url = Constants.wcfPlusValidation;
            Ion.with(_mContext)
                    .load(url)
                    .setHeader("Cache-Control", "No-Cache")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            try {
                                if (result != null) {
                                    UserProfile userProfile = JsonToObject.JsonToUserProfile(result);
                                    if (userProfile != null) {
                                        if (userProfile.getToken() != null) {
                                            BrewSharedPrefs.setUserToken(userProfile.getToken());
                                            BrewSharedPrefs.setIsUserLoggedIn(true);
                                            BrewSharedPrefs.setShowWelcome(true);
                                            BrewSharedPrefs.setScreenName(userProfile.getScreenName());
                                            BrewSharedPrefs.setFullName(userProfile.getFullName());
                                            BrewSharedPrefs.setEmailAddress(userProfile.getEmailAddress());
                                            BrewSharedPrefs.setUserProfileImageUrl(userProfile.getImageUrl());
                                            BrewSharedPrefs.setUserProfileDate(userProfile.getSignupDate());
                                            BrewSharedPrefs.setAcceptedTerms(userProfile.getDisclaimer());
                                        }
                                    }
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

    public void showImage(RecipeImage recipeImage) {
//        final Dialog builder = new Dialog(this);
        final Dialog builder = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });
        LayoutInflater factory = LayoutInflater.from(this);
        View dialogImage = factory.inflate(
                R.layout.dialog_image, null);

        builder.setContentView(dialogImage);

        ImageView dialog_image = (ImageView) dialogImage.findViewById(R.id.dialog_image);
        dialog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        Ion.with(dialog_image)
                .placeholder(R.mipmap.ic_beercap)
                .load(recipeImage.getImageUrl()
                );
        builder.show();
    }
}


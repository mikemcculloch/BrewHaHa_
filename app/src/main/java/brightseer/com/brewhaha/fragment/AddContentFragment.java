package brightseer.com.brewhaha.fragment;

import android.animation.LayoutTransition;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.AddUpdateRecipe;
import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.RecipeActivity;
import brightseer.com.brewhaha.objects.BatchSize;
import brightseer.com.brewhaha.objects.Ingredient;
import brightseer.com.brewhaha.objects.RecipeContent;
import brightseer.com.brewhaha.objects.Style;
import brightseer.com.brewhaha.repository.DBHelper_BatchSize;
import brightseer.com.brewhaha.repository.DBHelper_Style;
import brightseer.com.brewhaha.repository.JsonToObject;

/**
 * Created by Michael McCulloch on 2/26/2015.
 */
public class AddContentFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private String contentItemPk = "", contentToken = "";
    private DBHelper_Style repoStyle;
    private EditText my_recipe_title_edit_text, my_recipe_description_edit_text;

    private EditText my_recipe_og_value_text_view, my_recipe_srm_value_text_view, my_recipe_fg_value_text_view, my_recipe_ibu_value_text_view, my_recipe_abv_value_text_view;

    private Spinner my_recipe_batch_size_spinner, my_recipe_style_spinner;
    private int selectedBatchSizePk, selectedStylePk;
    private android.support.v7.widget.AppCompatButton my_recipe_publish_button;
    private List<Style> styleList;
    private SeekBar my_recipe_abv_seekbar, my_recipe_og_seekbar, my_recipe_fg_seekbar, my_recipe_srm_seekbar, my_recipe_ibu_seekbar;
    private TextView recipe_preview_link;
    private float abvProgress = 0, ogProgress = 0, fgProgress = 0, srmProgress = 0, ibuProgress = 0;
    private LinearLayout my_recipe_seekbar_layout;
    private ImageView my_recipe_expand_coll_imageview;
    private RelativeLayout my_grain_relative_layout;
    public Future<JsonObject> ionLoadRecipe;
    public Future<String> ionUpdateRecipe;
    public boolean activeRecipe = false, submittedRecipe = false;

    @Override //1
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _fContext = getActivity();
        initGoogleAnalytics(this.getClass().getSimpleName());
        Bundle bundle = this.getArguments();
        contentItemPk = bundle.getString(Constants.exContentItemPk);
//        LoadDialog(_fContext, false, true);

        repoStyle = new DBHelper_Style(getActivity());
        styleList = repoStyle.getStyleList();
    }

    @Override //2
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_content_add, container, false);
        initViews();
        EditTextListner();

        loadContent();
        return rootView;
    }

    public void loadContent() {
        String url = Constants.wcfGetContentById + contentItemPk + "/" + BrewSharedPrefs.getUserToken();
        ionLoadRecipe = Ion.with(_fContext)
                .load(url)
                .setHeader("Cache-Control", "No-Cache")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                                 @Override
                                 public void onCompleted(Exception e, JsonObject result) {
                                     try {

                                         RecipeContent recipeContent = JsonToObject.JsonToRecipeContent(result);
                                         contentToken = recipeContent.getToken();
                                         my_recipe_title_edit_text.setText(recipeContent.getTitle());
                                         my_recipe_description_edit_text.setText(recipeContent.getDescription());
                                         selectedStylePk = recipeContent.getStylePk();
                                         selectedBatchSizePk = recipeContent.getBatchSizePk();
                                         activeRecipe = recipeContent.isApproved();
                                         submittedRecipe = recipeContent.isSubmitted();
                                         abvProgress = Float.parseFloat(recipeContent.getRecipeSummaryM().getAlcoholByVol());
                                         ogProgress = Float.parseFloat(recipeContent.getRecipeSummaryM().getOriginalGravity());
                                         fgProgress = Float.parseFloat(recipeContent.getRecipeSummaryM().getFinalGravity());
                                         srmProgress = Float.parseFloat(recipeContent.getRecipeSummaryM().getColorSrm());
                                         ibuProgress = Float.parseFloat(recipeContent.getRecipeSummaryM().getBitternessIbu());

                                         my_recipe_og_value_text_view.setText(String.valueOf(ogProgress));
                                         my_recipe_srm_value_text_view.setText(String.valueOf(srmProgress));
                                         my_recipe_fg_value_text_view.setText(String.valueOf(fgProgress));
                                         my_recipe_ibu_value_text_view.setText(String.valueOf(ibuProgress));
                                         my_recipe_abv_value_text_view.setText(String.valueOf(abvProgress));


                                         populateBatchSizeSpinner(selectedBatchSizePk);
                                         populateStyleSpinner(repoStyle.getPostion(selectedStylePk, styleList));
                                         addListenerSpinner();
                                         setSeekBarProgress();
                                         setOnfocusListener();
                                         setPreviewMode();
                                         setButtonView();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_recipe_update_button:
                SubmitChanges();
                break;
            case R.id.my_recipe_cancel_button:
//                LoadDialog(_fContext, false, false);
                loadContent();
                break;
            case R.id.my_recipe_expand_coll_imageview:
                toggleSeekbars();
                break;

            case R.id.my_recipe_setAvb_layout:
                toggleSeekbars();
                break;

            case R.id.recipe_preview_link:
                OpenRecipe();
                break;

            case R.id.my_recipe_publish_button:
                publishRecipe();
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (ionLoadRecipe != null) {
            if (!ionLoadRecipe.isDone()) {
                ionLoadRecipe.cancel();
            }
        }
        if (ionUpdateRecipe != null) {
            if (!ionUpdateRecipe.isDone())
                ionUpdateRecipe.cancel();
        }
    }

    public void SubmitChanges() {
        JsonObject json = new JsonObject();
        json.addProperty("title", my_recipe_title_edit_text.getText().toString().trim().replaceAll("[\\/\\-\\+\\.\\^:,]", " "));
        json.addProperty("description", my_recipe_description_edit_text.getText().toString().trim());

        if (selectedBatchSizePk == 0 || selectedStylePk == 0) {

            Toast.makeText(_fContext,
                    "Select a batch size & Style ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(my_recipe_title_edit_text.getText().toString())) {

            Toast.makeText(_fContext,
                    "Missing Title", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(my_recipe_description_edit_text.getText().toString())) {

            Toast.makeText(_fContext,
                    "Missing Description", Toast.LENGTH_SHORT).show();
            return;
        }

//        LoadDialog(_fContext, false, true);
        String url = Constants.wcfUpdateRecipeContent + contentToken + "/" + BrewSharedPrefs.getUserToken() + "/" + selectedBatchSizePk + "/" + selectedStylePk + "/" + abvProgress + "/" + ogProgress + "/" + fgProgress + "/" + srmProgress + "/" + ibuProgress;
        ionUpdateRecipe = Ion.with(_fContext)
                .load(url)
                .setJsonObjectBody(json)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String s) {
                        try {
                            Boolean success = Boolean.valueOf(s);
                            Snackbar.make(rootView.findViewById(R.id.frag_content_add_parent), "Updated", Snackbar.LENGTH_SHORT)
                                    .show();
                        } catch (Exception ex) {
                            if (BuildConfig.DEBUG) {
                                Log.e(Constants.LOG, ex.getMessage());
                            }
//                            dialogProgress.dismiss();
                        }
                    }
                });

    }

    public void initViews() {
        my_recipe_title_edit_text = (EditText) rootView.findViewById(R.id.my_recipe_title_edit_text);
        my_recipe_title_edit_text.clearFocus();
        my_recipe_description_edit_text = (EditText) rootView.findViewById(R.id.my_recipe_description_edit_text);
        my_recipe_description_edit_text.clearFocus();
        my_recipe_batch_size_spinner = (Spinner) rootView.findViewById(R.id.my_recipe_batch_size_spinner);
        my_recipe_style_spinner = (Spinner) rootView.findViewById(R.id.my_recipe_style_spinner);

        android.support.v7.widget.AppCompatButton my_recipe_update_button = (android.support.v7.widget.AppCompatButton) rootView.findViewById(R.id.my_recipe_update_button);
        my_recipe_update_button.setOnClickListener(this);

        android.support.v7.widget.AppCompatButton my_recipe_cancel_button = (android.support.v7.widget.AppCompatButton) rootView.findViewById(R.id.my_recipe_cancel_button);
        my_recipe_cancel_button.setOnClickListener(this);

        my_recipe_abv_seekbar = (SeekBar) rootView.findViewById(R.id.my_recipe_abv_seekbar);
        my_recipe_abv_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                abvProgress = (float) progresValue / 10;
                if (fromUser) {
                    my_recipe_abv_value_text_view.setText(String.valueOf(abvProgress));
                    my_recipe_abv_value_text_view.setSelection(my_recipe_abv_value_text_view.getText().length());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        my_recipe_og_seekbar = (SeekBar) rootView.findViewById(R.id.my_recipe_og_seekbar);
        my_recipe_og_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                ogProgress = (float) progresValue / 1000;
                if (fromUser) {
                    my_recipe_og_value_text_view.setText(String.valueOf(ogProgress));
                    my_recipe_og_value_text_view.setSelection(my_recipe_og_value_text_view.getText().length());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        my_recipe_fg_seekbar = (SeekBar) rootView.findViewById(R.id.my_recipe_fg_seekbar);
        my_recipe_fg_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                fgProgress = (float) progresValue / 1000;
                if (fromUser) {
                    my_recipe_fg_value_text_view.setText(String.valueOf(fgProgress));
                    my_recipe_fg_value_text_view.setSelection(my_recipe_fg_value_text_view.getText().length());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        my_recipe_srm_seekbar = (SeekBar) rootView.findViewById(R.id.my_recipe_srm_seekbar);
        my_recipe_srm_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                srmProgress = (float) progresValue / 10;
                if (fromUser) {
                    my_recipe_srm_value_text_view.setText(String.valueOf(srmProgress));
                    my_recipe_srm_value_text_view.setSelection(my_recipe_srm_value_text_view.getText().length());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        my_recipe_ibu_seekbar = (SeekBar) rootView.findViewById(R.id.my_recipe_ibu_seekbar);
        my_recipe_ibu_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                ibuProgress = (float) progresValue / 10;
                if (fromUser) {
                    my_recipe_ibu_value_text_view.setText(String.valueOf(ibuProgress));
                    my_recipe_ibu_value_text_view.setSelection(my_recipe_ibu_value_text_view.getText().length());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        my_recipe_abv_value_text_view = (EditText) rootView.findViewById(R.id.my_recipe_abv_value_text_view);
        my_recipe_og_value_text_view = (EditText) rootView.findViewById(R.id.my_recipe_og_value_text_view);

        my_recipe_fg_value_text_view = (EditText) rootView.findViewById(R.id.my_recipe_fg_value_text_view);
        my_recipe_srm_value_text_view = (EditText) rootView.findViewById(R.id.my_recipe_srm_value_text_view);
        my_recipe_ibu_value_text_view = (EditText) rootView.findViewById(R.id.my_recipe_ibu_value_text_view);

        ScrollView my_recipe_content_scrollview = (ScrollView) rootView.findViewById(R.id.my_recipe_content_scrollview);
        my_recipe_content_scrollview.requestFocus();

        LinearLayout my_recipe_setAvb_layout = (LinearLayout) rootView.findViewById(R.id.my_recipe_setAvb_layout);
        my_recipe_setAvb_layout.setOnClickListener(this);

        my_recipe_seekbar_layout = (LinearLayout) rootView.findViewById(R.id.my_recipe_seekbar_layout);
        my_recipe_seekbar_layout.setVisibility(View.GONE);

        my_recipe_expand_coll_imageview = (ImageView) rootView.findViewById(R.id.my_recipe_expand_coll_imageview);
        my_recipe_expand_coll_imageview.setOnClickListener(this);

        FrameLayout frame_seekbar_toggle = (FrameLayout) rootView.findViewById(R.id.frame_seekbar_toggle);

        my_grain_relative_layout = (RelativeLayout) rootView.findViewById(R.id.my_grain_relative_layout);

        recipe_preview_link = (TextView) rootView.findViewById(R.id.recipe_preview_link);
        recipe_preview_link.setOnClickListener(this);

        my_recipe_publish_button = (android.support.v7.widget.AppCompatButton) rootView.findViewById(R.id.my_recipe_publish_button);
        my_recipe_publish_button.setOnClickListener(this);
    }

    public void setPreviewMode() {
        if (activeRecipe) {
            recipe_preview_link.setText("View Recipe");
            my_recipe_publish_button.setVisibility(View.GONE);
        } else {
            recipe_preview_link.setText("Preview Recipe");
            my_recipe_publish_button.setVisibility(View.VISIBLE);
        }
    }

    public void setButtonView() {
        if (submittedRecipe) {
            my_recipe_publish_button.setVisibility(View.GONE);
        }
    }

    public void EditTextListner() {
        my_recipe_og_value_text_view.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                float newValue = 0;
                String inputText = my_recipe_og_value_text_view.getText().toString();
                if (!TextUtils.isEmpty(inputText)) {
                    String valueSubstring = inputText.substring(0, 1);
                    if (!TextUtils.isDigitsOnly(valueSubstring)) {
                        inputText = "0" + inputText;
                        my_recipe_og_value_text_view.setText(inputText);
                        my_recipe_og_value_text_view.setSelection(inputText.length());
                    }
                    newValue = Float.parseFloat(inputText);
                    newValue = newValue * 1000;
                }
                my_recipe_og_seekbar.setProgress((int) newValue);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int i = 0;
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int i = 0;
            }
        });

        my_recipe_srm_value_text_view.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                float newValue = 0;
                String inputText = my_recipe_srm_value_text_view.getText().toString();
                if (!TextUtils.isEmpty(inputText)) {
                    String valueSubstring = inputText.substring(0, 1);
                    if (!TextUtils.isDigitsOnly(valueSubstring)) {
                        inputText = "0" + inputText;
                        my_recipe_srm_value_text_view.setText(inputText);
                        my_recipe_srm_value_text_view.setSelection(inputText.length());
                    }
                    newValue = Float.parseFloat(inputText);
                    newValue = newValue * 10;
                }
                my_recipe_srm_seekbar.setProgress((int) newValue);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int i = 0;
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int i = 0;
            }
        });

        my_recipe_fg_value_text_view.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                float newValue = 0;
                String inputText = my_recipe_fg_value_text_view.getText().toString();
                if (!TextUtils.isEmpty(inputText)) {
                    String valueSubstring = inputText.substring(0, 1);
                    if (!TextUtils.isDigitsOnly(valueSubstring)) {
                        inputText = "0" + inputText;
                        my_recipe_fg_value_text_view.setText(inputText);
                        my_recipe_fg_value_text_view.setSelection(inputText.length());
                    }
                    newValue = Float.parseFloat(inputText);
                    newValue = newValue * 1000;
                }
                my_recipe_fg_seekbar.setProgress((int) newValue);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int i = 0;
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int i = 0;
            }
        });

        my_recipe_ibu_value_text_view.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                float newValue = 0;
                String inputText = my_recipe_ibu_value_text_view.getText().toString();
                if (!TextUtils.isEmpty(inputText)) {
                    String valueSubstring = inputText.substring(0, 1);
                    if (!TextUtils.isDigitsOnly(valueSubstring)) {
                        inputText = "0" + inputText;
                        my_recipe_ibu_value_text_view.setText(inputText);
                        my_recipe_ibu_value_text_view.setSelection(inputText.length());
                    }
                    newValue = Float.parseFloat(inputText);
                    newValue = newValue * 10;
                }
                my_recipe_ibu_seekbar.setProgress((int) newValue);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int i = 0;
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int i = 0;
            }
        });

        my_recipe_abv_value_text_view.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                float newValue = 0;
                String inputText = my_recipe_abv_value_text_view.getText().toString();
                if (!TextUtils.isEmpty(inputText)) {
                    String valueSubstring = inputText.substring(0, 1);
                    if (!TextUtils.isDigitsOnly(valueSubstring)) {
                        inputText = "0" + inputText;
                        my_recipe_abv_value_text_view.setText(inputText);
                        my_recipe_abv_value_text_view.setSelection(inputText.length());
                    }
                    newValue = Float.parseFloat(inputText);
                    newValue = newValue * 10;
                }
                my_recipe_abv_seekbar.setProgress((int) newValue);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int i = 0;
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int i = 0;
            }
        });
    }

    public void populateBatchSizeSpinner(int position) {
        DBHelper_BatchSize batchSize = new DBHelper_BatchSize(_fContext);
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(getText(R.string.text_batch_size_spinner_default).toString());
        for (BatchSize item : batchSize.getBatchSizeList()) {
            stringArrayList.add(item.getValue() + " " + item.getDescription());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        my_recipe_batch_size_spinner.setAdapter(adapter);
        my_recipe_batch_size_spinner.setSelection(position, true);

    }

    public void populateStyleSpinner(int position) {
        ArrayList<String> stringStyleArrayList = new ArrayList<>();
        stringStyleArrayList.add(getText(R.string.text_style_spinner_default).toString());
        for (Style item : styleList) {
            stringStyleArrayList.add(item.getDescription());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringStyleArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        my_recipe_style_spinner.setAdapter(adapter);
        my_recipe_style_spinner.setSelection(position + 1);
    }

    public void addListenerSpinner() {
        my_recipe_batch_size_spinner.post(new Runnable() {
            @Override
            public void run() {
                my_recipe_batch_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        selectedBatchSizePk = pos;
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO maybe add something here later
                    }
                });
            }
        });

        my_recipe_style_spinner.post(new Runnable() {
            @Override
            public void run() {
                my_recipe_style_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {

                        repoStyle = new DBHelper_Style(getActivity());
                        Style styleItem = repoStyle.getStyle(parent.getItemAtPosition(pos).toString());
                        selectedStylePk = 0;
                        if (styleItem != null) {
                            selectedStylePk = styleItem.getStylePk();
                        }
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO maybe add something here later
                    }
                });
            }
        });
    }

    public void setSeekBarProgress() {

        my_recipe_abv_seekbar.setProgress((int) (abvProgress * 10));
        my_recipe_og_seekbar.setProgress((int) (ogProgress * 1000));
        my_recipe_fg_seekbar.setProgress((int) (fgProgress * 1000));
        my_recipe_srm_seekbar.setProgress((int) (srmProgress * 10));
        my_recipe_ibu_seekbar.setProgress((int) (ibuProgress * 10));
    }

    public void toggleSeekbars() {
        LayoutTransition tranistion = my_grain_relative_layout.getLayoutTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            tranistion.enableTransitionType(LayoutTransition.CHANGING);
            tranistion.enableTransitionType(LayoutTransition.CHANGE_APPEARING);
            tranistion.enableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
        }

        if (my_recipe_seekbar_layout.getVisibility() == View.GONE) {
            my_recipe_seekbar_layout.setVisibility(View.VISIBLE);
            my_recipe_expand_coll_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));

        } else {
            my_recipe_seekbar_layout.setVisibility(View.GONE);
            my_recipe_expand_coll_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
        }
    }

    public void setOnfocusListener() {
//((MyActivity)this.getActivity()).getJob();
        my_recipe_title_edit_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                mAdView = ((AddUpdateRecipe) _fContext).mAdView;

                if (hasFocus) {
                    mAdView.setVisibility(View.GONE);
                } else {
                    mAdView.setVisibility(View.VISIBLE);
                }
            }
        });

        my_recipe_description_edit_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mAdView = ((AddUpdateRecipe) _fContext).mAdView;
                if (hasFocus) {
                    mAdView.setVisibility(View.GONE);
                } else {
                    mAdView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void OpenRecipe() {
        try {
            Intent recipeIntent = new Intent(getActivity().getApplicationContext(), RecipeActivity.class);

            assert recipeIntent != null;

            recipeIntent.putExtra(Constants.exRecipeTitle, my_recipe_title_edit_text.getText().toString());
            recipeIntent.putExtra(Constants.exContentItemPk, String.valueOf(contentItemPk));
            recipeIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            if (!activeRecipe) {
                recipeIntent.putExtra(Constants.exRecipePreview, true);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                ActivityCompat.startActivity(getActivity(), recipeIntent, options.toBundle());
            } else {
                startActivity(recipeIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }


        } catch (Exception e) {
        }
    }

    public void publishRecipe() {
        LoadDialog(_fContext, false, false);
        String url = Constants.wcfGetContentById + contentItemPk + "/" + BrewSharedPrefs.getUserToken();
        ionLoadRecipe = Ion.with(_fContext)
                .load(url)
                .setHeader("Cache-Control", "No-Cache")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                                 @Override
                                 public void onCompleted(Exception e, JsonObject result) {
                                     try {
                                         RecipeContent recipeContent = JsonToObject.JsonToRecipeContent(result);
                                         boolean validRecipe = true;
                                         String errorMessage = "";

                                         if (TextUtils.isEmpty(recipeContent.getDescription()) && validRecipe) {
                                             validRecipe = false;
                                             errorMessage = "Missing Description";
                                         }
                                         if (TextUtils.isEmpty(recipeContent.getTitle()) && validRecipe) {
                                             validRecipe = false;
                                             errorMessage = "Missing Title";
                                         }

                                         List<Ingredient> ingredientList = recipeContent.getIngredientMList();
                                         List<Ingredient> grains = new Vector<>();
                                         List<Ingredient> hops = new Vector<>();
                                         List<Ingredient> yeasts = new Vector<>();
                                         for (Ingredient item : ingredientList) {
                                             if (item.getType().equals("Grain")) {
                                                 grains.add(item);
                                             }
                                             if (item.getType().equals("Hops")) {
                                                 hops.add(item);
                                             }
                                             if (item.getType().equals("Yeast")) {
                                                 yeasts.add(item);
                                             }
                                         }

                                         if (grains.size() <= 0 && validRecipe) {
                                             validRecipe = false;
                                             errorMessage = "You Must add at least 1 Grain";
                                         }
                                         if (hops.size() <= 0 && validRecipe) {
                                             validRecipe = false;
                                             errorMessage = "You Must add at least 1 Hop";
                                         }
                                         if (yeasts.size() <= 0 && validRecipe) {
                                             validRecipe = false;
                                             errorMessage = "You Must add at least 1 Yeast";
                                         }

                                         if (validRecipe) {
                                             RequestApproval();
                                         } else {
                                             Toast.makeText(_fContext, errorMessage, Toast.LENGTH_LONG).show();
                                         }
                                         dialogProgress.dismiss();
                                     } catch (Exception ex) {
                                         if (BuildConfig.DEBUG) {
                                             Log.e(Constants.LOG, ex.getMessage());
                                         }
                                         dialogProgress.dismiss();
                                     }
                                 }
                             }
                );
    }

    private void RequestApproval() {
        String url = Constants.wcfRequestApproval + BrewSharedPrefs.getUserToken() + "/" + contentToken;
        Ion.with(_fContext)
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String s) {
                        try {
                            Boolean success = Boolean.valueOf(s);
//                            snackTime("Publish Request Sent", _fContext);
                            //snackbar

                            Snackbar.make(rootView.findViewById(R.id.frag_content_add_parent), "Publish Request Sent", Snackbar.LENGTH_SHORT)
                                    .show();
                            my_recipe_publish_button.setVisibility(View.GONE);
                        } catch (Exception ex) {
                            if (BuildConfig.DEBUG) {
                                Log.e(Constants.LOG, ex.getMessage());
                            }
                        }
                    }
                });
    }
}
package brightseer.com.brewhaha.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.SearchResultsActivity;
import brightseer.com.brewhaha.objects.Grain;
import brightseer.com.brewhaha.objects.Hops;
import brightseer.com.brewhaha.objects.RecipeType;
import brightseer.com.brewhaha.objects.Style;
import brightseer.com.brewhaha.objects.Yeast;
import brightseer.com.brewhaha.repository.DBHelper_Grain;
import brightseer.com.brewhaha.repository.DBHelper_Hops;
import brightseer.com.brewhaha.repository.DBHelper_RecipeType;
import brightseer.com.brewhaha.repository.DBHelper_Style;
import brightseer.com.brewhaha.repository.DBHelper_Yeast;

/**
 * Created by mccul_000 on 10/1/2014.
 */
public class AdvancedSearchFragment extends BaseFragment implements View.OnClickListener {
    private DBHelper_RecipeType repoRecipeType;
    private DBHelper_Style repoStyle;
    private DBHelper_Grain repoGrain;
    private DBHelper_Hops repoHops;
    private DBHelper_Yeast repoYeast;

    private Spinner search_style_spinner, search_recipe_type_spinner, search_grain_spinner, search_hops_spinner, search_yeast_spinner;
    private android.support.v7.widget.AppCompatButton search_advanced_submit_button;
    private EditText search_advanced_text_edit_text;
    private TextView search_abv_value_text_view, search_ibu_value_text_view, search_expected_results_text_view;
    private int selectedStyle = 0, selectedType = 0, grainPk = 0, hopsPk = 0, yeastPk = 0;
    private SeekBar search_abv_seekbar, search_ibu_seekbar;
    private View rootView;
    private int abvProgress = 0;
    private int ibuProgress = 0;


    public AdvancedSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_advanced_search, container, false);

        _fContext = getActivity();
//        initAdMob(rootView);
        initSpinners();
        initButtons();
        initEditText();
        initSeekBar();
        initTextView();
        populateRecipeTypeSpinner();
        populateStyleSpinner();
        populateGrainSpinner();
        populateHopsSpinner();
        populateYeastSpinner();
        addListenerSpinner();
        return rootView;
    }

    private void initSpinners() {
        search_style_spinner = (Spinner) rootView.findViewById(R.id.search_style_spinner);
        search_recipe_type_spinner = (Spinner) rootView.findViewById(R.id.search_recipe_type_spinner);
        search_grain_spinner = (Spinner) rootView.findViewById(R.id.search_grain_spinner);
        search_hops_spinner = (Spinner) rootView.findViewById(R.id.search_hops_spinner);
        search_yeast_spinner = (Spinner) rootView.findViewById(R.id.search_yeast_spinner);
    }

    private void initButtons() {
        search_advanced_submit_button = (android.support.v7.widget.AppCompatButton) rootView.findViewById(R.id.search_advanced_submit_button);
        search_advanced_submit_button.setOnClickListener(this);
    }

    private void initEditText() {
        search_advanced_text_edit_text = (EditText) rootView.findViewById(R.id.search_advanced_text_edit_text);
        search_advanced_text_edit_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    getSearchResultsCount();
                }
            }
        });
    }

    private void initTextView() {
        search_abv_value_text_view = (TextView) rootView.findViewById(R.id.search_abv_value_text_view);
        search_ibu_value_text_view = (TextView) rootView.findViewById(R.id.search_ibu_value_text_view);
        search_expected_results_text_view = (TextView) rootView.findViewById(R.id.search_expected_results_text_view);
    }

    private void initSeekBar() {
        search_abv_seekbar = (SeekBar) rootView.findViewById(R.id.search_abv_seekbar);
        search_abv_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                abvProgress = progresValue;
                search_abv_value_text_view.setText(String.valueOf(abvProgress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                getSearchResultsCount();
            }
        });

        search_ibu_seekbar = (SeekBar) rootView.findViewById(R.id.search_ibu_seekbar);
        search_ibu_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                ibuProgress = progresValue;
                search_ibu_value_text_view.setText(String.valueOf(ibuProgress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                getSearchResultsCount();
            }
        });
    }

    public void populateRecipeTypeSpinner() {
        repoRecipeType = new DBHelper_RecipeType(getActivity());

        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(getText(R.string.text_recipe_type_spinner_default).toString());
        for (RecipeType item : repoRecipeType.getRecipeTypeList()) {
            stringArrayList.add(item.getDescription());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        search_recipe_type_spinner.setAdapter(adapter);
    }

    public void populateStyleSpinner() {
        repoStyle = new DBHelper_Style(getActivity());

        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(getText(R.string.text_style_spinner_default).toString());
        for (Style item : repoStyle.getStyleList()) {
            stringArrayList.add(item.getDescription());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search_style_spinner.setAdapter(adapter);
    }

    public void populateGrainSpinner() {
        repoGrain = new DBHelper_Grain(getActivity());

        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(getText(R.string.text_grain).toString());
        for (Grain item : repoGrain.getGrainList()) {
            stringArrayList.add(item.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search_grain_spinner.setAdapter(adapter);
        if (stringArrayList.size() == 1) {
            search_grain_spinner.setVisibility(View.GONE);
        }
    }

    public void populateHopsSpinner() {
        repoHops = new DBHelper_Hops(getActivity());

        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(getText(R.string.text_hops).toString());
        for (Hops item : repoHops.getHopsList()) {
            stringArrayList.add(item.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search_hops_spinner.setAdapter(adapter);
        if (stringArrayList.size() == 1) {
            search_hops_spinner.setVisibility(View.GONE);
        }
    }

    public void populateYeastSpinner() {
        repoYeast = new DBHelper_Yeast(getActivity());

        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(getText(R.string.text_yeast).toString());
        for (Yeast item : repoYeast.getYeastList()) {
            stringArrayList.add(item.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search_yeast_spinner.setAdapter(adapter);

        if (stringArrayList.size() == 1) {
            search_yeast_spinner.setVisibility(View.GONE);
        }
    }

    public void addListenerSpinner() {
        search_recipe_type_spinner.post(new Runnable() {
            @Override
            public void run() {
                search_recipe_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        repoRecipeType = new DBHelper_RecipeType(getActivity());
                        RecipeType recipeType = repoRecipeType.getRecipeTypeByDescription(parent.getItemAtPosition(pos).toString());
                        selectedType = 0;
                        if (recipeType != null) {
                            selectedType = recipeType.getRecipeTypePk();
                        }
                        getSearchResultsCount();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO maybe add something here later
                    }
                });
            }
        });

        search_style_spinner.post(new Runnable() {
            @Override
            public void run() {
                search_style_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        repoStyle = new DBHelper_Style(getActivity());
                        Style styleItem = repoStyle.getStyle(parent.getItemAtPosition(pos).toString());
                        selectedStyle = 0;
                        if (styleItem != null) {
                            selectedStyle = styleItem.getStylePk();
                        }
                        getSearchResultsCount();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO maybe add something here later
                    }
                });
            }
        });

        search_grain_spinner.post(new Runnable() {
            @Override
            public void run() {
                search_grain_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        repoGrain = new DBHelper_Grain(getActivity());
                        Grain grain = repoGrain.getGrainByName(parent.getItemAtPosition(pos).toString());
                        grainPk = 0;
                        if (grain != null) {
                            grainPk = grain.getGrainPk();
                        }
                        getSearchResultsCount();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO maybe add something here later
                    }
                });
            }
        });

        search_hops_spinner.post(new Runnable() {
            @Override
            public void run() {
                search_hops_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        repoHops = new DBHelper_Hops(getActivity());
                        Hops hops = repoHops.getHopsByName(parent.getItemAtPosition(pos).toString());
                        hopsPk = 0;
                        if (hops != null) {
                            hopsPk = hops.getHopsPk();
                        }

                        getSearchResultsCount();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO maybe add something here later
                    }
                });
            }
        });

        search_yeast_spinner.post(new Runnable() {
            @Override
            public void run() {
                search_yeast_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        repoYeast = new DBHelper_Yeast(getActivity());
                        Yeast yeast = repoYeast.getYeastByName(parent.getItemAtPosition(pos).toString());
                        yeastPk = 0;
                        if (yeast != null) {
                            yeastPk = yeast.getYeastPk();
                        }
                        getSearchResultsCount();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO maybe add something here later
                    }
                });
            }
        });
    }

    public void getSearchResultsCount() {
        try {
            String searchText = search_advanced_text_edit_text.getText().toString().trim();
            JsonObject json = new JsonObject();
            json.addProperty("searchText", searchText);
            String url = Constants.wcfGetSearchResultsCount + String.valueOf(selectedType) + "/" + String.valueOf(selectedStyle) + "/" + String.valueOf(abvProgress) + "/" + String.valueOf(ibuProgress) + "/" + String.valueOf(grainPk) + "/" + String.valueOf(hopsPk) + "/" + String.valueOf(yeastPk);
            Ion.with(getActivity().getApplicationContext())
                    .load(url)
                    .setHeader("Cache-Control", "No-Cache")
                    .setJsonObjectBody(json)
                    .asString()
                    .setCallback(new FutureCallback<String>() {

                        @Override
                        public void onCompleted(Exception e, String result) {
                            try {
                                int searchResults = 0;
                                if (result != null) {
                                    searchResults = Integer.parseInt(result);
//                                int searchResults = JsonToObject.GetSearchResultsCount(result);
                                }
                                search_expected_results_text_view.setText(getResources().getText(R.string.text_expected_results) + String.valueOf(searchResults));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_advanced_submit_button:

                if (BrewSharedPrefs.getIsUserLoggedIn()) {
                    Intent searchIntent = new Intent(getActivity().getApplicationContext(), SearchResultsActivity.class);
                    searchIntent.setAction(Intent.ACTION_SEARCH);
                    searchIntent.putExtra(Constants.exSearchValue, search_advanced_text_edit_text.getText().toString().trim());
                    searchIntent.putExtra(Constants.exRecipeTypeValue, selectedType);
                    searchIntent.putExtra(Constants.exStyleValue, selectedStyle);
                    searchIntent.putExtra(Constants.exAbvValue, abvProgress);
                    searchIntent.putExtra(Constants.exIbuValue, ibuProgress);
                    searchIntent.putExtra(Constants.exGrainPk, grainPk);
                    searchIntent.putExtra(Constants.exHopsPk, hopsPk);
                    searchIntent.putExtra(Constants.exYeastPk, yeastPk);
                    searchIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

//                    startActivity(searchIntent);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                        ActivityCompat.startActivity(getActivity(), searchIntent, options.toBundle());
                    } else {
                        startActivity(searchIntent);
                        getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                    }

                } else {
                    AlertLoginPrompt(_fContext, getText(R.string.text_login_to_use_adv_search).toString(), getText(R.string.text_sign_in).toString(), getText(R.string.text_sign_up).toString(), getText(R.string.text_close).toString());
                }
                break;
        }
    }
}

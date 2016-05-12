package brightseer.com.brewhaha.fragment;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.adapter.GrainMyRecipeRecycler;
import brightseer.com.brewhaha.helper.RecyclerItemClickListener;
import brightseer.com.brewhaha.objects.Country;
import brightseer.com.brewhaha.objects.Grain;
import brightseer.com.brewhaha.objects.GrainUse;
import brightseer.com.brewhaha.models.RecipeGrain;
import brightseer.com.brewhaha.objects.SrmColorKey;
import brightseer.com.brewhaha.objects.UnitOfMeasure;
import brightseer.com.brewhaha.repository.DBHelper_Country;
import brightseer.com.brewhaha.repository.DBHelper_Grain;
import brightseer.com.brewhaha.repository.DBHelper_GrainUse;
import brightseer.com.brewhaha.repository.DBHelper_SrmColorKey;
import brightseer.com.brewhaha.repository.DBHelper_UnitOfMeasure;
import brightseer.com.brewhaha.repository.JsonToObject;

/**
 * Created by Michael McCulloch on 2/26/2015.
 */
public class AddGrainsFragment extends BaseFragment implements View.OnClickListener {
    private View rootView, addGrainDialogView;
    private AlertDialog addDialog;
    private GrainMyRecipeRecycler adapter;
    private List<GrainUse> grainUse = new Vector<>();
    private List<Grain> grainList = new Vector<>();
    private Spinner my_grain_master_spinner, my_grain_measurement_size_spinner, my_grain_use_spinner, my_grain_country_spinner;
    private DBHelper_Grain repoGrain;
    private EditText my_grain_name_edit_text, my_grain_amount_edit_text;
    private List<UnitOfMeasure> unitOfMeasures = new Vector<>();
    private List<Country> counties = new Vector<>();
    private int ingredientGrainPk = 0, selectedGrainPk = 0, selectedCountyPk = 0, selectedGrainUsePk = 0, selectedUnitOfMeasurePk = 0, selectedSrm = 0;
    private int colorPickerProgress = 0, listPosition = 0;
    private android.support.v7.widget.AppCompatButton my_grain_submit_button;
    private JsonObject json = new JsonObject();
    private SeekBar my_grain_color_seekbar;
    private TextView my_recipe_color_value_text_view;
    private LinearLayout srm_color_linear_layout;
    private android.support.design.widget.FloatingActionButton grain_fab;
//    public Future<JsonArray> ionLoadGrains, ionLoadGrainMeasurements, ionLoadGrainUse, ionLoadCountry, ionLoadSrmColors, ionUpdateGrain;
//    public Future<String> ionDeleteGrain;
    private String mHeader = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _fContext = getActivity();
        initGoogleAnalytics(this.getClass().getSimpleName());
        Bundle bundle = this.getArguments();
        contentItemPk = bundle.getString(Constants.exContentItemPk);
        contentToken = bundle.getString(Constants.spContentToken);
//        LoadDialog(_fContext, false, true);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grains, container, false);
        initRecycler();
        createCustomDialog();
        initViews();
        populateSrmColorKeys();

        load();
        return rootView;
    }

    public void load() {
        String url = Constants.wcfGetRecipeGrain + contentToken;
//        ionLoadGrains = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                                 @Override
//                                 public void onCompleted(Exception e, JsonArray result) {
//                                     try {
//                                         List<RecipeGrain> recipeGrain = JsonToObject.JsonToRecipeGrainList(result);
//                                         adapter.addItemsToAdapter(recipeGrain);
////                                         for (RecipeGrain item : recipeGrain) {
//////                                             ingredientGrainList.add(item);
////                                             adapter.add(item, ingredientGrainList.size() - 1);
////                                         }
//
////                                         dialogProgress.dismiss();
//                                         addFabListener();
//                                     } catch (Exception ex) {
//                                         if (BuildConfig.DEBUG) {
//                                             Log.e(Constants.LOG, ex.getMessage());
//                                         }
////                                         dialogProgress.dismiss();
//                                     }
//                                 }
//                             }
//
//                );
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (ionLoadGrains != null) {
//            ionLoadGrains.cancel();
//        }
//        if (ionLoadGrainMeasurements != null) {
//            ionLoadGrainMeasurements.cancel();
//        }
//        if (ionLoadGrainUse != null) {
//            ionLoadGrainUse.cancel();
//        }
//        if (ionLoadCountry != null) {
//            ionLoadCountry.cancel();
//        }
//        if (ionLoadSrmColors != null) {
//            ionLoadSrmColors.cancel();
//        }
//        if (ionUpdateGrain != null) {
//            ionUpdateGrain.cancel();
//        }
//        if (ionDeleteGrain != null) {
//            ionDeleteGrain.cancel();
//        }
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
////            case R.id.my_grain_submit_button:
////                break;
//
//            case R.id.my_grain_add_new_button:
//                resetDialogValues();
//                addDialog.show();
//                break;
//        }
    }

    public void initViews() {
        my_grain_master_spinner = (Spinner) addGrainDialogView.findViewById(R.id.my_grain_master_spinner);
        populateGrainSpinner();
        my_grain_measurement_size_spinner = (Spinner) addGrainDialogView.findViewById(R.id.my_grain_measurement_size_spinner);
        populateMeasurementSpinner();
        my_grain_use_spinner = (Spinner) addGrainDialogView.findViewById(R.id.my_grain_use_spinner);
        populateGrainUseSpinner();
        my_grain_country_spinner = (Spinner) addGrainDialogView.findViewById(R.id.my_grain_country_spinner);
        populateCountrySpinner();

        spinnerListener();

        my_grain_name_edit_text = (EditText) addGrainDialogView.findViewById(R.id.my_grain_name_edit_text);
        my_grain_amount_edit_text = (EditText) addGrainDialogView.findViewById(R.id.my_grain_amount_edit_text);

//        my_grain_add_new_button = (ImageButton) rootView.findViewById(R.id.my_grain_add_new_button);
//        my_grain_add_new_button.setOnClickListener(this);

        my_grain_submit_button = (android.support.v7.widget.AppCompatButton) addGrainDialogView.findViewById(R.id.my_grain_submit_button);
//        my_grain_add_new_button.setOnClickListener(this);

        my_grain_color_seekbar = (SeekBar) addGrainDialogView.findViewById(R.id.my_grain_color_seekbar);
        seekBarListener();

        my_recipe_color_value_text_view = (TextView) addGrainDialogView.findViewById(R.id.my_recipe_color_value_text_view);
        srm_color_linear_layout = (LinearLayout) addGrainDialogView.findViewById(R.id.srm_color_linear_layout);
        grain_fab = (android.support.design.widget.FloatingActionButton) rootView.findViewById(R.id.grain_fab);
//        grain_fab.setColor(getResources().getColor(R.color.app_orange));
        grain_fab.setVisibility(View.VISIBLE);

    }

    public void initRecycler() {
        int screenOrientation = getResources().getConfiguration().orientation;
        RecyclerView my_recipe_grains_recycle_view = (RecyclerView) rootView.findViewById(R.id.my_recipe_grains_recycle_view);
        my_recipe_grains_recycle_view.setHasFixedSize(true);


        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);
            my_recipe_grains_recycle_view.setLayoutManager(layoutManager);
            my_recipe_grains_recycle_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
        }
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);
            my_recipe_grains_recycle_view.setLayoutManager(layoutManager);
        }
        List<RecipeGrain> placeHolder = new Vector<>();
        adapter = new GrainMyRecipeRecycler(placeHolder, AddGrainsFragment.this);

        my_recipe_grains_recycle_view.setAdapter(adapter);
        my_recipe_grains_recycle_view.setItemAnimator(new DefaultItemAnimator());


        my_recipe_grains_recycle_view.addOnItemTouchListener(
                new RecyclerItemClickListener(_fContext, my_recipe_grains_recycle_view, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
//                            RecipeGrain recipeGrain = adapter.getItemAt(position);
//
//                            mHeader = recipeGrain.getAmount() + " " + lookupUnitOfMeasure(recipeGrain.getUnitOfMeasureId(), 1).getDescription() + ", " + lookupCountry(recipeGrain.getCountryId()).getAbbreviation() + " " + recipeGrain.getName();
//
//                            ingredientGrainPk = recipeGrain.getIngredientGrainId();
//                            listPosition = position;
//                            registerForContextMenu(view);
//                            getActivity().openContextMenu(view);
//                            view.setLongClickable(false);
                        } catch (Exception e) {
                            if (BuildConfig.DEBUG) {
                                Log.e(Constants.LOG, e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                        ingredientGrainPk = ingredientGrainList.get(position).getIngredientGrainId();
//                        listPosition = position;
//                        registerForContextMenu(view);
//                        getActivity().openContextMenu(view);
//                        view.setLongClickable(false);
                    }
                })
        );
    }

    public void populateGrainSpinner() {
        repoGrain = new DBHelper_Grain(_fContext);
        grainList = repoGrain.getGrainList();
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(getText(R.string.text_grain_add).toString());
        for (Grain item : grainList) {
            stringArrayList.add(item.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        my_grain_master_spinner.setAdapter(adapter);
    }

    public void populateMeasurementSpinner() {
        try {
            DBHelper_UnitOfMeasure repoUnitOfMeasure = new DBHelper_UnitOfMeasure(_fContext);
            unitOfMeasures = repoUnitOfMeasure.getUnitOfMeasureTypeList(1);
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (UnitOfMeasure item : unitOfMeasures) {
                if (item.getType() == 1 && item.getUnitOfMeasurePk() != 0) {
                    stringArrayList.add(item.getDescription());
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            my_grain_measurement_size_spinner.setAdapter(adapter);

        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void populateGrainUseSpinner() {
        try {
            DBHelper_GrainUse repoGrainUse = DBHelper_GrainUse.getInstance(_fContext);
            grainUse = repoGrainUse.getGrainUseList();
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (GrainUse item : grainUse) {
                if (item.getGrainUsePk() != 0) {
                    stringArrayList.add(item.getDescription());
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            my_grain_use_spinner.setAdapter(adapter);

        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void populateCountrySpinner() {
        try {
            DBHelper_Country repoCountry = DBHelper_Country.getInstance(_fContext);
            counties = repoCountry.getCountryList();
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (Country item : counties) {
                if (item.getCountryPk() != 0) {
                    stringArrayList.add(item.getAbbreviation());
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            my_grain_country_spinner.setAdapter(adapter);

        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void populateSrmColorKeys() {
        try {
            DBHelper_SrmColorKey repoSrmColorKey = DBHelper_SrmColorKey.getInstance(_fContext);
            SrmColorKeyList = repoSrmColorKey.getSrmColorKeyList();
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void spinnerListener() {
        my_grain_master_spinner.post(new Runnable() {
            @Override
            public void run() {
                my_grain_master_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        List<Grain> grainList = repoGrain.getGrainList();
                        selectedGrainPk = 0;
                        if (pos > 0) {
                            Grain grain = grainList.get(pos - 1);

                            if (my_grain_master_spinner.isEnabled()) {
                                populateForm(grain);
                            }

                            selectedGrainPk = grain.getGrainPk();
                        }
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO maybe add something here later
                    }
                });
            }
        });

        my_grain_measurement_size_spinner.post(new Runnable() {
            @Override
            public void run() {
                my_grain_measurement_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        selectedUnitOfMeasurePk = unitOfMeasures.get(pos).getUnitOfMeasurePk();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO maybe add something here later
                    }
                });
            }
        });

        my_grain_use_spinner.post(new Runnable() {
            @Override
            public void run() {
                my_grain_use_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        selectedGrainUsePk = grainUse.get(pos + 1).getGrainUsePk();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO maybe add something here later
                    }
                });
            }
        });

        my_grain_country_spinner.post(new Runnable() {
            @Override
            public void run() {
                my_grain_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        selectedCountyPk = counties.get(pos + 1).getCountryPk();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO maybe add something here later
                    }
                });
            }
        });
    }

    public void seekBarListener() {
        my_grain_color_seekbar.post(new Runnable() {
            @Override
            public void run() {
                my_grain_color_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                        colorPickerProgress = progresValue;
                        my_recipe_color_value_text_view.setText(String.valueOf(colorPickerProgress));

                        srm_color_linear_layout.setBackgroundColor(Color.parseColor(GetSelectedSrmColor(colorPickerProgress)));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        selectedSrm = colorPickerProgress;
                    }
                });
            }
        });
    }

    public void populateForm(Grain grain) {
        my_grain_name_edit_text.setText(grain.getName());
        my_grain_measurement_size_spinner.setSelection(0);
    }

    public void createCustomDialog() {
        LayoutInflater factory = LayoutInflater.from(_fContext);
        addGrainDialogView = factory.inflate(
                R.layout.dialog_add_grain, null);
        addDialog = new AlertDialog.Builder(_fContext).create();
        addDialog.setView(addGrainDialogView);

        addGrainDialogView.findViewById(R.id.my_grain_submit_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //your business logic
//                addDialog.dismiss();
                if (validateGrainEntry()) {
                    addUpdateGrain();
                }
            }
        });
        addGrainDialogView.findViewById(R.id.my_grain_cancel_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addDialog.dismiss();
            }
        });
    }

    public void addUpdateGrain() {
        String url = Constants.wcfAddUpdateIngredientGrain + contentItemPk + "/" + ingredientGrainPk + "/" + selectedGrainPk + "/" + selectedGrainUsePk + "/" + selectedCountyPk + "/" + selectedUnitOfMeasurePk + "/" + selectedSrm;
        json = new JsonObject();
        json.addProperty("amount", my_grain_amount_edit_text.getText().toString().trim());
        json.addProperty("grainName", my_grain_name_edit_text.getText().toString().trim());

//        ionUpdateGrain = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .setJsonObjectBody(json)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                                 @Override
//                                 public void onCompleted(Exception e, JsonArray result) {
//                                     try {
//                                         addDialog.dismiss();
//                                         List<RecipeGrain> recipeGrain = JsonToObject.JsonToRecipeGrainList(result);
//                                         RecipeGrain item = recipeGrain.get(0);
//                                         int pos = 0;
//
////                                         if (ingredientGrainPk != 0) {
////                                             pos = adapter.getPostionByPk(item.getIngredientGrainId());
////                                             adapter.remove(pos);
////                                         } else {
////                                             pos = adapter.getItemCount();
////                                         }
//
//                                         adapter.add(item, pos);
//                                     } catch (Exception ex) {
//                                         if (BuildConfig.DEBUG) {
//                                             Log.e(Constants.LOG, ex.getMessage());
//                                         }
//                                     }
//                                 }
//                             }
//
//                );

    }

    public UnitOfMeasure lookupUnitOfMeasure(int itemPk, int type) {
        for (UnitOfMeasure item : unitOfMeasures) {
            if (item.getUnitOfMeasurePk() == itemPk && item.getType() == type) {
                return item;
            }
        }
        return null;
    }

    public Grain lookupGrain(int itemPk) {
        for (Grain item : grainList) {
            if (item.getGrainPk() == itemPk) {
                return item;
            }
        }
        return null;
    }

    public GrainUse lookupGrainUse(int itemPk) {
        for (GrainUse item : grainUse) {
            if (item.getGrainUsePk() == itemPk) {
                return item;
            }
        }
        return null;
    }

    public Country lookupCountry(int itemPk) {
        for (Country item : counties) {
            if (item.getCountryPk() == itemPk) {
                return item;
            }
        }
        return null;
    }

    public String GetSelectedSrmColor(int itemVal) {
        if (itemVal == 0) {
            return "#fee799";
        }
        for (SrmColorKey item : SrmColorKeyList) {
            if (item.getColorSrm() == itemVal) {
                return item.getHexColor();
            }
        }
        return String.valueOf(getResources().getColor(R.color.srm_default));
    }

    private void resetDialogValues() {
        my_grain_master_spinner.setEnabled(true);
        ingredientGrainPk = 0;
        selectedGrainPk = 0;
        selectedCountyPk = 0;
        selectedGrainUsePk = 0;
        selectedUnitOfMeasurePk = 0;
        my_grain_master_spinner.setSelection(0);
        my_grain_country_spinner.setSelection(0);
        my_grain_use_spinner.setSelection(0);
        my_grain_measurement_size_spinner.setSelection(0);
        my_grain_amount_edit_text.setText("");
        my_grain_name_edit_text.setText("");
        my_grain_submit_button.setText("Add");
        selectedSrm = 0;
        listPosition = 0;
        my_grain_name_edit_text.setHintTextColor(getResources().getColor(R.color.app_gray));
        my_grain_amount_edit_text.setHintTextColor(getResources().getColor(R.color.app_gray));
        my_grain_color_seekbar.setProgress(0);
    }

    public void setDialogValues(RecipeGrain selected) {
//        if (selected != null) {
//            ingredientGrainPk = selected.getIngredientGrainId();
//            selectedGrainPk = selected.getGrainId();
//            my_grain_amount_edit_text.setText(String.valueOf(selected.getAmount()));
//            my_grain_name_edit_text.setText(String.valueOf(selected.getName()));
//
//            my_grain_master_spinner.setSelection(grainList.indexOf(lookupGrain(selected.getGrainId())) + 1);
//            my_grain_master_spinner.setEnabled(false);
//
//            my_grain_country_spinner.setSelection(counties.indexOf(lookupCountry(selected.getCountryId())) - 1);
//
//            my_grain_use_spinner.setSelection(grainUse.indexOf(lookupGrainUse(selected.getGrainUseId())) - 1);
//            my_grain_measurement_size_spinner.setSelection(unitOfMeasures.indexOf(lookupUnitOfMeasure(selected.getUnitOfMeasureId(), 1)) - 1);
//
//            my_grain_submit_button.setText("Update");
//
//            my_grain_color_seekbar.setProgress((int) selected.getColor());
//            selectedSrm = (int) selected.getColor();
//        }
    }

    private boolean validateGrainEntry() {
        try {
            boolean valid = true;
            if (TextUtils.isEmpty(my_grain_name_edit_text.getText())) {
                my_grain_name_edit_text.setHintTextColor(getResources().getColor(R.color.red));
                Toast.makeText(_fContext, "Grain Name is required", Toast.LENGTH_SHORT).show();
                valid = false;
            }

            if (TextUtils.isEmpty(my_grain_amount_edit_text.getText())) {
                my_grain_amount_edit_text.setHintTextColor(getResources().getColor(R.color.red));
                Toast.makeText(_fContext, "Amount is required", Toast.LENGTH_SHORT).show();
                valid = false;
            }
            return valid;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        contextMenu = menu;
        menu.setHeaderTitle(mHeader);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_grain_modify, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_button_delete:
                if (adapter.getItemCount() > 1) {
                    DeleteItem();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Sorry! You must have at least 1 grain",
                            Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.menu_button_edit:
                setDialogValues(adapter.getItemAt(listPosition));
                addDialog.show();
                break;

            case R.id.menu_button_copy:
                makeCopyGrain();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void DeleteItem() {
        String url = Constants.wcfRemoveIngredientGrain + contentItemPk + "/" + ingredientGrainPk + "/false";
//        ionDeleteGrain = Ion.with(_fContext.getApplicationContext())
//                .load(url)
//                .addHeader("Content-Type", "application/json")
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String s) {
//                        adapter.remove(listPosition);
//                        resetDialogValues();
//                        Snackbar.make(rootView.findViewById(R.id.coordinatorLayout), mHeader + " Deleted", Snackbar.LENGTH_SHORT)
//                                .show();
//                    }
//                });
    }

    public void addFabListener() {
        try {
            grain_fab.setVisibility(View.VISIBLE);
//            grain_fab.initBackground();
//            grain_fab.setBackgroundResource(R.drawable.bottlecap_small);
//            grain_fab.setImageResource(R.mipmap.ic_add_black_24dp);
            grain_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetDialogValues();
                    addDialog.show();
                }
            });
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public void makeCopyGrain() {
        String url = Constants.wcfCopyGrain + ingredientGrainPk + "/" + contentToken;
//        ionUpdateGrain = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .setJsonObjectBody(json)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                                 @Override
//                                 public void onCompleted(Exception e, JsonArray result) {
//                                     try {
//                                         List<RecipeGrain> recipeGrain = JsonToObject.JsonToRecipeGrainList(result);
//                                         for (RecipeGrain item : recipeGrain) {
//                                             adapter.add(item);
//                                         }
//                                     } catch (Exception ex) {
//                                         if (BuildConfig.DEBUG) {
//                                             Log.e(Constants.LOG, ex.getMessage());
//                                         }
//                                     }
//                                 }
//                             }
//                );

    }
}

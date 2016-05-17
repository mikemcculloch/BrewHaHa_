package brightseer.com.brewhaha.fragment;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.graphics.Rect;
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
import android.view.Window;
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

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.adapter.HopMyRecipeRecycler;
import brightseer.com.brewhaha.helper.RecyclerItemClickListener;
import brightseer.com.brewhaha.objects.Hops;
import brightseer.com.brewhaha.objects.HopsForm;
import brightseer.com.brewhaha.objects.HopsUse;
import brightseer.com.brewhaha.models.RecipeHop;
import brightseer.com.brewhaha.objects.UnitOfMeasure;
import brightseer.com.brewhaha.repository.DBHelper_HopUse;
import brightseer.com.brewhaha.repository.DBHelper_Hops;
import brightseer.com.brewhaha.repository.DBHelper_HopsForm;
import brightseer.com.brewhaha.repository.DBHelper_UnitOfMeasure;
import brightseer.com.brewhaha.repository.JsonToObject;

/**
 * Created by Michael McCulloch on 3/22/2015.
 */
public class AddHopsFragment extends BaseFragment implements View.OnClickListener {
    private View rootView, addHopDialogView;
    private HopMyRecipeRecycler adapter;
    private Spinner my_hops_master_spinner, my_hops_measurement_size_spinner, my_hop_use_spinner, my_hops_time_unit_size_spinner, my_hop_form_spinner;
    private List<Hops> hopsMasterList = new Vector<>();
    private AlertDialog addHopDialog;
    private JsonObject json = new JsonObject();
    private List<HopsUse> hopUseList = new Vector<>();
    private List<HopsForm> hopsFormList = new Vector<>();
    private List<UnitOfMeasure> unitOfMeasures = new Vector<>();
    private List<UnitOfMeasure> unitOfWeight = new Vector<>();
    private List<UnitOfMeasure> unitOfTime = new Vector<>();
    private int ingredientHopPk = 0, selectedHopPk = 0, selectedMeasurement = 1, selectedHopsUsePk = 2, selectedTimeUnit = 4, selectedHopFormPk = 1;
    private int listPosition;
    private EditText my_hops_name_edit_text, my_hop_amount_edit_text, my_hop_time_edit_text;
    private android.support.v7.widget.AppCompatButton my_hop_submit_button;
    private android.support.design.widget.FloatingActionButton hop_fab;
//    public Future<JsonArray> ionLoadHops, ionLoadMeasurements, ionGetHopUse, ionLoadHopForm, ionUpdateHop;
//    public Future<String> ionDeleteHop;
    public SeekBar my_hop_alpha_acid_seekbar;
    private float acidProgress = 0;
    private TextView alph_acid_text_view;
    private android.support.v7.widget.SwitchCompat checkbox_add_instruction;
    private LinearLayout layout_auto_add;

    DBHelper_HopUse repoHopsUse;
    DBHelper_HopsForm repoHopsForm;

    private String mHeader = "";

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
        rootView = inflater.inflate(R.layout.fragment_hops, container, false);
        initRecycler();
        createHopsAddUpdateDialog();
        initViews();
        getMeasurements();
        spinnerListener();

        load();
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (ionLoadHops != null) {
//            ionLoadHops.cancel();
//        }
//        if (ionLoadMeasurements != null) {
//            ionLoadMeasurements.cancel();
//        }
//        if (ionGetHopUse != null) {
//            ionGetHopUse.cancel();
//        }
//        if (ionLoadHopForm != null) {
//            ionLoadHopForm.cancel();
//        }
//        if (ionUpdateHop != null) {
//            ionUpdateHop.cancel();
//        }
//        if (ionDeleteHop != null) {
//            ionDeleteHop.cancel();
//        }
    }

    @Override
    public void onClick(View v) {

    }

    public void load() {
//        String url = Constants.wcfGetRecipeHops + contentToken;
//        ionLoadHops = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                                 @Override
//                                 public void onCompleted(Exception e, JsonArray result) {
//                                     try {
//                                         List<RecipeHop> ingredient = JsonToObject.JsonToIngredientHopList(result);
//                                         adapter.addItemsToAdapter(ingredient);
//                                         addFabListener();
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

    public void initViews() {
        my_hops_master_spinner = (Spinner) addHopDialogView.findViewById(R.id.my_hops_master_spinner);
        populateHopMasterSpinner();

        my_hops_measurement_size_spinner = (Spinner) addHopDialogView.findViewById(R.id.my_hops_measurement_size_spinner);

        my_hop_use_spinner = (Spinner) addHopDialogView.findViewById(R.id.my_hop_use_spinner);
        populateHopUseSpinner();
        my_hops_time_unit_size_spinner = (Spinner) addHopDialogView.findViewById(R.id.my_hops_time_unit_size_spinner);
//        populateTimeUnitSpinner();
        my_hop_form_spinner = (Spinner) addHopDialogView.findViewById(R.id.my_hop_form_spinner);
        populateHopFormSpinner();

        my_hops_name_edit_text = (EditText) addHopDialogView.findViewById(R.id.my_hops_name_edit_text);
        my_hop_amount_edit_text = (EditText) addHopDialogView.findViewById(R.id.my_hop_amount_edit_text);
        my_hop_time_edit_text = (EditText) addHopDialogView.findViewById(R.id.my_hop_time_edit_text);

        my_hop_submit_button = (android.support.v7.widget.AppCompatButton) addHopDialogView.findViewById(R.id.my_hop_submit_button);

        alph_acid_text_view = (TextView) addHopDialogView.findViewById(R.id.alph_acid_text_view);

        my_hop_alpha_acid_seekbar = (SeekBar) addHopDialogView.findViewById(R.id.my_hop_alpha_acid_seekbar);
        my_hop_alpha_acid_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                acidProgress = (float) progresValue / 10;
                alph_acid_text_view.setText("Alph Acid " + String.valueOf(acidProgress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        hop_fab = (android.support.design.widget.FloatingActionButton) rootView.findViewById(R.id.hop_fab);
        hop_fab.setVisibility(View.VISIBLE);

        checkbox_add_instruction = (android.support.v7.widget.SwitchCompat) addHopDialogView.findViewById(R.id.checkbox_add_instruction);

        layout_auto_add = (LinearLayout) addHopDialogView.findViewById(R.id.layout_auto_add);
    }

    public void initRecycler() {
        int screenOrientation = getResources().getConfiguration().orientation;
        RecyclerView my_hops_recycle_view = (RecyclerView) rootView.findViewById(R.id.my_hops_recycle_view);
        my_hops_recycle_view.setHasFixedSize(true);

        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);
            my_hops_recycle_view.setLayoutManager(layoutManager);
            my_hops_recycle_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
        }
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

            layoutManager.scrollToPosition(0);
            my_hops_recycle_view.setLayoutManager(layoutManager);
        }
        List<RecipeHop> placeHolder = new Vector<>();
        adapter = new HopMyRecipeRecycler(placeHolder, AddHopsFragment.this);

        my_hops_recycle_view.setAdapter(adapter);
        my_hops_recycle_view.setItemAnimator(new DefaultItemAnimator());

        my_hops_recycle_view.addOnItemTouchListener(
                new RecyclerItemClickListener(_fContext, my_hops_recycle_view, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            RecipeHop recipeHop = adapter.getItemAt(position);
//                            mHeader = recipeHop.getAmount() + " " + lookupUnitOfMeasure(recipeHop.getUnitOfMeasureId(), 1).getDescription() + ", " + recipeHop.getName();
//                            ingredientHopPk = recipeHop.getIngredientHopsId();
//                            listPosition = position;

                            registerForContextMenu(view);
                            getActivity().openContextMenu(view);
                            view.setLongClickable(false);
                        } catch (Exception e) {
                            if (BuildConfig.DEBUG) {
                                Log.e(Constants.LOG, e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                        ingredientHopPk = ingredientList.get(position).getIngredientHopsId();
//                        listPosition = position;
//                        registerForContextMenu(view);
//                        getActivity().openContextMenu(view);
//                        view.setLongClickable(false);
                    }
                })
        );
    }

    public void getMeasurements() {
        try {
            DBHelper_UnitOfMeasure repoUnitOfMeasure = DBHelper_UnitOfMeasure.getInstance(_fContext);
            unitOfMeasures = repoUnitOfMeasure.getUnitOfMeasureList();

            unitOfWeight = repoUnitOfMeasure.getUnitOfMeasureTypeList(1);
            unitOfTime = repoUnitOfMeasure.getUnitOfMeasureTypeList(2);

            populateHopMeasurementSpinner();
            populateTimeUnitSpinner();
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void populateHopMasterSpinner() {
        DBHelper_Hops repoHops = new DBHelper_Hops(_fContext);
        hopsMasterList = repoHops.getHopsList();
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(getText(R.string.text_hops_spinner_default).toString());
        for (Hops item : hopsMasterList) {
            stringArrayList.add(item.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        my_hops_master_spinner.setAdapter(adapter);
    }

    public void populateHopMeasurementSpinner() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (UnitOfMeasure item : unitOfWeight) {
            if (item.getType() == 1 && item.getUnitOfMeasurePk() != 0) {
                stringArrayList.add(item.getDescription());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        my_hops_measurement_size_spinner.setAdapter(adapter);
    }

    public void populateHopUseSpinner() {
        try {
            repoHopsUse = new DBHelper_HopUse(_fContext);
            hopUseList = repoHopsUse.getHopsUseList();
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (HopsUse item : hopUseList) {
                if (item.getHopsUsePk() != 0) {
                    stringArrayList.add(item.getDescription());
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            my_hop_use_spinner.setAdapter(adapter);

        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void populateHopFormSpinner() {
        try {
            repoHopsForm = DBHelper_HopsForm.getInstance(_fContext);
            hopsFormList = repoHopsForm.getHopsFormList();
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (HopsForm item : hopsFormList) {
                if (item.getHopsFormPk() != 0) {
                    stringArrayList.add(item.getDescription());
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            my_hop_form_spinner.setAdapter(adapter);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void populateTimeUnitSpinner() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (UnitOfMeasure item : unitOfTime) {
            if (item.getType() == 2 && item.getUnitOfMeasurePk() != 0) {
                stringArrayList.add(item.getDescription());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        my_hops_time_unit_size_spinner.setAdapter(adapter);
    }

    public void spinnerListener() {
        my_hops_master_spinner.post(new Runnable() {
            @Override
            public void run() {
                my_hops_master_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        selectedHopPk = 0;
                        if (pos > 0) {
                            Hops ingredient = hopsMasterList.get(pos - 1);

                            if (my_hops_master_spinner.isEnabled()) {
                                populateForm(ingredient);
                            }

                            selectedHopPk = ingredient.getHopsPk();

                            acidProgress = Float.parseFloat(String.valueOf(ingredient.getAlphaAcidPercentage())) * 10;
                            my_hop_alpha_acid_seekbar.setProgress((int) acidProgress);
                        }
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }
        });

        my_hops_measurement_size_spinner.post(new Runnable() {
            @Override
            public void run() {
                my_hops_measurement_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        selectedMeasurement = unitOfWeight.get(pos).getUnitOfMeasurePk();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }
        });

        my_hop_use_spinner.post(new Runnable() {
            @Override
            public void run() {
                my_hop_use_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        selectedHopsUsePk = hopUseList.get(pos + 1).getHopsUsePk();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }
        });

        my_hops_time_unit_size_spinner.post(new Runnable() {
            @Override
            public void run() {
                my_hops_time_unit_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        selectedTimeUnit = unitOfTime.get(pos).getUnitOfMeasurePk();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }
        });

        my_hop_form_spinner.post(new Runnable() {
            @Override
            public void run() {
                my_hop_form_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        selectedHopFormPk = hopsFormList.get(pos + 1).getHopsFormPk();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }
        });

    }

    public void populateForm(Hops hop) {
        my_hops_name_edit_text.setText(hop.getName());
    }

    public void createHopsAddUpdateDialog() {
        try {

            Rect displayRectangle = new Rect();
            Window window = getActivity().getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);


            LayoutInflater factory = LayoutInflater.from(_fContext);
            addHopDialogView = factory.inflate(
                    R.layout.dialog_add_hop, null);

//            addHopDialogView.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
//            addHopDialogView.setMinimumHeight((int) (displayRectangle.height() * 0.9f));

            addHopDialog = new AlertDialog.Builder(_fContext).create();
            addHopDialog.setView(addHopDialogView);

            addHopDialogView.findViewById(R.id.my_hop_submit_button).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (validateHopEntry()) {
//                        addUpdateHop();
                    }
                }
            });
            addHopDialogView.findViewById(R.id.my_hop_cancel_button).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    addHopDialog.dismiss();
                }
            });
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }

    }

    private void resetDialogValues() {
        my_hops_master_spinner.setEnabled(true);
        selectedHopPk = 0;
        selectedMeasurement = 1;
        selectedHopsUsePk = 2;
        selectedTimeUnit = 4;
        selectedHopFormPk = 1;
        ingredientHopPk = 0;
        my_hops_master_spinner.setSelection(0);
        my_hops_measurement_size_spinner.setSelection(1);
        my_hop_use_spinner.setSelection(0);
        my_hops_time_unit_size_spinner.setSelection(1);
        my_hop_form_spinner.setSelection(0);
        listPosition = 0;
        my_hops_name_edit_text.setText("");
        my_hop_amount_edit_text.setText("");
        my_hop_time_edit_text.setText("");
        my_hop_submit_button.setText("Add Hop");

        my_hop_alpha_acid_seekbar.setProgress(0);

        my_hops_name_edit_text.setHintTextColor(getResources().getColor(R.color.app_gray));
        my_hop_time_edit_text.setHintTextColor(getResources().getColor(R.color.app_gray));
        my_hop_amount_edit_text.setHintTextColor(getResources().getColor(R.color.app_gray));

        if (checkbox_add_instruction.getVisibility() == View.VISIBLE) {
            checkbox_add_instruction.setChecked(true);
        }
    }

    public void setDialogValues(RecipeHop selected) {
        if (selected != null) {
            my_hops_master_spinner.setEnabled(false);
//            selectedHopPk = selected.getHopsId();
//            selectedMeasurement = selected.getUnitOfMeasureId();
//            selectedHopsUsePk = selected.getHopsUseId();
//            selectedTimeUnit = selected.getTimeUnitOfMeasureId();
//            selectedHopFormPk = selected.getHopsFormId();
//            ingredientHopPk = selected.getIngredientHopsId();

            my_hops_name_edit_text.setText(String.valueOf(selected.getName()));
            my_hop_amount_edit_text.setText(String.valueOf(selected.getAmount()));
            my_hop_time_edit_text.setText(String.valueOf(selected.getCookTime()));
            my_hop_submit_button.setText("Update");

            acidProgress = Float.parseFloat(String.valueOf(selected.getAlphaAcidPercentage())) * 10;
            my_hop_alpha_acid_seekbar.setProgress((int) acidProgress);

//            my_hops_measurement_size_spinner.setSelection(unitOfWeight.indexOf(lookupUnitOfMeasure(selected.getUnitOfMeasureId(), 1)) - 1);
//            my_hop_use_spinner.setSelection(hopUseList.indexOf(lookupHopUse(selected.getHopsUseId())) - 1);
//            my_hops_time_unit_size_spinner.setSelection(unitOfTime.indexOf(lookupUnitOfMeasure(selected.getTimeUnitOfMeasureId(), 2)) - 1);
//            my_hop_form_spinner.setSelection(hopsFormList.indexOf(lookupHopForm(selected.getHopsFormId())) - 1);
        }
    }

    public UnitOfMeasure lookupUnitOfMeasure(int itemPk, int type) {
        for (UnitOfMeasure item : unitOfMeasures) {
            if (item.getUnitOfMeasurePk() == itemPk) {
                return item;
            }
        }

        return null;
    }

    public HopsUse lookupHopUse(int itemPk) {
        for (HopsUse item : hopUseList) {
            if (item.getHopsUsePk() == itemPk) {
                return item;
            }
        }
        return null;
    }

    public HopsForm lookupHopForm(int itemPk) {
        for (HopsForm item : hopsFormList) {
            if (item.getHopsFormPk() == itemPk) {
                return item;
            }
        }
        return null;
    }

    private boolean validateHopEntry() {
        try {
            boolean valid = true;
            if (TextUtils.isEmpty(my_hops_name_edit_text.getText())) {
                my_hops_name_edit_text.setHintTextColor(getResources().getColor(R.color.red));
                Toast.makeText(_fContext, "Name is required", Toast.LENGTH_SHORT).show();
                valid = false;
            }

            if (TextUtils.isEmpty(my_hop_time_edit_text.getText())) {
                my_hop_time_edit_text.setHintTextColor(getResources().getColor(R.color.red));
                Toast.makeText(_fContext, "Cook Time is required", Toast.LENGTH_SHORT).show();
                valid = false;
            }

            if (TextUtils.isEmpty(my_hop_amount_edit_text.getText())) {
                my_hop_amount_edit_text.setHintTextColor(getResources().getColor(R.color.red));
                Toast.makeText(_fContext, "Amount is required", Toast.LENGTH_SHORT).show();
                valid = false;
            }
            return valid;
        } catch (Exception ex) {
            return false;
        }
    }

//    public void addUpdateHop() {
//
//        int addInstruction = 0;
//        if (ingredientHopPk == 0) {
//            if (checkbox_add_instruction.isChecked()) {
//                addInstruction = 1;
//            }
//        }
//
//        String url = Constants.wcfAddUpdateIngredientHopV2 + contentItemPk + "/" + BrewSharedPrefs.getUserKey() + "/" + ingredientHopPk + "/" + selectedHopsUsePk + "/" + selectedHopFormPk + "/" + selectedMeasurement + "/" + selectedHopPk + "/" + selectedTimeUnit + "/" + acidProgress + "/" + my_hop_time_edit_text.getText().toString() + "/" + my_hop_amount_edit_text.getText().toString().trim() + "/" + addInstruction;
//        json = new JsonObject();
//        json.addProperty("name", my_hops_name_edit_text.getText().toString().trim());
//
//        ionUpdateHop = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .setJsonObjectBody(json)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                                 @Override
//                                 public void onCompleted(Exception e, JsonArray result) {
//                                     try {
//                                         addHopDialog.dismiss();
//                                         List<RecipeHop> ingredient = JsonToObject.JsonToIngredientHopList(result);
//
//                                         RecipeHop item = ingredient.get(0);
//                                         int pos = 0;
//
////                                         if (ingredientHopPk != 0) {
////                                             pos = adapter.getPostionByPk(item.getIngredientHopsId());
////                                             adapter.remove(pos);
////                                         } else {
////                                             pos = adapter.getItemCount();
////                                         }
//
//                                         adapter.add(item, pos);
//                                         updateInstruction();
//
//                                     } catch (Exception ex) {
//                                         if (BuildConfig.DEBUG) {
//                                             Log.e(Constants.LOG, ex.getMessage());
//                                         }
//                                     }
//                                 }
//                             }
//
//                );
//
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        contextMenu = menu;
        menu.setHeaderTitle(mHeader);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_hop_modify, menu);
    }

    public void updateInstruction() {
//        AddUpdateRecipe test = ((AddUpdateRecipe) this._fContext);
//
////        ((AddInstructionsFragment) ).instructionDraggableRecyclerAdapter;
//        //if (instructionDraggableRecyclerAdapter != null) {
////            instructionDraggableRecyclerAdapter.notifyDataSetChanged();
////        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_hop_button_delete:
                if (adapter.getItemCount() > 1) {
                    DeleteItem();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Sorry! You must have at least 1 hop",
                            Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.menu_hop_button_edit:
                layout_auto_add.setVisibility(View.GONE);
                setDialogValues(adapter.getItemAt(listPosition));
                addHopDialog.show();
                break;

            case R.id.menu_hop_button_copy:
                makeCopyHop();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void DeleteItem() {

//        String url = Constants.wcfRemoveIngredientHop + contentItemPk + "/" + ingredientHopPk + "/false";
//        ionDeleteHop = Ion.with(_fContext.getApplicationContext())
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
            hop_fab.setVisibility(View.VISIBLE);
            hop_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetDialogValues();
                    layout_auto_add.setVisibility(View.VISIBLE);
                    addHopDialog.show();
                }
            });
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public void makeCopyHop() {
//        String url = Constants.wcfCopyHop + ingredientHopPk + "/" + contentToken;
//        ionUpdateHop = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .setJsonObjectBody(json)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                                 @Override
//                                 public void onCompleted(Exception e, JsonArray result) {
//                                     try {
//                                         List<RecipeHop> ingredient = JsonToObject.JsonToIngredientHopList(result);
//                                         for (RecipeHop item : ingredient) {
//                                             adapter.add(item);
//                                         }
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
}

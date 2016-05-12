package brightseer.com.brewhaha.fragment;

import android.app.AlertDialog;
import android.content.res.Configuration;
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
import brightseer.com.brewhaha.helper.RecyclerItemClickListener;
import brightseer.com.brewhaha.adapter.YeastMyRecipeRecycler;
import brightseer.com.brewhaha.objects.Laboratory;
import brightseer.com.brewhaha.models.RecipeYeast;
import brightseer.com.brewhaha.objects.Yeast;
import brightseer.com.brewhaha.repository.DBHelper_Laboratory;
import brightseer.com.brewhaha.repository.DBHelper_Yeast;
import brightseer.com.brewhaha.repository.JsonToObject;

/**
 * Created by Michael McCulloch on 3/22/2015.
 */
public class AddYeastFragment extends BaseFragment implements View.OnClickListener {
    private View rootView, addYeastDialogView;
    private YeastMyRecipeRecycler adapter;
    private RecyclerView my_yeast_recycle_view;
    private List<Laboratory> laboratoryList = new Vector<>();
    private List<Yeast> yeastMasterList = new Vector<>();
    private Spinner my_yeast_master_spinner, my_yeast_laboratory_spinner;
    private android.support.v7.widget.AppCompatButton my_yeast_submit_button;
    private TextView my_yeast_name_edit_text;
    private int listPosition;
    private int ingredientYeastPk = 0, selectedLabPk = 1, selectedYeastPk = 0;
    private android.support.design.widget.FloatingActionButton yeast_fab;
    private AlertDialog addYeastDialog;
    private JsonObject json = new JsonObject();

    DBHelper_Laboratory repoLaboratory;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_yeast, container, false);
        initRecycler();
        createYeastAddUpdateDialog();
        initViews();
        spinnerListener();
        load();
        return rootView;
    }

    @Override
    public void onClick(View v) {

    }

    public void load() {
//        String url = Constants.wcfGetRecipeYeast + contentToken;
//        ionLoadYeast = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                                 @Override
//                                 public void onCompleted(Exception e, JsonArray result) {
//                                     try {
//                                         List<RecipeYeast> ingredient = JsonToObject.JsonToIngredientYeastList(result);
//
//                                         adapter.addItemsToAdapter(ingredient);
//                                         addFabListener();
//                                     } catch (Exception ex) {
//                                         if (BuildConfig.DEBUG) {
//                                             Log.e(Constants.LOG, ex.getMessage());
//                                         }
//                                     }
//                                 }
//                             }
//                );
    }

    public void initViews() {
        my_yeast_laboratory_spinner = (Spinner) addYeastDialogView.findViewById(R.id.my_yeast_laboratory_spinner);
        populateLaboratorySpinner();
        my_yeast_master_spinner = (Spinner) addYeastDialogView.findViewById(R.id.my_yeast_master_spinner);
        populateYeastMasterSpinner();

        my_yeast_name_edit_text = (TextView) addYeastDialogView.findViewById(R.id.my_yeast_name_edit_text);

        my_yeast_submit_button = (android.support.v7.widget.AppCompatButton) addYeastDialogView.findViewById(R.id.my_yeast_submit_button);

        yeast_fab = (android.support.design.widget.FloatingActionButton) rootView.findViewById(R.id.yeast_fab);
        yeast_fab.setVisibility(View.VISIBLE);
    }

    public void initRecycler() {
        int screenOrientation = getResources().getConfiguration().orientation;
        my_yeast_recycle_view = (RecyclerView) rootView.findViewById(R.id.my_yeast_recycle_view);
        my_yeast_recycle_view.setHasFixedSize(true);

        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);
            my_yeast_recycle_view.setLayoutManager(layoutManager);
            my_yeast_recycle_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
        }

        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

            layoutManager.scrollToPosition(0);
            my_yeast_recycle_view.setLayoutManager(layoutManager);
        }

        List<RecipeYeast> placeHolder = new Vector<>();
        adapter = new YeastMyRecipeRecycler(placeHolder, AddYeastFragment.this);

        my_yeast_recycle_view.setAdapter(adapter);
        my_yeast_recycle_view.setItemAnimator(new DefaultItemAnimator());


        my_yeast_recycle_view.addOnItemTouchListener(
                new RecyclerItemClickListener(_fContext, my_yeast_recycle_view, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
//                            RecipeYeast recipeComment = adapter.getItemAt(position);
//                            mHeader = recipeComment.getName() + ", " + lookupLaboratory(recipeComment.getLaboratoryId()).getName();
//
//                            ingredientYeastPk = recipeComment.getIngredientYeastId();
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

                    }
                })
        );
    }

    public void populateYeastMasterSpinner() {
        DBHelper_Yeast repoYeast = new DBHelper_Yeast(getActivity());
        yeastMasterList = repoYeast.getYeastList();
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(getText(R.string.text_hops_spinner_default).toString());
        for (Yeast item : yeastMasterList) {
            stringArrayList.add(item.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        my_yeast_master_spinner.setAdapter(adapter);
    }

    public void populateLaboratorySpinner() {
        try {
            repoLaboratory = DBHelper_Laboratory.getInstance(_fContext);
            laboratoryList = repoLaboratory.getLaboratoryList();
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (Laboratory item : laboratoryList) {
                if (item.getLaboratoryPk() != 0) {
                    stringArrayList.add(item.getName());
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringArrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            my_yeast_laboratory_spinner.setAdapter(adapter);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void spinnerListener() {
        my_yeast_master_spinner.post(new Runnable() {
            @Override
            public void run() {
                my_yeast_master_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        selectedYeastPk = 0;
                        if (pos > 0) {
                            Yeast ingredient = yeastMasterList.get(pos - 1);
                            if (my_yeast_master_spinner.isEnabled()) {
                                my_yeast_name_edit_text.setText(ingredient.getName());
                            }
                            selectedYeastPk = ingredient.getYeastPk();
                        }
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO maybe add something here later
                    }
                });
            }
        });

        my_yeast_laboratory_spinner.post(new Runnable() {
            @Override
            public void run() {
                my_yeast_laboratory_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                        selectedLabPk = laboratoryList.get(pos).getLaboratoryPk();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO maybe add something here later
                    }
                });
            }
        });

    }

    public void createYeastAddUpdateDialog() {
        LayoutInflater factory = LayoutInflater.from(_fContext);
        addYeastDialogView = factory.inflate(
                R.layout.dialog_add_yeast, null);
        addYeastDialog = new AlertDialog.Builder(_fContext).create();
        addYeastDialog.setView(addYeastDialogView);

        addYeastDialogView.findViewById(R.id.my_yeast_submit_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //your business logic
//                addDialog.dismiss();
                if (validateYeastEntry()) {
                    addUpdateYeast();
                }
            }
        });
        addYeastDialogView.findViewById(R.id.my_yeast_cancel_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addYeastDialog.dismiss();
            }
        });
    }

    public void addFabListener() {
        try {
            yeast_fab.setVisibility(View.VISIBLE);
            yeast_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetDialogValues();
                    addYeastDialog.show();
                }
            });
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    private boolean validateYeastEntry() {
        try {
            boolean valid = true;
            if (TextUtils.isEmpty(my_yeast_name_edit_text.getText())) {
                my_yeast_name_edit_text.setHintTextColor(getResources().getColor(R.color.red));
                Toast.makeText(_fContext, "Name is required", Toast.LENGTH_SHORT).show();
                valid = false;
            }

            return valid;
        } catch (Exception ex) {
            return false;
        }
    }

    private void resetDialogValues() {
        my_yeast_master_spinner.setEnabled(true);
        ingredientYeastPk = 0;
        selectedLabPk = 1;
        selectedYeastPk = 0;
        my_yeast_master_spinner.setSelection(0);
        my_yeast_laboratory_spinner.setSelection(0);
        listPosition = 0;
        my_yeast_name_edit_text.setText("");
        my_yeast_submit_button.setText("Add Yeast");
        my_yeast_name_edit_text.setHintTextColor(getResources().getColor(R.color.app_gray));
    }

    public void setDialogValues(RecipeYeast selected) {
//        my_yeast_master_spinner.setEnabled(false);
//        ingredientYeastPk = selected.getIngredientYeastId();
//        selectedLabPk = selected.getLaboratoryId();
//        selectedYeastPk = selected.getYeastId();
//        my_yeast_name_edit_text.setText(String.valueOf(selected.getName()));
//        my_yeast_submit_button.setText("Update");
//        my_yeast_master_spinner.setSelection(yeastMasterList.indexOf(lookupYeast(selected.getYeastId())) + 1);
//        my_yeast_laboratory_spinner.setSelection(laboratoryList.indexOf(lookupLaboratory(selected.getLaboratoryId())));
    }

    private Yeast lookupYeast(int itemPk) {
        for (Yeast item : yeastMasterList) {
            if (item.getYeastPk() == itemPk) {
                return item;
            }
        }
        return new Yeast();
    }

    public Laboratory lookupLaboratory(int itemPk) {
        for (Laboratory item : laboratoryList) {
            if (item.getLaboratoryPk() == itemPk) {
                return item;
            }
        }
        return new Laboratory();
    }

    public void addUpdateYeast() {
//        String url = Constants.wcfAddUpdateIngredientYeast + ingredientYeastPk + "/" + contentItemPk + "/" + selectedLabPk + "/" + selectedYeastPk + "/0";
//
//        json = new JsonObject();
//        json.addProperty("name", my_yeast_name_edit_text.getText().toString().trim());
//
//        ionUpdateYeast = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .setJsonObjectBody(json)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                                 @Override
//                                 public void onCompleted(Exception e, JsonArray result) {
//                                     try {
//                                         addYeastDialog.dismiss();
//                                         List<RecipeYeast> ingredient = JsonToObject.JsonToIngredientYeastList(result);
//
//
//                                         RecipeYeast item = ingredient.get(0);
//                                         int pos = 0;
//
////                                         if (ingredientYeastPk != 0) {
////                                             pos = adapter.getPostionByPk(item.getIngredientYeastId());
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
//                                         addYeastDialog.dismiss();
//                                     }
//                                 }
//                             }
//
//                );

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        contextMenu = menu;
        menu.setHeaderTitle(mHeader);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_yeast_modify, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_yeast_button_delete:
                if (adapter.getItemCount() > 1) {
                    DeleteItem();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Sorry! You must have at least 1 yeast",
                            Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.menu_yeast_button_edit:
                setDialogValues(adapter.getItemAt(listPosition));
                addYeastDialog.show();
                break;

            case R.id.menu_yeast_button_copy:
                makeCopyYeast();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void DeleteItem() {
//        String url = Constants.wcfRemoveIngredientYeast + ingredientYeastPk + "/" + contentItemPk + "/false";
//        ionDeleteYeast = Ion.with(_fContext.getApplicationContext())
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

    public void makeCopyYeast() {
//        String url = Constants.wcfCopyYeast + ingredientYeastPk + "/" + contentToken;
//        ionUpdateYeast = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .setJsonObjectBody(json)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                                 @Override
//                                 public void onCompleted(Exception e, JsonArray result) {
//                                     try {
//                                         List<RecipeYeast> ingredient = JsonToObject.JsonToIngredientYeastList(result);
//                                         RecipeYeast item = ingredient.get(0);
//                                         adapter.add(item);
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

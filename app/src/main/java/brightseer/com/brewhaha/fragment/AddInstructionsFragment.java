package brightseer.com.brewhaha.fragment;

import android.app.AlertDialog;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.ItemShadowDecorator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager;


import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.adapter.InstructionDraggableRecyclerAdapter;
import brightseer.com.brewhaha.models.RecipeInstruction;
import brightseer.com.brewhaha.repository.JsonToObject;

//import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;

public class AddInstructionsFragment extends BaseFragment {
    private View rootView, addInstructionDialogView;
    private InstructionDraggableRecyclerAdapter instructionDraggableRecyclerAdapter;
    private android.support.design.widget.FloatingActionButton instruction_fab;
    private AlertDialog addInstructionDialog;
    private JsonObject json = new JsonObject();
    private android.support.v7.widget.AppCompatButton my_instruction_submit_button;
    private EditText my_instruction_name_edit_text;
    private int selectedInstructionPk = 0, selectedPosition = 0;
//    public Future<JsonArray> ionLoadInstruction, ionUpdateInstructions, ionCopyInstruction;
//    public Future<String> ionDeleteInstruction, ionOrderUpdate;
    private RecyclerView recyclerView;
    private String mHeader = "";
    private RecipeInstruction selectedRecipeInstruction;
    private boolean isContextOpen = false;

    public AddInstructionsFragment() {

    }

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
        rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
        initializeViewComponents();
        createInstructionAddUpdateDialog();
        initViews();

        loadInstructions();
        return rootView;
    }

    private void initializeViewComponents() {
        LinearLayoutManager recylerViewLayoutManager = new LinearLayoutManager(getActivity());
        recylerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recylerViewLayoutManager.scrollToPosition(0);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_instruction_recycle_view);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        // Touch guard manager  (this class is required to suppress scrolling while swipe-dismiss animation is running)
        RecyclerViewTouchActionGuardManager recyclerViewTouchActionGuardManager = new RecyclerViewTouchActionGuardManager();
        recyclerViewTouchActionGuardManager.setInterceptVerticalScrollingWhileAnimationRunning(true);
        recyclerViewTouchActionGuardManager.setEnabled(true);
        RecyclerViewDragDropManager recyclerViewDragDropManager = new RecyclerViewDragDropManager();

        recyclerViewDragDropManager.setDraggingItemShadowDrawable(
                (NinePatchDrawable) getResources().getDrawable(R.drawable.material_shadow_z39));

//        recyclerViewDragDropManager.setInitiateOnLongPress(true);

        List<RecipeInstruction> placeHolder = new Vector<>();
        instructionDraggableRecyclerAdapter = new InstructionDraggableRecyclerAdapter(placeHolder, AddInstructionsFragment.this);

        instructionDraggableRecyclerAdapter.setAdapterListener(new InstructionDraggableRecyclerAdapter.AdapterListener() {

            @Override
            public void onModelObjectRemoved(int position) {
            }

            @Override
            public void onModelObjectMoved() {

            }

            @Override
            public void onModelObjectSwiped(RecipeInstruction modelObject) {
            }

            @Override
            public void onModelObjectClicked(RecipeInstruction modelObject, int position, View view) {
                resetDialogValues();
                selectedRecipeInstruction = modelObject;

                int getLength = modelObject.getDescription().length();
                if (modelObject.getDescription().length() > 30) {
                    getLength = 30;
                }

                mHeader = modelObject.getDescription().substring(0, getLength);
//                selectedInstructionPk = modelObject.getInstructionId();

                registerForContextMenu(view);
                getActivity().openContextMenu(view);
                view.setLongClickable(false);
            }
        });

        RecyclerView.Adapter wrappedAdapter
                = recyclerViewDragDropManager.createWrappedAdapter(instructionDraggableRecyclerAdapter);

        final GeneralItemAnimator animator = new SwipeDismissItemAnimator();

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(wrappedAdapter);
        recyclerView.setItemAnimator(animator);

        if (!supportsViewElevation()) {
            recyclerView.addItemDecoration(new ItemShadowDecorator((NinePatchDrawable) getResources().getDrawable(R.drawable.material_shadow_z19)));
        }

        recyclerViewTouchActionGuardManager.attachRecyclerView(recyclerView);
        recyclerViewDragDropManager.attachRecyclerView(recyclerView);
    }

    public void loadInstructions() {
//        String url = Constants.wcfGetInstructions + contentToken;
//        ionLoadInstruction = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                                 @Override
//                                 public void onCompleted(Exception e, JsonArray result) {
//                                     try {
//                                         instructionDraggableRecyclerAdapter.clear();
//                                         List<RecipeInstruction> recipeInstruction = JsonToObject.JsonToInstructionList(result);
//
//                                         instructionDraggableRecyclerAdapter.addItemsToAdapter(recipeInstruction);
//
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        contextMenu = menu;
        isContextOpen = true;
        menu.setHeaderTitle(mHeader);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_instruction_modify, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        setDialogValues(selectedRecipeInstruction);
        switch (item.getItemId()) {
            case R.id.menu_instruction_button_delete:
                if (instructionDraggableRecyclerAdapter.getItemCount() > 1) {
                    DeleteItem();
                } else {
                    Toast.makeText(_fContext,
                            "Sorry! You must have at least 1 instruction",
                            Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.menu_instruction_button_edit:
                addInstructionDialog.show();
                break;

            case R.id.menu_instruction_button_copy:
                makeCopyInstruction();
                break;
        }
        return super.onContextItemSelected(item);
    }
//    private InstructionDraggableRecyclerAdapter unwrapAdapter() {
//        // Unwrap once for swipeable
//        RecyclerView.Adapter unWrappedAdapter = ((BaseWrapperAdapter) recyclerView.getAdapter()).getWrappedAdapter();
//
//        // Unwrap again for draggable
//        unWrappedAdapter = ((BaseWrapperAdapter) unWrappedAdapter).getWrappedAdapter();
//
//        // return adapter
//        return ((InstructionDraggableRecyclerAdapter) unWrappedAdapter);
//    }

    public void initViews() {
        my_instruction_submit_button = (android.support.v7.widget.AppCompatButton) addInstructionDialogView.findViewById(R.id.my_instruction_submit_button);
        my_instruction_name_edit_text = (EditText) addInstructionDialogView.findViewById(R.id.my_instruction_name_edit_text);

        instruction_fab = (android.support.design.widget.FloatingActionButton) rootView.findViewById(R.id.instruction_fab);
    }

    public void createInstructionAddUpdateDialog() {
        LayoutInflater factory = LayoutInflater.from(_fContext);
        addInstructionDialogView = factory.inflate(
                R.layout.dialog_add_instruction, null);
        addInstructionDialog = new AlertDialog.Builder(_fContext).create();
        addInstructionDialog.setView(addInstructionDialogView);

        addInstructionDialogView.findViewById(R.id.my_instruction_submit_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validateEntry()) {
//                    addUpdateInstruction();
                }
            }
        });
        addInstructionDialogView.findViewById(R.id.my_instruction_cancel_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addInstructionDialog.dismiss();
            }
        });
    }

    private boolean validateEntry() {
        try {
            boolean valid = true;
            if (TextUtils.isEmpty(my_instruction_name_edit_text.getText())) {
                my_instruction_name_edit_text.setHintTextColor(getResources().getColor(R.color.red));
                Toast.makeText(_fContext, "Required", Toast.LENGTH_SHORT).show();
                valid = false;
            }

            return valid;
        } catch (Exception ex) {
            return false;
        }
    }

    private void resetDialogValues() {
        selectedInstructionPk = 0;
        my_instruction_name_edit_text.setText("");
        my_instruction_submit_button.setText("Add");
        my_instruction_name_edit_text.setHintTextColor(getResources().getColor(R.color.app_gray));
        selectedPosition = instructionDraggableRecyclerAdapter.getItemCount() + 1;
    }

    public void setDialogValues(RecipeInstruction selected) {
        if (selected != null) {
//            selectedInstructionPk = selected.getInstructionId();
            my_instruction_name_edit_text.setText(selected.getDescription());
            my_instruction_submit_button.setText("Update");
            selectedPosition = selected.getOrder();
        }
    }

//    public void addUpdateInstruction() {
//        String url = Constants.wcfAddUpdateInstruction + selectedInstructionPk + "/" + contentItemPk + "/" + BrewSharedPrefs.getUserKey() + "/" + selectedPosition;
//        json = new JsonObject();
//        json.addProperty("description", my_instruction_name_edit_text.getText().toString().trim());
//        ionUpdateInstructions = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .setJsonObjectBody(json)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                                 @Override
//                                 public void onCompleted(Exception e, JsonArray result) {
//                                     try {
//                                         List<RecipeInstruction> recipeInstructions = JsonToObject.JsonToInstructionList(result);
//                                         if (selectedInstructionPk != 0) {
//                                             instructionDraggableRecyclerAdapter.remove(selectedPosition - 1);
//                                         }
//
//                                         RecipeInstruction item = recipeInstructions.get(0);
//                                         instructionDraggableRecyclerAdapter.add(item, item.getOrder() - 1);
//                                         instructionDraggableRecyclerAdapter.RefreshOrder(false);
//                                         addInstructionDialog.dismiss();
//                                     } catch (Exception ex) {
//                                         if (BuildConfig.DEBUG) {
//                                             Log.e(Constants.LOG, ex.getMessage());
//                                         }
//                                     }
//                                 }
//                             }
//
//                );
//    }

    public void addFabListener() {
        try {
            instruction_fab.setVisibility(View.VISIBLE);
            instruction_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetDialogValues();
                    addInstructionDialog.show();
                }
            });
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    private void DeleteItem() {
//        String url = Constants.wcfRemoveInstruction + selectedInstructionPk + "/" + contentItemPk + "/false";
//        ionDeleteInstruction = Ion.with(_fContext.getApplicationContext())
//                .load(url)
//                .addHeader("Content-Type", "application/json")
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String s) {
//                        instructionDraggableRecyclerAdapter.remove(selectedPosition - 1);
//                        resetDialogValues();
//                        instructionDraggableRecyclerAdapter.RefreshOrder(true);
//                        Snackbar.make(rootView.findViewById(R.id.coordinatorLayout), mHeader + " Deleted", Snackbar.LENGTH_SHORT)
//                                .show();
//                    }
//                });

    }

    public void UpdateOrder(List<RecipeInstruction> newOrderList) {
        Gson test = new Gson();
        JsonElement element = test.toJsonTree(newOrderList);
        json = new JsonObject();
        json.add("recipeInstructions", element);

//        String url = Constants.UpdateInstructions;
//        ionOrderUpdate = Ion.with(_fContext.getApplicationContext())
//                .load(url)
//                .addHeader("Content-Type", "application/json")
//                .setJsonObjectBody(json)
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String s) {
////                        String test = s;
//                    }
//                });
    }

    public void makeCopyInstruction() {
//        String url = Constants.wcfCopyInstruction + selectedInstructionPk + "/" + contentToken;
//        ionCopyInstruction = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .setJsonObjectBody(json)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                                 @Override
//                                 public void onCompleted(Exception e, JsonArray result) {
//                                     try {
//                                         List<RecipeInstruction> recipeInstructions = JsonToObject.JsonToInstructionList(result);
//                                         RecipeInstruction item = recipeInstructions.get(0);
//                                         instructionDraggableRecyclerAdapter.add(item, item.getOrder() - 1);
//                                         instructionDraggableRecyclerAdapter.RefreshOrder(true);
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

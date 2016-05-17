package brightseer.com.brewhaha.recipe_fragments;

import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.ItemShadowDecorator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.RecipeCardsActivity;
import brightseer.com.brewhaha.models.IngredientSelected;
import brightseer.com.brewhaha.models.RecipeInstruction;
import brightseer.com.brewhaha.recipe_adapters.InstructionAdapter;
import brightseer.com.brewhaha.recipe_adapters.RecipeDirectionViewHolder;
import brightseer.com.brewhaha.repository.DBHelper_IngredientSelected;

/**
 * Created by wooan on 3/21/2016.
 */
public class DirectionFragment extends BaseRecipeFragment {
    private View rootView;
    private List<RecipeInstruction> recipeInstructions = new Vector<>();
    private String feedKey;
    private Firebase rootRef;

    private InstructionAdapter instructionAdapter;
    private FirebaseRecyclerAdapter fbInstructionAdapter;
    private boolean isEditMode;
    DBHelper_IngredientSelected repoSelected;

    public DirectionFragment() {
    }

    public static DirectionFragment newInstance(int centerX, int centerY, int color, String _feedKey) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
        args.putString(Constants.fbFeedKey, _feedKey);

        DirectionFragment fragment = new DirectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe_direction, container, false);
        rootView = SetCircularReveal(rootView);
        ReadBundle();
        initFirebaseDb();
        isEditMode = ((RecipeCardsActivity) getActivity()).GetIsEditEnabled();

        if (!BrewSharedPrefs.getUserKey().isEmpty()) {
            repoSelected = new DBHelper_IngredientSelected(getActivity());
        }

//        addTestDirections();
        if (isEditMode) {
            GetDirections();
        }

        initRecyclerView();

        return rootView;
    }

    private void initFirebaseDb() {
        rootRef = new Firebase(Constants.fireBaseRoot).child(Constants.fbDirections).child(feedKey);
    }

    private void ReadBundle() {
        feedKey = getArguments().getString(Constants.fbFeedKey);
    }

    public void addTestDirections() {
        try {
            ///ADD NEW //////////////////

            Firebase pushInst = rootRef.push();
            RecipeInstruction recipeInstruction = new RecipeInstruction();
            recipeInstruction.setActive(true);
            recipeInstruction.setDescription("this started as number 1");
            recipeInstruction.setOrder(1);
            pushInst.setValue(recipeInstruction);

            String postId = pushInst.getKey();
            recipeInstruction.setKey(postId);
            Firebase theChild = rootRef.child(postId);
            Map<String, Object> keyValue = new HashMap<String, Object>();
            keyValue.put("key", postId);
            theChild.updateChildren(keyValue);


            pushInst = rootRef.push();
            recipeInstruction = new RecipeInstruction();
            recipeInstruction.setActive(true);
            recipeInstruction.setDescription("this started as number 2");
            recipeInstruction.setOrder(2);
            pushInst.setValue(recipeInstruction);

            postId = pushInst.getKey();
            recipeInstruction.setKey(postId);
            theChild = rootRef.child(postId);
            keyValue = new HashMap<String, Object>();
            keyValue.put("key", postId);
            theChild.updateChildren(keyValue);

            pushInst = rootRef.push();
            recipeInstruction = new RecipeInstruction();
            recipeInstruction.setActive(true);
            recipeInstruction.setDescription("this started as number 3");
            recipeInstruction.setOrder(3);
            pushInst.setValue(recipeInstruction);

            postId = pushInst.getKey();
            recipeInstruction.setKey(postId);
            theChild = rootRef.child(postId);
            keyValue = new HashMap<String, Object>();
            keyValue.put("key", postId);
            theChild.updateChildren(keyValue);

            pushInst = rootRef.push();
            recipeInstruction = new RecipeInstruction();
            recipeInstruction.setActive(true);
            recipeInstruction.setDescription("this started as number 4");
            recipeInstruction.setOrder(4);
            pushInst.setValue(recipeInstruction);

            postId = pushInst.getKey();
            recipeInstruction.setKey(postId);
            theChild = rootRef.child(postId);
            keyValue = new HashMap<String, Object>();
            keyValue.put("key", postId);
            theChild.updateChildren(keyValue);

            pushInst = rootRef.push();
            recipeInstruction = new RecipeInstruction();
            recipeInstruction.setActive(true);
            recipeInstruction.setDescription("this started as number 5");
            recipeInstruction.setOrder(5);
            pushInst.setValue(recipeInstruction);

            postId = pushInst.getKey();
            recipeInstruction.setKey(postId);
            theChild = rootRef.child(postId);
            keyValue = new HashMap<String, Object>();
            keyValue.put("key", postId);
            theChild.updateChildren(keyValue);

            //////////////////
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void GetDirections() {
        try {
            Query queryRef = rootRef.orderByChild("order");
            queryRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    RecipeInstruction recipeInstruction = dataSnapshot.getValue(RecipeInstruction.class);
                    recipeInstruction.setKey(dataSnapshot.getKey());
                    recipeInstructions.add(recipeInstruction);
//                    instructionAdapter.add(recipeInstruction, recipeInstruction.getOrder());
                    instructionAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager recylerViewLayoutManager = new LinearLayoutManager(getActivity());
        recylerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recylerViewLayoutManager.scrollToPosition(0);

        RecyclerView my_instruction_recycle_view = (RecyclerView) rootView.findViewById(R.id.my_instruction_recycle_view);
        my_instruction_recycle_view.setLayoutManager(recylerViewLayoutManager);

        if (!isEditMode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                my_instruction_recycle_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider, getActivity().getTheme()), true));
            } else {
                my_instruction_recycle_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
            }

            Query queryRef = rootRef.orderByChild("order");
            fbInstructionAdapter = new FirebaseRecyclerAdapter<RecipeInstruction, RecipeDirectionViewHolder>(RecipeInstruction.class, R.layout.row_recipe_direction, RecipeDirectionViewHolder.class, queryRef) {
                @Override
                protected void populateViewHolder(RecipeDirectionViewHolder recipeDirectionViewHolder, RecipeInstruction recipeInstruction, final int i) {
                    String description = recipeInstruction.getDescription();
                    String position = String.valueOf(recipeInstruction.getOrder());

                    recipeDirectionViewHolder.my_instruction_text_text_view.setText(description);
                    recipeDirectionViewHolder.position.setText(position);

                    if (!BrewSharedPrefs.getUserKey().isEmpty()) {
                        if (repoSelected.isSelected(feedKey, BrewSharedPrefs.getUserKey(), recipeInstruction.getKey())) {
                            recipeDirectionViewHolder.direction_checkbox.setChecked(true);
                        }
                    }

                    recipeDirectionViewHolder.direction_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            RecipeInstruction recipeInstructionChecked = (RecipeInstruction) fbInstructionAdapter.getItem(i);
                            if (BrewSharedPrefs.getUserKey().isEmpty())
                                return;

                            if (isChecked) {
                                IngredientSelected newItem = new IngredientSelected();
                                newItem.setFeedKey(feedKey);
                                newItem.setUserKey(BrewSharedPrefs.getUserKey());
                                newItem.setKey(recipeInstructionChecked.getKey());

                                repoSelected.insertIngredientSelected(newItem);
                            } else {
                                repoSelected.deleteIngredientSelectedRecord(recipeInstructionChecked.getKey());
                            }
                        }
                    });

                }
            };

            my_instruction_recycle_view.setAdapter(fbInstructionAdapter);
            my_instruction_recycle_view.setItemAnimator(new DefaultItemAnimator());
        }

        if (isEditMode) {
            // Touch guard manager  (this class is required to suppress scrolling while swipe-dismiss animation is running)
            RecyclerViewTouchActionGuardManager recyclerViewTouchActionGuardManager = new RecyclerViewTouchActionGuardManager();
            recyclerViewTouchActionGuardManager.setInterceptVerticalScrollingWhileAnimationRunning(true);
            recyclerViewTouchActionGuardManager.setEnabled(true);
            RecyclerViewDragDropManager recyclerViewDragDropManager = new RecyclerViewDragDropManager();

            recyclerViewDragDropManager.setDraggingItemShadowDrawable(
                    (NinePatchDrawable) getResources().getDrawable(R.drawable.material_shadow_z39));


            instructionAdapter = new InstructionAdapter(recipeInstructions, DirectionFragment.this);
            instructionAdapter.setAdapterListener(new InstructionAdapter.AdapterListener() {

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
//                resetDialogValues();
//                selectedRecipeInstructionselectedRecipeInstruction = modelObject;

                    int getLength = modelObject.getDescription().length();
                    if (modelObject.getDescription().length() > 30) {
                        getLength = 30;
                    }

//                mHeader = modelObject.getDescription().substring(0, getLength);
//                selectedInstructionPk = modelObject.getInstructionId();

                    registerForContextMenu(view);
                    getActivity().openContextMenu(view);
                    view.setLongClickable(false);
                }
            });

            RecyclerView.Adapter wrappedAdapter
                    = recyclerViewDragDropManager.createWrappedAdapter(instructionAdapter);

            final GeneralItemAnimator animator = new SwipeDismissItemAnimator();


            my_instruction_recycle_view.setAdapter(wrappedAdapter);
            my_instruction_recycle_view.setItemAnimator(animator);

            if (!supportsViewElevation()) {
                my_instruction_recycle_view.addItemDecoration(new ItemShadowDecorator((NinePatchDrawable) getResources().getDrawable(R.drawable.material_shadow_z19)));
            }

            recyclerViewTouchActionGuardManager.attachRecyclerView(my_instruction_recycle_view);
            recyclerViewDragDropManager.attachRecyclerView(my_instruction_recycle_view);
        }
    }

    public void UpdateOrder(List<RecipeInstruction> newOrderList) {
        try {
            for (RecipeInstruction item : newOrderList) {
                Firebase ref = rootRef.child(item.getKey());
                Map<String, Object> newOrder = new HashMap<String, Object>();
                newOrder.put("order", item.getOrder());
                ref.updateChildren(newOrder);
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fbInstructionAdapter != null)
            fbInstructionAdapter.cleanup();
    }
}

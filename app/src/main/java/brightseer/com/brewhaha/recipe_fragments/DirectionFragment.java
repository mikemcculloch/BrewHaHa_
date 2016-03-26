package brightseer.com.brewhaha.recipe_fragments;

import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.ItemShadowDecorator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.adapter.InstructionDraggableRecyclerAdapter;
import brightseer.com.brewhaha.helper.CustomRecyclerView;
import brightseer.com.brewhaha.objects.Instruction;
import brightseer.com.brewhaha.recipe_adapters.InstructionAdapter;

/**
 * Created by wooan on 3/21/2016.
 */
public class DirectionFragment extends BaseRecipeFragment {
    private View rootView;
    private List<Instruction> recipeInstructions = new Vector<>();
    private String contentToken;

    private InstructionAdapter instructionAdapter;
    private RecyclerView recyclerView;
    private Instruction selectedInstruction;
    public Future<String> ionOrderUpdate;


    public DirectionFragment() {
    }

    public static DirectionFragment newInstance(int centerX, int centerY, int color, List<Instruction> instructions, String _contentToken) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
        args.putSerializable(Constants.bundleRecipeInstructions, (Serializable) instructions);

        args.putString(Constants.spContentToken, _contentToken);

        DirectionFragment fragment = new DirectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recipe_direction, container, false);
        rootView = SetCircularReveal(rootView);
        rootView.setBackgroundColor(getArguments().getInt("color"));

//        Context _fContext = getActivity();
        ReadBundle();

        initRecyclerView();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void ReadBundle() {
        recipeInstructions = (List<Instruction>) getArguments().getSerializable(Constants.bundleRecipeInstructions);
    }

    private void initRecyclerView() {
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

        instructionAdapter = new InstructionAdapter(recipeInstructions, DirectionFragment.this);
        instructionAdapter.setAdapterListener(new InstructionAdapter.AdapterListener() {

            @Override
            public void onModelObjectRemoved(int position) {
            }

            @Override
            public void onModelObjectMoved() {

            }

            @Override
            public void onModelObjectSwiped(Instruction modelObject) {
            }

            @Override
            public void onModelObjectClicked(Instruction modelObject, int position, View view) {
//                resetDialogValues();
                selectedInstruction = modelObject;

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

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(wrappedAdapter);
        recyclerView.setItemAnimator(animator);

        if (!supportsViewElevation()) {
            recyclerView.addItemDecoration(new ItemShadowDecorator((NinePatchDrawable) getResources().getDrawable(R.drawable.material_shadow_z19)));
        }

        recyclerViewTouchActionGuardManager.attachRecyclerView(recyclerView);
        recyclerViewDragDropManager.attachRecyclerView(recyclerView);
    }

    public void UpdateOrder(List<Instruction> newOrderList) {
        Gson test = new Gson();
        JsonElement element = test.toJsonTree(newOrderList);
        JsonObject json = new JsonObject();
        json.add("instructionMList", element);

        String url = Constants.wcfInstructionOrderUpdate + contentToken;
        ionOrderUpdate = Ion.with(getActivity().getApplicationContext())
                .load(url)
                .addHeader("Content-Type", "application/json")
                .setJsonObjectBody(json)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String s) {
//                        String test = s;
                    }
                });
    }
}

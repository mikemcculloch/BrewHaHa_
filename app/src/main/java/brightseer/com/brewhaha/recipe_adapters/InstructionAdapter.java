package brightseer.com.brewhaha.recipe_adapters;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.objects.RecipeInstruction;
import brightseer.com.brewhaha.recipe_fragments.DirectionFragment;

/**
 * An adapter with drag and drop implementation for some Instructions
 */
public class InstructionAdapter extends RecyclerView.Adapter< InstructionViewHolder.Instruction>
        implements DraggableItemAdapter<InstructionViewHolder.Instruction> {

    private static final String TAG = InstructionAdapter.class.getSimpleName();
    private static final int MODEL_OBJECT = 1;
    private List<RecipeInstruction> recipeInstructionList = new Vector<>();
    private AdapterListener adapterListener;

    private Fragment _fragment;

    public InstructionAdapter(List<RecipeInstruction> recipeInstructions, Fragment fragment) {
        this.recipeInstructionList = recipeInstructions;
        _fragment = fragment;
        setHasStableIds(true);
    }

    public void add(RecipeInstruction item, int position) {
        recipeInstructionList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        recipeInstructionList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        recipeInstructionList.clear();
        notifyDataSetChanged();
    }

    @Override
    public InstructionViewHolder.Instruction onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        try {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.recipe_direction_row, viewGroup, false);
            return new InstructionViewHolder.Instruction(itemView, viewType);
        } catch (Exception e) {
            String test = e.getMessage();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(InstructionViewHolder.Instruction viewHolder, final int position) {
        // set background resource (target view ID: container)
        int dragState = viewHolder.getDragStateFlags();

        final RecipeInstruction modelObject = recipeInstructionList.get(position);
        final View thisItemView = viewHolder.itemView;

        viewHolder._my_instruction_text_text_view.setText(modelObject.getDescription());
        viewHolder._position.setText("" + (modelObject.getOrder()));

        viewHolder._viewHolderListener = new InstructionViewHolder.Instruction.ViewHolderListener() {
            @Override
            public void onSelectionClicked() {
                adapterListener.onModelObjectClicked(modelObject, position, thisItemView);
            }
        };

        if (((dragState & RecyclerViewDragDropManager.STATE_FLAG_IS_UPDATED) != 0)) {
            int bgResId;
            if ((dragState & RecyclerViewDragDropManager.STATE_FLAG_IS_ACTIVE) != 0) {
                bgResId = R.drawable.bg_item_dragging_active_state;
            } else if ((dragState & RecyclerViewDragDropManager.STATE_FLAG_DRAGGING) != 0) {
                bgResId = R.drawable.bg_item_dragging_state;
            } else {
                bgResId = R.drawable.bg_swipe_item_neutral;
            }
            viewHolder._container.setBackgroundResource(bgResId);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return MODEL_OBJECT;
    }

    @Override
    public int getItemCount() {
        return recipeInstructionList.size();
    }

    @Override
    public long getItemId(int position) {
        return recipeInstructionList.get(position).hashCode();
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {
        Log.i(TAG, "onMoveItem(fromPosition = " + fromPosition + ", toPosition = " + toPosition + ")");
        try {
            if ((fromPosition == toPosition)) {
                return; //NO CHANGE
            }

            final RecipeInstruction item1 = recipeInstructionList.remove(fromPosition);
            recipeInstructionList.add(toPosition, item1);
            notifyItemMoved(fromPosition, toPosition);

            RefreshOrder(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void RefreshOrder(boolean forceUpdate) {
        List<RecipeInstruction> newOrder = new Vector<>();
        int rowNumber = 1;
        for (RecipeInstruction item : recipeInstructionList) {
            item.setOrder(rowNumber);
            newOrder.add(item);
            rowNumber += 1;
        }
        recipeInstructionList.clear();
        recipeInstructionList = newOrder;
        notifyDataSetChanged();

        if (forceUpdate) {
            ((DirectionFragment) this._fragment).UpdateOrder(recipeInstructionList);
        }
    }

    @Override
    public boolean onCheckCanStartDrag(InstructionViewHolder.Instruction holder, int position, int x, int y) {
        if (holder.viewType != MODEL_OBJECT) {
            return false;
        }
        // x, y --- relative from the itemView's top-left
        final View containerView = holder._container;
        final View dragHandleView = holder._drag_handle;

        final int offsetX = containerView.getLeft() + (int) (ViewCompat.getTranslationX(containerView) + 0.5f);
        final int offsetY = containerView.getTop() + (int) (ViewCompat.getTranslationY(containerView) + 0.5f);

        return Utilities.hitTest(dragHandleView, x - offsetX, y - offsetY);
    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(InstructionViewHolder.Instruction instructionMyRecipeViewHolder, int i) {
        return null;
    }


    public void addItemsToAdapter(List<RecipeInstruction> modelsToAdd) {
        recipeInstructionList.addAll(modelsToAdd);

        for (int i = 0; i < recipeInstructionList.size(); i++) {
            recipeInstructionList.get(i).setOrder(i + 1);
        }

        notifyDataSetChanged();
    }

    public void setAdapterListener(AdapterListener listener) {
        this.adapterListener = listener;
    }

    public interface AdapterListener {
        void onModelObjectRemoved(int position);

        void onModelObjectMoved();

        void onModelObjectSwiped(RecipeInstruction modelObject);

        void onModelObjectClicked(RecipeInstruction modelObject, int position, View view);
    }
}


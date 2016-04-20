package brightseer.com.brewhaha.adapter;

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
import brightseer.com.brewhaha.fragment.AddInstructionsFragment;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.RecipeInstruction;
import brightseer.com.brewhaha.objects.RecyclerObjects;

/**
 * An adapter with drag and drop implementation for some Instructions
 */
public class InstructionDraggableRecyclerAdapter extends RecyclerView.Adapter<RecyclerObjects.InstructionMyRecipeViewHolder>
        implements DraggableItemAdapter<RecyclerObjects.InstructionMyRecipeViewHolder> {

    private static final String TAG = InstructionDraggableRecyclerAdapter.class.getSimpleName();
    private static final int MODEL_OBJECT = 1;
    private List<RecipeInstruction> modelObjects = new Vector<>();
    private AdapterListener adapterListener;

    private Fragment _fragment;

    public InstructionDraggableRecyclerAdapter(List<RecipeInstruction> jsonObject, Fragment fragment) {
        this.modelObjects = jsonObject;
        _fragment = fragment;
        setHasStableIds(true);
    }

    public void add(RecipeInstruction item, int position) {
        modelObjects.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        modelObjects.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        modelObjects.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerObjects.InstructionMyRecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        try {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.row_my_instruction, viewGroup, false);
            return new RecyclerObjects.InstructionMyRecipeViewHolder(itemView, viewType);
        } catch (Exception e) {
            String test = e.getMessage();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.InstructionMyRecipeViewHolder viewHolder, final int position) {
        // set background resource (target view ID: container)
        int dragState = viewHolder.getDragStateFlags();

        final RecipeInstruction modelObject = modelObjects.get(position);
        final View thisItemView = viewHolder.itemView;

        viewHolder.my_instruction_text_text_view.setText(modelObject.getDescription());
        viewHolder.positionTextView.setText("" + (modelObject.getOrder()));

        viewHolder.viewHolderListener = new RecyclerObjects.InstructionMyRecipeViewHolder.ViewHolderListener() {
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
            viewHolder.mContainer.setBackgroundResource(bgResId);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return MODEL_OBJECT;
    }

    @Override
    public int getItemCount() {
        return modelObjects.size();
    }

    @Override
    public long getItemId(int position) {
        return modelObjects.get(position).hashCode();
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {
        Log.i(TAG, "onMoveItem(fromPosition = " + fromPosition + ", toPosition = " + toPosition + ")");
        try {
            if ((fromPosition == toPosition)) {
                return; //NO CHANGE
            }

            final RecipeInstruction item1 = modelObjects.remove(fromPosition);
            modelObjects.add(toPosition, item1);
            notifyItemMoved(fromPosition, toPosition);

            RefreshOrder(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void RefreshOrder(boolean forceUpdate) {
        List<RecipeInstruction> newOrder = new Vector<>();
        int rowNumber = 1;
        for (RecipeInstruction item : modelObjects) {
            item.setOrder(rowNumber);
            newOrder.add(item);
            rowNumber += 1;
        }
        modelObjects.clear();
        modelObjects = newOrder;
        notifyDataSetChanged();

        if (forceUpdate) {
            ((AddInstructionsFragment) this._fragment).UpdateOrder(modelObjects);
        }
    }

    @Override
    public boolean onCheckCanStartDrag(RecyclerObjects.InstructionMyRecipeViewHolder holder, int position, int x, int y) {
        if (holder.viewType != MODEL_OBJECT) {
            return false;
        }
        // x, y --- relative from the itemView's top-left
        final View containerView = holder.mContainer;
        final View dragHandleView = holder.mDragHandle;

        final int offsetX = containerView.getLeft() + (int) (ViewCompat.getTranslationX(containerView) + 0.5f);
        final int offsetY = containerView.getTop() + (int) (ViewCompat.getTranslationY(containerView) + 0.5f);

        return Utilities.hitTest(dragHandleView, x - offsetX, y - offsetY);
    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(RecyclerObjects.InstructionMyRecipeViewHolder instructionMyRecipeViewHolder, int i) {
        return null;
    }


    public void addItemsToAdapter(List<RecipeInstruction> modelsToAdd) {
        modelObjects.addAll(modelsToAdd);

        for (int i = 0; i < modelObjects.size(); i++) {
            modelObjects.get(i).setOrder(i + 1);
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


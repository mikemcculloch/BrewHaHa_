package brightseer.com.brewhaha.recipe_adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableSwipeableItemViewHolder;

import brightseer.com.brewhaha.R;

/**
 * Created by wooan on 3/25/2016.
 */

public class InstructionViewHolder extends AbstractDraggableSwipeableItemViewHolder
        implements View.OnClickListener {

    public int viewType;
    public ViewGroup _container;
    public View _drag_handle;
    public TextView _my_instruction_text_text_view;
    public TextView _position;
    public ViewHolderListener _viewHolderListener;
    public CheckBox _direction_checkbox;

    public InstructionViewHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType = viewType;
        _container = (ViewGroup) itemView.findViewById(R.id.container);
        _drag_handle = itemView.findViewById(R.id.drag_handle);
        _my_instruction_text_text_view = (TextView) itemView.findViewById(R.id.my_instruction_text_text_view);
        _position = (TextView) itemView.findViewById(R.id.position);
        _direction_checkbox = (CheckBox) itemView.findViewById(R.id.direction_checkbox);

        _container.setOnClickListener(this);
    }

    /**
     * Gets the View to animate for swiping
     *
     * @return The container view to animate for swiping
     */
    @Override
    public View getSwipeableContainerView() {
        return _container;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.container:
                _viewHolderListener.onSelectionClicked();
                break;
        }
    }

    public interface ViewHolderListener {
        void onSelectionClicked();
    }


}

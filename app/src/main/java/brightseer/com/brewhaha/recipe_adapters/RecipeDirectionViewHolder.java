package brightseer.com.brewhaha.recipe_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import brightseer.com.brewhaha.R;

/**
 * Created by wooan on 4/18/2016.
 */
public class RecipeDirectionViewHolder extends RecyclerView.ViewHolder {
    public TextView my_instruction_text_text_view;
    public TextView position;
    public CheckBox direction_checkbox;

    public RecipeDirectionViewHolder(View itemView) {
        super(itemView);
        my_instruction_text_text_view = (TextView) itemView.findViewById(R.id.my_instruction_text_text_view);
        position = (TextView) itemView.findViewById(R.id.position);
        direction_checkbox = (CheckBox) itemView.findViewById(R.id.direction_checkbox);
    }
}

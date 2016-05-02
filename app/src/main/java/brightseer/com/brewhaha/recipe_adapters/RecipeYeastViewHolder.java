package brightseer.com.brewhaha.recipe_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import brightseer.com.brewhaha.R;

/**
 * Created by wooan on 4/18/2016.
 */
public class RecipeYeastViewHolder extends RecyclerView.ViewHolder {
    public TextView my_yeast_type_text_view;
    public TextView my_yeast_Lab_text_view;
    public CheckBox yeast_checkbox;

    public RecipeYeastViewHolder(View itemView) {
        super(itemView);
        my_yeast_type_text_view = (TextView) itemView.findViewById(R.id.my_yeast_type_text_view);
        my_yeast_Lab_text_view = (TextView) itemView.findViewById(R.id.my_yeast_Lab_text_view);
        yeast_checkbox = (CheckBox) itemView.findViewById(R.id.yeast_checkbox);
    }
}
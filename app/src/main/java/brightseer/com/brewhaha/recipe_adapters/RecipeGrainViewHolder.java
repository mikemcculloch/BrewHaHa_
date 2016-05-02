package brightseer.com.brewhaha.recipe_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import brightseer.com.brewhaha.R;

/**
 * Created by wooan on 4/18/2016.
 */
public class RecipeGrainViewHolder extends RecyclerView.ViewHolder {

    public TextView my_grain_amount_text_view;
    public TextView my_grain_weight_text_view;
    public TextView my_grain_description_text_view;
    public ImageView my_grain_color_image_view;

    public TextView row_grain_use_text_view;
    public TextView row_grain_country_text_view;
    public CheckBox grain_checkbox;

    public RecipeGrainViewHolder(View itemView) {
        super(itemView);
        my_grain_amount_text_view = (TextView) itemView.findViewById(R.id.my_grain_amount_text_view);
        my_grain_weight_text_view = (TextView) itemView.findViewById(R.id.my_grain_weight_text_view);
        my_grain_description_text_view = (TextView) itemView.findViewById(R.id.my_grain_description_text_view);
        my_grain_color_image_view = (ImageView) itemView.findViewById(R.id.my_grain_color_image_view);
        row_grain_use_text_view = (TextView) itemView.findViewById(R.id.row_grain_use_text_view);
        row_grain_country_text_view = (TextView) itemView.findViewById(R.id.row_grain_country_text_view);
        grain_checkbox = (CheckBox) itemView.findViewById(R.id.grain_checkbox);
    }
}

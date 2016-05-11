package brightseer.com.brewhaha.recipe_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import brightseer.com.brewhaha.R;

/**
 * Created by wooan on 4/17/2016.
 */
public class RecipeMenuViewHolder extends RecyclerView.ViewHolder {
    public ImageView menu_image_view;
    public TextView menu_text_view;

    public RecipeMenuViewHolder(View itemView) {
        super(itemView);
        menu_image_view = (ImageView) itemView.findViewById(R.id.menu_image_view);
        menu_text_view = (TextView) itemView.findViewById(R.id.menu_text_view);
    }
}
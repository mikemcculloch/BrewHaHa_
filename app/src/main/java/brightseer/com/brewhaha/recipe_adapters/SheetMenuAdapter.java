package brightseer.com.brewhaha.recipe_adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.models.RecipeMenuItem;

/**
 * Created by wooan on 5/10/2016.
 */
public class SheetMenuAdapter extends RecyclerView.Adapter<RecipeMenuViewHolder> {
    List<RecipeMenuItem> recipeMenuItems;
    Activity activity;

    public SheetMenuAdapter(List<RecipeMenuItem> _recipeMenuItems, Activity _activity) {
        this.recipeMenuItems = _recipeMenuItems;
        activity = _activity;
    }

    @Override
    public RecipeMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.row_recipe_menu, parent, false);
        return new RecipeMenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeMenuViewHolder holder, int position) {
        final RecipeMenuItem recipeMenuItem = recipeMenuItems.get(position);
        holder.menu_image_view.setImageResource(recipeMenuItem.getmDrawableRes());
        holder.menu_text_view.setText(recipeMenuItem.getmTitle());

    }

    @Override
    public int getItemCount() {
        return recipeMenuItems.size();
    }

    public void addList(List<RecipeMenuItem> modelsToAdd) {
        recipeMenuItems.addAll(modelsToAdd);
        notifyDataSetChanged();
    }

    public RecipeMenuItem getItemAt(int postion) {
        return recipeMenuItems.get(postion);
    }

}

package brightseer.com.brewhaha.recipe_adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.fragment.AddYeastFragment;
import brightseer.com.brewhaha.objects.Laboratory;
import brightseer.com.brewhaha.objects.RecipeYeast;
import brightseer.com.brewhaha.objects.RecyclerObjects;
import brightseer.com.brewhaha.recipe_fragments.IngredientFragment;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class YeastRecycler extends RecyclerView.Adapter<RecyclerObjects.YeastMyRecipeViewHolder> {
    private List<RecipeYeast> recipeYeasts = new Vector<>();
    private Fragment _fragment;

    public YeastRecycler(List<RecipeYeast> jsonObject, Fragment fragment) {
        this.recipeYeasts = jsonObject;
        _fragment = fragment;
    }

    public void add(RecipeYeast item, int position) {
        recipeYeasts.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        recipeYeasts.remove(position);
        notifyItemRemoved(position);
    }

    public void add(RecipeYeast item) {
        recipeYeasts.add(item);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerObjects.YeastMyRecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.recipe_yeast_row, viewGroup, false);
        return new RecyclerObjects.YeastMyRecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.YeastMyRecipeViewHolder itemViewHolder, int i) {
        RecipeYeast item = recipeYeasts.get(i);
        itemViewHolder.my_yeast_Lab_text_view.setText(lookupLab(item.getLaboratoryId()));
        itemViewHolder.my_yeast_type_text_view.setText(item.getName() + ",");
    }

    @Override
    public int getItemCount() {
        return recipeYeasts.size();
    }

    private String lookupLab(int itemPk) {
        Laboratory unit = ((IngredientFragment) this._fragment).GetLaboratory(itemPk);
        return unit.getName();
    }

    public void addItemsToAdapter(List<RecipeYeast> modelsToAdd) {
        recipeYeasts.addAll(modelsToAdd);
        notifyDataSetChanged();
    }

    public RecipeYeast getItemAt(int postion) {
        return recipeYeasts.get(postion);
    }

    public int getPostionById(int itemId) {
        for (RecipeYeast item : recipeYeasts) {
            if (itemId == item.getIngredientYeastId()) {
                return recipeYeasts.indexOf(item);
            }
        }
        return 0;
    }
}

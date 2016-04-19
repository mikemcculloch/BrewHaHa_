package brightseer.com.brewhaha.Depricated;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.objects.HopsForm;
import brightseer.com.brewhaha.objects.HopsUse;
import brightseer.com.brewhaha.models.RecipeHop;
import brightseer.com.brewhaha.objects.RecyclerObjects;
import brightseer.com.brewhaha.objects.UnitOfMeasure;
import brightseer.com.brewhaha.recipe_fragments.IngredientFragment;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class HopsRecycler extends RecyclerView.Adapter<RecyclerObjects.HopMyRecipeViewHolder> {
    private List<RecipeHop> recipeHops = new Vector<>();
    private Fragment _fragment;

    public HopsRecycler(List<RecipeHop> jsonObject, Fragment fragment) {
        this.recipeHops = jsonObject;
        _fragment = fragment;
    }

    public void add(RecipeHop item, int position) {
        recipeHops.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        recipeHops.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void add(RecipeHop item) {
        recipeHops.add(item);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerObjects.HopMyRecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.recipe_hop_row, viewGroup, false);
        return new RecyclerObjects.HopMyRecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.HopMyRecipeViewHolder itemViewHolder, int i) {
        RecipeHop item = recipeHops.get(i);
//        itemViewHolder.my_hop_amount_text_view.setText(String.valueOf(item.getAmount()));
//        itemViewHolder.my_hop_time_text_view.setText(String.valueOf(item.getCookTime()) + " " + lookupUnitOfMeasure(item.getTimeUnitOfMeasureId(), 2) + ",");
//        itemViewHolder.my_hop_type_text_view.setText(item.getName() + ",");
//
//        itemViewHolder.my_hop_weight_text_view.setText(lookupUnitOfMeasure(item.getUnitOfMeasureId(), 1) + ",");
//
//        itemViewHolder.my_hop_use_text_view.setText(lookupHopUse(item.getHopsUseId()) + ",");
//        itemViewHolder.my_hop_form_text_view.setText(lookupHopForm(item.getHopsFormId()) + ",");
//        itemViewHolder.row_hop_alpha_text_view.setText(String.valueOf(item.getAlphaAcidPercentage()) + "%");
    }

    @Override
    public int getItemCount() {
        return recipeHops.size();
    }

    private String lookupUnitOfMeasure(int itemId, int type) {
        UnitOfMeasure unit = ((IngredientFragment) this._fragment).GetUnitOfMeasure(itemId, type);
        return unit.getDescription();
    }

    private String lookupHopUse(int itemId) {
        HopsUse unit = ((IngredientFragment) this._fragment).GetHopUse(itemId);
        return unit.getDescription();
    }

    private String lookupHopForm(int itemId) {
        HopsForm unit = ((IngredientFragment) this._fragment).GetHopForm(itemId);
        return unit.getDescription();
    }

    public void addItemsToAdapter(List<RecipeHop> modelsToAdd) {
        recipeHops.addAll(modelsToAdd);
        notifyDataSetChanged();
    }

    public RecipeHop getItemAt(int postion) {
        return recipeHops.get(postion);
    }

    public int getPostionById(int itemId) {
//        for (RecipeHop item : recipeHops) {
//            if (itemId == item.getIngredientHopsId()) {
//                return recipeHops.indexOf(item);
//            }
//        }
        return 0;
    }

}

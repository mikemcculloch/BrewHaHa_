package brightseer.com.brewhaha.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.fragment.AddHopsFragment;
import brightseer.com.brewhaha.objects.HopsForm;
import brightseer.com.brewhaha.objects.HopsUse;
import brightseer.com.brewhaha.models.RecipeHop;
import brightseer.com.brewhaha.objects.RecyclerObjects;
import brightseer.com.brewhaha.objects.UnitOfMeasure;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class HopMyRecipeRecycler extends RecyclerView.Adapter<RecyclerObjects.HopMyRecipeViewHolder> {
    private List<RecipeHop> jsonObjects = new Vector<>();
    private Fragment _fragment;

    public HopMyRecipeRecycler(List<RecipeHop> jsonObject, Fragment fragment) {
        this.jsonObjects = jsonObject;
        _fragment = fragment;
    }

    public void add(RecipeHop item, int position) {
        jsonObjects.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        jsonObjects.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void add(RecipeHop item) {
        jsonObjects.add(item);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerObjects.HopMyRecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row_my_hop, viewGroup, false);
        return new RecyclerObjects.HopMyRecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.HopMyRecipeViewHolder itemViewHolder, int i) {
//        RecipeHop item = jsonObjects.get(i);
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
        return jsonObjects.size();
    }

    private String lookupUnitOfMeasure(int itemPk, int type) {
        UnitOfMeasure unit = ((AddHopsFragment) this._fragment).lookupUnitOfMeasure(itemPk, type);
        return unit.getDescription();
    }

    private String lookupHopUse(int itemPk) {
        HopsUse unit = ((AddHopsFragment) this._fragment).lookupHopUse(itemPk);
        return unit.getDescription();
    }

    private String lookupHopForm(int itemPk) {
        HopsForm unit = ((AddHopsFragment) this._fragment).lookupHopForm(itemPk);
        return unit.getDescription();
    }

    public void addItemsToAdapter(List<RecipeHop> modelsToAdd) {
        jsonObjects.addAll(modelsToAdd);
        notifyDataSetChanged();
    }

    public RecipeHop getItemAt(int postion) {
        return jsonObjects.get(postion);
    }

    public int getPostionByPk(int itemPk) {
//        for (RecipeHop item : jsonObjects) {
//            if (itemPk == item.getIngredientHopsId()) {
//                return jsonObjects.indexOf(item);
//            }
//        }
        return 0;
    }

}

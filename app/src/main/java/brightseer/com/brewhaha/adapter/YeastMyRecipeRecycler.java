package brightseer.com.brewhaha.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.fragment.AddYeastFragment;
import brightseer.com.brewhaha.objects.IngredientYeast;
import brightseer.com.brewhaha.objects.Laboratory;
import brightseer.com.brewhaha.objects.RecyclerObjects;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class YeastMyRecipeRecycler extends RecyclerView.Adapter<RecyclerObjects.YeastMyRecipeViewHolder> {
    private List<IngredientYeast> jsonObjects = new Vector<>();
    private Fragment _fragment;

    public YeastMyRecipeRecycler(List<IngredientYeast> jsonObject, Fragment fragment) {
        this.jsonObjects = jsonObject;
        _fragment = fragment;
    }

    public void add(IngredientYeast item, int position) {
        jsonObjects.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        jsonObjects.remove(position);
        notifyItemRemoved(position);
    }

    public void add(IngredientYeast item) {
        jsonObjects.add(item);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerObjects.YeastMyRecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row_my_yeast, viewGroup, false);
        return new RecyclerObjects.YeastMyRecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.YeastMyRecipeViewHolder itemViewHolder, int i) {
        IngredientYeast item = jsonObjects.get(i);
        itemViewHolder.my_yeast_Lab_text_view.setText(lookupLab(item.getLaboratoryPk()));
        itemViewHolder.my_yeast_type_text_view.setText(item.getName() + ",");
//        itemViewHolder.my_yeast_atten_text_view.setText(String.valueOf(item.getAttenuationPercentage()));
    }

    @Override
    public int getItemCount() {
        return jsonObjects.size();
    }

    private String lookupLab(int itemPk) {
        Laboratory unit = ((AddYeastFragment) this._fragment).lookupLaboratory(itemPk);
        return unit.getName();
    }

    public void addItemsToAdapter(List<IngredientYeast> modelsToAdd) {
        jsonObjects.addAll(modelsToAdd);
        notifyDataSetChanged();
    }

    public IngredientYeast getItemAt(int postion) {
        return jsonObjects.get(postion);
    }

    public int getPostionByPk(int itemPk) {
        for (IngredientYeast item : jsonObjects) {
            if (itemPk == item.getIngredientYeastPk()) {
                return jsonObjects.indexOf(item);
            }
        }
        return 0;
    }
}

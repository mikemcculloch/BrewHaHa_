package brightseer.com.brewhaha.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.fragment.AddGrainsFragment;
import brightseer.com.brewhaha.objects.Country;
import brightseer.com.brewhaha.objects.GrainUse;
import brightseer.com.brewhaha.objects.IngredientGrain;
import brightseer.com.brewhaha.objects.RecyclerObjects;
import brightseer.com.brewhaha.objects.UnitOfMeasure;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class GrainMyRecipeRecycler extends RecyclerView.Adapter<RecyclerObjects.GrainMyRecipeViewHolder> {
    private List<IngredientGrain> jsonObjects = new Vector<>();
    private Fragment _fragment;

    public GrainMyRecipeRecycler(List<IngredientGrain> jsonObject, Fragment fragment) {
        this.jsonObjects = jsonObject;
        _fragment = fragment;
    }

    public void add(IngredientGrain item, int position) {
        jsonObjects.add(position, item);
        notifyItemInserted(position);
    }

    public void add(IngredientGrain item) {
        jsonObjects.add(item);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        jsonObjects.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerObjects.GrainMyRecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row_my_grain, viewGroup, false);
        return new RecyclerObjects.GrainMyRecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.GrainMyRecipeViewHolder itemViewHolder, int i) {
        IngredientGrain item = jsonObjects.get(i);
        itemViewHolder.my_grain_amount_text_view.setText(String.valueOf(item.getAmount()));
        itemViewHolder.my_grain_description_text_view.setText(item.getName() + ",");
        itemViewHolder.my_grain_weight_text_view.setText(lookupUnitOfMeasure(item.getUnitOfMeasurePk()) + ",");
        if (TextUtils.isEmpty(item.getHexColor())) {
            item.setHexColor("#fee799");
        }
        itemViewHolder.my_grain_color_image_view.setBackgroundColor(Color.parseColor(item.getHexColor()));

        itemViewHolder.row_grain_use_text_view.setText(lookupGrainUse(item.getGrainUsePk()));
        itemViewHolder.row_grain_country_text_view.setText(lookupCountry(item.getCountryPk()));
    }

    @Override
    public int getItemCount() {
        return jsonObjects.size();
    }

    private String lookupUnitOfMeasure(int itemPk) {
        UnitOfMeasure unit = ((AddGrainsFragment) this._fragment).lookupUnitOfMeasure(itemPk, 1);
        return unit.getDescription();
    }

    private String lookupGrainUse(int itemPk) {
        GrainUse unit = ((AddGrainsFragment) this._fragment).lookupGrainUse(itemPk);
        return unit.getDescription();
    }

    private String lookupCountry(int itemPk) {
        Country unit = ((AddGrainsFragment) this._fragment).lookupCountry(itemPk);
        return unit.getAbbreviation();
    }

    public void addItemsToAdapter(List<IngredientGrain> modelsToAdd) {
        jsonObjects.addAll(modelsToAdd);
        notifyDataSetChanged();
    }

    public IngredientGrain getItemAt(int postion) {
        return jsonObjects.get(postion);
    }

    public int getPostionByPk(int itemPk) {
        for (IngredientGrain item : jsonObjects) {
            if (itemPk == item.getIngredientGrainPk()) {
                return jsonObjects.indexOf(item);
            }
        }
        return 0;
    }

}

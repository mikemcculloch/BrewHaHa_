package brightseer.com.brewhaha.Depricated;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.objects.Country;
import brightseer.com.brewhaha.objects.GrainUse;
import brightseer.com.brewhaha.models.RecipeGrain;
import brightseer.com.brewhaha.objects.RecyclerObjects;
import brightseer.com.brewhaha.objects.UnitOfMeasure;
import brightseer.com.brewhaha.recipe_fragments.IngredientFragment;

/**
 * Created by Michael McCulloch on 3/2/2015.
 */
public class GrainRecycler extends RecyclerView.Adapter<RecyclerObjects.GrainMyRecipeViewHolder> {
    private List<RecipeGrain> recipeGrains = new Vector<>();
    private Fragment _fragment;

    public GrainRecycler(List<RecipeGrain> jsonObject, Fragment fragment) {
        this.recipeGrains = jsonObject;
        _fragment = fragment;
    }

    public void add(RecipeGrain item, int position) {
        recipeGrains.add(position, item);
        notifyItemInserted(position);
    }

    public void add(RecipeGrain item) {
        recipeGrains.add(item);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        recipeGrains.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerObjects.GrainMyRecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row_recipe_grain, viewGroup, false);
        return new RecyclerObjects.GrainMyRecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.GrainMyRecipeViewHolder itemViewHolder, int i) {
        RecipeGrain item = recipeGrains.get(i);
        itemViewHolder.my_grain_amount_text_view.setText(String.valueOf(item.getAmount()));
        itemViewHolder.my_grain_description_text_view.setText(item.getName() + ",");
//        itemViewHolder.my_grain_weight_text_view.setText(lookupUnitOfMeasure(item.getUnitOfMeasureId()) + ",");
        if (TextUtils.isEmpty(item.getHexColor())) {
            item.setHexColor("#fee799");
        }

        int newColor = Color.parseColor(item.getHexColor());
        Drawable mDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawable = _fragment.getContext().getResources().getDrawable(R.drawable.circle_srm, _fragment.getActivity().getTheme());
        }
        else{
            mDrawable = _fragment.getContext().getResources().getDrawable(R.drawable.circle_srm);
        }

        mDrawable.setColorFilter(new PorterDuffColorFilter(newColor, PorterDuff.Mode.MULTIPLY));
        itemViewHolder.my_grain_color_image_view.setImageDrawable(mDrawable);

//
//        itemViewHolder.row_grain_use_text_view.setText(lookupGrainUse(item.getGrainUseId()));
//        itemViewHolder.row_grain_country_text_view.setText(lookupCountry(item.getCountryId()));



//        CheckBox ingredient_checkbox = (CheckBox) theView.findViewById(R.id.ingredient_checkbox);
//        if (BrewSharedPrefs.getIsUserLoggedIn()) {
//            for (IngredientSelected appDataItem : _appDataList) {
//                if (appDataItem.getKey() == item.getKey() && appDataItem.getType() == Utilities.getIngredientTypeId(item.getType()) && _ContentPk == appDataItem.getRecipeContentId()) {
//                    ingredient_checkbox.setChecked(true);
//                }
//            }
//            ingredient_checkbox.setOnCheckedChangeListener(this);
//            ingredient_checkbox.setTag(position);
//        }
    }

    @Override
    public int getItemCount() {
        return recipeGrains.size();
    }

//    private String lookupUnitOfMeasure(int itemPk) {
//        UnitOfMeasure unit = ((IngredientFragment) this._fragment).GetUnitOfMeasure(itemPk, 1);
//        return unit.getDescription();
//    }
//
//    private String lookupGrainUse(int itemPk) {
//        GrainUse unit = ((IngredientFragment) this._fragment).GetGrainUse(itemPk);
//        return unit.getDescription();
//    }
//
//    private String lookupCountry(int itemPk) {
//        Country unit = ((IngredientFragment) this._fragment).GetCountry(itemPk);
//        return unit.getAbbreviation();
//    }

    public void addItemsToAdapter(List<RecipeGrain> modelsToAdd) {
        recipeGrains.addAll(modelsToAdd);
        notifyDataSetChanged();
    }

    public RecipeGrain getItemAt(int postion) {
        return recipeGrains.get(postion);
    }

    public int getPostionById(int itemId) {
//        for (RecipeGrain item : recipeGrains) {
//            if (itemId == item.getIngredientGrainId()) {
//                return recipeGrains.indexOf(item);
//            }
//        }
        return 0;
    }

}

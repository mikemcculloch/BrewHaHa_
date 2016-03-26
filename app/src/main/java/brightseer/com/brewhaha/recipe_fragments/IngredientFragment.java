package brightseer.com.brewhaha.recipe_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.objects.RecipeGrain;
import brightseer.com.brewhaha.objects.RecipeHop;
import brightseer.com.brewhaha.objects.RecipeYeast;

/**
 * Created by wooan on 3/21/2016.
 */
public class IngredientFragment extends BaseRecipeFragment {
    private View rootView;
    private List<RecipeGrain> recipeGrains = new Vector<>();
    private List<RecipeHop> recipeHops = new Vector<>();
    private List<RecipeYeast> recipeYeasts = new Vector<>();

    public static IngredientFragment newInstance(int centerX, int centerY, int color, List<RecipeGrain> grains, List<RecipeHop> hops, List<RecipeYeast> yeasts) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);

        args.putSerializable(Constants.bundleRecipeGrains, (Serializable) grains);
        args.putSerializable(Constants.bundleRecipeHops, (Serializable) hops);
        args.putSerializable(Constants.bundleRecipeYeasts, (Serializable) yeasts);

        IngredientFragment fragment = new IngredientFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe_ingredient, container, false);
        rootView = SetCircularReveal(rootView);
        rootView.setBackgroundColor(getArguments().getInt("color"));

        ReadBundle();
        return rootView;
    }


    private void ReadBundle() {
        recipeGrains = (List<RecipeGrain>) getArguments().getSerializable(Constants.bundleRecipeGrains);
        recipeHops = (List<RecipeHop>) getArguments().getSerializable(Constants.bundleRecipeHops);
        recipeYeasts = (List<RecipeYeast>) getArguments().getSerializable(Constants.bundleRecipeYeasts);
    }
}

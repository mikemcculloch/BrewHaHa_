package brightseer.com.brewhaha.recipe_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brightseer.com.brewhaha.R;

/**
 * Created by wooan on 3/21/2016.
 */
public class IngredientFragment extends BaseRecipeFragment {
    View rootView;

    public static IngredientFragment newInstance(int centerX, int centerY, int color) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
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
        return rootView;
    }
}

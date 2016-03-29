package brightseer.com.brewhaha.recipe_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.objects.RecipeSummary;

/**
 * Created by wooan on 3/21/2016.
 */
public class OverviewFragment extends BaseRecipeFragment {
    private View rootView;
    private RecipeSummary recipeSummary = new RecipeSummary();

    public OverviewFragment() {
    }

    public static OverviewFragment newInstance(int centerX, int centerY, int color, RecipeSummary recipeSummary) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
        args.putSerializable(Constants.bundleRecipeSummary, (Serializable) recipeSummary);
        OverviewFragment fragment = new OverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe_overview, container, false);
        rootView = SetCircularReveal(rootView);
//        rootView.setBackgroundColor(getArguments().getInt("color"));


        ReadBundle();
        return rootView;
    }

    private void ReadBundle() {
        recipeSummary = (RecipeSummary) getArguments().getSerializable(Constants.bundleRecipeSummary);
    }
}

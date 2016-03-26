package brightseer.com.brewhaha.recipe_fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.objects.Instruction;

/**
 * Created by wooan on 3/21/2016.
 */
public class DirectionFragment extends BaseRecipeFragment {
    private View rootView;
    private List<Instruction> recipeInstructions = new Vector<>();

    public DirectionFragment() {
    }

    public static DirectionFragment newInstance(int centerX, int centerY, int color, List<Instruction> instructions) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
        args.putSerializable(Constants.bundleRecipeInstructions, (Serializable) instructions);

        DirectionFragment fragment = new DirectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recipe_direction, container, false);
        rootView = SetCircularReveal(rootView);
        rootView.setBackgroundColor(getArguments().getInt("color"));

        ReadBundle();
        return  rootView;
    }

    private void ReadBundle() {
        recipeInstructions = (List<Instruction>) getArguments().getSerializable(Constants.bundleRecipeInstructions);
    }
}

package brightseer.com.brewhaha.recipe_fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brightseer.com.brewhaha.R;

/**
 * Created by wooan on 3/21/2016.
 */
public class DirectionFragment extends BaseRecipeFragment {
    View rootView;
    public DirectionFragment() {
    }

    public static DirectionFragment newInstance(int centerX, int centerY, int color) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
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
        return  rootView;
    }
}

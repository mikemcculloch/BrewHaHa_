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
import brightseer.com.brewhaha.objects.Comment;

/**
 * Created by Michael McCulloch on 2/26/2015.
 */
public class CommentFragment extends BaseRecipeFragment {
    private View rootView;
    private  List<Comment> recipeComents = new Vector<>();

    public CommentFragment() {
    }

    public static CommentFragment newInstance(int centerX, int centerY, int color, List<Comment> comments) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
        args.putSerializable(Constants.bundleRecipeComments, (Serializable) comments);

        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*Runs 1st*/
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*Runs 2nd*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe_comments, container, false);
        rootView = SetCircularReveal(rootView);
//        rootView.setBackgroundColor(getArguments().getInt("color"));

        ReadBundle();
        return rootView;
    }

    /*Runs 3rd*/
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReadBundle();
    }


    private void ReadBundle() {
        recipeComents = (List<Comment>) getArguments().getSerializable(Constants.bundleRecipeComments);
    }

}

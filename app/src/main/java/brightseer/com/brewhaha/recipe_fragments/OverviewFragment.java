package brightseer.com.brewhaha.recipe_fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.objects.RecipeSummary;

/**
 * Created by wooan on 3/21/2016.
 */
public class OverviewFragment extends BaseRecipeFragment {
    private View rootView;
    private RecipeSummary recipeSummary = new RecipeSummary();
    String recipeTitle, recipeDesctiption, authorImageUrl, recipeDateCreated, recipeDateModified;


    public OverviewFragment() {
    }

    public static OverviewFragment newInstance(int centerX, int centerY, int color, RecipeSummary recipeSummary, String recipeTitle, String recipeDesctiption,
                                               String authorImageUrl, String recipeDateCreated, String recipeDateModified) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
        args.putSerializable(Constants.bundleRecipeSummary, recipeSummary);

        args.putString(Constants.exRecipeTitle, recipeTitle);
        args.putString(Constants.exRecipeDesctiption, recipeDesctiption);

        args.putString(Constants.exAuthorImageUrl, authorImageUrl);
        args.putString(Constants.exRecipeDateCreated, recipeDateCreated);
        args.putString(Constants.exRecipeDateModified, recipeDateModified);

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
        initViews();
        return rootView;
    }

    private void ReadBundle() {
        recipeSummary = (RecipeSummary) getArguments().getSerializable(Constants.bundleRecipeSummary);

        recipeTitle = getArguments().getString(Constants.exRecipeTitle);
        recipeDesctiption = getArguments().getString(Constants.exRecipeDesctiption);
        authorImageUrl = getArguments().getString(Constants.exAuthorImageUrl);
        recipeDateCreated = getArguments().getString(Constants.exRecipeDateCreated);
        recipeDateModified = getArguments().getString(Constants.exRecipeDateModified);
    }

    private void initViews() {
        TextView recipe_title_text_view = (TextView) rootView.findViewById(R.id.recipe_title_text_view);
        recipe_title_text_view.setText(recipeTitle);

        TextView recipe_description_text_view = (TextView) rootView.findViewById(R.id.recipe_description_text_view);
        recipe_description_text_view.setText(recipeDesctiption);

        TextView original_gravity_text_view = (TextView) rootView.findViewById(R.id.original_gravity_text_view);
        original_gravity_text_view.setText(recipeSummary.getOriginalGravity());

        TextView final_gravity_text_view = (TextView) rootView.findViewById(R.id.final_gravity_text_view);
        final_gravity_text_view.setText(recipeSummary.getFinalGravity());

        TextView bitterness_text_view = (TextView) rootView.findViewById(R.id.bitterness_text_view);
        bitterness_text_view.setText(recipeSummary.getBitternessIbu());

        TextView srm_color_text_view = (TextView) rootView.findViewById(R.id.srm_color_text_view);
        srm_color_text_view.setText(recipeSummary.getColorSrm());

        TextView abv_text_view = (TextView) rootView.findViewById(R.id.abv_text_view);
        abv_text_view.setText(recipeSummary.getAlcoholByVol() + "%");

        int newColor = Color.parseColor(recipeSummary.SrmHex);
        Drawable mDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawable = getContext().getResources().getDrawable(R.drawable.circle_srm, getActivity().getTheme());
        }
        else{
            mDrawable = getContext().getResources().getDrawable(R.drawable.circle_srm);
        }

        mDrawable.setColorFilter(new PorterDuffColorFilter(newColor, PorterDuff.Mode.MULTIPLY));
        ImageView circle_srm_image_view = (ImageView) rootView.findViewById(R.id.circle_srm_image_view);
        circle_srm_image_view.setImageDrawable(mDrawable);
    }
}

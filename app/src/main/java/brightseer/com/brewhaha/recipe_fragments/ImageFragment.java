package brightseer.com.brewhaha.recipe_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.LinePageIndicator;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.adapter.RecipeImageRecycler;
import brightseer.com.brewhaha.objects.RecipeImage;
import brightseer.com.brewhaha.recipe_adapters.ImagesAdapter;

/**
 * Created by Michael McCulloch on 2/26/2015.
 */
public class ImageFragment extends BaseRecipeFragment {
    private View rootView;
    private List<RecipeImage> recipeImages = new Vector<>();
    private RecyclerView recycler_view_recipe_images;
    private RecipeImageRecycler recipeImageRecycler;

    private ViewPager recipe_image_view_pager;
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    public ImageFragment() {
    }

    public static ImageFragment newInstance(int centerX, int centerY, int color, List<RecipeImage> recipeImages) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
        args.putSerializable(Constants.bundleRecipeImages, (Serializable) recipeImages);

        ImageFragment fragment = new ImageFragment();
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
        rootView = inflater.inflate(R.layout.fragment_recipe_images, container, false);
        rootView = SetCircularReveal(rootView);
//        rootView.setBackgroundColor(getArguments().getInt("color"));

        ReadBundle();
        initViewPager();
//        initRecyclerView();
        return rootView;
    }

    /*Runs 3rd*/
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReadBundle();
    }


    private void ReadBundle() {
        recipeImages = (List<RecipeImage>) getArguments().getSerializable(Constants.bundleRecipeImages);
    }

    private void initRecyclerView() {
        recycler_view_recipe_images = (RecyclerView) rootView.findViewById(R.id.recycler_view_recipe_images);
        recycler_view_recipe_images.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);

        recycler_view_recipe_images.setLayoutManager(layoutManager);

        recipeImageRecycler = new RecipeImageRecycler(recipeImages);

        recycler_view_recipe_images.setAdapter(recipeImageRecycler);

//        recycler_view_recipe_images.addOnItemTouchListener(
//                new RecyclerItemClickListener(this, recycler_view_recipe_images, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        try {
//                            showImage(recipeImages.get(position));
//                        } catch (Exception e) {
//                            if (BuildConfig.DEBUG) {
//                                Log.e(Constants.LOG, e.getMessage());
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onItemLongClick(View view, int position) {
////                        selectedImagePk = recipeImageList.get(position).getImagePk();
////                        deletePosition = position;
////                        menuType = 2;
////                        registerForContextMenu(view);
////                        getActivity().openContextMenu(view);
////                        view.setLongClickable(false);
//
//                    }
//                })
//        );
    }

    private void initViewPager() {
        try {
            recipe_image_view_pager = (ViewPager) rootView.findViewById(R.id.recipe_image_view_pager);
            recipe_image_view_pager.setPageTransformer(false, new ViewPager.PageTransformer() {
                @Override
                public void transformPage(View view, float position) {
                    int pageWidth = view.getWidth();
                    int pageHeight = view.getHeight();

                    if (position < -1) { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        view.setAlpha(0);

                    } else if (position <= 1) { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                        float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                        float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                        if (position < 0) {
                            view.setTranslationX(horzMargin - vertMargin / 2);
                        } else {
                            view.setTranslationX(-horzMargin + vertMargin / 2);
                        }

                        // Scale the page down (between MIN_SCALE and 1)
                        view.setScaleX(scaleFactor);
                        view.setScaleY(scaleFactor);

                        // Fade the page relative to its size.
                        view.setAlpha(MIN_ALPHA +
                                (scaleFactor - MIN_SCALE) /
                                        (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                    } else { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        view.setAlpha(0);
                    }
                }
            });

            ImagesAdapter recipe_image_adapter = new ImagesAdapter(getActivity(), recipeImages);

            recipe_image_view_pager.setAdapter(recipe_image_adapter);
            recipe_image_view_pager.setCurrentItem(0);

            LinePageIndicator view_page_indicator = (LinePageIndicator) rootView.findViewById(R.id.view_page_indicator);
            view_page_indicator.setViewPager(recipe_image_view_pager);
        } catch (Exception ex) {
        }
    }
}

package brightseer.com.brewhaha.recipe_fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.viewpagerindicator.LinePageIndicator;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.RecipeImage;
import brightseer.com.brewhaha.recipe_adapters.ImagesAdapter;

/**
 * Created by Michael McCulloch on 2/26/2015.
 */
public class ImageFragment extends BaseRecipeFragment {
    private View rootView;
    private List<RecipeImage> recipeImages = new Vector<>();
    private String feedKey;
    private Firebase rootRef;
    private ViewPager recipe_image_view_pager;
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    public ImageFragment() {
    }

    public static ImageFragment newInstance(int centerX, int centerY, int color, String _feedKey) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
//        args.putSerializable(Constants.bundleRecipeImages, (Serializable) recipeImages);

        args.putString(Constants.fbFeedKey, _feedKey);

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
        rootView = Utilities.SetCircularReveal(rootView, this, 500);
//        rootView.setBackgroundColor(getArguments().getInt("color"));


        ReadBundle();
        initFirebaseDb();
//        addTestImages();

        GetImages();
        return rootView;
    }

    private void initFirebaseDb() {
        rootRef = new Firebase(Constants.fireBaseRoot).child(Constants.fbImages).child(feedKey);
    }

    private void ReadBundle() {
        feedKey = getArguments().getString(Constants.fbFeedKey);
    }

    public void addTestImages() {
        try {
            ///ADD NEW RecipeGrain//////////////////

            RecipeImage recipeImage = new RecipeImage();
            recipeImage.setCaption("fart smell");
            recipeImage.setImageName("PoopStick");
            recipeImage.setImageUrl("http://www.paphosbeerfestival.com/assets/images/bg_beer.jpg");
            rootRef.push().setValue(recipeImage);

            recipeImage = new RecipeImage();
            recipeImage.setCaption("stink or pink");
            recipeImage.setImageName("pink");
            recipeImage.setImageUrl("http://wholesalegourmet.net/images/AA2254.jpg");
            rootRef.push().setValue(recipeImage);

            recipeImage = new RecipeImage();
            recipeImage.setCaption("stink or pink");
            recipeImage.setImageName("pink");
            recipeImage.setImageUrl("https://image.spreadshirtmedia.net/image-server/v1/designs/15687130,width=178,height=178,version=1373472021/Sex-And-Beer-Drinking-Team.png");
            rootRef.push().setValue(recipeImage);

            //////////////////
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void GetImages() {
        try {
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        RecipeImage recipeImage = postSnapshot.getValue(RecipeImage.class);
                        recipeImages.add(recipeImage);
                    }
                    initViewPager();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
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
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }
}

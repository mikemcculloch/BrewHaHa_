package brightseer.com.brewhaha;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.plus.PlusOneButton;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.BitmapInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.adapter.RecipeImageRecycler;
import brightseer.com.brewhaha.adapter.RecyclerItemClickListener;
import brightseer.com.brewhaha.helper.AnimatorPath;
import brightseer.com.brewhaha.helper.PathEvaluator;
import brightseer.com.brewhaha.helper.PathPoint;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.objects.Comment;
import brightseer.com.brewhaha.objects.Instruction;
import brightseer.com.brewhaha.objects.RecipeGrain;
import brightseer.com.brewhaha.objects.RecipeHop;
import brightseer.com.brewhaha.objects.RecipeImage;
import brightseer.com.brewhaha.objects.RecipeItem;
import brightseer.com.brewhaha.objects.RecipeSummary;
import brightseer.com.brewhaha.objects.RecipeYeast;
import brightseer.com.brewhaha.recipe_fragments.CommentFragment;
import brightseer.com.brewhaha.recipe_fragments.DirectionFragment;
import brightseer.com.brewhaha.recipe_fragments.IngredientFragment;
import brightseer.com.brewhaha.recipe_fragments.OverviewFragment;
import brightseer.com.brewhaha.repository.JsonToObject;

/**
 * Created by wooan on 10/24/2015.
 */
public class RecipeCardsActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private FloatingActionButton fabEdit;
    private int sceneId, sceneIdLast = 0;

    private int imageCount = 0, contentItemPk;
    private String recipeTitle, author, datePosted, authorImageUrl;
    private ImageView recipe_header_user_image_view;
    private TextView recipe_author_text_view, recipe_title_text_view, recipe_date_posted;

    private NestedScrollView nestedscrollview;
    private ViewGroup scene_target;
    private boolean exitActivity = true, toggleSceneButtons = false, curveDir = true;
    private PlusOneButton mPlusOneButton;

    private TextView textview_abv_overview;

    //    BottomSheetLayout bottomSheetLayout;
    private AppCompatButton card_overview, card_ingredients, card_directions, card_comments;


    private RecyclerView recycler_view_recipe_images;
    private RecipeImageRecycler recipeImageRecycler;
    private List<RecipeImage> recipeImageList = new Vector<>();

    private String userToken, recipeToken;

    private RecipeSummary recipeSummary = new RecipeSummary();
    private List<RecipeGrain> recipeGrains = new Vector<>();
    private List<RecipeHop> recipeHops = new Vector<>();
    private List<RecipeYeast> recipeYeasts = new Vector<>();
    private List<Comment> recipeComents = new Vector<>();
    private List<Instruction> recipeInstructions = new Vector<>();
    private List<RecipeImage> recipeImages = new Vector<>();

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        goToSceneOverView(card_overview);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setupTransistionSlide();
//        setupTransistion();
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_recipe_cards);
            _mContext = RecipeCardsActivity.this;
            initExtras();
            initRecyclerView();
            initViews();
            initPrefs();
            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_overview:
                if (toggleSceneButtons)
                    goToSceneOverView(v);
                break;
            case R.id.card_ingredients:
                if (toggleSceneButtons)
                    goToSceneIngredients(v);
                break;
            case R.id.card_directions:
                if (toggleSceneButtons)
                    goToSceneDirections(v);
                break;
            case R.id.card_comments:
                if (toggleSceneButtons) {
                    goToSceneComments(v);
                }
                break;

            case R.id.fabEdit:
                openEditOption();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(adapterPosition);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            finish();
        }

    }

    private void initViews() {
        try {
            toolbar = (Toolbar) findViewById(R.id.standard_toolbar);
            setSupportActionBar(toolbar);
            if (toolbar != null) {
                toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            }

            nestedscrollview = (NestedScrollView) findViewById(R.id.nestedscrollview);
            scene_target = (ViewGroup) findViewById(R.id.scene_target);

            mPlusOneButton = (PlusOneButton) findViewById(R.id.plus_one_button);

            recipe_header_user_image_view = (ImageView) findViewById(R.id.recipe_header_user_image_view);
            imageTransition(recipe_header_user_image_view, authorImageUrl, Constants.exBitMapInfo);

            recipe_author_text_view = (TextView) findViewById(R.id.recipe_author_text_view);
            recipe_author_text_view.setText(author);
            ViewCompat.setTransitionName(recipe_author_text_view, getResources().getString(R.string.transition_username));

            recipe_title_text_view = (TextView) findViewById(R.id.recipe_title_text_view);
            recipe_title_text_view.setText(recipeTitle);
            ViewCompat.setTransitionName(recipe_title_text_view, getResources().getString(R.string.transition_title));

            recipe_date_posted = (TextView) findViewById(R.id.recipe_date_posted);
            recipe_date_posted.setText(Utilities.DisplayTimeFormater(datePosted));
            ViewCompat.setTransitionName(recipe_date_posted, getResources().getString(R.string.transition_userdate));

            fabEdit = (FloatingActionButton) findViewById(R.id.fabEdit);
            fabEdit.setOnClickListener(this);

//            bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);

            card_overview = (AppCompatButton) findViewById(R.id.card_overview);
            card_overview.setOnClickListener(this);
            card_ingredients = (AppCompatButton) findViewById(R.id.card_ingredients);
            card_ingredients.setOnClickListener(this);
            card_directions = (AppCompatButton) findViewById(R.id.card_directions);
            card_directions.setOnClickListener(this);
            card_comments = (AppCompatButton) findViewById(R.id.card_comments);
            card_comments.setOnClickListener(this);


            textview_abv_overview = (TextView) findViewById(R.id.textview_abv_overview);
            textview_abv_overview.setText("69");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initExtras() {
        Intent activityThatCalled = getIntent();

        recipeToken = activityThatCalled.getExtras().getString(Constants.exRecipeToken);
        recipeToken = "8A87F5C2-3F08-4DBB-B8C1-CC0EC9DA011E";

        contentItemPk = activityThatCalled.getExtras().getInt(Constants.exContentItemPk);
        recipeTitle = activityThatCalled.getExtras().getString(Constants.exRecipeTitle);
        adapterPosition = activityThatCalled.getExtras().getInt(Constants.exPosition);
        author = activityThatCalled.getExtras().getString(Constants.exUsername);
        datePosted = activityThatCalled.getExtras().getString(Constants.exUserdate);
        authorImageUrl = activityThatCalled.getExtras().getString(Constants.exAuthorImage);
    }

    private void initPrefs() {
        userToken = BrewSharedPrefs.getUserToken();
        if (TextUtils.isEmpty(userToken)) {
            userToken = "na";
        }
    }

    private void loadData() {
        String contentUrl = Constants.GetRecipeItemUrl + recipeToken + "/" + userToken;
        Ion.with(_mContext)
                .load(contentUrl)
                .setHeader("Cache-Control", "No-Cache")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {

                                 @Override
                                 public void onCompleted(Exception e, JsonObject result) {
                                     try {
                                         if (result != null) {
                                             RecipeItem recipeContent = JsonToObject.JsonToRecipeItem(result);

                                             recipeGrains = recipeContent.getRecipeGrains();
                                             recipeHops = recipeContent.getRecipeHops();
                                             recipeYeasts = recipeContent.getRecipeYeasts();
                                             recipeSummary = recipeContent.getRecipeSummary();
                                             recipeComents = recipeContent.getComments();
                                             recipeInstructions = recipeContent.getInstructions();
                                             recipeImages = recipeContent.getRecipeImage();
                                             if (recipeImages == null)
                                                 recipeImages = new Vector<>();

                                             loadRecipeImages();
                                             goToSceneOverView(findViewById(R.id.card_overview));
                                             toggleSceneButtons = true;
                                         }
                                     } catch (Exception ex) {
                                         if (BuildConfig.DEBUG) {
                                             Log.e(Constants.LOG, e.getMessage());
                                             Log.e(Constants.LOG, ex.getMessage());
                                         }
                                     }
                                 }
                             }

                );
    }

    private void loadRecipeImages() {
        RecipeImage test = new RecipeImage();
        test.setImageUrl("http://www.brewhaha.beer/Content/images/banner.jpg");
        recipeImages.add(test);
        recipeImages.add(test);
        recipeImages.add(test);
        recipeImages.add(test);
        recipeImages.add(test);
        recipeImages.add(test);
        recipeImages.add(test);
        recipeImages.add(test);
        recipeImages.add(test);
        recipeImages.add(test);

        for (RecipeImage item : recipeImages) {
            recipeImageRecycler.add(item, recipeImages.size() - 1);
        }
    }

    private void initRecyclerView() {
        recycler_view_recipe_images = (RecyclerView) findViewById(R.id.recycler_view_recipe_images);
        recycler_view_recipe_images.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);

        recycler_view_recipe_images.setLayoutManager(layoutManager);

        List<RecipeImage> placeHolder = new Vector<>();
        recipeImageRecycler = new RecipeImageRecycler(placeHolder, RecipeCardsActivity.this);

        recycler_view_recipe_images.setAdapter(recipeImageRecycler);

        recycler_view_recipe_images.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recycler_view_recipe_images, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            showImage(recipeImageList.get(position));
                        } catch (Exception e) {
                            if (BuildConfig.DEBUG) {
                                Log.e(Constants.LOG, e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                        selectedImagePk = recipeImageList.get(position).getImagePk();
//                        deletePosition = position;
//                        menuType = 2;
//                        registerForContextMenu(view);
//                        getActivity().openContextMenu(view);
//                        view.setLongClickable(false);

                    }
                })
        );
    }

    public void goToSceneOverView(View view) {
        exitActivity = false;
        sceneId = Constants.sceneOverview;
        moveButton(view);
    }

    public void goToSceneIngredients(View view) {
        exitActivity = false;
        sceneId = Constants.sceneIngredients;
        moveButton(view);
    }

    public void goToSceneDirections(View view) {
        exitActivity = false;
        sceneId = Constants.sceneDirections;
        moveButton(view);
    }

    public void goToSceneComments(View view) {
        exitActivity = false;
        sceneId = Constants.sceneComments;
        moveButton(view);
    }

    /**
     * Toggles button location on click between top-left and bottom-right
     */
    private void moveButton(View view) {
        try {
            toggleSceneButtons = false;
            setLayoutParam(view);
            curveMotion(view, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void curveMotion(final View view, final boolean animateEnd) {
        try {
            final int oldLeft = view.getLeft();
            final int oldTop = view.getTop();
            view.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {
                        public boolean onPreDraw() {
                            view.getViewTreeObserver().removeOnPreDrawListener(this);

                            String buttonLocation = "buttonLoc";
                            // Capture new location
                            int left = view.getLeft();
                            int top = view.getTop();
                            int deltaX = left - oldLeft;
                            int deltaY = top - oldTop;

                            // Set up path to new location using a B�zier spline curve
                            AnimatorPath path = new AnimatorPath();
                            path.moveTo(-deltaX, -deltaY);

                            float one = -(deltaX / 2);
                            float two = -deltaY;
                            float three = 0;
                            float four = -deltaY / 2;

//                            if (curveDir) {
//                              path.curveTo(-(deltaX / 2), -deltaY, 0, -deltaY / 2, 0, 0);
                            path.curveTo(-(deltaX / 2), -deltaY, 0, -deltaY / 2, 0, 0);
//                            } else {
//                            }
//                            curveDir = !curveDir;

                            // Set up the animation
                            ObjectAnimator anim = ObjectAnimator.ofObject(
                                    RecipeCardsActivity.this, buttonLocation,
                                    new PathEvaluator(), path.getPoints().toArray());

                            anim.setInterpolator(new AccelerateDecelerateInterpolator());
                            anim.setDuration(300);
                            if (animateEnd)
                                anim.addListener(animatorSceneEnter);
                            anim.start();
                            return true;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * We need this setter to translate between the information the animator
     * produces (a new "PathPoint" describing the current animated location)
     * and the information that the button requires (an xy location). The
     * setter will be called by the ObjectAnimator given the 'buttonLoc'
     * property string.
     */
    public void setButtonLoc(PathPoint newLoc) {
        View view = null;
        if (sceneId == Constants.sceneOverview) {
            view = findViewById(R.id.card_overview);
        }
        if (sceneId == Constants.sceneIngredients) {
            view = findViewById(R.id.card_ingredients);
        }
        if (sceneId == Constants.sceneDirections) {
            view = findViewById(R.id.card_directions);
        }
        if (sceneId == Constants.sceneComments) {
            view = findViewById(R.id.card_comments);
        }

        view.setTranslationX(newLoc.mX);
        view.setTranslationY(newLoc.mY);
    }

    private Animator.AnimatorListener animatorSceneEnter = new Animator.AnimatorListener() {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onAnimationStart(Animator animation) {
//            View view = null;
//            if (sceneId == R.layout.scene_comments) {
//                view = card_comments;
//
////                view.setBackground(getDrawable(R.drawable.round_button));
//
////
////                ChangeBounds changeBounds = new ChangeBounds();
////                changeBounds.setResizeClip(true);
////                changeBounds.setDuration(500);
////                TransitionValues tv = new TransitionValues();
////                changeBounds.createAnimator(view, tv, tv);
////
////view.startAnimation(changeBounds);
////                ScaleAnimation scale = new ScaleAnimation((float) 1.0, (float) 1.5, (float) 1.0, (float) 1.5);
////                scale.setFillAfter(false);
////                scale.setDuration(1000);
////                view.startAnimation(scale);
//
//
////                RotateAnimation r = new RotateAnimation(0f, -90f,200,200); // HERE
//////                r.setStartOffset(1000);
////                r.setDuration(500);
////                view.startAnimation(r);
//            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                View view = null;
                int randomColor = Color.argb(255, (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
                Fragment fragment = null;
                if (sceneId == Constants.sceneOverview) {
                    view = card_overview;
                    fragment = OverviewFragment.newInstance(20, 20, randomColor, recipeSummary);
                }

                if (sceneId == Constants.sceneIngredients) {
                    view = card_ingredients;
                    fragment = IngredientFragment.newInstance(20, 20, randomColor, recipeGrains, recipeHops, recipeYeasts);
                }

                if (sceneId == Constants.sceneDirections) {
                    view = card_directions;
                    fragment = DirectionFragment.newInstance(20, 20, randomColor, recipeInstructions, recipeToken);
                }

                if (sceneId == Constants.sceneComments) {
                    view = card_comments;
                    fragment = CommentFragment.newInstance(20, 20, randomColor, recipeComents);
                }

                getSupportFragmentManager().beginTransaction().add(R.id.scene_target, fragment).commit();

                view.setVisibility(View.INVISIBLE);

                toggleSceneButtons = true;
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private void imageTransition(final ImageView imageview_holder, final String urlImage, String bitMapInfo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            String bitmapKey = getIntent().getStringExtra(bitMapInfo);
            BitmapInfo bi = Ion.getDefault(this)
                    .getBitmapCache()
                    .get(bitmapKey);

            if (bi == null)
                return;

            imageview_holder.setImageBitmap(bi.bitmap);

            getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                }

                @Override
                public void onTransitionPause(Transition transition) {
                }

                @Override
                public void onTransitionResume(Transition transition) {
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().getEnterTransition().removeListener(this);
                    }

                    // load the full version, crossfading from the thumbnail image
                    Ion.with(imageview_holder)
                            .crossfade(true)
                            .transform(trans)
                            .load(urlImage);
                }
            });
        } else {
            Ion.with(imageview_holder)
                    .centerCrop()
                    .transform(trans)
                    .load(urlImage);
        }
    }

    private void openEditOption() {
        try {
//            MenuSheetView menuSheetView =
//                    new MenuSheetView(RecipeCardsActivity.this, MenuSheetView.MenuType.LIST, "Create...", new MenuSheetView.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            Toast.makeText(RecipeCardsActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
//                            if (bottomSheetLayout.isSheetShowing()) {
//                                bottomSheetLayout.dismissSheet();
//                            }
//                            return true;
//                        }
//                    });
//            menuSheetView.inflateMenu(R.menu.menu_bottom_sheet);
//            bottomSheetLayout.showWithSheetView(menuSheetView);
        } catch (Exception ex) {
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recipeTitle != null) {
            String URL = Constants.urlBrewHahaContent + recipeTitle.replace(" ", "-");
            // Refresh the state of the +1 button each time the activity receives focus.
            mPlusOneButton.initialize(URL, PLUS_ONE_REQUEST_CODE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setLayoutParam(View view) {
        try {
            LayoutParams mainLayoutParam = (LayoutParams) view.getLayoutParams();
//            LayoutParams childLayoutParam;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mainLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mainLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_START);
            mainLayoutParam.addRule(RelativeLayout.BELOW, R.id.recycler_view_recipe_images);
            mainLayoutParam.setMarginStart((int) getResources().getDimension(R.dimen.full_margin));
            resetLayouts();

            if (sceneId == Constants.sceneOverview) {
            }
            if (sceneId == Constants.sceneIngredients) {
                mainLayoutParam.removeRule(RelativeLayout.END_OF);
                mainLayoutParam.removeRule(RelativeLayout.RIGHT_OF);
            }
            if (sceneId == Constants.sceneDirections) {
                mainLayoutParam.removeRule(RelativeLayout.LEFT_OF);
                mainLayoutParam.removeRule(RelativeLayout.START_OF);
            }
            if (sceneId == Constants.sceneComments) {
                mainLayoutParam.removeRule(RelativeLayout.ALIGN_PARENT_END);
                mainLayoutParam.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }

            view.setLayoutParams(mainLayoutParam);
            sceneIdLast = sceneId;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void resetLayouts() {
        LayoutParams mainLayoutParam;
        if (sceneIdLast == Constants.sceneOverview) {
            mainLayoutParam = (LayoutParams) card_overview.getLayoutParams();
            mainLayoutParam.removeRule(RelativeLayout.BELOW);
            mainLayoutParam.setMarginStart(0);

            card_overview.setLayoutParams(mainLayoutParam);
            card_overview.setVisibility(View.VISIBLE);
//            curveMotion(card_overview, false);
        }
        if (sceneIdLast == Constants.sceneIngredients) {
            mainLayoutParam = (LayoutParams) card_ingredients.getLayoutParams();

            mainLayoutParam.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mainLayoutParam.removeRule(RelativeLayout.ALIGN_PARENT_START);
            mainLayoutParam.removeRule(RelativeLayout.BELOW);
            mainLayoutParam.setMarginStart(0);
            mainLayoutParam.addRule(RelativeLayout.END_OF, R.id.card_overviewHolder);
            mainLayoutParam.addRule(RelativeLayout.RIGHT_OF, R.id.card_overviewHolder);


            card_ingredients.setLayoutParams(mainLayoutParam);
            card_ingredients.setVisibility(View.VISIBLE);
        }

        if (sceneIdLast == Constants.sceneDirections) {
            mainLayoutParam = (LayoutParams) card_directions.getLayoutParams();


            mainLayoutParam.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mainLayoutParam.removeRule(RelativeLayout.ALIGN_PARENT_START);
            mainLayoutParam.removeRule(RelativeLayout.BELOW);
            mainLayoutParam.setMarginStart(0);
            mainLayoutParam.addRule(RelativeLayout.LEFT_OF, R.id.card_commentsHolder);
            mainLayoutParam.addRule(RelativeLayout.START_OF, R.id.card_commentsHolder);

            card_directions.setLayoutParams(mainLayoutParam);
            card_directions.setVisibility(View.VISIBLE);
        }

        if (sceneIdLast == Constants.sceneComments) {
            card_comments.setVisibility(View.VISIBLE);
            mainLayoutParam = (LayoutParams) card_comments.getLayoutParams();

            mainLayoutParam.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mainLayoutParam.removeRule(RelativeLayout.ALIGN_PARENT_START);
            mainLayoutParam.removeRule(RelativeLayout.BELOW);
            mainLayoutParam.setMarginStart(0);
            mainLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_END);
            mainLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            card_comments.setLayoutParams(mainLayoutParam);
            card_comments.setVisibility(View.VISIBLE);
        }
    }
}

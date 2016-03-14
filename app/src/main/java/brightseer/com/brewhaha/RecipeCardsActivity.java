package brightseer.com.brewhaha;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.plus.PlusOneButton;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.BitmapInfo;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.adapter.RecipeImageRecycler;
import brightseer.com.brewhaha.adapter.RecyclerItemClickListener;
import brightseer.com.brewhaha.helper.AnimatorPath;
import brightseer.com.brewhaha.helper.PathEvaluator;
import brightseer.com.brewhaha.helper.PathPoint;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.objects.Image;

//import com.flipboard.bottomsheet.BottomSheetLayout;
//import com.flipboard.bottomsheet.commons.MenuSheetView;

/**
 * Created by wooan on 10/24/2015.
 */
public class RecipeCardsActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private FloatingActionButton fabEdit;
    private Scene sceneOverView;
    private Scene sceneIngredients;
    private Scene sceneDirections;
    private Scene sceneComments;
    private int sceneId, sceneIdLast = 0;

    private int imageCount = 0, contentItemPk;
    private String recipeTitle, author, datePosted, authorImageUrl;
    private ImageView recipe_header_user_image_view;
    private TextView recipe_author_text_view, recipe_title_text_view, recipe_date_posted;

    private NestedScrollView nestedscrollview;
    private ViewGroup scene_target;
    private boolean exitActivity = true, toggleSceneButtons = true, curveDir = true;
    private PlusOneButton mPlusOneButton;

    //    BottomSheetLayout bottomSheetLayout;
    AppCompatButton card_overview, card_ingredients, card_directions, card_comments;


    RecyclerView recycler_view_recipe_images;
    RecipeImageRecycler recipeImageRecycler;
    private List<Image> imageList = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupTransistionSlide();
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_recipe_cards);

            _mContext = this;
            initExtras();
            initRecyclerView();
            initViews();
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
                    sceneId = R.layout.scene_comments;
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
//        if (exitActivity) {
        super.onBackPressed();
//        } else {
////            goToSceneCardView(nestedscrollview);
//        }
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

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//    private void sceneEnter() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            sceneCards = Scene.getSceneForLayout(scene_target,
//                    R.layout.scene_cards, this);
//
//            sceneOverView = Scene.getSceneForLayout(scene_target,
//                    R.layout.scene_overview, this);
//
//            sceneIngredients = Scene.getSceneForLayout(scene_target,
//                    R.layout.scene_ingredients, this);
//
//            sceneDirections = Scene.getSceneForLayout(scene_target,
//                    R.layout.scene_directions, this);
//
//            sceneComments = Scene.getSceneForLayout(scene_target,
//                    R.layout.scene_comments, this);
//
//            sceneCards.enter();
//        }
//    }

    private void initExtras() {
        Intent activityThatCalled = getIntent();
        contentItemPk = activityThatCalled.getExtras().getInt(Constants.exContentItemPk);
        recipeTitle = activityThatCalled.getExtras().getString(Constants.exRecipeTitle);
        adapterPosition = activityThatCalled.getExtras().getInt(Constants.exPosition);
        author = activityThatCalled.getExtras().getString(Constants.exUsername);
        datePosted = activityThatCalled.getExtras().getString(Constants.exUserdate);
        authorImageUrl = activityThatCalled.getExtras().getString(Constants.exAuthorImage);
    }

    private void loadData() {

        Image test = new Image();
        test.setImageUrl("http://www.brewhaha.beer/Content/images/banner.jpg");
        imageList.add(test);
        imageList.add(test);
        imageList.add(test);
        imageList.add(test);
        imageList.add(test);
        imageList.add(test);
        imageList.add(test);
        imageList.add(test);
        imageList.add(test);
        imageList.add(test);

        for (Image item : imageList) {
            recipeImageRecycler.add(item, imageList.size() - 1);
        }
    }

    private void initRecyclerView() {
        recycler_view_recipe_images = (RecyclerView) findViewById(R.id.recycler_view_recipe_images);
        recycler_view_recipe_images.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);

        recycler_view_recipe_images.setLayoutManager(layoutManager);

        List<Image> placeHolder = new Vector<>();
        recipeImageRecycler = new RecipeImageRecycler(placeHolder, RecipeCardsActivity.this);

        recycler_view_recipe_images.setAdapter(recipeImageRecycler);

        recycler_view_recipe_images.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recycler_view_recipe_images, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            showImage(imageList.get(position));
                        } catch (Exception e) {
                            if (BuildConfig.DEBUG) {
                                Log.e(Constants.LOG, e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                        selectedImagePk = imageList.get(position).getImagePk();
//                        deletePosition = position;
//                        menuType = 2;
//                        registerForContextMenu(view);
//                        getActivity().openContextMenu(view);
//                        view.setLongClickable(false);

                    }
                })
        );


    }

//    public void goToSceneCardView(View view) {
//        exitActivity = true;
//        card_comments.setLayoutParams(card_comments.getLayoutParams());
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            sceneId = R.layout.scene_cards;
//            runCircularReveal(scene_target);
//        }
//    }

    public void goToSceneOverView(View view) {
        exitActivity = false;
        sceneId = R.layout.scene_overview;
//        openScene(view);
        moveButton(view);
    }

    public void goToSceneIngredients(View view) {
        exitActivity = false;
        sceneId = R.layout.scene_ingredients;
//        openScene(view);
        moveButton(view);
    }

    public void goToSceneDirections(View view) {
        exitActivity = false;
        sceneId = R.layout.scene_directions;
//        openScene(view);
        moveButton(view);
    }

    public void goToSceneComments(View view) {
        exitActivity = false;
        sceneId = R.layout.scene_comments;
//        openScene(view);
        moveButton(view);
    }

//    private void openScene(final View view) {
//
//        DisplayMetrics dm = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int statusBarOffset = dm.heightPixels - scene_target.getMeasuredHeight();
//
//        int destinationPos[] = new int[2];
//
//        card_overview.getLocationOnScreen(destinationPos);
//
//
//        int originalPos[] = new int[2];
//        view.getLocationOnScreen(originalPos);
//
//        int xDest = destinationPos[0]; //
////        int xDest = dm.widthPixels / 2;
////        xDest -= (view.getMeasuredWidth() / 2);
////        int yDest = dm.heightPixels / 2 - (view.getMeasuredHeight() / 2) - statusBarOffset;
//
//        int yDest = statusBarOffset;
//        final TranslateAnimation animMove = new TranslateAnimation(0, xDest - originalPos[0], 0, yDest - originalPos[1]);
//        animMove.setDuration(5000);
////        view.startAnimation(animMove);
//
//
//        // Change layout parameters of button to move it
//        moveButton();
//
//
////        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
////            float x = view.getX();
////            float y = view.getY();
////            AnimatorPath path = new AnimatorPath();
////            path.moveTo(0, 0);
////            path.lineTo(xDest - originalPos[0], yDest - originalPos[1]);
////            path.curveTo(0.17f, .67f, .83f, .67f, xDest - originalPos[0], yDest - originalPos[1]);
////
////            final ObjectAnimator objectAnimator = ObjectAnimator.ofObject(this, "card_comments",
////                    new PathEvaluator(), path.getPoints().toArray());
////            objectAnimator.setDuration(1000);
////
////            objectAnimator.start();
////
//////            float x = view.getX();
//////            float y = view.getY();
//////            Path path = new Path();
//////
//////            path.moveTo(x + 0, y + 0);
//////            path.lineTo(x + 100, y + 150);
//////            path.lineTo(x + 400, y + 150);
//////            path.lineTo(x + 0, y + 0);
//////            ObjectAnimator objectAnimator =
//////                    null;
//////
//////            objectAnimator = ObjectAnimator.ofFloat(view, View.X,
//////                    View.Y, path);
//////            objectAnimator.setInterpolator(new PathInterpolator(0.17f, .67f, .83f, .67f));
//////            objectAnimator.setDuration(3000);
//////            objectAnimator.start();
////        }
//
//
////        final int defaultWidth = view.getWidth();
//
////        ResizeAnimation resizeAnimation = new ResizeAnimation(
////                view,
////                view.getHeight(),
////                view.getHeight(),
////                view.getWidth(),
////                scene_target.getMeasuredWidth(),
////                5000
////        );
////        resizeAnimation.setFillAfter(false);
////        anim.setFillAfter(false);
//
////        AnimationSet animationSet = new AnimationSet(true);
////        animationSet.addAnimation(anim);
////        animationSet.addAnimation(resizeAnimation);
////        resizeAnimation.setAnimationListener(new Animation.AnimationListener() {
////            @Override
////            public void onAnimationStart(Animation animation) {
////
////            }
////
////            @Override
////            public void onAnimationEnd(Animation animation) {
////                view.getLayoutParams().width = defaultWidth;
////            }
////
////            @Override
////            public void onAnimationRepeat(Animation animation) {
////
////            }
////        });
//
////        view.startAnimation(anim);
//
////        view.startAnimation(anim);
//
////        anim.setAnimationListener(new Animation.AnimationListener() {
////            @Override
////            public void onAnimationStart(Animation animation) {
////
////            }
////
////            @Override
////            public void onAnimationEnd(Animation animation) {
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////                    Transition ts = new android.transition.ChangeBounds();
//////                    ts.setDuration(500);
////                    ts.setInterpolator(new AccelerateDecelerateInterpolator());
////
////                    if (sceneId == R.layout.scene_overview) {
////                        sceneOverView = Scene.getSceneForLayout(
////                                (ViewGroup) findViewById(R.id.scene_target),
////                                sceneId,
////                                RecipeCardsActivity.this);
////
////                        TransitionManager.go(sceneOverView, ts);
////                    }
////                    if (sceneId == R.layout.scene_ingredients) {
////                        sceneIngredients = Scene.getSceneForLayout(
////                                (ViewGroup) findViewById(R.id.scene_target),
////                                sceneId,
////                                RecipeCardsActivity.this);
////
////                        TransitionManager.go(sceneIngredients, ts);
////                    }
////
////                    if (sceneId == R.layout.scene_directions) {
////                        sceneDirections = Scene.getSceneForLayout(
////                                (ViewGroup) findViewById(R.id.scene_target),
////                                sceneId,
////                                RecipeCardsActivity.this);
////
////                        TransitionManager.go(sceneDirections, ts);
////                    }
////
////                    if (sceneId == R.layout.scene_comments) {
////                        sceneComments = Scene.getSceneForLayout(
////                                (ViewGroup) findViewById(R.id.scene_target),
////                                sceneId,
////                                RecipeCardsActivity.this);
////
////                        TransitionManager.go(sceneComments, ts);
////                    }
////
////                }
////            }
////
////            @Override
////            public void onAnimationRepeat(Animation animation) {
////
////            }
////        });
//
//    }nk'l
// ,;,;,;,;m/

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
                            anim.setDuration(500);
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
        if (sceneId == R.layout.scene_overview) {
            view = findViewById(R.id.card_overview);
        }
        if (sceneId == R.layout.scene_ingredients) {
            view = findViewById(R.id.card_ingredients);
        }
        if (sceneId == R.layout.scene_directions) {
            view = findViewById(R.id.card_directions);
        }
        if (sceneId == R.layout.scene_comments) {
            view = findViewById(R.id.card_comments);
        }

        view.setTranslationX(newLoc.mX);
        view.setTranslationY(newLoc.mY);
    }

    private void runCircularReveal(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Rect bounds = new Rect();
            v.getDrawingRect(bounds);
            int centerX = bounds.centerX();
            int centerY = bounds.centerY();
            int finalRadius = Math.max(bounds.width(), bounds.height());

            Animator anim = ViewAnimationUtils.createCircularReveal(scene_target, centerX, centerY, 0f, finalRadius);
//            anim.setDuration(500);
            anim.setInterpolator(new AccelerateInterpolator());
            anim.addListener(animateIn);
            anim.start();
        }
    }

    private Animator.AnimatorListener animateIn = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.go(
                        Scene.getSceneForLayout(
                                (ViewGroup) findViewById(R.id.scene_target),
                                sceneId,
                                RecipeCardsActivity.this), new ChangeBounds());

            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private Animator.AnimatorListener animatorSceneEnter = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Transition ts = new android.transition.ChangeBounds();
                ts.setInterpolator(new AccelerateDecelerateInterpolator());
                ts.setDuration(10000);
                View view = null;
                if (sceneId == R.layout.scene_overview) {
                    view = card_overview;
                    sceneOverView = Scene.getSceneForLayout(
                            (ViewGroup) findViewById(R.id.scene_target),
                            sceneId,
                            RecipeCardsActivity.this);

                    TransitionManager.go(sceneOverView, ts);
                }
                if (sceneId == R.layout.scene_ingredients) {
                    view = card_ingredients;
                    sceneIngredients = Scene.getSceneForLayout(
                            (ViewGroup) findViewById(R.id.scene_target),
                            sceneId,
                            RecipeCardsActivity.this);

                    TransitionManager.go(sceneIngredients, ts);
                }

                if (sceneId == R.layout.scene_directions) {
                    view = card_directions;
                    sceneDirections = Scene.getSceneForLayout(
                            (ViewGroup) findViewById(R.id.scene_target),
                            sceneId,
                            RecipeCardsActivity.this);

                    TransitionManager.go(sceneDirections, ts);
                }

                if (sceneId == R.layout.scene_comments) {
                    view = card_comments;
                    sceneComments = Scene.getSceneForLayout(
                            (ViewGroup) findViewById(R.id.scene_target),
                            sceneId,
                            RecipeCardsActivity.this);

                    TransitionManager.go(sceneComments, ts);
                }
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

            if (sceneId == R.layout.scene_overview) {
            }
            if (sceneId == R.layout.scene_ingredients) {
                mainLayoutParam.removeRule(RelativeLayout.END_OF);
                mainLayoutParam.removeRule(RelativeLayout.RIGHT_OF);
            }
            if (sceneId == R.layout.scene_directions) {
                mainLayoutParam.removeRule(RelativeLayout.LEFT_OF);
                mainLayoutParam.removeRule(RelativeLayout.START_OF);
            }
            if (sceneId == R.layout.scene_comments) {
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
        if (sceneIdLast == R.layout.scene_overview) {
            mainLayoutParam = (LayoutParams) card_overview.getLayoutParams();
            mainLayoutParam.removeRule(RelativeLayout.BELOW);
            mainLayoutParam.setMarginStart(0);

            card_overview.setLayoutParams(mainLayoutParam);
            card_overview.setVisibility(View.VISIBLE);
//            curveMotion(card_overview, false);
        }
        if (sceneIdLast == R.layout.scene_ingredients) {
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

        if (sceneIdLast == R.layout.scene_directions) {
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

        if (sceneIdLast == R.layout.scene_comments) {
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

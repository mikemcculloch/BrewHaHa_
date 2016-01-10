package brightseer.com.brewhaha;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.BitmapInfo;
import com.koushikdutta.ion.future.ImageViewFuture;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by wooan on 10/24/2015.
 */
public class RecipeCardsActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private android.support.design.widget.FloatingActionButton fab;
    private AppBarLayout app_bar_layout;
    private ImageView image;
    private String imageRecipe;
    private ImageViewFuture ionImage;
    private CardView detail_instruction, detail_stats, detail_ingredients, detail_comments;
    private View scene_cards;

    private ViewGroup root;
    private int sceneChoice;

    int  xCord;
    int  yCord;

    android.support.v7.widget.AppCompatButton button_test;
    android.support.design.widget.CoordinatorLayout layout_root;

    private final int interval = 5000; // 1 Second
    private Handler handler = new Handler();
    ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
    private int imageId = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setupTransistion();
        setupTransistionSlide();
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_recipe_cards);
            _mContext = this;
//            initExtras();
            initViews();
//           slideIn();

            imageTransition(image, imageRecipe, Constants.exBitMapInfoMain);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initExtras() {
        Intent activityThatCalled = getIntent();
//        responseContentItemPk = activityThatCalled.getExtras().getString(Constants.exContentItemPk);
//        previewMode = activityThatCalled.getExtras().getBoolean(Constants.exRecipePreview);
//        recipeTitle = activityThatCalled.getExtras().getString(Constants.exRecipeTitle);
        adapterPosition = activityThatCalled.getExtras().getInt(Constants.exPosition);
        imageRecipe = activityThatCalled.getExtras().getString(Constants.exRecipeImage);
//        author = activityThatCalled.getExtras().getString(Constants.exUsername);
//        datePosted = activityThatCalled.getExtras().getString(Constants.exUserdate);
//        authorImageUrl = activityThatCalled.getExtras().getString(Constants.exAuthorImage);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        layout_root = (android.support.design.widget.CoordinatorLayout) findViewById(R.id.layout_root);

        app_bar_layout = (android.support.design.widget.AppBarLayout) findViewById(R.id.app_bar_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    onBackPressed();
                }
            });
        }
//        fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fatal error with out this click event
                //String test = "fart";
            }
        });

//        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
//        handler.postDelayed(runnable, interval);

//        new Timer().scheduleAtFixedRate(runnable, interval, interval);

        ionImage = Ion.with(image)
                .animateIn(R.anim.fade_in)
                .centerCrop()
                .load("http://wcf.brewhaha.beer/contentimages/A720C5FB-2711-4EA0-9AC5-02592879231A/alphabrew.jpg");

//        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
//            public void run() {
//                doSomethingUseful();
//            }
//        }, 0, 4, TimeUnit.SECONDS);

        detail_instruction = (CardView) findViewById(R.id.detail_instruction);
        detail_instruction.setOnClickListener(this);
        detail_stats = (CardView) findViewById(R.id.detail_stats);
        detail_stats.setOnClickListener(this);
        detail_ingredients = (CardView) findViewById(R.id.detail_ingredients);
        detail_ingredients.setOnClickListener(this);
        detail_ingredients.setOnTouchListener(this);


        detail_comments = (CardView) findViewById(R.id.detail_comments);
        detail_comments.setOnClickListener(this);


        root = (ViewGroup) findViewById(R.id.root);
        scene_cards = findViewById(R.id.scene_cards);


        button_test = (android.support.v7.widget.AppCompatButton) findViewById(R.id.button_test);
        button_test.setOnClickListener(this);
    }

//    private void doSomethingUseful() {
//        image = (ImageView) findViewById(R.id.image);
////
//        Ion.getDefault(this).getCache().clear();
//        ionImage.withBitmapInfo().cancel();
//
//
//        if (imageId == 0) {
//            ionImage = Ion.with(image)
//                    .animateIn(R.anim.fade_in)
//                    .fitCenter()
//                    .load("http://wcf.brewhaha.beer/contentimages/A720C5FB-2711-4EA0-9AC5-02592879231A/alphabrew.jpg");
//            imageId = 1;
//        } else if (imageId == 1) {
//
//
////            ImageViewFuture bitmap = Ion.with(this)
////                    .load("http://wcf.brewhaha.beer/contentimages/A720C5FB-2711-4EA0-9AC5-02592879231A/raiseaclass.jpg")
////                    .intoImageView(image);
//
////            ionImage = Ion.with(image)
////                    .animateIn(R.anim.fade_in)
////                    .fitCenter()
////                    .load("http://wcf.brewhaha.beer/contentimages/A720C5FB-2711-4EA0-9AC5-02592879231A/raiseaclass.jpg");
//            imageId = 0;
//        }
//    }

    //    private Runnable runnable = new Runnable() {
//        public void run() {
//            if (imageId == 0) {
//                Ion.with(image)
//                        .animateIn(R.anim.fade_in)
//                        .fitCenter()
//                        .load("http://wcf.brewhaha.beer/contentimages/A720C5FB-2711-4EA0-9AC5-02592879231A/alphabrew.jpg");
//                imageId = 1;
//            } else if (imageId == 1) {
//                Ion.with(image)
//                        .animateIn(R.anim.fade_in)
//                        .fitCenter()
//                        .load("http://wcf.brewhaha.beer/contentimages/A720C5FB-2711-4EA0-9AC5-02592879231A/raiseaclass.jpg");
//                imageId = 0;
//            }
//        }
//    };
    private void slideIn() {
        Slide slide = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            slide = new Slide();
            slide.setSlideEdge(Gravity.TOP);
            slide.setDuration(1000);

            ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
            TransitionManager.beginDelayedTransition(root, slide);
        }
    }

    private void imageTransition(final ImageView imageview_holder, final String urlImage, String bitMapInfo) {
//        final ImageView imageview_holder = imageView;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_test:
                runCircularRevealOut(v);
                break;

            case R.id.detail_instruction:
                sceneChoice = Constants.sceneDirections;
                runCircularReveal(v);
                break;

            case R.id.detail_stats:
                sceneChoice = Constants.sceneOverview;
                runCircularReveal(v);
                break;

            case R.id.detail_ingredients:
                sceneChoice = Constants.sceneIngredients;
                runCircularReveal(v);
                break;

            case R.id.detail_comments:
                sceneChoice = Constants.sceneComments;
                runCircularReveal(v);
                break;
        }
        ;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
//            case MotionEvent.ACTION_UP:
//        }
//        return false;
//    }

    private void runCircularReveal(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Context _context = this;

//            Rect bounds = new Rect();
//            v.getDrawingRect(bounds);
//            int centerX = bounds.centerX();
//            int centerY = bounds.centerY();
//            int finalRadius = Math.max(bounds.width(), bounds.height());

//            int centerX = v.getWidth();
//            int centerY = v.getHeight();

//            int centerX = (v.getLeft() + v.getRight()) / 2;
//            int centerY = (v.getTop() + v.getBottom()) / 2;

            int centerX = (layout_root.getWidth()) / 2;
            int centerY = (layout_root.getHeight()) / 2;

            int finalRadius = (int) Math.hypot(layout_root.getWidth() / 2, layout_root.getHeight() / 2);

            Animator anim = ViewAnimationUtils.createCircularReveal(layout_root, centerX, centerY, 0f, finalRadius);
            anim.setDuration(500);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.addListener(animateIn);

//            scene_cards.setVisibility(View.GONE);
            anim.start();
        }
    }

    private void runCircularRevealOut(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Context _context = this;

            int finalRadius = (int) Math.hypot(root.getWidth() / 2, root.getHeight() / 2);

            Animator anim = ViewAnimationUtils.createCircularReveal(root, (int) v.getWidth() / 2, (int) v.getHeight() / 2, 0, finalRadius);
            anim.setDuration(300);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.addListener(animateOut);

//            scene_cards.setVisibility(View.VISIBLE);
            anim.start();
        }
    }

    private Animator.AnimatorListener animateIn = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {


            int sceneId = R.layout.scene_overview;
            switch (sceneChoice) {
                case Constants.sceneComments:
                    sceneId = R.layout.scene_comments;
                    layout_root.setBackgroundColor(getResources().getColor(R.color.app_blue));
                    break;

                case Constants.sceneDirections:
                    sceneId = R.layout.scene_directions;
                    layout_root.setBackgroundColor(getResources().getColor(R.color.app_gray));
                    break;

                case Constants.sceneIngredients:
                    sceneId = R.layout.scene_ingredients;
                    layout_root.setBackgroundColor(getResources().getColor(R.color.app_orange));
                    break;

                case Constants.sceneOverview:
                    sceneId = R.layout.scene_overview;
                    layout_root.setBackgroundColor(getResources().getColor(R.color.app_yellow));
                    break;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.go(
                        Scene.getSceneForLayout(
                                (ViewGroup) findViewById(R.id.root),
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

    private Animator.AnimatorListener animateOut = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            layout_root.setBackgroundColor(getResources().getColor(R.color.white));

            int sceneId = R.layout.scene_overview;
            switch (sceneChoice) {
                case Constants.sceneComments:
                    sceneId = R.layout.scene_comments;
                    break;

                case Constants.sceneDirections:
                    sceneId = R.layout.scene_directions;
                    break;

                case Constants.sceneIngredients:
                    sceneId = R.layout.scene_ingredients;
                    break;

                case Constants.sceneOverview:
                    sceneId = R.layout.scene_comments;
                    break;
            }
            ;


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.go(
                        Scene.getSceneForLayout(
                                (ViewGroup) findViewById(R.id.root),
                                R.id.scene_cards,
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int location[] = new int[2];
        v.getLocationOnScreen(location);
        xCord = location[0] / 2;
        yCord = location[1] / 2;
//         xCord = (int)event.getRawX()/2;
//         yCord = (int) event.getRawY()/2;

        return false;
    }



}

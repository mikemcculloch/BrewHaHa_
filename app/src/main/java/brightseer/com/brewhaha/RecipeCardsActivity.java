package brightseer.com.brewhaha;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.plus.PlusOneButton;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.firebase_helpers.CloneRecipe;
import brightseer.com.brewhaha.firebase_helpers.FirebaseCrud;
import brightseer.com.brewhaha.helper.AnimatorPath;
import brightseer.com.brewhaha.helper.PathEvaluator;
import brightseer.com.brewhaha.helper.PathPoint;
import brightseer.com.brewhaha.helper.RecyclerItemClickListener;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.RecipeDetail;
import brightseer.com.brewhaha.models.RecipeMenuItem;
import brightseer.com.brewhaha.recipe_adapters.SheetMenuAdapter;
import brightseer.com.brewhaha.recipe_fragments.DirectionFragment;
import brightseer.com.brewhaha.recipe_fragments.ImageFragment;
import brightseer.com.brewhaha.recipe_fragments.IngredientFragment;
import brightseer.com.brewhaha.recipe_fragments.OverviewFragment;

/**
 * Created by wooan on 10/24/2015.
 */
public class RecipeCardsActivity extends NewActivtyBase implements View.OnClickListener {
    private Toolbar toolbar;
    private FloatingActionButton fabMenu;
    private int sceneId, sceneIdLast = 0;
    private boolean toggleSceneButtons = false, curveDir = true;
    private PlusOneButton mPlusOneButton;
    private AppCompatButton card_overview, card_ingredients, card_directions, card_images, card_overviewHolder, card_commentsHolder;
    private String feedKey, cloneKey, recipeTitle, authorImageUrl, recipeAuthor, recipeStyle, dateCreated;
    private Firebase rootRef;
    private RecipeDetail recipeDetail;
    private boolean isOwner = false, isEditEnabled = false;
    private BottomSheetBehavior menuSheetBehavior;
    private AnimatedVectorDrawable menuToCross;
    private AnimatedVectorDrawable crossToMenu;
    private boolean tick = false;
    private SheetMenuAdapter sheetMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupTransistion();
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_recipe_animated);
            initExtras();
            initViews();
            buttonWidth();

            initFirebaseDb();
            getRecipeDetail();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_overview:
                if (toggleSceneButtons)
                    goToSceneOverView(v, true);
                break;
            case R.id.card_ingredients:
                if (toggleSceneButtons)
                    goToSceneIngredients(v);
                break;
            case R.id.card_directions:
                if (toggleSceneButtons)
                    goToSceneDirections(v);
                break;
            case R.id.card_images:
                if (toggleSceneButtons) {
                    goToSceneImages(v);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            setResult(BackPressed);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                finish();
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
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
                getSupportActionBar().setTitle(recipeTitle);
            }

            mPlusOneButton = (PlusOneButton) findViewById(R.id.plus_one_button);
            ViewCompat.setTransitionName(mPlusOneButton, getResources().getString(R.string.transition_googlePlus));

            TextView recipe_title_text_view = (TextView) findViewById(R.id.recipe_title_text_view);
            recipe_title_text_view.setText(recipeTitle);
            ViewCompat.setTransitionName(recipe_title_text_view, getResources().getString(R.string.transition_title));

            TextView recipe_author_text_view = (TextView) findViewById(R.id.recipe_author_text_view);
            recipe_author_text_view.setText(recipeAuthor);
            ViewCompat.setTransitionName(recipe_author_text_view, getResources().getString(R.string.transition_author));

            TextView recipe_style_text_view = (TextView) findViewById(R.id.recipe_style_text_view);
            recipe_style_text_view.setText(recipeStyle);
            ViewCompat.setTransitionName(recipe_style_text_view, getResources().getString(R.string.transition_style));

            TextView recipe_date_created_text_view = (TextView) findViewById(R.id.recipe_date_created_text_view);
            recipe_date_created_text_view.setText(Utilities.DisplayTimeFormater(dateCreated));
            ViewCompat.setTransitionName(recipe_date_created_text_view, getResources().getString(R.string.transition_dateCreated));


            CardView parent_layout = (CardView) findViewById(R.id.parent_layout);
            ViewCompat.setTransitionName(parent_layout, getResources().getString(R.string.transition_layout));

            ImageView author_image_view = (ImageView) findViewById(R.id.author_image_view);
//            Ion.with(author_image_view)
//                    .centerCrop()
//                    .transform(Utilities.GetRoundTransform())
//                    .load(authorImageUrl);

            Picasso.with(RecipeCardsActivity.this)
                    .load(authorImageUrl)
                    .transform(Utilities.GetRoundTransform())
                    .into(author_image_view);


            author_image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
                    showLoginBottomSheetDialog(RecipeCardsActivity.this, findViewById(R.id.dialog_bottom_sheet));
                }
            });

            fabMenu = (FloatingActionButton) findViewById(R.id.fabMenu);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                menuToCross = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_menu_to_cross);
                crossToMenu = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_cross_to_menu);
                fabMenu.setImageDrawable(menuToCross);
            } else {
                fabMenu.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_white_24dp));
            }

            fabMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animatedVector();
                }
            });

            card_overview = (AppCompatButton) findViewById(R.id.card_overview);
            card_overview.setOnClickListener(this);
            card_ingredients = (AppCompatButton) findViewById(R.id.card_ingredients);
            card_ingredients.setOnClickListener(this);
            card_directions = (AppCompatButton) findViewById(R.id.card_directions);
            card_directions.setOnClickListener(this);
            card_images = (AppCompatButton) findViewById(R.id.card_images);
            card_images.setOnClickListener(this);

            card_overviewHolder = (AppCompatButton) findViewById(R.id.card_overviewHolder);
            card_commentsHolder = (AppCompatButton) findViewById(R.id.card_commentsHolder);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void animatedVector() {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                AnimatedVectorDrawable drawable = tick ? menuToCross : crossToMenu;
                if (tick) {
                    menuSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    menuSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }

                fabMenu.setImageDrawable(drawable);
                drawable.start();
                tick = !tick;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initBottomSheet() {
        try {
            View menu_bottom_sheet = findViewById(R.id.menu_bottom_sheet);
            menuSheetBehavior = BottomSheetBehavior.from(menu_bottom_sheet);

            menuSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == 4 && tick) {
                        animatedVector();
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });

            RecyclerView recipe_menu_recycler_view = (RecyclerView) menu_bottom_sheet.findViewById(R.id.recipe_menu_recycler_view);
            recipe_menu_recycler_view.setHasFixedSize(true);
            recipe_menu_recycler_view.setLayoutManager(new LinearLayoutManager(this));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                recipe_menu_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider, getTheme()), true));
            } else {
                recipe_menu_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
            }

            recipe_menu_recycler_view.addOnItemTouchListener(
                    new RecyclerItemClickListener(getBaseContext(), recipe_menu_recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                AuthData authData = rootRef.getAuth();
                                if (authData == null) {
                                    ChildShowLoginDialog();
                                    return;
                                }

                                RecipeMenuItem recipeMenuItem = sheetMenuAdapter.getItemAt(position);

                                switch (recipeMenuItem.getmId()) {
                                    case Constants.menuClone:
                                        menuCloneClick();
                                        break;
                                    case Constants.menuDelete:
                                        menuDeleteClick();
                                        break;
                                    case Constants.menuEdit:
                                        menuEditClick();
                                        break;
                                }

                                menuSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            } catch (Exception e) {
                                if (BuildConfig.DEBUG) {
                                    Log.e(Constants.LOG, e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {


                        }
                    })
            );

            sheetMenuAdapter = new SheetMenuAdapter(GetRecipeMenuItems(), this);
            recipe_menu_recycler_view.setAdapter(sheetMenuAdapter);

        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void buttonWidth() {
        try {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x / 4;
            int height = size.y;


//            int dpConversion = (int) (width * Resources.getSystem().getDisplayMetrics().density);

            card_overview.setWidth(width);
            card_directions.setWidth(width);
            card_images.setWidth(width);
            card_ingredients.setWidth(width);
            card_overviewHolder.setWidth(width);
            card_commentsHolder.setWidth(width);

        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void initExtras() {
        try {
            Intent activityThatCalled = getIntent();
            recipeTitle = activityThatCalled.getExtras().getString(Constants.exRecipeTitle);
            recipeAuthor = activityThatCalled.getExtras().getString(Constants.exRecipeAuthor);
            authorImageUrl = activityThatCalled.getExtras().getString(Constants.exAuthorImage);
            feedKey = activityThatCalled.getExtras().getString(Constants.fbFeedKey);
            recipeStyle = activityThatCalled.getExtras().getString(Constants.exRecipeStyle);
            dateCreated = activityThatCalled.getExtras().getString(Constants.exDateCreated);
            cloneKey = activityThatCalled.getExtras().getString(Constants.exCloneKey);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void initFirebaseDb() {
        rootRef = new Firebase(Constants.fireBaseRoot).child(Constants.fbRecipeDetail).child(feedKey);
    }

    private void getRecipeDetail() {
        try {
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    recipeDetail = dataSnapshot.getValue(RecipeDetail.class);
                    recipeDetail.setKey(dataSnapshot.getKey());
                    goToSceneOverView(findViewById(R.id.card_overview), false);
                    toggleSceneButtons = true;
                    evaluateUser();
                    initBottomSheet();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void goToSceneOverView(View view, boolean toggleSheet) {
        try {
            sceneId = Constants.sceneOverview;
            moveButton(view, toggleSheet);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void goToSceneIngredients(View view) {
        try {
            sceneId = Constants.sceneIngredients;
            moveButton(view, true);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void goToSceneDirections(View view) {
        try {
            sceneId = Constants.sceneDirections;
            moveButton(view, true);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void goToSceneImages(View view) {
        try {
            sceneId = Constants.sceneImages;
            moveButton(view, true);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void moveButton(View view, boolean toggleSheet) {
        try {
            if (toggleSheet && tick) {
                animatedVector();
            }
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
        try {
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
            if (sceneId == Constants.sceneImages) {
                view = findViewById(R.id.card_images);
            }

            view.setTranslationX(newLoc.mX);
            view.setTranslationY(newLoc.mY);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    Animator.AnimatorListener animatorSceneEnter = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    View view = null;
                    int randomColor = Color.argb(255, (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
                    Fragment fragment = null;
                    if (sceneId == Constants.sceneOverview) {
                        view = card_overview;
                        fragment = OverviewFragment.newInstance(20, 20, randomColor, recipeDetail, recipeTitle, feedKey, authorImageUrl);
                    }

                    if (sceneId == Constants.sceneIngredients) {
                        view = card_ingredients;
                        fragment = IngredientFragment.newInstance(20, 20, randomColor, feedKey);
                    }

                    if (sceneId == Constants.sceneDirections) {
                        view = card_directions;
                        fragment = DirectionFragment.newInstance(20, 20, randomColor, feedKey);
                    }

                    if (sceneId == Constants.sceneImages) {
                        view = card_images;
                        fragment = ImageFragment.newInstance(20, 20, randomColor, feedKey);
                    }

                    ViewGroup scene_target = (ViewGroup) findViewById(R.id.scene_target);
                    if (scene_target != null) {
                        scene_target.removeAllViews();
                    }

                    if (view != null) {
                        view.setVisibility(View.INVISIBLE);
                    }

                    getSupportFragmentManager().beginTransaction().add(R.id.scene_target, fragment).commit();

                    toggleSceneButtons = true;
                }
            } catch (Exception e) {
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (recipeTitle != null) {
//                String URL = Constants.urlBrewHahaContent + recipeTitle.replace(" ", "-");
                // Refresh the state of the +1 button each time the activity receives focus.
//                mPlusOneButton.initialize(URL, PLUS_ONE_REQUEST_CODE);
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setLayoutParam(View view) {
        try {
            LayoutParams mainLayoutParam = (LayoutParams) view.getLayoutParams();
            mainLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mainLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_START);
            mainLayoutParam.addRule(RelativeLayout.BELOW, R.id.card_overviewHolder);
            mainLayoutParam.setMargins(0, 0, 0, 0);
            mainLayoutParam.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
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
            if (sceneId == Constants.sceneImages) {
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
        try {
            LayoutParams mainLayoutParam;
            if (sceneIdLast == Constants.sceneOverview) {
                mainLayoutParam = (LayoutParams) card_overview.getLayoutParams();
                mainLayoutParam.removeRule(RelativeLayout.BELOW);
                mainLayoutParam.setMarginStart(0);
                mainLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);

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
                mainLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);

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
                mainLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);

                card_directions.setLayoutParams(mainLayoutParam);
                card_directions.setVisibility(View.VISIBLE);
            }

            if (sceneIdLast == Constants.sceneImages) {
                card_images.setVisibility(View.VISIBLE);
                mainLayoutParam = (LayoutParams) card_images.getLayoutParams();

                mainLayoutParam.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                mainLayoutParam.removeRule(RelativeLayout.ALIGN_PARENT_START);
                mainLayoutParam.removeRule(RelativeLayout.BELOW);
                mainLayoutParam.setMarginStart(0);
                mainLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_END);
                mainLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                mainLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);

                card_images.setLayoutParams(mainLayoutParam);
                card_images.setVisibility(View.VISIBLE);
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void evaluateUser() {
        try {
            if (!BrewSharedPrefs.getEmailAddress().isEmpty()) {
                AuthData authData = rootRef.getAuth();
                if (authData != null) {
                    if (recipeDetail.getOwnerEmail().equals(BrewSharedPrefs.getEmailAddress())) {
                        isOwner = true;
                    }
                }
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void ChildShowLoginDialog() {
        try {
            showLoginBottomSheetDialog(RecipeCardsActivity.this, findViewById(R.id.dialog_bottom_sheet));
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public boolean GetIsEditEnabled(){
        return isEditEnabled;
    }

    private List<RecipeMenuItem> GetRecipeMenuItems() {
        try {
            List<RecipeMenuItem> recipeMenuItems = new Vector<>();
            recipeMenuItems.add(new RecipeMenuItem(R.drawable.ic_content_copy_black_24dp, "Clone", Constants.menuClone));

            if (isOwner) {
                recipeMenuItems.add(new RecipeMenuItem(R.drawable.ic_delete_forever_black_24dp, "Delete", Constants.menuDelete));
                recipeMenuItems.add(new RecipeMenuItem(R.drawable.ic_mode_edit_black_24dp, "Edit", Constants.menuEdit));
            }
            return recipeMenuItems;
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
            return null;
        }
    }

    private void menuCloneClick() {
        try {
            String feedName = Constants.fbPublicFeeds;
            if (isOwner)
                feedName = BrewSharedPrefs.getEmailAddress();

            CloneRecipe cloneRecipe = new CloneRecipe();
            cloneRecipe.Clone(feedKey, feedName, findViewById(R.id.coordinatorlayout));
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void menuDeleteClick() {
        try {
            DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    FirebaseCrud firebaseCrud = new FirebaseCrud();
                    firebaseCrud.DeleteRecipe(feedKey, cloneKey);

                    finish();
                }
            };

            Utilities.DeletePrompt(RecipeCardsActivity.this, positiveClick);

        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void menuEditClick() {
        try {
            isEditEnabled = true;

            if (sceneId == Constants.sceneOverview) {
               goToSceneOverView(findViewById(R.id.card_overview), true);
            }
            if (sceneId == Constants.sceneIngredients) {
                goToSceneIngredients(findViewById(R.id.card_ingredients));
            }
            if (sceneId == Constants.sceneDirections) {
                goToSceneDirections(findViewById(R.id.card_directions));
            }
            if (sceneId == Constants.sceneImages) {
                goToSceneImages(findViewById(R.id.card_images));
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }
}

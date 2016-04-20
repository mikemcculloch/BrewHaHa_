package brightseer.com.brewhaha;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Transition;
import android.util.Log;
import android.view.ContextMenu;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.plus.PlusOneButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.BitmapInfo;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.adapter.CommentAdapter;
import brightseer.com.brewhaha.adapter.IngredientAdapter;
import brightseer.com.brewhaha.adapter.InstructionAdapter_Legacy;
import brightseer.com.brewhaha.adapter.SimpleViewPagerAdapter;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.objects.BatchSize;
import brightseer.com.brewhaha.objects.RecipeSummary;
import brightseer.com.brewhaha.models.Comment;
import brightseer.com.brewhaha.models.RecipeImage;
import brightseer.com.brewhaha.objects.Ingredient;
import brightseer.com.brewhaha.models.RecipeInstruction;
import brightseer.com.brewhaha.objects.RecipeContent;
import brightseer.com.brewhaha.repository.DBHelper_BatchSize;
import brightseer.com.brewhaha.repository.JsonToObject;

import static android.view.ViewGroup.LayoutParams;

public class RecipeActivity extends BaseActivity implements View.OnClickListener{
    private DBHelper_BatchSize repoBatch;
    private int contentItemPk;
    private boolean toggleStar = false;
    private android.support.design.widget.FloatingActionButton fab;
    private int imageCount = 0;
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    private String responseContentItemPk, recipeTitle, author, datePosted, authorImageUrl;
    private boolean previewMode = false;
    private TextView recipe_author_text_view, overview_text_view,
            recipe_title_text_view, recipe_date_posted,
            recipe_total_comments_text_view, recipe_total_images_text_view, summary_header_batch_size_text_view,
            summary_srm_text_view, summary_abv_text_view, summary_ibu_text_view,
            summary_fg_text_view, summary_og_text_view;
    private EditText recipe_comment_add_edit_text_view;
    private ImageView recipe_header_user_image_view, recipe_comment_add_image_view;
    private Button recipe_comments_load_more;
    public ListView comments_list_view, instructions_list_view, ingredient_list_view;
    private LinearLayout footer, recipe_parent_layout;
    private ScrollView sticky_scroll;
    private ViewPager viewPager;
    private boolean isFirstOrLastPage;
    private int currentPageIndex = 0;
    private PlusOneButton mPlusOneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _mContext = this;
        _Activity = RecipeActivity.this;
        LoadDialog(_mContext, false, true);
        try {
//            myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
            setContentView(R.layout.activity_recipe);

            initExtras();

            initGoogleAnalytics(this.getClass().getSimpleName());
//            initGooglePlus();
            initScrollView();
            initAdMob();

            initializeEditTextViews();
            initializeListViews();
            initializeButtons();
            initializeLinearLayout();
            initializeCheckbox();
            initializeImageView();
            initializeTextViews();


            imageTransition(recipe_header_user_image_view, authorImageUrl, Constants.exBitMapInfo);
            AppBarLayout app_bar_layout_standard = (AppBarLayout) findViewById(R.id.app_bar_layout_standard);
            app_bar_layout_standard.setExpanded(false);

            Toolbar toolbar = (Toolbar) findViewById(R.id.standard_toolbar);
            setSupportActionBar(toolbar);
            if (toolbar != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
                    LayoutParams layoutParams = toolbar.getLayoutParams();
                    layoutParams.height = layoutParams.height + getStatusBarHeight();
                }

                toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            }

            repoBatch = new DBHelper_BatchSize(_mContext);
//            load();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    private void initExtras() {
        Intent activityThatCalled = getIntent();
        responseContentItemPk = activityThatCalled.getExtras().getString(Constants.exContentItemPk);
        previewMode = activityThatCalled.getExtras().getBoolean(Constants.exRecipePreview);
        recipeTitle = activityThatCalled.getExtras().getString(Constants.exRecipeTitle);
        adapterPosition = activityThatCalled.getExtras().getInt(Constants.exPosition);
        author = activityThatCalled.getExtras().getString(Constants.exUsername);
        datePosted = activityThatCalled.getExtras().getString(Constants.exUserdate);
        authorImageUrl = activityThatCalled.getExtras().getString(Constants.exAuthorImage);
    }

//    private void load() {
//        String contentUrl = Constants.wcfGetContentById + responseContentItemPk.toString() + "/" + BrewSharedPrefs.getUserToken();
//        Ion.with(_mContext)
//                .load(contentUrl)
//                .setHeader("Cache-Control", "No-Cache")
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//
//                                 @Override
//                                 public void onCompleted(Exception e, JsonObject result) {
//                                     try {
//                                         dialogProgress.dismiss();
//                                         if (result != null) {
//                                             RecipeContent recipeContent = JsonToObject.JsonToRecipeContent(result);
//                                             BrewSharedPrefs.setCurrentToken(recipeContent.getToken());
//                                             BrewSharedPrefs.setCurrentContentTitle(recipeContent.getTitle());
//
//                                             setShareIntent();
//
//                                             toggleStar = recipeContent.isFavorite();
//                                             contentItemPk = recipeContent.getContentItemPk();
//                                             comment_count = recipeContent.getCommentCount();
//                                             int imageCount = recipeContent.getImagesMList().size();
//
//                                             if (TextUtils.isEmpty(recipe_author_text_view.getText())) {
//                                                 recipe_author_text_view.setText(recipeContent.getAuthor());
//                                             }
//
//                                             overview_text_view.setText(recipeContent.getDescription());
//
//                                             if (TextUtils.isEmpty(recipeTitle)) {
//                                                 recipe_title_text_view.setText(recipeContent.getTitle());
//                                             }
//
//                                             if (TextUtils.isEmpty(datePosted)) {
//                                                 recipe_date_posted.setText(Utilities.DisplayTimeFormater(recipeContent.getLastUpdatedTimestamp()));
//                                             }
//                                             recipe_total_comments_text_view.setText(String.valueOf(recipeContent.getCommentCount()) + " " + recipe_total_comments_text_view.getText());
//                                             recipe_total_images_text_view.setText(String.valueOf(imageCount) + " " + recipe_total_images_text_view.getText());
//
//                                             if (TextUtils.isEmpty(authorImageUrl)) {
//                                                 Ion.with(recipe_header_user_image_view)
//                                                         .placeholder(R.drawable.ic_person_black_24dp)
//                                                         .error(R.drawable.ic_person_black_24dp)
//                                                         .transform(trans)
//                                                         .load(recipeContent.getUserImageUrl());
//                                             }
//
//                                             String batch_text = "";
//                                             if (repoBatch.getBatchSizeCount() > 0) {
//                                                 BatchSize batchSize = repoBatch.getBatchSizeByPk(recipeContent.getBatchSizePk());
//
//                                                 batch_text = batchSize.getValue() + " " + batchSize.getDescription();
//
//                                             }
//                                             summary_header_batch_size_text_view.setText(batch_text);
//
//                                             addSummaryItems(recipeContent.getRecipeSummaryM());
//                                             addIngredientItems(recipeContent.getIngredientMList());
//                                             addRecipeSteps(recipeContent.getRecipeInstructions());
//                                             comments = recipeContent.getCommentMList();
//                                             addComments(comments);
//                                             addImages(recipeContent.getImagesMList());
//                                             addFabListener();
//                                         }
//                                     } catch (Exception ex) {
//                                         if (BuildConfig.DEBUG) {
//                                             Log.e(Constants.LOG, e.getMessage());
//                                             Log.e(Constants.LOG, ex.getMessage());
//                                         }
//                                     }
//                                 }
//                             }
//
//                );
//    }

    @Override
    public void onClick(View v) {
//        myVib.vibrate(50);
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        switch (v.getId()) {

            case R.id.recipe_comments_load_more:
                LoadDialog(this, false, true);
                loadMoreComments();
                break;
            case R.id.recipe_comment_add_image_view:
//                if (BrewSharedPrefs.getIsUserLoggedIn()) {
//                    LoadDialog(this, false, true);
//                    AddUpdateComments();
//                } else {
//                    AlertLoginPrompt(_mContext, "", getText(R.string.text_login_to_comment).toString(), getText(R.string.text_sign_in).toString(), getText(R.string.text_close).toString());
//                }
//                break;

            case R.id.recipe_total_comments_text_view:
                sticky_scroll.fullScroll(ScrollView.FOCUS_DOWN);
                break;

            case R.id.recipe_parent_layout:
                recipe_comment_add_edit_text_view.clearFocus();
                break;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_menu_options).setVisible(false);
        menu.findItem(R.id.menu_item_share).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        selectedView = v;
        contextMenu = menu;
        menu.setHeaderTitle("");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_comment_buttons, menu);
    }

    public void addSummaryItems(RecipeSummary recipeSummary) {
        try {
            summary_srm_text_view.setText(recipeSummary.getColorSrm() + " SRM");
//            int ibu = Integer.parseInt(recipeSummary.getBitternessIbu());
//            String ibuog =
////                    summary_ibuog_text_view.setText(recipeSummary());
            summary_abv_text_view.setText(recipeSummary.getAlcoholByVol());
            summary_ibu_text_view.setText(recipeSummary.getBitternessIbu() + " IBU");
            summary_fg_text_view.setText(recipeSummary.getFinalGravity() + " FG");
            summary_og_text_view.setText(recipeSummary.getOriginalGravity() + " OG");
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public void addIngredientItems(List<Ingredient> ingredients) {
        try {

            List<Ingredient> grains = new Vector<>();
            List<Ingredient> hops = new Vector<>();
            for (Ingredient item : ingredients) {
                if (item.getType().equals("Grain")) {
                    grains.add(item);
                }
                if (item.getType().equals("Hops")) {
                    hops.add(item);
                }
            }
            int lastValue = grains.size() + hops.size();
            List<Integer> titlesArray = new Vector<>();// {0, grains.size(), lastValue};
            titlesArray.add(0);
            titlesArray.add(grains.size());
            titlesArray.add(lastValue);
            IngredientAdapter ingredientAdapter = new IngredientAdapter(this, R.layout.row_ingredient, ingredients, RecipeActivity.this, titlesArray, Integer.parseInt(responseContentItemPk));
            ingredient_list_view.setAdapter(ingredientAdapter);

            setListViewHeightBasedOnChildren(ingredient_list_view);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public void addRecipeSteps(List<RecipeInstruction> recipeInstructions) {
        try {
            InstructionAdapter_Legacy instructionAdapterLegacy = new InstructionAdapter_Legacy(this, R.layout.row_instruction, recipeInstructions, RecipeActivity.this, Integer.parseInt(responseContentItemPk));
            instructions_list_view.setAdapter(instructionAdapterLegacy);
            setListViewHeightBasedOnChildren(instructions_list_view);

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public void addComments(List<Comment> comments) {
        try {
            commentAdapter = new CommentAdapter(this, R.layout.row_comment, comments, RecipeActivity.this);
            comments_list_view.setAdapter(commentAdapter);
            setListViewHeightBasedOnChildren(comments_list_view);
            if (comment_count > 6) {
                recipe_comments_load_more.setVisibility(View.VISIBLE);
            }
//            if (BrewSharedPrefs.getIsUserLoggedIn()) {
//                comments_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        _Comment = commentAdapter.getItem(position);
//                        if (_Comment.getUserToken().trim().equals(BrewSharedPrefs.getUserToken().trim())) {
//                            ClearCommentInput();
//                            registerForContextMenu(view);
//                            openContextMenu(view);
//                            view.setLongClickable(false);
//
//                        }
//                    }
//                });
//            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public void addImages(List<RecipeImage> recipeImages) {
        try {
            imageCount = recipeImages.size();
            viewPager = (ViewPager) findViewById(R.id.recipe_image_pager);
            viewPager.setVisibility(View.VISIBLE);
            viewPager.setOnPageChangeListener(pageChangeListener);
            viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
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

            ArrayList<String> imageUrlList = new ArrayList<String>();
            for (RecipeImage item : recipeImages) {
                imageUrlList.add(item.getImageUrl());
            }
            SimpleViewPagerAdapter imagePagerAdapter = new SimpleViewPagerAdapter(RecipeActivity.this, imageUrlList);

            viewPager.setAdapter(imagePagerAdapter);
            viewPager.setCurrentItem(0);

            LinePageIndicator titleIndicator = (LinePageIndicator) findViewById(R.id.titlepageindicator);
            titleIndicator.setViewPager(viewPager);

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public void slideToBottom(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);

    }

    public void ClearCommentInput() {
        commentEditPk = 0;
        recipe_comment_add_edit_text_view.setText("");
        recipe_comment_add_image_view.setImageResource(R.drawable.ic_send_black_24dp);
    }

    private void initializeTextViews() {
        recipe_author_text_view = (TextView) findViewById(R.id.recipe_author_text_view);
        recipe_author_text_view.setText(author);
        ViewCompat.setTransitionName(recipe_author_text_view, getResources().getString(R.string.transition_username));

        overview_text_view = (TextView) findViewById(R.id.overview_text_view);
        recipe_title_text_view = (TextView) findViewById(R.id.recipe_title_text_view);
        recipe_title_text_view.setText(recipeTitle);
        ViewCompat.setTransitionName(recipe_title_text_view, getResources().getString(R.string.transition_title));

        recipe_date_posted = (TextView) findViewById(R.id.recipe_date_posted);
        recipe_date_posted.setText(Utilities.DisplayTimeFormater(datePosted));
        ViewCompat.setTransitionName(recipe_date_posted, getResources().getString(R.string.transition_userdate));

        recipe_total_comments_text_view = (TextView) findViewById(R.id.recipe_total_comments_text_view);
        recipe_total_comments_text_view.setOnClickListener(this);
        recipe_total_images_text_view = (TextView) findViewById(R.id.recipe_total_images_text_view);
        summary_header_batch_size_text_view = (TextView) findViewById(R.id.summary_header_batch_size_text_view);

        summary_srm_text_view = (TextView) findViewById(R.id.summary_srm_text_view);
        summary_abv_text_view = (TextView) findViewById(R.id.summary_abv_text_view);
        summary_ibu_text_view = (TextView) findViewById(R.id.summary_ibu_text_view);
        summary_fg_text_view = (TextView) findViewById(R.id.summary_fg_text_view);
        summary_og_text_view = (TextView) findViewById(R.id.summary_og_text_view);

    }

    private void initializeEditTextViews() {
        recipe_comment_add_edit_text_view = (EditText) findViewById(R.id.recipe_comment_add_edit_text_view);
        recipe_comment_add_edit_text_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (recipe_comment_add_image_view.getVisibility() != View.VISIBLE) {
                    Animation in = AnimationUtils.loadAnimation(_mContext, R.anim.slide_down);
                    recipe_comment_add_image_view.startAnimation(in);
                    recipe_comment_add_image_view.setVisibility(View.VISIBLE);
                }

                if (s.length() == 0) {
                    slideToBottom(recipe_comment_add_image_view);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        recipe_comment_add_edit_text_view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mAdView.setVisibility(View.GONE);
                } else {
                    mAdView.setVisibility(View.VISIBLE);
                }
                if (commentEditPk > 0) {
                    ClearCommentInput();
                }
            }
        });
    }

    public void initializeListViews() {
        comments_list_view = (ListView) findViewById(R.id.comments_list_view);
        instructions_list_view = (ListView) findViewById(R.id.instructions_list_view);
        instructions_list_view.setDividerHeight(0);

        ingredient_list_view = (ListView) findViewById(R.id.ingredient_list_view);
        ingredient_list_view.setDividerHeight(0);
    }

    public void initializeButtons() {
        recipe_comments_load_more = (Button) findViewById(R.id.recipe_comments_load_more);
        recipe_comments_load_more.setOnClickListener(this);
        recipe_comments_load_more.setVisibility(View.GONE);

        mPlusOneButton = (PlusOneButton) findViewById(R.id.plus_one_button);
//        PlusShare.Builder
    }

    public void initializeLinearLayout() {

        footer = (LinearLayout) findViewById(R.id.footer);

//        if (previewMode) {
//            footer.setVisibility(View.GONE);
//        } else {
//            sticky_scroll.setOnTouchListener(new ShowHideOnScroll(footer));
//        }


        recipe_parent_layout = (LinearLayout) findViewById(R.id.recipe_parent_layout);
        recipe_parent_layout.setOnClickListener(this);

    }

    public void initializeCheckbox() {
        fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
    }

    public void initScrollView() {
        sticky_scroll = (ScrollView) findViewById(R.id.sticky_scroll);
    }

    public void initializeImageView() {
        recipe_header_user_image_view = (ImageView) findViewById(R.id.recipe_header_user_image_view);
        recipe_comment_add_image_view = (ImageView) findViewById(R.id.recipe_comment_add_image_view);
        recipe_comment_add_image_view.setVisibility(View.GONE);
        recipe_comment_add_image_view.setOnClickListener(this);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        if (listAdapter.getCount() <= 1) return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void addFabListener() {
        try {

            if (toggleStar) {
                fab.setImageResource(R.drawable.ic_star_white_24dp);
            } else {
                fab.setImageResource(R.drawable.ic_star_border_white_24dp);
            }

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (!BrewSharedPrefs.getIsUserLoggedIn()) {
//                        AlertLoginPrompt(_mContext, "", getText(R.string.text_login_to_favorite).toString(), getText(R.string.text_sign_in).toString(), getText(R.string.text_close).toString());
//                    } else {
//                        if (toggleStar) {
//                            fab.setImageResource(R.drawable.ic_star_border_white_24dp);
//                            toggleStar = false;
//                        } else {
//                            fab.setImageResource(R.drawable.ic_star_white_24dp);
//                            toggleStar = true;
//                        }
//                        String url = Constants.wcfAddUpdateFavorite + contentItemPk + "/" + BrewSharedPrefs.getUserToken();
//                        Ion.with(_mContext)
//                                .load(url)
//                                .asString();
//                    }
                }
            });
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    private void loadMoreComments() {
        int commentSize = comments.size() - 1;
        final Comment comment = comments.get(commentSize);

//        Ion.with(_mContext)
//                .load(Constants.wcfGetCommentSetByLastId + responseContentItemPk + "/" + comment.getCommentId() + "/6")
//                .setHeader("Cache-Control", "No-Cache")
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//                        if (result != null) {
//                            try {
//                                List<Comment> commentList = JsonToObject.JsonToCommentsList(result);
//                                if (commentList != null) {
//                                    for (Comment item : commentList) {
//                                        commentAdapter.addAll(item);
//                                    }
//                                    commentAdapter.notifyDataSetChanged();
//                                    setListViewHeightBasedOnChildren(comments_list_view);
//                                }
//
//                                if (comments.size() == comment_count) {
//                                    recipe_comments_load_more.setEnabled(false);
//                                    recipe_comments_load_more.setVisibility(View.GONE);
//                                }
//                                dialogProgress.dismiss();
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                    Log.e(Constants.LOG, e.getMessage());
//                                }
//                                dialogProgress.dismiss();
//                            }
//                        }
//                        dialogProgress.dismiss();
//                    }
//                });
    }

    public void AddUpdateComments() {
        try {
            if (!TextUtils.isEmpty(recipe_comment_add_edit_text_view.getText().toString().trim())) {
                JsonObject json = new JsonObject();
                json.addProperty("body", recipe_comment_add_edit_text_view.getText().toString().trim());

//                String test = BrewSharedPrefs.getUserToken();

//                Ion.with(_mContext)
//                        .load(Constants.wcfAddUpdateComment + commentEditPk + "/" + responseContentItemPk + "/" + BrewSharedPrefs.getUserToken() + "/" + String.valueOf(comments.size() + 1))
//                        .setHeader("Cache-Control", "No-Cache")
//                        .setJsonObjectBody(json)
//                        .asJsonArray()
//                        .setCallback(new FutureCallback<JsonArray>() {
//                            @Override
//                            public void onCompleted(Exception e, JsonArray result) {
//                                try {
//                                    if (result != null) {
//                                        try {
//                                            List<Comment> commentList = JsonToObject.JsonToCommentsList(result);//.ToCommentsListAdd(result);
//                                            comment_count = commentList.size();
//                                            comments = commentList; //needed to refresh original list
//                                            commentAdapter.clear();
//                                            addComments(commentList);
//                                            commentAdapter.notifyDataSetChanged();
//                                        } catch (Exception ex) {
//                                            if (BuildConfig.DEBUG) {
//                                                Log.e(Constants.LOG, ex.getMessage());
//                                            }
//                                        } finally {
//                                            dialogProgress.dismiss();
//                                            ClearCommentInput();
//                                        }
//                                    }
//                                } catch (Exception ex) {
//                                    if (BuildConfig.DEBUG) {
//                                        Log.e(Constants.LOG, ex.getMessage());
//                                    }
//                                }
//                            }
//                        });
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (currentPageIndex != position) {
                isFirstOrLastPage = false;
                return;
            }
            if ((position == 0 || position == imageCount - 1) && positionOffset == 0 && positionOffsetPixels == 0) {
                if (isFirstOrLastPage) {
//                    if (currentPageIndex == 0) {
//                        viewPager.setCurrentItem(imageCount - 1, true);
//                    } else {
//                        viewPager.setCurrentItem(0, true);
//                    }
                } else {
                    isFirstOrLastPage = true;

                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            currentPageIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            if (requestCode == RC_SIGN_IN) {
//                mIntentInProgress = false;
//                if (!mGoogleApiClient.isConnecting()) {
//                    mGoogleApiClient.connect();
//                }
//            }
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

//    @Override
//    public void onConnected(Bundle bundle) {
//        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
//            BrewSharedPrefs.setIsUserLoggedIn(true);
//            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
//            BrewSharedPrefs.setFullName(currentPerson.getDisplayName());
//            String personPhoto = currentPerson.getImage().getUrl();
//
//            try {
//                URL url = new URL(personPhoto);
//                personPhoto = personPhoto.replace(url.getQuery(), "");
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            BrewSharedPrefs.setGoolgePlusProfileUrl(currentPerson.getUrl());
//            BrewSharedPrefs.setUserProfileImageUrl(personPhoto);
//            BrewSharedPrefs.setEmailAddress(Plus.AccountApi.getAccountName(mGoogleApiClient));
//            PlusAccountSetup();
//        }
//    }

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
}
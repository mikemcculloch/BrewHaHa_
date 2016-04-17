package brightseer.com.brewhaha;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.adapter.FullScreenImageAdapter;
import brightseer.com.brewhaha.helper.TouchImageView;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.objects.RecipeImage;
import brightseer.com.brewhaha.objects.RecipeContent;
import brightseer.com.brewhaha.repository.JsonToObject;

public class FullScreenViewActivity extends BaseActivity implements View.OnClickListener {
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    private ArrayList<String> imageUrlList = new ArrayList<String>();
    List<RecipeImage> recipeImageList = new Vector<>();
    int position;
    private int imageCount = 0;
    private int mSelectedPageIndex;
    private int NextContentItemId = 0;
    String recipeTitle;
    private TextView pager_caption_text_view;
    private ImageView pager_expand_collapse_image;
    private LinearLayout pager_caption_layout, pager_caption_collapse_layout;
    Animation animFadein, animFadeout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupTransistion();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        _mContext = this;
        initAdMob();

        initGoogleAnalytics(this.getClass().getSimpleName());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(4);
//        viewPager.setOnPageChangeListener(pageChangeListener);
        viewPager.addOnPageChangeListener(pageChangeListener);

        initViews();


        toolbar = (Toolbar) findViewById(R.id.standard_toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
                ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                layoutParams.height = layoutParams.height + getStatusBarHeight();
            }
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            getSupportActionBar().setTitle(BrewSharedPrefs.getCurrentContentTitle());
        }

        Intent i = getIntent();
        position = i.getIntExtra("position", 0);
        String contentPk = i.getStringExtra(Constants.exContentItemPk);
        String userToken = BrewSharedPrefs.getUserToken();

        Ion.with(getApplicationContext())
                .load(Constants.wcfGetContentById + contentPk + "/" + userToken)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            if (result != null) {
                                RecipeContent recipeContent = JsonToObject.JsonToRecipeContent(result);
                                NextContentItemId = recipeContent.getNextContentItemId();
                                recipeTitle = recipeContent.getTitle();
                                BrewSharedPrefs.setLastContentItemPk(recipeContent.getContentItemPk());
                                recipeImageList = recipeContent.getImagesMList();
                                imageUrlList = new ArrayList<String>();
                                for (RecipeImage item : recipeImageList) {
                                    imageUrlList.add(item.getImageUrl());
                                }
                                imageCount = recipeImageList.size();
                                adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, imageUrlList);

                                viewPager.setAdapter(adapter);
                                viewPager.setCurrentItem(position);

                                viewPager.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        pageChangeListener.onPageSelected(viewPager.getCurrentItem());
                                    }
                                });

                            }
                        } catch (Exception ex) {
                            if (BuildConfig.DEBUG) {
                                Log.e(Constants.LOG, ex.getMessage());
                            }
                        }
                    }
                });
    }

    private void initViews() {
        ImageView pager_action_back_image = (ImageView) findViewById(R.id.pager_action_back_image);
        pager_action_back_image.setOnClickListener(this);
        ImageView pager_action_forward_image = (ImageView) findViewById(R.id.pager_action_forward_image);
        pager_action_forward_image.setOnClickListener(this);

        ImageView pager_download_image = (ImageView) findViewById(R.id.pager_download_image);
        pager_download_image.setOnClickListener(this);

        pager_expand_collapse_image = (ImageView) findViewById(R.id.pager_expand_collapse_image);
        pager_expand_collapse_image.setOnClickListener(this);

        pager_caption_text_view = (TextView) findViewById(R.id.pager_caption_text_view);

        pager_caption_layout = (LinearLayout) findViewById(R.id.pager_caption_layout);
        pager_caption_layout.setVisibility(View.INVISIBLE);

        pager_caption_collapse_layout = (LinearLayout) findViewById(R.id.pager_caption_collapse_layout);
    }

    private boolean isFirstOrLastPage;
    private boolean scrollDragged;

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mSelectedPageIndex != position) {
                isFirstOrLastPage = false;
                return;
            }
            // if ((position == 0 || position == imageCount - 1) && positionOffset == 0 && positionOffsetPixels == 0) {
            if ((position == imageCount - 1) && positionOffset == 0 && positionOffsetPixels == 0) {
                if (isFirstOrLastPage) {
                } else {
                    isFirstOrLastPage = true;

                }
            }
        }

        @Override
        public void onPageSelected(int position) {

            getSupportActionBar().setSubtitle(String.valueOf(position + 1) + " of " + String.valueOf(imageCount));
//            toolbar.setSubtitle(String.valueOf(position + 1) + " of " + String.valueOf(imageCount));
            mSelectedPageIndex = position;
            String text = recipeImageList.get(position).getCaption();
            pager_caption_text_view.setText(text);

            pager_expand_collapse_image.setVisibility(View.VISIBLE);
            pager_caption_collapse_layout.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(text)) {
                pager_caption_layout.setVisibility(View.INVISIBLE);
                pager_expand_collapse_image.setVisibility(View.GONE);
                pager_caption_collapse_layout.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (isFirstOrLastPage) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    scrollDragged = true;
                }
                if (state == ViewPager.SCROLL_STATE_IDLE && scrollDragged && NextContentItemId > 0) {
                    LoadRecipe();
                }
            } else {
                scrollDragged = false;
            }
        }


    };

    public void LoadRecipe() {
        Intent goToRecipe = new Intent(getApplicationContext(), RecipeActivity.class);

        goToRecipe.putExtra(Constants.exRecipeTitle, recipeTitle);
        goToRecipe.putExtra(Constants.exContentItemPk, String.valueOf(NextContentItemId));
        goToRecipe.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            ActivityCompat.startActivity(this, goToRecipe, options.toBundle());
            finishAfterTransition();
        } else {
            startActivity(goToRecipe);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            finish();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search_main).setVisible(true);
//        menu.findItem(R.id.menu_item_download).setVisible(false);
//        menu.findItem(R.id.action_menu_options).setVisible(false);
//        menu.findItem(R.id.menu_item_share).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
//        myVib.vibrate(100);
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        switch (v.getId()) {
            case R.id.pager_action_back_image:
                viewPager.setCurrentItem(mSelectedPageIndex - 1, true);
                break;
            case R.id.pager_action_forward_image:
                viewPager.setCurrentItem(mSelectedPageIndex + 1, true);

                if (isFirstOrLastPage && NextContentItemId > 0) {
                    LoadRecipe();
                }

                break;
            case R.id.pager_download_image:
                View currentPage = viewPager.findViewWithTag(viewPager.getCurrentItem());
                TouchImageView imgDisplay = (TouchImageView) currentPage.findViewById(R.id.imgDisplay);
                Utilities.saveImage(imgDisplay);
                break;
            case R.id.pager_expand_collapse_image:
                if (pager_caption_layout.getVisibility() == View.INVISIBLE) {
                    animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.fade_in);

                    animFadein.setAnimationListener(animationListenerFadeIn);
                    pager_caption_layout.startAnimation(animFadein);

                    pager_expand_collapse_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                } else {
                    animFadeout = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.fade_out);
                    animFadeout.setAnimationListener(animationListenerFadeOut);
                    pager_caption_layout.startAnimation(animFadeout);

                    pager_expand_collapse_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                }

                break;
        }
    }

    Animation.AnimationListener animationListenerFadeOut = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            pager_caption_layout.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    Animation.AnimationListener animationListenerFadeIn = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            pager_caption_layout.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}

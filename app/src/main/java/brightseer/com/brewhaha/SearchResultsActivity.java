package brightseer.com.brewhaha;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.BitmapInfo;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.adapter.SearchResultRecycler;
import brightseer.com.brewhaha.objects.MainFeedItem;
import brightseer.com.brewhaha.repository.JsonToObject;

public class SearchResultsActivity extends BaseActivity {
    String query = "na";
    int selectedStyle = 0, selectedType = 0, abvValue = 0, ibuValue = 0, grainPk = 0, hopsPk = 0, yeastPk = 0;
    private List<MainFeedItem> mainFeedItemList = new Vector<>();
    private SearchResultRecycler adapter;
    private RecyclerView search_results_recycler_view;

    private int previousTotal = 0;
    private boolean loadingBool = true;
    private int visibleThreshold = 5;
    private int firstVisibleItem, visibleItemCount, totalItemCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupTransistion();
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(final Intent intent) {
        setContentView(R.layout.activity_search_results);
        initGoogleAnalytics(this.getClass().getSimpleName());
        initAdMob();
        initViews();
        _mContext = this;
        LoadDialog(_mContext, false, true);

        query = intent.getStringExtra(SearchManager.QUERY);
        if (query == null) {
            query = "na";
        }
        selectedStyle = intent.getIntExtra(Constants.exStyleValue, 0);
        selectedType = intent.getIntExtra(Constants.exRecipeTypeValue, 0);
        abvValue = intent.getIntExtra(Constants.exAbvValue, 0);
        ibuValue = intent.getIntExtra(Constants.exIbuValue, 0);
        grainPk = intent.getIntExtra(Constants.exGrainPk, 0);
        hopsPk = intent.getIntExtra(Constants.exHopsPk, 0);
        yeastPk = intent.getIntExtra(Constants.exYeastPk, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.standard_toolbar);
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
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }

        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
        suggestions.saveRecentQuery(query, null);

        try {
            int screenOrientation = getResources().getConfiguration().orientation;
            search_results_recycler_view = (RecyclerView) findViewById(R.id.search_results_recycler_view);
//            search_results_recycler_view.setItemAnimator(new SlideScaleInOutRightItemAnimator(search_results_recycler_view));
            search_results_recycler_view.setHasFixedSize(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                search_results_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider, getTheme()), true));
            } else {
                search_results_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
            }
//            if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                final LinearLayoutManager layoutManager = new LinearLayoutManager(_mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                layoutManager.scrollToPosition(0);
                search_results_recycler_view.setLayoutManager(layoutManager);
//            }
//            if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
//                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//
//                layoutManager.scrollToPosition(0);
//                search_results_recycler_view.setLayoutManager(layoutManager);
//            }

            search_results_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    visibleItemCount = search_results_recycler_view.getChildCount();
                    totalItemCount = adapter.getItemCount();
                    firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                    if (loadingBool) {
                        if (totalItemCount > previousTotal) {
                            loadingBool = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loadingBool && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + visibleThreshold)) {
                        load();
                        loadingBool = true;
                    }
                }
            });



            List<MainFeedItem> placeHolder = new Vector<>();
            adapter = new SearchResultRecycler(placeHolder, SearchResultsActivity.this);
            search_results_recycler_view.setAdapter(adapter);
//            search_results_recycler_view.addOnItemTouchListener(
//                    new RecyclerItemClickListener(_mContext, search_results_recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(View view, int position) {
//                            try {
//                                MainFeedItem homeItem = mainFeedItemList.get(position);
//                                Intent getNameScreenIntent = null;
//                                if (homeItem.getItemTypeId() == 1) {
//                                    getNameScreenIntent = new Intent(_mContext, RecipeActivity.class);
//                                }
//                                if (homeItem.getItemTypeId() == 2) {
//                                    getNameScreenIntent = new Intent(_mContext, GridViewActivity.class);
//                                }
//                                getNameScreenIntent.putExtra(Constants.exContentItemPk, String.valueOf(homeItem.getRecipeContentId()));
//                                getNameScreenIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SearchResultsActivity.this, view.findViewById(R.id.itemTitle), "recipeTitle");
//                                    ActivityCompat.startActivity(SearchResultsActivity.this, getNameScreenIntent, options.toBundle());
//                                } else {
//                                    startActivity(getNameScreenIntent);
//                                    overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
//                                }
//
//                            } catch (Exception e) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, e.getMessage());
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onItemLongClick(View view, int position) {
//
//                        }
//                    })
//            );
            load();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public void load() {
        if (loading != null && !loading.isDone() && !loading.isCancelled())
            return;

        String url = Constants.wcfGetHomeContentFromSearch + "0/" + String.valueOf(selectedType) + "/" + String.valueOf(selectedStyle) + "/" + String.valueOf(abvValue) + "/" + String.valueOf(ibuValue) + "/" + String.valueOf(grainPk) + "/" + String.valueOf(hopsPk) + "/" + String.valueOf(yeastPk);
        if (mainFeedItemList != null) {
            if (mainFeedItemList.size() > 0) {
                MainFeedItem mainFeedItem = mainFeedItemList.get(mainFeedItemList.size() - 1);
                url = Constants.wcfGetHomeContentFromSearch + String.valueOf(mainFeedItem.getContentItemPk()) + "/" + String.valueOf(selectedType) + "/" + String.valueOf(selectedStyle) + "/" + String.valueOf(abvValue) + "/" + String.valueOf(ibuValue) + "/" + String.valueOf(grainPk) + "/" + String.valueOf(hopsPk) + "/" + String.valueOf(yeastPk);
            }
        }
        JsonObject json = new JsonObject();
        json.addProperty("searchText", query.trim());
        loading = Ion.with(getApplicationContext())
                .load(url)
                .setHeader("Cache-Control", "No-Cache")
                .setJsonObjectBody(json)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            dialogProgress.dismiss();
                            if (result != null) {
                                List<MainFeedItem> resultsList = JsonToObject.JsonToHomeItemList(result);
                                for (MainFeedItem item : resultsList) {
                                    mainFeedItemList.add(item);
                                    adapter.add(item, mainFeedItemList.size() - 1);
                                }
                            }
                            if (mainFeedItemList.size() == 0) {
                                search_results_recycler_view.setVisibility(View.GONE);
                            }
                        } catch (Exception ex) {
                            if (BuildConfig.DEBUG) {
                                Log.e(Constants.LOG, ex.getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_menu_options).setVisible(false);
        menu.findItem(R.id.menu_item_share).setVisible(false);
        menu.findItem(R.id.action_search_main).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    private void initViews() {

        SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_search_swipe_refresh_layout);
        mSwipeRefreshLayout.setEnabled(false);
    }

    public void openDetailActivity(View view, int position) {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setElevation(10);
            }

            MainFeedItem mainFeedItem = mainFeedItemList.get(position);
            Intent newIntent = new Intent();
            if (mainFeedItem.getItemTypeId() == 1) {
                newIntent = new Intent(this, RecipeActivity.class);
            }
            if (mainFeedItem.getItemTypeId() == 2) {
                newIntent = new Intent(this, GridViewActivity.class);
            }

            eventGoogleAnalytics(Constants.gacRecipe, Constants.gacOpen, mainFeedItem.getTitle());

            newIntent.putExtra(Constants.exRecipeTitle, mainFeedItem.getTitle());
            newIntent.putExtra(Constants.exContentItemPk, String.valueOf(mainFeedItem.getContentItemPk()));
            newIntent.putExtra(Constants.exPosition, position);

            newIntent.putExtra(Constants.exUsername, mainFeedItem.getAuthor());
            newIntent.putExtra(Constants.exUserdate, String.valueOf(mainFeedItem.getDateCreated()));

            newIntent.putExtra(Constants.exAuthorImage, mainFeedItem.getUserImageUrl());
            newIntent.putExtra(Constants.exRecipeImage, mainFeedItem.getImageUrl());

            BitmapInfo bi = Ion.with((ImageView) view.findViewById(R.id.home_row_user_image_view)).getBitmapInfo();
            newIntent.putExtra(Constants.exBitMapInfo, bi.key);

            newIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Pair p1 = Pair.create(view.findViewById(R.id.itemAuthor), getResources().getString(R.string.transition_username));
                Pair p2 = Pair.create(view.findViewById(R.id.home_row_time_from_post_text_view), getResources().getString(R.string.transition_userdate));
                Pair p3 = Pair.create(view.findViewById(R.id.itemTitle), getResources().getString(R.string.transition_title));
//                Pair p4 = Pair.create(view.findViewById(R.id.image), getResources().getString(R.string.transition_bitmapmain));
                Pair p5 = Pair.create(view.findViewById(R.id.home_row_user_image_view), getResources().getString(R.string.transition_bitmapuser));
                Pair p6 = Pair.create(view.findViewById(R.id.plus_one_button), getResources().getString(R.string.transition_googlePlus));

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, p1, p2, p3, p5, p6);
                ActivityCompat.startActivityForResult(this, newIntent, 0, options.toBundle());

          } else {
                startActivity(newIntent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }
}
package brightseer.com.brewhaha.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.JsonArray;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.BitmapInfo;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.GridViewActivity;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.RecipeActivity;
import brightseer.com.brewhaha.adapter.HomeItemAdapter;
import brightseer.com.brewhaha.objects.HomeItem;
import brightseer.com.brewhaha.repository.JsonToObject;

public class HomeFragment extends BaseFragment {
    private List<HomeItem> homeItemList = new Vector<>();
    private HomeItemAdapter adapter;
    private String userToken = "na";
    private RecyclerView home_recycler_view;
    private View rootView;

    private int previousTotal = 0;
    private boolean loadingBool = true;
    private int visibleThreshold = 4;
    private int firstVisibleItem, visibleItemCount, totalItemCount;

//    SwipeRefreshLayout mSwipeRefreshLayout;

    public HomeFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        _fContext = getActivity();
        initGoogleAnalytics(this.getClass().getSimpleName());
////        initAdMob(rootView);
        initViews();

        return rootView;
    }

    private void initViews() {
//        mPlusOneButton = (PlusOneButton) rootView.findViewById(R.id.plus_one_button);

//        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.app_orange, R.color.app_yellow, R.color.app_blue);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                home_recycler_view.setVisibility(View.VISIBLE);
//                homeItemList = new Vector<>();
//                adapter.clear();
////                LoadDialog(_fContext, false, false);
//                load();
//            }
//        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            int screenOrientation = getResources().getConfiguration().orientation;

            if (!TextUtils.isEmpty(BrewSharedPrefs.getUserToken())) {
                userToken = BrewSharedPrefs.getUserToken();
            }

            home_recycler_view = (RecyclerView) view.findViewById(R.id.home_recycler_view);
            home_recycler_view.setItemAnimator(new DefaultItemAnimator());
            home_recycler_view.setHasFixedSize(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                home_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider, getActivity().getTheme()), true));
            } else {
                home_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
            }
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);
            home_recycler_view.setLayoutManager(layoutManager);

            home_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    visibleItemCount = home_recycler_view.getChildCount();
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


            List<HomeItem> placeHolder = new Vector<>();
            adapter = new HomeItemAdapter(placeHolder, HomeFragment.this);
            home_recycler_view.setAdapter(adapter);
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

        String url = Constants.wcfGetHomeContentListByLastId + "0/" + userToken + "/" + "0/false";
        if (homeItemList != null) {
            if (homeItemList.size() > 0) {
                HomeItem homeItem = homeItemList.get(homeItemList.size() - 1);
                url = Constants.wcfGetHomeContentListByLastId + String.valueOf(homeItem.getContentItemPk()) + "/" + userToken + "/" + "0/false";
            }
        }
        LoadDialog(_fContext, false, true);
        loading = Ion.with(getActivity().getApplicationContext())
                .load(url)
                .setHeader("Cache-Control", "No-Cache")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                                 @Override
                                 public void onCompleted(Exception e, JsonArray jsonArray) {
                                     try {
//                                         mSwipeRefreshLayout.setRefreshing(false);
//                                         dialogProgress.dismiss();
                                         if (jsonArray != null) {
                                             List<HomeItem> resultsList = JsonToObject.JsonToHomeItemList(jsonArray);

                                             for (HomeItem item : resultsList) {
                                                 homeItemList.add(item);
                                                 adapter.add(item, homeItemList.size() - 1);
                                             }
                                         }
                                         if (homeItemList.size() == 0) {
                                             home_recycler_view.setVisibility(View.GONE);
                                         }
                                     } catch (Exception ex) {
                                         if (BuildConfig.DEBUG) {
                                             Log.e(Constants.LOG, ex.getMessage());
                                         }
                                     } finally {
                                         dialogProgress.dismiss();
                                     }
                                 }
                             }
                );
    }

    public void openDetailActivity(View view, int position) {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setElevation(10);
            }

            HomeItem homeItem = homeItemList.get(position);
            Intent newIntent = new Intent();
            if (homeItem.getItemTypePk() == 1) {
                newIntent = new Intent(_fContext, RecipeActivity.class);
            }
            if (homeItem.getItemTypePk() == 2) {
                newIntent = new Intent(_fContext, GridViewActivity.class);
            }

            eventGoogleAnalytics(Constants.gacRecipe, Constants.gacOpen, homeItem.getTitle());

            newIntent.putExtra(Constants.exRecipeTitle, homeItem.getTitle());
            newIntent.putExtra(Constants.exContentItemPk, String.valueOf(homeItem.getContentItemPk()));
            newIntent.putExtra(Constants.exPosition, position);

            newIntent.putExtra(Constants.exUsername, homeItem.getAuthor());
            newIntent.putExtra(Constants.exUserdate, String.valueOf(homeItem.getTimestamp()));

            newIntent.putExtra(Constants.exAuthorImage, homeItem.getUserImageUrl());
            newIntent.putExtra(Constants.exRecipeImage, homeItem.getImageUrl());

            BitmapInfo bi = Ion.with((ImageView) view.findViewById(R.id.home_row_user_image_view)).getBitmapInfo();
            newIntent.putExtra(Constants.exBitMapInfo, bi.key);

            BitmapInfo biMain = Ion.with((ImageView) view.findViewById(R.id.image)).getBitmapInfo();
            newIntent.putExtra(Constants.exBitMapInfoMain, biMain.key);

            newIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Pair p1 = Pair.create(view.findViewById(R.id.itemAuthor), getResources().getString(R.string.transition_username));
                Pair p2 = Pair.create(view.findViewById(R.id.home_row_time_from_post_text_view), getResources().getString(R.string.transition_userdate));
                Pair p3 = Pair.create(view.findViewById(R.id.itemTitle), getResources().getString(R.string.transition_title));
                Pair p4 = Pair.create(view.findViewById(R.id.image), getResources().getString(R.string.transition_bitmapmain));
                Pair p5 = Pair.create(view.findViewById(R.id.home_row_user_image_view), getResources().getString(R.string.transition_bitmapuser));
                Pair p6 = Pair.create(view.findViewById(R.id.plus_one_button), getResources().getString(R.string.transition_googlePlus));

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), p1, p2, p3, p4, p5, p6);
                ActivityCompat.startActivityForResult(getActivity(), newIntent, 0, options.toBundle());

//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.itemTitle), "recipeTitle");
//                ActivityCompat.startActivity(getActivity(), newIntent, options.toBundle());
            } else {
                startActivity(newIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

}
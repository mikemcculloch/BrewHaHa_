package brightseer.com.brewhaha.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.GridViewActivity;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.RecipeActivity;
import brightseer.com.brewhaha.adapter.FavoriteItemRecycler;
import brightseer.com.brewhaha.adapter.RecyclerItemClickListener;
import brightseer.com.brewhaha.objects.MainFeedItem;
import brightseer.com.brewhaha.repository.JsonToObject;

/**
 * Created by mccul_000 on 11/16/2014.
 */
public class FavoriteItemsFragment extends BaseFragment {
    private List<MainFeedItem> mainFeedItemList = new Vector<>();
    private FavoriteItemRecycler adapter;
    private RecyclerView list_view_main;

    private String userToken = "na";
    private int listType = 0;
    private ImageView home_featured_holder;
    private View rootView, home_featured_holder_break;
    private TextView home_error_message_text_view;
//    SwipeRefreshLayout mSwipeRefreshLayout;

    public FavoriteItemsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        _fContext = getActivity();
        initGoogleAnalytics(this.getClass().getSimpleName());
//        initViews();
        return rootView;
    }

    private void initViews() {
//        home_featured_holder_break = rootView.findViewById(R.id.home_featured_holder_break);
//        home_featured_holder_break.setVisibility(View.GONE);
//        home_featured_holder = (ImageView) rootView.findViewById(R.id.home_featured_holder);
//        home_featured_holder.setVisibility(View.GONE);
//        home_error_message_text_view = (TextView) rootView.findViewById(R.id.home_error_message_text_view);
//        home_error_message_text_view.setVisibility(View.GONE);

        AdView hideMe = (AdView) rootView.findViewById(R.id.adView);
        hideMe.setVisibility(View.GONE);

//        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.app_orange, R.color.app_yellow, R.color.app_blue);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                list_view_main.setVisibility(View.VISIBLE);
//                home_error_message_text_view.setVisibility(View.GONE);
//                mainFeedItemList = new Vector<MainFeedItem>();
//                adapter.clear();
//                LoadDialog(_fContext, false, false);
//                load();
//            }
//        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadDialog(_fContext, false, true);
        Bundle bundle = this.getArguments();
        try {
            int screenOrientation = getResources().getConfiguration().orientation;
            if (bundle != null) {
                listType = bundle.getInt("listType");
            }
            if (!TextUtils.isEmpty(BrewSharedPrefs.getUserToken())) {
                userToken = BrewSharedPrefs.getUserToken();
            }
            list_view_main = (RecyclerView) view.findViewById(R.id.home_recycler_view);
            list_view_main.setHasFixedSize(false);

            list_view_main = (RecyclerView) view.findViewById(R.id.home_recycler_view);
            list_view_main.setHasFixedSize(false);

            if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                layoutManager.scrollToPosition(0);
                list_view_main.setLayoutManager(layoutManager);
            }
            if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

                layoutManager.scrollToPosition(0);
                list_view_main.setLayoutManager(layoutManager);
            }
            List<MainFeedItem> placeHolder = new Vector<>();
            adapter = new FavoriteItemRecycler(placeHolder, FavoriteItemsFragment.this);

            list_view_main.setAdapter(adapter);

            list_view_main.setItemAnimator(new DefaultItemAnimator());
            list_view_main.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity().getBaseContext(), list_view_main, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                MainFeedItem mainFeedItem = mainFeedItemList.get(position);
                                Intent getNameScreenIntent = null;
                                if (mainFeedItem.getItemTypeId() == 1) {
                                    getNameScreenIntent = new Intent(getActivity().getApplicationContext(), RecipeActivity.class);
                                }
                                if (mainFeedItem.getItemTypeId() == 2) {
                                    getNameScreenIntent = new Intent(getActivity().getApplicationContext(), GridViewActivity.class);
                                }

                                getNameScreenIntent.putExtra(Constants.exRecipeTitle, mainFeedItem.getTitle());
                                getNameScreenIntent.putExtra(Constants.exContentItemPk, String.valueOf(mainFeedItem.getContentItemPk()));
                                getNameScreenIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.itemTitle), "recipeTitle");
                                    ActivityCompat.startActivity(getActivity(), getNameScreenIntent, options.toBundle());
                                } else {
                                    startActivity(getNameScreenIntent);
                                    getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                                }
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

        String url = Constants.wcfGetHomeContentListByLastId + "0/" + userToken + "/" + listType + "/true";
        if (mainFeedItemList != null) {
            if (mainFeedItemList.size() > 0) {
                MainFeedItem mainFeedItem = mainFeedItemList.get(mainFeedItemList.size() - 1);
                url = Constants.wcfGetHomeContentListByLastId + String.valueOf(mainFeedItem.getContentItemPk()) + "/" + userToken + "/" + listType + "/true";
            }
        }
        loading = Ion.with(_fContext)
                .load(url)
                .setHeader("Cache-Control", "No-Cache")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray jsonArray) {
                        try {
//                            mSwipeRefreshLayout.setRefreshing(false);
                            dialogProgress.dismiss();
                            if (jsonArray != null) {
                                List<MainFeedItem> resultsList = JsonToObject.JsonToHomeItemList(jsonArray);
                                for (MainFeedItem item : resultsList) {
                                    mainFeedItemList.add(item);
                                    adapter.add(item, mainFeedItemList.size() - 1);
                                }
                            }
                            if (mainFeedItemList.size() == 0) {
                                list_view_main.setVisibility(View.GONE);
                                home_error_message_text_view.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception ex) {
                            if (BuildConfig.DEBUG) {
                                Log.e(Constants.LOG, ex.getMessage());
                            }
                        }
                    }
                });
    }
}
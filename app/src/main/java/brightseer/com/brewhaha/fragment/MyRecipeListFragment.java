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

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.AddUpdateRecipe;
import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.adapter.AddRecipeRecycler;
import brightseer.com.brewhaha.models.MainFeedItem;
import brightseer.com.brewhaha.swipedismiss.SwipeToDismissTouchListener;
import brightseer.com.brewhaha.swipedismiss.adapter.RecyclerViewAdapter;

/**
 * Created by Michael McCulloch on 2/25/2015.
 */
public class MyRecipeListFragment extends BaseFragment {
    private View rootView;
    //    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeToDismissTouchListener<RecyclerViewAdapter> swipeTouchListener;
    private RecyclerView recycle_add_recipe_view;
    private List<MainFeedItem> mainFeedItemList = new Vector<>();
    private AddRecipeRecycler adapter;
    private String userToken;
    //    private FloatingActionButton fab;
    private MainFeedItem itemRemoveMe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        _fContext = getActivity();
        initAdMob(rootView);
        initGoogleAnalytics(this.getClass().getSimpleName());
        initViews();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadDialog(_fContext, false, true);
        try {
            initRecycler();
            load();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    private void initViews() {
//        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.app_orange, R.color.app_yellow, R.color.app_blue);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                recycle_add_recipe_view.setVisibility(View.VISIBLE);
//                mainFeedItemList = new Vector<MainFeedItem>();
//                adapter.clear();
//                load();
//            }
//        });

//        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
//        fab.setColor(getResources().getColor(R.color.app_blue));
//        fab.setVisibility(View.VISIBLE);
    }

    public void load() {
//        if (loading != null && !loading.isDone() && !loading.isCancelled())
//            return;
//
//        String url = Constants.wcfGetUserContentByLastId + "0/" + userToken;
//        if (mainFeedItemList != null) {
//            if (mainFeedItemList.size() > 0) {
//                MainFeedItem mainFeedItem = mainFeedItemList.get(mainFeedItemList.size() - 1);
//                url = Constants.wcfGetUserContentByLastId + String.valueOf(mainFeedItem.getContentItemPk()) + "/" + userToken;
//            }
//        }
//        loading = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray jsonArray) {
//                        try {
////                            mSwipeRefreshLayout.setRefreshing(false);
//                            dialogProgress.dismiss();
//                            if (jsonArray != null) {
//                                List<MainFeedItem> resultsList = JsonToObject.JsonToHomeItemList(jsonArray);
//                                for (MainFeedItem item : resultsList) {
//                                    mainFeedItemList.add(item);
//                                    adapter.add(item, mainFeedItemList.size() - 1);
//                                }
//                            }
////                            addFabListener();
//                        } catch (Exception ex) {
//                            if (BuildConfig.DEBUG) {
//                                Log.e(Constants.LOG, ex.getMessage());
//                            }
//                        }
//                    }
//                });
    }

//    public void addFabListener() {
//        try {
//            fab.setVisibility(View.VISIBLE);
//            fab.initBackground();
//            fab.setBackgroundResource(R.drawable.bottlecap_small);
//            fab.setImageResource(R.mipmap.ic_add_black_24dp);
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    StartAddUpdate("");
//                }
//            });
//        } catch (Exception e) {
//            if (BuildConfig.DEBUG) {
//                Log.e(Constants.LOG, e.getMessage());
//            }
//        }
//    }

    public void StartAddUpdate(String contentPk) {
        try {
            Intent newActivityIntent = new Intent(getActivity(), AddUpdateRecipe.class);
            newActivityIntent.putExtra(Constants.spContentToken, contentToken);
            newActivityIntent.putExtra(Constants.exContentItemPk, contentPk);
            newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                ActivityCompat.startActivity(getActivity(), newActivityIntent, options.toBundle());
            } else {
                startActivity(newActivityIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    private void RemoveRecipe(final int position) {
        try {
            itemRemoveMe = mainFeedItemList.get(position);
            adapter.remove(itemRemoveMe, position);
            mainFeedItemList.remove(position);
//            RemoveFromDB();
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

//    private void RemoveFromDB() {
//        String token = String.valueOf(itemRemoveMe.getToken());
//        String url = Constants.wcfRemoveRecipe + BrewSharedPrefs.getUserToken() + "/" + token;
//        Ion.with(_fContext)
//                .load(url)
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String s) {
//                        try {
//                            Boolean success = Boolean.valueOf(s);
//
//                        } catch (Exception ex) {
//                            if (BuildConfig.DEBUG) {
//                                Log.e(Constants.LOG, ex.getMessage());
//                            }
//                        }
//                        itemRemoveMe = null;
//                    }
//                });
//    }

    private void initRecycler() {
        recycle_add_recipe_view = (RecyclerView) rootView.findViewById(R.id.recycle_add_recipe_view);
        int screenOrientation = getResources().getConfiguration().orientation;

        if (!TextUtils.isEmpty(BrewSharedPrefs.getUserToken())) {
            userToken = BrewSharedPrefs.getUserToken();
        }

        recycle_add_recipe_view.setHasFixedSize(true);

        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);
            recycle_add_recipe_view.setLayoutManager(layoutManager);
        }
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

            layoutManager.scrollToPosition(0);
            recycle_add_recipe_view.setLayoutManager(layoutManager);
        }
        List<MainFeedItem> placeHolder = new Vector<>();
        adapter = new AddRecipeRecycler(placeHolder, MyRecipeListFragment.this);

        recycle_add_recipe_view.setAdapter(adapter);
        recycle_add_recipe_view.setItemAnimator(new DefaultItemAnimator());

//        final SwipeToDismissTouchListener touchListener =
//                new SwipeToDismissTouchListener(
//                        new RecyclerViewAdapter(recycle_add_recipe_view),
//                        new SwipeToDismissTouchListener.DismissCallbacks() {
//                            @Override
//                            public boolean canDismiss(int position) {
//                                return !mainFeedItemList.get(position).isApproved();
//                            }
//
//                            @Override
//                            public void onDismiss(ViewAdapter recyclerView, int position) {
//                                RemoveRecipe(position);
//                            }
//                        });

//        recycle_add_recipe_view.setOnTouchListener(touchListener);
//        recycle_add_recipe_view.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
//        recycle_add_recipe_view.addOnItemTouchListener(new SwipeableItemClickListener(_fContext,
//                new OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        try {
//                            if (view.getId() == R.id.txt_delete) {
//                                touchListener.processPendingDismisses();
////
//                            } else if (view.getId() == R.id.txt_undo) {
//                                touchListener.undoPendingDismiss();
//                            } else {
//                                openContent(position);
//                            }
//                        } catch (Exception e) {
//                            if (BuildConfig.DEBUG) {
//                                Log.e(Constants.LOG, e.getMessage());
//                            }
//                        }
//                    }
//                }));
    }

    private void openContent(int position) {
        try {
//            MainFeedItem mainFeedItem = mainFeedItemList.get(position);
//            if (mainFeedItem.getItemTypeId() == 1) {
//                contentToken = mainFeedItem.getToken();
//                StartAddUpdate(String.valueOf(mainFeedItem.getContentItemPk()));
//            }
//            if (mainFeedItem.getItemTypeId() == 2) {
//                Intent getNameScreenIntent = new Intent(getActivity().getApplicationContext(), GridViewActivity.class);
//                getNameScreenIntent.putExtra(Constants.exContentItemPk, String.valueOf(mainFeedItem.getContentItemPk()));
//                getNameScreenIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
//                    ActivityCompat.startActivity(getActivity(), getNameScreenIntent, options.toBundle());
//                } else {
//                    startActivity(getNameScreenIntent);
//                    getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
//                }
//
//
//            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }


//    Thread closeActivity = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            try {
//                Thread.sleep(3000);
//                swipeTouchListener.processPendingDismisses();
//            } catch (Exception e) {
//                e.getLocalizedMessage();
//            }
//        }
//    });


}

package brightseer.com.brewhaha.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.adapter.AdminRecipeRecycler;
import brightseer.com.brewhaha.models.MainFeedItem;
import brightseer.com.brewhaha.repository.JsonToObject;
import brightseer.com.brewhaha.swipedismiss.OnItemClickListener;
import brightseer.com.brewhaha.swipedismiss.SwipeToDismissTouchListener;
import brightseer.com.brewhaha.swipedismiss.SwipeableItemClickListener;
import brightseer.com.brewhaha.swipedismiss.adapter.RecyclerViewAdapter;
import brightseer.com.brewhaha.swipedismiss.adapter.ViewAdapter;

/**
 * Created by wooan_000 on 4/19/2015.
 */
public class AdminFragment extends BaseFragment {
    private View rootView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeToDismissTouchListener<RecyclerViewAdapter> swipeTouchListener;
    private RecyclerView recyclerViewAdmin;
    private List<MainFeedItem> mainFeedItemList = new Vector<>();
    private AdminRecipeRecycler adapter;
    private String userToken;
    private MainFeedItem itemApproveMe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin_recipe, container, false);
        _fContext = getActivity();

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
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.admin_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.app_orange, R.color.app_yellow, R.color.app_blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerViewAdmin.setVisibility(View.VISIBLE);
                mainFeedItemList = new Vector<>();
                adapter.clear();
                load();
            }
        });
    }

    public void load() {
//        if (loading != null && !loading.isDone() && !loading.isCancelled())
//            return;
//
//        String url = Constants.wcfGetAllPendingApproval + BrewSharedPrefs.getUserKey();
//        loading = Ion.with(_fContext)
//                .load(url)
//                .setHeader("Cache-Control", "No-Cache")
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray jsonArray) {
//                        try {
//                            mSwipeRefreshLayout.setRefreshing(false);
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

    private void Approve(final int position) {
        try {
            itemApproveMe = mainFeedItemList.get(position);
            adapter.remove(itemApproveMe, position);
            mainFeedItemList.remove(position);
            ApproveRecipe();
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void ApproveRecipe() {
//        String token = String.valueOf(itemApproveMe.getToken());
//        String url = Constants.wcfApproveRequest + BrewSharedPrefs.getUserKey() + "/" + token;
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
//                        itemApproveMe = null;
//                    }
//                });
    }

    private void initRecycler() {
        recyclerViewAdmin = (RecyclerView) rootView.findViewById(R.id.admin_recycle_view);
        int screenOrientation = getResources().getConfiguration().orientation;

//        if (!TextUtils.isEmpty(BrewSharedPrefs.getUserKey())) {
//            userToken = BrewSharedPrefs.getUserKey();
//        }

        recyclerViewAdmin.setHasFixedSize(true);

        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);
            recyclerViewAdmin.setLayoutManager(layoutManager);
        }
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

            layoutManager.scrollToPosition(0);
            recyclerViewAdmin.setLayoutManager(layoutManager);
        }
        List<MainFeedItem> placeHolder = new Vector<>();
        adapter = new AdminRecipeRecycler(placeHolder, AdminFragment.this);

        recyclerViewAdmin.setAdapter(adapter);
        recyclerViewAdmin.setItemAnimator(new DefaultItemAnimator());

        final SwipeToDismissTouchListener touchListener =
                new SwipeToDismissTouchListener(
                        new RecyclerViewAdapter(recyclerViewAdmin),
                        new SwipeToDismissTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
//                                if (mainFeedItemList.get(position).isApproved())
//                                {
//                                return false;
//                                }
                                return true;
                            }

                            @Override
                            public void onDismiss(ViewAdapter recyclerView, int position) {
                                Approve(position);
                            }
                        });

        recyclerViewAdmin.setOnTouchListener(touchListener);
        recyclerViewAdmin.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
        recyclerViewAdmin.addOnItemTouchListener(new SwipeableItemClickListener(_fContext,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            if (view.getId() == R.id.txt_approve) {
                                touchListener.processPendingDismisses();
//
                            } else if (view.getId() == R.id.txt_undo) {
                                touchListener.undoPendingDismiss();
                            } else {
                                openContent(position);
                            }
                        } catch (Exception e) {
                            if (BuildConfig.DEBUG) {
                                Log.e(Constants.LOG, e.getMessage());
                            }
                        }
                    }
                }));
    }

    private void openContent(int position) {
        try {
//            MainFeedItem mainFeedItem = mainFeedItemList.get(position);
//            Intent recipeIntent = null;
//            if (mainFeedItem.getItemTypeId() == 1) {
//                recipeIntent = new Intent(getActivity().getApplicationContext(), RecipeActivity.class);
//            }
//            if (mainFeedItem.getItemTypeId() == 2) {
//                recipeIntent = new Intent(getActivity().getApplicationContext(), GridViewActivity.class);
//            }
//
//            assert recipeIntent != null;
//            recipeIntent.putExtra(Constants.exContentItemPk, String.valueOf(mainFeedItem.getFeedKey()));
//            recipeIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            recipeIntent.putExtra(Constants.exRecipePreview, true);
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
//                ActivityCompat.startActivity(getActivity(), recipeIntent, options.toBundle());
//            } else {
//                startActivity(recipeIntent);
//                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
//            }

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }
}
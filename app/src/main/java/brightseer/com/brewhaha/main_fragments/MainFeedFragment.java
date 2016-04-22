package brightseer.com.brewhaha.main_fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.BitmapInfo;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.RecipeCardsActivity;
import brightseer.com.brewhaha.helper.RecyclerItemClickListener;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.main_adapters.MainFeedViewHolder;
import brightseer.com.brewhaha.models.MainFeedItem;
import brightseer.com.brewhaha.models.RecipeDetail;

public class MainFeedFragment extends Fragment {
    private String userToken = "na";
    private RecyclerView home_recycler_view;
    private View rootView;

    Firebase ref;
    FirebaseRecyclerAdapter mAdapter;

//    private int previousTotal = 0;
//    private boolean loadingBool = true;
//    private int visibleThreshold = 4;
//    private int firstVisibleItem, visibleItemCount, totalItemCount;
//
//    private Future<JsonObject> ionMainFeed;
//    private boolean overRide = false;
//    private int skipCount = 5;

    public MainFeedFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ref = new Firebase(Constants.fireBaseRoot).child(Constants.exFeeds);

//        _fContext = getActivity();
//        initGoogleAnalytics(this.getClass().getSimpleName());

//        addTestData();
        return rootView;
    }

    public void addTestData() {
        try {
            ///ADD NEW FEED//////////////////
            Firebase refFeed = new Firebase(Constants.fireBaseRoot).child(Constants.exFeeds);
            Firebase refFeedPush = refFeed.push();

            MainFeedItem mainFeedItem = new MainFeedItem();
            mainFeedItem.setTitle("Two recipe");
            mainFeedItem.setAuthor("Mike Mc");
            mainFeedItem.setUserKey("1");
            mainFeedItem.setUserImageUrl("https://lh3.googleusercontent.com/-aawpJBwnegU/AAAAAAAAAAI/AAAAAAAAAAA/Mh3nJ_5Rm4Q/photo.jpg");
            mainFeedItem.setImageUrl("https://lh4.googleusercontent.com/-kyaG1I42a9Q/AAAAAAAAAAI/AAAAAAAAAGk/vDTdKFir-xo/s96-c/photo.jpg");
            mainFeedItem.setDateCreated(DateTime.now().toString());
            mainFeedItem.setKey("");
            refFeedPush.setValue(mainFeedItem);

            String postId = refFeedPush.getKey();

            mainFeedItem.setKey(postId);

            Firebase theChild = refFeed.child(postId);

            Map<String, Object> keyValue = new HashMap<String, Object>();
            keyValue.put("key", postId);
            theChild.updateChildren(keyValue);
            ///////////////////////////

            ///ADD NEW RecipeDetail//////////////////
            Firebase refDetail = new Firebase(Constants.fireBaseRoot).child(Constants.exRecipeDetail);
            RecipeDetail recipeDetail = new RecipeDetail();
            recipeDetail.setFeedKey(postId);
            recipeDetail.setDateCreated(DateTime.now().toString());
            recipeDetail.setDateModified(DateTime.now().toString());
            recipeDetail.setAlcoholByVol("7.8");
            recipeDetail.setBitternessIbu("3.78");
            recipeDetail.setColorSrm("15");
            recipeDetail.setDescription("Two is my description Sucka fo.");
            recipeDetail.setOriginalGravity("5");
            recipeDetail.setSrmHex("#FE3499");
            recipeDetail.setFinalGravity("4");
            recipeDetail.setStyle("Get From list");
            recipeDetail.setStyleDescription("Bland pail ale");
            recipeDetail.setUserProfileKey("1");
            recipeDetail.setYieldByGallon("5");

            refDetail.push().setValue(recipeDetail);
            //////////////////

            ///ADD NEW RecipeDetail//////////////////
            Firebase refGrain = new Firebase(Constants.fireBaseRoot).child(Constants.exIngredients).child(Constants.exGrains);


            //////////////////
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null)
            mAdapter.cleanup();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
//            int screenOrientation = getResources().getConfiguration().orientation;
            home_recycler_view = (RecyclerView) rootView.findViewById(R.id.home_recycler_view);
            home_recycler_view.setHasFixedSize(true);
            home_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                home_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider, getActivity().getTheme()), true));
            } else {
                home_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
            }

            home_recycler_view.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity().getBaseContext(), home_recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                openDetailActivity(view, position);
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


            mAdapter = new FirebaseRecyclerAdapter<MainFeedItem, MainFeedViewHolder>(MainFeedItem.class, R.layout.row_home, MainFeedViewHolder.class, ref) {
                @Override
                public void populateViewHolder(MainFeedViewHolder mainFeedViewHolder, MainFeedItem mainFeedItem, int position) {
                    mainFeedViewHolder.vAuthor.setText(mainFeedItem.getAuthor());
                    mainFeedViewHolder.vTitle.setText(mainFeedItem.getTitle());
                    mainFeedViewHolder.vtime_from_post_text_view.setText(Utilities.DisplayTimeFormater(mainFeedItem.getDateCreated()));

                    Ion.with(mainFeedViewHolder.vimage)
                            .placeholder(R.mipmap.ic_beercap)
                            .centerCrop()
                            .load(mainFeedItem.getImageUrl());

                    Ion.with(mainFeedViewHolder.vuser_image_view)
                            .placeholder(R.drawable.ic_person_black_24dp)
                            .error(R.drawable.ic_person_black_24dp)
                            .centerCrop()
                            .transform(Utilities.GetRoundTransform())
                            .load(mainFeedItem.getUserImageUrl());

                    String URL = Constants.urlBrewHahaContent + mainFeedItem.getTitle().replace(" ", "-");
                    mainFeedViewHolder.mPlusOneButton.initialize(URL, 0);
                }
            };
            home_recycler_view.setAdapter(mAdapter);


//            home_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//
//                    visibleItemCount = home_recycler_view.getChildCount();
//                    totalItemCount = adapter.getItemCount();
//                    firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
//
//                    if (loadingBool) {
//                        if (totalItemCount > previousTotal) {
//                            loadingBool = false;
//                            previousTotal = totalItemCount;
//                        }
//                    }
//                    if (!loadingBool && (totalItemCount - visibleItemCount)
//                            <= (firstVisibleItem + visibleThreshold)) {
//                        load();
//                        loadingBool = true;
//                    }
//                }
//            });
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

//    public void load() {
//
//
////        if (loading != null && !loading.isDone() && !loading.isCancelled())
////            return;
////
////        String url = Constants.GetMainFeed;
////        url += "5";
////
////        if (adapter != null) {
////            if (adapter.getItemCount() > 0 || overRide) {
////                url += "/" + skipCount;
////                skipCount += 5;
////                overRide = false;
////            } else {
////                url += "/" + 0;
////            }
////        }
////
////        ionMainFeed = Ion
////                .with(this)
////                .load(url)
////                .setHeader("Cache-Control", "No-Cache")
////                .asJsonObject()
////                .setCallback(new FutureCallback<JsonObject>() {
////                                 @Override
////                                 public void onCompleted(Exception e, JsonObject result) {
////                                     try {
////                                         if (result != null) {
////
////                                             MainFeed mainFeed = JsonToObject.JsonToMainFeed(result);
////
////                                             for (MainFeedItem item : mainFeed.getMainFeedItems()) {
////                                                 adapter.add(item, mainFeed.getMainFeedItems().size() - 1);
////                                             }
////
////                                             adapter.notifyDataSetChanged();
////                                         }
////                                     } catch (Exception ex) {
////                                         if (BuildConfig.DEBUG) {
////                                             Log.e(Constants.LOG, ex.getMessage());
////                                         }
////                                     } finally {
////                                         if (adapter.getItemCount() < 5) {
////                                             overRide = true;
////                                             load();
////                                         }
////                                     }
////                                 }
////                             }
////                );
//    }

    public void openDetailActivity(View view, int position) {
        try {
            MainFeedItem feedItem = (MainFeedItem) mAdapter.getItem(position);

            Intent newIntent = new Intent();
//            if (feedItem.getItemTypeId() == 1) {
            newIntent = new Intent(getActivity(), RecipeCardsActivity.class);
//            }
//            if (feedItem.getItemTypeId() == 2) {
//                newIntent = new Intent(_fContext, GridViewActivity.class);
//            }

//            eventGoogleAnalytics(Constants.gacRecipe, Constants.gacOpen, feedItem.getTitle());

            newIntent.putExtra(Constants.exRecipeTitle, feedItem.getTitle());
            newIntent.putExtra(Constants.exPosition, position);
            newIntent.putExtra(Constants.exUsername, feedItem.getAuthor());
            newIntent.putExtra(Constants.exUserdate, String.valueOf(feedItem.getDateCreated()));
            newIntent.putExtra(Constants.exAuthorImage, feedItem.getUserImageUrl());
            newIntent.putExtra(Constants.exRecipeImage, feedItem.getImageUrl());
            newIntent.putExtra(Constants.exFeedKey, feedItem.getKey());


//            BitmapInfo bi = Ion.with((ImageView) view.findViewById(R.id.home_row_user_image_view)).getBitmapInfo();
//            newIntent.putExtra(Constants.exBitMapInfo, bi.key);

            BitmapInfo biMain = Ion.with((ImageView) view.findViewById(R.id.image)).getBitmapInfo();
            if (biMain != null)
                newIntent.putExtra(Constants.exBitMapInfoMain, biMain.key);

            newIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Pair p1 = Pair.create(view.findViewById(R.id.itemAuthor), getResources().getString(R.string.transition_username));
                Pair p2 = Pair.create(view.findViewById(R.id.home_row_time_from_post_text_view), getResources().getString(R.string.transition_userdate));
                Pair p3 = Pair.create(view.findViewById(R.id.itemTitle), getResources().getString(R.string.transition_title));
                Pair p4 = Pair.create(view.findViewById(R.id.image), getResources().getString(R.string.transition_bitmapmain));
                Pair p5 = Pair.create(view.findViewById(R.id.home_row_user_image_view), getResources().getString(R.string.transition_bitmapuser));
                Pair p6 = Pair.create(view.findViewById(R.id.plus_one_button), getResources().getString(R.string.transition_googlePlus));

//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), p1, p2, p3, p4, p5, p6);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                ActivityCompat.startActivityForResult(getActivity(), newIntent, 0, options.toBundle());

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
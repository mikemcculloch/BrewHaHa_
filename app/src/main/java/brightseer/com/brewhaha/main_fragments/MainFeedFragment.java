package brightseer.com.brewhaha.main_fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.koushikdutta.ion.Ion;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.helper.RecyclerItemClickListener;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.main_adapters.MainFeedViewHolder;
import brightseer.com.brewhaha.models.MainFeedItem;
import brightseer.com.brewhaha.models.RecipeDetail;

public class MainFeedFragment extends FeedsBaseFragment {
    private View rootView;
    private Firebase rootRef;
    private FirebaseRecyclerAdapter mAdapter;

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
        initFirebaseDb();
//        addTestData();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }

    private void initFirebaseDb() {
        rootRef = new Firebase(Constants.fireBaseRoot).child(Constants.fbUserFeeds).child("everyone");
//        rootRef = new Firebase(Constants.fireBaseRoot).child(Constants.fbUserFeeds).child(Constants.fbPublicFeeds);
    }

    public void addTestData() {
        try {
            if (!BrewSharedPrefs.getEmailAddress().isEmpty()) {
                ///ADD NEW FEED//////////////////
//                Firebase refUserLists = new Firebase(Constants.fireBaseRoot).child(Constants.fbUserFeeds).child("brightseerstudios@gmail,com");
                Firebase refFeedPush = rootRef.push();

                MainFeedItem mainFeedItem = new MainFeedItem();
                mainFeedItem.setTitle("Make Beer Great Again");
                mainFeedItem.setAuthor("Trump Dump");
//            mainFeedItem.setUserKey("brightseerstudios@gmail,com");
                mainFeedItem.setUserImageUrl("http://images.huffingtonpost.com/2016-03-07-1457372468-7442274-trump.jpg");
                mainFeedItem.setImageUrl("http://rlv.zcache.ca/trump_make_america_great_again_18_oz_beer_stein-rd05525b7776c452d8deb1b45f76bd937_x76ia_8byvr_324.jpg");
                mainFeedItem.setDateCreated(DateTime.now().toString());
                mainFeedItem.setKey("");
                mainFeedItem.setStyle("Indian Pale Ale");
                refFeedPush.setValue(mainFeedItem);

                String postId = refFeedPush.getKey();

                mainFeedItem.setKey(postId);

                Firebase theChild = rootRef.child(postId);

                Map<String, Object> keyValue = new HashMap<String, Object>();
                keyValue.put("key", postId);
                theChild.updateChildren(keyValue);
                ///////////////////////////

                ///ADD NEW RecipeDetail//////////////////
                Firebase refDetail = new Firebase(Constants.fireBaseRoot).child(Constants.fbRecipeDetail).child(postId);
                RecipeDetail recipeDetail = new RecipeDetail();
                recipeDetail.setDateCreated(DateTime.now().toString());
                recipeDetail.setDateModified(DateTime.now().toString());
                recipeDetail.setAlcoholByVol("7.8");
                recipeDetail.setOwnerEmail(BrewSharedPrefs.getEmailAddress());
                recipeDetail.setBitternessIbu("3.78");
                recipeDetail.setColorSrm("15");
                recipeDetail.setDescription(" A confident Donald Trump told supporters on Saturday that he's not changing his pitch to voters, a day after his chief adviser assured Republican officials their party's front-runner would show more restraint while campaigning.\n" +
                        "\n" +
                        " You know, being presidential's easy — much easier than what I have to do,  he told thousands at a rally in Bridgeport, Connecticut.  Here, I have to rant and rave. I have to keep you people going. Otherwise you're going to fall asleep on me, right?");
                recipeDetail.setOriginalGravity("5");
                recipeDetail.setSrmHex("#FE3499");
                recipeDetail.setFinalGravity("4");
                recipeDetail.setStyle("Bland pale ale");
                recipeDetail.setYieldByGallon("5");

                refDetail.setValue(recipeDetail);
                //////////////////
            }
//            Firebase refUserLists = new Firebase(Constants.fireBaseRoot).child(Constants.fbUserFeeds).child("brightseerstudios@gmail,com").child(postId);
//            Map<String, Object> userList = new HashMap<String, Object>();
//            userList.put("dateCreated", DateTime.now().toString());
//            refUserLists.setValue(userList);

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

    private void initRecyclerView() {
        try {
//            int screenOrientation = getResources().getConfiguration().orientation;
            RecyclerView home_recycler_view = (RecyclerView) rootView.findViewById(R.id.home_recycler_view);
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
                                openDetailActivity(view, position, mAdapter);
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
            mAdapter = GetFireBaseAdapter(rootRef, R.layout.row_home);
//            mAdapter = new FirebaseRecyclerAdapter<MainFeedItem, MainFeedViewHolder>(MainFeedItem.class, R.layout.row_home, MainFeedViewHolder.class, rootRef) {
//                @Override
//                public void populateViewHolder(MainFeedViewHolder mainFeedViewHolder, MainFeedItem mainFeedItem, int position) {
//                    mainFeedViewHolder.vAuthor.setText(mainFeedItem.getAuthor());
//                    mainFeedViewHolder.vTitle.setText(mainFeedItem.getTitle());
//                    mainFeedViewHolder.vtime_from_post_text_view.setText(Utilities.DisplayTimeFormater(mainFeedItem.getDateCreated()));
//
//                    mainFeedViewHolder.itemStyle.setText(mainFeedItem.getStyle());
//
//                    Ion.with(mainFeedViewHolder.vimage)
//                            .placeholder(R.mipmap.ic_beercap)
//                            .centerCrop()
//                            .load(mainFeedItem.getImageUrl());
//
//                    Ion.with(mainFeedViewHolder.vuser_image_view)
//                            .placeholder(R.drawable.ic_person_black_24dp)
//                            .error(R.drawable.ic_person_black_24dp)
//                            .centerCrop()
//                            .transform(Utilities.GetRoundTransform())
//                            .load(mainFeedItem.getUserImageUrl());
//
//                    String URL = Constants.urlBrewHahaContent + mainFeedItem.getTitle().replace(" ", "-");
//                    mainFeedViewHolder.mPlusOneButton.initialize(URL, 0);
//                }
//            };
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

    public void onSaveInstanceState(Bundle outState) {
        //DO NOT DELETE ME
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


}
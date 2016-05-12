package brightseer.com.brewhaha.main_fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;


import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.RecipeCardsActivity;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.main_adapters.MainFeedViewHolder;
import brightseer.com.brewhaha.models.MainFeedItem;

/**
 * Created by wooan on 4/27/2016.
 */
public class FeedsBaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void openDetailActivity(View view, int position, FirebaseRecyclerAdapter mAdapter) {
        try {
            MainFeedItem feedItem = (MainFeedItem) mAdapter.getItem(position);

            Intent newIntent = new Intent(getActivity(), RecipeCardsActivity.class);
//            eventGoogleAnalytics(Constants.gacRecipe, Constants.gacOpen, feedItem.getTitle());

            newIntent.putExtra(Constants.exRecipeTitle, feedItem.getTitle());
            newIntent.putExtra(Constants.exRecipeAuthor, feedItem.getAuthor());
            newIntent.putExtra(Constants.exAuthorImage, feedItem.getUserImageUrl());
            newIntent.putExtra(Constants.fbFeedKey, feedItem.getKey());
            newIntent.putExtra(Constants.exRecipeStyle, feedItem.getStyle());
            newIntent.putExtra(Constants.exDateCreated, feedItem.getDateCreated());
            newIntent.putExtra(Constants.exCloneKey, feedItem.getCloneKey());
//            newIntent.putExtra(Constants.exPosition, position);
//            newIntent.putExtra(Constants.exUserdate, String.valueOf(feedItem.getDateCreated()));

//            BitmapInfo biMain = Ion.with((ImageView) view.findViewById(R.id.home_row_user_image_view)).getBitmapInfo();
//            if (biMain != null)
//                newIntent.putExtra(Constants.exBitMapInfoMain, biMain.key);

            newIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Pair p1 = Pair.create(view.findViewById(R.id.itemTitle), getResources().getString(R.string.transition_title));
//                Pair p2 = Pair.create(view.findViewById(R.id.home_row_user_image_view), getResources().getString(R.string.transition_bitmapuser));
                Pair p3 = Pair.create(view.findViewById(R.id.plus_one_button), getResources().getString(R.string.transition_googlePlus));
                Pair p4 = Pair.create(view.findViewById(R.id.itemAuthor), getResources().getString(R.string.transition_author));
                Pair p5 = Pair.create(view.findViewById(R.id.itemStyle), getResources().getString(R.string.transition_style));
                Pair p6 = Pair.create(view.findViewById(R.id.home_row_time_from_post_text_view), getResources().getString(R.string.transition_dateCreated));
                Pair p7 = Pair.create(view.findViewById(R.id.parent_layout), getResources().getString(R.string.transition_layout));

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), p1, p3, p4, p5, p6, p7);
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

    public FirebaseRecyclerAdapter GetFireBaseAdapter(Firebase rootRef, int layoutId) {
        try {
            FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<MainFeedItem, MainFeedViewHolder>(MainFeedItem.class, layoutId, MainFeedViewHolder.class, rootRef) {
                @Override
                public void populateViewHolder(MainFeedViewHolder mainFeedViewHolder, MainFeedItem mainFeedItem, int position) {
                    mainFeedViewHolder.vAuthor.setText(mainFeedItem.getAuthor());
                    mainFeedViewHolder.vTitle.setText(mainFeedItem.getTitle());
                    mainFeedViewHolder.vtime_from_post_text_view.setText(Utilities.DisplayTimeFormater(mainFeedItem.getDateCreated()));

                    mainFeedViewHolder.itemStyle.setText(mainFeedItem.getStyle());

                    Picasso.with(getContext())
                            .load(mainFeedItem.getImageUrl())
                            .resize(50, 50)
                            .centerCrop()
                            .into(mainFeedViewHolder.vimage);

//                    Ion.with(mainFeedViewHolder.vuser_image_view)
//                            .placeholder(R.drawable.ic_person_black_24dp)
//                            .error(R.drawable.ic_person_black_24dp)
//                            .centerCrop()
//                            .transform(Utilities.GetRoundTransform())
//                            .load(mainFeedItem.getUserImageUrl());

                    String URL = Constants.urlBrewHahaContent + mainFeedItem.getTitle().replace(" ", "-");
                    mainFeedViewHolder.mPlusOneButton.initialize(URL, 0);
                }
            };
            return mAdapter;
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
            return null;
        }
    }
}

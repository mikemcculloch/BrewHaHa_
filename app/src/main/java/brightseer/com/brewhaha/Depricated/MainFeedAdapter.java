package brightseer.com.brewhaha.Depricated;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.makeramen.RoundedDrawable;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.main_adapters.MainFeedViewHolder;
import brightseer.com.brewhaha.main_fragments.MainFeedFragment;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.MainFeedItem;

/**
 * Created by wooan_000 on 12/16/2014.
 */

///DEPRICATED USE FIREBASEADAPTER
public class MainFeedAdapter extends RecyclerView.Adapter<MainFeedViewHolder> {
    private List<MainFeedItem> mainFeedItems = new Vector<>();
    private MainFeedFragment _fragment;


    public MainFeedAdapter(List<MainFeedItem> jsonObject, MainFeedFragment fragment) {
        this.mainFeedItems = jsonObject;
        _fragment = fragment;
    }

    public void add(MainFeedItem item, int position) {
        mainFeedItems.add(item);
        notifyItemInserted(position);
    }

    public void sendUpdate(int position) {
        notifyItemChanged(position);
    }

    public void clear() {
        mainFeedItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public MainFeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row_home, viewGroup, false);
        return new MainFeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainFeedViewHolder homeItemViewHolder, int position) {
//        _fragment.load();
        MainFeedItem mainFeedItem = mainFeedItems.get(position);
        final int currentPos = position;
        if (mainFeedItem != null) {
            homeItemViewHolder.vTitle.setText(mainFeedItem.getTitle());
            homeItemViewHolder.vAuthor.setText(mainFeedItem.getAuthor());
            homeItemViewHolder.vtime_from_post_text_view.setText(Utilities.DisplayTimeFormater(mainFeedItem.getDateCreated()));

//            Ion.with(homeItemViewHolder.vuser_image_view)
//                    .placeholder(R.drawable.ic_person_black_24dp)
//                    .error(R.drawable.ic_person_black_24dp)
//                    .centerCrop()
//                    .transform(trans)
//                    .load(mainFeedItem.getUserImageUrl());

            String URL = Constants.urlBrewHahaContent + mainFeedItem.getTitle().replace(" ", "-");
            homeItemViewHolder.mPlusOneButton.initialize(URL, 0);

//            homeItemViewHolder.vCardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    _fragment.openDetailActivity(v, currentPos, );
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return mainFeedItems.size();
    }

    public MainFeedItem getItemAt(int position) {
        return mainFeedItems.get(position);
    }


}

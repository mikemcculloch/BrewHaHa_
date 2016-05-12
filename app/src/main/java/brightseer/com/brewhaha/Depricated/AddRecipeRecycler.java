package brightseer.com.brewhaha.Depricated;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.makeramen.RoundedDrawable;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.main_fragments.UserFeedsFragment;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.MainFeedItem;
import brightseer.com.brewhaha.objects.RecyclerObjects;

/**
 * Created by Michael McCulloch on 2/25/2015.
 */
public class AddRecipeRecycler extends RecyclerView.Adapter<RecyclerObjects.MyRecipeItemViewHolder> {
    private List<MainFeedItem> jsonObjects = new Vector<>();
    private UserFeedsFragment _fragment;

    int cornerRadius = 200;


    public AddRecipeRecycler(List<MainFeedItem> jsonObject, UserFeedsFragment fragment) {
        this.jsonObjects = jsonObject;
        _fragment = fragment;
    }

    public void add(MainFeedItem item, int position) {
        jsonObjects.add(item);
        notifyItemInserted(position);
    }

    public void remove(MainFeedItem item, int position) {
        jsonObjects.remove(item);
        notifyItemRemoved(position);
    }

    public void clear() {
        jsonObjects.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerObjects.MyRecipeItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row_my_recipes, viewGroup, false);
        return new RecyclerObjects.MyRecipeItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.MyRecipeItemViewHolder homeItemViewHolder, int i) {
//        _fragment.load();
        MainFeedItem mainFeedItem = jsonObjects.get(i);
        if (mainFeedItem != null) {
            if (TextUtils.isEmpty(mainFeedItem.getTitle())) {
                mainFeedItem.setTitle("Untitled");
                homeItemViewHolder.vTitle.setTextColor(_fragment.getResources().getColor(R.color.red));
            }

            homeItemViewHolder.vTitle.setText(mainFeedItem.getTitle());
            homeItemViewHolder.vAuthor.setText(mainFeedItem.getAuthor());
            homeItemViewHolder.vtime_from_post_text_view.setText(Utilities.DisplayTimeFormater(mainFeedItem.getDateCreated()));

            String published = "Unpublished";
            homeItemViewHolder.my_recipe_published_text_view.setTextColor(_fragment.getResources().getColor(R.color.red));
//            if (mainFeedItem.isApproved()) {
//                published = "Published";
//                homeItemViewHolder.my_recipe_published_text_view.setTextColor(_fragment.getResources().getColor(R.color.app_blue));
//            }
//            if (mainFeedItem.isSubmitted() && !mainFeedItem.isApproved()) {
//                published = "Pending";
//                homeItemViewHolder.my_recipe_published_text_view.setTextColor(_fragment.getResources().getColor(R.color.app_orange));
//            }

            homeItemViewHolder.my_recipe_published_text_view.setText(published);

        }
    }

    @Override
    public int getItemCount() {
        return jsonObjects.size();
    }
}

package brightseer.com.brewhaha.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.Transform;
import com.makeramen.RoundedDrawable;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.fragment.AdminFragment;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.MainFeedItem;
import brightseer.com.brewhaha.objects.RecyclerObjects;

/**
 * Created by Michael McCulloch on 2/25/2015.
 */
public class AdminRecipeRecycler extends RecyclerView.Adapter<RecyclerObjects.AdminItemViewHolder> {
    private List<MainFeedItem> jsonObjects = new Vector<>();
    private AdminFragment _fragment;

    int cornerRadius = 200;

    Transform trans = new Transform() {
        boolean isOval = false;

        @Override
        public Bitmap transform(Bitmap bitmap) {
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, cornerRadius, cornerRadius, false);
            Bitmap transformed = RoundedDrawable.fromBitmap(scaled).setScaleType(ImageView.ScaleType.CENTER_CROP).setCornerRadius(cornerRadius).setOval(isOval).toBitmap();
            if (!bitmap.equals(scaled)) bitmap.recycle();
            if (!scaled.equals(transformed)) bitmap.recycle();

            return transformed;
        }

        @Override
        public String key() {
            return "rounded_radius_" + cornerRadius + "_oval_" + isOval;
        }
    };

    public AdminRecipeRecycler(List<MainFeedItem> jsonObject, AdminFragment fragment) {
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
    public RecyclerObjects.AdminItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row_admin_recipes, viewGroup, false);
        return new RecyclerObjects.AdminItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.AdminItemViewHolder homeItemViewHolder, int i) {
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
//
//            if (mainFeedItem.isSubmitted() && !mainFeedItem.isApproved()) {
//                published = "Pending";
//                homeItemViewHolder.my_recipe_published_text_view.setTextColor(_fragment.getResources().getColor(R.color.app_orange));
//            }

            homeItemViewHolder.my_recipe_published_text_view.setText(published);

            Ion.with(homeItemViewHolder.vimage)
                    .placeholder(R.mipmap.ic_beercap)
                    .centerCrop()
                    .load(mainFeedItem.getImageUrl());

            Ion.with(homeItemViewHolder.vuser_image_view)
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .error(R.drawable.ic_person_black_24dp)
                    .centerCrop()
                    .transform(trans)
                    .load(mainFeedItem.getUserImageUrl());
        }
    }

    @Override
    public int getItemCount() {
        return jsonObjects.size();
    }
}

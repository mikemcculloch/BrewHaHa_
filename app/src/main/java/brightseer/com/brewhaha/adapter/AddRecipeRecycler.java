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
import brightseer.com.brewhaha.fragment.MyRecipeListFragment;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.objects.HomeItem;
import brightseer.com.brewhaha.objects.RecyclerObjects;

/**
 * Created by Michael McCulloch on 2/25/2015.
 */
public class AddRecipeRecycler extends RecyclerView.Adapter<RecyclerObjects.MyRecipeItemViewHolder> {
    private List<HomeItem> jsonObjects = new Vector<>();
    private MyRecipeListFragment _fragment;

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

    public AddRecipeRecycler(List<HomeItem> jsonObject, MyRecipeListFragment fragment) {
        this.jsonObjects = jsonObject;
        _fragment = fragment;
    }

    public void add(HomeItem item, int position) {
        jsonObjects.add(item);
        notifyItemInserted(position);
    }

    public void remove(HomeItem item, int position) {
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
        _fragment.load();
        HomeItem homeItem = jsonObjects.get(i);
        if (homeItem != null) {
            if (TextUtils.isEmpty(homeItem.getTitle())) {
                homeItem.setTitle("Untitled");
                homeItemViewHolder.vTitle.setTextColor(_fragment.getResources().getColor(R.color.red));
            }

            homeItemViewHolder.vTitle.setText(homeItem.getTitle());
            homeItemViewHolder.vAuthor.setText(homeItem.getAuthor());
            homeItemViewHolder.vtime_from_post_text_view.setText(Utilities.DisplayTimeFormater(homeItem.getTimestamp()));

            String published = "Unpublished";
            homeItemViewHolder.my_recipe_published_text_view.setTextColor(_fragment.getResources().getColor(R.color.red));
            if (homeItem.isApproved()) {
                published = "Published";
                homeItemViewHolder.my_recipe_published_text_view.setTextColor(_fragment.getResources().getColor(R.color.app_blue));
            }
            if (homeItem.isSubmitted() && !homeItem.isApproved()) {
                published = "Pending";
                homeItemViewHolder.my_recipe_published_text_view.setTextColor(_fragment.getResources().getColor(R.color.app_orange));
            }

            homeItemViewHolder.my_recipe_published_text_view.setText(published);

            Ion.with(homeItemViewHolder.vimage)
                    .placeholder(R.mipmap.ic_beercap)

                    .centerCrop()
                    .load(homeItem.getImageUrl());

            Ion.with(homeItemViewHolder.vuser_image_view)
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .error(R.drawable.ic_person_black_24dp)
                    .centerCrop()
                    .transform(trans)
                    .load(homeItem.getUserImageUrl());
        }
    }

    @Override
    public int getItemCount() {
        return jsonObjects.size();
    }
}

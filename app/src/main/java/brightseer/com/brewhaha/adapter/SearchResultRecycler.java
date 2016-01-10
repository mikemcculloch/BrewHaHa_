package brightseer.com.brewhaha.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.Transform;
import com.makeramen.RoundedDrawable;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.SearchResultsActivity;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.objects.HomeItem;
import brightseer.com.brewhaha.objects.RecyclerObjects;

/**
 * Created by wooan_000 on 12/18/2014.
 */
public class SearchResultRecycler extends RecyclerView.Adapter<RecyclerObjects.HomeItemViewHolder> {
    private List<HomeItem> jsonObjects = new Vector<>();
    private SearchResultsActivity _activity;

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

    public SearchResultRecycler(List<HomeItem> jsonObject, SearchResultsActivity activity) {
        this.jsonObjects = jsonObject;
        _activity = activity;
    }

    public void add(HomeItem item, int position) {
        jsonObjects.add(item);
        notifyItemInserted(position);
    }

    @Override
    public RecyclerObjects.HomeItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row_home, viewGroup, false);
        return new RecyclerObjects.HomeItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.HomeItemViewHolder homeItemViewHolder, int position) {
        HomeItem homeItem = jsonObjects.get(position);
        final int currentPos = position;
        if (homeItem != null) {
            homeItemViewHolder.vTitle.setText(homeItem.getTitle());
            homeItemViewHolder.vAuthor.setText(homeItem.getAuthor());
            homeItemViewHolder.vtime_from_post_text_view.setText(Utilities.DisplayTimeFormater(homeItem.getTimestamp()));

            Ion.with(homeItemViewHolder.vimage)
                    .placeholder(R.mipmap.ic_beercap)
                    .centerCrop()
                    .load(homeItem.getImageUrl());

            Ion.with(homeItemViewHolder.vuser_image_view)
                    .placeholder(R.mipmap.ic_person_black_24dp)
                    .error(R.mipmap.ic_person_black_24dp)
                    .centerCrop()
                    .transform(trans)
                    .load(homeItem.getUserImageUrl());

            String URL = Constants.urlBrewHahaContent + homeItem.getTitle().replace(" ", "-");
            homeItemViewHolder.mPlusOneButton.initialize(URL, 0);

            homeItemViewHolder.vCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _activity.openDetailActivity(v, currentPos);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return jsonObjects.size();
    }


}

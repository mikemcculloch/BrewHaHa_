package brightseer.com.brewhaha.adapter;

import android.content.res.Resources;
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

import brightseer.com.brewhaha.GridViewActivity;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.objects.Image;
import brightseer.com.brewhaha.objects.RecyclerObjects;

/**
 * Created by wooan_000 on 12/16/2014.
 */
public class GridImagesRecycler extends RecyclerView.Adapter<RecyclerObjects.GridImageItemViewHolder> {
    private List<Image> jsonObjects = new Vector<>();
    private GridViewActivity _activity;
    private boolean _addRecipe;

    Transform trans = new Transform() {
        boolean isOval = false;
        int cornerRadius = 100;

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

    public GridImagesRecycler(List<Image> jsonObject, GridViewActivity activity) {
        this.jsonObjects = jsonObject;
        _activity = activity;
    }

    public void add(Image item, int position) {
        jsonObjects.add(item);
        notifyItemInserted(position);
    }

    public void setAddRecipe(boolean addRecipe) {
        _addRecipe = addRecipe;
    }


    public void add(Image item) {
        jsonObjects.add(item);
    }

    public void sendUpdate(int position) {
        notifyItemChanged(position);
    }

    public void clear() {
        jsonObjects.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerObjects.GridImageItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row_grid, viewGroup, false);
        return new RecyclerObjects.GridImageItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.GridImageItemViewHolder homeItemViewHolder, int position) {
//        _fragment.load();
        Image homeItem = jsonObjects.get(position);
        final int currentPos = position;
        if (homeItem != null) {

//            int dpConversion = (int) (112 * Resources.getSystem().getDisplayMetrics().density);
//            homeItemViewHolder.vimage.setMinimumWidth(dpConversion);
//            homeItemViewHolder.vimage.setMinimumHeight(dpConversion);
            Ion.with(homeItemViewHolder.vimage)
                    .placeholder(R.mipmap.ic_image_black_48dp)
                    .centerCrop()
                    .load(homeItem.getImageUrl());

//            homeItemViewHolder.vimage.setMinimumWidth(dpConversion);
//            homeItemViewHolder.vimage.setMinimumHeight(dpConversion);

            homeItemViewHolder.vimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_addRecipe && currentPos == jsonObjects.size() - 1) {
                        _activity.openRecipeDetail(v, currentPos);

                    } else {
                        _activity.openImageDetail(v, currentPos);
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return jsonObjects.size();
    }

    public Image GetItemAt(int position) {
        return jsonObjects.get(position);
    }
}

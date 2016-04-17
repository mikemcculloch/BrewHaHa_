package brightseer.com.brewhaha.recipe_adapters;

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

import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.Comment;
import brightseer.com.brewhaha.objects.RecyclerObjects;

public class CommentRecycler extends RecyclerView.Adapter<RecyclerObjects.CommentItemViewHolder> {
    List<Comment> comments = new Vector<>();
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

    public CommentRecycler(List<Comment> _comments) {
        this.comments = _comments;
//        _Activity = activity;
    }


    public void add(Comment item, int position) {
//        comments.add(item);
        comments.add(position, item);
        notifyItemInserted(position);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        comments.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void removeAll() {
        comments.clear();
        notifyAll();
//        notifyItemInserted(position);
    }

    @Override
    public RecyclerObjects.CommentItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row_comment, viewGroup, false);

        return new RecyclerObjects.CommentItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerObjects.CommentItemViewHolder holder, int position) {

        Comment getItem = comments.get(position);

        Ion.with(holder.comment_user_image)
                .placeholder(R.drawable.ic_person_black_24dp)
                .error(R.drawable.ic_person_black_24dp)
                .centerCrop()
                .transform(trans)
                .load(getItem.getImageUrl());

        holder.comment_item_author.setText(getItem.getAuthorName());
        holder.comment_item_timestamp.setText(Utilities.DisplayTimeFormater(getItem.getDateCreated()));
        holder.comment_text_view.setText(getItem.getBody());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public int getPostionByKey(String itemKey) {
        for (Comment item : comments) {
            if (itemKey.equals(item.getKey())) {
                return comments.indexOf(item);
            }
        }
        return 0;
    }
}

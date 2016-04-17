package brightseer.com.brewhaha.recipe_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import brightseer.com.brewhaha.R;

/**
 * Created by wooan on 4/17/2016.
 */
public class CommentViewHolder  extends RecyclerView.ViewHolder {

    public ImageView comment_user_image;
    public TextView comment_item_author;
    public TextView comment_item_timestamp;
    public TextView comment_text_view;

    public CommentViewHolder(View itemView) {
        super(itemView);
        comment_user_image = (ImageView) itemView.findViewById(R.id.comment_user_image);
        comment_item_author = (TextView) itemView.findViewById(R.id.comment_item_author);
        comment_item_timestamp = (TextView) itemView.findViewById(R.id.comment_item_timestamp);
        comment_text_view = (TextView) itemView.findViewById(R.id.comment_text_view);
    }

}
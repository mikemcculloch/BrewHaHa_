package brightseer.com.brewhaha.firebase_helpers;

import android.content.Context;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.main_adapters.MainFeedViewHolder;
import brightseer.com.brewhaha.models.Comment;
import brightseer.com.brewhaha.models.MainFeedItem;
import brightseer.com.brewhaha.recipe_adapters.CommentViewHolder;

/**
 * Created by wooan on 5/28/2016.
 */
public class FirebaseAdapters{

    public static FirebaseRecyclerAdapter MainFeedAdapter(Firebase rootRef, int layoutId, final Context context) {
        try {
            FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<MainFeedItem, MainFeedViewHolder>(MainFeedItem.class, layoutId, MainFeedViewHolder.class, rootRef) {
                @Override
                public void populateViewHolder(MainFeedViewHolder mainFeedViewHolder, MainFeedItem mainFeedItem, int position) {
                    mainFeedViewHolder.vAuthor.setText(mainFeedItem.getAuthor());
                    mainFeedViewHolder.vTitle.setText(mainFeedItem.getTitle());
                    mainFeedViewHolder.vtime_from_post_text_view.setText(Utilities.DisplayTimeFormater(mainFeedItem.getDateCreated()));

                    mainFeedViewHolder.itemStyle.setText(mainFeedItem.getStyle());

                    Picasso.with(context)
                            .load(mainFeedItem.getImageUrl())
                            .resize(50, 50)
                            .centerCrop()
                            .into(mainFeedViewHolder.vimage);
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

    public static FirebaseRecyclerAdapter CommentFeedAdapter(Query queryRef, int layoutId, final Context context) {
        try {
            FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(Comment.class, R.layout.row_comment, CommentViewHolder.class, queryRef) {
                @Override
                public void populateViewHolder(CommentViewHolder commentViewHolder, Comment comment, int position) {
                    Picasso.with(context)
                            .load(comment.getAuthorImageUrl())
                            .fit().centerCrop()
                            .transform(Utilities.GetRoundTransform())
                            .into(commentViewHolder.comment_user_image);

                    commentViewHolder.comment_item_author.setText(comment.getAuthorName());
                    commentViewHolder.comment_item_timestamp.setText(Utilities.DisplayTimeFormater(comment.getDateCreated()));
                    commentViewHolder.comment_text_view.setText(comment.getBody());
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

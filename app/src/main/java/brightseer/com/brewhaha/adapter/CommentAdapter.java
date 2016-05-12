package brightseer.com.brewhaha.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.RecipeActivity;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.Comment;

public class CommentAdapter extends ArrayAdapter<Comment> {

    private Context _context;
    int cornerRadius = 200;
    Activity _Activity;

    public CommentAdapter(Context context, int resourceId, List<Comment> objects, RecipeActivity activity) {
        super(context, resourceId, objects);
        _Activity = activity;
    }

    static class ViewHolder {
        private TextView comment_item_author;
        private TextView comment_item_timestamp;
        private ImageView comment_user_image;
        private TextView comment_text_view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.row_comment, parent, false);
        try {
            Comment comment = getItem(position);
            ViewHolder ViewHolder = new ViewHolder();
            ViewHolder.comment_user_image = (ImageView) theView.findViewById(R.id.comment_user_image);
//            Ion.with(ViewHolder.comment_user_image)
//                    .placeholder(R.drawable.ic_person_black_24dp)
//                    .error(R.drawable.ic_person_black_24dp)
//                    .transform(trans)
//                    .load(comment.getAuthorImageUrl());

            ViewHolder.comment_item_author = (TextView) theView.findViewById(R.id.comment_item_author);
            ViewHolder.comment_item_author.setText(comment.getAuthorName());

            ViewHolder.comment_item_timestamp = (TextView) theView.findViewById(R.id.comment_item_timestamp);
            ViewHolder.comment_item_timestamp.setText(Utilities.DisplayTimeFormater(comment.getDateCreated()));

            ViewHolder.comment_text_view = (TextView) theView.findViewById(R.id.comment_text_view);
            ViewHolder.comment_text_view.setText(comment.getBody());
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return theView;
    }


}

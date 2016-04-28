package brightseer.com.brewhaha.main_adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.plus.PlusOneButton;

import brightseer.com.brewhaha.R;

/**
 * Created by wooan on 4/16/2016.
 */
public class MainFeedViewHolder extends RecyclerView.ViewHolder {
    public TextView vTitle;
    public TextView vAuthor;
    public TextView itemStyle;
    public ImageView vimage;
    public ImageView vuser_image_view;
    public TextView vtime_from_post_text_view;
//    public LinearLayout home_parent_linear_layout;
    public PlusOneButton mPlusOneButton;
//    public CardView vCardView;

    public MainFeedViewHolder(View v) {
        super(v);
        vTitle = (TextView) v.findViewById(R.id.itemTitle);
        vAuthor = (TextView) v.findViewById(R.id.itemAuthor);
        vimage = (ImageView) v.findViewById(R.id.image);
        itemStyle = (TextView) v.findViewById(R.id.itemStyle);

        vuser_image_view = (ImageView) v.findViewById(R.id.home_row_user_image_view);
        vtime_from_post_text_view = (TextView) v.findViewById(R.id.home_row_time_from_post_text_view);
//        home_parent_linear_layout = (LinearLayout) v.findViewById(R.id.home_parent_linear_layout);
        mPlusOneButton = (PlusOneButton) v.findViewById(R.id.plus_one_button);
//        vCardView = (CardView) v.findViewById(R.id.home_cardview);
    }
}


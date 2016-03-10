package brightseer.com.brewhaha.objects;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.plus.PlusOneButton;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableSwipeableItemViewHolder;

import brightseer.com.brewhaha.R;

/**
 * Created by wooan_000 on 12/18/2014.
 */
public class RecyclerObjects {
    public static class HomeItemViewHolder extends RecyclerView.ViewHolder {
        public TextView vTitle;
        public TextView vAuthor;
        public ImageView vimage;
        public ImageView vuser_image_view;
        public TextView vtime_from_post_text_view;
        public LinearLayout home_parent_linear_layout;
        public PlusOneButton mPlusOneButton;
        public CardView vCardView;

        public HomeItemViewHolder(View v) {
            super(v);
            vTitle = (TextView) v.findViewById(R.id.itemTitle);
            vAuthor = (TextView) v.findViewById(R.id.itemAuthor);
            vimage = (ImageView) v.findViewById(R.id.image);
            vuser_image_view = (ImageView) v.findViewById(R.id.home_row_user_image_view);
            vtime_from_post_text_view = (TextView) v.findViewById(R.id.home_row_time_from_post_text_view);
            home_parent_linear_layout = (LinearLayout) v.findViewById(R.id.home_parent_linear_layout);
            mPlusOneButton = (PlusOneButton) v.findViewById(R.id.plus_one_button);
            vCardView = (CardView) v.findViewById(R.id.home_cardview);
        }
    }

    public static class MyRecipeItemViewHolder extends RecyclerView.ViewHolder {
        public TextView vTitle;
        public TextView vAuthor;
        public ImageView vimage;
        public ImageView vuser_image_view;
        public TextView vtime_from_post_text_view;
        public TextView my_recipe_published_text_view;
        public FrameLayout lyt_container;

        public MyRecipeItemViewHolder(View v) {
            super(v);
            vTitle = (TextView) v.findViewById(R.id.itemTitle);
            vAuthor = (TextView) v.findViewById(R.id.itemAuthor);
            vimage = (ImageView) v.findViewById(R.id.image);
            vuser_image_view = (ImageView) v.findViewById(R.id.my_recipes_row_user_image_view);
            vtime_from_post_text_view = (TextView) v.findViewById(R.id.my_recipes_row_time_from_post_text_view);
            lyt_container = (FrameLayout) v.findViewById(R.id.lyt_container);
            my_recipe_published_text_view = (TextView) v.findViewById(R.id.my_recipe_published_text_view);
        }
    }

    public static class CommentItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView comment_user_image;
        public TextView comment_item_author;
        public TextView comment_item_timestamp;
        public TextView comment_text_view;

        public CommentItemViewHolder(View itemView) {
            super(itemView);
            comment_user_image = (ImageView) itemView.findViewById(R.id.comment_user_image);
            comment_item_author = (TextView) itemView.findViewById(R.id.comment_item_author);
            comment_item_timestamp = (TextView) itemView.findViewById(R.id.comment_item_timestamp);
            comment_text_view = (TextView) itemView.findViewById(R.id.comment_text_view);
        }

    }

    public static class GrainMyRecipeViewHolder extends RecyclerView.ViewHolder {

        public TextView my_grain_amount_text_view;
        public TextView my_grain_weight_text_view;
        public TextView my_grain_description_text_view;
        public ImageView my_grain_color_image_view;

        public TextView row_grain_use_text_view;
        public TextView row_grain_country_text_view;

        public GrainMyRecipeViewHolder(View itemView) {
            super(itemView);
            my_grain_amount_text_view = (TextView) itemView.findViewById(R.id.my_grain_amount_text_view);
            my_grain_weight_text_view = (TextView) itemView.findViewById(R.id.my_grain_weight_text_view);
            my_grain_description_text_view = (TextView) itemView.findViewById(R.id.my_grain_description_text_view);
            my_grain_color_image_view = (ImageView) itemView.findViewById(R.id.my_grain_color_image_view);

            row_grain_use_text_view = (TextView) itemView.findViewById(R.id.row_grain_use_text_view);
            row_grain_country_text_view = (TextView) itemView.findViewById(R.id.row_grain_country_text_view);
        }
    }

    public static class HopMyRecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView my_hop_amount_text_view;
        public TextView my_hop_weight_text_view;
        public TextView my_hop_type_text_view;
        public TextView my_hop_use_text_view;
        public TextView my_hop_time_text_view;
        public TextView my_hop_form_text_view;
        public TextView row_hop_alpha_text_view;


        public HopMyRecipeViewHolder(View itemView) {
            super(itemView);
            my_hop_amount_text_view = (TextView) itemView.findViewById(R.id.my_hop_amount_text_view);
            my_hop_weight_text_view = (TextView) itemView.findViewById(R.id.my_hop_weight_text_view);
            my_hop_type_text_view = (TextView) itemView.findViewById(R.id.my_hop_type_text_view);
            my_hop_use_text_view = (TextView) itemView.findViewById(R.id.my_hop_use_text_view);
            my_hop_time_text_view = (TextView) itemView.findViewById(R.id.my_hop_time_text_view);
            my_hop_form_text_view = (TextView) itemView.findViewById(R.id.my_hop_form_text_view);
            row_hop_alpha_text_view = (TextView) itemView.findViewById(R.id.row_hop_alpha_text_view);
        }
    }

    public static class YeastMyRecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView my_yeast_type_text_view;
        public TextView my_yeast_Lab_text_view;

        public YeastMyRecipeViewHolder(View itemView) {
            super(itemView);
            my_yeast_type_text_view = (TextView) itemView.findViewById(R.id.my_yeast_type_text_view);
            my_yeast_Lab_text_view = (TextView) itemView.findViewById(R.id.my_yeast_Lab_text_view);
        }
    }

    public static class InstructionMyRecipeViewHolder extends AbstractDraggableSwipeableItemViewHolder
            implements View.OnClickListener {

        public int viewType;

        public ViewGroup mContainer;
        public View mDragHandle;
        public TextView my_instruction_text_text_view;
        public TextView positionTextView;
        public ViewHolderListener viewHolderListener;

        public InstructionMyRecipeViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            mContainer = (ViewGroup) itemView.findViewById(R.id.container);
            mDragHandle = itemView.findViewById(R.id.drag_handle);
            my_instruction_text_text_view = (TextView) itemView.findViewById(R.id.my_instruction_text_text_view);
            positionTextView = (TextView) itemView.findViewById(R.id.position);
            mContainer.setOnClickListener(this);
        }

        /**
         * Gets the View to animate for swiping
         *
         * @return The container view to animate for swiping
         */
        @Override
        public View getSwipeableContainerView() {
            return mContainer;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.container:
                    viewHolderListener.onSelectionClicked();
                    break;
            }
        }

        public interface ViewHolderListener {
            void onSelectionClicked();
        }
    }


    public static class ImageMyRecipeViewHolder extends RecyclerView.ViewHolder {
        public ImageView my_beer_image_view;

        public ImageMyRecipeViewHolder(View itemView) {
            super(itemView);
            my_beer_image_view = (ImageView) itemView.findViewById(R.id.single_image_view);
        }
    }

    public static class AdminItemViewHolder extends RecyclerView.ViewHolder {
        public TextView vTitle;
        public TextView vAuthor;
        public ImageView vimage;
        public ImageView vuser_image_view;
        public TextView vtime_from_post_text_view;
        public TextView my_recipe_published_text_view;
        public FrameLayout lyt_container;

        public AdminItemViewHolder(View v) {
            super(v);
            vTitle = (TextView) v.findViewById(R.id.itemTitle);
            vAuthor = (TextView) v.findViewById(R.id.itemAuthor);
            vimage = (ImageView) v.findViewById(R.id.image);
            vuser_image_view = (ImageView) v.findViewById(R.id.my_recipes_row_user_image_view);
            vtime_from_post_text_view = (TextView) v.findViewById(R.id.my_recipes_row_time_from_post_text_view);
            lyt_container = (FrameLayout) v.findViewById(R.id.lyt_container);
            my_recipe_published_text_view = (TextView) v.findViewById(R.id.my_recipe_published_text_view);
        }
    }

    public static class GridImageItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView vimage;

        public GridImageItemViewHolder(View v) {
            super(v);

            vimage = (ImageView) v.findViewById(R.id.gridImage);
        }
    }


    public static class RecipeImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView recipeImage;

        public RecipeImageViewHolder(View itemView) {
            super(itemView);
            recipeImage = (ImageView) itemView.findViewById(R.id.single_image_view);
        }
    }
}

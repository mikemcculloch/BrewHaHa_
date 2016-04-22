package brightseer.com.brewhaha.recipe_fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.koushikdutta.ion.Ion;

import org.joda.time.DateTime;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.helper.RecyclerItemClickListener;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.Comment;
import brightseer.com.brewhaha.models.RecipeDetail;
import brightseer.com.brewhaha.recipe_adapters.CommentViewHolder;

/**
 * Created by wooan on 3/21/2016.
 */
public class OverviewFragment extends BaseRecipeFragment implements View.OnClickListener {
    private View rootView;
    private RecipeDetail recipeDetail = new RecipeDetail();
    private String recipeTitle;
    private TextView recipe_title_text_view, recipe_description_text_view, original_gravity_text_view, final_gravity_text_view, bitterness_text_view, srm_color_text_view, abv_text_view;
    private ImageView circle_srm_image_view;
    private RecyclerView comments_recycler_view;
    private EditText recipe_comment_edit_view;
    private View send_comment_button;
    private Firebase rootRef;
    FirebaseRecyclerAdapter mAdapter;

    public OverviewFragment() {
    }

    public static OverviewFragment newInstance(int centerX, int centerY, int color, RecipeDetail _recipeDetail, String _recipeTitle) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
        args.putSerializable(Constants.bundleRecipeDetail, _recipeDetail);
        args.putString(Constants.exRecipeTitle, _recipeTitle);

        OverviewFragment fragment = new OverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe_overview, container, false);
        rootView = SetCircularReveal(rootView);
//        rootView.setBackgroundColor(getArguments().getInt("color"));

        ReadBundle();
        initViews();
        populateViews();
        initFirebaseDb();

        RecipeDetailListener();
        initCommentRecyclerView();
        return rootView;
    }

    private void ReadBundle() {
        recipeDetail = (RecipeDetail) getArguments().getSerializable(Constants.bundleRecipeDetail);
        recipeTitle = getArguments().getString(Constants.exRecipeTitle);

//        recipeDesctiption = getArguments().getString(Constants.exRecipeDesctiption);
//        authorImageUrl = getArguments().getString(Constants.exAuthorImageUrl);
//        recipeDateCreated = getArguments().getString(Constants.exRecipeDateCreated);
//        recipeDateModified = getArguments().getString(Constants.exRecipeDateModified);
//        recipeContentId = getArguments().getInt(Constants.exContentItemPk);
    }

    private void initViews() {
        recipe_title_text_view = (TextView) rootView.findViewById(R.id.recipe_title_text_view);

        recipe_description_text_view = (TextView) rootView.findViewById(R.id.recipe_description_text_view);

        original_gravity_text_view = (TextView) rootView.findViewById(R.id.original_gravity_text_view);
        final_gravity_text_view = (TextView) rootView.findViewById(R.id.final_gravity_text_view);

        bitterness_text_view = (TextView) rootView.findViewById(R.id.bitterness_text_view);

        srm_color_text_view = (TextView) rootView.findViewById(R.id.srm_color_text_view);

        abv_text_view = (TextView) rootView.findViewById(R.id.abv_text_view);

        circle_srm_image_view = (ImageView) rootView.findViewById(R.id.circle_srm_image_view);


        recipe_comment_edit_view = (EditText) rootView.findViewById(R.id.recipe_comment_edit_view);
        send_comment_button = rootView.findViewById(R.id.send_comment_button);
        send_comment_button.setOnClickListener(this);
    }

    private void populateViews() {
        recipe_title_text_view.setText(recipeTitle);
        recipe_description_text_view.setText(recipeDetail.getDescription());
        original_gravity_text_view.setText(recipeDetail.getOriginalGravity());
        final_gravity_text_view.setText(recipeDetail.getFinalGravity());
        bitterness_text_view.setText(recipeDetail.getBitternessIbu());
        srm_color_text_view.setText(recipeDetail.getColorSrm());
        abv_text_view.setText(recipeDetail.getAlcoholByVol() + "%");

        int newColor = Color.parseColor(recipeDetail.getSrmHex());
        Drawable mDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawable = getContext().getResources().getDrawable(R.drawable.circle_srm, getActivity().getTheme());
        } else {
            mDrawable = getContext().getResources().getDrawable(R.drawable.circle_srm);
        }
        mDrawable.setColorFilter(new PorterDuffColorFilter(newColor, PorterDuff.Mode.MULTIPLY));
        circle_srm_image_view.setImageDrawable(mDrawable);

        recipe_comment_edit_view = (EditText) rootView.findViewById(R.id.recipe_comment_edit_view);
        send_comment_button = rootView.findViewById(R.id.send_comment_button);
        send_comment_button.setOnClickListener(this);
    }

    private void initFirebaseDb() {
        rootRef = new Firebase(Constants.fireBaseRoot);
    }

    private void initCommentRecyclerView() {
        try {
            LinearLayoutManager recylerViewLayoutManager = new LinearLayoutManager(getActivity());
            recylerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recylerViewLayoutManager.scrollToPosition(0);

            comments_recycler_view = (RecyclerView) rootView.findViewById(R.id.comments_recycler_view);
            comments_recycler_view.setLayoutManager(recylerViewLayoutManager);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                comments_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider, getActivity().getTheme()), true));
            } else {
                comments_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
            }

            Firebase ref = rootRef.child(Constants.exComments).child(recipeTitle);
            mAdapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(Comment.class, R.layout.row_comment, CommentViewHolder.class, ref) {
                @Override
                public void populateViewHolder(CommentViewHolder commentViewHolder, Comment comment, int position) {
                    Ion.with(commentViewHolder.comment_user_image)
                            .placeholder(R.drawable.ic_person_black_24dp)
                            .error(R.drawable.ic_person_black_24dp)
                            .centerCrop()
                            .transform(Utilities.GetRoundTransform())
                            .load(comment.getImageUrl());

                    commentViewHolder.comment_item_author.setText(comment.getAuthorName());
                    commentViewHolder.comment_item_timestamp.setText(Utilities.DisplayTimeFormater(comment.getDateCreated()));
                    commentViewHolder.comment_text_view.setText(comment.getBody());
                }
            };
            comments_recycler_view.setAdapter(mAdapter);

            comments_recycler_view.setItemAnimator(new DefaultItemAnimator());
            comments_recycler_view.addOnItemTouchListener(
                    new RecyclerItemClickListener(getContext(), comments_recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {

                            } catch (Exception e) {
                                if (BuildConfig.DEBUG) {
                                    Log.e(Constants.LOG, e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {
                        }
                    })
            );
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_comment_button:
                AddComment();
                break;

        }
    }

    private void AddComment() {
        try {

//            if (!BrewSharedPrefs.getIsUserLoggedIn())
//            {
//
//                return;
//            }

            Comment comment = new Comment();
//            comment.setAuthorName(BrewSharedPrefs.getScreenkm  jhzvV,,Name());
            comment.setBody(recipe_comment_edit_view.getText().toString());
            comment.setFeedKey(recipeDetail.getFeedKey());
//            comment.setImageUrl(BrewSharedPrefs.getUserProfileImageUrl());
            comment.setDateCreated(DateTime.now().toString());

            Firebase postRef = rootRef.child(Constants.exComments).child(recipeTitle).push();
            postRef.setValue(comment);
            recipe_comment_edit_view.setText("");

        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void RecipeDetailListener() {
        try {
            Firebase ref = new Firebase(Constants.fireBaseRoot).child(Constants.exRecipeDetail);
            Query queryRef = ref.orderByChild(Constants.exFeedKey).equalTo(recipeDetail.getFeedKey());

            queryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        recipeDetail = postSnapshot.getValue(RecipeDetail.class);
                        recipeDetail.setKey(postSnapshot.getKey());
                    }
                    populateViews();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }
}

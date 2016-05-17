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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import java.util.Date;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.RecipeCardsActivity;
import brightseer.com.brewhaha.firebase_helpers.FirebaseCrud;
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
    private String recipeTitle, authorImageUrl;
    private TextView recipe_title_text_view, recipe_description_text_view, original_gravity_text_view, final_gravity_text_view, bitterness_text_view, srm_color_text_view, abv_text_view,
            yield_by_gallon_text_view;
    private ImageView circle_srm_image_view;
    private EditText recipe_comment_edit_view;
    private View send_comment_button;
    private Firebase rootRef, commentRef;
    FirebaseRecyclerAdapter mAdapter;
    private String feedKey;

    public OverviewFragment() {
    }

    public static OverviewFragment newInstance(int centerX, int centerY, int color, RecipeDetail _recipeDetail, String _recipeTitle, String _feedKey, String _authorImageUrl) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
        args.putSerializable(Constants.bundleRecipeDetail, _recipeDetail);
        args.putString(Constants.exRecipeTitle, _recipeTitle);
        args.putString(Constants.fbFeedKey, _feedKey);
        args.putString(Constants.exAuthorImageUrl, _authorImageUrl);

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
        feedKey = String.valueOf(getArguments().get(Constants.fbFeedKey));
        authorImageUrl = getArguments().getString(Constants.exAuthorImageUrl);
    }

    private void initViews() {
        recipe_title_text_view = (TextView) rootView.findViewById(R.id.recipe_title_text_view);
        recipe_description_text_view = (TextView) rootView.findViewById(R.id.recipe_description_text_view);
        original_gravity_text_view = (TextView) rootView.findViewById(R.id.original_gravity_text_view);

        final ImageView send_drawable = (ImageView) rootView.findViewById(R.id.send_drawable);
        send_comment_button = rootView.findViewById(R.id.send_comment_button);
        send_comment_button.setOnClickListener(this);

        final_gravity_text_view = (TextView) rootView.findViewById(R.id.final_gravity_text_view);
        bitterness_text_view = (TextView) rootView.findViewById(R.id.bitterness_text_view);
        srm_color_text_view = (TextView) rootView.findViewById(R.id.srm_color_text_view);
        abv_text_view = (TextView) rootView.findViewById(R.id.abv_text_view);
        circle_srm_image_view = (ImageView) rootView.findViewById(R.id.circle_srm_image_view);
        recipe_comment_edit_view = (EditText) rootView.findViewById(R.id.recipe_comment_edit_view);

        recipe_comment_edit_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    send_drawable.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_send_white_24dp, getActivity().getTheme()));
                    send_comment_button.setBackground(getContext().getResources().getDrawable(R.drawable.circle_send_inactive, getActivity().getTheme()));
                } else {
                    send_drawable.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_send_white_24dp));
                    send_comment_button.setBackground(getContext().getResources().getDrawable(R.drawable.circle_send_inactive));
                }

                String viewText = String.valueOf(recipe_comment_edit_view.getText());
                if (!viewText.isEmpty()) {
                    ((RecipeCardsActivity) getActivity()).ChildShowLoginDialog();

                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        send_drawable.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_send_black_24dp, getActivity().getTheme()));
                        send_comment_button.setBackground(getContext().getResources().getDrawable(R.drawable.circle_send, getActivity().getTheme()));
                    } else {
                        send_drawable.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_send_black_24dp));
                        send_comment_button.setBackground(getContext().getResources().getDrawable(R.drawable.circle_send));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        recipe_comment_edit_view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    send_drawable.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_send_white_24dp, getActivity().getTheme()));
//                    send_comment_button.setBackground(getContext().getResources().getDrawable(R.drawable.circle_send_inactive, getActivity().getTheme()));
//                } else {
//                    send_drawable.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_send_white_24dp));
//                    send_comment_button.setBackground(getContext().getResources().getDrawable(R.drawable.circle_send_inactive));
//                }
//
//                String viewText = String.valueOf(recipe_comment_edit_view.getText());
//                if (hasFocus && !viewText.isEmpty() ) {
//                    ((RecipeCardsActivity) getActivity()).ChildShowLoginDialog();
//
//                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        send_drawable.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_send_black_24dp, getActivity().getTheme()));
//                        send_comment_button.setBackground(getContext().getResources().getDrawable(R.drawable.circle_send, getActivity().getTheme()));
//                    } else {
//                        send_drawable.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_send_black_24dp));
//                        send_comment_button.setBackground(getContext().getResources().getDrawable(R.drawable.circle_send));
//                    }
//                }
            }
        });


        yield_by_gallon_text_view = (TextView) rootView.findViewById(R.id.yield_by_gallon_text_view);
    }

    private void populateViews() {
        try {
            recipe_title_text_view.setText(recipeTitle);
            recipe_description_text_view.setText(recipeDetail.getDescription());
            original_gravity_text_view.setText(recipeDetail.getOriginalGravity());
            final_gravity_text_view.setText(recipeDetail.getFinalGravity());
            bitterness_text_view.setText(recipeDetail.getBitternessIbu());
            srm_color_text_view.setText(recipeDetail.getColorSrm());
            String abvText = recipeDetail.getAlcoholByVol() + "%";
            abv_text_view.setText(abvText);

            int newColor = Color.parseColor(recipeDetail.getSrmHex());
            Drawable mDrawable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mDrawable = getContext().getResources().getDrawable(R.drawable.circle_srm, getActivity().getTheme());
            } else {
                mDrawable = getContext().getResources().getDrawable(R.drawable.circle_srm);
            }
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(newColor, PorterDuff.Mode.MULTIPLY);
            if (mDrawable != null) {
                mDrawable.setColorFilter(porterDuffColorFilter);
                circle_srm_image_view.setImageDrawable(mDrawable);
            }

            send_comment_button.setOnClickListener(this);

            String yield = recipeDetail.getYieldByGallon() + " Gallons";
            yield_by_gallon_text_view.setText(yield);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void initFirebaseDb() {
        rootRef = new Firebase(Constants.fireBaseRoot).child(Constants.fbRecipeDetail).child(feedKey);
        commentRef = new Firebase(Constants.fireBaseRoot).child(Constants.fbComments).child(feedKey);
    }

    private void initCommentRecyclerView() {
        try {
            RecyclerView comments_recycler_view = (RecyclerView) rootView.findViewById(R.id.comments_recycler_view);
            comments_recycler_view.setHasFixedSize(false);

            LinearLayoutManager recylerViewLayoutManager = new LinearLayoutManager(getActivity());
            recylerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recylerViewLayoutManager.scrollToPosition(0);
            recylerViewLayoutManager.setAutoMeasureEnabled(true);
            comments_recycler_view.setLayoutManager(recylerViewLayoutManager);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                comments_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider, getActivity().getTheme()), true));
            } else {
                comments_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
            }

            Query queryRef = commentRef.orderByChild("dateCreatedInverse");

            mAdapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(Comment.class, R.layout.row_comment, CommentViewHolder.class, queryRef) {
                @Override
                public void populateViewHolder(CommentViewHolder commentViewHolder, Comment comment, int position) {
                    Picasso.with(getActivity())
                            .load(comment.getAuthorImageUrl())
                            .fit().centerCrop()
                            .transform(Utilities.GetRoundTransform())
                            .into(commentViewHolder.comment_user_image);

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
            if (BrewSharedPrefs.getUserKey().isEmpty()) {
                ((RecipeCardsActivity) getActivity()).ChildShowLoginDialog();
                return;
            }
            if (recipe_comment_edit_view.getText().toString().isEmpty())
                return;

            AuthData authData = commentRef.getAuth();
            if (authData != null) {
                long inverse = Long.MAX_VALUE - DateTime.now().getMillis();

                Comment comment = new Comment();
                comment.setBody(recipe_comment_edit_view.getText().toString());
                comment.setFeedKey(feedKey);
                comment.setAuthorName(String.valueOf(authData.getProviderData().get("displayName")));
                comment.setAuthorEmail(BrewSharedPrefs.getEmailAddress());
                comment.setAuthorImageUrl(String.valueOf(authData.getProviderData().get("profileImageURL")));
                comment.setDateCreated(DateTime.now().toString());
                comment.setDateCreatedInverse(inverse);

                FirebaseCrud firebaseCrud = new FirebaseCrud();
                firebaseCrud.AddComment(comment, feedKey);

                recipe_comment_edit_view.setText("");
            } else {

            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void RecipeDetailListener() {
        try {
            rootRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    recipeDetail = dataSnapshot.getValue(RecipeDetail.class);
                    if (recipeDetail != null) {
                        recipeDetail.setKey(dataSnapshot.getKey());
                        populateViews();
                    }
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
        if (mAdapter != null)
            mAdapter.cleanup();
    }
}

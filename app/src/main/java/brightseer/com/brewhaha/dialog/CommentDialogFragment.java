package brightseer.com.brewhaha.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.firebase_helpers.FirebaseAdapters;
import brightseer.com.brewhaha.firebase_helpers.FirebaseCrud;
import brightseer.com.brewhaha.helper.RecyclerItemClickListener;
import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.Comment;

/**
 * Created by wooan on 5/22/2016.
 */
public class CommentDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {
    private View rootView;
    private FirebaseRecyclerAdapter mAdapter;
    EditText comment_edit_view;
    String feedKey, userName, authorImageUrl;

    public interface CommentDialogListener {
        void onFinishEditDialog(String inputText);
    }

    public CommentDialogFragment() {
    }

    public static CommentDialogFragment newInstance(int centerX, int centerY, String user_name, String user_image_url, String feedKey) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putString(Constants.exUsername, user_name);
        args.putString(Constants.exAuthorImageUrl, user_image_url);
        args.putString(Constants.fbFeedKey, feedKey);
        CommentDialogFragment fragment = new CommentDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dialog_comment, container, false);
        rootView = Utilities.SetCircularReveal(rootView, this, 700);
        ReadBundle();
        Utilities.GetStatusBarHeight(getActivity(), rootView);

        initViews();
        initCommentRecyclerView();
        return rootView;
    }

    private void ReadBundle() {
        userName = getArguments().getString(Constants.exUsername);
        feedKey = String.valueOf(getArguments().get(Constants.fbFeedKey));
        authorImageUrl = getArguments().getString(Constants.exAuthorImageUrl);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            String testText = comment_edit_view.getText().toString();

            CommentDialogListener activity = (CommentDialogListener) getActivity();
            activity.onFinishEditDialog(testText);

            DismissDialog();
            return true;
        }
        return false;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void initViews() {
        comment_edit_view = (EditText) rootView.findViewById(R.id.comment_edit_view);

        ImageView comment_author_image_view = (ImageView) rootView.findViewById(R.id.comment_author_image_view);
        Picasso.with(getActivity())
                .load(authorImageUrl)
                .transform(Utilities.GetRoundTransform())
                .into(comment_author_image_view);

        TextView comment_header = (TextView) rootView.findViewById(R.id.comment_header);
        String headerText = getResources().getString(R.string.you_are_commenting_as) + " '" + userName + "'";
        comment_header.setText(headerText);

        AppCompatButton send_comment_appcompat_button = (AppCompatButton) rootView.findViewById(R.id.send_comment_appcompat_button);
        send_comment_appcompat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = String.valueOf(comment_edit_view.getText());
                if (commentText.isEmpty()) {
                    comment_edit_view.setError("Required");
                    return;
                }
                AddComment(commentText);
                DismissDialog();
            }
        });

        AppCompatButton cancel_comment_appcompat_button = (AppCompatButton) rootView.findViewById(R.id.cancel_comment_appcompat_button);
        cancel_comment_appcompat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DismissDialog();
            }
        });
    }

    public void DismissDialog() {
        comment_edit_view.setText("");
        dismiss();
    }

    private void initCommentRecyclerView() {
        try {
            RecyclerView recipe_comment_recycler_view = (RecyclerView) rootView.findViewById(R.id.recipe_comment_recycler_view);
            recipe_comment_recycler_view.setHasFixedSize(false);

            LinearLayoutManager recylerViewLayoutManager = new LinearLayoutManager(getActivity());
            recylerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recylerViewLayoutManager.scrollToPosition(0);
            recylerViewLayoutManager.setAutoMeasureEnabled(true);
            recipe_comment_recycler_view.setLayoutManager(recylerViewLayoutManager);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                recipe_comment_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider, getActivity().getTheme()), true));
            } else {
                recipe_comment_recycler_view.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
            }
            Firebase commentRef = new Firebase(Constants.fireBaseRoot).child(Constants.fbComments).child(feedKey);
            Query queryRef = commentRef.orderByChild("dateCreatedInverse").limitToFirst(10);

            mAdapter = FirebaseAdapters.CommentFeedAdapter(queryRef, R.layout.row_comment, getContext());
            recipe_comment_recycler_view.setAdapter(mAdapter);
            recipe_comment_recycler_view.setItemAnimator(new DefaultItemAnimator());
            recipe_comment_recycler_view.addOnItemTouchListener(
                    new RecyclerItemClickListener(getContext(), recipe_comment_recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
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

    private void AddComment(String commentText) {
        try {
            long inverse = Long.MAX_VALUE - DateTime.now().getMillis();

            Comment comment = new Comment();
            comment.setBody(commentText);
            comment.setFeedKey(feedKey);
            comment.setAuthorName(userName);
            comment.setAuthorEmail(BrewSharedPrefs.getEmailAddress());
            comment.setAuthorImageUrl(authorImageUrl);
            comment.setDateCreated(DateTime.now().toString());
            comment.setDateCreatedInverse(inverse);

            FirebaseCrud firebaseCrud = new FirebaseCrud();
            firebaseCrud.AddComment(comment, feedKey);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

//    public void getStatusBarHeight() {
//        try {
//            int statusBarHeight = 0;
//            Rect displayRect = new Rect();
//            getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(displayRect);
//            statusBarHeight = displayRect.top;
//            if (statusBarHeight <= 0) {
//                int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//                if (resourceId > 0) {
//                    statusBarHeight = getResources().getDimensionPixelSize(resourceId);
//                }
//            }
//
//
//            LinearLayout parent_layout = (LinearLayout) rootView.findViewById(R.id.parent_layout);
//
//            FrameLayout.LayoutParams mainLayoutParam;
//            mainLayoutParam = (FrameLayout.LayoutParams) parent_layout.getLayoutParams();
//
//            int topMargin = mainLayoutParam.topMargin;
//            mainLayoutParam.setMargins(0, topMargin + statusBarHeight, 0, 0);
//        } catch (Exception ex) {
//            if (BuildConfig.DEBUG) {
//                Log.e(Constants.LOG, ex.getMessage());
//            }
//        }
//    }

    Runnable runnableGetHeight = new Runnable() {
        @Override
        public void run() {
            Utilities.GetStatusBarHeight(getActivity(), rootView);
        }
    };

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }





}


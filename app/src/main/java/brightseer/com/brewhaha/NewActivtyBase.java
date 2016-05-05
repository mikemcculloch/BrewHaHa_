package brightseer.com.brewhaha;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.UserProfile;

/**
 * Created by wooan on 4/21/2016.
 */
public class NewActivtyBase extends AppCompatActivity {
    public GoogleApiClient mGoogleApiClient;
    public static final int RC_SIGN_IN = 0;
    public static final int PLUS_ONE_REQUEST_CODE = 0;
    public static final int BackPressed = 69;

    private String emailAddress;

    public boolean tabletSize;
    public int cornerRadius = 200;

    private BottomSheetBehavior loginBehavior;
    private BottomSheetDialog mLoginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initGooglePlus();
        initGoogleAuth();
//        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        tabletSize = getResources().getBoolean(R.bool.isTablet);
    }

    public void setupTransistion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);

            //set the transition
            Transition ts = new android.transition.ChangeBounds();
            ts.setDuration(300);

            getWindow().setSharedElementsUseOverlay(true);

            getWindow().setSharedElementEnterTransition(ts);
            getWindow().setEnterTransition(ts);
            getWindow().setAllowEnterTransitionOverlap(true);

            getWindow().setSharedElementExitTransition(ts);
            getWindow().setExitTransition(ts);
            getWindow().setAllowReturnTransitionOverlap(true);
        }
    }

    public void eventGoogleAnalytics(String categoryId, String actionId, String labelId) {
        Tracker t = ((BrewApplication) getApplication()).getTracker(
                BrewApplication.TrackerName.APP_TRACKER);
        t.send(new HitBuilders.EventBuilder()
                .setCategory(categoryId)
                .setAction(actionId)
                .setLabel(labelId)
                .build());
    }

    public void eventGoogleAnalytics(String categoryId, String actionId, String labelId, int value) {
        Tracker t = ((BrewApplication) getApplication()).getTracker(
                BrewApplication.TrackerName.APP_TRACKER);
        t.send(new HitBuilders.EventBuilder()
                .setCategory(categoryId)
                .setAction(actionId)
                .setLabel(labelId)
                .setValue(value)
                .build());
    }

    private void initGoogleAuth() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.server_client_id))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result != null && result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            emailAddress = account.getEmail();
            new getGoogleToken().execute();

        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    private void fireBaseAuth(String idToken) {
        try {
            Firebase rootRef = new Firebase(Constants.fireBaseRoot);
            rootRef.authWithOAuthToken("google", idToken, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    UserProfile userProfile = GetUserProfile(authData);
                    evaluateUser(userProfile);
                    BrewSharedPrefs.setEmailAddress(userProfile.getEmailAddress());

                    if (mLoginDialog != null) {
                        mLoginDialog.dismiss();
                    }

                    recreate();
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    // there was an error
                }
            });
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private class getGoogleToken extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(getApplicationContext(), emailAddress, scopes);
                fireBaseAuth(token);
            } catch (IOException | GoogleAuthException e) {
                if (BuildConfig.DEBUG) {
                    Log.e(Constants.LOG, e.getMessage());
                }
            }
            return token;
        }
    }

    private void evaluateUser(final UserProfile userProfile) {
        try {
            Firebase rootRef = new Firebase(Constants.fireBaseRoot);
            Firebase ref = rootRef.child(Constants.fbUsers).child(Utilities.encodeEmail(userProfile.getEmailAddress()));
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChildren()) {
                        Firebase refUser = new Firebase(Constants.fireBaseRoot).child(Constants.fbUsers).child(Utilities.encodeEmail(userProfile.getEmailAddress()));
                        Firebase refUserPush = refUser.push();
                        refUserPush.setValue(userProfile);

                        String postId = refUserPush.getKey();
                        Firebase theChild = refUser.child(postId);
                        Map<String, Object> keyValue = new HashMap<String, Object>();
                        keyValue.put("key", postId);
                        theChild.updateChildren(keyValue);
                    }

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        UserProfile userProfile = postSnapshot.getValue(UserProfile.class);
                        BrewSharedPrefs.setUserKey(userProfile.getKey());
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

    public UserProfile GetUserProfile(AuthData authData) {
        try {
            if (authData != null) {
                String uId = String.valueOf(authData.getProviderData().get("id"));
                String displayName = String.valueOf(authData.getProviderData().get("displayName"));
                String emailAddress = String.valueOf(authData.getProviderData().get("email"));
                String userProfileImage = String.valueOf(authData.getProviderData().get("profileImageURL"));


                UserProfile userProfile = new UserProfile();
                userProfile.setEmailAddress(Utilities.encodeEmail(emailAddress));
                userProfile.setDisplayName(displayName);
                userProfile.setImageUrl(userProfileImage);
                userProfile.setUid(uId);
                userProfile.setKey("");

                return userProfile;
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
        return null;
    }

    public void showLoginBottomSheetDialog(Activity activity, View bottomSheet) {

        final Firebase rootRef = new Firebase(Constants.fireBaseRoot);
        AuthData authData = rootRef.getAuth();
        if (authData != null) {
            return;
        }

        loginBehavior = BottomSheetBehavior.from(bottomSheet);
        loginBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });


        if (loginBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            loginBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        mLoginDialog = new BottomSheetDialog(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.sheet_login_dialog, null);

        SignInButton signInButton = (SignInButton) view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthData authData = rootRef.getAuth();
                if (authData == null) {
                    googleSignIn();
                }
            }
        });

        mLoginDialog.setContentView(view);
        mLoginDialog.show();
        mLoginDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mLoginDialog = null;
            }
        });
    }

}

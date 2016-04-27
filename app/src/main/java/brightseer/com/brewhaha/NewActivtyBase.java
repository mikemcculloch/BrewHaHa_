package brightseer.com.brewhaha;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

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

    public boolean tabletSize;
    public int cornerRadius = 200;

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

//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private Transition exitTransition() {
//        ChangeBounds bounds = new ChangeBounds();
//        bounds.setInterpolator(new BounceInterpolator());
//        bounds.setDuration(2000);
//
//        return bounds;
//    }
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private Transition reenterTransition() {
//        ChangeBounds bounds = new ChangeBounds();
//        bounds.setInterpolator(new OvershootInterpolator());
//        bounds.setDuration(2000);
//
//        return bounds;
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initGooglePlus();
        initGoogleAuth();
//        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        tabletSize = getResources().getBoolean(R.bool.isTablet);
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

    public void initGoogleAuth() {
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

    public void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
}

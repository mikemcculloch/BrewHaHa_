package brightseer.com.brewhaha;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import brightseer.com.brewhaha.helper.Utilities;
import brightseer.com.brewhaha.models.UserProfile;


/**
 * Created by wooan on 4/23/2016.
 */
public class LoginActivity extends NewActivtyBase {
    private Toolbar toolbar;
    private Firebase rootRef;
    private String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupTransistion();
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_login);

            initViews();
            initFirebaseDb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFirebaseDb() {
        rootRef = new Firebase(Constants.fireBaseRoot);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.standard_toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            getSupportActionBar().setTitle(getResources().getString(R.string.string_login));
        }

        ImageView image = (ImageView) findViewById(R.id.image);
        Ion.with(image).load(Constants.loginImage);

//        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_WIDE);
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                googleSignIn();
//            }
//        });
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

    public void handleSignInResult(GoogleSignInResult result) {
        if (result != null && result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            emailAddress = account.getEmail();
            new getGoogleToken().execute();

        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    public void fireBaseAuth(String idToken) {
        try {
            rootRef.authWithOAuthToken("google", idToken, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    UserProfile userProfile = GetUserProfile(authData);
                    evaluateUser(userProfile);
                    BrewSharedPrefs.setEmailAddress(userProfile.getEmailAddress());


                    onBackPressed();
//                    UpdateUi(userProfile);
                    // the Google user is now authenticated with your Firebase app
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

    public class getGoogleToken extends AsyncTask<String, Void, String> {
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

    public void evaluateUser(final UserProfile userProfile) {
        try {
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

                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                finish();
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }
}

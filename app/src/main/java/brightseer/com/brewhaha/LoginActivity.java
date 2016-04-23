package brightseer.com.brewhaha;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.firebase.client.Firebase;
import com.google.android.gms.common.SignInButton;
import com.koushikdutta.ion.Ion;


/**
 * Created by wooan on 4/23/2016.
 */
public class LoginActivity extends NewActivtyBase {
    Toolbar toolbar;
    Firebase rootRef;

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
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }

        ImageView image = (ImageView) findViewById(R.id.image);
        Ion.with(image)
                .load(Constants.loginImage);

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
//        signInButton.setScopes(gso.getScopeArray());
    }

}

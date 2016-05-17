package brightseer.com.brewhaha.Depricated;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import brightseer.com.brewhaha.R;

/**
 * Created by wooan_000 on 1/11/2015.
 */
public class InterstitialActivity extends BaseActivity {
    private InterstitialAd mInterstitialAd;
    private Button mNextLevelButton;
    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _mContext = this;
        initGoogleAnalytics(this.getClass().getSimpleName());
        // Create an interstitial ad. When a natural transition in the app occurs (such as a
        // level ending in a game), show the interstitial. In this simple example, the press of a
        // button is used instead.
        //
        // If the button is clicked before the interstitial is loaded, the user should proceed to
        // the next part of the app (in this case, the next level).
        //
        // If the interstitial is finished loading, the user will view the interstitial before
        // proceeding.
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("myAdUnitId");

        // Create an ad request.
        AdRequest adRequestBuilder = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getResources().getText(R.string.deviceIdMike).toString())
                .build();

        // Set an AdListener.
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(InterstitialActivity.this,
                        "The interstitial is loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                goToNextLevel();
            }
        });

        // Start loading the ad now so that it is ready by the time the user is ready to go to
        // the next level.
        mInterstitialAd.loadAd(adRequestBuilder);

        // Create the button to go to the next level.
        mNextLevelButton = new Button(this);
        mNextLevelButton.setText("Next Level");
        mNextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the interstitial if it is ready. Otherwise, proceed to the next level
                // without ever showing it.
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    // Proceed to the next level.
                    goToNextLevel();
                }
            }
        });

        // Add the next level button to the layout.
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(mNextLevelButton);

        // Create a TextView to display the current level.
        mTextView = new TextView(this);
        mTextView.setText("Level 1");
        layout.addView(mTextView);

        setContentView(layout);
    }

    public void goToNextLevel() {
        // Show the next level (and disable the next level button since there are no more levels.
        mNextLevelButton.setEnabled(false);
        mTextView.setText("Level 2");
    }
}
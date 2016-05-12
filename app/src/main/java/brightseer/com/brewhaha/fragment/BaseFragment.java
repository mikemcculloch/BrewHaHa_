package brightseer.com.brewhaha.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.BrewApplication;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;
import brightseer.com.brewhaha.helper.ToastAdListener;
import brightseer.com.brewhaha.objects.SrmColorKey;

/**
 * Created by wooan_000 on 1/11/2015.
 */
public class BaseFragment extends Fragment {
    public AdView mAdView;
    public String contentItemPk = "";
    protected ContextMenu contextMenu;
    public Context _fContext;

    public Dialog dialogProgress;
    public boolean tabletSize;
    public String contentToken = "";
    public List<SrmColorKey> SrmColorKeyList = new Vector<>();
    public GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _fContext = getActivity();
        tabletSize = getResources().getBoolean(R.bool.isTablet);
    }

    public void initAdMob(View view) {
        mAdView = (AdView) view.findViewById(R.id.adView);
        if (BuildConfig.FLAVOR == Constants.flavorFull) {
            mAdView.setVisibility(View.GONE);
            return;
        }
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice(getResources().getText(R.string.deviceIdMike).toString())
//                .addTestDevice(getResources().getText(R.string.deviceIds4).toString())
//                .addTestDevice(getText(R.string.deviceIdMikeTablet).toString())
//                .addTestDevice(getText(R.string.deviceElyseS3).toString())
                .build();
        mAdView.setAdListener(new ToastAdListener(_fContext));

//        mAdView.setAdUnitId(getResources().getString(R.string.banner_standard_id));
//        mAdView.setAdSize(AdSize.SMART_BANNER);

        mAdView.loadAd(adRequest);

    }


    public void LoadDialog(Context mContext, boolean dimBackground, boolean cancelable) {
        dialogProgress = new Dialog(mContext);
        dialogProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogProgress.setContentView(R.layout.dialog_loading);
        dialogProgress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogProgress.setCancelable(cancelable);

        if (!dimBackground) {
            dialogProgress.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        dialogProgress.show();
    }

    private DialogInterface.OnClickListener negativeClick = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
        }
    };

    public void AlertLoginPrompt(Context mContext, String title, String body, String loginText, String negativeText) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(body)
                .setCancelable(false)
                .setPositiveButton(loginText, loginClick)
//                .setNeutralButton(signUpText, signUpClick)
                .setNegativeButton(negativeText, negativeClick);


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private DialogInterface.OnClickListener loginClick = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            }
        }
    };


    public void initGoogleAnalytics(String path) {
        Tracker t = ((BrewApplication) getActivity().getApplication()).getTracker(
                BrewApplication.TrackerName.APP_TRACKER);
        t.setScreenName(path);
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    public void eventGoogleAnalytics(String categoryId, String actionId, String labelId) {
        Tracker t = ((BrewApplication) getActivity().getApplication()).getTracker(
                BrewApplication.TrackerName.APP_TRACKER);
        t.send(new HitBuilders.EventBuilder()
                .setCategory(categoryId)
                .setAction(actionId)
                .setLabel(labelId)
                .build());
    }

    public void eventGoogleAnalytics(String categoryId, String actionId, String labelId, int value) {
        Tracker t = ((BrewApplication) getActivity().getApplication()).getTracker(
                BrewApplication.TrackerName.APP_TRACKER);
        t.send(new HitBuilders.EventBuilder()
                .setCategory(categoryId)
                .setAction(actionId)
                .setLabel(labelId)
                .setValue(value)
                .build());
    }

    public boolean supportsViewElevation() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }
}

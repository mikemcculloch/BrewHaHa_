package brightseer.com.brewhaha.recipe_fragments;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;

/**
 * Created by wooan on 3/21/2016.
 */
public class BaseRecipeFragment extends Fragment {

//    public View SetCircularReveal(View rootView){
//        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
//                                       int oldRight, int oldBottom) {
//                v.removeOnLayoutChangeListener(this);
//                int cx = getArguments().getInt("cx");
//                int cy = getArguments().getInt("cy");
//
//                // get the hypothenuse so the radius is from one corner to the other
//                int radius = (int) Math.hypot(right, bottom);
//
//                Animator reveal = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, radius);
//                reveal.setInterpolator(new DecelerateInterpolator(2f));
//                reveal.setDuration(500);
//                reveal.start();
//            }
//        });
//        return rootView;
//    }

    public boolean supportsViewElevation() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }
}

package brightseer.com.brewhaha.helper;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.RoundedDrawable;
import com.squareup.picasso.Transformation;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.R;

/**
 * Created by mccul_000 on 12/6/2014.
 */
public class Utilities {
    private Context _context;

    // constructor
    public Utilities(Context context) {
        this._context = context;
    }

    public static String DisplayTimeFormater(String itemDate) {
        DateTime dt = DateTime.now();
        DateTime itemTime = new DateTime(itemDate);

        String timeFromPost;
        Days daysBetween = Days.daysBetween(itemTime, dt);
        if (daysBetween.isLessThan(Days.days(1))) {
            if (Hours.hoursBetween(itemTime, dt).isLessThan(Hours.hours(1))) {
                timeFromPost = Minutes.minutesBetween(itemTime, dt).toString().replace("PT", "").replace("M", "") + " mins ago";
            } else {
                timeFromPost = Hours.hoursBetween(itemTime, dt).toString().replace("PT", "").replace("H", "") + " hours ago";
            }
        } else {
            DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/YYYY");
            timeFromPost = fmt.print(itemTime);
        }

        return timeFromPost;
    }

    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }

    public static void saveImage(View view) {
        Uri outputFileUri = null;
        try {
            view.buildDrawingCache();
            Bitmap bm = view.getDrawingCache();
            OutputStream fOut = null;

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + ".jpg";

            try {
                File root = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "BrewHaHa" + File.separator);
                root.mkdirs();
                File sdImageMainDirectory = new File(root, imageFileName);
                outputFileUri = Uri.fromFile(sdImageMainDirectory);
                fOut = new FileOutputStream(sdImageMainDirectory);
            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    Log.e(Constants.LOG, e.getMessage());
                }
                Toast.makeText(view.getContext(), "Error occured. Please try again later.", Toast.LENGTH_SHORT).show();
            }

            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    Log.e(Constants.LOG, e.getMessage());
                }
            }


            Toast.makeText(view.getContext(), "File saved:" + outputFileUri.getPath(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
    }

    public static boolean hitTest(View v, int x, int y) {
        final int tx = (int) (ViewCompat.getTranslationX(v) + 0.5f);
        final int ty = (int) (ViewCompat.getTranslationY(v) + 0.5f);
        final int left = v.getLeft() + tx;
        final int right = v.getRight() + tx;
        final int top = v.getTop() + ty;
        final int bottom = v.getBottom() + ty;

        return (x >= left) && (x <= right) && (y >= top) && (y <= bottom);
    }

    public static Transformation GetRoundTransform() {
        Transformation trans = new Transformation() {
            boolean isOval = false;
            int cornerRadius = 100;

            @Override
            public Bitmap transform(Bitmap bitmap) {
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, cornerRadius, cornerRadius, false);
                Bitmap transformed = RoundedDrawable.fromBitmap(scaled).setScaleType(ImageView.ScaleType.CENTER_CROP).setCornerRadius(cornerRadius).setOval(isOval).toBitmap();
                if (!bitmap.equals(scaled)) bitmap.recycle();
                if (!scaled.equals(transformed)) bitmap.recycle();

                return transformed;
            }

            @Override
            public String key() {
                return "rounded_radius_" + cornerRadius + "_oval_" + isOval;
            }
        };

        return trans;
    }

    public static Drawable SetDrawableColor(String hexColor, Context context, Activity activity) {
        try {
            int newColor = Color.parseColor(hexColor);
            Drawable mDrawable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mDrawable = context.getResources().getDrawable(R.drawable.circle_srm, activity.getTheme());
            } else {
                mDrawable = context.getResources().getDrawable(R.drawable.circle_srm);
            }

            mDrawable.setColorFilter(new PorterDuffColorFilter(newColor, PorterDuff.Mode.MULTIPLY));
            return mDrawable;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    public static String decodeEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }

    public static void RunSnackBar(View view, String message, View.OnClickListener mOnClickListener) {
        try {
            Snackbar snackbar = Snackbar
                    .make(view, message, Snackbar.LENGTH_LONG)
                    .setAction("Undo", mOnClickListener);
            snackbar.setActionTextColor(Color.RED);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.DKGRAY);
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();

        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public static void DeletePrompt(Context context, DialogInterface.OnClickListener positiveClick) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.delete_message)
                    .setTitle(R.string.delete_title);

            builder.setPositiveButton(R.string.delete_yes, positiveClick);
            builder.setNegativeButton(R.string.delete_no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    return;
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public static View SetCircularReveal(View view, final Fragment fragment, final int duration){
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                       int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                int cx = fragment.getArguments().getInt("cx");
                int cy = fragment.getArguments().getInt("cy");

                // get the hypothenuse so the radius is from one corner to the other
                int radius = (int) Math.hypot(right, bottom);

                Animator reveal = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, radius);
                reveal.setInterpolator(new DecelerateInterpolator(2f));
                reveal.setDuration(duration);
                reveal.start();
            }
        });
        return view;
    }

    public static Point GetCenterPointOfView(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int x = location[0] + view.getWidth() / 2;
        int y = location[1] + view.getHeight() / 2;
        return new Point(x, y);
    }

    public static void GetStatusBarHeight(Activity activity, View rootView) {
        try {
            int statusBarHeight = 0;
            Rect displayRect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(displayRect);
            statusBarHeight = displayRect.top;
            if (statusBarHeight <= 0) {
                int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
                }
            }


            LinearLayout parent_layout = (LinearLayout) rootView.findViewById(R.id.parent_layout);

            FrameLayout.LayoutParams mainLayoutParam;
            mainLayoutParam = (FrameLayout.LayoutParams) parent_layout.getLayoutParams();

            int topMargin = mainLayoutParam.topMargin;
            mainLayoutParam.setMargins(0, topMargin + statusBarHeight, 0, 0);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }





//
//    public class BackgroundTask extends AsyncTask<Void, Void, Void> {
//        public ProgressDialog dialog;
//
//        public BackgroundTask(Activity activity) {
//            dialog = new ProgressDialog(activity);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            dialog.setMessage("Doing something, please wait.");
//            dialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//    }
}


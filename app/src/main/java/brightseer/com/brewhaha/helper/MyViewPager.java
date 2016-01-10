package brightseer.com.brewhaha.helper;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wooan_000 on 1/26/2015.
 */
public class MyViewPager extends ViewPager {

    private boolean isPagingEnabled;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isPagingEnabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);

    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }

}

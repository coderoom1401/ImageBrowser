package coderoom1401.lawrence.imagebrowser;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by wangjingyuan on 16/7/18.
 */
public class MyScroller extends Scroller {
    private int mDuration;

    public MyScroller(Context context, int duration) {
        super(context);
        mDuration = duration;
    }

    public MyScroller(Context context, Interpolator interpolator, int duration) {
        super(context, interpolator);
        mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

}

package coderoom1401.lawrence.imagebrowser;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

/**
 * 图片浏览器
 * <p/>
 * 包含两个部分:
 * 轮播viewpager 显示轮播页码的PointView
 *
 * @author wjy
 */
public class ImageBrowser extends RelativeLayout implements
        OnPageChangeListener {
    public static final int DEFAULT_SCROLL_RATE = 3000;
    public static final int DEFAULT_SCROLL_SPEED = 1000;

    private MyViewPager mViewPager;
    private ViewGroup mViewGroup;
    private View[] mPoints;
    private int mIndex;
    private int mImageCount;
    private int mAutoScrollRate = 3000;
    private int mScrollSpeed;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(mIndex + 1);
            sendEmptyMessageDelayed(0, mAutoScrollRate);
        }
    };
    private boolean isSetAdapter;

    public ImageBrowser(Context context) {
        super(context);
        init();
    }

    public ImageBrowser(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageBrowser(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.view_image_browser, this);
        mViewPager = (MyViewPager) findViewById(R.id.pager);
        mViewGroup = (ViewGroup) findViewById(R.id.viewGroup);
    }

    public void setPagerAdapter(PagerAdapter pagerAdapter, int imageCount, @LayoutRes int pointViewId) {
        if (pagerAdapter == null || imageCount <= 0) {
            throw new IllegalArgumentException("apdater不可为null && imageCount必须大于0");
        }
        this.mImageCount = imageCount;
        mViewGroup.removeAllViews();
        mPoints = new View[mImageCount];
        View pointView;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(getContext(), 10), DensityUtil.dip2px(getContext(), 10));
        params.setMargins(DensityUtil.dip2px(getContext(), 9), 0, 0, 0);

        for (int i = 0; i < mImageCount; i++) {
            pointView = View.inflate(getContext(), pointViewId, null);
            // 默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
            if (i == mIndex) {
                pointView.setSelected(true);
            } else {
                pointView.setSelected(false);
            }
            // 将小圆点layout添加到数组中
            mPoints[i] = pointView;
            // 将imageviews添加到小圆点视图组
            mViewGroup.addView(pointView, params);
        }
        mViewPager.setAdapter(pagerAdapter);
        isSetAdapter = true;
        mViewPager.addOnPageChangeListener(this);
        if (mIndex > 0) {
            mViewPager.setCurrentItem(mIndex);
        }
    }

    public void setPagerAdapter(PagerAdapter pagerAdapter, int imageCount) {
        setPagerAdapter(pagerAdapter, imageCount, R.layout.default_point_view);
    }


    public void startAutoScroll(int autoScrollRate, int speed) {
        if (isSetAdapter) {
            this.mAutoScrollRate = autoScrollRate;
            this.mScrollSpeed = speed;
            setScrollSpeed();
            handler.sendEmptyMessageDelayed(0, mAutoScrollRate);
        }
    }

    public void stopAutoScroll() {
        if (isSetAdapter) {
            handler.removeMessages(0);
        }
    }

    private void setScrollSpeed() {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            MyScroller scroller = new MyScroller(mViewPager.getContext(),
                    new AccelerateDecelerateInterpolator(), mScrollSpeed);
            field.set(mViewPager, scroller);
        } catch (Exception e) {
        }
    }

    /**
     * 轮播图滚动到指定页码
     *
     * @param pagerIndex
     */
    public void setPagerIndex(int pagerIndex) {
        this.mIndex = pagerIndex;
        mViewPager.setCurrentItem(mIndex);
    }

    /**
     * 获取轮播图的当前页码
     *
     * @return
     */
    public int getPagerIndex() {
        return mIndex;
    }


    public void onPageScrollStateChanged(int arg0) {

    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    public void onPageSelected(int arg0) {
        mIndex = arg0;
        for (int i = 0; i < mImageCount; i++) {
            if (i == arg0 % mImageCount) {
                mPoints[i].setSelected(true);
            } else {
                mPoints[i].setSelected(false);
            }
        }
    }

}

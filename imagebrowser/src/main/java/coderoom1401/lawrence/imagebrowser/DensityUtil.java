package coderoom1401.lawrence.imagebrowser;

import android.content.Context;

public class DensityUtil {
    /**
     * dip转px
     *
     * @param dpValue dip
     * @return px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}

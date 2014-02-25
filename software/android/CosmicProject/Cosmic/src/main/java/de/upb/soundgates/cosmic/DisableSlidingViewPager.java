package de.upb.soundgates.cosmic;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by posewsky on 18.11.13.
 */
public class DisableSlidingViewPager extends ViewPager {

    public DisableSlidingViewPager(Context context) {
        super(context);
    }

    public DisableSlidingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v.getId() == R.id.interaction_main) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
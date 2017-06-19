package com.xtdar.app.listener;

import android.support.v4.view.ViewPager;

/**
 * 将OnPageChangeListener转换成容易使用的监听器
 * change OnPageChangeListener to easy-to-read data
 */
public class OnPageScrolledListener implements ViewPager.OnPageChangeListener {
    private ViewPager pager;
    private int curItem;

    public void setViewPager(ViewPager viewPager) {
        this.pager = viewPager;
        viewPager.addOnPageChangeListener(this);
    }

    public ViewPager getViewPager() {
        return pager;
    }

    @Override
    public final void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int nextItem;
        if (position < curItem) {
            positionOffset = 1f - positionOffset;
            nextItem = curItem - 1;
        } else if (position > curItem) {
            positionOffset = 1f + positionOffset;
            nextItem = curItem + 1;
        } else {
            nextItem = curItem + 1;
        }
        if (nextItem >= 0 && nextItem < pager.getAdapter().getCount()) {
            onPageScrolled(curItem, nextItem, positionOffset);
        }
    }

    /**
     * 回调参数
     * @param curItem the current item
     * @param nextItem the item will scroll to
     * @param offset the scroll rate (0~1)
     */
    public void onPageScrolled(int curItem, int nextItem, float offset) {
//        String out = String.format("curItem = %d, nextItem = %d, offset = %f",
//                curItem, nextItem, offset);
//        Log.d("HELP", out);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                curItem = -1;
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                curItem = pager.getCurrentItem();
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                break;
        }
    }

}
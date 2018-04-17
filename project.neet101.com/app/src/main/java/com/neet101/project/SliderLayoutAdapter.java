package com.neet101.project;

import android.support.v4.view.PagerAdapter;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SliderLayoutAdapter extends PagerAdapter {

    Context mContext;

    SliderLayoutAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return sliderImagesId.length;
    }

    private int[] sliderImagesId = new int[]{
            R.mipmap.artboard_1, R.mipmap.artboard_2, R.mipmap.artboard_3,
            R.mipmap.artboard_4, R.mipmap.artboard_5, R.mipmap.artboard_6,
            R.mipmap.artboard_7, R.mipmap.artboard_8,
    };

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((ImageView) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {
        ImageView mImageView = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setImageResource(sliderImagesId[i]);
        ((ViewPager) container).addView(mImageView, 0);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((ImageView) obj);
    }

}

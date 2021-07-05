package com.lwlocalhost.android.viewpagerdialog;

import android.app.Activity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 参考自 Github: rubensousa/ViewPagerCards项目
 * @author Linwei
 * Pager的适配器，单个内容为View
 */

public class CardPagerAdapter extends PagerAdapter implements ICardAdapter {

    private List<CardView> mViews;
    private List<String> mData;
    private float mBaseElevation;
    private Activity mActivity;
    private int[] cardItemRes;
    private OnPageClickListener pageClickListener;

    public CardPagerAdapter(Activity activity) {
        mActivity = activity;

        mData = new ArrayList<String>();
        mViews = new ArrayList<CardView>();
    }

    public void setDataSource(int[] cardImgRes) {
        cardItemRes = cardImgRes;
        for (int ignored : cardImgRes) {
            mViews.add(null);
        }
    }

    public void setPageClickListener(OnPageClickListener listener) {
        pageClickListener = listener;
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.view_dialog_body, container, false);
        container.addView(view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }
        final int pos = position;

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ToastUtil.showToast("点击了" + pos);
                if (pageClickListener != null) {
                    pageClickListener.onClickPage(pos);
                }
                mActivity.finish();
            }
        });
        ImageView image = (ImageView) cardView.findViewById(R.id.imv_card_content);
//        image.setImageResource(cardImgRes[pos % cardImgRes.length]);
        image.setImageResource(cardItemRes[position]);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    public interface OnPageClickListener {
        void onClickPage(int position);
    }

}

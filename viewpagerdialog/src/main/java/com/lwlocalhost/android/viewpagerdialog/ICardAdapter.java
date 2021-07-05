package com.lwlocalhost.android.viewpagerdialog;

/**
 * 参考自 Github: rubensousa/ViewPagerCards项目
 * 单个卡片内容接口
 */

import androidx.cardview.widget.CardView;

public interface ICardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}

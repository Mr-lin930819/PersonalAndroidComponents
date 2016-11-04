package com.lwlocalhost.android.viewpagerdialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

public class VPageDialog extends Activity {

    private final String TAG = getClass().getSimpleName();
    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpage_dialog);
        mViewPager = (ViewPager)findViewById(R.id.vp_dialog);
        findViewById(R.id.activity_advert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mCardAdapter = new CardPagerAdapter(this);
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);


        mCardAdapter.setDataSource(new int[] {R.drawable.sample1, R.drawable.sample2});
        mCardAdapter.setPageClickListener(new CardPagerAdapter.OnPageClickListener() {
            @Override
            public void onClickPage(int position) {
                Toast.makeText(VPageDialog.this, "点击了:" + position,
                        Toast.LENGTH_LONG).show();
            }
        });
        mViewPager.setAdapter(mCardAdapter);
        mCardShadowTransformer.enableScaling(true);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

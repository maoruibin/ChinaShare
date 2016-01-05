package com.chow.module.share.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chow.module.share.R;
import com.chow.module.share.ShareManager;
import com.chow.module.share.info.IShareInfo;
import com.chow.module.share.templet.AbsWarpTemplateShare;
import com.chow.module.share.way.BaseShareWay;

import java.util.List;

/**
 * 屏幕底部弹出菜单中是一个个可以点击的图片gird item
 * Created by GuDong on 2014/11/24.
 */
public class ShareView implements View.OnClickListener, View.OnTouchListener {
    /**
     * 设置要分享的内容，不同分享内容 自己实现分享内容的格式
     *
     * @param activity
     * @param shareInfo 具体分享的内容
     */
    BaseShareWay share;
    private View shareView;
    private View fullMaskView;
    private ViewStub mViewStub;
    private LinearLayout llContent;
    private GridView mGirdView;
    private TextView mTvClose;
    private int mShareItemTextColor = R.color.black;
    private int mHeight = 0;
    private long duration;
    private boolean isShowing = false;
    private Context mContext;

    public ShareView(Activity context) {
        this.mContext = context;
        setUpView(context);
        duration = 300;
    }

    public ShareView(Activity context, String title) {
        this(context);
        setTitle(title);
    }

    private void setUpView(Activity context) {
        shareView = View.inflate(context, R.layout.layout_share_view, null);
        fullMaskView = shareView.findViewById(R.id.full_mask);
        mViewStub = (ViewStub) shareView.findViewById(R.id.view_stub);
        mViewStub.inflate();
        llContent = (LinearLayout) shareView.findViewById(R.id.ll_content);
        mGirdView = (GridView) shareView.findViewById(R.id.gv_share);
        mTvClose = (TextView) shareView.findViewById(R.id.tv_close);
        // add shareView to activity's root view
        ((ViewGroup) context.getWindow().getDecorView()).addView(shareView);
        initListener();
    }

    private void initListener() {
        shareView.setOnTouchListener(this);
        fullMaskView.setOnTouchListener(this);
        llContent.setOnTouchListener(this);
        shareView.findViewById(R.id.tv_close).setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.full_mask) {
            if (isShowing && event.getAction() == KeyEvent.ACTION_DOWN) {
                hide();
            }
        }
        return true;
    }

    public void setTitle(String title) {
        TextView tvTitle = (TextView) shareView.findViewById(R.id.tv_share_title);
        tvTitle.setText(title);
        tvTitle.setVisibility(!TextUtils.isEmpty(title) ? View.VISIBLE : View.GONE);
    }

    public void setTitleColor(int titleColor) {
        TextView tvTitle = (TextView) shareView.findViewById(R.id.tv_share_title);
        tvTitle.setTextColor(tvTitle.getResources().getColor(titleColor));
    }

    public void show() {
        isShowing = true;
        shareView.setVisibility(View.VISIBLE);
        shareViewAnimator(mHeight, 0, 0.0f, 0.5f);
    }

    public void hide() {
        isShowing = false;
        shareViewAnimator(0, mHeight, 0.5f, 0.0f);
    }

    private void shareViewAnimator(final float start, final float end, final float startAlpha, final float endAlpha) {
        shareViewTranslationAnimator(start, end);
        shareViewAlphaAnimator(startAlpha, endAlpha);
        //shareViewRotationAnimator(0f,30f);
    }

    private void shareViewTranslationAnimator(final float start, final float end) {
        if (Build.VERSION.SDK_INT >= 11) {
            ObjectAnimator bottomAnim = ObjectAnimator.ofFloat(llContent, "translationY", start, end);
            bottomAnim.setDuration(duration);
            bottomAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (start < end) {//close ShareView
                        shareView.setVisibility(View.GONE);
                    } else {//show ShareView
                    }
                }
            });
            bottomAnim.start();
        }
    }

    private void shareViewAlphaAnimator(final float startAlpha, final float endAlpha) {
        if (Build.VERSION.SDK_INT >= 11) {
            ObjectAnimator bottomAnimAlpha = ObjectAnimator.ofFloat(fullMaskView, "alpha", startAlpha, endAlpha);
            bottomAnimAlpha.setDuration(duration);
            bottomAnimAlpha.start();
        }
    }

    public void setAnimatorDuration(long duration) {
        this.duration = duration;
    }

    private void shareViewRotationAnimator(final float startAngle, final float endAngle) {
        if (Build.VERSION.SDK_INT >= 11) {
            ObjectAnimator bottomAnimAlpha = ObjectAnimator.ofFloat(mTvClose, "rotation", startAngle, endAngle);
            bottomAnimAlpha.setDuration(duration);
            bottomAnimAlpha.start();
        }
    }

    public void setShareInfo(final Activity activity, AbsWarpTemplateShare shareInfo) {
        final List<IShareInfo> listInfo = shareInfo.getListInfo();
        final List<BaseShareWay> list = ShareManager.getShareWay(activity);
        mGirdView.setAdapter(new ShareAdapter(activity, list));
        mGirdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                share = list.get(position);
                share.onShare(listInfo.get(position));
            }
        });
        initHeight();

    }

    private void initHeight() {
        mHeight = getViewHeight(llContent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_close) {
            hide();
        }
    }

    /**
     * 设置分享的背景图片
     *
     * @param shareBackground
     */
    public void setShareBackground(int shareBackground) {
        shareView.setBackgroundResource(shareBackground);
    }

    public void setShareItemTextColor(int shareItemTextColor) {
        this.mShareItemTextColor = shareItemTextColor;
    }

    public void setOnActivityResult(int requestCode, int resultCode, Intent data) {
        share.onActivityResult(requestCode, resultCode, data);
    }

    private int getViewHeight(View mView) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        mView.measure(w, h);
        return mView.getMeasuredHeight();
    }

    /**
     * 点击透明背景对应的监听事件处理 默认点击背景会隐藏整个dialog
     */
    private class OnClickCloseIcon implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            hide();
        }
    }

    class ShareAdapter extends BaseAdapter {
        private List<BaseShareWay> mData;
        private Context mContext;
        private LayoutInflater mInflater;

        public ShareAdapter(Context context, List<BaseShareWay> data) {
            mContext = context;
            mData = data;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tView = (TextView) mInflater.inflate(R.layout.share_item, null);
            BaseShareWay info = (BaseShareWay) getItem(position);
            String label = info.getTitle();
            Drawable icon = mContext.getResources().getDrawable(info.getResIcon());
            tView.setText(label);
            tView.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
            tView.setTextColor(mContext.getResources().getColor(mShareItemTextColor));
            return tView;
        }
    }
}

package com.chow.module.share.way;

import android.app.Activity;
import android.content.Intent;

import com.chow.module.share.IShareWay;

/**
 * 分享方式的基类
 * Created by GuDong on 8/19/15.
 */
public abstract class BaseShareWay implements IShareWay {
    /**分享对应的图标资源id 这里不支持selector**/
    private int resIcon;
    /**分享方式对应的名称**/
    private String mTitle;
    protected Activity mActivity;

    public BaseShareWay(Activity context) {
        mActivity = context;
    }

    public BaseShareWay(Activity context, int resIcon,String mTitle) {
        this(context);
        this.setResIcon(resIcon);
        this.setTitle(mTitle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}

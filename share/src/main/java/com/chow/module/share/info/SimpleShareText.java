package com.chow.module.share.info;

import android.content.Context;

/**
 * 一些情况可能只需要分享一个标题一句话 而不需要其他信息
 * Created by GuDong on 8/20/15.
 */
public class SimpleShareText implements IShareInfo {
    private String mTitle;
    private String mContent;
    private String mShareUrl;
    private String mImgUrl;

    public SimpleShareText(String title,String content, String shareUrl) {
        mContent = content;
        mShareUrl = shareUrl;
        mTitle = title;
    }

    public SimpleShareText(String title,String content, String shareUrl,String imgUrl) {
        this(title,content, shareUrl);
        this.mImgUrl = imgUrl;
    }

    @Override
    public String getShareTitle() {
        return mTitle;
    }

    @Override
    public String getShareContent() {
        return mContent;
    }

    @Override
    public String getShareImgUrl() {
        return mImgUrl;
    }

    @Override
    public String getShareUrl() {
        return mShareUrl;
    }
}

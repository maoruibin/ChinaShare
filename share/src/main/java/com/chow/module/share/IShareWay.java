package com.chow.module.share;

import android.content.Intent;

import com.chow.module.share.info.IShareInfo;

/**
 * 分享方式对应的接口
 * Created by GuDong on 2014/10/9.
 */
public interface IShareWay {
    /**
     * 分享对应的具体操作
     * @param shareInfo
     */
    public void onShare(IShareInfo shareInfo);

    /**
     * QQ分享和微博分享会回调至Activity，需要在onActivityResult做一些操作
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data);
}

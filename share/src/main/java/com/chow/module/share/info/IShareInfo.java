package com.chow.module.share.info;

import android.content.Context;

/**
 * 最终分享的信息对应的实现接口
 */
public interface IShareInfo {
    /**
     * 分享标题
     * @return
     */
    String getShareTitle();

    /**
     * 分享内容
     * @return
     */
    String getShareContent();

    /**
     * 分享对应的图片URL
     * @return
     */
    String getShareImgUrl();

    /**
     * 分享对应的URL
     * @return
     */
    String getShareUrl();
}
package com.chow.module.share.templet;

import com.chow.module.share.info.IShareInfo;

/**
 * 要Share信息的包装模板。
 * 任何要分享的信息都要通过实现这个模板接口，才可以把信息分享出去
 * Created by GuDong on 8/20/15.
 */
public interface IWarpTemplateShare {

    /**
     * 分享到微信或者朋友圈对应的信息
     * @return
     */
     IShareInfo warpWechatInfo();
    
    /**
     * 分享到微博对应的信息
     * @return
     */
     IShareInfo warpSinaInfo();

    /**
     * 分享到QQ或者QQ空间对应的信息
     * @return
     */
     IShareInfo warpQQInfo();

    /**
     * 分享到短信对应的信息
     * @return
     */
     IShareInfo warpMessageInfo();
}

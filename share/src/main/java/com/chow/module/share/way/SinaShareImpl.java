package com.chow.module.share.way;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.chow.module.share.R;
import com.chow.module.share.ShareManager;
import com.chow.module.share.info.IShareInfo;
import com.chow.module.share.way.helper.AccessTokenKeeper;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * Created by GuDong on 8/20/15.
 */
public class SinaShareImpl extends BaseShareWay {
    //授权
    private Oauth2AccessToken mAccessToken;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;


    //分享
    /**
     * 微博微博分享接口实例
     **/
    private IWeiboShareAPI mWeiboShareAPI = null;
    /**
     * 微博分享的接口实例
     **/
    private IWeiboShareAPI mSinaAPI;

    public SinaShareImpl(Activity activity, int resIcon, String title) {
        super(activity, resIcon, title);
        mAuthInfo = new AuthInfo(mActivity, ShareManager.getWeiboAppId(),
                ShareManager.getSinaRedirectUrl(), ShareManager.getScope());
        mSsoHandler = new SsoHandler(mActivity, mAuthInfo);

        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mActivity, ShareManager.getWeiboAppId());
        mWeiboShareAPI.registerApp();

        mAccessToken = AccessTokenKeeper.readAccessToken(mActivity);
    }

    @Override
    public void onShare(IShareInfo shareInfo) {
        if (mAccessToken.isSessionValid()) {
            sendWeiBoMessage(shareInfo);
        } else {
            authorizeSinaWeibo(new AuthDialogListener(shareInfo));
        }
    }

    /**
     * 获取新浪微博授权
     */
    private void authorizeSinaWeibo(WeiboAuthListener listener) {
        // mSsoHandler = new SsoHandler(mActivity, mAuthInfo);
        // SSO 授权, ALL IN ONE   如果手机安装了微博客户端则使用客户端授权,没有则进行网页授权
        mSsoHandler.authorize(listener);
    }

    private void sendWeiBoMessage(IShareInfo shareInfo) {
        boolean hasImage = !TextUtils.isEmpty(shareInfo.getShareImgUrl());
        sendSingleMessage(true, hasImage, shareInfo.getShareContent(), shareInfo.getShareImgUrl());
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 当{@link IWeiboShareAPI#getWeiboAppSupportAPI()} < 10351 时，只支持分享单条消息，即
     * 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
     *
     * @param hasText  分享的内容是否有文本
     * @param hasImage 分享的内容是否有图片
     */
    private void sendSingleMessage(boolean hasText, boolean hasImage, String content, String imageUrl) {
        // 1. 初始化微博的分享消息
        // 用户可以分享文本、图片、网页、音乐、视频中的一种
        WeiboMessage weiboMessage = new WeiboMessage();
        if (hasText) {
            weiboMessage.mediaObject = getTextObj(content);
        }
        if (hasImage) {
            weiboMessage.mediaObject = getImageObj(imageUrl);
        }
        // 2. 初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(mActivity, request);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String text) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj(String imageUrl) {
        ImageObject imageObject = new ImageObject();
        Bitmap bmp = BitmapFactory.decodeFile(imageUrl);
        imageObject.setImageObject(bmp);
        return imageObject;
    }

    private class AuthDialogListener implements WeiboAuthListener {
        IShareInfo shareInfo;

        public AuthDialogListener(IShareInfo shareInfo) {
            this.shareInfo = shareInfo;
        }

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(mActivity, mAccessToken);
                sendWeiBoMessage(shareInfo);
                Toast.makeText(mActivity, R.string.suc_auth_sina, Toast.LENGTH_SHORT).show();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = "授权失败";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onWeiboException(WeiboException e) {
            //Toast.makeText(mActivity, R.string.fail_auth_sina, Toast.LENGTH_SHORT).show();
            Toast.makeText(mActivity,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

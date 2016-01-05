package com.chow.module.share;

import android.util.Log;
import com.chow.module.share.way.SHARE_MEDIA;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class Setting {


    private String mWechatAppId = "";

    private String mWeiboAppId = "";

    private String mQQAppId = "";

    /**
     * 微博回调地址
     **/
    private String mSinaRedirectUrl = "";
    private String mScope = "";

    /**
     * 说明：
     * 设置默认分享图片有两种方式 一种是一个字符串格式的远程url,一种是本地资源id
     * 其实按道理说 用本地资源id就可以了 我一开始也是这么做的 但是发现QQ分享比较特殊 传递本地资源id后
     * 不能正常显示出图片，所以现在用了两种方式
     */

    //没有图片分享的情况下 可设置默认的图片url
    private String mDefShareImageUrl = "";

    /**
     * 默认分享图片的资源id
     **/
    private int mDefShareImageUrlId = R.drawable.ic_launcher;

    /**
     * app名称
     **/
    private String mAppName = "";
    /**
     * 分享的类型
     */
    private LinkedHashSet<SHARE_MEDIA> shareMedias;
    /**
     * 分享对应的图标map
     */
    private LinkedHashMap<SHARE_MEDIA, Integer> mShareWayIconMap = new LinkedHashMap<>();

    private static Setting setting = new Setting();

    private Setting() {
        shareMedias = new LinkedHashSet<>();
    }

    public synchronized static Setting getInstance() {
        return setting;
    }

    /**
     * 设置一个存储在网络的icon路径 这里是为了解决QQ分享的一个bug
     * 当使用QQ分享时，需要传递一个分享图标，就是那个分享提示框上面显示的那个icon,后来发现传递本地icon资源id不起作用
     * 后来就使用了这种办法
     *
     * @param url
     * @return
     */
    public Setting setDefShareImageUrl(String url) {
        mDefShareImageUrl = url;
        return setting;
    }

    public Setting setQQAppId(String QQAppId) {
        mQQAppId = QQAppId;
        return setting;
    }

    public Setting setWechatAppId(String wechatAppId) {
        mWechatAppId = wechatAppId;
        return setting;
    }

    public Setting setWeiboAppId(String weiboAppId) {
        mWeiboAppId = weiboAppId;
        return setting;
    }

    public Setting setAppName(String appName) {
        mAppName = appName;
        return setting;
    }

    /**
     * 微信 QQ 分享需要显示App Icon
     *
     * @param defShareImageUrlId
     * @return
     */
    public Setting setDefImageUrlId(int defShareImageUrlId) {
        mDefShareImageUrlId = defShareImageUrlId;
        return setting;
    }

    public Setting setSinaRedirectUrl(String redirectUrl) {
        mSinaRedirectUrl = redirectUrl;
        return setting;
    }

    public Setting setScope(String scope) {
        mScope = scope;
        return setting;
    }

    public Setting setShareWayIconMap(LinkedHashMap<SHARE_MEDIA, Integer> shareWayMap) {
        mShareWayIconMap = shareWayMap;
        return setting;
    }

    public String getQQAppId() {
        return mQQAppId;
    }

    public String getWechatAppId() {
        return mWechatAppId;
    }

    public String getWeiboAppId() {
        return mWeiboAppId;
    }

    public String getAppName() {
        return mAppName;
    }

    public String getDefShareImageUrl() {
        return mDefShareImageUrl;
    }

    public int getDefShareImageUrlId() {
        return mDefShareImageUrlId;
    }

    public String getSinaRedirectUrl() {
        return mSinaRedirectUrl;
    }

    public String getScope() {
        return mScope;
    }

    public LinkedHashMap<SHARE_MEDIA, Integer> getShareWayIconMap() {
        return mShareWayIconMap;
    }

    public Setting addShareMedia(SHARE_MEDIA... shareMedia) {
        for (SHARE_MEDIA item : shareMedia) {
            shareMedias.add(item);
        }
        return setting;
    }

    public Setting removeShareMedia(SHARE_MEDIA... shareMedia) {
        for (SHARE_MEDIA item : shareMedia) {
            shareMedias.remove(item);
        }
        return setting;
    }

    public Set<SHARE_MEDIA> getShareMedia() {
        return shareMedias;
    }
}

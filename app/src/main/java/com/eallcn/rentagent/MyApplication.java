package com.eallcn.rentagent;

import android.app.Activity;
import android.app.Application;

import com.chow.module.share.ShareManager;
import com.chow.module.share.way.SHARE_MEDIA;

import java.util.LinkedHashMap;

/**
 * Created by xy on 2015/12/24.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LinkedHashMap<SHARE_MEDIA, Integer> iconMap = new LinkedHashMap<>();
        iconMap.put(SHARE_MEDIA.WEICHAT, R.drawable.share_weixin);
        iconMap.put(SHARE_MEDIA.WEICHATCIRCLE, R.drawable.share_momment);
        iconMap.put(SHARE_MEDIA.SINA, R.drawable.share_sina);
        iconMap.put(SHARE_MEDIA.QQ, R.drawable.share_qq);
        iconMap.put(SHARE_MEDIA.QQZONE, R.drawable.share_qzeon);
        iconMap.put(SHARE_MEDIA.MESSAGE, R.drawable.share_message);
        ShareManager.init()
                //应用的名字
                .setAppName("test")
                .setDefShareImageUrl("http://b.hiphotos.baidu.com/image/h%3D200/sign=9a3972dc65d9f2d33f1123ef99ed8a53/3b87e950352ac65cf1f52b4efcf2b21193138a1f.jpg")
                        //.addShareMedia(SHARE_MEDIA.WEICHAT, SHARE_MEDIA.WEICHATCIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QQZONE, SHARE_MEDIA.MESSAGE)
                .addShareMedia(SHARE_MEDIA.MESSAGE, SHARE_MEDIA.QQZONE, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA, SHARE_MEDIA.WEICHATCIRCLE, SHARE_MEDIA.WEICHAT)
                .setShareWayIconMap(iconMap)
                .setQQAppId("1104680275")
                .setWeiboAppId("1962535544")
                .setWechatAppId("wxdc3ad22802d2f283")
                .setSinaRedirectUrl("https://api.weibo.com/oauth2/default.html")
                .setScope("email,direct_messages_read,direct_messages_write,\"\n" +
                        "            + \"friendships_groups_read,friendships_groups_write,statuses_to_me_read,\"\n" +
                        "            + \"follow_app_official_microblog,\" + \"invitation_write")
                .setDefImageUrlId(R.drawable.ic_launcher);
    }
}

package com.chow.module.share;

import android.app.Activity;

import com.chow.module.share.way.BaseShareWay;
import com.chow.module.share.way.MessageShareImpl;
import com.chow.module.share.way.QQShareImpl;
import com.chow.module.share.way.SHARE_MEDIA;
import com.chow.module.share.way.SinaShareImpl;
import com.chow.module.share.way.WeichatShareImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by GuDong on 8/18/15.
 */
public class ShareManager {

    private static Setting setting;

    static {
        setting = Setting.getInstance();
    }

    public static Setting init() {
        return setting;
    }


    public static List<BaseShareWay> getShareWay(Activity activity) {
        List<BaseShareWay> list = new ArrayList<>();
        //如果不设置分享的icon 则使用默认样式的分享图标
        if (getShareWayIconMap().isEmpty()) {
            for (SHARE_MEDIA item : Setting.getInstance().getShareMedia()) {
                switch (item) {
                    case WEICHAT:
                        list.add(new WeichatShareImpl(activity, R.drawable.share_weixin, activity.getString(R.string.title_share_weixin), WeichatShareImpl.SHARE_WEICHAT));
                        break;
                    case WEICHATCIRCLE:
                        list.add(new WeichatShareImpl(activity, R.drawable.share_momment, activity.getString(R.string.title_share_momment), WeichatShareImpl.SHARE_MOMMENT));
                        break;
                    case SINA:
                        list.add(new SinaShareImpl(activity, R.drawable.share_sina, activity.getString(R.string.title_share_sina)));
                        break;
                    case QQ:
                        list.add(new QQShareImpl(activity, R.drawable.share_qq, activity.getString(R.string.title_share_qq), QQShareImpl.SHARE_QQ));
                        break;
                    case QQZONE:
                        list.add(new QQShareImpl(activity, R.drawable.share_qzeon, activity.getString(R.string.title_share_qzeon), QQShareImpl.SHARE_Qzeon));
                        break;
                    case MESSAGE:
                        list.add(new MessageShareImpl(activity, R.drawable.share_message, activity.getString(R.string.title_share_message)));
                        break;
                    default:
                        break;
                }
            }
        } else {//自定义了图标
            LinkedHashMap<SHARE_MEDIA, Integer> iconMap = getShareWayIconMap();
            for (SHARE_MEDIA item : Setting.getInstance().getShareMedia()) {
                switch (item) {
                    case WEICHAT:
                        list.add(new WeichatShareImpl(activity, iconMap.get(SHARE_MEDIA.WEICHAT), activity.getString(R.string.title_share_weixin), WeichatShareImpl.SHARE_WEICHAT));
                        break;
                    case WEICHATCIRCLE:
                        list.add(new WeichatShareImpl(activity, iconMap.get(SHARE_MEDIA.WEICHATCIRCLE), activity.getString(R.string.title_share_momment), WeichatShareImpl.SHARE_MOMMENT));
                        break;
                    case SINA:
                        list.add(new SinaShareImpl(activity, iconMap.get(SHARE_MEDIA.SINA), activity.getString(R.string.title_share_sina)));
                        break;
                    case QQ:
                        list.add(new QQShareImpl(activity, iconMap.get(SHARE_MEDIA.QQ), activity.getString(R.string.title_share_qq), QQShareImpl.SHARE_QQ));
                        break;
                    case QQZONE:
                        list.add(new QQShareImpl(activity, iconMap.get(SHARE_MEDIA.QQZONE), activity.getString(R.string.title_share_qzeon), QQShareImpl.SHARE_Qzeon));
                        break;
                    case MESSAGE:
                        list.add(new MessageShareImpl(activity, iconMap.get(SHARE_MEDIA.MESSAGE), activity.getString(R.string.title_share_message)));
                        break;
                    default:
                        break;
                }
            }
        }

        return list;
    }

    public static String getQQAppId() {
        return setting.getQQAppId();
    }

    public static String getWechatAppId() {
        return setting.getWechatAppId();
    }

    public static String getWeiboAppId() {
        return setting.getWeiboAppId();
    }

    public static String getAppName() {
        return setting.getAppName();
    }

    public static String getDefShareImageUrl() {
        return setting.getDefShareImageUrl();
    }

    public static int getDefShareImageUrlId() {
        return setting.getDefShareImageUrlId();
    }

    public static String getSinaRedirectUrl() {
        return setting.getSinaRedirectUrl();
    }

    public static String getScope() {
        return setting.getScope();
    }

    public static LinkedHashMap<SHARE_MEDIA, Integer> getShareWayIconMap() {
        return setting.getShareWayIconMap();
    }
}

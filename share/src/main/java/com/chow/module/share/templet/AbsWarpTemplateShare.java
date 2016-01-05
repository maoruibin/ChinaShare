package com.chow.module.share.templet;

import android.content.Context;

import com.chow.module.share.Setting;
import com.chow.module.share.info.IShareInfo;
import com.chow.module.share.way.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

/**
 * 包装模板的抽象实现
 * Created by GuDong on 8/20/15.
 */
public abstract class AbsWarpTemplateShare<T> implements IWarpTemplateShare {
    protected Context mContext;
    /**
     * 包装的源信息实体
     */
    protected T mDataSource;
    protected List<IShareInfo> dataList;

    public AbsWarpTemplateShare() {
        dataList = new ArrayList<>();
    }


    public AbsWarpTemplateShare(Context context) {
        this.mContext = context;
        dataList = new ArrayList<>();

    }

    public AbsWarpTemplateShare(Context context, T dataSource) {
        this(context);
        this.mDataSource = dataSource;
    }

    /**
     * 不同分享内容的集合
     *
     * @return
     */
    public List<IShareInfo> getListInfo() {
        if (Setting.getInstance().getShareMedia().size() == 0) {
            //Setting.getInstance().addShareMedia(SHARE_MEDIA.WEICHAT, SHARE_MEDIA.WEICHATCIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QQZONE, SHARE_MEDIA.MESSAGE);
            return dataList;
        }
        for (SHARE_MEDIA item : Setting.getInstance().getShareMedia()) {
            switch (item) {
                case WEICHAT:
                    dataList.add(warpWechatInfo());
                    break;
                case WEICHATCIRCLE:
                    dataList.add(warpWechatInfo());
                    break;
                case SINA:
                    dataList.add(warpSinaInfo());
                    break;
                case QQ:
                    dataList.add(warpQQInfo());
                    break;
                case QQZONE:
                    dataList.add(warpQQInfo());
                    break;
                case MESSAGE:
                    dataList.add(warpMessageInfo());
                    break;
                default:
                    break;
            }
        }
        return dataList;
    }
}

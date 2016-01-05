package com.eallcn.rentagent;

import android.content.Context;

import com.chow.module.share.info.IShareInfo;
import com.chow.module.share.info.SimpleShareText;
import com.chow.module.share.templet.AbsWarpTemplateShare;

/**
 * Created by xy on 2015/12/24.
 */
public class TestImpl extends AbsWarpTemplateShare {
    public TestImpl(Context context) {
        super(context);
    }

    @Override
    public IShareInfo warpWechatInfo() {
        return new SimpleShareText("这是分享标题","这是分享内容","http://www.weibo.com","");
    }

    @Override
    public IShareInfo warpSinaInfo() {
        return new SimpleShareText("这是分享标题","这是分享内容","http://www.weibo.com","");
    }

    @Override
    public IShareInfo warpQQInfo() {
        return new SimpleShareText("这是分享标题","这是分享内容","http://www.weibo.com","");
    }

    @Override
    public IShareInfo warpMessageInfo() {
        return new SimpleShareText("这是分享标题","这是分享内容","http://www.weibo.com","");
    }
}

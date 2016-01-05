package com.eallcn.rentagent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.chow.module.share.Setting;
import com.chow.module.share.view.ShareView;
import com.chow.module.share.way.SHARE_MEDIA;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btShare;
    private ShareView mShareView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btShare = (Button) findViewById(R.id.bt_share);
        btShare.setOnClickListener(this);
        mShareView = new ShareView(this, "分享");

    }

    @Override
    public void onClick(View v) {
        Setting.getInstance().removeShareMedia(SHARE_MEDIA.WEICHATCIRCLE);
        mShareView.setShareInfo(this, new TestImpl(this));
        mShareView.show();
    }
}

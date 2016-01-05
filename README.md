#描述
每个应用程序中，都可能会有分享的需求，比如分享一个商品详情页或者一些活动到微博或者微信等社交平台。
这个 lib 将分享相关的功能模块化了，使用它你可以方便的实现自己项目的分享功能。 

##效果图
![效果图](/shot/Screenshot_2016-01-05-17-18-37_com.eallcn.rentage.png)

##使用方法

###1、将 share 模块拷贝至自己项目

说明一下，因为 share 中有较多的 三方 lib jar文件，再加上他们可能会更新，所以 share lib 不会发布到
中央仓库去，这里如果要使用，直接通过最粗暴的方式将 share 模块直接拷贝至自己项目目录，然后在setting 中加入
share 模块

    include ':app', ':share'
    
接着在项目 build 目录中加入如下依赖路径
    
    compile project(':share')


###2、在Application的onCreate方法中设置分享相关属性
    
     @Override
        public void onCreate() {
            super.onCreate();
            
            //配置所有的分享方式
            LinkedHashMap<SHARE_MEDIA,Integer>iconMap = new LinkedHashMap<>();
            iconMap.put(SHARE_MEDIA.WEICHAT,R.drawable.share_weixin);
            iconMap.put(SHARE_MEDIA.WEICHATCIRCLE,R.drawable.share_momment);
            iconMap.put(SHARE_MEDIA.SINA,R.drawable.share_sina);
            iconMap.put(SHARE_MEDIA.QQ,R.drawable.share_qq);
            iconMap.put(SHARE_MEDIA.QQZONE,R.drawable.share_qzeon);
            iconMap.put(SHARE_MEDIA.MESSAGE, R.drawable.share_message);
            
            //初始化分享相关的key、appId 信息
            ShareManager.init()
                    //应用的名字
                    .setAppName("test")
                    .setDefShareImageUrl("默认分享的图片url")
                    .addShareMedia(SHARE_MEDIA.WEICHAT, SHARE_MEDIA.WEICHATCIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QQZONE, SHARE_MEDIA.MESSAGE)
                    //如果要自定义分享的图标，可以通过下面的方式进行设置，使用的默认分享图标，注释该方法即可
                    //.setShareWayIconMap(iconMap)
                    .setQQAppId("自己申请的appId")
                    .setWeiboAppId("自己申请的appId")
                    .setWechatAppId("自己申请的appId")
                    .setSinaRedirectUrl("https://api.weibo.com/oauth2/default.html")
                    .setScope("email,direct_messages_read,direct_messages_write,\"\n" +
                            "            + \"friendships_groups_read,friendships_groups_write,statuses_to_me_read,\"\n" +
                            "            + \"follow_app_official_microblog,\" + \"invitation_write")
                    .setDefImageUrlId(R.drawable.ic_launcher);
        }


share lib 默认已经提供了一个很常见的分享界面，如上面截图所示。
如果你的项目中的分享界面样式跟这个有出入（这简直是一定的），你可以直接修改layout文件 /layout/layout_share_grideview.xml

###3、使用ShareView

ShareView可以在任何界面中使用。

* 申明ShareView的一个实例

        ShareView mShareView;

* 实例化ShareView
最好在onCreate时实例化

        mShareView = new ShareView(this,"分享");
        //可以移除对应的分享方式
        Setting.getInstance().removeShareMedia(SHARE_MEDIA.SINA, SHARE_MEDIA.WEICHAT);
        mShareView.setShareInfo(this, new TestImpl(this));

* 显示ShareView

        mShareView.show();

具体的分享内容通过实现AbsWarpTemplateShare类的子类来进行设置。TestImpl就是一个例子，如下

    public class TestImpl extends AbsWarpTemplateShare {
        public DuoYongBaoShareImpl(Context context) {
            super(context);
        }

        @Override
        public IShareInfo warpWeichatInfo() {
            return new SimpleShareText("快来关注多佣宝",mContext.getString(R.string.share_duo_yongbao),"http://www.meiliwu.com","");
        }

        @Override
        public IShareInfo warpSinaInfo() {
            return new SimpleShareText("快来关注多佣宝",mContext.getString(R.string.share_duo_yongbao),"http://www.meiliwu.com","");
        }

        @Override
        public IShareInfo warpQQInfo() {
            return new SimpleShareText("快来关注多佣宝",mContext.getString(R.string.share_duo_yongbao),"http://www.meiliwu.com","");
        }

        @Override
        public IShareInfo warpMessageInfo() {
            return new SimpleShareText("快来关注多佣宝",mContext.getString(R.string.share_duo_yongbao),"http://www.meiliwu.com","");
        }
    }

###新浪微博分享
[新浪微博文档](https://github.com/sinaweibosdk/weibo_android_sdk)

1)新浪微博分享前准备工作
在进行新浪微博分享前，需要在AndroidManifest.xml中，在需要接收消息的Activity（唤起微博主程序的类）里声明对应的Action：ACTION_SDK_REQ_ACTIVITY，如下所示：
  
      <activity
          android:name="com.sina.weibo.sdk.demo.WBShareActivity"
          android:configChanges="keyboardHidden|orientation"
          android:screenOrientation="portrait" >
          <intent-filter>
                <action android:name="com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY" />
                <category android:name="android.intent.category.DEFAULT" />
      </intent-filter>
      </activity>
      
      
2)在进行新浪微博分享前，需要在分享Activity（唤起微博主程序的类）里重写onActivityResult方法，并调用mShareView.setOnActivityResult(requestCode, resultCode, data)方法，如下所示：
    
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            mShareView.setOnActivityResult(requestCode, resultCode, data);
        }
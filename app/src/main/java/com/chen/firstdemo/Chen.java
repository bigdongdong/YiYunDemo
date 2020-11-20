package com.chen.firstdemo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chen.firstdemo.greendao_demo.dao.DaoMaster;
import com.chen.firstdemo.greendao_demo.dao.DaoSession;


public class Chen extends Application {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    /**
     * 注意：每个进程都会创建自己的Application 然后调用onCreate() 方法，
     * 如果用户有自己的逻辑需要写在Application#onCreate()（还有Application的其他方法）中，一定要注意判断进程，
     * 不能把业务逻辑写在core进程，
     * 理论上，core进程的Application#onCreate()（还有Application的其他方法）只能做与im sdk 相关的工作
     */



    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("aaa", "onCreate: Application ");

        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
       // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
       // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
       // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        // 此处test-db表示数据库名称 可以任意填写
       mHelper = new DaoMaster.DevOpenHelper(this, "test-db", null);
       db = mHelper.getWritableDatabase();
       // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
       mDaoMaster = new DaoMaster(db);
       mDaoSession = mDaoMaster.newSession();

//        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
//        NIMClient.init(this, loginInfo(), options());

//        if (NIMUtil.isMainProcess(this)) {
//            // 注意：以下操作必须在主进程中进行
//            // 1、UI相关初始化操作
//            // 2、相关Service调用
//        }
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }

//    // 如果返回值为 null，则全部使用默认参数。
//    private SDKOptions options() {
//        SDKOptions options = new SDKOptions();
//
//        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
//        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
////        config.notificationEntrance = WelcomeActivity.class; // 点击通知栏跳转到该Activity
////        config.notificationSmallIconId = R.drawable.ic_stat_notify_msg;//通知栏的icon
//        // 呼吸灯配置
//        config.ledARGB = Color.GREEN;
//        config.ledOnMs = 1000;
//        config.ledOffMs = 1500;
//        // 通知铃声的uri字符串
//        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
//        options.statusBarNotificationConfig = config;
//
//        // 配置保存图片，文件，log 等数据的目录
//        // 如果 options 中没有设置这个值，SDK 会使用采用默认路径作为 SDK 的数据目录。
//        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
//        String sdkPath = getAppCacheDir(this) + "/nim"; // 可以不设置，那么将采用默认路径
//        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
//        options.sdkStorageRootPath = sdkPath;
//
//        // 配置是否需要预下载附件缩略图，默认为 true
//        options.preloadAttach = true;
//
//        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
//        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
//        options.thumbnailSize = ScreenUtil.getScreenWidth(this) / 2;
//
//        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
//        options.userInfoProvider = new UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String s) {
//                return null;
//            }
//
//            @Override
//            public String getDisplayNameForMessageNotifier(String s, String s1, SessionTypeEnum sessionTypeEnum) {
//                return null;
//            }
//
//            @Override
//            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionTypeEnum, String s) {
//                return null;
//            }
//        };
//        return options;
//    }
//
//    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
//    private LoginInfo loginInfo() {
//        return null;
//    }
//
//    /**
//     * 配置 APP 保存图片/语音/文件/log等数据的目录
//     * 这里示例用SD卡的应用扩展存储目录
//     */
//    static String getAppCacheDir(Context context) {
//        String storageRootPath = null;
//        try {
//            // SD卡应用扩展存储区(APP卸载后，该目录下被清除，用户也可以在设置界面中手动清除)，请根据APP对数据缓存的重要性及生命周期来决定是否采用此缓存目录.
//            // 该存储区在API 19以上不需要写权限，即可配置 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="18"/>
//            if (context.getExternalCacheDir() != null) {
//                storageRootPath = context.getExternalCacheDir().getCanonicalPath();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (TextUtils.isEmpty(storageRootPath)) {
//            // SD卡应用公共存储区(APP卸载后，该目录不会被清除，下载安装APP后，缓存数据依然可以被加载。SDK默认使用此目录)，该存储区域需要写权限!
//            storageRootPath = Environment.getExternalStorageDirectory() + "/" + context.getPackageName();
//        }
//
//        return storageRootPath;
//    }
}

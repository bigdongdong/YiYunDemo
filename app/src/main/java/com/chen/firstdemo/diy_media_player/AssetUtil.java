package com.chen.firstdemo.diy_media_player;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetUtil {
    private final static String TAG = "aaa";

    /**
     * 将asset中的文件copy到本地存储
     * @param context
     * @param assetPath
     * @return  本地存储的路径
     */
    public static Uri copyAssetFileToLocal(Context context , String assetPath){
        AssetManager am = context.getAssets();
        // getExternalCacheDir() : /storage/emulated/0/Android/data/com.xxx/cache
        String assetsDirPath = context.getExternalCacheDir().getPath()+"/assets";
        File assetsDirFile = new File(assetsDirPath);
        if(!assetsDirFile.exists()){
            assetsDirFile.mkdir();
        }
        File assetsFile = new File(assetsDirPath+"/"+assetPath);
        if(assetsFile.exists()){
            return Uri.fromFile(assetsFile);
        }

        //do copy
        InputStream is = null ;
        FileOutputStream fos = null ;
        try {
            assetsFile.createNewFile();
            is = am.open(assetPath);
            fos = new FileOutputStream(assetsFile);
            byte[] buffer = new byte[2048];
            int i = 0 ;
            while ((i = is.read(buffer)) > 0){
                fos.write(buffer,0,i);
            }
            fos.flush();
            fos.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Uri.fromFile(assetsFile);
    }

}

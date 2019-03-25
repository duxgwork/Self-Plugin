package com.github.kevin.pluginlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManager {
    private static final PluginManager instance = new PluginManager();
    private PluginApk mPluginApk;
    private Context mContext;

    private PluginManager() {

    }

    public static PluginManager getInstance() {
        return instance;
    }

    public void init(Context context) {
        mContext = context;
    }

    public PluginApk getPluginApk(){
        return mPluginApk;
    }

    //加载APK文件
    public void loadApk(String apkPath) {
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo == null) {
            return;
        }
        //创建ClassLoader
        DexClassLoader classLoader = createDexClassLoader(apkPath);
        //创建AssetManager
        AssetManager assetManager = createAssetManager(apkPath);
        //创建Resources
        Resources resources = createResources(assetManager);

        mPluginApk = new PluginApk(packageInfo, resources, classLoader);
    }


    private DexClassLoader createDexClassLoader(String apkPath) {
        File file = mContext.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, file.getAbsolutePath(), null, mContext.getClassLoader());
    }

    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(assetManager, apkPath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Resources createResources(AssetManager assetManager) {
        Resources resources = mContext.getResources();
        return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
    }

}

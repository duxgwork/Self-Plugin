package com.github.kevin.pluginlib;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

public class PluginApk {
    public PackageInfo packageInfo;
    public Resources resources;
    public AssetManager assetManager;
    public DexClassLoader dexClassLoader;

    public PluginApk(PackageInfo packageInfo, Resources resources, DexClassLoader dexClassLoader) {
        this.packageInfo = packageInfo;
        this.resources = resources;
        this.assetManager = resources.getAssets();
        this.dexClassLoader = dexClassLoader;
    }
}

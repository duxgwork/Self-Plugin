package com.github.kevin.pluginlib;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

//代理Activity,管理插件Activity的生命周期
public class ProxyActivity extends Activity {

    private String mClassName;
    private PluginApk mPluginApk;
    private IPlugin mIPlugin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getIntent().getStringExtra("className");
        mPluginApk = PluginManager.getInstance().getPluginApk();
        //加载插件APK
        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if (mPluginApk == null) {
            throw new RuntimeException("请先加载插件APK");
        }
        try {
            //这个clazz就是Activity的实例对象，但这个实例对象没有生命周期和上下文环境
            Class<?> clazz = mPluginApk.dexClassLoader.loadClass(mClassName);
            Object object = clazz.newInstance();
            if (object instanceof IPlugin) {
                mIPlugin = (IPlugin) object;
                mIPlugin.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM", IPlugin.FROM_EXTERNAL);
                mIPlugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return (mPluginApk != null) ? mPluginApk.resources : super.getResources();
    }

    @Override
    public ClassLoader getClassLoader() {
        return (mPluginApk != null) ? mPluginApk.dexClassLoader : super.getClassLoader();
    }
}

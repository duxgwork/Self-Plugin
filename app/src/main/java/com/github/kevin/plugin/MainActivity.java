package com.github.kevin.plugin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.kevin.pluginlib.PluginManager;
import com.github.kevin.pluginlib.ProxyActivity;

public class MainActivity extends Activity {
    private static final String APK_NAME = "plugin.apk";
    private static final String PLUGIN_ACTIVITY_NAME = "com.github.kevin.pluginapk.OtherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //传递上下文环境
        PluginManager.getInstance().init(this);
    }

    public void loadApk(View view) {
        String apkPath = Utils.copyAssetAndWrite(MainActivity.this, APK_NAME);
        //加载apk 初始化PluginApk
        PluginManager.getInstance().loadApk(apkPath);
    }

    public void jumpPage(View view) {
        Intent intent = new Intent(MainActivity.this, ProxyActivity.class);
        intent.putExtra("className", PLUGIN_ACTIVITY_NAME);
        startActivity(intent);
    }

}

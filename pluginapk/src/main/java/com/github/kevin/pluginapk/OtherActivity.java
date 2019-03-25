package com.github.kevin.pluginapk;

import android.os.Bundle;
import com.github.kevin.pluginlib.PluginActivity;

public class OtherActivity extends PluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
    }
}

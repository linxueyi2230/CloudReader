package com.example.jingbin.cloudreader.app;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

import com.ego.shadow.Shadow;
import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.MainActivity;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by jingbin on 2016/11/22.
 */

public class CloudReaderApplication extends MultiDexApplication{

    private static CloudReaderApplication cloudReaderApplication;

    public static CloudReaderApplication getInstance() {
        return cloudReaderApplication;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        cloudReaderApplication = this;
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
        LeakCanary.install(this);
        CrashReport.initCrashReport(getApplicationContext(), "3977b2d86f", DebugUtil.DEBUG);

        initTextSize();
        Shadow.splash = R.drawable.img_transition_default;
        Shadow.init("1808051020", MainActivity.class);
    }

    /**
     * 使其系统更改字体大小无效
     */
    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

}

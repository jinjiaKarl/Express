package cn.edu.njupt.iot.b16070520.express.application;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;
import cn.edu.njupt.iot.b16070520.express.utils.StaticClass;

public class BaseApplication extends Application {

    @Override
    public void onCreate(){

        super.onCreate();

        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);

        //第一：默认初始化Bomb
        Bmob.initialize(this,StaticClass.BMOB_APP_ID);

    }
}

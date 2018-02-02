package com.takahide.memoapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by Hide on 2018/02/01.
 */

public class MyApplication extends Application {

    private static Context context;
    public static Context getContext () { return context; }

    @Override
    public void onCreate () {
        super.onCreate ();
        context = this;
    }
}

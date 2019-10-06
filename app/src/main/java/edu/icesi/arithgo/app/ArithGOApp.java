package edu.icesi.arithgo.app;

import android.app.Application;
import android.content.Context;

public class ArithGOApp extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        ArithGOApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ArithGOApp.context;
    }
}

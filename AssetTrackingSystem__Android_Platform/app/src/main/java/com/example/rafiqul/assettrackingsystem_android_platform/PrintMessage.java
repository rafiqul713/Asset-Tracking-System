package com.example.rafiqul.assettrackingsystem_android_platform;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by rafiqul on 4/17/16.
 */
public class PrintMessage {
    static Context context;
    PrintMessage(Context context){
        this.context=context;
    }
    public static void ToastPrint(String str){
      Toast.makeText(context,str,Toast.LENGTH_LONG).show();
    }
}

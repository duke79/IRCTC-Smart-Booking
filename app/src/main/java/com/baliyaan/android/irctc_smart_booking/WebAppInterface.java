package com.baliyaan.android.irctc_smart_booking;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by Pulkit Singh on 5/26/2016.
 */
public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void putOnConsole(String str)
    {
        Log.d("js says: ",str);
    }

    @JavascriptInterface
    public void captchaposition(double top, double left,double height, double width)
    {
        MainActivity.captchatop=top;
        MainActivity.captchaleft=left;
        MainActivity.captchaheight=height;
        MainActivity.captchawidth=width;
    }
}
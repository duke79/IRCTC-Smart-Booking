package com.baliyaan.android.library.web;

import android.content.Context;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by Pulkit Singh on 5/26/2016.
 */
public class MyWebChromeClient extends WebChromeClient {
    Context mContext;
    MyCallbackInterface mCallbackInterface;

    public MyWebChromeClient(Context context,MyCallbackInterface callbackInterface){
        mContext = context;
        mCallbackInterface=callbackInterface;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        //Required functionality here
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public void onProgressChanged(WebView view, int progress) {

        if (progress == 1) {
            mCallbackInterface.Call("1");
        }

        if (progress == 100) {
            mCallbackInterface.Call("100");
        }
    }
}

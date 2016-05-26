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

    public MyWebChromeClient(Context context){
        mContext = context;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        //Required functionality here
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public void onProgressChanged(WebView view, int progress) {

        if (progress == 1) {
            Toast.makeText(mContext,"Page loading starts...",Toast.LENGTH_SHORT).show();
        }

        if (progress == 100) {
            Toast.makeText(mContext,"Page loaded",Toast.LENGTH_SHORT).show();
        }
    }
}

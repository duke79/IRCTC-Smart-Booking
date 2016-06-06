package com.baliyaan.android.library.web;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Pulkit Singh on 5/26/2016.
 */
public class MyWebViewClient extends WebViewClient{
    Context mContext;

    public MyWebViewClient(Context context){
        mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return false;
    }
}

package com.baliyaan.android.library.web;

import android.content.Context;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.baliyaan.android.irctc_smart_booking.WebAppInterface;

/**
 * Created by Pulkit Singh on 5/25/2016.
 */
public class MyWebView extends WebView {
    MyCallbackInterface mCallbackInterface;

    public MyWebView(Context context,String url,MyCallbackInterface callbackInterface) {
        super(context);

        mCallbackInterface = callbackInterface;
        // Enable javascript
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Bind javascript interface
        addJavascriptInterface(new WebAppInterface(context), "Android");

        // Set Layout
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);

        // Load URL
        loadUrl(url);

        // To force all links to open in the same webview
        MyWebViewClient webViewClient = new MyWebViewClient(context);
        setWebViewClient(webViewClient);

        // To show Java Script alerts in the webview
        MyWebChromeClient webChromeClient = new MyWebChromeClient(context,mCallbackInterface);
        setWebChromeClient(webChromeClient);
    }
}


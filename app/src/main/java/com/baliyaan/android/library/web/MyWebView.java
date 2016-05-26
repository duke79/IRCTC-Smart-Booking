package com.baliyaan.android.library.web;

import android.content.Context;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baliyaan.android.library.web.WebAppInterface;

/**
 * Created by Pulkit Singh on 5/25/2016.
 */
public class MyWebView extends WebView {

    public MyWebView(Context context,String url) {
        super(context);

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
        MyWebChromeClient webChromeClient = new MyWebChromeClient(context);
        setWebChromeClient(webChromeClient);
    }
}


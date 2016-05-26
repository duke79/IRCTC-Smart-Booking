package com.baliyaan.android.library.web;

import android.content.Context;
import android.webkit.JavascriptInterface;

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
        //Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}
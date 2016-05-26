package com.baliyaan.android.irctc_smart_booking;

import android.content.Context;
import android.webkit.WebView;
import android.widget.Toast;

import com.baliyaan.android.library.web.MyCallbackInterface;

/**
 * Created by Pulkit Singh on 5/26/2016.
 */
public class MyCallbackExtension extends MyCallbackInterface {

    MainActivity mContext;

    public MyCallbackExtension(MainActivity context)
    {
        mContext = context;
    }

    @Override
    public void Call(String msg) {
        //Toast.makeText(mContext,"Page Loaded.",Toast.LENGTH_SHORT).show();

        if(MainActivity.mWebView != null) {
            MainActivity.mWebView.loadUrl("javascript: $(\"input[name='j_username']\").val('subhash673');" +
                    "$(\"input[name='j_password']\").val('sjs430');");
        }


    }
}

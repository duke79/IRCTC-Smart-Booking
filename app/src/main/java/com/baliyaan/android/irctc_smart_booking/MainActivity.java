package com.baliyaan.android.irctc_smart_booking;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.baliyaan.android.library.web.MyWebView;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    static MyWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        //Toast.makeText(mContext,"Activity starts.",Toast.LENGTH_SHORT).show();
        //setContentView(R.layout.activity_main);
        String url = getResources().getString(R.string.irctc_url);
        MyCallbackExtension callbackExtension = new MyCallbackExtension(this);
        mWebView = new MyWebView(this,url,callbackExtension);
        setContentView(mWebView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

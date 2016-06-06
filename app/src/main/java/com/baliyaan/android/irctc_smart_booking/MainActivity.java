package com.baliyaan.android.irctc_smart_booking;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.baliyaan.android.library.web.MyWebView;


public class MainActivity extends AppCompatActivity {

    Context mContext;
    static MyWebView mWebView;
    static double captchatop;
    static double captchaleft;
    static double captchaheight=40;
    static double captchawidth=136;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        //Toast.makeText(mContext,"Activity starts.",Toast.LENGTH_SHORT).show();
        //setContentView(R.layout.activity_main);
        String url = getResources().getString(R.string.irctc_url);
        MyCallbackExtension callbackExtension = new MyCallbackExtension(this);
        mWebView = new MyWebView(this,url,callbackExtension);
        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
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

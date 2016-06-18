package com.baliyaan.android.irctc_smart_booking;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.baliyaan.android.library.web.MyWebView;


public class MainActivity extends AppCompatActivity {

    Context mContext;
    LoginFragment mLoginFragment;
    WebFragment mWebFragment;
    static double captchatop;
    static double captchaleft;
    static double captchaheight=40;
    static double captchawidth=136;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.activity_main);

        // Web Fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mWebFragment = new WebFragment();
        mWebFragment.setRetainInstance(true);
        fragmentTransaction.add(R.id.fragment_container, mWebFragment);
        //fragmentTransaction.hide(mWebFragment);
        fragmentTransaction.commit();
    }

    public void showWeb()
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(mLoginFragment);
        fragmentTransaction.show(mWebFragment);
        //fragmentTransaction.replace(R.id.fragment_container, mWebFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebFragment.mWebView.canGoBack()) {
                        mWebFragment.mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

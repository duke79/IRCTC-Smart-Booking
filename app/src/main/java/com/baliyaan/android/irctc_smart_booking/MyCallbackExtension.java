package com.baliyaan.android.irctc_smart_booking;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.baliyaan.android.library.web.MyCallbackInterface;

/**
 * Created by Pulkit Singh on 5/26/2016.
 */
public class MyCallbackExtension extends MyCallbackInterface {

    MainActivity mContext;

    public MyCallbackExtension(MainActivity context) {
        mContext = context;
    }

    @Override
    public void Call(String msg) {
        if (mContext != null) {
            if (mContext.mLoginFragment == null) {
                //make visible to program

                Activity activity = mContext;
                FragmentManager fragmentManager = activity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                mContext.mLoginFragment = new LoginFragment();
                fragmentTransaction.add(R.id.fragment_container, mContext.mLoginFragment);
                fragmentTransaction.hide(mContext.mWebFragment);
                fragmentTransaction.commit();
            }
        }
    }
}

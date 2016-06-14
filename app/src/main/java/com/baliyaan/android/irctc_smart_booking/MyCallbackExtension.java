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
    LoginFragment mLoginFragment;

    public MyCallbackExtension(MainActivity context) {
        mContext = context;
    }

    @Override
    public void Call(String msg) {
        if(mLoginFragment==null) {
            //make visible to program
            Activity activity = (Activity) mContext;
            activity.setContentView(R.layout.activity_main);
            FragmentManager fragmentManager = activity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mLoginFragment = new LoginFragment();
            fragmentTransaction.add(R.id.fragment_container, (Fragment) mLoginFragment);
            fragmentTransaction.commit();
        }
    }
}

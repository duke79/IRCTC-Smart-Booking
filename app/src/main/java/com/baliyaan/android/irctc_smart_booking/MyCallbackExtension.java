package com.baliyaan.android.irctc_smart_booking;

import android.content.Context;
import android.widget.Toast;

import com.baliyaan.android.library.web.MyCallbackInterface;

/**
 * Created by Pulkit Singh on 5/26/2016.
 */
public class MyCallbackExtension extends MyCallbackInterface {

    Context mContext;

    public MyCallbackExtension(Context context)
    {
        mContext = context;
    }

    @Override
    public void Call(String msg) {
        Toast.makeText(mContext,"Page Loaded.",Toast.LENGTH_SHORT).show();
    }
}

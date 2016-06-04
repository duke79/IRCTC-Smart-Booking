package com.baliyaan.android.irctc_smart_booking;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baliyaan.android.library.io.FileIO;
import com.baliyaan.android.library.web.MyCallbackInterface;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pulkit Singh on 5/26/2016.
 */
public class MyCallbackExtension extends MyCallbackInterface {

    MainActivity mContext;
    Bitmap captchaImage = null;
    private boolean mIsLoginFormInflated;

    public MyCallbackExtension(MainActivity context) {
        mContext = context;
    }

    @Override
    public void Call(String msg) {
        //Toast.makeText(mContext,"Page Loaded.",Toast.LENGTH_SHORT).show();

        // Fill user details
        js("$(\"input[name='j_username']\").val('subhash673');" +
                "$(\"input[name='j_password']\").val('sjs430');");

        // Get captcha from bitmap
        Bitmap captchaImage = getCaptchaImage();
        //displayBitmap(captchaImage);
        displayLoginForm(captchaImage);
        setCaptchaImage();
        Log.d("MyCallbackExtension: ","finish");

    }

    private void setCaptchaImage() {
        Activity activity = (Activity) mContext;
        //setting image resource
        ImageView imgcaptcha = (ImageView) activity.findViewById(R.id.captchaimage);
        if(null != imgcaptcha) {
            imgcaptcha.setImageBitmap(captchaImage);
        }
    }

    private void displayLoginForm(Bitmap captchaImage) {
        if(false == mIsLoginFormInflated) {
            //make visible to program
            Activity activity = (Activity) mContext;
            activity.setContentView(R.layout.login_form);
        }
        mIsLoginFormInflated = true;
    }

    public Bitmap getCaptchaImage() {
        if(captchaImage==null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    URL url = null;
                    try {
                        url = new URL("https://www.irctc.co.in/eticketing/captchaImage");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        captchaImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return captchaImage;
    }

    public void displayBitmap(Bitmap image) {
        //LinearLayOut Setup
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        //ImageView Setup
        ImageView imageView = new ImageView(mContext);

        //setting image resource
        //imageView.setImageResource(R.drawable.play);
        imageView.setImageBitmap(image);

        //setting image position
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        //adding view to layout
        linearLayout.addView(imageView);
        //make visible to program
        Activity activity = (Activity) mContext;
        activity.setContentView(linearLayout);
    }

    private void js(String string) {
        if (MainActivity.mWebView != null) {
            MainActivity.mWebView.loadUrl("javascript: " + string);
        }
    }
}

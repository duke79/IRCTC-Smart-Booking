package com.baliyaan.android.irctc_smart_booking;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baliyaan.android.library.io.FileIO;
import com.baliyaan.android.library.web.MyCallbackInterface;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Pulkit Singh on 5/26/2016.
 */
public class MyCallbackExtension extends MyCallbackInterface {

    MainActivity mContext;
    Bitmap captchaImage = null;
    private boolean mIsLoginFormInflated;
    String sessionCookie = "";
    String slbCookie = "";

    public MyCallbackExtension(MainActivity context) {
        mContext = context;
    }

    @Override
    public void Call(String msg) {
        //Toast.makeText(mContext,"Page Loaded.",Toast.LENGTH_SHORT).show();

        // Fill user details
        //js("$(\"input[name='j_username']\").val('subhash673');" +
        //        "$(\"input[name='j_password']\").val('sjs430');");

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
            final EditText userid = (EditText) activity.findViewById(R.id.userid);
            if(null != userid) {
                userid.setText("subhash673");
            }

            final EditText password = (EditText) activity.findViewById(R.id.password);
            if(null != password) {
                password.setText("sjs430");
            }

            final EditText captcha = (EditText) activity.findViewById(R.id.captcha);

            Button loginButton = (Button) activity.findViewById(R.id.loginbutton);
            if(null != loginButton)
            {
                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(null!=userid && null!=password && null!=captcha) {
                            String useridStr = userid.getText().toString();
                            String passwordStr = password.getText().toString();
                            String captchaStr = captcha.getText().toString();
                            Login(useridStr,passwordStr,captchaStr);
                        }
                    }
                });
            }
        }
        mIsLoginFormInflated = true;
    }

    private void Login(String useridStr, String passwordStr, String captchaStr) {
        // Fill user details
        js("$(\"input[name='j_username']\").val('"+ useridStr +"');" +
                "$(\"input[name='j_password']\").val('"+ passwordStr +"');"+
                "$(\"input[name='j_captcha']\").val('"+ captchaStr +"');");
        Activity activity = (Activity)mContext;
        if(null != activity && null != MainActivity.mWebView) {
            activity.setContentView(MainActivity.mWebView);
        }
    }

    public String getCookie(String siteName,String CookieName){
        String CookieValue = null;

        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(siteName);
        String[] temp=cookies.split(";");
        for (String ar1 : temp ){
            if(ar1.contains(CookieName)){
                String[] temp1=ar1.split("=");
                CookieValue = temp1[1];
            }
        }
        return CookieValue;
    }

    public Bitmap getCaptchaImage() {
        if(captchaImage==null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    URL url = null;
                    try {
                        sessionCookie = getCookie("https://www.irctc.co.in","JSESSIONID");
                        slbCookie = getCookie("https://www.irctc.co.in","SLB_Cookie");
                        Log.d("cookie found: ",sessionCookie);
                        Log.d("slb cookie found: ",slbCookie);
                        url = new URL("https://www.irctc.co.in/eticketing/captchaImage");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        URLConnection connection = url.openConnection();
                        connection.setRequestProperty("Cookie","JSESSIONID="+sessionCookie+" ; SLB_Cookie="+slbCookie);
                        captchaImage = BitmapFactory.decodeStream(connection.getInputStream());
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

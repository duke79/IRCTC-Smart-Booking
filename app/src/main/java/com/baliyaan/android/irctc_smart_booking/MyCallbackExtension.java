package com.baliyaan.android.irctc_smart_booking;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
        //Toast.makeText(mContext,"Page Loaded.",Toast.LENGTH_SHORT).show();

        if (MainActivity.mWebView != null) {
            MainActivity.mWebView.loadUrl("javascript: $(\"input[name='j_username']\").val('subhash673');" +
                    "$(\"input[name='j_password']\").val('sjs430');");

            // Get captcha
            float scale = MainActivity.mWebView.getScale();
            int webViewHeight = (int) (MainActivity.mWebView.getContentHeight() * scale);
            Bitmap image = Bitmap.createBitmap(MainActivity.mWebView.getWidth(), webViewHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(image);
            MainActivity.mWebView.draw(canvas);
            Bitmap captchaImage = Bitmap.createBitmap(image, 488, 363, 120, 40);

            //LinearLayOut Setup
            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            //ImageView Setup
            ImageView imageView = new ImageView(mContext);

            //setting image resource
            //imageView.setImageResource(R.drawable.play);
            imageView.setImageBitmap(captchaImage);

            //setting image position
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            //adding view to layout
            linearLayout.addView(imageView);
            //make visible to program
            Activity activity = (Activity) mContext;
            activity.setContentView(linearLayout);
        }


    }
}

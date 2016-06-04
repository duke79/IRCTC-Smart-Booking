package com.baliyaan.android.irctc_smart_booking;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baliyaan.android.library.io.FileIO;
import com.baliyaan.android.library.web.MyCallbackInterface;

import java.io.IOException;
import java.io.InputStream;

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

        // Fill user details
        js("$(\"input[name='j_username']\").val('subhash673');" +
                "$(\"input[name='j_password']\").val('sjs430');");

        // Function to convert image to base64
        js("function getBase64Image(img) {\n" +
                "    // Create an empty canvas element\n" +
                "    var canvas = document.createElement(\"canvas\");\n" +
                "    canvas.width = img.width;\n" +
                "    canvas.height = img.height;\n" +
                "\n" +
                "    // Copy the image contents to the canvas\n" +
                "    var ctx = canvas.getContext(\"2d\");\n" +
                "    ctx.drawImage(img, 0, 0);\n" +
                "\n" +
                "    // Get the data-URL formatted image\n" +
                "    // Firefox supports PNG and JPEG. You could check img.src to\n" +
                "    // guess the original format, but be aware the using \"image/jpg\"\n" +
                "    // will re-encode the image.\n" +
                "    var dataURL = canvas.toDataURL(\"image/png\");\n" +
                "\n" +
                "    return dataURL.replace(/^data:image\\/(png|jpg);base64,/, \"\");\n" +
                "};");

        // Function to confirm initialization
        js("function confirmInitialized(parent,varstring)\n" +
                "{\n" +
                "    var arr = varstring.split(\".\");\n" +
                "    var newvarstring=\"\";\n" +
                "    for(i=1;i<arr.length;i++)\n" +
                "    {\n" +
                "      newvarstring+=arr[i];\n" +
                "      if(i != arr.length-1)\n" +
                "      {\n" +
                "        newvarstring+=\".\";\n" +
                "      }\n" +
                "    }\n" +
                "    if(typeof parent[arr[0]] == 'undefined')\n" +
                "    {\n" +
                "      parent[arr[0]]={}\n" +
                "    }\n" +
                "\n" +
                "    if(arr.length>1)\n" +
                "    confirmInitialized(parent[arr[0]],newvarstring);\n" +
                "}");

        // Extract captcha image
        js("var checkExist = setInterval(function() {\n" +
                "   confirmInitialized(Android,\"irctc\");\n" +
                "   if(typeof Android.irctc.imgcaptcha=='undefined')\n" +
                "   {\n" +
                "     if ($('#cimage').length)\n" +
                "     {\n" +
                "       Android.putOnConsole(\"Captcha Image Exists!!\");\n" +
                "       Android.irctc.imgcaptcha=$('#cimage')[0];\n" +
                "       Android.putOnConsole(getBase64Image(Android.irctc.imgcaptcha));\n" +
                "       clearInterval(checkExist);\n" +
                "     }\n" +
                "   }\n" +
                "   else\n" +
                "   {\n" +
                "     clearInterval(checkExist);\n" +
                "   }\n" +
                "}, 100); // check every 100ms");

        // Get captcha from javascript
        AssetManager am = mContext.getAssets();
        try {
            InputStream is = am.open("script.js");
            String jsContent = FileIO.ReadFromInputStream(is);
            js(jsContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //MainActivity.mWebView.loadUrl("javascript: Android.showToast($(\"input[name='j_username']\"));");
        //MainActivity.mWebView.loadUrl("javascript: Android.showToast(getBase64Image($(\"#cimage\")[0]));");
        //MainActivity.mWebView.loadUrl("javascript: Android.showToast(\"Hello Hello\");");
        //MainActivity.mWebView.loadUrl("javascript: Android.putCaptcha();");

        // Get captcha from bitmap
        Bitmap captchaImage = getCaptchaImage();
        //displayBitmap(captchaImage);

    }

    public Bitmap getCaptchaImage() {
        // Get captcha
        float scale = MainActivity.mWebView.getScale();
        int webViewHeight = (int) (MainActivity.mWebView.getContentHeight() * scale);
        Bitmap image = Bitmap.createBitmap(MainActivity.mWebView.getWidth(), webViewHeight, Bitmap.Config.ARGB_8888);
        //int width = image.getWidth();
        //int height = image.getHeight();
        Canvas canvas = new Canvas(image);
        MainActivity.mWebView.draw(canvas);
        Bitmap captchaImage = Bitmap.createBitmap(image, 488, 363, 120, 40);
        /*
        int iX=370;
        if(width>975)
            iX = 340*width/975+ 45;
        int iY = 363;//363*height/1220;
        int iWidth = 150;//120*width/1280;
        int iHeight= 200;//40*height/1220;
        Bitmap captchaImage = Bitmap.createBitmap(image,iX,iY,iWidth,iHeight);
        */
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

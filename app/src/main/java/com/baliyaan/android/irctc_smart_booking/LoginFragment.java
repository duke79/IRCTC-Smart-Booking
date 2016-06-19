package com.baliyaan.android.irctc_smart_booking;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Bitmap mCaptchaImage = null;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.mWebFragment.sendCaptchaPosition();

        // Get captcha from bitmap
        //mCaptchaImage = getCaptchaImage();
        if(mCaptchaImage==null) {
            mCaptchaImage = getCaptchaImageFromUrl();
        }
        //displayBitmap(captchaImage);
        displayLoginForm();
        setCaptchaImage();
        Log.d("MyCallbackExtension: ", "finish");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setCaptchaImage() {
        Activity activity = getActivity();
        //setting image resource
        ImageView imgcaptcha = (ImageView) activity.findViewById(R.id.fragment_login_captchaimage);
        if (null != imgcaptcha && null != mCaptchaImage) {
            imgcaptcha.setImageBitmap(mCaptchaImage);
        }
    }

    private void displayLoginForm() {
        View fragmentView = getView();
        final EditText userid = (EditText) fragmentView.findViewById(R.id.fragment_login_userid);
        if (null != userid) {
            userid.setText("subhash673");
        }

        final EditText password = (EditText) getView().findViewById(R.id.fragment_login_password);
        if (null != password) {
            password.setText("sjs430");
        }

        final EditText captcha = (EditText) getView().findViewById(R.id.fragment_login_captcha);

        Button loginButton = (Button) getView().findViewById(R.id.fragment_login_loginbutton);
        if (null != loginButton) {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != userid && null != password && null != captcha) {
                        String useridStr = userid.getText().toString();
                        String passwordStr = password.getText().toString();
                        String captchaStr = captcha.getText().toString();
                        Login(useridStr, passwordStr, captchaStr);
                    }
                }
            });
        }
    }

    private void Login(String useridStr, String passwordStr, String captchaStr) {
        // Fill user details
        MainActivity mainActivity = (MainActivity) getActivity();
        if (null != mainActivity) {
            mainActivity.mWebFragment.fillLoginCredentials(useridStr, passwordStr, captchaStr);
            mainActivity.showWeb();
        }
    }

    public Bitmap getCaptchaImage() {
        Bitmap captchaImage=null;
        // Get captcha
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            float scale = mainActivity.mWebFragment.mWebView.getScale();
            int webViewHeight = (int) (mainActivity.mWebFragment.mWebView.getContentHeight() * scale);
            Bitmap image = Bitmap.createBitmap(mainActivity.mWebFragment.mWebView.getWidth(), webViewHeight, Bitmap.Config.ARGB_8888);
            //int width = image.getWidth();
            //int height = image.getHeight();
            Canvas canvas = new Canvas(image);
            mainActivity.mWebFragment.mWebView.draw(canvas);
            captchaImage = Bitmap.createBitmap(image, (int) MainActivity.captchaleft, (int) MainActivity.captchatop, (int) MainActivity.captchawidth, (int) MainActivity.captchaheight);
        /*
        int iX=370;
        if(width>975)
            iX = 340*width/975+ 45;
        int iY = 363;//363*height/1220;
        int iWidth = 150;//120*width/1280;
        int iHeight= 200;//40*height/1220;
        Bitmap captchaImage = Bitmap.createBitmap(image,iX,iY,iWidth,iHeight);
        */
        }
        return captchaImage;
    }

    public Bitmap getCaptchaImageFromUrl()
    {
        //final Bitmap[] captchaImage = {null};
            new Thread(new Runnable() {
                @Override
                public void run() {
                    URL url = null;
                    try {
                        String strURL = getActivity().getString(R.string.captcha_url);
                        url = new URL(strURL);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        String sessionCookie = mainActivity.mWebFragment.getCookie(getString(R.string.irctc_url),"JSESSIONID");
                        String slbCookie = mainActivity.mWebFragment.getCookie(getString(R.string.irctc_url),"SLB_Cookie");
                        String gaCookie = mainActivity.mWebFragment.getCookie(getString(R.string.irctc_url),"_ga");
                        Log.d("cookie found: ",sessionCookie);
                        Log.d("slb cookie found: ",slbCookie);
                        Log.d("_ga cookie found: ",gaCookie);
                        URLConnection connection = url.openConnection();
                        connection.setRequestProperty("Cookie","JSESSIONID="+sessionCookie+" ; SLB_Cookie="+slbCookie+" ; _ga="+gaCookie);
                        mCaptchaImage = BitmapFactory.decodeStream(connection.getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        return mCaptchaImage;
    }

    public void displayBitmap(Bitmap image) {
        //LinearLayOut Setup
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        //ImageView Setup
        ImageView imageView = new ImageView(getActivity());

        //setting image resource
        //imageView.setImageResource(R.drawable.play);
        imageView.setImageBitmap(image);

        //setting image position
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        //adding view to layout
        linearLayout.addView(imageView);
        //make visible to program
        Activity activity = getActivity();
        activity.setContentView(linearLayout);
    }
}

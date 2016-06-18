package com.baliyaan.android.irctc_smart_booking;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.baliyaan.android.library.web.MyWebView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public MyWebView mWebView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onStart() {
        super.onStart();

        if (mWebView == null) {
            String url = getResources().getString(R.string.irctc_url);
            MyCallbackExtension callbackExtension = new MyCallbackExtension((MainActivity) getActivity());
            mWebView = new MyWebView(getActivity(), url, callbackExtension);
            mWebView.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");
            ViewGroup frameLayout = (ViewGroup) getActivity().findViewById(R.id.fragment_web_container);
            frameLayout.addView(mWebView);
        }
    }

    public void webViewCallback() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            if (mainActivity.mLoginFragment == null) {
                //Show Login Fragment
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                mainActivity.mLoginFragment = new LoginFragment();
                fragmentTransaction.add(R.id.fragment_container, mainActivity.mLoginFragment);
                fragmentTransaction.hide(mainActivity.mWebFragment);
                fragmentTransaction.commit();
            }
        }
    }

    public void fillLoginCredentials(String id, String pass, String captcha) {
        appendJS("$(\"input[name='j_username']\").val('" + id + "');" +
                "$(\"input[name='j_password']\").val('" + pass + "');" +
                "$(\"input[name='j_captcha']\").val('" + captcha + "');");
    }

    public void sendCaptchaPosition() {
        appendJS("Android.captchaposition($(\"#cimage\").offset().top,$(\"#cimage\").offset().left,$(\"#cimage\").height(),$(\"#cimage\").width());");
    }

    public void appendJS(String string) {
        if (mWebView != null) {
            mWebView.appendJS(string);
        }
    }

    public WebFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WebFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebFragment newInstance(String param1, String param2) {
        WebFragment fragment = new WebFragment();
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
        return inflater.inflate(R.layout.fragment_web, container, false);
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
}

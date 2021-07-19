package com.somi.vegfinder.auth.welcome;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.somi.vegfinder.R;


public class WelcomeFragment extends Fragment implements View.OnClickListener{


    private WelcomeActivity activity;

    private WelcomeListener listener;

    private Button b_signUp, b_SignIn, b_withOutAuth;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        activity = (WelcomeActivity) getActivity();

        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

        b_signUp = rootView.findViewById(R.id.b_welcome_sign_up);
        b_SignIn = rootView.findViewById(R.id.b_welcome_sign_in);
        b_withOutAuth = rootView.findViewById(R.id.b_welcome_no_auth);

        b_signUp.setOnClickListener(this);
        b_SignIn.setOnClickListener(this);
        b_withOutAuth.setOnClickListener(this);

        return rootView;

    }//onCreateView


    public void setListener(WelcomeListener _listener){

        listener = _listener;

    }//setListener


    public void onClick(View view) {

        if (view == b_SignIn) {

            listener.onSignInChoice();

        } else if(view == b_signUp) {

            listener.onSignUpChoice();

        } else if(view == b_withOutAuth) {

            listener.onWithOutChoice();

        }

    }//onClick



}//WelcomeFragment

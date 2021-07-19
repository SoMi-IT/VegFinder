package com.somi.vegfinder.auth.signUp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.somi.vegfinder.R;
import com.somi.vegfinder.auth.welcome.WelcomeActivity;


public class SignUpFragment extends Fragment implements View.OnClickListener{


    private WelcomeActivity activity;

    private SignUpListener listener;

    private EditText et_email, et_password, et_confirmPsw;

    private Button b_confirm, b_dismiss;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        activity = (WelcomeActivity) getActivity();

        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        et_email = rootView.findViewById(R.id.et_sign_up_email);
        et_password = rootView.findViewById(R.id.et_sign_up_password);
        et_confirmPsw = rootView.findViewById(R.id.et_sign_up_confirmpassword);


        b_confirm = rootView.findViewById(R.id.b_sign_up_confirm_label);
        b_confirm.setOnClickListener(this);

        b_dismiss = rootView.findViewById(R.id.b_sign_up_dismiss_label);
        b_dismiss.setOnClickListener(this);


        return rootView;

    }//onCreateView


    private void saveData(){

        String emailString = et_email.getText().toString();
        String pswString = et_password.getText().toString();
        String confirmPswString = et_confirmPsw.getText().toString();

        if(emailString.equals("")){

            et_email.setError("write your e-mail");

        } else if(pswString.equals("")){

            et_password.setError("choose a password");

        } else if(pswString.length() < 6){

            et_password.setError("password length must be minimum 6 characters");

        } else if(!pswString.equals(confirmPswString)){

            et_confirmPsw.setError("passwords must match");

        } else {

            et_email.setError(null);
            et_password.setError(null);
            et_confirmPsw.setError(null);

            listener.onSignUpStarted(emailString, pswString);

        }

    }//saveData


    public void setListener(SignUpListener _listener){

        listener = _listener;

    }//setListener


    public void onClick(View view) {

        if (view == b_confirm) {

            saveData();

        } else if(view == b_dismiss) {

           getFragmentManager().popBackStack();

        }

    }//onClick

    
}//SignUpFragment

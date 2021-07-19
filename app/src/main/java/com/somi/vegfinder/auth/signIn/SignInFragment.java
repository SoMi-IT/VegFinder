package com.somi.vegfinder.auth.signIn;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.somi.vegfinder.R;
import com.somi.vegfinder.auth.welcome.WelcomeActivity;
import com.somi.vegfinder.auth.signIn.forgotPsw.ForgotPswDialog;
import com.somi.vegfinder.auth.signIn.forgotPsw.ForgotPswDialogListener;


public class SignInFragment extends Fragment implements View.OnClickListener, ForgotPswDialogListener {


    private WelcomeActivity activity;

    private SignInListener listener;

    private TextView tv_forgotPsw;

    private EditText et_email;
    private EditText et_password;

    private Button b_confirm;
    private Button b_dismiss;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        activity = (WelcomeActivity) getActivity();

        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        tv_forgotPsw = rootView.findViewById(R.id.tv_sign_in_password_forgot);
        tv_forgotPsw.setOnClickListener(this);

        et_email = rootView.findViewById(R.id.et_sign_in_email);
        et_password = rootView.findViewById(R.id.et_sign_in_password);

        b_confirm = rootView.findViewById(R.id.b_sign_in_confirm_label);
        b_confirm.setOnClickListener(this);

        b_dismiss = rootView.findViewById(R.id.b_sign_in_dismiss_label);
        b_dismiss.setOnClickListener(this);

        return rootView;

    }//onCreateView


    private void saveData(){

        String emailString = et_email.getText().toString();
        String pswString = et_password.getText().toString();

        if(emailString.equals("")){

            et_email.setError("write your e-mail!");

        } else if(pswString.equals("")){

            et_password.setError("write your password()!");

        } else {

            et_email.setError(null);
            et_password.setError(null);


            listener.onLoginStarted(emailString, pswString);

        }

    }//saveData


    public void setListener(SignInListener _listener){

        listener = _listener;

    }//setListener


    public void onClick(View view) {

        if (view == b_confirm) {

            saveData();

        } else if(view == b_dismiss) {

            getFragmentManager().popBackStack();

        } else if(view == tv_forgotPsw) {

            ForgotPswDialog forgotPswDialog = new ForgotPswDialog(activity);
            forgotPswDialog.setListener(this);
            forgotPswDialog.show();

        }

    }//onClick


    public void onRecoverPSWStarted(String email) {

        listener.onRecoverPSWStarted(email);

    }//onRecoverPSWStarted


}//SignInFragment

package com.somi.vegfinder.auth.signIn.forgotPsw;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.somi.vegfinder.R;
import com.somi.vegfinder.auth.welcome.WelcomeActivity;


public class ForgotPswDialog extends Dialog implements Button.OnClickListener {


    private ForgotPswDialogListener listener;

    private EditText et_email;
    private Button b_confirm;
    private Button b_dismiss;


    public ForgotPswDialog(WelcomeActivity _context) {

        super(_context);

        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 20);

        this.getWindow().setBackgroundDrawable(inset);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_forget_psw);

        Window window = this.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        et_email = findViewById(R.id.et_forget_psw_dialog_email);

        b_confirm = findViewById(R.id.b_forget_psw_dialog_confirm);
        b_confirm.setOnClickListener(this);

        b_dismiss = findViewById(R.id.b_forget_psw_dialog_dismiss);
        b_dismiss.setOnClickListener(this);


    }//CheckPasswordDialog


    public void setListener(ForgotPswDialogListener _listener){

        listener = _listener;

    }//setListener


    public void onClick(View view) {

        if (view == b_confirm) {

            String emailString = et_email.getText().toString();

            if(emailString.equals("")){

                et_email.setError("write your e-mail!");

            } else {

                listener.onRecoverPSWStarted(emailString);
                dismiss();

            }

        } else if(view == b_dismiss) {

            dismiss();

        }

    }//onClick



}//ForgotPswDialog
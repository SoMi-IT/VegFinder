package com.somi.vegfinder.account.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.somi.vegfinder.R;
import com.somi.vegfinder.main.MainActivity;


public class DeleteDialog extends Dialog implements Button.OnClickListener{


    private DeleteDialogListener listener;
    private Context context;

    private View v_delete;
    private ProgressBar pb_delete;
    private EditText et_email;
    private EditText et_password;
    private Button b_confirm;
    private Button b_dismiss;


    public DeleteDialog(MainActivity _context) {

        super(_context);
        context = _context;

        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 20);

        this.getWindow().setBackgroundDrawable(inset);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_delete);

        Window window = this.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        pb_delete = findViewById(R.id.pb_delete);
        pb_delete.setVisibility(View.GONE);

        v_delete = findViewById(R.id.v_delete);
        v_delete.setVisibility(View.GONE);

        et_email = findViewById(R.id.et_deletedialog_email);
        et_password = findViewById(R.id.et_deletedialog_password);

        b_confirm = findViewById(R.id.b_deletedialog_confirm_label);
        b_confirm.setOnClickListener(this);

        b_dismiss = findViewById(R.id.b_deletedialog_dismiss_label);
        b_dismiss.setOnClickListener(this);


    }//CheckPasswordDialog


    private void confirmData(){

        String emailString = et_email.getText().toString();
        String pswString = et_password.getText().toString();

        if(emailString.equals("")){

            et_email.setError("write mail!");

        } else if(pswString.equals("")){

            et_password.setError("write password!");

        } else {

            et_email.setError(null);
            et_password.setError(null);


            v_delete.setVisibility(View.VISIBLE);
            pb_delete.setVisibility(View.VISIBLE);


            listener.onDeleteStarted(emailString, pswString);

        }

    }//confirmData


    public void setListener(DeleteDialogListener _listener){

        listener = _listener;

    }//setListener


    public void onClick(View view) {

        if (view == b_confirm) {

            confirmData();

        } else if(view == b_dismiss) {

            dismiss();

        }

    }//onClick


    public void dismissLoader(){

        v_delete.setVisibility(View.GONE);
        pb_delete.setVisibility(View.GONE);


    }//dismissLoader


}//DeleteDialog
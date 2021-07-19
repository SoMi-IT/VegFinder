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
import android.widget.TextView;

import com.somi.vegfinder.R;
import com.somi.vegfinder.main.MainActivity;

public class EditDialog extends Dialog implements Button.OnClickListener {


    private EditDialogListener listener;
    private Context context;
    private String currentEmail;

    private View v_edit;
    private ProgressBar pb_edit;
    private TextView tv_current_email;
    private EditText  et_new_email;
    private EditText et_password;
    private Button b_confirm;
    private Button b_dismiss;


    public EditDialog(MainActivity _context, String email) {

        super(_context);
        context = _context;
        currentEmail = email;
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 20);

        this.getWindow().setBackgroundDrawable(inset);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_edit);

        Window window = this.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        pb_edit = findViewById(R.id.pb_edit);
        pb_edit.setVisibility(View.GONE);

        v_edit = findViewById(R.id.v_edit);
        v_edit.setVisibility(View.GONE);

        tv_current_email = findViewById(R.id.tv_editdialog_current_email);
        tv_current_email.setText(currentEmail);
        et_new_email = findViewById(R.id.et_editdialog_new_email);
        et_password = findViewById(R.id.et_editdialog_password);

        b_confirm = findViewById(R.id.b_editdialog_confirm_label);
        b_confirm.setOnClickListener(this);

        b_dismiss = findViewById(R.id.b_editdialog_dismiss_label);
        b_dismiss.setOnClickListener(this);


    }//CheckPasswordDialog


    private void saveData(){

        String newEmailString = et_new_email.getText().toString();
        String pswString = et_password.getText().toString();

        if(newEmailString.equals("")){

            et_new_email.setError("write email!");

        } else if(pswString.equals("")){

            et_password.setError("write your password");

        } else if(newEmailString.equals(currentEmail)){

            et_new_email.setError("The new email and the old one are the same");

        } else {

            et_new_email.setError(null);
            et_password.setError(null);



            v_edit.setVisibility(View.VISIBLE);
            pb_edit.setVisibility(View.VISIBLE);

            listener.onEditStarted(newEmailString, pswString);

        }

    }//saveData


    public void setListener(EditDialogListener _listener){

        listener = _listener;

    }//setListener


    public void onClick(View view) {

        if (view == b_confirm) {

            saveData();

        } else if(view == b_dismiss) {

            dismiss();

        }

    }//onClick


    public void dismissLoader(){

        v_edit.setVisibility(View.GONE);
        pb_edit.setVisibility(View.GONE);

    }//dismissLoader


}//RegisterDialog
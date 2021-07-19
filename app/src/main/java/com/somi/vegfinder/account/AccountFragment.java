package com.somi.vegfinder.account;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.somi.vegfinder.R;
import com.somi.vegfinder.auth.welcome.WelcomeActivity;
import com.somi.vegfinder.account.accountManager.AccountManager;
import com.somi.vegfinder.account.accountManager.AccountManagerListener;
import com.somi.vegfinder.account.dialog.DeleteDialog;
import com.somi.vegfinder.account.dialog.DeleteDialogListener;
import com.somi.vegfinder.account.dialog.EditDialog;
import com.somi.vegfinder.account.dialog.EditDialogListener;
import com.somi.vegfinder.main.MainActivity;


public class AccountFragment extends Fragment implements Button.OnClickListener, AccountManagerListener, DeleteDialogListener, EditDialogListener, FirestoreUserManagerListener {


    private com.somi.vegfinder.main.MainActivity activity;

    private AccountManager accountManager;
    private FirestoreUsersManager firestoreUsersManager;
    private String firebaseUserUID;

    private EditDialog editDialog;
    private DeleteDialog deleteDialog;

    private View v_account;
    private ProgressBar pb_account;

    //logged
    private ConstraintLayout cl_UID, cl_email, cl_info, cl_privileges;
    private CustomAccount customAccount;
    private ImageView iv_edit_email;
    private TextView tv_UID, tv_email,  tv_info, tv_privileges;
    private Button b_logout, b_delete;

    //not logged


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = (MainActivity) getActivity();

        accountManager = new AccountManager(activity);
        accountManager.setListener(this);


        firestoreUsersManager = new FirestoreUsersManager();
        firestoreUsersManager.setListener(this);

        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        v_account = rootView.findViewById(R.id.v_account);
        pb_account = rootView.findViewById(R.id.pb_account);

        //logged
        cl_UID = rootView.findViewById(R.id.cl_account_uid);
        cl_email = rootView.findViewById(R.id.cl_account_email);
        cl_info = rootView.findViewById(R.id.cl_account_info);
        cl_privileges = rootView.findViewById(R.id.cl_account_privileges);

        tv_UID = rootView.findViewById(R.id.tv_account_UID);
        tv_email = rootView.findViewById(R.id.tv_account_email);
        tv_info = rootView.findViewById(R.id.tv_account_info);
        tv_privileges = rootView.findViewById(R.id.tv_account_privilege);

        b_logout = rootView.findViewById(R.id.b_account_logout);
        b_logout.setOnClickListener(this);
        b_delete = rootView.findViewById(R.id.b_account_delete);
        b_delete.setOnClickListener(this);

        iv_edit_email = rootView.findViewById(R.id.iv_account_edit_email);
        iv_edit_email.setOnClickListener(this);


        //accountManager.startReauthenticate();
        updateUserState(true);

        return rootView;

    }//onCreateView


    private void updateUserState(boolean isInit){

        hideAll();

        if (isInit && accountManager.isUserLogged() && accountManager.isUserEmailIsOk()){

            firebaseUserUID = accountManager.getFirebaseUser().getUid();
            firestoreUsersManager.initUserData(firebaseUserUID);

        }else if(!isInit && accountManager.isUserLogged() && accountManager.isUserEmailIsOk()) {

            firebaseUserUID = accountManager.getFirebaseUser().getUid();

            showUserLoggedViews();

            tv_email.setText(customAccount.getEmail());
            tv_UID.setText(customAccount.getUID());
            tv_info.setText(customAccount.getName() + " " + customAccount.getSurname());

            if(customAccount.isAdmin())tv_privileges.setText("Admin");
            else tv_privileges.setText("User");

        } else showUserNotLoggedViews();

    }//updateUserState



    private void showUserNotLoggedViews(){

        v_account.setVisibility(View.GONE);
        pb_account.setVisibility(View.GONE);

        cl_UID.setVisibility(View.INVISIBLE);
        cl_email.setVisibility(View.INVISIBLE);
        cl_info.setVisibility(View.INVISIBLE);
        cl_privileges.setVisibility(View.INVISIBLE);
        b_logout.setVisibility(View.INVISIBLE);
        b_delete.setVisibility(View.INVISIBLE);


    }//showUserNotLoggedViews


    private void showUserLoggedViews(){

        v_account.setVisibility(View.GONE);
        pb_account.setVisibility(View.GONE);

        cl_UID.setVisibility(View.VISIBLE);
        cl_email.setVisibility(View.VISIBLE);
        cl_info.setVisibility(View.VISIBLE);
        cl_privileges.setVisibility(View.VISIBLE);
        b_logout.setVisibility(View.VISIBLE);
        b_delete.setVisibility(View.VISIBLE);

    }//showUserLoggedViews


    private void hideAll(){

        v_account.setVisibility(View.VISIBLE);
        pb_account.setVisibility(View.VISIBLE);

        cl_UID.setVisibility(View.INVISIBLE);
        cl_email.setVisibility(View.INVISIBLE);
        cl_info.setVisibility(View.INVISIBLE);
        cl_privileges.setVisibility(View.INVISIBLE);
        b_logout.setVisibility(View.INVISIBLE);
        b_delete.setVisibility(View.INVISIBLE);

    }//hideAll


    public void onClick(View v) {

        if(v == b_logout) {

            accountManager.logout();

        } else if(v == b_delete) {

            firebaseUserUID = accountManager.getFirebaseUser().getUid();

            deleteDialog = new DeleteDialog(activity);
            deleteDialog.setListener(this);
            deleteDialog.show();

        } else if(v == iv_edit_email) {

            editDialog = new EditDialog(activity, customAccount.getEmail());
            editDialog.setListener(this);
            editDialog.show();

        }

    }//onClick



    //------------------------------------------------------------LOGOUT


    public void onLogoutCompleted() {

        Intent myIntent = new Intent(activity, WelcomeActivity.class);
        startActivity(myIntent);

    }//onLogoutCompleted


    //------------------------------------------------------------DELETE


    public void onDeleteStarted(String email, String psw) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        accountManager.startUserDelete(email, psw);

    }//onDeleteConfirmed



    public void onDeleteSuccess() {

        deleteDialog.dismiss();

        firestoreUsersManager.deleteUserData(firebaseUserUID);

        Toast.makeText(activity, "Account deleted!", Toast.LENGTH_SHORT).show();

        updateUserState(false);

    }//onDeleteSuccess


    public void onDeleteError(String error){

        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
        deleteDialog.dismissLoader();

    }//onDeleteError



    //------------------------------------------------------------LOGIN


    public void onLoginStarted(String email, String psw) {

        accountManager.startLogin(email, psw);

    }//onLoginStarted


    public void onLoginSuccess() {

        Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show();
        updateUserState(true);

    }//onLoginSuccess


    public void onLoginError(String error) {

        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();

    }//onLoginError



    //------------------------------------------------------------EMAIL-CHANGE

    public void onEditStarted(String email, String psw) {

        accountManager.startEmailChange(email, psw);

    }//onEditStarted

    public void onEmailChanged() {

        editDialog.dismiss();
        Toast.makeText(activity, "Email changed! Please, check your email - we have sent a confirmation link", Toast.LENGTH_SHORT).show();
        accountManager.logout();

    }//onEmailChanged


    public void onEmailChangeError(String error) {

        editDialog.dismissLoader();
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();

    }//onEmailChangeError

    @Override
    public void onReauthenticateDone() {

    }

    @Override
    public void onReauthenticateError(String error) {

    }


    //------------------------------------------------------------?


    public void onUserDataRetrieved(CustomAccount _customAccount) {

        customAccount = _customAccount;
        updateUserState(false);

    }//onUserDataRetrieved


    public void onUserDataRetrievedError(String error) {

        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();

    }//onUserDataRetrievedError


}//AccountFragment

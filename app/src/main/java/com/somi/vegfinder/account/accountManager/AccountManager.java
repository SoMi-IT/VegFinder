package com.somi.vegfinder.account.accountManager;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;


public class AccountManager {

    private static final int MODE_START_DELETE = 4;
    private static final int MODE_END_DELETE = 5;

    private int currentMode = 0;

    private AccountManagerListener listener;


    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;

    private String newEmail;

    private AuthSubClassCheckIfEmailIsAlreadyUsed authSubClassCheckIfEmailIsAlreadyUsed;
    private VoidSubClassStartReauthenticate voidSubClassStartReauthenticate;
    private VoidSubClassStartChangeEmail voidSubClassStartChangeEmail;
    private VoidSubClassVerifyEmail voidSubClassVerifyEmail;

    private Context context;


    public AccountManager(Context _context){

        context = _context;

    }//AccountManager


    public void setListener(AccountManagerListener _listener) {

        listener = _listener;

    }//setMenuListener


    public void logout(){

        FirebaseAuth.getInstance().signOut();
        Toast.makeText(context, "Logout successful!", Toast.LENGTH_SHORT).show();
        listener.onLogoutCompleted();

    }//logout


    //-----------------------------------------------------------------------------------------CHECK


    public boolean isUserLogged() {

        if (getFirebaseUser() != null) return true;
        else return false;

    }//isUserLogged


    public boolean isUserEmailIsOk() {

        return FirebaseAuth.getInstance().getCurrentUser().isEmailVerified();

    }//isUserEmailIsOk


    public void startReauthenticate(String _password){

        voidSubClassStartReauthenticate = new VoidSubClassStartReauthenticate();

        voidSubClassStartReauthenticate.startVoidReauthenticate(_password);

    }//startReauthenticate


    private class VoidSubClassStartReauthenticate implements OnCompleteListener<Void> {


        public void startVoidReauthenticate(String _password){

            AuthCredential credential = EmailAuthProvider.getCredential(getFirebaseUser().getEmail(), _password);

            getFirebaseUser().reauthenticate(credential).addOnCompleteListener(this);

        }//VoidSubClassStartReauthenticate


        public void onComplete(@NonNull Task<Void> task) {

            if(task.isSuccessful()){

                listener.onReauthenticateDone();

            }else if (!task.isSuccessful())listener.onReauthenticateError(task.getException().getMessage());

        }//onComplete


    }//VoidSubClassStartReauthenticate


    //-----------------------------------------------------------------------------------------GET


    public FirebaseUser getFirebaseUser(){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser;

    }//getFirebaseUser



    //-----------------------------------------------------------------------------------------LOGIN


    public void startLogin (String email, String psw){

        mAuth = FirebaseAuth.getInstance();
        AuthLoginSubClass authLoginSubClass = new AuthLoginSubClass();
        authLoginSubClass.subStartLogin(email, psw);

    }//startLogin


    private class AuthLoginSubClass implements OnCompleteListener<AuthResult> {



        public void subStartLogin(String email, String psw){

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, psw).addOnCompleteListener(this);

        }//createNewUserFromEmailAndPsw


        public void onComplete(@NonNull Task<AuthResult> task) {

            if (task.isSuccessful()) {

                if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) listener.onLoginSuccess();

                else listener.onLoginError("Please, verify your email address");

            } else listener.onLoginError(task.getException().getMessage() + "");


        }//onComplete


    }//AuthLoginSubClass



    //-----------------------------------------------------------------------------------------EMAIL-CHANGE


    public void startEmailChange(String _newEmail, String _password){

        mAuth = FirebaseAuth.getInstance();
        newEmail = _newEmail;

        authSubClassCheckIfEmailIsAlreadyUsed = new AuthSubClassCheckIfEmailIsAlreadyUsed();
        voidSubClassStartChangeEmail = new VoidSubClassStartChangeEmail();
        voidSubClassVerifyEmail = new VoidSubClassVerifyEmail();

        voidSubClassStartChangeEmail.startCheckingIfEmailAlreadyExist(_password);

    }//Constructor


    private class VoidSubClassStartChangeEmail implements OnCompleteListener<Void> {


        public void startCheckingIfEmailAlreadyExist(String _password){

            FirebaseUser user = mAuth.getCurrentUser();

            AuthCredential credential = EmailAuthProvider.getCredential(mAuth.getCurrentUser().getEmail(), _password);

            user.reauthenticate(credential).addOnCompleteListener(this);

        }//startCheckingIfEmailAlreadyExist


        public void onComplete(@NonNull Task<Void> task) {

            if(task.isSuccessful()){

                authSubClassCheckIfEmailIsAlreadyUsed.subStartCheckIfEmailIsAlreadyUsed ();

            }else if (!task.isSuccessful())listener.onEmailChangeError(task.getException().getMessage());

        }//onComplete


    }//VoidSubClassStartChangeEmail



    private class AuthSubClassCheckIfEmailIsAlreadyUsed implements OnCompleteListener<SignInMethodQueryResult> {


        public void subStartCheckIfEmailIsAlreadyUsed (){

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.fetchSignInMethodsForEmail(newEmail).addOnCompleteListener(this);

        }//createNewUserFromEmailAndPsw


        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

            if(task.isSuccessful() && task.getResult().getSignInMethods().size() == 1){

                listener.onEmailChangeError("Email already used.");

            }else if(task.isSuccessful() && task.getResult().getSignInMethods().size() == 0){

                voidSubClassVerifyEmail.subVerifyCreateNewUserFromAccount();

            }else if (!task.isSuccessful())listener.onEmailChangeError(task.getException().getMessage());
        }

    }//AuthSubClass


    private class VoidSubClassVerifyEmail implements OnCompleteListener<Void> {

        private final int MODE_CHECK_NEW_EMAIL = 2;
        private final int MODE_EMAIL_UPDATED = 3;

        private int currentMode;


        public void subVerifyCreateNewUserFromAccount(){

            currentMode = MODE_CHECK_NEW_EMAIL;
            mAuth.getCurrentUser().updateEmail(newEmail).addOnCompleteListener(this);


        }//createNewUserFromEmailAndPsw


        public void onComplete(@NonNull Task<Void> task) {

            if(task.isSuccessful() && currentMode == MODE_CHECK_NEW_EMAIL){

                currentMode = MODE_EMAIL_UPDATED;
                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(this);

            } else if(task.isSuccessful() && currentMode == MODE_EMAIL_UPDATED){

                listener.onEmailChanged();

            }else if (!task.isSuccessful()){

                listener.onEmailChangeError(task.getException().getMessage());
            }

        }//onComplete


    }//VoidSubClass


    //-----------------------------------------------------------------------------------------DELETE


    public void startUserDelete (String email, String psw){

        currentMode = MODE_START_DELETE;

        AuthDeleteSubClass authDeleteSubClass = new AuthDeleteSubClass();
        authDeleteSubClass.subStartDeleteUserFromEmailAndPsw(email, psw);


    }//startUserDelete


    private class AuthDeleteSubClass implements OnCompleteListener<Void> {



        public void subStartDeleteUserFromEmailAndPsw(String email, String psw){

            AuthCredential credential = EmailAuthProvider.getCredential(email, psw);
            getFirebaseUser().reauthenticate(credential).addOnCompleteListener(this);

        }//createNewUserFromEmailAndPsw


        public void onComplete(@NonNull Task<Void> task) {


            if (task.isSuccessful() && currentMode == MODE_END_DELETE) {

                listener.onDeleteSuccess();

            }

            else if (task.isSuccessful() && currentMode == MODE_START_DELETE){

                currentMode = MODE_END_DELETE;

                getFirebaseUser().delete().addOnCompleteListener(this);


            }else if (!task.isSuccessful()) {

                listener.onDeleteError(task.getException().getMessage());

            }

        }


    }//AuthSubClass

}//AccountManager

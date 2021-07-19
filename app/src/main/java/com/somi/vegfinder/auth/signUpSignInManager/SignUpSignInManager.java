package com.somi.vegfinder.auth.signUpSignInManager;


import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpSignInManager {


    private SignUpSignInManagerListener listener;


    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;

    private VoidRegistrationSubClass voidRegistrationSubClass;

    private Context context;


    public SignUpSignInManager(Context _context){

        context = _context;

    }//AccountManager


    public void setListener(SignUpSignInManagerListener _listener) {

        listener = _listener;

    }//setMenuListener



    //-----------------------------------------------------------------------------------------GET


    public FirebaseUser getFirebaseUser(){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser;

    }//getFirebaseUser


    //-----------------------------------------------------------------------------------------REGISTER


    public void startRegistration(String email, String psw){


        mAuth = FirebaseAuth.getInstance();
        AuthRegistrationSubClass authRegistrationSubClass = new AuthRegistrationSubClass();
        voidRegistrationSubClass = new VoidRegistrationSubClass();

        authRegistrationSubClass.subStartCreateNewUserFromEmailAndPsw(email, psw);

    }//startRegistration


    private class AuthRegistrationSubClass implements OnCompleteListener<AuthResult> {



        public void subStartCreateNewUserFromEmailAndPsw(String email, String psw){

            mAuth.createUserWithEmailAndPassword(email, psw).addOnCompleteListener(this);

        }//createNewUserFromEmailAndPsw


        public void onComplete(@NonNull Task<AuthResult> task) {

            if (task.isSuccessful()) {

                voidRegistrationSubClass.subVerifyCreateNewUserFromEmailAndPsw();


            } else listener.onRegistrationError(task.getException().getMessage());


        }//onComplete


    }//AuthSubClass


    private class VoidRegistrationSubClass implements OnCompleteListener<Void> {


        public void subVerifyCreateNewUserFromEmailAndPsw(){

            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(this);

        }//createNewUserFromEmailAndPsw


        public void onComplete(@NonNull Task<Void> task) {

            if(task.isSuccessful())listener.onVerificationEmailSend();
            else listener.onRegistrationError(task.getException().getMessage());

        }//onComplete


    }//VoidSubClass


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


    //-----------------------------------------------------------------------------------------RECOVER


    public void startRecoverPSW (String email){

        mAuth = FirebaseAuth.getInstance();
        AuthRecoverPSWSubClass authRecoverPSWSubClass = new AuthRecoverPSWSubClass();
        authRecoverPSWSubClass.subStartLogin(email);

    }//startRecoverPSW


    private class AuthRecoverPSWSubClass implements OnCompleteListener<Void> {



        public void subStartLogin(String email){

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(this);

        }//createNewUserFromEmailAndPsw


        public void onComplete(@NonNull Task<Void> task) {

            if (task.isSuccessful()) {

                listener.onMailSent();

            } else listener.onMailSentError(task.getException().getMessage() + "");

        }//onComplete


    }//AuthLoginSubClass

}//AccountManager

package com.somi.vegfinder.auth.signIn;

public interface SignInListener {

    void onLoginStarted(String email, String psw);
    void onRecoverPSWStarted(String email);


}//SignInListener

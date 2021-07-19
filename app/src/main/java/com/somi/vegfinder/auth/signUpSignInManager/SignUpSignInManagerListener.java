package com.somi.vegfinder.auth.signUpSignInManager;

public interface SignUpSignInManagerListener {

    void onVerificationEmailSend();
    void onRegistrationError(String error);
    void onLoginSuccess();
    void onLoginError(String error);
    void onMailSent();
    void onMailSentError(String error);


}//SignUpSignInManagerListener

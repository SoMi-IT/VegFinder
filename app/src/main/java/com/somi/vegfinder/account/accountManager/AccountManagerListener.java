package com.somi.vegfinder.account.accountManager;

public interface AccountManagerListener {


    void onLogoutCompleted();
    void onDeleteSuccess();
    void onDeleteError(String error);
    void onLoginSuccess();
    void onLoginError(String error);
    void onEmailChanged();
    void onEmailChangeError(String error);
    void onReauthenticateDone();
    void onReauthenticateError(String error);

}//AccountManagerListener

package com.somi.vegfinder.account;

public interface FirestoreUserManagerListener {


    void onUserDataRetrieved(CustomAccount customAccount);
    void onUserDataRetrievedError(String error);

}//FirestoreUserManagerListener

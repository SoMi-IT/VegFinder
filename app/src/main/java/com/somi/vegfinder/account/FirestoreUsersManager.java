package com.somi.vegfinder.account;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;


public class FirestoreUsersManager implements EventListener<DocumentSnapshot>, OnCompleteListener<DocumentSnapshot> {


    private FirestoreUserManagerListener listener;
    private String firebaseUserUID;


    public void setListener(FirestoreUserManagerListener _listener) {

        listener = _listener;

    }//setMenuListener



    public void initUserData(String _firebaseUserUID){

        firebaseUserUID = _firebaseUserUID;

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference  = firebaseFirestore.collection("users").document(firebaseUserUID);

        documentReference.get().addOnCompleteListener(this);

    }//initUserData


    public void deleteUserData(String _firebaseUserUID){

        firebaseUserUID = _firebaseUserUID;

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseUserUID);
        documentReference.delete();

    }//deleteUserData


    private void createUserData(){

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseUserUID);

        Map<String, Object> user = new HashMap<>();

        user.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        user.put("name", "");
        user.put("surname", "");
        user.put("isAdmin", false);

        documentReference.set(user);

        retrieveData();

    }//createUserData


    private void retrieveData() {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference  = firebaseFirestore.collection("users").document(firebaseUserUID);

        documentReference.addSnapshotListener(this);

    }//retrieveData


    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {



        if(FirebaseAuth.getInstance().getCurrentUser() == null)return;

        CustomAccount customAccount = new CustomAccount();

        customAccount.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        customAccount.setUID(FirebaseAuth.getInstance().getCurrentUser().getUid());
        customAccount.setName(documentSnapshot.getString("name"));
        customAccount.setSurname(documentSnapshot.getString("surname"));
        customAccount.setAdmin(documentSnapshot.getBoolean("isAdmin"));

        listener.onUserDataRetrieved(customAccount);

    }//onEvent


    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

        if (task.isSuccessful()) {

            DocumentSnapshot document = task.getResult();

            if (document.exists()) {

                retrieveData();

            } else {

                createUserData();
            }

        } else {

            listener.onUserDataRetrievedError(task.getException().getMessage());
        }


    }//onComplete

}//FirestoreUsersManager

package com.somi.vegfinder.auth.welcome;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.somi.vegfinder.R;
import com.somi.vegfinder.auth.signIn.SignInFragment;
import com.somi.vegfinder.auth.signIn.SignInListener;
import com.somi.vegfinder.auth.signUp.SignUpFragment;
import com.somi.vegfinder.auth.signUp.SignUpListener;
import com.somi.vegfinder.auth.signUpSignInManager.SignUpSignInManager;
import com.somi.vegfinder.auth.signUpSignInManager.SignUpSignInManagerListener;
import com.somi.vegfinder.main.MainActivity;


public class WelcomeActivity extends AppCompatActivity implements WelcomeListener, SignInListener, SignUpListener, SignUpSignInManagerListener {



    private SignUpSignInManager signUpSignInManager;

    private FragmentManager mainFragmentManager;

    private WelcomeFragment welcomeFragment;
    private SignUpFragment signUpFragment;
    private SignInFragment signInFragment;

    private View v_signUpSignIn;
    private ProgressBar pb_signUpSignIn;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.__2);

        /*v_signUpSignIn = findViewById(R.id.v_sign_up_sign_in);
        pb_signUpSignIn = findViewById(R.id.pb_sign_up_sign_in);

        v_signUpSignIn.setVisibility(View.GONE);
        pb_signUpSignIn.setVisibility(View.GONE);

        signUpSignInManager = new SignUpSignInManager(this);
        signUpSignInManager.setListener(this);

        showWelcomeFragment();*/

    }//onCreate



    public void dismissLoader(){

        v_signUpSignIn.setVisibility(View.GONE);
        pb_signUpSignIn.setVisibility(View.GONE);

    }//dismissLoader


    public void showLoader(){

        v_signUpSignIn.setVisibility(View.VISIBLE);
        pb_signUpSignIn.setVisibility(View.VISIBLE);

    }//showLoader


    public void showWelcomeFragment() {

        welcomeFragment = new WelcomeFragment();
        welcomeFragment.setListener(this);
        mainFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = mainFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_sign_up_sign_in, welcomeFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        welcomeFragment.setListener(this);

    }//showWelcomeFragment


    public void showSignUpFragment() {

        signUpFragment = new SignUpFragment();
        mainFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = mainFragmentManager.beginTransaction().addToBackStack(null);
        fragmentTransaction.replace(R.id.fl_sign_up_sign_in, signUpFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        signUpFragment.setListener(this);

    }//showSignUpFragment


    public void showSignInFragment() {

        signInFragment = new SignInFragment();
        mainFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = mainFragmentManager.beginTransaction().addToBackStack(null);
        fragmentTransaction.replace(R.id.fl_sign_up_sign_in, signInFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        signInFragment.setListener(this);

    }//showSignInFragment


    public boolean isInternetAvailable() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }//isInternetAvailable



    //------------------------------------------------------------REGISTER


    public void onSignUpChoice() {

        if (isInternetAvailable()) {

            showSignUpFragment();

        }else Toast.makeText(this, "Please, check internet connection", Toast.LENGTH_SHORT).show();

    }//onSignUpChoice


    public void onSignUpStarted(String email, String psw) {

        showLoader();
        signUpSignInManager.startRegistration(email, psw);

    }//onSignUpStarted


    public void onVerificationEmailSend() {

        Toast.makeText(this, "Please, check your email - we have sent a confirmation link", Toast.LENGTH_SHORT).show();
        dismissLoader();

    }//onVerificationEmailSend


    public void onRegistrationError(String error) {

        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        dismissLoader();

    }//onRegistrationError


    //------------------------------------------------------------LOGIN

    public void onSignInChoice() {

        if (isInternetAvailable()) {

            showSignInFragment();

        }else Toast.makeText(this, "Please, check internet connection", Toast.LENGTH_SHORT).show();

    }//onSignInChoice


    public void onWithOutChoice() {

        if (isInternetAvailable()) {

            Intent myIntent = new Intent(this, MainActivity.class);
            startActivity(myIntent);

        }else Toast.makeText(this, "Please, check internet connection", Toast.LENGTH_SHORT).show();

    }//onSignInChoice


    public void onLoginStarted(String email, String psw) {

        showLoader();
        signUpSignInManager.startLogin(email, psw);

    }//onLoginStarted


    public void onLoginSuccess() {

        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        dismissLoader();

        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);

    }//onLoginSuccess


    public void onLoginError(String error) {

        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        dismissLoader();

    }//onLoginError


    //-----------------------------------------------------------------------------------------RECOVER

    public void onRecoverPSWStarted(String email) {

        showLoader();
        signUpSignInManager.startRecoverPSW(email);

    }//onRecoverPSWStarted


    public void onMailSent() {

        dismissLoader();
        Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show();


    }//onMailSent


    public void onMailSentError(String error) {

        dismissLoader();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

    }//onMailSentError


}//SignUpSignInActivity

package com.neet101.project;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public TextView btnLogin;

    public Button btnSignUp, btnFBLogin;

    public Context _context;

    public LoginButton btnFBLogin1;

    public CallbackManager callbackManager;

    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);
        _context = getApplicationContext();

        btnLogin = (TextView) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnFBLogin = (Button) findViewById(R.id.btnFBLogin);

        btnFBLogin1 = (LoginButton) findViewById(R.id.login_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), SignUpAActivity.class);
                startActivity(i);
            }
        });


        callbackManager = CallbackManager.Factory.create();

        btnFBLogin1 = (LoginButton) findViewById(R.id.login_button);

        btnFBLogin1.setReadPermissions(Arrays.asList(EMAIL));

        btnFBLogin1.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                Log.d("Status", "Success");
                Log.d("Facebook_AppID", loginResult.getAccessToken().getApplicationId());
                Log.d("Facebook_Token", loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("Status", "Cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("Status", "Error" + exception.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}

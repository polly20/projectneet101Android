package com.neet101.project;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    public TextView btnLogin;

    public Button btnSignUp, btnFBLogin;

    public Context _context;

    public LoginButton btnFBLogin1;

    public CallbackManager callbackManager;

    private static final String EMAIL = "email";

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    public static String fb_url = "";

    public String userId, firstName, lastName, email, birthday, gender;

    public URL profilePicture;

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

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.neet101.project",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);

                finish();

            }
        });

        btnFBLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFBLogin1.performClick();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), SignUpAActivity.class);
                startActivity(i);

                finish();
            }
        });

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logOut();

        btnFBLogin1.setReadPermissions(Arrays.asList(EMAIL));

        btnFBLogin1.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                Log.d("Status", "Success");
                Log.d("Facebook_AppID", loginResult.getAccessToken().getApplicationId());
                Log.d("Facebook_Token", loginResult.getAccessToken().getToken());
                Log.d("Facebook_UserId", loginResult.getAccessToken().getUserId());

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e(TAG,object.toString());
                        Log.e(TAG,response.toString());

                        try {
                            userId = object.getString("id");
                            profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");
                            if(object.has("first_name"))
                                firstName = object.getString("first_name");
                            if(object.has("last_name"))
                                lastName = object.getString("last_name");
                            if (object.has("email"))
                                email = object.getString("email");
                            if (object.has("birthday"))
                                birthday = object.getString("birthday");
                            if (object.has("gender"))
                                gender = object.getString("gender");

                            Helper.Put(MainActivity.this, "facebook_id", userId);
                            Helper.Put(MainActivity.this, "facebook_profile", profilePicture.toString());
                            Helper.Put(MainActivity.this, "fname", firstName);
                            Helper.Put(MainActivity.this, "lname", lastName);
                            Helper.Put(MainActivity.this, "email", email);

                            new validate_facebook().execute();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                //Here we put the requested fields to be returned from the JSONObject
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, birthday, gender");
                request.setParameters(parameters);
                request.executeAsync();
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

    private class validate_facebook extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {

            String[] defAccount = Helper.DefaultAccount(_context);

            HttpHandler sh = new HttpHandler(defAccount[0], defAccount[1]);

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(Helper.Api_Url + "/api/login/validation?account=" + email, "POST", _context);

            if (jsonStr != null) {
                Log.e(TAG, "Response from url: " + jsonStr);
                return jsonStr;
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(String JSON) {
            super.onPostExecute(JSON);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            try {
                JSONObject jsonObj = new JSONObject(JSON);

                String status = jsonObj.get("status").toString();

                String result = jsonObj.get("message").toString();

                Log.d("status", status);

                Log.d("result", result);

                Intent main;


                if(Integer.parseInt(status) != 200) {
                    main = new Intent(MainActivity.this, SignUpAActivity.class);
                }
                else {
                    main = new Intent(MainActivity.this, DashboardActivity.class);

                    Integer StudentUid = jsonObj.getInt("UID");

                    Log.d("StudentUid", StudentUid + "");

                    Helper.Put(MainActivity.this, "UID", StudentUid.toString());
                }

                startActivity(main);
                finish();

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

        }

    }




}

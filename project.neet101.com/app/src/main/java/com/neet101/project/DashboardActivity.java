package com.neet101.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;

import java.io.InputStream;

public class DashboardActivity extends AppCompatActivity {

    public CallbackManager callbackManager;

    public ProfileTracker profileTracker;

    public String userId, firstName, lastName, email, imageUrl;

    public Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setTitle("Profile");

        Bundle inBundle = getIntent().getExtras();

        String fname = Helper.Get(DashboardActivity.this, "fname");
        String lname = Helper.Get(DashboardActivity.this, "lname");
        String emailAddress = Helper.Get(DashboardActivity.this, "email");
        String facebook_profile = Helper.Get(DashboardActivity.this, "facebook_profile");

        firstName = fname;
        lastName = lname;
        email = emailAddress;
        imageUrl = facebook_profile;

        TextView Fullname = (TextView)findViewById(R.id.Fullname);
        Fullname.setText(firstName + " " + lastName);

        TextView Email = (TextView)findViewById(R.id.Email);
        Email.setText(email);

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        new DownloadImageTask((ImageView) findViewById(R.id.profileImage))
                .execute(imageUrl);
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void logout(){
        LoginManager.getInstance().logOut();
        Intent login = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(login);
        finish();
    }

}

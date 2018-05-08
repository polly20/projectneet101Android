package com.neet101.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        Helper.Put(DashboardActivity.this, "is_logged", "YES");

        String fname = Helper.Get(DashboardActivity.this, "fname");
        String lname = Helper.Get(DashboardActivity.this, "lname");
        String emailAddress = Helper.Get(DashboardActivity.this, "email");
        String facebook_id = Helper.Get(DashboardActivity.this, "facebook_id");
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

        if(imageUrl.length() > 0) {

            Log.d("FBImages1", imageUrl);

            new DownloadImageTask((ImageView) findViewById(R.id.profileImage))
                    .execute(imageUrl);
        }
        else {

            if(facebook_id != null) {
                String fb_me = "https://graph.facebook.com/" + facebook_id + "/picture?type=large";

                Log.d("FBImages2", fb_me);

                new DownloadImageTask((ImageView) findViewById(R.id.profileImage))
                        .execute(fb_me);
            }
            else {
                Log.d("FBImages2", null);

                ImageView profile = (ImageView) findViewById(R.id.profileImage);
                profile.setImageResource(R.mipmap.avatar);
            }

        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Toast.makeText(getApplicationContext(), "You cannot go back.",
                    Toast.LENGTH_SHORT).show();

            return false;
        }

        return super.onKeyDown(keyCode, event);
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
        Helper.Put(DashboardActivity.this, "is_logged", "NO");
        LoginManager.getInstance().logOut();
        Intent login = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(login);
        finish();
    }

}

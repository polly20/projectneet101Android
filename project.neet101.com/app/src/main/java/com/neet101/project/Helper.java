package com.neet101.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    private static SharedPreferences sharedPref;

    public static Integer StudentUid;
    public static Integer SubjectId;

    public static Integer Correct = 0;
    public static Integer Incorrect = 0;
    public static Integer DontKnow = 0;
    public static Integer Know = 0;
    public static Integer SomewhatKnow = 0;

    public static List<DashboardSubject> SubjectList;

    public static DashboardSubject _Bio;
    public static DashboardSubject _Chem;
    public static DashboardSubject _Phy;

    public static String[] DefaultAccount(Context context) {
        String email = context.getResources().getString(R.string.neet_api_email);

        String password = context.getResources().getString(R.string.need_api_password);

        return new String[] { email, password };
    }

    public static void Put(Activity activity, String key, String value) {
        sharedPref = activity.getSharedPreferences("MySharedPrefTrans", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String Get(Activity activity, String key) {
        sharedPref = activity.getSharedPreferences("MySharedPrefTrans", Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    public static void Del(Activity activity, String key) {
        sharedPref = activity.getSharedPreferences("MySharedPrefTrans", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, null);
        editor.commit();
    }

    public static void Flush(Activity activity) {
        SharedPreferences mysharedpred = activity.getSharedPreferences("MySharedPrefTrans", Context.MODE_PRIVATE);
        mysharedpred.edit().clear().commit();
    }

    public static boolean IsEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public static boolean IsEmailValid(EditText email) {
        String  expression="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email.getText().toString();
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }
    
    public static void MessageBoxOKShow(Activity activity, String caption, String message, int height){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_message_box_ok);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = height;

        TextView title_dialog = (TextView) dialog.findViewById(R.id.txtTitle);
        TextView title_message = (TextView) dialog.findViewById(R.id.txtMessage);
        Button btnOK = (Button) dialog.findViewById(R.id.btnOk);

        title_dialog.setText(caption);
        title_message.setText(Html.fromHtml(message));

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}

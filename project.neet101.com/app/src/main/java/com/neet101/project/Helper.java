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
import android.widget.TextView;

public class Helper {

    private static SharedPreferences sharedPref;

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

    public static void dialogBox(Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("Click on Image for tag");
        alertDialogBuilder.setPositiveButton("Ok",
        new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        alertDialogBuilder.setNegativeButton("cancel",
        new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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

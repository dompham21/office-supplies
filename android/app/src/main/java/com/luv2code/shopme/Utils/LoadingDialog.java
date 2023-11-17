package com.luv2code.shopme.Utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

import com.luv2code.shopme.R;

public class LoadingDialog {
    AlertDialog alertDialog;
    private Activity activity;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void showLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.dialog_loading, null));
        builder.setCancelable(false);


        alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public void dismissLoading() {
        if(alertDialog != null) {
            alertDialog.dismiss();

        }
    }
}

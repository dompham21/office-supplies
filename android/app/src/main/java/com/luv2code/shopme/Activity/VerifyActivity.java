package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.luv2code.shopme.App;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LoadingDialog;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.OTPViewModel;

public class VerifyActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ConstraintLayout layoutEmail;
    private OTPViewModel otpViewModel;
    LocalStorage localStorage;
    private LoadingDialog loadingDialog;

    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        setControls();
        setEvents();
    }

    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        otpViewModel.getGenerateOTPData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 0 && message != null) {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();

            }
        });

        layoutEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accessToken = "Bearer ".concat(localStorage.getKeyAccessToken());
                otpViewModel.getGenerateOTP(accessToken);

                Intent intent = new Intent(view.getContext(), OtpActivity.class);
                if(type != null) {
                    intent.putExtra(App.KEY_EDIT_PROFILE, type);
                }
                startActivity(intent);
                finish();
            }
        });
    }

    private void setControls() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString(App.KEY_EDIT_PROFILE);
        }

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Xác minh bảo mật");

        layoutEmail = findViewById(R.id.layout_email);
        otpViewModel = new ViewModelProvider(this).get(OTPViewModel.class);
        localStorage = new LocalStorage(App.getContext());
        loadingDialog = new LoadingDialog(this);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
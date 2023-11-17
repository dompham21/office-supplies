package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.luv2code.shopme.App;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Request.GenerateTokenForgotPassword;
import com.luv2code.shopme.Utils.CustomTextInputLayout;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.OTPViewModel;
import com.luv2code.shopme.ViewModel.UserViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CustomTextInputLayout layoutEmail;
    private OTPViewModel otpViewModel;
    private LocalStorage localStorage;
    private UserViewModel userViewModel;
    private EditText edtEmail;
    private AppCompatButton btnVerify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        setControls();
        setEvents();
    }

    private void setControls() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        edtEmail = findViewById(R.id.edt_email);
        layoutEmail = findViewById(R.id.layout_tv_email);

        btnVerify = findViewById(R.id.btn_verify);
        otpViewModel = new ViewModelProvider(this).get(OTPViewModel.class);
        localStorage = new LocalStorage(App.getContext());
    }

    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = charSequence.toString().trim();
                if (email.isEmpty()) {
                    layoutEmail.setError("Please enter your email in the field!");
                }
                else {
                    layoutEmail.setError("");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b && edtEmail.getText().toString().trim().isEmpty()) {
                    layoutEmail.setError("Please enter your email in the field!");
                }
            }
        });

        otpViewModel.getGenerateForgotOTPData().observe(this, object -> {
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

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edtEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), "Please enter your email in the field!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else {
                    GenerateTokenForgotPassword generateTokenForgotPassword = new GenerateTokenForgotPassword(email);
                    otpViewModel.postGenerateForgotOTP(generateTokenForgotPassword);

                    Intent intent = new Intent(view.getContext(), OtpActivity.class);
                    intent.putExtra(App.KEY_EDIT_PROFILE, App.VALUE_FORGOT_PASSWORD);
                    intent.putExtra(App.KEY_FORGOT_PASSWORD, email);
                    startActivity(intent);
                    finish();
                }
            }
        });
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
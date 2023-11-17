package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luv2code.shopme.App;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Request.SignupRequest;
import com.luv2code.shopme.Utils.CustomTextInputLayout;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.Utils.PhoneNumberValidator;
import com.luv2code.shopme.ViewModel.LoginViewModel;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtEmail, edtName, edtPhone, edtPassword;
    private CustomTextInputLayout layoutEmail, layoutPassword, layoutName, layoutPhone;
    private AppCompatButton btnSignup;
    private TextView tvLogin;
    Gson gson = new Gson();
    LocalStorage localStorage;
    private LoginViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setControl();
        setEvent();
    }

    private void setControl() {
        edtEmail = findViewById(R.id.edt_email);
        edtName = findViewById(R.id.edt_name);
        edtPhone = findViewById(R.id.edt_phone);
        edtPassword = findViewById(R.id.edt_password);
        layoutEmail = findViewById(R.id.layout_tv_email);
        layoutPassword = findViewById(R.id.layout_tv_password);
        layoutName = findViewById(R.id.layout_tv_name);
        layoutPhone = findViewById(R.id.layout_tv_phone);

        btnSignup = findViewById(R.id.btn_signup);
        tvLogin = findViewById(R.id.tv_log_in);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        localStorage = new LocalStorage(App.getContext());

    }

    private void setEvent() {
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = charSequence.toString().trim();
                if (email.isEmpty()) {
                    layoutEmail.setError(getString(R.string.enter_email_field));
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
                    layoutEmail.setError(getString(R.string.enter_email_field));
                }
            }
        });

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = charSequence.toString().trim();
                if (name.isEmpty()) {
                    layoutName.setError(getString(R.string.enter_name_field));
                }
                else {
                    layoutName.setError("");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b && edtName.getText().toString().trim().isEmpty()) {
                    layoutName.setError(getString(R.string.enter_name_field));
                }
            }
        });

        edtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String phone  = edtPhone.getText().toString().trim();
                if(!b && phone.isEmpty()) {
                    layoutPhone.setError(getString(R.string.enter_phone_field));
                }
                else if(!b && !PhoneNumberValidator.validate(phone)) {
                    layoutPhone.setError(getString(R.string.invalid_phone));
                }
            }
        });

        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phone = charSequence.toString().trim();
                if (phone.isEmpty()) {
                    layoutPhone.setError(getString(R.string.enter_phone_field));
                }
                else if(!PhoneNumberValidator.validate(phone)) {
                    layoutPhone.setError(getString(R.string.invalid_phone));
                }
                else {
                    layoutPhone.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });



        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = charSequence.toString().trim();
                if (password.isEmpty()) {
                    layoutPassword.setError(getString(R.string.enter_password_field));
                }
                else if(password.length() < 6) {
                    layoutPassword.setError(getString(R.string.password_than_6));
                }
                else {
                    layoutPassword.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String password = edtPassword.getText().toString().trim();
                if(!b && password.isEmpty()) {
                    layoutPassword.setError(getString(R.string.enter_password_field));
                }
                else if(!b && password.length() < 6) {
                    layoutPassword.setError(getString(R.string.password_than_6));
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.enter_email_field), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(name.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.enter_name_field), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(phone.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.enter_phone_field), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(!PhoneNumberValidator.validate(phone)) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.invalid_phone), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(password.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.enter_password_field), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(password.length() < 6) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.password_than_6), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else {
                    SignupRequest signupRequest = new SignupRequest(name, email, password, phone);
                    viewModel.postRegister(signupRequest);
                }
            }
        });

        viewModel.getSignupData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1) {
                String userString = gson.toJson(object.getData());
                localStorage.createUserLoginSession(userString, object.getAccessToken(), object.getRefreshToken());

                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_SHORT, CustomToast.SUCCESS).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            else if(result == 0 && message != null) {
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });



    }
}
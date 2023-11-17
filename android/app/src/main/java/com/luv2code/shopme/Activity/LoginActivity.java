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
import android.widget.Toast;

import com.google.gson.Gson;
import com.luv2code.shopme.App;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Request.LoginRequest;
import com.luv2code.shopme.Utils.CustomTextInputLayout;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private CustomTextInputLayout layoutEmail, layoutPassword;
    private AppCompatButton btnLogin;
    private TextView forgotPassword, signUp;
    Gson gson = new Gson();
    LocalStorage localStorage;
    private LoginViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setControl();
        setEvent();
    }

    private void setControl() {
        edtEmail = findViewById(R.id.login_email);
        edtPassword = findViewById(R.id.login_password);
        layoutEmail = findViewById(R.id.layout_tv_email);
        layoutPassword = findViewById(R.id.layout_tv_password);

        btnLogin = findViewById(R.id.btn_login);

        forgotPassword = findViewById(R.id.tv_forgot_password);
        signUp = findViewById(R.id.tv_sign_up);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        localStorage = new LocalStorage(App.getContext());
    }

    private void setEvent() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edtEmail.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();
                if (email.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.enter_email_field), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(password.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.enter_password_field), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(password.length() < 6) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.password_than_6), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else {
                    LoginRequest loginRequest = new LoginRequest(email, password);

                    viewModel.postLogin(loginRequest);
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        viewModel.isLoadingLogin().observe(this, isLoading -> {
//            if (isLoading) {
//                showProgressDialog();
//            } else {
//                hideProgressDialog();
//            }
        });


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

        viewModel.getLoginData().observe(this, object -> {
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
            } else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });
    }

}
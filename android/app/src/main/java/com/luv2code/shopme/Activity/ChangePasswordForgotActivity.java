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
import com.luv2code.shopme.Request.ChangePasswordRequest;
import com.luv2code.shopme.Request.VerifyTokenNewPasswordRequest;
import com.luv2code.shopme.Utils.CustomTextInputLayout;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.OTPViewModel;

public class ChangePasswordForgotActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private OTPViewModel otpViewModel;
    private LocalStorage localStorage;
    private String emailSend;
    private Integer otpVerify;
    private EditText edtOldPassword, edtNewPassword, edtConfirmNewPassword;
    private CustomTextInputLayout layoutEdtOldPassword, layoutEdtNewPassword, layoutEdtConfirmNewPassword;
    private AppCompatButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_forgot);

        setControls();
        setEvents();
    }

    private void setControls() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            emailSend = bundle.getString(App.KEY_EMAIL_SEND);
            otpVerify = bundle.getInt(App.KEY_OTP_VERIFY);
        }
        toolbar = findViewById(R.id.toolbar);
        otpViewModel = new ViewModelProvider(this).get(OTPViewModel.class);


        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setTitle("Thay đổi mật khẩu mới");
        }

        edtNewPassword = findViewById(R.id.edt_new_password);
        edtConfirmNewPassword = findViewById(R.id.edt_confirm_password);
        layoutEdtNewPassword = findViewById(R.id.layout_tv_new_password);
        layoutEdtConfirmNewPassword = findViewById(R.id.layout_tv_confirm_password);
        btnSave = findViewById(R.id.btn_verify);
    }

    private void setEvents() {
        edtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String newPassword = charSequence.toString().trim();

                if (newPassword.isEmpty()) {
                    layoutEdtNewPassword.setError(getString(R.string.enter_new_password_field));
                }
                else if(newPassword.length() < 6) {
                    layoutEdtNewPassword.setError(getString(R.string.new_password_than_6));
                }
                else {
                    layoutEdtNewPassword.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edtNewPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String newPassword = edtNewPassword.getText().toString().trim();

                if(!b && newPassword.isEmpty()) {
                    layoutEdtNewPassword.setError(getString(R.string.enter_new_password_field));
                }
            }
        });

        edtConfirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String confirmPassword = charSequence.toString().trim();
                String newPassword = edtNewPassword.getText().toString().trim();
                if (confirmPassword.isEmpty()) {
                    layoutEdtConfirmNewPassword.setError(getString(R.string.enter_confirm_password_field));
                }
                else if(confirmPassword.length() < 6) {
                    layoutEdtConfirmNewPassword.setError(getString(R.string.confirm_password_than_6));
                }
                else if(!confirmPassword.equals(newPassword)) {
                    layoutEdtConfirmNewPassword.setError(getString(R.string.confirm_same_new_password));
                }
                else {
                    layoutEdtConfirmNewPassword.setError("");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edtConfirmNewPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String confirmPassword = edtConfirmNewPassword.getText().toString().trim();
                String newPassword = edtNewPassword.getText().toString().trim();

                if(!b && confirmPassword.isEmpty()) {
                    layoutEdtConfirmNewPassword.setError(getString(R.string.enter_new_password_field));
                }
                else if(!b && !confirmPassword.equals(newPassword)) {
                    layoutEdtConfirmNewPassword.setError(getString(R.string.confirm_same_new_password));
                }
            }
        });

        otpViewModel.getForgotPassword().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && message != null) {
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_LONG, CustomToast.SUCCESS).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
            else if(result == 0 && message != null) {
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String confirmPassword = edtConfirmNewPassword.getText().toString().trim();
                String newPassword = edtNewPassword.getText().toString().trim();

                if (newPassword.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.enter_new_password_field), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                } else if (confirmPassword.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.enter_confirm_password_field), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                } else if (newPassword.length() < 6) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.new_password_than_6), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                } else if (confirmPassword.length() < 6) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.confirm_password_than_6), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                } else if (!confirmPassword.equals(newPassword)) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.confirm_same_new_password), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                } else {
                    if (emailSend != null && otpVerify != null) {
                        VerifyTokenNewPasswordRequest verifyTokenNewPasswordRequest = new VerifyTokenNewPasswordRequest(newPassword, otpVerify, emailSend);
                        otpViewModel.postVerifyTokenNewPassword(verifyTokenNewPasswordRequest);
                    }
                }
            }
        });
    }
}
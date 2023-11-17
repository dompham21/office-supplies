package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.User;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Request.OTPRequest;
import com.luv2code.shopme.Request.ValidateTokenForgotRequest;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LoadingDialog;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.OTPViewModel;
import com.luv2code.shopme.ViewModel.UserViewModel;

public class OtpActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edtCode1,edtCode2,edtCode3,edtCode4,edtCode5,edtCode6;
    private TextView tvNewCode, tvEmail;
    private AppCompatButton btnVerify;
    private OTPViewModel otpViewModel;
    private LoadingDialog loadingDialog;
    private LocalStorage localStorage;
    private String type;
    private String newEmail;
    private UserViewModel userViewModel;
    private String emailSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        setControls();
        setEvents();
    }

    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        otpViewModel.getValidateOTPData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && message != null) {
                CustomToast.toastCenterTransparent(this, "Xác minh thành công!", CustomToast.LENGTH_SHORT, CustomToast.SUCCESS).show();

                if(type != null) {
                    if(type.equals(App.VALUE_EDIT_PHONE) || type.equals(App.VALUE_EDIT_EMAIL)) {
                        Intent intent = new Intent(this, ChangeInfoUserActivity.class);
                        intent.putExtra(App.KEY_EDIT_PROFILE, type);
                        startActivity(intent);
                        finish();
                    }
                }
            }
            else if(result == 0 && message != null){
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        otpViewModel.getValidateForgotOTPData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && message != null && object.getEmail() != null && object.getOtp() != null) {
                CustomToast.toastCenterTransparent(this, "Xác minh thành công!", CustomToast.LENGTH_SHORT, CustomToast.SUCCESS).show();

                if(type != null) {
                    if(type.equals(App.VALUE_FORGOT_PASSWORD)) {
                        Intent intent = new Intent(this, ChangePasswordForgotActivity.class);
                        intent.putExtra(App.KEY_EMAIL_SEND, object.getEmail());
                        intent.putExtra(App.KEY_OTP_VERIFY, object.getOtp());

                        startActivity(intent);
                        finish();
                    }
                }
            }
            else if(result == 0 && message != null){
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });




        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code1 = edtCode1.getText().toString().trim();
                String code2 = edtCode2.getText().toString().trim();
                String code3 = edtCode3.getText().toString().trim();
                String code4 = edtCode4.getText().toString().trim();
                String code5 = edtCode5.getText().toString().trim();
                String code6 = edtCode6.getText().toString().trim();

                if(code1.isEmpty() || code2.isEmpty() ||
                        code3.isEmpty() || code4.isEmpty() ||
                        code5.isEmpty() || code6.isEmpty())  {
                    CustomToast.toastCenterTransparent(view.getContext(), "Please enter otp code!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else {
                    String otpString = code1.concat(code2).concat(code3).concat(code4).concat(code5).concat(code6);

                    if(type != null && type.equals(App.VALUE_FORGOT_PASSWORD)) {
                        ValidateTokenForgotRequest validateTokenForgotRequest = new ValidateTokenForgotRequest(Integer.parseInt(otpString), emailSend);
                        otpViewModel.postValidateForgotOTP(validateTokenForgotRequest);
                    }
                    else {
                        OTPRequest otpRequest = new OTPRequest(Integer.parseInt(otpString));
                        String accessToken = "Bearer ".concat(localStorage.getKeyAccessToken());
                        otpViewModel.postValidateOTP(accessToken, otpRequest);
                    }

                }

            }
        });

        tvNewCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accessToken = "Bearer ".concat(localStorage.getKeyAccessToken());
                otpViewModel.getGenerateOTP(accessToken);

                new CountDownTimer(60000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        tvNewCode.setClickable(false);
                        tvNewCode.setText("Nhận mã mới sau " + millisUntilFinished / 1000 + "s");
                    }

                    public void onFinish() {
                        tvNewCode.setClickable(true);
                        tvNewCode.setText("Nhận mã mới");
                    }

                }.start();
            }
        });

        edtCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()) {
                    edtCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()) {
                    edtCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()) {
                    edtCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()) {
                    edtCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()) {
                    edtCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setControls() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString(App.KEY_EDIT_PROFILE);
            emailSend = bundle.getString(App.KEY_FORGOT_PASSWORD);
        }

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Xác minh bảo mật");



        edtCode1 = findViewById(R.id.otp_code_1);
        edtCode2 = findViewById(R.id.otp_code_2);
        edtCode3 = findViewById(R.id.otp_code_3);
        edtCode4 = findViewById(R.id.otp_code_4);
        edtCode5 = findViewById(R.id.otp_code_5);
        edtCode6 = findViewById(R.id.otp_code_6);
        tvNewCode = findViewById(R.id.tv_new_otp);
        tvEmail = findViewById(R.id.tv_email);
        btnVerify = findViewById(R.id.btn_verify);

        loadingDialog = new LoadingDialog(this);
        otpViewModel = new ViewModelProvider(this).get(OTPViewModel.class);
        localStorage = new LocalStorage(App.getContext());
        edtCode1.requestFocus();

        if(emailSend != null) {
            tvEmail.setText(emailSend);
        }
        else {
            if(App.checkUserLogged()) {
                String userString = localStorage.getUserLogin();

                Gson gson = new Gson();
                User user = gson.fromJson(userString, User.class);
                tvEmail.setText(user.getEmail());
            }
        }


        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvNewCode.setClickable(false);
                tvNewCode.setText("Nhận mã mới sau " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                tvNewCode.setClickable(true);
                tvNewCode.setText("Nhận mã mới");
            }

        }.start();

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
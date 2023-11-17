package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luv2code.shopme.App;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.OTPViewModel;
import com.luv2code.shopme.ViewModel.UserViewModel;

public class ChangeInfoUserActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView img;
    private TextView tvTitle;
    private AppCompatButton btnVerify;
    private EditText edtText;
    private String type;
    private OTPViewModel otpViewModel;
    private LocalStorage localStorage;
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info_user);
        setControls();
        setEvents();
    }

    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        userViewModel.getChangeEmailData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1) {
                Gson gson = new Gson();
                String userString = gson.toJson(object.getData());
                localStorage.createUserLoginSession(userString, object.getAccessToken(), object.getRefreshToken());

                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_LONG, CustomToast.SUCCESS).show();
                startActivity(new Intent(this, EditProfileActivity.class));
                finish();
            } else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });

        userViewModel.getChangePhoneData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1) {
                Gson gson = new Gson();
                String userString = gson.toJson(object.getData());
                localStorage.setUser(userString);

                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_LONG, CustomToast.SUCCESS).show();
                startActivity(new Intent(this, EditProfileActivity.class));
                finish();
            } else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type != null) {

                    if(type.equals(App.VALUE_EDIT_EMAIL)) {

                        String newEmail = edtText.getText().toString().trim();
                        if(newEmail.isEmpty()) {
                            CustomToast.toastCenterTransparent(view.getContext(), "Please enter new email address!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                        }
                        else {
                            userViewModel.changeEmail(App.getAuthorization(), newEmail);
                        }

                    }
                    else if(type.equals(App.VALUE_EDIT_PHONE)) {
                        String newPhone = edtText.getText().toString().trim();
                        if(newPhone.isEmpty()) {
                            CustomToast.toastCenterTransparent(view.getContext(), "Please enter new phone number!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                        }
                        else {
                            userViewModel.changePhone(App.getAuthorization(), newPhone);
                        }
                    }
                    else {
                        Intent intent = new Intent(view.getContext(), EditProfileActivity.class);
                        startActivity(intent);
                    }
                }
                else {
                    Intent intent = new Intent(view.getContext(), EditProfileActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void setControls() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString(App.KEY_EDIT_PROFILE);
        }
        toolbar = findViewById(R.id.toolbar);
        img = findViewById(R.id.img_avt);
        tvTitle = findViewById(R.id.tv_title);
        btnVerify = findViewById(R.id.btn_verify);
        edtText = findViewById(R.id.edt_field);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        otpViewModel = new ViewModelProvider(this).get(OTPViewModel.class);
        localStorage = new LocalStorage(App.getContext());

        if(type != null) {
            if(type.equals(App.VALUE_EDIT_EMAIL)) {
                toolbar.setTitle(getString(R.string.change_email));
                tvTitle.setText(getString(R.string.enter_new_email));
                edtText.setHint(getString(R.string.enter_email));
                edtText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            }
            else if(type.equals(App.VALUE_EDIT_PHONE)) {
                toolbar.setTitle(getString(R.string.change_phone));
                tvTitle.setText(getString(R.string.enter_phone_new));
                edtText.setHint(getString(R.string.enter_phone_number));
                edtText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
            }
            else {
                Intent intent = new Intent(this, EditProfileActivity.class);
                startActivity(intent);
            }
        }
        else {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        }

    }
}
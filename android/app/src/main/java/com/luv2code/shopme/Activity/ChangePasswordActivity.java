package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.luv2code.shopme.App;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Request.ChangePasswordRequest;
import com.luv2code.shopme.Utils.CustomTextInputLayout;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.Utils.NameValidator;
import com.luv2code.shopme.ViewModel.UserViewModel;

public class ChangePasswordActivity extends AppCompatActivity {
    private Context context = this;
    private Toolbar toolbar;
    private UserViewModel userViewModel;
    private LocalStorage localStorage;
    private EditText edtOldPassword, edtNewPassword, edtConfirmNewPassword;
    private AppCompatButton btnSave;
    private CustomTextInputLayout layoutEdtOldPassword, layoutEdtNewPassword, layoutEdtConfirmNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setControls();
        setEvents();
    }

    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        edtOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = charSequence.toString().trim();

                if (password.isEmpty()) {
                    layoutEdtOldPassword.setError(getString(R.string.enter_old_password_field));
                }
                else if(password.length() < 6) {
                    layoutEdtOldPassword.setError(getString(R.string.old_password_than_6));
                }
                else {
                    layoutEdtOldPassword.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edtOldPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String oldPassword = edtOldPassword.getText().toString().trim();

                if(!b && oldPassword.isEmpty()) {
                    layoutEdtOldPassword.setError(getString(R.string.enter_old_password_field));
                }
            }
        });

        edtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String newPassword = charSequence.toString().trim();
                String oldPassword = edtOldPassword.getText().toString().trim();

                if (newPassword.isEmpty()) {
                    layoutEdtNewPassword.setError(getString(R.string.enter_new_password_field));
                }
                else if(newPassword.length() < 6) {
                    layoutEdtNewPassword.setError(getString(R.string.new_password_than_6));
                }
                else if(newPassword.equals(oldPassword)) {
                    layoutEdtNewPassword.setError(getString(R.string.new_and_old_different_password));
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
                String oldPassword = edtOldPassword.getText().toString().trim();

                if(!b && newPassword.isEmpty()) {
                    layoutEdtNewPassword.setError(getString(R.string.enter_new_password_field));
                }
                else if(!b && newPassword.equals(oldPassword)) {
                    layoutEdtNewPassword.setError(getString(R.string.new_and_old_different_password));
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
                    layoutEdtConfirmNewPassword.setError(getString(R.string.enter_confirm_password_field));
                }
                else if(!b && !confirmPassword.equals(newPassword)) {
                    layoutEdtConfirmNewPassword.setError(getString(R.string.confirm_same_new_password));
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String confirmPassword = edtConfirmNewPassword.getText().toString().trim();
                String newPassword = edtNewPassword.getText().toString().trim();
                String oldPassword = edtOldPassword.getText().toString().trim();

                if(oldPassword.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.enter_old_password_field), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(newPassword.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.enter_new_password_field), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(confirmPassword.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.enter_confirm_password_field), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(oldPassword.length() < 6) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.old_password_than_6), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(newPassword.length() < 6) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.new_password_than_6), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(confirmPassword.length() < 6) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.confirm_password_than_6), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(newPassword.equals(oldPassword)) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.new_and_old_different_password), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(!confirmPassword.equals(newPassword)) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.confirm_same_new_password), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else {
                    ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(oldPassword, newPassword, confirmPassword);
                    userViewModel.changePassword(App.getAuthorization(), changePasswordRequest);
                }
            }
        });

        userViewModel.getChangePasswordData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && message != null) {
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_LONG, CustomToast.SUCCESS).show();
                startActivity(new Intent(this, EditProfileActivity.class));
                finish();
            }
            else if(result == 0 && message != null) {
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }

        });
    }

    private void setControls() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.change_password));

        edtOldPassword = findViewById(R.id.edt_old_password);
        edtNewPassword = findViewById(R.id.edt_new_password);
        edtConfirmNewPassword = findViewById(R.id.edt_confirm_password);
        layoutEdtOldPassword = findViewById(R.id.layout_old_password);
        layoutEdtNewPassword = findViewById(R.id.layout_new_password);
        layoutEdtConfirmNewPassword = findViewById(R.id.layout_confirm_new_password);
        btnSave = findViewById(R.id.btn_save);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        localStorage = new LocalStorage(App.getContext());
    }
}
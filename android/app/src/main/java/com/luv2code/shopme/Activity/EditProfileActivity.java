package com.luv2code.shopme.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.User;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LoadingDialog;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.Utils.RealPathUtil;
import com.luv2code.shopme.ViewModel.UserViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 10;
    private Context context = this;
    private ConstraintLayout layoutUpload, layoutPhone, layoutName, layoutEmail, layoutPassword;
    private ImageView imgIconAdd, btnCloseBottomSheet;
    private Dialog bottomSheetDialog;
    private TextView tvTitleBottomSheet, tvLabelBottomSheet, tvNameValue, tvPhoneValue, tvEmailValue;
    private EditText edtBottomSheet;
    private AppCompatButton btnSave;
    private Toolbar toolbar;
    private UserViewModel userViewModel;
    private LocalStorage localStorage;
    private CircleImageView imgPreview;
    private String username;
    private LoadingDialog loadingDialog;
    private Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setControls();
        setEvents();
    }

    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        layoutUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickRequestPermission();

            }
        });

        layoutPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), VerifyActivity.class);
                intent.putExtra(App.KEY_EDIT_PROFILE, App.VALUE_EDIT_PHONE);
                startActivity(intent);
            }
        });

        layoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBottomSheetDialog(username);
            }
        });


        layoutEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), VerifyActivity.class);
                intent.putExtra(App.KEY_EDIT_PROFILE, App.VALUE_EDIT_EMAIL);
                startActivity(intent);
            }
        });


        layoutPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.checkUserLogged()) {
                    Intent intent = new Intent(view.getContext(), ChangePasswordActivity.class);
                    startActivity(intent);
                }
            }
        });

        userViewModel.getProfileData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }
            int result = object.getResult();
            String message = object.getMsg();

            User user = object.getData();
            if (result == 1 && user != null) {
                updateProfileUser(user);
            }
            else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        userViewModel.getChangeNameData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();
            User user = object.getData();
            if (result == 1 &&  user != null) {
                Gson gson = new Gson();
                String userString = gson.toJson(user);
                localStorage.setUser(userString);
                updateProfileUser(user);
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_LONG, CustomToast.SUCCESS).show();

            } else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });

        userViewModel.getChangeAvatarData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();
            User user = object.getData();
            if (result == 1 &&  user != null) {
                Gson gson = new Gson();
                String userString = gson.toJson(user);
                localStorage.setUser(userString);
                updateProfileUser(user);
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_LONG, CustomToast.SUCCESS).show();

            } else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });


        userViewModel.isLoadingChangeName().observe(this, isLoading -> {

            if(isLoading) {
                loadingDialog.showLoading();
            }
            else {
                loadingDialog.dismissLoading();
            }
        });

        userViewModel.isLoadingChangeAvatar().observe(this, isLoading -> {

            if(isLoading) {
                loadingDialog.showLoading();
            }
            else {
                loadingDialog.dismissLoading();
            }
        });

    }

    private void setControls() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sửa hồ sơ");


        layoutUpload = findViewById(R.id.layout_upload_avatar);
        imgPreview = findViewById(R.id.img_preview);
        imgIconAdd = findViewById(R.id.img_icon_add);
        layoutPhone = findViewById(R.id.layout_change_phone);
        layoutPassword = findViewById(R.id.layout_change_password);
        layoutEmail = findViewById(R.id.layout_change_email);
        layoutName = findViewById(R.id.layout_change_name);
        tvNameValue = findViewById(R.id.tv_name_value);
        tvPhoneValue = findViewById(R.id.tv_phone_value);
        tvEmailValue = findViewById(R.id.tv_email_value);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        localStorage = new LocalStorage(App.getContext());
        loadingDialog = new LoadingDialog(this);
        getInfoUser();
    }

    private void getInfoUser() {
        String accessToken = "Bearer ".concat(localStorage.getKeyAccessToken());
        userViewModel.getProfile(accessToken);
    }

    private void updateProfileUser(User user) {
        if(user != null) {
            username  = user.getName();
            tvNameValue.setText(user.getName());
            tvEmailValue.setText(user.getEmail());
            tvPhoneValue.setText(user.getPhone());
            imgPreview.setVisibility(View.VISIBLE);
            imgIconAdd.setVisibility(View.GONE);
            Picasso.get().load(user.getAvatar()).into(imgPreview);
        }
    }

    private void clickRequestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }

        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
        else {
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, REQUEST_CODE);
        }
    }

    private void openBottomSheetDialog(String value) {
        bottomSheetDialog = new Dialog(this);
        bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_edit_profile);
        bottomSheetDialog.show();

        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        btnCloseBottomSheet = bottomSheetDialog.findViewById(R.id.btn_close_bottom_sheet);

        edtBottomSheet = bottomSheetDialog.findViewById(R.id.bottom_sheet_edt);
        btnSave = bottomSheetDialog.findViewById(R.id.bottom_sheet_btn_save);

        edtBottomSheet.setText(value);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = edtBottomSheet.getText().toString().trim();

                if(newName.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.enter_new_name), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else {
                    userViewModel.changeName(App.getAuthorization(), newName);
                    bottomSheetDialog.dismiss();
                }

            }
        });
        btnCloseBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
    }



    private void openGallery() {

        ImagePicker.Companion.with(this)
                .galleryOnly()
                .crop()//Crop image(Optional), Check Customization for more option
                .cropSquare()
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start(REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            if(data == null) {
                return;
            }

            Uri uri = data.getData();
            if(uri == null) {
                return;
            }

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgPreview.setImageBitmap(bitmap);
                String realPath = RealPathUtil.getRealPath(context, uri);
                File file = new File(realPath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData(App.KEY_AVATAR,file.getName(), requestBody);



                userViewModel.changeAvatar(App.getAuthorization(), body);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(REQUEST_CODE == requestCode) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
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

    @Override
    protected void onResume() {
//        getInfoUser();
        super.onResume();
    }
}
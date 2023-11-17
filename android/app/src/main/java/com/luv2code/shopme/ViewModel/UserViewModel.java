package com.luv2code.shopme.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luv2code.shopme.API.RestClient;
import com.luv2code.shopme.Model.User;
import com.luv2code.shopme.Request.ChangeEmailRequest;
import com.luv2code.shopme.Request.ChangeNameRequest;
import com.luv2code.shopme.Request.ChangePasswordRequest;
import com.luv2code.shopme.Request.ChangePhoneRequest;
import com.luv2code.shopme.Request.OrderRequest;
import com.luv2code.shopme.Response.APIError;
import com.luv2code.shopme.Response.BaseResponse;
import com.luv2code.shopme.Response.LoginResponse;
import com.luv2code.shopme.Response.ProductResponse;
import com.luv2code.shopme.Response.UserResponse;
import com.luv2code.shopme.Utils.ErrorUtils;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserResponse> getProfile = new MutableLiveData<>();
    private MutableLiveData<LoginResponse> changeEmail = new MutableLiveData<>();
    private MutableLiveData<UserResponse> changePhone = new MutableLiveData<>();
    private MutableLiveData<UserResponse> changeName = new MutableLiveData<>();

    private MutableLiveData<BaseResponse> changePassword = new MutableLiveData<>();
    private MutableLiveData<UserResponse> changeAvatar = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingChangeName = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingChangeAvatar = new MutableLiveData<>();


    public LiveData<Boolean> isLoadingChangeAvatar()
    {
        if (isLoadingChangeAvatar == null) {
            isLoadingChangeAvatar = new MutableLiveData<>();
        }
        return this.isLoadingChangeAvatar;
    }

    public LiveData<Boolean> isLoadingChangeName()
    {
        if (isLoadingChangeName == null) {
            isLoadingChangeName = new MutableLiveData<>();
        }
        return this.isLoadingChangeName;
    }

    public LiveData<UserResponse> getProfileData()
    {
        if (getProfile == null) {
            getProfile = new MutableLiveData<>();
        }
        return this.getProfile;
    }

    public LiveData<BaseResponse> getChangePasswordData()
    {
        if (changePassword == null) {
            changePassword = new MutableLiveData<>();
        }
        return this.changePassword;
    }

    public LiveData<LoginResponse> getChangeEmailData()
    {
        if (changeEmail == null) {
            changeEmail = new MutableLiveData<>();
        }
        return this.changeEmail;
    }

    public LiveData<UserResponse> getChangePhoneData()
    {
        if (changePhone == null) {
            changePhone = new MutableLiveData<>();
        }
        return this.changePhone;
    }

    public LiveData<UserResponse> getChangeAvatarData()
    {
        if (changeAvatar == null) {
            changeAvatar = new MutableLiveData<>();
        }
        return this.changeAvatar;
    }

    public LiveData<UserResponse> getChangeNameData()
    {
        if (changeName == null) {
            changeName = new MutableLiveData<>();
        }
        return this.changeName;
    }

    public void getProfile(String authorization) {

        /*Step 1*/
        Call<UserResponse> call = RestClient.getRestService().getProfile(authorization);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {

                if(response.isSuccessful()){
                    UserResponse userResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(userResponse == null) {
                        getProfile.setValue(null);
                    }
                    else {
                        getProfile.setValue(userResponse);
                    }
                }
                else {
                    getProfile.setValue(null);
                }


            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                getProfile.setValue(null);
            }
        });
    }

    public void changeEmail(String authorization, String newEmail) {


        ChangeEmailRequest changeEmailRequest = new ChangeEmailRequest(newEmail);

        /*Step 1*/
        Call<LoginResponse> call = RestClient.getRestService().changeEmail(authorization, changeEmailRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {

                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(loginResponse == null) {
                        changeEmail.setValue(null);
                    }
                    else {
                        changeEmail.setValue(loginResponse);
                    }
                }
                else {
                    changeEmail.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                changeEmail.setValue(null);
            }
        });
    }

    public void changePhone(String authorization, String newPhone) {


        ChangePhoneRequest changePhoneRequest = new ChangePhoneRequest(newPhone);

        /*Step 1*/
        Call<UserResponse> call = RestClient.getRestService().changePhone(authorization, changePhoneRequest);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {

                if(response.isSuccessful()){
                    UserResponse userResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(userResponse == null) {
                        changePhone.setValue(null);
                    }
                    else {
                        changePhone.setValue(userResponse);
                    }
                }
                else {
                    changePhone.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                changePhone.setValue(null);
            }
        });
    }

    public void changeName(String authorization, String newName) {

        isLoadingChangeName.setValue(true);
        ChangeNameRequest changeNameRequest = new ChangeNameRequest(newName);

        /*Step 1*/
        Call<UserResponse> call = RestClient.getRestService().changeName(authorization, changeNameRequest);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {

                if(response.isSuccessful()){
                    UserResponse userResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(userResponse == null) {
                        changeName.setValue(null);
                    }
                    else {
                        changeName.setValue(userResponse);
                    }
                }
                else {
                    changeName.setValue(null);
                }
                isLoadingChangeName.setValue(false);


            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                changeName.setValue(null);
                isLoadingChangeName.setValue(false);
            }
        });
    }

    public void changeAvatar(String authorization, MultipartBody.Part file) {

        isLoadingChangeAvatar.setValue(true);

        /*Step 1*/
        Call<UserResponse> call = RestClient.getRestService().changeAvatar(authorization, file);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {

                if(response.isSuccessful()){
                    UserResponse userResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(userResponse == null) {
                        changeAvatar.setValue(null);
                    }
                    else {
                        changeAvatar.setValue(userResponse);
                    }
                }
                else {
                    changeAvatar.setValue(null);
                }
                isLoadingChangeAvatar.setValue(false);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                changeAvatar.setValue(null);
                isLoadingChangeAvatar.setValue(false);
            }
        });
    }

    public void changePassword(String authorization, ChangePasswordRequest changePasswordRequest) {

        /*Step 1*/
        Call<BaseResponse> call = RestClient.getRestService().changePassword(authorization, changePasswordRequest);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {

                BaseResponse baseResponse = new BaseResponse();
                if(response.isSuccessful()){
                    baseResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(baseResponse == null) {
                        changePassword.setValue(null);
                    }
                    else {
                        changePassword.setValue(baseResponse);
                    }

                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                    baseResponse.setMsg(error.getMsg());
                    baseResponse.setCode(error.getCode());
                    baseResponse.setStatus(error.getStatus());
                    baseResponse.setTimestamp(error.getTimestamp());
                    baseResponse.setResult(error.getResult());
                    changePassword.setValue(baseResponse);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                changePassword.setValue(null);
            }
        });
    }



}

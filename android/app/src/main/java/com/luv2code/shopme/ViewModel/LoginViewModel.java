package com.luv2code.shopme.ViewModel;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luv2code.shopme.API.RestClient;
import com.luv2code.shopme.Request.SignupRequest;
import com.luv2code.shopme.Response.APIError;
import com.luv2code.shopme.Request.LoginRequest;
import com.luv2code.shopme.Response.LoginResponse;
import com.luv2code.shopme.Utils.ErrorUtils;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResponse> login = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingLogin = new MutableLiveData<>();
    private MutableLiveData<LoginResponse> signup = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingSignup = new MutableLiveData<>();

    public LiveData<LoginResponse> getSignupData()
    {
        if (signup == null) {
            signup = new MutableLiveData<>();
        }
        return this.signup;
    }


    public LiveData<Boolean> isLoadingSignup() {
        if (isLoadingSignup == null) {
            isLoadingSignup = new MutableLiveData<>();
        }
        return isLoadingSignup;
    }

    public LiveData<LoginResponse> getLoginData()
    {
        if (login == null) {
            login = new MutableLiveData<>();
        }
        return this.login;
    }


    public LiveData<Boolean> isLoadingLogin() {
        if (isLoadingLogin == null) {
            isLoadingLogin = new MutableLiveData<>();
        }
        return isLoadingLogin;
    }

    public void postLogin(LoginRequest loginRequest)
    {

        isLoadingLogin.setValue(true);

        Call<LoginResponse> call = RestClient.getRestService().login(loginRequest);


        /*Step 2*/
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                isLoadingLogin.setValue(false);
                LoginResponse userResult = new LoginResponse();

                if(response.isSuccessful()){
                    userResult = response.body();
                    Log.d("Response :=>", response.body() + "");

                    assert userResult != null;

                    Log.d("Response :=>", userResult.toString() + "");

                    login.setValue(userResult);
                }
                else if (response.code() == 401) {
                    userResult.setMsg("Tên đăng nhập hoặc mật khẩu của bạn không chính xác!");
                    userResult.setResult(0);
                    login.setValue(userResult);
                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                    userResult.setMsg(error.getMsg());
                    userResult.setCode(error.getCode());
                    userResult.setStatus(error.getStatus());
                    userResult.setTimestamp(error.getTimestamp());
                    userResult.setResult(error.getResult());
                    login.setValue(userResult);
                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                isLoadingLogin.setValue(false);
                login.setValue(null);
            }
        });

    }



    public void postRegister(SignupRequest signupRequest)
    {

        isLoadingSignup.setValue(true);
        Call<LoginResponse> call = RestClient.getRestService().register(signupRequest);


        /*Step 2*/
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                isLoadingSignup.setValue(false);
                LoginResponse userResult = new LoginResponse();

                if(response.isSuccessful()){
                    userResult = response.body();
                    Log.d("Response :=>", response.body() + "");

                    assert userResult != null;

                    Log.d("Response :=>", userResult.toString() + "");

                    signup.setValue(userResult);
                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                    userResult.setMsg(error.getMsg());
                    userResult.setCode(error.getCode());
                    userResult.setStatus(error.getStatus());
                    userResult.setTimestamp(error.getTimestamp());
                    userResult.setResult(error.getResult());
                    signup.setValue(userResult);
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("Error==> ", t.getMessage());
                isLoadingSignup.setValue(false);
                signup.setValue(null);
            }
        });

    }
}

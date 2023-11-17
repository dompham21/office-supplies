package com.luv2code.shopme.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luv2code.shopme.API.RestClient;
import com.luv2code.shopme.Request.GenerateTokenForgotPassword;
import com.luv2code.shopme.Request.OTPRequest;
import com.luv2code.shopme.Request.OrderRequest;
import com.luv2code.shopme.Request.ValidateTokenForgotRequest;
import com.luv2code.shopme.Request.VerifyTokenNewPasswordRequest;
import com.luv2code.shopme.Response.APIError;
import com.luv2code.shopme.Response.BaseResponse;
import com.luv2code.shopme.Response.ListCategoryResponse;
import com.luv2code.shopme.Response.ListProductResponse;
import com.luv2code.shopme.Response.OtpResponse;
import com.luv2code.shopme.Utils.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPViewModel extends ViewModel {
    private MutableLiveData<BaseResponse> generateOTP = new MutableLiveData<>();
    private MutableLiveData<BaseResponse> validateOTP = new MutableLiveData<>();

    private MutableLiveData<BaseResponse> generateForgotOTP = new MutableLiveData<>();
    private MutableLiveData<OtpResponse> validateForgotOTP = new MutableLiveData<>();
    private MutableLiveData<BaseResponse> forgotPassword = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoadingValidateOTP = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingGenerateOTP = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoadingValidateForgotOTP = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingGenerateForgotOTP = new MutableLiveData<>();


    public LiveData<BaseResponse> getForgotPassword()
    {
        if (forgotPassword == null) {
            forgotPassword = new MutableLiveData<>();
        }
        return this.forgotPassword;
    }


    public LiveData<BaseResponse> getGenerateOTPData()
    {
        if (generateOTP == null) {
            generateOTP = new MutableLiveData<>();
        }
        return this.generateOTP;
    }

    public LiveData<BaseResponse> getGenerateForgotOTPData()
    {
        if (generateForgotOTP == null) {
            generateForgotOTP = new MutableLiveData<>();
        }
        return this.generateForgotOTP;
    }

    public LiveData<OtpResponse> getValidateForgotOTPData()
    {
        if (validateForgotOTP == null) {
            validateForgotOTP = new MutableLiveData<>();
        }
        return this.validateForgotOTP;
    }

    public LiveData<BaseResponse> getValidateOTPData()
    {
        if (validateOTP == null) {
            validateOTP = new MutableLiveData<>();
        }
        return this.validateOTP;
    }

    public LiveData<Boolean> isLoadingGenerateOTP() {
        if (isLoadingGenerateOTP == null) {
            isLoadingGenerateOTP = new MutableLiveData<>();
        }
        return isLoadingGenerateOTP;
    }


    public LiveData<Boolean> isLoadingValidateOTP() {
        if (isLoadingValidateOTP == null) {
            isLoadingValidateOTP = new MutableLiveData<>();
        }
        return isLoadingValidateOTP;
    }

    public LiveData<Boolean> isLoadingValidateForgotOTP() {
        if (isLoadingValidateForgotOTP == null) {
            isLoadingValidateForgotOTP = new MutableLiveData<>();
        }
        return isLoadingValidateForgotOTP;
    }

    public LiveData<Boolean> isLoadingGenerateForgotOTP() {
        if (isLoadingGenerateForgotOTP == null) {
            isLoadingGenerateForgotOTP = new MutableLiveData<>();
        }
        return isLoadingGenerateForgotOTP;
    }

    public void getGenerateOTP(String authorization) {

        isLoadingGenerateOTP.setValue(true);
        /*Step 1*/
        Call<BaseResponse> call = RestClient.getRestService().generateOTP(authorization);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {

                if(response.isSuccessful()){
                    BaseResponse baseResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(baseResponse == null) {
                        generateOTP.setValue(null);
                        isLoadingGenerateOTP.setValue(false);
                    }
                    else {
                        generateOTP.setValue(baseResponse);
                        isLoadingGenerateOTP.setValue(false);
                    }
                }
                else {
                    generateOTP.setValue(null);
                    isLoadingGenerateOTP.setValue(false);
                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                generateOTP.setValue(null);
                isLoadingGenerateOTP.setValue(false);

            }
        });
    }


    public void postValidateOTP(String authorization, OTPRequest otpRequest) {

        isLoadingValidateOTP.setValue(true);
        /*Step 1*/
        Call<BaseResponse> call = RestClient.getRestService().validateOTP(authorization, otpRequest);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                BaseResponse baseResponse = new BaseResponse();

                if(response.isSuccessful()){
                    baseResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(baseResponse == null) {
                        validateOTP.setValue(null);
                    }
                    else {
                        validateOTP.setValue(baseResponse);
                    }

                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                    baseResponse.setMsg(error.getMsg());
                    baseResponse.setCode(error.getCode());
                    baseResponse.setStatus(error.getStatus());
                    baseResponse.setTimestamp(error.getTimestamp());
                    baseResponse.setResult(error.getResult());
                    validateOTP.setValue(baseResponse);

                }
                isLoadingValidateOTP.setValue(false);


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");
                validateOTP.setValue(null);
                isLoadingValidateOTP.setValue(false);

            }
        });
    }

    public void postGenerateForgotOTP(GenerateTokenForgotPassword generateTokenForgotPassword) {

        isLoadingGenerateForgotOTP.setValue(true);
        /*Step 1*/
        Call<BaseResponse> call = RestClient.getRestService().generateForgotOTP(generateTokenForgotPassword);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {

                if(response.isSuccessful()){
                    BaseResponse baseResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(baseResponse == null) {
                        generateForgotOTP.setValue(null);
                        isLoadingGenerateForgotOTP.setValue(false);
                    }
                    else {
                        generateForgotOTP.setValue(baseResponse);
                        isLoadingGenerateForgotOTP.setValue(false);
                    }
                }
                else {
                    generateForgotOTP.setValue(null);
                    isLoadingGenerateForgotOTP.setValue(false);
                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                generateForgotOTP.setValue(null);
                isLoadingGenerateForgotOTP.setValue(false);
            }
        });
    }

    public void postValidateForgotOTP(ValidateTokenForgotRequest validateTokenForgotRequest) {

        isLoadingValidateForgotOTP.setValue(true);
        /*Step 1*/
        Call<OtpResponse> call = RestClient.getRestService().validateForgotOTP(validateTokenForgotRequest);

        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(@NonNull Call<OtpResponse> call, @NonNull Response<OtpResponse> response) {

                if(response.isSuccessful()){
                    OtpResponse otpResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(otpResponse == null) {
                        validateForgotOTP.setValue(null);
                        isLoadingValidateForgotOTP.setValue(false);
                    }
                    else {
                        validateForgotOTP.setValue(otpResponse);
                        isLoadingValidateForgotOTP.setValue(false);
                    }
                }
                else {
                    validateForgotOTP.setValue(null);
                    isLoadingValidateForgotOTP.setValue(false);
                }


            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                generateForgotOTP.setValue(null);
                isLoadingValidateForgotOTP.setValue(false);
            }
        });
    }

    public void postVerifyTokenNewPassword(VerifyTokenNewPasswordRequest verifyTokenNewPasswordRequest) {

        /*Step 1*/
        Call<BaseResponse> call = RestClient.getRestService().verifyTokenNewPassword(verifyTokenNewPasswordRequest);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {

                if(response.isSuccessful()){
                    BaseResponse baseResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(baseResponse == null) {
                        forgotPassword.setValue(null);
                    }
                    else {
                        forgotPassword.setValue(baseResponse);
                    }
                }
                else {
                    forgotPassword.setValue(null);
                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                forgotPassword.setValue(null);
            }
        });
    }


}

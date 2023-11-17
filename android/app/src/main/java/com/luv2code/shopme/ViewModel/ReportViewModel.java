package com.luv2code.shopme.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luv2code.shopme.API.RestClient;
import com.luv2code.shopme.Request.GenerateTokenForgotPassword;
import com.luv2code.shopme.Request.OTPRequest;
import com.luv2code.shopme.Request.ValidateTokenForgotRequest;
import com.luv2code.shopme.Request.VerifyTokenNewPasswordRequest;
import com.luv2code.shopme.Response.APIError;
import com.luv2code.shopme.Response.BaseResponse;
import com.luv2code.shopme.Response.BuyHistoryResponse;
import com.luv2code.shopme.Response.OtpResponse;
import com.luv2code.shopme.Utils.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportViewModel extends ViewModel {
    private MutableLiveData<BuyHistoryResponse> buyHistory = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoadingBuyHistory = new MutableLiveData<>();


    public LiveData<BuyHistoryResponse> getBuyHistoryData()
    {
        if (buyHistory == null) {
            buyHistory = new MutableLiveData<>();
        }
        return this.buyHistory;
    }


    public LiveData<Boolean> isLoadingBuyHistory() {
        if (isLoadingBuyHistory == null) {
            isLoadingBuyHistory = new MutableLiveData<>();
        }
        return isLoadingBuyHistory;
    }

    public void getBuyHistory(String authorization, Integer type) {

        isLoadingBuyHistory.setValue(true);
        /*Step 1*/
        Call<BuyHistoryResponse> call = RestClient.getRestService().getBuyHistory(authorization, type);

        call.enqueue(new Callback<BuyHistoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<BuyHistoryResponse> call, @NonNull Response<BuyHistoryResponse> response) {

                if(response.isSuccessful()){
                    BuyHistoryResponse baseResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(baseResponse == null) {
                        buyHistory.setValue(null);
                        isLoadingBuyHistory.setValue(false);
                    }
                    else {
                        buyHistory.setValue(baseResponse);
                        isLoadingBuyHistory.setValue(false);
                    }
                }
                else {
                    buyHistory.setValue(null);
                    isLoadingBuyHistory.setValue(false);
                }


            }

            @Override
            public void onFailure(Call<BuyHistoryResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                buyHistory.setValue(null);
                isLoadingBuyHistory.setValue(false);

            }
        });
    }




}

package com.luv2code.shopme.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luv2code.shopme.API.RestClient;
import com.luv2code.shopme.Model.OrderStatus;
import com.luv2code.shopme.Response.BaseResponse;
import com.luv2code.shopme.Response.ListAddressResponse;
import com.luv2code.shopme.Response.ListOrderStatusResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatusViewModel extends ViewModel {
    private MutableLiveData<ListOrderStatusResponse> listOrderStatus = new MutableLiveData<>();

    public LiveData<ListOrderStatusResponse> getListOrderStatusData()
    {
        if (listOrderStatus == null) {
            listOrderStatus = new MutableLiveData<>();
        }
        return this.listOrderStatus;
    }

    public void getListOrderStatus(String authorization) {

        /*Step 1*/
        Call<ListOrderStatusResponse> call = RestClient.getRestService().getListOrderStatus(authorization);

        call.enqueue(new Callback<ListOrderStatusResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListOrderStatusResponse> call, @NonNull Response<ListOrderStatusResponse> response) {

                if(response.isSuccessful()){
                    ListOrderStatusResponse listOrderStatusResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listOrderStatusResponse == null) {
                        listOrderStatus.setValue(null);
                    }
                    else {
                        listOrderStatus.setValue(listOrderStatusResponse);
                    }
                }
                else {
                    listOrderStatus.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ListOrderStatusResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                listOrderStatus.setValue(null);
            }
        });
    }

}

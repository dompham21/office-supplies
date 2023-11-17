package com.luv2code.shopme.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luv2code.shopme.API.RestClient;
import com.luv2code.shopme.Response.BaseResponse;
import com.luv2code.shopme.Response.CartResponse;
import com.luv2code.shopme.Response.ListCartResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ListCartResponse> listCart = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingCart = new MutableLiveData<>();



    public LiveData<ListCartResponse> getListCartData()
    {
        if (listCart == null) {
            listCart = new MutableLiveData<>();
        }
        return this.listCart;
    }

    public LiveData<Boolean> isLoadingCart() {
        if (isLoadingCart == null) {
            isLoadingCart = new MutableLiveData<>();
        }
        return isLoadingCart;
    }




    public void getListCart(String authorization) {
        isLoadingCart.setValue(true);
        /*Step 1*/
        Call<ListCartResponse> call = RestClient.getRestService().getListCart(authorization);

        call.enqueue(new Callback<ListCartResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListCartResponse> call, @NonNull Response<ListCartResponse> response) {
                if(response.isSuccessful()){
                    ListCartResponse listCartResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listCartResponse == null) {
                        listCart.setValue(null);
                        isLoadingCart.setValue(true);
                    }
                    else {
                        listCart.setValue(listCartResponse);
                        isLoadingCart.setValue(false);

                    }
                }
                else {
                    listCart.setValue(null);
                    isLoadingCart.setValue(true);
                }

            }

            @Override
            public void onFailure(Call<ListCartResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                listCart.setValue(null);
                isLoadingCart.setValue(false);

            }
        });
    }


}

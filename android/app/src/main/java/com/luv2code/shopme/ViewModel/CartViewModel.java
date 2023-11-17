package com.luv2code.shopme.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luv2code.shopme.API.RestClient;
import com.luv2code.shopme.Model.Cart;
import com.luv2code.shopme.Request.CartRequest;
import com.luv2code.shopme.Response.BaseResponse;
import com.luv2code.shopme.Response.CartResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {
    private MutableLiveData<CartResponse> addToCart = new MutableLiveData<>();
    private MutableLiveData<CartResponse> updateCart = new MutableLiveData<>();
    private MutableLiveData<CartResponse> deleteCart = new MutableLiveData<>();

    public LiveData<CartResponse> getAddToCartData()
    {
        if (addToCart == null) {
            addToCart = new MutableLiveData<>();
        }
        return this.addToCart;
    }

    public LiveData<CartResponse> getUpdateCartData()
    {
        if (updateCart == null) {
            updateCart = new MutableLiveData<>();
        }
        return this.updateCart;
    }

    public LiveData<CartResponse> getDeleteCartData()
    {
        if (deleteCart == null) {
            deleteCart = new MutableLiveData<>();
        }
        return this.deleteCart;
    }

    public void addToCart(String authorization, Integer productId) {

        CartRequest cartRequest = new CartRequest(productId, 1);
        /*Step 1*/
        Call<CartResponse> call = RestClient.getRestService().addToCart(authorization, cartRequest);

        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(@NonNull Call<CartResponse> call, @NonNull Response<CartResponse> response) {
                CartResponse cartResponse = response.body();
                addToCart.setValue(cartResponse);

            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                addToCart.setValue(null);
            }
        });
    }

    public void updateCart(String authorization, Integer productId, Integer quantity) {

        /*Step 1*/
        CartRequest cartRequest = new CartRequest(productId, quantity);
        Call<CartResponse> call = RestClient.getRestService().updateCart(authorization, cartRequest);

        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(@NonNull Call<CartResponse> call, @NonNull Response<CartResponse> response) {
                CartResponse cartResponse = response.body();
                updateCart.setValue(cartResponse);

            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                updateCart.setValue(null);
            }
        });
    }

    public void deleteCart(String authorization, Integer id) {

        /*Step 1*/
        Call<CartResponse> call = RestClient.getRestService().deleteCart(authorization, id);

        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(@NonNull Call<CartResponse> call, @NonNull Response<CartResponse> response) {
                CartResponse cartResponse = response.body();
                deleteCart.setValue(cartResponse);
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                deleteCart.setValue(null);
            }
        });
    }
}

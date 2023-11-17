package com.luv2code.shopme.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luv2code.shopme.API.RestClient;
import com.luv2code.shopme.Model.Order;
import com.luv2code.shopme.Request.AddressRequest;
import com.luv2code.shopme.Request.OrderRequest;
import com.luv2code.shopme.Response.APIError;
import com.luv2code.shopme.Response.AddressResponse;
import com.luv2code.shopme.Response.BaseResponse;
import com.luv2code.shopme.Response.ListAddressResponse;
import com.luv2code.shopme.Response.ListOrderResponse;
import com.luv2code.shopme.Response.OrderResponse;
import com.luv2code.shopme.Utils.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewModel extends ViewModel {
    private MutableLiveData<BaseResponse> addNewOrder = new MutableLiveData<>();
    private MutableLiveData<ListOrderResponse> listOrderByStatus = new MutableLiveData<>();
    private MutableLiveData<OrderResponse> orderDetail = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingOrder = new MutableLiveData<>();


    public LiveData<Boolean> isLoadingOrder()
    {
        if (isLoadingOrder == null) {
            isLoadingOrder = new MutableLiveData<>();
        }
        return this.isLoadingOrder;
    }

    public LiveData<BaseResponse> getAddNewOrder()
    {
        if (addNewOrder == null) {
            addNewOrder = new MutableLiveData<>();
        }
        return this.addNewOrder;
    }

    public LiveData<ListOrderResponse> getListOrderByStatusData()
    {
        if (listOrderByStatus == null) {
            listOrderByStatus = new MutableLiveData<>();
        }
        return this.listOrderByStatus;
    }

    public LiveData<OrderResponse> getOrderDetailData()
    {
        if (orderDetail == null) {
            orderDetail = new MutableLiveData<>();
        }
        return this.orderDetail;
    }

    public void addNewOrder(String authorization, OrderRequest orderRequest) {

        /*Step 1*/
        Call<BaseResponse> call = RestClient.getRestService().addOrder(authorization, orderRequest);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                BaseResponse baseResponse = new BaseResponse();

                if(response.isSuccessful()){
                    baseResponse = response.body();
                    Log.d("Response :=>", response.body() + "");

                    if(baseResponse == null) {
                        addNewOrder.setValue(null);
                    }
                    else {
                        addNewOrder.setValue(baseResponse);
                    }
                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                    baseResponse.setMsg(error.getMsg());
                    baseResponse.setCode(error.getCode());
                    baseResponse.setStatus(error.getStatus());
                    baseResponse.setTimestamp(error.getTimestamp());
                    baseResponse.setResult(error.getResult());
                    addNewOrder.setValue(baseResponse);
                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                addNewOrder.setValue(null);
            }
        });
    }

    public void getListOrderByStatus(String authorization, Integer statusId) {
        isLoadingOrder.setValue(true);
        /*Step 1*/
        Call<ListOrderResponse> call = RestClient.getRestService().getOrderByStatus(authorization, statusId);

        call.enqueue(new Callback<ListOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListOrderResponse> call, @NonNull Response<ListOrderResponse> response) {

                ListOrderResponse listOrderResponse = new ListOrderResponse();
                if(response.isSuccessful()){
                    listOrderResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listOrderResponse == null) {
                        listOrderByStatus.setValue(null);
                    }
                    else {
                        listOrderByStatus.setValue(listOrderResponse);
                    }
                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                    listOrderResponse.setMsg(error.getMsg());
                    listOrderResponse.setCode(error.getCode());
                    listOrderResponse.setStatus(error.getStatus());
                    listOrderResponse.setTimestamp(error.getTimestamp());
                    listOrderResponse.setResult(error.getResult());
                    listOrderByStatus.setValue(listOrderResponse);
                }

                isLoadingOrder.setValue(false);

            }

            @Override
            public void onFailure(Call<ListOrderResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                listOrderByStatus.setValue(null);
                isLoadingOrder.setValue(false);
            }
        });
    }
    public void getOrderDetail(String authorization, Integer id) {

        /*Step 1*/
        Call<OrderResponse> call = RestClient.getRestService().getOrderDetail(authorization, id);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<OrderResponse> call, @NonNull Response<OrderResponse> response) {

                OrderResponse orderResponse = new OrderResponse();
                if(response.isSuccessful()){
                    orderResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(orderResponse == null) {
                        orderDetail.setValue(null);
                    }
                    else {
                        orderDetail.setValue(orderResponse);
                    }
                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                    orderResponse.setMsg(error.getMsg());
                    orderResponse.setCode(error.getCode());
                    orderResponse.setStatus(error.getStatus());
                    orderResponse.setTimestamp(error.getTimestamp());
                    orderResponse.setResult(error.getResult());
                    orderDetail.setValue(orderResponse);
                }


            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                orderDetail.setValue(null);
            }
        });
    }
}

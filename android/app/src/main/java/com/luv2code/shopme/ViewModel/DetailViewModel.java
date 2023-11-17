package com.luv2code.shopme.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luv2code.shopme.API.RestClient;
import com.luv2code.shopme.Response.ListReviewResponse;
import com.luv2code.shopme.Response.ProductResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<ProductResponse> detailProduct = new MutableLiveData<>();

    private MutableLiveData<ListReviewResponse> listReviews = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingReviews = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingDetailProduct = new MutableLiveData<>();

    public LiveData<ProductResponse> getDetailProductData()
    {
        if (detailProduct == null) {
            detailProduct = new MutableLiveData<>();
        }
        return this.detailProduct;
    }


    public LiveData<ListReviewResponse> getReviewsData()
    {
        if (listReviews == null) {
            listReviews = new MutableLiveData<>();
        }
        return this.listReviews;
    }

    public LiveData<Boolean> isLoadingDetailProduct() {
        if (isLoadingDetailProduct == null) {
            isLoadingDetailProduct = new MutableLiveData<>();
        }
        return isLoadingDetailProduct;
    }

    public LiveData<Boolean> isLoadingReviews() {
        if (isLoadingReviews == null) {
            isLoadingReviews = new MutableLiveData<>();
        }
        return isLoadingReviews;
    }



    public void getDetailProduct(Integer id) {
        isLoadingDetailProduct.setValue(true);
        /*Step 1*/
        Call<ProductResponse> call = RestClient.getRestService().getDetailProduct(id);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {

                if(response.isSuccessful()){
                    ProductResponse productResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(productResponse == null) {
                        detailProduct.setValue(null);
                        isLoadingDetailProduct.setValue(true);

                    }
                    else {
                        detailProduct.setValue(productResponse);
                        isLoadingDetailProduct.setValue(false);

                    }
                }
                else {
                    detailProduct.setValue(null);
                    isLoadingDetailProduct.setValue(true);
                }

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                isLoadingDetailProduct.setValue(true);
                detailProduct.setValue(null);
            }
        });
    }

    public void getReviews(Integer productId, Integer pageNum) {
        isLoadingReviews.setValue(true);
        /*Step 1*/
        Call<ListReviewResponse> call = RestClient.getRestService().getReviewsByProductId(productId, pageNum);

        call.enqueue(new Callback<ListReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListReviewResponse> call, @NonNull Response<ListReviewResponse> response) {

                if(response.isSuccessful()){
                    ListReviewResponse listReviewResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listReviewResponse == null) {
                        listReviews.setValue(null);
                        isLoadingReviews.setValue(true);

                    }
                    else {
                        listReviews.setValue(listReviewResponse);
                        isLoadingReviews.setValue(false);

                    }
                }
                else {
                    listReviews.setValue(null);
                    isLoadingReviews.setValue(true);
                }

            }

            @Override
            public void onFailure(Call<ListReviewResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                isLoadingReviews.setValue(true);
                listReviews.setValue(null);
            }
        });
    }

}

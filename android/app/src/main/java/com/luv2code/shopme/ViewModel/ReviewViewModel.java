package com.luv2code.shopme.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luv2code.shopme.API.RestClient;
import com.luv2code.shopme.Request.ReviewRequest;
import com.luv2code.shopme.Response.APIError;
import com.luv2code.shopme.Response.BaseResponse;
import com.luv2code.shopme.Response.ListReviewResponse;
import com.luv2code.shopme.Response.ReviewResponse;
import com.luv2code.shopme.Utils.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewViewModel extends ViewModel {
    private MutableLiveData<BaseResponse> addReview = new MutableLiveData<>();
    private MutableLiveData<BaseResponse> updateReview = new MutableLiveData<>();
    private MutableLiveData<ListReviewResponse> listReviewByUser = new MutableLiveData<>();
    private MutableLiveData<ReviewResponse> getDetailReview = new MutableLiveData<>();

    public LiveData<BaseResponse> getUpdateReviewData()
    {
        if (updateReview == null) {
            updateReview = new MutableLiveData<>();
        }
        return this.updateReview;
    }


    public LiveData<ReviewResponse> getDetailReviewData()
    {
        if (getDetailReview == null) {
            getDetailReview = new MutableLiveData<>();
        }
        return this.getDetailReview;
    }

    public LiveData<BaseResponse> getAddNewReviewData()
    {
        if (addReview == null) {
            addReview = new MutableLiveData<>();
        }
        return this.addReview;
    }

    public LiveData<ListReviewResponse> getListReviewByUserData()
    {
        if (listReviewByUser == null) {
            listReviewByUser = new MutableLiveData<>();
        }
        return this.listReviewByUser;
    }

    public void addNewReview(String authorization, ReviewRequest reviewRequest) {

        /*Step 1*/
        Call<BaseResponse> call = RestClient.getRestService().addReview(authorization, reviewRequest);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                BaseResponse baseResponse = new BaseResponse();
                if(response.isSuccessful()){
                    baseResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(baseResponse == null) {
                        addReview.setValue(null);
                    }
                    else {
                        addReview.setValue(baseResponse);
                    }
                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                    baseResponse.setMsg(error.getMsg());
                    baseResponse.setCode(error.getCode());
                    baseResponse.setStatus(error.getStatus());
                    baseResponse.setTimestamp(error.getTimestamp());
                    baseResponse.setResult(error.getResult());
                    addReview.setValue(baseResponse);
                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                addReview.setValue(null);
            }
        });
    }

    public void getListReviewUser(String authorization) {

        /*Step 1*/
        Call<ListReviewResponse> call = RestClient.getRestService().getListReviewByUser(authorization);

        call.enqueue(new Callback<ListReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListReviewResponse> call, @NonNull Response<ListReviewResponse> response) {
                ListReviewResponse listReviewResponse = new ListReviewResponse();
                if(response.isSuccessful()){
                    listReviewResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listReviewResponse == null) {
                        listReviewByUser.setValue(null);
                    }
                    else {
                        listReviewByUser.setValue(listReviewResponse);
                    }
                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                    listReviewResponse.setMsg(error.getMsg());
                    listReviewResponse.setCode(error.getCode());
                    listReviewResponse.setStatus(error.getStatus());
                    listReviewResponse.setTimestamp(error.getTimestamp());
                    listReviewResponse.setResult(error.getResult());
                    listReviewByUser.setValue(listReviewResponse);
                }


            }

            @Override
            public void onFailure(Call<ListReviewResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                listReviewByUser.setValue(null);
            }
        });
    }

    public void getDetailReview(String authorization, Integer id) {

        /*Step 1*/
        Call<ReviewResponse> call = RestClient.getRestService().getDetailReview(authorization, id);

        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {
                ReviewResponse reviewResponse = new ReviewResponse();
                if(response.isSuccessful()){
                    reviewResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(reviewResponse == null) {
                        getDetailReview.setValue(null);
                    }
                    else {
                        getDetailReview.setValue(reviewResponse);
                    }
                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                    reviewResponse.setMsg(error.getMsg());
                    reviewResponse.setCode(error.getCode());
                    reviewResponse.setStatus(error.getStatus());
                    reviewResponse.setTimestamp(error.getTimestamp());
                    reviewResponse.setResult(error.getResult());
                    getDetailReview.setValue(reviewResponse);
                }

            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");
                getDetailReview.setValue(null);
            }
        });
    }

    public void updateReview(String authorization, ReviewRequest reviewRequest, Integer id) {

        /*Step 1*/
        Call<BaseResponse> call = RestClient.getRestService().updateReview(authorization, reviewRequest, id);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                BaseResponse baseResponse = new BaseResponse();
                if(response.isSuccessful()){
                    baseResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(baseResponse == null) {
                        updateReview.setValue(null);
                    }
                    else {
                        updateReview.setValue(baseResponse);
                    }
                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                    baseResponse.setMsg(error.getMsg());
                    baseResponse.setCode(error.getCode());
                    baseResponse.setStatus(error.getStatus());
                    baseResponse.setTimestamp(error.getTimestamp());
                    baseResponse.setResult(error.getResult());
                    updateReview.setValue(baseResponse);
                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                updateReview.setValue(null);
            }
        });
    }
}

package com.luv2code.shopme.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luv2code.shopme.API.RestClient;
import com.luv2code.shopme.Response.ListCategoryResponse;
import com.luv2code.shopme.Response.ListPostersResponse;
import com.luv2code.shopme.Response.ListProductResponse;
import com.luv2code.shopme.Response.ListSuggestResponse;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<ListProductResponse> listSearch = new MutableLiveData<>();
    private MutableLiveData<ListSuggestResponse> listSuggest = new MutableLiveData<>();
    private MutableLiveData<ListCategoryResponse> listCategory = new MutableLiveData<>();

    public LiveData<ListProductResponse> getListSearchData()
    {
        if (listSearch == null) {
            listSearch = new MutableLiveData<>();
        }
        return this.listSearch;
    }

    public LiveData<ListCategoryResponse> getListCategoryData()
    {
        if (listCategory == null) {
            listCategory = new MutableLiveData<>();
        }
        return this.listCategory;
    }

    public LiveData<ListSuggestResponse> getListSuggestData()
    {
        if (listSuggest == null) {
            listSuggest = new MutableLiveData<>();
        }
        return this.listSuggest;
    }

    public void getSearch(Integer pageNo, Integer pageSize, Double priceMin, Double priceMax,
                          String sortField,String sortDirection, List<Integer> categoryIds, String keyword) {


        /*Step 1*/
        Call<ListProductResponse> call = RestClient.getRestService().getSearch(pageNo, pageSize, priceMin, priceMax, sortField, sortDirection, categoryIds, keyword);

        call.enqueue(new Callback<ListProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListProductResponse> call, @NonNull Response<ListProductResponse> response) {

                if(response.isSuccessful()){
                    ListProductResponse listProductResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listProductResponse == null) {
                        listSearch.setValue(null);
                    }
                    else {
                        listSearch.setValue(listProductResponse);

                    }
                }
                else {
                    listSearch.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<ListProductResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");
                listSearch.setValue(null);
            }
        });
    }

    public void getSuggest(String keyword) {
        /*Step 1*/
        Call<ListSuggestResponse> call = RestClient.getRestService().getSuggest(keyword);

        call.enqueue(new Callback<ListSuggestResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListSuggestResponse> call, @NonNull Response<ListSuggestResponse> response) {

                if(response.isSuccessful()){
                    ListSuggestResponse listSuggestResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listSuggestResponse == null) {
                        listSuggest.setValue(null);

                    }
                    else {
                        listSuggest.setValue(listSuggestResponse);

                    }
                }
                else {
                    listSuggest.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<ListSuggestResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                listSuggest.setValue(null);
            }
        });
    }

    public void getListCategory(Integer pageNo, Integer pageSize) {
        /*Step 1*/
        Call<ListCategoryResponse> call = RestClient.getRestService().getListCategoryByPage(pageNo, pageSize);

        call.enqueue(new Callback<ListCategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListCategoryResponse> call, @NonNull Response<ListCategoryResponse> response) {

                if(response.isSuccessful()){
                    ListCategoryResponse listCategoryResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listCategoryResponse == null) {
                        listCategory.setValue(null);
                    }
                    else {
                        listCategory.setValue(listCategoryResponse);
                    }
                }
                else {
                    listCategory.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<ListCategoryResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");
                listCategory.setValue(null);
            }
        });
    }
}

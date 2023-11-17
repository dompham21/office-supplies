package com.luv2code.shopme.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luv2code.shopme.API.RestClient;
import com.luv2code.shopme.Response.APIError;
import com.luv2code.shopme.Response.ListCategoryResponse;
import com.luv2code.shopme.Response.ListPostersResponse;
import com.luv2code.shopme.Response.ListProductResponse;
import com.luv2code.shopme.Utils.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<ListPostersResponse> listPosters = new MutableLiveData<>();
    private MutableLiveData<ListProductResponse> newProducts = new MutableLiveData<>();
    private MutableLiveData<ListProductResponse> promotionProducts = new MutableLiveData<>();

    private MutableLiveData<ListProductResponse> mostPurchasedProducts = new MutableLiveData<>();
    private MutableLiveData<ListProductResponse> allProducts = new MutableLiveData<>();
    private MutableLiveData<ListCategoryResponse> listCategories = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoadingNewProducts = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingPromotionProducts = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingMostPurchased = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingAllProducts = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingPosters = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingCategories = new MutableLiveData<>();

    public LiveData<ListCategoryResponse> getListCategoriesData()
    {
        if (listCategories == null) {
            listCategories = new MutableLiveData<>();
        }
        return this.listCategories;
    }

    public LiveData<ListPostersResponse> getPostersData()
    {
        if (listPosters == null) {
            listPosters = new MutableLiveData<>();
        }
        return this.listPosters;
    }

    public LiveData<ListProductResponse> getNewProductsData()
    {
        if (newProducts == null) {
            newProducts = new MutableLiveData<>();
        }
        return this.newProducts;
    }
    public LiveData<ListProductResponse> getPromotionProductsData()
    {
        if (promotionProducts == null) {
            promotionProducts = new MutableLiveData<>();
        }
        return this.promotionProducts;
    }

    public LiveData<ListProductResponse> getAllProductsData()
    {
        if (allProducts == null) {
            allProducts = new MutableLiveData<>();
        }
        return this.allProducts;
    }

    public LiveData<ListProductResponse> getMostPurchasedProductsData()

    {
        if (mostPurchasedProducts == null) {
            mostPurchasedProducts = new MutableLiveData<>();
        }
        return this.mostPurchasedProducts;
    }


    public LiveData<Boolean> isLoadingPosters() {
        if (isLoadingPosters == null) {
            isLoadingPosters = new MutableLiveData<>();
        }
        return isLoadingPosters;
    }

    public LiveData<Boolean> isLoadingAllProducts() {
        if (isLoadingAllProducts == null) {
            isLoadingAllProducts = new MutableLiveData<>();
        }
        return isLoadingAllProducts;
    }

    public LiveData<Boolean> isLoadingMostPurchased() {
        if (isLoadingMostPurchased == null) {
            isLoadingMostPurchased = new MutableLiveData<>();
        }
        return isLoadingMostPurchased;
    }

    public LiveData<Boolean> isLoadingNewProducts() {
        if (isLoadingNewProducts == null) {
            isLoadingNewProducts = new MutableLiveData<>();
        }
        return isLoadingNewProducts;
    }

    public LiveData<Boolean> isLoadingPromotionProducts() {
        if (isLoadingPromotionProducts == null) {
            isLoadingPromotionProducts = new MutableLiveData<>();
        }
        return isLoadingPromotionProducts;
    }

    public LiveData<Boolean> isLoadingCategories() {
        if (isLoadingCategories == null) {
            isLoadingCategories = new MutableLiveData<>();
        }
        return isLoadingCategories;
    }

    public void getListCategories() {
        isLoadingCategories.setValue(true);
        /*Step 1*/
        Call<ListCategoryResponse> call = RestClient.getRestService().getListCategories();

        call.enqueue(new Callback<ListCategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListCategoryResponse> call, @NonNull Response<ListCategoryResponse> response) {

                if(response.isSuccessful()){
                    ListCategoryResponse listCategoryResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listCategoryResponse == null) {
                        listCategories.setValue(null);
                        isLoadingCategories.setValue(true);

                    }
                    else {
                        listCategories.setValue(listCategoryResponse);
                        isLoadingCategories.setValue(false);

                    }
                }
                else {
                    listCategories.setValue(null);
                    isLoadingCategories.setValue(true);
                }

            }

            @Override
            public void onFailure(Call<ListCategoryResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                isLoadingCategories.setValue(true);
                listCategories.setValue(null);
            }
        });
    }

    public void getPosters() {
        isLoadingPosters.setValue(true);
        /*Step 1*/
        Call<ListPostersResponse> call = RestClient.getRestService().getListsPoster();

        call.enqueue(new Callback<ListPostersResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListPostersResponse> call, @NonNull Response<ListPostersResponse> response) {

                if(response.isSuccessful()){
                    ListPostersResponse listPostersResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listPostersResponse == null) {
                        listPosters.setValue(null);
                        isLoadingPosters.setValue(true);

                    }
                    else {
                        listPosters.setValue(listPostersResponse);
                        isLoadingPosters.setValue(false);

                    }
                }
                else {
                    listPosters.setValue(null);
                    isLoadingPosters.setValue(true);
                }

            }

            @Override
            public void onFailure(Call<ListPostersResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                isLoadingPosters.setValue(true);
                listPosters.setValue(null);
            }
        });
    }

    public void getNewProducts(int pageSize) {
        isLoadingNewProducts.setValue(true);
        /*Step 1*/
        Call<ListProductResponse> call = RestClient.getRestService().getListProducts(1, pageSize, "id", "DESC", null);

        call.enqueue(new Callback<ListProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListProductResponse> call, @NonNull Response<ListProductResponse> response) {

                if(response.isSuccessful()){
                    ListProductResponse listProductResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listProductResponse == null) {
                        newProducts.setValue(null);
                        isLoadingNewProducts.setValue(true);

                    }
                    else {
                        newProducts.setValue(listProductResponse);
                        isLoadingNewProducts.setValue(false);

                    }
                }
                else {
                    newProducts.setValue(null);
                    isLoadingNewProducts.setValue(true);
                }

            }

            @Override
            public void onFailure(Call<ListProductResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                isLoadingNewProducts.setValue(true);
                newProducts.setValue(null);
            }
        });
    }

    public void getPromotionProducts() {
        isLoadingNewProducts.setValue(true);
        /*Step 1*/
        Call<ListProductResponse> call = RestClient.getRestService().getListPromotionProducts();

        call.enqueue(new Callback<ListProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListProductResponse> call, @NonNull Response<ListProductResponse> response) {
                ListProductResponse listProductResponse = new ListProductResponse();
                if(response.isSuccessful()){
                    listProductResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listProductResponse == null) {
                        promotionProducts.setValue(null);
                        isLoadingPromotionProducts.setValue(true);

                    }
                    else {
                        promotionProducts.setValue(listProductResponse);
                        isLoadingPromotionProducts.setValue(false);

                    }
                }
                else {
                    APIError error = ErrorUtils.parseError(response);
                    listProductResponse.setMsg(error.getMsg());
                    listProductResponse.setCode(error.getCode());
                    listProductResponse.setStatus(error.getStatus());
                    listProductResponse.setTimestamp(error.getTimestamp());
                    listProductResponse.setResult(error.getResult());
                    promotionProducts.setValue(listProductResponse);
                    isLoadingPromotionProducts.setValue(true);
                }

            }

            @Override
            public void onFailure(Call<ListProductResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                isLoadingPromotionProducts.setValue(true);
                promotionProducts.setValue(null);
            }
        });
    }

    public void getMostPurchasedProducts(int pageSize) {
        isLoadingMostPurchased.setValue(true);
        /*Step 1*/
        Call<ListProductResponse> call = RestClient.getRestService().getListProducts(1, pageSize, "soldQuantity", "DESC", null);

        call.enqueue(new Callback<ListProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListProductResponse> call, @NonNull Response<ListProductResponse> response) {

                if(response.isSuccessful()){
                    ListProductResponse listProductResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listProductResponse == null) {
                        isLoadingMostPurchased.setValue(true);
                        mostPurchasedProducts.setValue(null);
                    }
                    else {
                        isLoadingMostPurchased.setValue(false);
                        mostPurchasedProducts.setValue(listProductResponse);
                    }
                }
                else {
                    mostPurchasedProducts.setValue(null);
                    isLoadingMostPurchased.setValue(true);
                }

            }

            @Override
            public void onFailure(Call<ListProductResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                isLoadingMostPurchased.setValue(true);
                mostPurchasedProducts.setValue(null);
            }
        });
    }

    public void getAllProducts(int currentPage, int pageSize) {
        isLoadingAllProducts.setValue(true);
        /*Step 1*/
        Call<ListProductResponse> call = RestClient.getRestService().getListProducts(currentPage, pageSize, "id", "ASC", null);

        call.enqueue(new Callback<ListProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListProductResponse> call, @NonNull Response<ListProductResponse> response) {

                if(response.isSuccessful()){

                    ListProductResponse listProductResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listProductResponse == null) {
                        allProducts.setValue(null);
                        isLoadingAllProducts.setValue(true);
                    }
                    else {
                        isLoadingAllProducts.setValue(false);
                        allProducts.setValue(listProductResponse);
                    }

                }
                else {
                    allProducts.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<ListProductResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                isLoadingAllProducts.setValue(true);
                allProducts.setValue(null);
            }
        });
    }
}

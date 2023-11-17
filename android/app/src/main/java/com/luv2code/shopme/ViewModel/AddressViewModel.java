package com.luv2code.shopme.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luv2code.shopme.API.RestClient;
import com.luv2code.shopme.Request.AddressRequest;
import com.luv2code.shopme.Response.AddressResponse;
import com.luv2code.shopme.Response.BaseResponse;
import com.luv2code.shopme.Response.ListAddressResponse;
import com.luv2code.shopme.Response.ListDistrictResponse;
import com.luv2code.shopme.Response.ListProvinceResponse;
import com.luv2code.shopme.Response.ListWardResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressViewModel extends ViewModel {
    private MutableLiveData<ListAddressResponse> listAddress = new MutableLiveData<>();
    private MutableLiveData<ListProvinceResponse> listProvinces = new MutableLiveData<>();
    private MutableLiveData<ListDistrictResponse> listDistricts = new MutableLiveData<>();
    private MutableLiveData<ListWardResponse> listWards = new MutableLiveData<>();
    private MutableLiveData<AddressResponse> addressDefault = new MutableLiveData<>();
    private MutableLiveData<AddressResponse> addNewAddress = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingAddressDefault = new MutableLiveData<>();
    private MutableLiveData<BaseResponse> updateAddress = new MutableLiveData<>();
    private MutableLiveData<BaseResponse> deleteAddress = new MutableLiveData<>();


    public LiveData<Boolean> isLoadingAddressDefault()
    {
        if (isLoadingAddressDefault == null) {
            isLoadingAddressDefault = new MutableLiveData<>();
        }
        return this.isLoadingAddressDefault;
    }

    public LiveData<BaseResponse> getUpdateAddressData()
    {
        if (updateAddress == null) {
            updateAddress = new MutableLiveData<>();
        }
        return this.updateAddress;
    }

    public LiveData<BaseResponse> getDeleteAddressData()
    {
        if (deleteAddress == null) {
            deleteAddress = new MutableLiveData<>();
        }
        return this.deleteAddress;
    }

    public LiveData<AddressResponse> getAddressDefault()
    {
        if (addressDefault == null) {
            addressDefault = new MutableLiveData<>();
        }
        return this.addressDefault;
    }

    public LiveData<AddressResponse> getAddNewAddressData()
    {
        if (addNewAddress == null) {
            addNewAddress = new MutableLiveData<>();
        }
        return this.addNewAddress;
    }

    public LiveData<ListAddressResponse> getListAddressData()
    {
        if (listAddress == null) {
            listAddress = new MutableLiveData<>();
        }
        return this.listAddress;
    }

    public LiveData<ListProvinceResponse> getListProvincesData()
    {
        if (listProvinces == null) {
            listProvinces = new MutableLiveData<>();
        }
        return this.listProvinces;
    }

    public LiveData<ListDistrictResponse> getListDistrictsData()
    {
        if (listDistricts == null) {
            listDistricts = new MutableLiveData<>();
        }
        return this.listDistricts;
    }

    public LiveData<ListWardResponse> getListWardsData()
    {
        if (listWards == null) {
            listWards = new MutableLiveData<>();
        }
        return this.listWards;
    }

    public void getProvince() {
        /*Step 1*/
        Call<ListProvinceResponse> call = RestClient.getRestService().getProvinces();

        call.enqueue(new Callback<ListProvinceResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListProvinceResponse> call, @NonNull Response<ListProvinceResponse> response) {

                if(response.isSuccessful()){
                    ListProvinceResponse listProvinceResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listProvinceResponse == null) {
                        listProvinces.setValue(null);

                    }
                    else {
                        listProvinces.setValue(listProvinceResponse);
                    }
                }
                else {
                    listProvinces.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<ListProvinceResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                listProvinces.setValue(null);
            }
        });
    }

    public void getDistrict(String code) {
        /*Step 1*/
        Call<ListDistrictResponse> call = RestClient.getRestService().getDistricts(code);

        call.enqueue(new Callback<ListDistrictResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListDistrictResponse> call, @NonNull Response<ListDistrictResponse> response) {

                if(response.isSuccessful()){
                    ListDistrictResponse listDistrictResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listDistrictResponse == null) {
                        listDistricts.setValue(null);

                    }
                    else {
                        listDistricts.setValue(listDistrictResponse);
                    }
                }
                else {
                    listDistricts.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<ListDistrictResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                listDistricts.setValue(null);
            }
        });
    }

    public void getWard(String code) {
        /*Step 1*/
        Call<ListWardResponse> call = RestClient.getRestService().getWards(code);

        call.enqueue(new Callback<ListWardResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListWardResponse> call, @NonNull Response<ListWardResponse> response) {

                if(response.isSuccessful()){
                    ListWardResponse listWardResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listWardResponse == null) {
                        listWards.setValue(null);

                    }
                    else {
                        listWards.setValue(listWardResponse);
                    }
                }
                else {
                    listWards.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<ListWardResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                listWards.setValue(null);
            }
        });
    }

    public void getAddressByUser(String authorization) {
        /*Step 1*/
        Call<ListAddressResponse> call = RestClient.getRestService().getAddressByUser(authorization);

        call.enqueue(new Callback<ListAddressResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListAddressResponse> call, @NonNull Response<ListAddressResponse> response) {

                if(response.isSuccessful()){
                    ListAddressResponse listAddressResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(listAddressResponse == null) {
                        listAddress.setValue(null);

                    }
                    else {
                        listAddress.setValue(listAddressResponse);
                    }
                }
                else {
                    listAddress.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<ListAddressResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                listAddress.setValue(null);
            }
        });
    }

    public void getAddressDefaultByUser(String authorization) {
        isLoadingAddressDefault.setValue(true);

        /*Step 1*/
        Call<AddressResponse> call = RestClient.getRestService().getAddressDefaultByUser(authorization);

        call.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddressResponse> call, @NonNull Response<AddressResponse> response) {

                if(response.isSuccessful()){
                    AddressResponse addressResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(addressResponse == null) {
                        addressDefault.setValue(null);
                    }
                    else {
                        addressDefault.setValue(addressResponse);
                    }
                }
                else {
                    addressDefault.setValue(null);
                }
                isLoadingAddressDefault.setValue(false);


            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                addressDefault.setValue(null);
                isLoadingAddressDefault.setValue(false);
            }
        });
    }

    public void addAddress(String authorization, AddressRequest addressRequest) {

        /*Step 1*/
        Call<AddressResponse> call = RestClient.getRestService().addAddress(authorization, addressRequest);

        call.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddressResponse> call, @NonNull Response<AddressResponse> response) {

                if(response.isSuccessful()){
                    AddressResponse addressResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(addressResponse == null) {
                        addNewAddress.setValue(null);
                    }
                    else {
                        addNewAddress.setValue(addressResponse);
                    }
                }
                else {
                    addNewAddress.setValue(null);
                }


            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                addNewAddress.setValue(null);
            }
        });
    }

    public void updateAddress(String authorization,Integer id, AddressRequest addressRequest) {

        /*Step 1*/
        Call<BaseResponse> call = RestClient.getRestService().updateAddress(authorization, id, addressRequest);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {

                if(response.isSuccessful()){
                    BaseResponse baseResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(baseResponse == null) {
                        updateAddress.setValue(null);
                    }
                    else {
                        updateAddress.setValue(baseResponse);
                    }
                }
                else {
                    updateAddress.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                updateAddress.setValue(null);
            }
        });
    }

    public void deleteAddress(String authorization,Integer id) {

        /*Step 1*/
        Call<BaseResponse> call = RestClient.getRestService().deleteAddress(authorization, id);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {

                if(response.isSuccessful()){
                    BaseResponse baseResponse = response.body();

                    Log.d("Response :=>", response.body() + "");

                    if(baseResponse == null) {
                        deleteAddress.setValue(null);
                    }
                    else {
                        deleteAddress.setValue(baseResponse);
                    }
                }
                else {
                    deleteAddress.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Throwable :=>", t.toString() + "");

                deleteAddress.setValue(null);
            }
        });
    }
}

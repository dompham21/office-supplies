package com.luv2code.shopme.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.luv2code.shopme.Adapter.DistrictAdapter;
import com.luv2code.shopme.Adapter.ProvinceAdapter;
import com.luv2code.shopme.Adapter.WardAdapter;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Address;
import com.luv2code.shopme.Model.Cart;
import com.luv2code.shopme.Model.District;
import com.luv2code.shopme.Model.Province;
import com.luv2code.shopme.Model.Ward;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Request.AddressRequest;
import com.luv2code.shopme.Utils.CustomTextInputLayout;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.Utils.NameValidator;
import com.luv2code.shopme.Utils.PhoneNumberValidator;
import com.luv2code.shopme.ViewModel.AddressViewModel;


import java.util.ArrayList;
import java.util.List;


public class NewAddressActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner spnWard, spnProvince, spnDistrict;
    private AddressViewModel addressViewModel;
    private ProvinceAdapter provinceAdapter;
    private DistrictAdapter districtAdapter;
    private WardAdapter wardAdapter;
    private EditText edtName, edtPhone, edtSpecific;
    private Switch sIsDefault;
    private CustomTextInputLayout layoutEdtName, layoutEdtPhone, layoutEdtSpecific;
    private AppCompatButton btnSave, btnDelete;
    private Address currentAddress;
    private Province currentProvince;
    private District currentDistrict;


    LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
        currentAddress = (Address) getIntent().getSerializableExtra(App.ADDRESS);

        setControls();
        setEvents();
        getListProvinces();
        getAddressDefault();

    }



    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String hintProvince = getResources().getString(R.string.hint_province);
                currentProvince = provinceAdapter.getItem(i);
                if(!provinceAdapter.getItem(i).getFullName().equals(hintProvince)) {
                    addressViewModel.getDistrict(provinceAdapter.getItem(i).getCode());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String hintDistrict = getResources().getString(R.string.hint_district);
                currentDistrict = districtAdapter.getItem(i);
                if(!districtAdapter.getItem(i).getFullName().equals(hintDistrict)) {
                    System.out.println("change item " + i);
                    addressViewModel.getWard(districtAdapter.getItem(i).getCode());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addressViewModel.getListProvincesData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {

                List<Province> listProvince = new ArrayList<>();

                if(currentAddress == null) {
                    String hintProvince = getResources().getString(R.string.hint_province);
                    Province province = new Province(hintProvince);
                    listProvince.add(province);
                }

                listProvince.addAll(object.getData());


                provinceAdapter = new ProvinceAdapter(this, R.layout.item_selected, listProvince);

                spnProvince.setAdapter(provinceAdapter);

                if (currentAddress != null) {

                    Province province = currentAddress.getWard().getDistrict().getProvince();
                    int position = provinceAdapter.getPosition(province);
                    spnProvince.setSelection(position);
                }



            }
            else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        addressViewModel.getListDistrictsData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                List<District> listDistrict = new ArrayList<>();

                if(currentAddress == null) {
                    String hintDistrict = getResources().getString(R.string.hint_district);
                    District district = new District(hintDistrict);
                    listDistrict.add(district);
                }

                listDistrict.addAll(object.getData());

                districtAdapter = new DistrictAdapter(this, R.layout.item_selected, listDistrict);
                spnDistrict.setAdapter(districtAdapter);
                spnDistrict.setEnabled(true);

                if(currentAddress != null && currentProvince != null) {
                    if(currentAddress.getWard().getDistrict().getProvince().equals(currentProvince)) {
                        District district = currentAddress.getWard().getDistrict();
                        int position = districtAdapter.getPosition(district);
                        spnDistrict.setSelection(position);
                    }
                }

            }
            else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        addressViewModel.getListWardsData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                List<Ward> listWard = new ArrayList<>();

                if(currentAddress == null) {
                    String hintWard = getResources().getString(R.string.hint_ward);
                    Ward ward = new Ward(hintWard);
                    listWard.add(ward);
                }

                listWard.addAll(object.getData());
                wardAdapter = new WardAdapter(this, R.layout.item_selected, listWard);
                spnWard.setAdapter(wardAdapter);
                spnWard.setEnabled(true);

                if(currentAddress != null && currentProvince != null && currentDistrict != null) {
                    if(currentAddress.getWard().getDistrict().equals(currentDistrict) && currentAddress.getWard().getDistrict().getProvince().equals(currentProvince)) {
                        Ward ward = currentAddress.getWard();
                        spnWard.setSelection(wardAdapter.getPosition(ward));
                    }
                }


            }
            else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = charSequence.toString();

                if (!name.isEmpty()) {
                    layoutEdtName.setError("");
                }

                if(!NameValidator.validate(name)) {
                    layoutEdtName.setError("Invalid a person's name!");
                }
                else {
                    layoutEdtName.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b && edtName.getText().toString().trim().isEmpty()) {
                    layoutEdtName.setError("Please enter a name in the field!");
                }
            }
        });


        edtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String phone  = edtPhone.getText().toString().trim();
                if(!b && phone.isEmpty()) {
                    layoutEdtPhone.setError("Please enter your phone number in the field!");
                }
                else if(!b && !PhoneNumberValidator.validate(phone)) {
                    layoutEdtPhone.setError("Invalid Vietnam phone number!");
                }
            }
        });

        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phone = charSequence.toString().trim();
                if (phone.isEmpty()) {
                    layoutEdtPhone.setError("Please enter your phone number in the field!");
                }
                else if(!PhoneNumberValidator.validate(phone)) {
                    layoutEdtPhone.setError("Invalid Vietnam phone number!");
                }
                else {
                    layoutEdtPhone.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edtSpecific.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phone = charSequence.toString();
                if (!phone.isEmpty()) {
                    layoutEdtSpecific.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edtSpecific.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b && edtSpecific.getText().toString().trim().isEmpty()) {
                    layoutEdtSpecific.setError("Please enter a address specific in the field!");
                }
            }
        });

        addressViewModel.getAddressDefault().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            Address address = object.getData();
            updateInfoSwitchIsDefault(address);
        });

        addressViewModel.getUpdateAddressData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && message != null) {
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_SHORT, CustomToast.SUCCESS).show();
                finish();
            }
            else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        addressViewModel.getDeleteAddressData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && message != null) {
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_SHORT, CustomToast.SUCCESS).show();
                finish();
            }
            else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtName.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                String specificAddress = edtSpecific.getText().toString().trim();
                boolean isDefault  = sIsDefault.isChecked();
                int posProvince = spnProvince.getSelectedItemPosition();
                int posWard = spnWard.getSelectedItemPosition();
                int posDistrict = spnDistrict.getSelectedItemPosition();

                Ward ward = wardAdapter.getItem(posWard);
                District district = districtAdapter.getItem(posDistrict);
                Province province = provinceAdapter.getItem(posProvince);

                String hintProvince = getResources().getString(R.string.hint_province);
                String hintDistrict = getResources().getString(R.string.hint_district);
                String hintWard = getResources().getString(R.string.hint_ward);


                if(name.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), "Please enter a name in the field!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(!NameValidator.validate(name)) {
                    CustomToast.toastCenterTransparent(view.getContext(), "Invalid a person's name!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(phone.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), "Please enter a phone in the field!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(!PhoneNumberValidator.validate(phone)) {
                    CustomToast.toastCenterTransparent(view.getContext(), "Invalid Vietnam phone number!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(specificAddress.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), "Please enter a specific address in the field!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(province == null || province.getFullName().equals(hintProvince)) {
                    CustomToast.toastCenterTransparent(view.getContext(), "Please select a province!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(district == null || district.getFullName().equals(hintDistrict)) {
                    CustomToast.toastCenterTransparent(view.getContext(), "Please select a district!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(ward == null || ward.getFullName().equals(hintWard)) {
                    CustomToast.toastCenterTransparent(view.getContext(), "Please select a ward!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else {
                    String accessToken = "Bearer ".concat(localStorage.getKeyAccessToken());
                    AddressRequest addressRequest = new AddressRequest(name, phone, specificAddress, ward.getCode(), isDefault);

                    if(currentAddress != null) {
                        addressViewModel.updateAddress(accessToken, currentAddress.getId(), addressRequest);
                    }
                    else {
                        addressViewModel.addAddress(accessToken, addressRequest);
                    }
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentAddress.isDefault_address()) {
                    showAlertDialogRemoveClicked();
                }
                else {
                    CustomToast.toastCenterTransparent(view.getContext(), "You cannot delete the default address!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
            }
        });

        addressViewModel.getAddNewAddressData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_SHORT, CustomToast.SUCCESS).show();
                finish();
            }
            else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

    }

    private void setControls() {
        toolbar = findViewById(R.id.toolbar);

        List<Province> listProvince = new ArrayList<>();
        List<District> listDistrict = new ArrayList<>();
        List<Ward> listWard = new ArrayList<>();

        localStorage = new LocalStorage(App.getContext());
        spnProvince = findViewById(R.id.spn_province);
        spnDistrict = findViewById(R.id.spn_district);
        spnWard = findViewById(R.id.spn_ward);

        edtName = findViewById(R.id.tv_name);
        edtPhone = findViewById(R.id.tv_phone);
        edtSpecific = findViewById(R.id.tv_address_specific);
        sIsDefault = findViewById(R.id.switch_default);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);

        layoutEdtName = findViewById(R.id.layout_tv_name);
        layoutEdtPhone = findViewById(R.id.layout_tv_phone);
        layoutEdtSpecific =findViewById(R.id.layout_tv_specific);

        if(currentAddress != null) {
            toolbar.setTitle("Edit Address");
        }
        else {
            toolbar.setTitle("New Address");
        }

        String hintProvince = getResources().getString(R.string.hint_province);
        String hintDistrict = getResources().getString(R.string.hint_district);
        String hintWard = getResources().getString(R.string.hint_ward);

        Province province = new Province(hintProvince);
        District district = new District(hintDistrict);
        Ward ward = new Ward(hintWard);

        listProvince.add(province);
        listDistrict.add(district);
        listWard.add(ward);

        provinceAdapter = new ProvinceAdapter(this, R.layout.item_selected, listProvince);
        districtAdapter = new DistrictAdapter(this, R.layout.item_selected, listDistrict);
        wardAdapter = new WardAdapter(this, R.layout.item_selected, listWard);

        spnProvince.setAdapter(provinceAdapter);
        spnDistrict.setAdapter(districtAdapter);
        spnWard.setAdapter(wardAdapter);
        spnDistrict.setEnabled(false);
        spnWard.setEnabled(false);

        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);

        if(currentAddress != null) {
            updateInfoAddress(currentAddress);
            btnDelete.setVisibility(View.VISIBLE);
        }
        else {
            btnDelete.setVisibility(View.GONE);
        }
    }

    private void updateInfoAddress(Address address) {
        edtName.setText(address.getName());
        edtPhone.setText(address.getPhone());
        edtSpecific.setText(address.getSpecificAddress());
        sIsDefault.setChecked(address.isDefault_address());
    }

    private void updateInfoSwitchIsDefault(Address address) {
        // Tai khoan nay chua co dia chi mac dinh va dang add new address
        if(address == null && currentAddress == null) {
            sIsDefault.setChecked(true);
            sIsDefault.setEnabled(false);
        }
        else if(currentAddress != null) {
            if(currentAddress.isDefault_address()) {
                sIsDefault.setChecked(true);
                sIsDefault.setEnabled(false);
            }
        }
    }

    private void getListProvinces() {
        addressViewModel.getProvince();
    }

    private void getAddressDefault() {
        String accessToken = "Bearer ".concat(localStorage.getKeyAccessToken());
        addressViewModel.getAddressDefaultByUser(accessToken);
    }

    public void showAlertDialogRemoveClicked() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this address?");

        // add the buttons
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String accessToken = "Bearer ".concat(localStorage.getKeyAccessToken());
                addressViewModel.deleteAddress(accessToken, currentAddress.getId());
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
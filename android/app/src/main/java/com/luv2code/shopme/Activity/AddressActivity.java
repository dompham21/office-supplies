package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.luv2code.shopme.Adapter.AddressAdapter;
import com.luv2code.shopme.Adapter.CategoryAdapter;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Address;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.AddressViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Context context = this;
    private LinearLayout btnAddNewAddress;
    private RecyclerView rcvAddress;
    private AddressViewModel addressViewModel;
    private AddressAdapter addressAdapter;
    private LocalStorage localStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        setControls();
        setEvents();
        loadListAddress();
    }



    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        addressViewModel.getListAddressData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                List<Address> listAddress = object.getData();

                AddressAdapter addressAdapter = new AddressAdapter(listAddress, new AddressAdapter.OnItemActionListener() {
                    @Override
                    public void onClick(Address address) {
                        Intent intent = new Intent(context, NewAddressActivity.class);
                        intent.putExtra(App.ADDRESS, address);
                        startActivity(intent);
                    }
                });
                rcvAddress.setAdapter(addressAdapter);
            }
            else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        btnAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControls() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.my_address));

        btnAddNewAddress = findViewById(R.id.btn_add_new_address);
        rcvAddress = findViewById(R.id.rcv_address);
        rcvAddress.setHasFixedSize(true);
        rcvAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvAddress.setFocusable(false);
        rcvAddress.setNestedScrollingEnabled(false);


        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        localStorage = new LocalStorage(App.getContext());


    }

    private void loadListAddress() {
        addressViewModel.getAddressByUser(App.getAuthorization());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListAddress();
    }
}
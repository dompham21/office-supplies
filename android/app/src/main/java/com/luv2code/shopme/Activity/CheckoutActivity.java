package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luv2code.shopme.Adapter.AddressAdapter;
import com.luv2code.shopme.Adapter.CheckoutAdapter;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Address;
import com.luv2code.shopme.Model.Cart;
import com.luv2code.shopme.Model.Order;
import com.luv2code.shopme.Model.User;
import com.luv2code.shopme.Model.Ward;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Request.OrderRequest;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.AddressViewModel;
import com.luv2code.shopme.ViewModel.OrderViewModel;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Context context = this;
    private AppCompatImageView btnCloseBottomSheet;
    private AppCompatButton btnOrder;
    private RecyclerView rcvProductCheckout;
    private TextView tvTotalPayment, tvAddressName, tvAddressPhone, tvAddressSpecific, tvAddress;
    private LinearLayout btnAddNewAddress, btnAddNewAddressBottomSheet;
    private ProgressBar progressBarAddress;
    private AddressViewModel addressViewModel;
    private OrderViewModel orderViewModel;
    private RelativeLayout rlSelectAddress, rlNoAddress;
    private Address currentAddress;
    private Dialog bottomSheetDialog;
    private RecyclerView rcvAddress;
    private AddressAdapter addressAdapter;

    LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        setControls();
        setEvents();
        loadUserAddress();
        loadDetailCheckout();

    }

    private void loadUserAddress() {
        addressViewModel.getAddressByUser(App.getAuthorization());
    }

    private void loadDetailCheckout() {
        CheckoutAdapter checkoutAdapter = new CheckoutAdapter(App.cartAdapter.getSelectedCartList());
        rcvProductCheckout.setAdapter(checkoutAdapter);
        tvTotalPayment.setText(String.format("%s đ", App.formatNumberMoney(App.cartAdapter.getSelectedCartItemTotalPayment())));
    }

    private void setControls() {

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Checkout");

        rlSelectAddress = findViewById(R.id.layout_select_address);
        rlNoAddress = findViewById(R.id.layout_no_address);
        tvAddressName = findViewById(R.id.tv_address_name);
        tvAddressPhone = findViewById(R.id.tv_address_phone);
        tvAddressSpecific = findViewById(R.id.tv_address_specific);
        tvAddress = findViewById(R.id.tv_address);
        btnAddNewAddress = findViewById(R.id.btn_add_new_address);
        btnOrder = findViewById(R.id.btn_order);

        progressBarAddress = findViewById(R.id.progress_bar_address);

        rcvProductCheckout = findViewById(R.id.rcv_product_checkout);
        rcvProductCheckout.setHasFixedSize(true);
        rcvProductCheckout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvProductCheckout.setFocusable(false);
        rcvProductCheckout.setNestedScrollingEnabled(false);

        btnOrder = findViewById(R.id.btn_order);
        tvTotalPayment = findViewById(R.id.tv_total_payment);
        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        localStorage = new LocalStorage(App.getContext());

        bottomSheetDialog = new Dialog(this);
        bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_select_address);

        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        rcvAddress = bottomSheetDialog.findViewById(R.id.rcv_address);
        rcvAddress.setHasFixedSize(true);
        rcvAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvAddress.setFocusable(false);
        rcvAddress.setNestedScrollingEnabled(false);

        btnCloseBottomSheet = bottomSheetDialog.findViewById(R.id.btn_close_bottom_sheet);
        btnAddNewAddressBottomSheet = bottomSheetDialog.findViewById(R.id.btn_add_new_address_bottom_sheet);
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
                for(Address address : listAddress) {
                    if(address.isDefault_address()) {
                        currentAddress = address;
                    }
                }
                addressAdapter = new AddressAdapter(listAddress, new AddressAdapter.OnItemActionListener() {
                    @Override
                    public void onClick(Address address) {
                        currentAddress = address;
                        loadInfoAddress(currentAddress);
                        bottomSheetDialog.dismiss();
                    }
                });

                loadInfoAddress(currentAddress);
                rcvAddress.setAdapter(addressAdapter);
            }
            else if(result == 0 && message != null){
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }

        });

        addressViewModel.isLoadingAddressDefault().observe(this, isLoading -> {
            if (isLoading) {
                progressBarAddress.setVisibility(View.VISIBLE);
            } else {
                progressBarAddress.setVisibility(View.GONE);
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentAddress ==  null) {
                    CustomToast.toastCenterTransparent(view.getContext(), "Please select a delivery address!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else {
                    List<Cart> listCart = App.cartAdapter.getSelectedCartList();

                    OrderRequest orderRequest = new OrderRequest(App.cartAdapter.getSelectedCartItemTotalPayment(), currentAddress.getId(), listCart);
                    orderViewModel.addNewOrder(App.getAuthorization(), orderRequest);
                }
            }
        });

        btnAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewAddressActivity.class);
                startActivity(intent);
            }
        });

        btnAddNewAddressBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewAddressActivity.class);
                startActivity(intent);
            }
        });

        orderViewModel.getAddNewOrder().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && message != null) {
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_SHORT, CustomToast.SUCCESS).show();
                Intent intent = new Intent(this, CheckoutDoneActivity.class);
                startActivity(intent);
            }
            else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        rlSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show();
            }

        });

        btnCloseBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

    }

    private void loadInfoAddress(Address address) {
        if (address != null) {
            rlNoAddress.setVisibility(View.GONE);
            rlSelectAddress.setVisibility(View.VISIBLE);

            tvAddressName.setText(address.getName());

            tvAddressSpecific.setText(address.getSpecificAddress());
            tvAddressPhone.setText(address.getPhone());


            tvAddress.setText(String.format("%s, %s, %s",
                    address.getWard().getFullName(), address.getWard().getDistrict().getFullName(),
                    address.getWard().getDistrict().getProvince().getFullName()));
        }
        else {
            rlNoAddress.setVisibility(View.VISIBLE);
            rlSelectAddress.setVisibility(View.GONE);
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        loadUserAddress();
    }
}
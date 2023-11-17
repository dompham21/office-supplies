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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luv2code.shopme.Adapter.OrderDetailAdapter;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Order;
import com.luv2code.shopme.Model.OrderDetail;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.OrderViewModel;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Context context = this;
    LocalStorage localStorage;
    private OrderViewModel orderViewModel;
    private RecyclerView rcvOrderDetail;
    private LinearLayout layoutWaitPayment, layoutDone, layoutCancel;
    private TextView tvPaymentMethod, tvAddressName, tvAddressPhone, tvAddressSpecific, tvAddress, tvQuantity, tvTotal;
    private AppCompatButton btnRepurchase, btnRequestCancel, btnProcess;
    private RelativeLayout layoutFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        setControls();
        setEvents();
        loadOrderDetail();

    }

    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        orderViewModel.getOrderDetailData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                Order order = object.getData();
                List<OrderDetail> listOrderDetail = order.getOrderDetails();
                OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(listOrderDetail, OrderDetailAdapter.tagInOrderDetailActivity, order.getStatus().getId(), new OrderDetailAdapter.OnItemActionListener() {
                    @Override
                    public void onClick() {

                    }

                    @Override
                    public void onBtnReviewClick(Product product) {
                        Intent intent = new Intent(context, EditReviewActivity.class);
                        intent.putExtra(App.KEY_PRODUCT_REVIEW, product);
                        startActivity(intent);
                    }
                });
                rcvOrderDetail.setAdapter(orderDetailAdapter);

                loadInfoOrder(order);
            } else {
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });
    }

    private void setControls() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Detail order");

        layoutWaitPayment = findViewById(R.id.layout_wait_payment);
        layoutDone = findViewById(R.id.layout_done);
        layoutCancel = findViewById(R.id.layout_cancel);
        layoutFooter = findViewById(R.id.layout_footer);

        tvPaymentMethod = findViewById(R.id.tv_payment_method);
        tvAddressName = findViewById(R.id.tv_address_name);
        tvAddressPhone = findViewById(R.id.tv_address_phone);
        tvAddressSpecific = findViewById(R.id.tv_address_specific);
        tvAddress = findViewById(R.id.tv_address);
        tvQuantity = findViewById(R.id.text_quantity);
        tvTotal = findViewById(R.id.tv_total);

        btnProcess = findViewById(R.id.btn_process);
        btnRepurchase = findViewById(R.id.btn_repurchase);
        btnRequestCancel = findViewById(R.id.btn_request_cancel);

        rcvOrderDetail = findViewById(R.id.rcv_order_detail);
        rcvOrderDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcvOrderDetail.setHasFixedSize(true);
        rcvOrderDetail.setFocusable(false);
        rcvOrderDetail.setNestedScrollingEnabled(false);

        localStorage = new LocalStorage(App.getContext());
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

    }
    private void loadOrderDetail() {
        Integer orderId = (Integer) getIntent().getExtras().get(App.KEY_ORDER_ID);

        String accessToken = App.getAuthorization();
        orderViewModel.getOrderDetail(accessToken, orderId);
    }

    private void loadInfoOrder(Order order) {
        if (order.getStatus().getId().equals(1) || order.getStatus().getId().equals(2)) { //cho xac nhan, cho lay hang
            layoutWaitPayment.setVisibility(View.VISIBLE);
            layoutCancel.setVisibility(View.GONE);
            layoutDone.setVisibility(View.GONE);

            btnRequestCancel.setVisibility(View.VISIBLE);
            btnRepurchase.setVisibility(View.GONE);
            btnProcess.setVisibility(View.GONE);
        }
        else if (order.getStatus().getId().equals(3)){ //Dang giao
            layoutWaitPayment.setVisibility(View.VISIBLE);
            layoutCancel.setVisibility(View.GONE);
            layoutDone.setVisibility(View.GONE);

            btnRequestCancel.setVisibility(View.GONE);
            btnRepurchase.setVisibility(View.GONE);
            btnProcess.setVisibility(View.VISIBLE);
        }
        else if (order.getStatus().getId().equals(4)){ //Da giao
            layoutWaitPayment.setVisibility(View.GONE);
            layoutCancel.setVisibility(View.GONE);
            layoutDone.setVisibility(View.VISIBLE);

            btnRequestCancel.setVisibility(View.GONE);
            btnRepurchase.setVisibility(View.GONE);
            btnProcess.setVisibility(View.GONE);
            layoutFooter.setVisibility(View.GONE);
        }
        else if (order.getStatus().getId().equals(5)){ //Da huy
            layoutWaitPayment.setVisibility(View.GONE);
            layoutCancel.setVisibility(View.VISIBLE);
            layoutDone.setVisibility(View.GONE);

            btnRequestCancel.setVisibility(View.GONE);
            btnRepurchase.setVisibility(View.VISIBLE);
            btnProcess.setVisibility(View.GONE);
        }

        tvAddressName.setText(order.getAddress().getName());
        tvAddressPhone.setText(order.getAddress().getPhone());
        tvAddressSpecific.setText(order.getAddress().getSpecificAddress());
        tvAddress.setText(String.format("%s, %s, %s",
                order.getAddress().getWard().getFullName(), order.getAddress().getWard().getDistrict().getFullName(),
                order.getAddress().getWard().getDistrict().getProvince().getFullName()));

        tvQuantity.setText(String.valueOf(order.getOrderDetails().size()));
        tvTotal.setText(App.formatNumberMoney(order.getTotalPrice()));

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrderDetail();
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
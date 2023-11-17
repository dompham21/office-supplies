package com.luv2code.shopme.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.luv2code.shopme.Activity.OrderDetailActivity;
import com.luv2code.shopme.Adapter.OrderAdapter;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Order;
import com.luv2code.shopme.Model.OrderStatus;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.OrderViewModel;

import java.util.List;

public class OrderFragment extends Fragment {
    private RecyclerView rcvOrders;
    private View view;
    private OrderStatus orderStatus;
    private OrderViewModel orderViewModel;
    private LocalStorage localStorage;
    private LinearLayout layoutEmpty;

    public OrderFragment() {
        // Required empty public constructor
    }


    public static OrderFragment newInstance(OrderStatus orderStatus) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putSerializable(App.KEY_ORDER_STATUS, orderStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();

        if (getArguments() != null) {
            orderStatus = (OrderStatus) bundle.getSerializable(App.KEY_ORDER);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_order, container, false);
        setControls();
        setEvents();
        loadOrders();
        return view;
    }



    private void setEvents() {
        orderViewModel.getListOrderByStatusData().observe(getViewLifecycleOwner(), object -> {
            if (object == null) {
                CustomToast.makeText(getActivity(), "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                List<Order> orderList = object.getData();
                OrderAdapter orderAdapter = new OrderAdapter(orderList, new OrderAdapter.OnItemActionListener() {
                    @Override
                    public void onClick(Order order) {
                        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                        intent.putExtra(App.KEY_ORDER_ID, order.getId());
                        startActivity(intent);
                    }

                    @Override
                    public void onButtonRepurchaseClick(Order order) {

                    }

                    @Override
                    public void onButtonReviewClick(Order order) {

                    }
                });
                rcvOrders.setAdapter(orderAdapter);
                if(orderAdapter.getItemCount() == 0) {
                    layoutEmpty.setVisibility(View.VISIBLE);
                }
                else {
                    layoutEmpty.setVisibility(View.GONE);
                }


            } else {
                CustomToast.toastCenterTransparent(getActivity(), message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });

    }

    private void setControls() {

        rcvOrders = view.findViewById(R.id.rcv_orders);
        rcvOrders.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rcvOrders.setHasFixedSize(true);
        rcvOrders.setFocusable(false);
        rcvOrders.setNestedScrollingEnabled(false);
        localStorage = new LocalStorage(App.getContext());
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        layoutEmpty = view.findViewById(R.id.layout_empty);
    }

    private void loadOrders() {
        if (!App.checkUserLogged()) {
//            App.goToLoginActivity(getActivity());
            return;
        }
        else {

            orderViewModel.getListOrderByStatus(App.getAuthorization(), orderStatus.getId());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
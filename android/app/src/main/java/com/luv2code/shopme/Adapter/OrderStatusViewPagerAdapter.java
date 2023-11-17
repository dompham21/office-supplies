package com.luv2code.shopme.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.luv2code.shopme.App;
import com.luv2code.shopme.Fragment.OrderFragment;
import com.luv2code.shopme.Model.OrderStatus;

public class OrderStatusViewPagerAdapter extends FragmentStateAdapter {


    public OrderStatusViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        OrderStatus orderStatus = OrderStatus.getOrderStatusList().get(position);

        OrderFragment orderFragment = new OrderFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putSerializable(App.KEY_ORDER, orderStatus);
        orderFragment.setArguments(args);

        return orderFragment;
    }

    @Override
    public int getItemCount() {
        return OrderStatus.getOrderStatusList().size();
    }

    public OrderStatus getItemAt(int position) {
        return OrderStatus.getOrderStatusList().get(position);
    }
}

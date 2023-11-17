package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.luv2code.shopme.Adapter.OrderStatusViewPagerAdapter;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Fragment.OrderFragment;
import com.luv2code.shopme.Model.OrderStatus;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.OrderStatusViewModel;

import java.util.Objects;

public class OrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Context context = this;
    private ViewPager2 viewPager;
    private NestedScrollView nestedScrollView;
    private TabLayout tabLayout;
    private OrderStatusViewModel orderStatusViewModel;
    LocalStorage localStorage;
    private OrderStatusViewPagerAdapter orderStatusViewPagerAdapter;
    private Integer oldPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setControls();
        setEvents();
        loadOrderStatus();

    }


    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    private void setControls() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order");

        nestedScrollView = findViewById(R.id.nested_scroll_view);
        tabLayout = findViewById(R.id.tab_layout_order);
        localStorage = new LocalStorage(App.getContext());
    }

    private void loadOrderStatus() {
        OrderStatus selectedOrderStatus = (OrderStatus) getIntent().getExtras().get(App.KEY_ORDER_STATUS);

        tabLayout.addTab(tabLayout.newTab().setText(Objects.requireNonNull(OrderStatus.getOrderStatusById(1)).getName()), false);
        tabLayout.addTab(tabLayout.newTab().setText(Objects.requireNonNull(OrderStatus.getOrderStatusById(2)).getName()), false);
        tabLayout.addTab(tabLayout.newTab().setText(Objects.requireNonNull(OrderStatus.getOrderStatusById(3)).getName()), false);
        tabLayout.addTab(tabLayout.newTab().setText(Objects.requireNonNull(OrderStatus.getOrderStatusById(4)).getName()), false);
        tabLayout.addTab(tabLayout.newTab().setText(Objects.requireNonNull(OrderStatus.getOrderStatusById(5)).getName()), false);

        Integer currentPosition = OrderStatus.getPositionOrderStatus(selectedOrderStatus);
        oldPosition = currentPosition;

        setSelectedTab(currentPosition);

        tabLayout.getTabAt(currentPosition).select();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setSelectedTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }
    private void setSelectedTab(Integer position) {
        OrderStatus orderStatus = OrderStatus.getOrderStatusList().get(position);
        OrderFragment orderFragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putSerializable(App.KEY_ORDER, orderStatus);
        orderFragment.setArguments(args);

        if(oldPosition < position) {
            replaceFragment(orderFragment, true);
        }
        else {
            replaceFragment(orderFragment, false);
        }

        oldPosition = position;
    }

    private void replaceFragment(Fragment fragment, boolean isRightToLeft) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(isRightToLeft) {
            ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
        }
        else {
            ft.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);
        }

        ft.replace(R.id.frame_layout, fragment);
        ft.commit();
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
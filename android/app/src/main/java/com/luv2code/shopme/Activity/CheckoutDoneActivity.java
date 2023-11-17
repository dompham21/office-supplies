package com.luv2code.shopme.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.OrderStatus;
import com.luv2code.shopme.R;

public class CheckoutDoneActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private AppCompatButton btnHome, btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_done);
        setControls();
        setEvents();
    }

    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.checkUserLogged()) {
                    OrderStatus orderStatus = OrderStatus.getOrderStatusById(4); //da giao
                    Intent intent = new Intent(view.getContext(), OrderActivity.class);
                    intent.putExtra(App.KEY_ORDER_STATUS, orderStatus);
                    startActivity(intent);
                }
            }
        });

    }

    private void setControls() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

        btnHome = findViewById(R.id.btn_home);
        btnOrder = findViewById(R.id.btn_order);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
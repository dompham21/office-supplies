package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.luv2code.shopme.App;
import com.luv2code.shopme.Fragment.CartBlankFragment;
import com.luv2code.shopme.Fragment.CartFragment;
import com.luv2code.shopme.R;

public class CartActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Context context = this;
    private CartFragment cartFragment = new CartFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        displayFragment();
        setControls();
        setEvents();
    }
    private void setControls() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.cart));


    }

    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void displayFragment() {
        if (App.checkUserLogged()) {
            // Your cart is empty
            if (App.cartAdapter.getItemCount() <= 0) {
                CartBlankFragment cartBlankFragmentEmpty = new CartBlankFragment()
                    .setTitle(getString(R.string.cart_empty))
                    .setDescription(getString(R.string.cart_empty_description))
                    .setBtnText(getString(R.string.cart_empty_button))
                    .setType(CartBlankFragment.BLANK);
                replaceFragment(cartBlankFragmentEmpty);
            }
            else {
                replaceFragment(cartFragment);
            }
        }
        // Not logged in
        else {
            CartBlankFragment cartBlankFragmentNotLogin = new CartBlankFragment()
                .setTitle(getString(R.string.not_login))
                .setDescription(getString(R.string.please_sign_in))
                .setBtnText(getString(R.string.let_login_button))
                .setType(CartBlankFragment.NOT_LOGIN);

            replaceFragment(cartBlankFragmentNotLogin);
        }

    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
        ft.replace(R.id.content_frame, fragment);
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
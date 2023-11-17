package com.luv2code.shopme.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.luv2code.shopme.Adapter.CartAdapter;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Fragment.CartBlankFragment;
import com.luv2code.shopme.Fragment.CartFragment;
import com.luv2code.shopme.Model.Cart;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Fragment.HomeFragment;
import com.luv2code.shopme.Fragment.ProfileFragment;
import com.luv2code.shopme.Model.User;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LanguageManager;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.MainViewModel;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private LocalStorage localStorage;
    private Context context = this;
    private static int cart_count = 0;
    private User user;
    private BottomNavigationView bottomNavigationView;
    private MainViewModel viewModel;
    private HomeFragment homeFragment = new HomeFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private CartFragment cartFragment = new CartFragment();
    private NavigationView navigationView;
    private Menu navMenu;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private Spinner spinner;
    private LanguageManager languageManager;

    private ActionBarDrawerToggle toggle;

    public static final String[] languages = {"English", "Việt Nam"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setControls();
        setEvent();

        loadUserInfo();
        loadCartInfo();

    }



    private void setControls() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        localStorage = new LocalStorage(getApplicationContext());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String langCodeCurrent = localStorage.getLanguage();


        languageManager = new LanguageManager(this);

        if(langCodeCurrent.equals("vi")) {
            spinner.setSelection(1);
        }
        else if(langCodeCurrent.equals("en")) {
            spinner.setSelection(0);
        }

        navMenu = navigationView.getMenu();


        replaceFragment(homeFragment);

    }

    private void setEvent() {
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_drawer_home).setChecked(true);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        if(navigationView.getCheckedItem() != null && navigationView.getCheckedItem().getItemId() != R.id.nav_drawer_home) {
                            navigationView.setCheckedItem(R.id.nav_drawer_home);
                        }

                        replaceFragment(homeFragment);
                        break;
                    case R.id.nav_new_product:
//                        replaceFragment(new HomeFragment());
                        break;

                    case R.id.nav_cart:
                        if(navigationView.getCheckedItem() != null && navigationView.getCheckedItem().getItemId() != R.id.nav_drawer_cart) {
                            navigationView.setCheckedItem(R.id.nav_drawer_cart);
                        }

                        Intent intent = new Intent(context, CartActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_profile:
                        if(navigationView.getCheckedItem() != null && navigationView.getCheckedItem().getItemId() != R.id.nav_drawer_profile) {
                            navigationView.setCheckedItem(R.id.nav_drawer_profile);
                        }
                        replaceFragment(profileFragment);
                        break;
                }
                return true;
            }
        });

        viewModel.getListCartData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                List<Cart> listCart = object.getData();

                if (App.cartAdapter == null) {
                    App.cartAdapter = new CartAdapter(listCart);
                }
                else {
                    App.cartAdapter.setData(listCart);
                }
                updateCartItemCount();

            } else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectLang = adapterView.getItemAtPosition(i).toString();
                String langStore = localStorage.getLanguage();

                if(selectLang.equals("English") && !langStore.equals("en")) {
                    languageManager.updateResource("en");
                    recreate();

                }
                else if(selectLang.equals("Việt Nam") && !langStore.equals("vi")) {
                    languageManager.updateResource("vi");
                    recreate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_drawer_home:
                bottomNavigationView.setSelectedItemId(R.id.nav_home);
                break;
            case R.id.nav_drawer_new_product:
                break;

            case R.id.nav_drawer_cart:
                bottomNavigationView.setSelectedItemId(R.id.nav_cart);
                break;
            case R.id.nav_drawer_profile:
                bottomNavigationView.setSelectedItemId(R.id.nav_profile);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    private void loadCartInfo() {

        if(App.checkUserLogged()) {
            viewModel.getListCart(App.getAuthorization());
        }
        else {
            BadgeDrawable badgeCart  = bottomNavigationView.getOrCreateBadge(R.id.nav_home);
            badgeCart.setVisible(false);
        }
    }

    private void loadUserInfo() {
        if(App.checkUserLogged()) {
            String userString = localStorage.getUserLogin();
            Gson gson = new Gson();
            user = gson.fromJson(userString, User.class);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }



    private void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
        ft.replace(R.id.frame_layout, fragment);
        ft.commit();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int seletedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.nav_cart == seletedItemId) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }

        loadUserInfo();
        loadCartInfo();
    }

    private void updateCartItemCount() {
        if (App.cartAdapter == null) return;
        int count = App.cartAdapter.getItemCount();
        BadgeDrawable badgeCart  = bottomNavigationView.getOrCreateBadge(R.id.nav_cart);

        if (count > 0) {
            badgeCart.setVisible(true);
            badgeCart.setBackgroundColor(getResources().getColor(R.color.red));
            badgeCart.setNumber(count);
            badgeCart.setVerticalOffset(7);
            badgeCart.setHorizontalOffset(5);
        }
        else {
            badgeCart.setVisible(false);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

}
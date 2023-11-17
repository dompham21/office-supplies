package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.luv2code.shopme.Adapter.CheckBoxCategoryAdapter;
import com.luv2code.shopme.Adapter.ProductAdapter;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Category;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.ViewModel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class AfterSearchActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Context context = this;
    private SearchViewModel searchViewModel;

    private TextView tvSearch;
    private LinearLayout layoutFilter, layoutSearch;
    private RecyclerView rcvProduct, rcvCategory;
    private String keyword;
    private boolean isLastPage;
    private boolean isLastPageCategory;

    private int currentPage = 0;
    private int totalPage;
    private static final int PAGE_SIZE = 10;
    private static final int PAGE_SIZE_CATEGORY = 6;
    private Double priceMin = null;
    private Double priceMax = null;
    private String sortField = null;
    private String sortDirection = null;
    private List<Integer> categoryIds = new ArrayList<>();
    private NestedScrollView scrollView;
    private ProductAdapter allProductsAdatper;
    private CheckBoxCategoryAdapter checkBoxCategoryAdapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Integer totalPageCategory = 0;
    private int currentPageCategory = 0;
    private AppCompatButton btnFilter;
    private TextView tvShowMore;
    private RadioGroup rgPrice, rgOther;
    private EditText edtPriceMin, edtPriceMax;
    private RadioButton rdPrice100, rdPrice200, rdPrice300;
    private RelativeLayout layoutEmptyProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_search);

        setControl();
        setEvent();


        loadSearch();
        loadCategory();

    }


    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        rcvProduct = findViewById(R.id.rcv_product);
        layoutFilter = findViewById(R.id.layout_filter);
        tvSearch = findViewById(R.id.tv_search);
        layoutSearch = findViewById(R.id.layout_search);
        drawerLayout = findViewById(R.id.drawer_search);
        navigationView = findViewById(R.id.navigation_search);

        edtPriceMin = findViewById(R.id.edt_price_min);
        edtPriceMax = findViewById(R.id.edt_price_max);

        tvShowMore = findViewById(R.id.tv_show_more);
        btnFilter = findViewById(R.id.btn_filter);
        rgPrice = findViewById(R.id.rg_price);
        rgOther = findViewById(R.id.rg_other);

        rdPrice100 = findViewById(R.id.radio_price_100);
        rdPrice200 = findViewById(R.id.radio_price_200);
        rdPrice300 = findViewById(R.id.radio_price_300);
        layoutEmptyProduct = findViewById(R.id.layout_empty_product);

        Bundle bundle = getIntent().getExtras();
        keyword = bundle.getString(App.KEY_SEARCH);

        tvSearch.setText(keyword);

        rcvProduct.setHasFixedSize(true);
        rcvProduct.setLayoutManager(new LinearLayoutManager(this));
        rcvProduct.setFocusable(false);
        rcvProduct.setNestedScrollingEnabled(false);

        scrollView = findViewById(R.id.scroll_view);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        List<Product> productList = new ArrayList<>();
        allProductsAdatper = new ProductAdapter(productList, this);

        rcvProduct = findViewById(R.id.rcv_product);
        rcvProduct.setHasFixedSize(true);
        rcvProduct.setLayoutManager(new GridLayoutManager(this, 2));
        rcvProduct.setFocusable(false);
        rcvProduct.setNestedScrollingEnabled(false);
        rcvProduct.setAdapter(allProductsAdatper);

        List<Category> listCategory = new ArrayList<>();
        checkBoxCategoryAdapter = new CheckBoxCategoryAdapter(listCategory);
        rcvCategory = findViewById(R.id.rcv_category);
        rcvCategory.setHasFixedSize(true);
        rcvCategory.setLayoutManager(new GridLayoutManager(this, 2));
        rcvCategory.setFocusable(false);
        rcvCategory.setNestedScrollingEnabled(false);
        rcvCategory.setAdapter(checkBoxCategoryAdapter);

    }

    private void setEvent() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        layoutFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });


        rgPrice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_price_100: // 0 - 100k
                        edtPriceMin.setText(String.valueOf(0));
                        edtPriceMax.setText(String.valueOf(100000));
                        break;
                    case R.id.radio_price_200: // 100k - 200k
                        edtPriceMin.setText(String.valueOf(100000));
                        edtPriceMax.setText(String.valueOf(200000));
                        break;
                    case R.id.radio_price_300: // 200k - 300k
                        edtPriceMin.setText(String.valueOf(200000));
                        edtPriceMax.setText(String.valueOf(300000));
                        break;
                    case -1:
                        break;
                }
            }
        });

        searchViewModel.getListSearchData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                totalPage = object.getTotalPage();

                List<Product> listProducts = object.getData();
                if(listProducts.size() <= 0) {
                    layoutEmptyProduct.setVisibility(View.VISIBLE);
                }
                else {
                    layoutEmptyProduct.setVisibility(View.GONE);
                }

                if(currentPage == 1) {
                    allProductsAdatper.setData(listProducts);
                }
                else {
                    allProductsAdatper.addData(listProducts);
                }

                if (currentPage >= totalPage) {
                    isLastPage = true;
                }

            } else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollView.getChildAt(0).getBottom() <= (scrollView.getHeight() + scrollY)) {
                        if (isLastPage) return;
                        loadSearch();
                    }
                }
            });
        }







        searchViewModel.getListCategoryData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                totalPageCategory = object.getTotalPage();

                List<Category> listCategory = object.getData();
                checkBoxCategoryAdapter.addData(listCategory);

                if (currentPageCategory >= totalPageCategory) {
                    isLastPageCategory = true;
                }

            } else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });

        tvShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLastPageCategory) return;
                loadCategory();
            }
        });

        checkBoxCategoryAdapter.setOnItemClickListener(new CheckBoxCategoryAdapter.OnItemActionListener() {
            @Override
            public void onCheckBoxCheckedChanged(Integer categoryId, Boolean b) {
                categoryIds.remove(categoryId);
                if(b) {
                    categoryIds.add(categoryId);
                }
            }
        });


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringPriceMin = edtPriceMin.getText().toString().trim();
                String stringPriceMax = edtPriceMax.getText().toString().trim();

                if(!stringPriceMin.isEmpty()) {
                    priceMin = Double.valueOf(stringPriceMin);
                }

                if(!stringPriceMax.isEmpty()) {
                    priceMax = Double.valueOf(stringPriceMax);
                }
                sortField = null;

                Integer selectedIdRadioGroupOther = rgOther.getCheckedRadioButtonId();
                switch (selectedIdRadioGroupOther) {
                    case R.id.radio_price_asc:
                        sortField = "price";
                        sortDirection = "asc";
                        break;
                    case R.id.radio_price_desc:
                        sortField = "price";
                        sortDirection = "desc";
                        break;
                    case R.id.radio_new:
                        sortField = "registrationDate";
                        sortDirection = "desc";
                        break;

                    case R.id.radio_sold_best:
                        sortField = "soldQuantity";
                        sortDirection = "desc";
                        break;
                    case -1:
                        break;
                }

                if(priceMin != null && priceMax != null && (priceMin > priceMax)) {
                    CustomToast.toastCenterTransparent(context, "Min price should be less than max price!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else {
                    drawerLayout.closeDrawer(GravityCompat.END);
                    currentPage = 0;
                    loadSearch();
                }

            }
        });



    }

    private void loadSearch() {
        Bundle bundle = getIntent().getExtras();
        keyword = bundle.getString(App.KEY_SEARCH);
        ++currentPage;
        if(categoryIds.size() > 0) {
            searchViewModel.getSearch(currentPage, PAGE_SIZE, priceMin, priceMax, sortField, sortDirection, categoryIds, keyword);
        }
        else {
            searchViewModel.getSearch(currentPage, PAGE_SIZE, priceMin, priceMax, sortField, sortDirection, null, keyword);
        }
    }


    private void loadCategory() {
        ++ currentPageCategory;
        searchViewModel.getListCategory(currentPageCategory, PAGE_SIZE_CATEGORY);
    }



    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSearch();
    }
}
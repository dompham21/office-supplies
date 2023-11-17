package com.luv2code.shopme.Fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luv2code.shopme.Activity.MainActivity;
import com.luv2code.shopme.Activity.SearchActivity;
import com.luv2code.shopme.Adapter.CategoryAdapter;
import com.luv2code.shopme.Adapter.PosterViewPagerAdapter;
import com.luv2code.shopme.Adapter.ProductAdapter;
import com.luv2code.shopme.Model.Category;
import com.luv2code.shopme.Model.Poster;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Model.User;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class HomeFragment extends Fragment {
    private View view;
    private ViewPager2 viewPagerPoster;
    private CircleIndicator3 circleIndicator;
    private TextView tvSeeAllCategories;
    private RecyclerView rcvNewProducts;
    private RecyclerView rcvMostPurchased;
    private RecyclerView rcvPromotionProducts;
    private RecyclerView rcvAllProducts;
    private RecyclerView rcvCategory;
    private NestedScrollView scrollView;
    private ProgressBar progressBarNewProducts;
    private ProgressBar progressBarPromotionProducts;
    private ProgressBar progressBarMostPurchased;
    private ProgressBar progressBarAllProducts;
    private ProgressBar progressBarCategory;
    private ProductAdapter allProductsAdatper;
    private HomeViewModel homeViewModel;
    private LinearLayout layoutSearch;

    private Toolbar toolbar;

    private boolean isLastPage;
    private int currentPage = 0;
    private int totalPage;
    LocalStorage localStorage;
    Gson gson = new Gson();
    User user;
    private List<Poster> posterList;
    private static final int PAGE_SIZE = 10;


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (posterList == null) return;
            if (viewPagerPoster.getCurrentItem() == posterList.size() - 1) {
                viewPagerPoster.setCurrentItem(0);
            } else {
                viewPagerPoster.setCurrentItem(viewPagerPoster.getCurrentItem() + 1);
            }
        }
    };

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Home");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        setControl();
        setEvent();
        loadPosters();
        loadCategories();
        loadNewProducts();
        loadPromotionProducts();
        loadMostPurchasedProducts();
        loadAllProducts();

        return view;
    }

    private void setControl() {

        toolbar = view.findViewById(R.id.toolbar);

        viewPagerPoster = view.findViewById(R.id.view_pager_poster);
        circleIndicator = view.findViewById(R.id.circle_indicator_poster);
        tvSeeAllCategories = view.findViewById(R.id.tv_see_all_categories);
        scrollView = view.findViewById(R.id.home_scroll_view);
        progressBarAllProducts = view.findViewById(R.id.progress_bar_all_products);
        progressBarNewProducts = view.findViewById(R.id.progress_bar_new_products);
        progressBarMostPurchased = view.findViewById(R.id.progress_bar_most_purchased);
        progressBarCategory = view.findViewById(R.id.progress_bar_category);
        progressBarPromotionProducts = view.findViewById(R.id.progress_bar_promotion_products);

        rcvNewProducts = view.findViewById(R.id.rcv_new_products);
        rcvNewProducts.setHasFixedSize(true);
        rcvNewProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcvNewProducts.setFocusable(false);
        rcvNewProducts.setNestedScrollingEnabled(false);

        rcvPromotionProducts = view.findViewById(R.id.rcv_promotion_products);
        rcvPromotionProducts.setHasFixedSize(true);
        rcvPromotionProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcvPromotionProducts.setFocusable(false);
        rcvPromotionProducts.setNestedScrollingEnabled(false);

        layoutSearch = view.findViewById(R.id.layout_search);

        rcvMostPurchased = view.findViewById(R.id.rcv_most_purchased);
        rcvMostPurchased.setHasFixedSize(true);
        rcvMostPurchased.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcvMostPurchased.setFocusable(false);
        rcvMostPurchased.setNestedScrollingEnabled(false);

        rcvAllProducts = view.findViewById(R.id.rcv_all_products);
        rcvAllProducts.setHasFixedSize(true);
        rcvAllProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcvAllProducts.setFocusable(false);
        rcvAllProducts.setNestedScrollingEnabled(false);

        rcvCategory = view.findViewById(R.id.rcv_category);
        rcvCategory.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvCategory.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setOrientation(gridLayoutManager.HORIZONTAL);
        rcvCategory.setFocusable(false);
        rcvCategory.setNestedScrollingEnabled(false);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        List<Product> productList = new ArrayList<>();
        allProductsAdatper = new ProductAdapter(productList, getContext());
        rcvAllProducts.setAdapter(allProductsAdatper);

    }

    private void setEvent() {
        homeViewModel.getPostersData().observe(getViewLifecycleOwner(), object -> {
            if (object == null) {
                CustomToast.makeText(getActivity(), "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                List<Poster> listPosters = object.getData();
                PosterViewPagerAdapter adapter = new PosterViewPagerAdapter(listPosters);
                viewPagerPoster.setAdapter(adapter);
                circleIndicator.setViewPager(viewPagerPoster);
            }
            else {
                CustomToast.makeText(getActivity(), message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });

        homeViewModel.getListCategoriesData().observe(getViewLifecycleOwner(), object -> {
            if (object == null) {
                CustomToast.makeText(getActivity(), "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                List<Category> listCategories = object.getData();
                CategoryAdapter adapter = new CategoryAdapter(listCategories);
                rcvCategory.setAdapter(adapter);
            }
            else {
                CustomToast.makeText(getActivity(), message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });


        homeViewModel.getNewProductsData().observe(getViewLifecycleOwner(), object -> {
            if (object == null) {
                CustomToast.makeText(getActivity(), "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                List<Product> listProducts = object.getData();
                ProductAdapter productAdapter = new ProductAdapter(listProducts, getContext());
                rcvNewProducts.setAdapter(productAdapter);

            } else {
                CustomToast.makeText(getActivity(), message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });

        homeViewModel.getPromotionProductsData().observe(getViewLifecycleOwner(), object -> {
            if (object == null) {
                CustomToast.makeText(getActivity(), "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                List<Product> listProducts = object.getData();
                ProductAdapter productAdapter = new ProductAdapter(listProducts, getContext());
                rcvPromotionProducts.setAdapter(productAdapter);

            } else {
                CustomToast.makeText(getActivity(), message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });


        homeViewModel.getMostPurchasedProductsData().observe(getViewLifecycleOwner(), object -> {
            if (object == null) {
                CustomToast.makeText(getActivity(), "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                List<Product> listProducts = object.getData();
                ProductAdapter productAdapter = new ProductAdapter(listProducts, getContext());
                rcvMostPurchased.setAdapter(productAdapter);

            } else {
                CustomToast.makeText(getActivity(), message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });

        homeViewModel.getAllProductsData().observe(getViewLifecycleOwner(), object -> {
            if (object == null) {
                CustomToast.makeText(getActivity(), "Server error and please try after sometime!", CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                totalPage = object.getTotalPage();

                List<Product> listProducts = object.getData();
                allProductsAdatper.addData(listProducts);

                if (currentPage >= totalPage) {
                    isLastPage = true;
                }

            } else {
                CustomToast.makeText(getActivity(), message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollView.getChildAt(0).getBottom() <= (scrollView.getHeight() + scrollY)) {
                        if (isLastPage) return;
                        loadAllProducts();
                    }
                }
            });
        }

        homeViewModel.isLoadingNewProducts().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressBarNewProducts.setVisibility(View.VISIBLE);
            } else {
                progressBarNewProducts.setVisibility(View.GONE);
            }
        });

        homeViewModel.isLoadingPromotionProducts().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressBarPromotionProducts.setVisibility(View.VISIBLE);
            } else {
                progressBarPromotionProducts.setVisibility(View.GONE);
            }
        });

        homeViewModel.isLoadingMostPurchased().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressBarMostPurchased.setVisibility(View.VISIBLE);
            } else {
                progressBarMostPurchased.setVisibility(View.GONE);
            }
        });

        homeViewModel.isLoadingAllProducts().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressBarAllProducts.setVisibility(View.VISIBLE);
            } else {
                progressBarAllProducts.setVisibility(View.GONE);
            }
        });

        homeViewModel.isLoadingCategories().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressBarCategory.setVisibility(View.VISIBLE);
            } else {
                progressBarCategory.setVisibility(View.GONE);
            }
        });

        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                DrawerLayout drawerLayout = activity.findViewById(R.id.drawer_layout);
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });



    }


    private void loadPosters() {
        homeViewModel.getPosters();
    }

    private void loadCategories() {
        homeViewModel.getListCategories();
    }

    private void loadMostPurchasedProducts() {
        homeViewModel.getMostPurchasedProducts(PAGE_SIZE);
    }

    private void loadNewProducts() {
        homeViewModel.getNewProducts(PAGE_SIZE);
    }

    private void loadPromotionProducts() {
        homeViewModel.getPromotionProducts();
    }


    private void loadAllProducts() {
        ++currentPage;
        homeViewModel.getAllProducts(currentPage, PAGE_SIZE);
    }

    @Override
    public void onResume() {
        super.onResume();
        currentPage = 0;
        loadAllProducts();
    }
}
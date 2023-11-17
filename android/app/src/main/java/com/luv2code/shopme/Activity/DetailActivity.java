package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.luv2code.shopme.Adapter.ReviewAdapter;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Cart;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.Model.Review;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.CartViewModel;
import com.luv2code.shopme.ViewModel.DetailViewModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;

public class DetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DetailViewModel detailViewModel;
    private CartViewModel cartViewModel;
    private TextView tvName, tvSoldQuantity, tvPrice, tvPriceAfterDiscount, tvDiscount, buttonExpandable, tvBrand, tvCategory, tvBadgeCount;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBarReview;
    private RatingBar ratingBarProduct, ratingBarReview;
    private ImageView imageProduct;
    private AppCompatButton btnAddCart;
    private ExpandableTextView tvDescription;
    private ReviewAdapter reviewAdapter;
    private RecyclerView rcvReview;
    private LinearLayout llayoutHasReview, llayoutNoReview;
    private FrameLayout btnCart;
    private SearchView searchView;
    private boolean isLastPage;
    private int currentPage = 0;
    private int totalPage;
    private int productId;
    LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setControls();
        setEvents();
        loadDetail();
        loadReviews();
        updateCartItemCount();
    }


    private void setControls() {
        Bundle bundle = getIntent().getExtras();
        productId = bundle.getInt(App.PRODUCT_ID);

        toolbar = findViewById(R.id.toolbar);
        tvName = findViewById(R.id.tv_name);
        tvSoldQuantity = findViewById(R.id.tv_sold_quantity);
        tvPrice = findViewById(R.id.tv_price);
        tvBrand = findViewById(R.id.tv_product_brand);
        tvCategory = findViewById(R.id.tv_product_category);
        tvBadgeCount = findViewById(R.id.tv_cart_badge);
        ratingBarProduct = findViewById(R.id.rtb_rate);
        ratingBarReview = findViewById(R.id.rtb_rate_review);

        tvPriceAfterDiscount = findViewById(R.id.tv_price_after_discount);
        tvDiscount = findViewById(R.id.tv_discount);

        searchView = findViewById(R.id.search_view);
        imageProduct = findViewById(R.id.img_product);
        tvDescription = findViewById(R.id.tvDescription);
        buttonExpandable = findViewById(R.id.button_expandable);
        nestedScrollView = findViewById(R.id.nested_scroll_detail);
        progressBarReview = findViewById(R.id.progress_bar_reviews);
        llayoutHasReview = findViewById(R.id.llayout_has_review);
        llayoutNoReview = findViewById(R.id.llayout_no_review);

        btnCart = findViewById(R.id.btn_cart);
        btnAddCart = findViewById(R.id.btn_add_to_cart);
        rcvReview = findViewById(R.id.rcv_reviews);

        rcvReview.setHasFixedSize(true);
        rcvReview.setLayoutManager(new LinearLayoutManager(this));
        rcvReview.setFocusable(false);
        rcvReview.setNestedScrollingEnabled(false);

        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        List<Review> listReviews = new ArrayList<>();

        reviewAdapter = new ReviewAdapter(listReviews, this, ReviewAdapter.TagInDetailProduct, new ReviewAdapter.OnItemActionListener() {
            @Override
            public void onProductClick(Product product) {

            }

            @Override
            public void onBtnEditClick(Review review) {

            }
        });
        rcvReview.setAdapter(reviewAdapter);
        localStorage = new LocalStorage(getApplicationContext());


    }

    private void setEvents() {

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        buttonExpandable.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                buttonExpandable.setText(tvDescription.isExpanded() ? R.string.see_more : R.string.collapse);
                tvDescription.toggle();
            }
        });

        detailViewModel.getDetailProductData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                Product product = object.getData();
                if (product == null) return;
                Picasso.get().load(product.getImage()).into(imageProduct);
                tvName.setText(product.getName());

                Integer discount = product.getDiscount();
                Double priceAfterDiscount = product.getPriceAfterDiscount();
                if (discount != null && discount > 0 && priceAfterDiscount != null) {
                    tvDiscount.setVisibility(View.VISIBLE);
                    tvPrice.setVisibility(View.VISIBLE);

                    tvDiscount.setText(String.format("-%s%%", product.getDiscount()));
                    tvPrice.setText(String.format("%s đ",App.formatNumberMoney(product.getPrice())));
                    tvPriceAfterDiscount.setText(String.format("%s đ", App.formatNumberMoney(product.getPriceAfterDiscount())));
                }
                else {
                    tvDiscount.setVisibility(View.INVISIBLE);
                    tvPriceAfterDiscount.setText(String.format("%s đ",App.formatNumberMoney(product.getPrice())));
                    tvPrice.setVisibility(View.GONE);
                }

                int soldQuantity = product.getSoldQuantity();
                if (soldQuantity > 0) {
                    tvSoldQuantity.setVisibility(View.VISIBLE);
                    tvSoldQuantity.setText(String.format("%s sold", product.getSoldQuantity()));
                }
                else {
                    tvSoldQuantity.setVisibility(View.INVISIBLE);
                }


                tvCategory.setText(product.getCategory().getName());
                tvBrand.setText(product.getBrand().getName());
                if(product.getRate() !=  null) {
                    ratingBarProduct.setRating(product.getRate());
                    ratingBarProduct.setVisibility(View.VISIBLE);
                    ratingBarReview.setVisibility(View.VISIBLE);
                    ratingBarReview.setRating(product.getRate());
                }
                else {
                    ratingBarProduct.setVisibility(View.GONE);
                    ratingBarReview.setVisibility(View.GONE);
                }


            }
            else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });

        detailViewModel.getReviewsData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                totalPage = object.getTotalPage();

                if(totalPage == 0 ) {
                    llayoutNoReview.setVisibility(View.VISIBLE);
                    llayoutHasReview.setVisibility(View.GONE);
                }

                List<Review> listReviews = object.getData();

                reviewAdapter.addData(listReviews);

                if (currentPage >= totalPage) {
                    isLastPage = true;
                }

            } else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });


        cartViewModel.getAddToCartData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }
            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                Cart cart = object.getData();
                CustomToast.toastCenterTransparent(this, "Add to cart success!", CustomToast.LENGTH_SHORT, CustomToast.SUCCESS).show();
                App.cartAdapter.updateItem(cart);
                updateCartItemCount();
            } else {
                CustomToast.makeText(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (nestedScrollView.getChildAt(0).getBottom() <= (nestedScrollView.getHeight() + scrollY)) {
                        if (isLastPage) return;
                        loadReviews();
                    }
                }
            });
        }

        detailViewModel.isLoadingReviews().observe(this, isLoading -> {
            if (isLoading) {
                progressBarReview.setVisibility(View.VISIBLE);
            } else {
                progressBarReview.setVisibility(View.GONE);
            }
        });

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.checkUserLogged()) {
                    cartViewModel.addToCart(App.getAuthorization(), productId);
                }
                else {
                    CustomToast.toastCenterTransparent(view.getContext(), getString(R.string.must_login), CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CartActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadDetail() {
        detailViewModel.getDetailProduct(productId);
    }

    private void loadReviews() {
        ++currentPage;

        detailViewModel.getReviews(productId,currentPage);
    }


    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void updateCartItemCount() {
        if(!App.checkUserLogged()) {
            tvBadgeCount.setVisibility(View.INVISIBLE);
        }
        else {
            if (App.cartAdapter == null) return;
            if (tvBadgeCount == null) return;
            int count = App.cartAdapter.getItemCount();

            if (count > 0) {
                tvBadgeCount.setVisibility(View.VISIBLE);
                tvBadgeCount.setText(String.valueOf(count));
            }
            else {
                tvBadgeCount.setVisibility(View.INVISIBLE);
            }
        }

    }


}
package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.luv2code.shopme.Adapter.ReviewAdapter;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.Model.Review;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.ReviewViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Context context = this;
    private LocalStorage localStorage;
    private ReviewViewModel reviewViewModel;
    private RecyclerView rcvReview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        setControls();
        setEvents();
        loadReviews();

    }


    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        reviewViewModel.getListReviewByUserData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                List<Review> listReviews = object.getData();

                ReviewAdapter reviewAdapter = new ReviewAdapter(listReviews, context, ReviewAdapter.TagInReviewList, new ReviewAdapter.OnItemActionListener() {
                    @Override
                    public void onProductClick(Product product) {
                        Intent intent = new Intent(context, DetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt(App.PRODUCT_ID, product.getId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void onBtnEditClick(Review review) {
                        Intent intent = new Intent(context, EditReviewActivity.class);
                        intent.putExtra(App.KEY_REVIEW, review.getId());
                        startActivity(intent);
                    }
                });
                rcvReview.setAdapter(reviewAdapter);

            } else if(result == 0 && message != null) {
                CustomToast.makeText(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });
    }

    private void setControls() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Review");

        rcvReview = findViewById(R.id.rcv_reviews);
        rcvReview.setHasFixedSize(true);
        rcvReview.setLayoutManager(new LinearLayoutManager(this));
        rcvReview.setFocusable(false);
        rcvReview.setNestedScrollingEnabled(false);

        localStorage = new LocalStorage(App.getContext());
        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

    }

    private void loadReviews() {
        String accessToken = App.getAuthorization();
        reviewViewModel.getListReviewUser(accessToken);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReviews();
    }
}
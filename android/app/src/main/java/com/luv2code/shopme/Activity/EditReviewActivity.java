package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Address;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.Model.Review;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Request.ReviewRequest;
import com.luv2code.shopme.Utils.CustomTextInputLayout;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.DetailViewModel;
import com.luv2code.shopme.ViewModel.ReviewViewModel;
import com.squareup.picasso.Picasso;

public class EditReviewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Context context = this;
    private LocalStorage localStorage;
    private TextView tvProductName;
    private ImageView imgProduct;
    private RatingBar ratingBar;
    private EditText edtComment;
    private AppCompatButton btnReview;
    private CustomTextInputLayout layoutEdtComment;
    private ReviewViewModel reviewViewModel;
    private DetailViewModel detailViewModel;
    private Integer currentReviewId;
    private Review currentReview;
    private Product product;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_review);

        Bundle bundle = getIntent().getExtras();

        currentReviewId = bundle.getInt(App.KEY_REVIEW, Integer.MIN_VALUE);
        product = (Product) bundle.getSerializable(App.KEY_PRODUCT_REVIEW);

        setControls();
        setEvents();
        if(currentReviewId != Integer.MIN_VALUE) {
            loadReviewDetail();
        }

    }




    private void setEvents() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        edtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String comment = charSequence.toString();

                if (comment.isEmpty()) {
                    layoutEdtComment.setError("Please enter a comment in the field!");
                }
                else if(comment.length() > 300) {
                    layoutEdtComment.setError("Comment should be less than 300 characters!");
                }
                else {
                    layoutEdtComment.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edtComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String comment = edtComment.getText().toString().trim();
                if(!b && comment.isEmpty()) {
                    layoutEdtComment.setError("Please enter a comment in the field!");
                }
                else if(!b && comment.length() > 300) {
                    layoutEdtComment.setError("Comment should be less than 300 characters!");
                }
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = edtComment.getText().toString().trim();
                Integer vote = Math.round(ratingBar.getRating());

                if(comment.isEmpty()) {
                    CustomToast.toastCenterTransparent(view.getContext(), "Please enter a comment in the field!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(comment.length() > 300) {
                    CustomToast.toastCenterTransparent(view.getContext(), "Comment should be less than 300 characters!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                }
                else if(currentReview != null){ //edit
                    String accessToken = App.getAuthorization();
                    ReviewRequest reviewRequest = new ReviewRequest(currentReview.getProduct().getId(), vote, comment);
                    reviewViewModel.updateReview(accessToken, reviewRequest, currentReview.getId());
                }
                else if(product != null) {// add new
                    String accessToken = App.getAuthorization();
                    ReviewRequest reviewRequest = new ReviewRequest(product.getId(), vote, comment);
                    reviewViewModel.addNewReview(accessToken, reviewRequest);
                }
            }
        });

        reviewViewModel.getAddNewReviewData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && message != null) {
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_SHORT, CustomToast.SUCCESS).show();
                finish();
            }
            else if(result == 0 && message != null) {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        reviewViewModel.getUpdateReviewData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && message != null) {
                CustomToast.toastCenterTransparent(this, message, CustomToast.LENGTH_SHORT, CustomToast.SUCCESS).show();
                finish();
            }
            else if(result == 0 && message != null) {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        reviewViewModel.getDetailReviewData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                currentReview = object.getData();
                if(currentReview != null) {
                    ratingBar.setRating(currentReview.getVote());
                    edtComment.setText(currentReview.getComment());
                    tvProductName.setText(currentReview.getProduct().getName());
                    Picasso.get().load(currentReview.getProduct().getImage()).into(imgProduct);
                }
            }
            else if(result == 0 && message != null) {
                CustomToast.makeText(this, message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });
    }

    private void setControls() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Review");

        tvProductName = findViewById(R.id.tv_product_name);
        imgProduct = findViewById(R.id.img_product);
        edtComment = findViewById(R.id.edt_comment);
        btnReview = findViewById(R.id.btn_review);
        ratingBar = findViewById(R.id.rating_bar);
        layoutEdtComment = findViewById(R.id.layout_edt_comment);

        localStorage = new LocalStorage(App.getContext());



        if (product != null){
            tvProductName.setText(product.getName());
            Picasso.get().load(product.getImage()).into(imgProduct);
        }

        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
    }

    private void loadReviewDetail(){
        reviewViewModel.getDetailReview(App.getAuthorization(), currentReviewId);
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

    @Override
    protected void onResume() {
        super.onResume();

        if(currentReviewId != Integer.MIN_VALUE) {
            loadReviewDetail();
        }

    }
}
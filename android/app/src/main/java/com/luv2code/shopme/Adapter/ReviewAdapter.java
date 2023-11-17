package com.luv2code.shopme.Adapter;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.luv2code.shopme.Model.Order;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.Model.Review;
import com.luv2code.shopme.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.CommentViewHolder>{
    private List<Review> listReviews;
    private Context context;
    private Integer tag;
    public static Integer TagInDetailProduct = 0;
    public static Integer TagInReviewList = 1;

    private OnItemActionListener onItemActionListener;


    public interface OnItemActionListener {
        void onProductClick(Product product);
        void onBtnEditClick(Review review);
    }

    public void setOnItemClickListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    public ReviewAdapter(List<Review> listReviews, Context context, Integer tag, OnItemActionListener onItemActionListener) {
        this.listReviews = listReviews;
        this.context = context;
        this.tag = tag;
        this.onItemActionListener = onItemActionListener;
    }

    public void addData(List<Review> listReviewsNew) {
        this.listReviews.addAll(listReviewsNew);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);

        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Review review = listReviews.get(position);
        holder.tvUserName.setText(review.getUser().getName());

        Picasso.get()
                .load(review.getUser().getAvatar())
                .into(holder.imgUser);
        holder.rtbRate.setRating(review.getVote());
        holder.tvComment.setText(review.getComment());

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");//formating according to my need
        String date = formatter.format(review.getDate());
        holder.tvDate.setText(date);
        if(tag.equals(TagInDetailProduct)) {
            holder.layoutProduct.setVisibility(View.GONE);
            holder.btnEdit.setVisibility(View.GONE);
        }
        else if(tag.equals(TagInReviewList)){
            holder.layoutProduct.setVisibility(View.VISIBLE);
            holder.tvProductName.setText(review.getProduct().getName());
            holder.btnEdit.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(review.getProduct().getImage())
                    .into(holder.imgProduct);

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemActionListener != null){
                        onItemActionListener.onBtnEditClick(review);
                    }
                }
            });

            holder.layoutProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemActionListener != null){
                        onItemActionListener.onProductClick(review.getProduct());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listReviews != null) {
            return listReviews.size();
        }
        return 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser, imgProduct;
        private TextView tvComment, tvUserName, tvDate, tvProductName;
        private RatingBar rtbRate;
        private LinearLayout layoutProduct;
        private AppCompatButton btnEdit;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_avt);
            tvComment = itemView.findViewById(R.id.tv_comment);
            tvComment.setMovementMethod(ScrollingMovementMethod.getInstance());
            tvUserName = itemView.findViewById(R.id.tv_username);
            tvDate = itemView.findViewById(R.id.tv_date);
            rtbRate = itemView.findViewById(R.id.rtb_rate_user);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            layoutProduct = itemView.findViewById(R.id.layout_product);
            btnEdit = itemView.findViewById(R.id.btn_edit);
        }
    }
}

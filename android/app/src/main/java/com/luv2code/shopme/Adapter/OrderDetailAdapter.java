package com.luv2code.shopme.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Order;
import com.luv2code.shopme.Model.OrderDetail;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;

public class OrderDetailAdapter  extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>{
    private List<OrderDetail> listOrderDetail;
    private OnItemActionListener onItemActionListener;
    private Integer orderStatusId;
    private String tag;
    public static String tagInOrderActivity = "order_activity";
    public static String tagInOrderDetailActivity = "order_detail_activity";


    public interface OnItemActionListener {
        void onClick();
        void onBtnReviewClick(Product product);
    }

    public void setOnItemClickListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }



    public OrderDetailAdapter(List<OrderDetail> listOrderDetail, String tag,Integer orderStatusId, OnItemActionListener onItemActionListener) {
        this.listOrderDetail = listOrderDetail;
        this.onItemActionListener = onItemActionListener;
        this.tag = tag;
        this.orderStatusId = orderStatusId;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderDetailAdapter.OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        OrderDetail orderDetail = listOrderDetail.get(position);

        holder.tvName.setText(orderDetail.getProduct().getName());
        holder.tvPrice.setText(App.formatNumberMoney(orderDetail.getProduct().getPrice()));
        Picasso.get().load(orderDetail.getProduct().getImage()).into(holder.imgProduct);
        holder.tvQuantity.setText("x".concat(String.valueOf(orderDetail.getQuantity())));


        holder.layoutProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemActionListener.onClick();
            }
        });

        if(tag.equals(tagInOrderDetailActivity)) {
            if(orderDetail.getHasReviewed()) {
                holder.layoutReview.setVisibility(View.GONE);
                holder.layoutHasReview.setVisibility(View.VISIBLE);

            }
            else {
                holder.layoutReview.setVisibility(View.VISIBLE);
                holder.layoutHasReview.setVisibility(View.GONE);
            }
        }
        else if(tag.equals(tagInOrderActivity)) {
            holder.layoutReview.setVisibility(View.GONE);
        }


        if(orderStatusId != 4) {
            holder.btnReview.setVisibility(View.GONE);
            holder.layoutHasReview.setVisibility(View.GONE);
        }
        else {
            holder.btnReview.setVisibility(View.VISIBLE);
        }


        holder.btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemActionListener.onBtnReviewClick(orderDetail.getProduct());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listOrderDetail != null) {
            return listOrderDetail.size();
        }
        return 0;
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvPrice, tvQuantity;
        private LinearLayout layoutProduct;
        private RelativeLayout layoutReview, layoutHasReview;
        private AppCompatButton btnReview;
        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            imgProduct = itemView.findViewById(R.id.img_product_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            layoutProduct = itemView.findViewById(R.id.layout_product);
            layoutReview = itemView.findViewById(R.id.layout_btn_review);
            btnReview = itemView.findViewById(R.id.btn_review);
            layoutHasReview = itemView.findViewById(R.id.layout_btn_has_review);
        }
    }
}

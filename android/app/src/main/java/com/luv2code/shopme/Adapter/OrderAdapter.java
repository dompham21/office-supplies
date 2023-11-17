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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Order;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    private List<Order> listOrders;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private OnItemActionListener onItemActionListener;


    public interface OnItemActionListener {
        void onClick(Order order);

        void onButtonRepurchaseClick(Order order);
        void onButtonReviewClick(Order order);
    }

    public void setOnItemClickListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    public OrderAdapter(List<Order> listOrders, OnItemActionListener onItemActionListener) {
        this.listOrders = listOrders;
        this.onItemActionListener = onItemActionListener;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = listOrders.get(position);
        holder.tvTotal.setText(App.formatNumberMoney(order.getTotalPrice()));
        holder.tvDetailQuantity.setText(String.valueOf(order.getOrderDetails().size()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rcvOrderDetail.getContext(),RecyclerView.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(order.getOrderDetails().size());

        holder.rcvOrderDetail.setLayoutManager(layoutManager);
        holder.rcvOrderDetail.setHasFixedSize(true);
        holder.rcvOrderDetail.setFocusable(false);
        holder.rcvOrderDetail.setNestedScrollingEnabled(false);


        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(order.getOrderDetails(), OrderDetailAdapter.tagInOrderActivity,order.getStatus().getId(), new OrderDetailAdapter.OnItemActionListener() {
            @Override
            public void onClick() {
                onItemActionListener.onClick(order);
            }

            @Override
            public void onBtnReviewClick(Product product) {

            }
        });

        holder.rcvOrderDetail.setRecycledViewPool(viewPool);
        holder.rcvOrderDetail.setAdapter(orderDetailAdapter);
        if(order.getStatus().getId().equals(4)) {
            holder.layoutDeliverySuccess.setVisibility(View.VISIBLE);
            holder.layoutBtnRepurchase.setVisibility(View.GONE);
        }
        else if(order.getStatus().getId().equals(5)) {
            holder.layoutDeliverySuccess.setVisibility(View.GONE);
            holder.layoutBtnRepurchase.setVisibility(View.VISIBLE);
        }



        holder.layoutOrder.setOnClickListener(view -> {
            onItemActionListener.onClick(order);
        });


        holder.btnRepurchase.setOnClickListener(view -> {
            onItemActionListener.onButtonRepurchaseClick(order);
        });

    }

    @Override
    public int getItemCount() {
        if (listOrders != null) {
            return listOrders.size();
        }
        return 0;
    }



    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private LinearLayout layoutOrder;
        private RecyclerView rcvOrderDetail;
        private TextView tvDetailQuantity, tvTotal;
        private AppCompatButton btnRepurchase;
        private RelativeLayout layoutDeliverySuccess, layoutBtnReview, layoutBtnRepurchase;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            layoutOrder = itemView.findViewById(R.id.layout_order);
            rcvOrderDetail = itemView.findViewById(R.id.rcv_order_detail);
            tvDetailQuantity = itemView.findViewById(R.id.text_quantity);
            tvTotal = itemView.findViewById(R.id.tv_total);
            btnRepurchase = itemView.findViewById(R.id.btn_repurchase);
            layoutDeliverySuccess = itemView.findViewById(R.id.layout_delivery_success);
            layoutBtnRepurchase = itemView.findViewById(R.id.layout_btn_repurchase);

        }
    }
}

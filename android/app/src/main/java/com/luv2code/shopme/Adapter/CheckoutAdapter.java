package com.luv2code.shopme.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Cart;
import com.luv2code.shopme.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>{
    private List<Cart> listCartSelected;

    public CheckoutAdapter(List<Cart> listCartSelected) {
        this.listCartSelected = listCartSelected;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_checkout, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        Cart cart = listCartSelected.get(position);
        Picasso.get().load( cart.getProduct().getImage()).into(holder.imgProduct);
        holder.tvName.setText(cart.getProduct().getName());

        Integer discount = cart.getProduct().getDiscount();
        Double priceAfterDiscount = cart.getProduct().getPriceAfterDiscount();
        Double total;

        if (discount != null && discount > 0 && priceAfterDiscount != null) {
            holder.tvPrice.setVisibility(View.VISIBLE);

            holder.tvPrice.setText(String.format("%s đ",App.formatNumberMoney( cart.getProduct().getPrice())));
            holder.tvPriceAfterDiscount.setText(String.format("%s đ", App.formatNumberMoney( cart.getProduct().getPriceAfterDiscount())));
            total = cart.getProduct().getPriceAfterDiscount() * cart.getQuantity();
        }
        else {
            holder.tvPriceAfterDiscount.setText(String.format("%s đ",App.formatNumberMoney( cart.getProduct().getPrice())));
            holder.tvPrice.setVisibility(View.GONE);
            total = cart.getProduct().getPrice() * cart.getQuantity();
        }

        holder.tvTotal.setText(String.format("%s đ", App.formatNumberMoney(total)));
        holder.tvQuantity.setText(String.format("x%s" ,cart.getQuantity()));
        holder.tvQuantityText.setText(String.valueOf(cart.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if (listCartSelected != null) {
            return listCartSelected.size();
        }
        return 0;
    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvPrice, tvQuantity, tvTotal, tvQuantityText, tvPriceAfterDiscount;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            imgProduct = itemView.findViewById(R.id.img_product_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvQuantityText = itemView.findViewById(R.id.text_quantity);
            tvTotal = itemView.findViewById(R.id.tv_total);
            tvPriceAfterDiscount = itemView.findViewById(R.id.tv_price_after_discount);
        }
    }

}

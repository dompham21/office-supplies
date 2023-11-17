package com.luv2code.shopme.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.luv2code.shopme.Activity.DetailActivity;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public void addData(List<Product> productList) {
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    public void setData(List<Product> productList) {
        this.productList.clear();
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        if (product == null) return;
        Picasso.get().load(product.getImage()).into(holder.imgProduct);
        holder.tvName.setText(product.getName());
        int soldQuantity = product.getSoldQuantity();
        if (soldQuantity > 0) {
            holder.tvSoldQuantity.setVisibility(View.VISIBLE);
            holder.tvSoldQuantity.setText(String.format("%s sold", product.getSoldQuantity()));
        }
        else {
            holder.tvSoldQuantity.setVisibility(View.INVISIBLE);
        }

        Integer discount = product.getDiscount();
        Double priceAfterDiscount = product.getPriceAfterDiscount();
        if (discount != null && discount > 0 && priceAfterDiscount != null) {
            holder.tvDiscount.setVisibility(View.VISIBLE);
            holder.tvPrice.setVisibility(View.VISIBLE);

            holder.tvDiscount.setText(String.format("-%s%%", product.getDiscount()));
            holder.tvPrice.setText(String.format("%s đ",App.formatNumberMoney(product.getPrice())));
            holder.tvPriceAfterDiscount.setText(String.format("%s đ", App.formatNumberMoney(product.getPriceAfterDiscount())));
        }
        else {
            holder.tvDiscount.setVisibility(View.INVISIBLE);
            holder.tvPriceAfterDiscount.setText(String.format("%s đ",App.formatNumberMoney(product.getPrice())));
            holder.tvPrice.setVisibility(View.GONE);
        }
        if(product.getRate() != null) {
            holder.rtbRate.setRating(product.getRate());
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(App.PRODUCT_ID, product.getId());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvSoldQuantity, tvDiscount, tvPrice, tvPriceAfterDiscount;
        private RatingBar rtbRate;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            imgProduct = itemView.findViewById(R.id.img_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSoldQuantity = itemView.findViewById(R.id.tv_sold_quantity);
            tvPrice = itemView.findViewById(R.id.tv_price);
            rtbRate = itemView.findViewById(R.id.rtb_rate);
            tvPriceAfterDiscount = itemView.findViewById(R.id.tv_price_after_discount);
            tvDiscount = itemView.findViewById(R.id.tv_discount);
        }
    }
}

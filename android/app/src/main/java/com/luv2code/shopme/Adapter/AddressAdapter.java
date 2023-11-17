package com.luv2code.shopme.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.luv2code.shopme.Activity.NewAddressActivity;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Address;
import com.luv2code.shopme.Model.Cart;
import com.luv2code.shopme.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder>{
    private List<Address> listAddress;

    private OnItemActionListener onItemActionListener;

    public interface OnItemActionListener {
        void onClick(Address address);
    }

    public AddressAdapter(List<Address> listAddress) {
        this.listAddress = listAddress;
    }


    public AddressAdapter(List<Address> listAddress, OnItemActionListener onItemActionListener) {
        this.listAddress = listAddress;
        this.onItemActionListener = onItemActionListener;
    }

    public OnItemActionListener getOnItemActionListener() {
        return onItemActionListener;
    }

    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }


    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressAdapter.AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = listAddress.get(position);

        holder.tvName.setText(address.getName());
        holder.tvPhone.setText(address.getPhone());
        holder.tvSpecific.setText(address.getSpecificAddress());
        holder.tvAddress.setText(String.format("%s, %s, %s",
                address.getWard().getFullName(), address.getWard().getDistrict().getFullName(),
                address.getWard().getDistrict().getProvince().getFullName()));

        if(address.isDefault_address()) {
            holder.tvDefault.setVisibility(View.VISIBLE);
        }
        else {
            holder.tvDefault.setVisibility(View.GONE);
        }

        holder.layoutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemActionListener !=  null) {
                    onItemActionListener.onClick(address);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        if (listAddress != null) {
            return listAddress.size();
        }
        return 0;
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView tvName, tvPhone, tvSpecific, tvAddress, tvDefault;
        private LinearLayout layoutAddress;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            layoutAddress = itemView.findViewById(R.id.layout_address);
            tvName = itemView.findViewById(R.id.tv_address_name);
            tvPhone = itemView.findViewById(R.id.tv_address_phone);
            tvSpecific = itemView.findViewById(R.id.tv_address_specific);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvDefault = itemView.findViewById(R.id.tv_default);
        }
    }
}

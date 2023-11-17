package com.luv2code.shopme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luv2code.shopme.Model.District;
import com.luv2code.shopme.Model.Province;
import com.luv2code.shopme.Model.Ward;
import com.luv2code.shopme.R;

import java.util.List;

public class WardAdapter extends ArrayAdapter<Ward> {
    public WardAdapter(Context context, int resource, List<Ward> objects ) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tv_selected);

        Ward ward = this.getItem(position);

        if(ward != null) {
            tvSelected.setText(ward.getFullName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_province, parent, false);
        TextView tvItem = convertView.findViewById(R.id.tv_item);
        String hintWard = parent.getContext().getResources().getString(R.string.hint_ward);
        Ward ward = this.getItem(position);

        if(ward != null) {
            if (this.getItem(position).getFullName().equals(hintWard)) {
                tvItem.setVisibility(View.GONE);
            } else {
                tvItem.setText(ward.getFullName());
            }
        }
        return convertView;
    }

    public void setData(List<Ward> listWard) {
        clear();
        addAll(listWard);
        notifyDataSetChanged();
    }
}

package com.luv2code.shopme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luv2code.shopme.Model.Province;
import com.luv2code.shopme.R;

import java.util.Collection;
import java.util.List;

public class ProvinceAdapter extends ArrayAdapter<Province> {
    public ProvinceAdapter(Context context, int resource, List<Province> objects ) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tv_selected);

        Province province = this.getItem(position);

        if(province != null) {
            tvSelected.setText(province.getFullName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_province, parent, false);
        TextView tvItem = convertView.findViewById(R.id.tv_item);
        String hintProvince = parent.getContext().getResources().getString(R.string.hint_province);

        Province province = this.getItem(position);

        if(province != null) {
            if (this.getItem(position).getFullName().equals(hintProvince)) {
                tvItem.setVisibility(View.GONE);
            } else {
                tvItem.setText(province.getFullName());
            }
        }
        return convertView;
    }

    public void setData(List<Province> listProvince) {
        clear();
        addAll(listProvince);
        notifyDataSetChanged();
    }

    public void clearData() {
        clear();
    }


}

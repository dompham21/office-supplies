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
import com.luv2code.shopme.R;

import java.util.List;

public class DistrictAdapter extends ArrayAdapter<District> {
    public DistrictAdapter(Context context, int resource, List<District> objects ) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tv_selected);

        District district = this.getItem(position);

        if(district != null) {
            tvSelected.setText(district.getFullName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_province, parent, false);
        TextView tvItem = convertView.findViewById(R.id.tv_item);
        String hintDistrict = parent.getContext().getResources().getString(R.string.hint_district);
        District district = this.getItem(position);

        if(district != null) {
            if (this.getItem(position).getFullName().equals(hintDistrict)) {
                tvItem.setVisibility(View.GONE);
            } else {
                tvItem.setText(district.getFullName());
            }
        }

        return convertView;
    }

    public void setData(List<District> listDistrict) {
        clear();
        addAll(listDistrict);
        notifyDataSetChanged();
    }
}

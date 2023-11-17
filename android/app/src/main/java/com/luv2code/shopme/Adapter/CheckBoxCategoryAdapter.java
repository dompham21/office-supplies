package com.luv2code.shopme.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luv2code.shopme.Model.Cart;
import com.luv2code.shopme.Model.Category;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.R;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxCategoryAdapter extends RecyclerView.Adapter<CheckBoxCategoryAdapter.CheckBoxCategoryViewHolder> {
    List<Category> listCategory;
    private final List<Integer> selectedCategoryList;

    private OnItemActionListener onItemActionListener;

    public interface OnItemActionListener {
        void onCheckBoxCheckedChanged(Integer categoryId, Boolean b);
    }

    public void setOnItemClickListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    public CheckBoxCategoryAdapter(List<Category> listCategory) {
        this.listCategory = listCategory;
        this.selectedCategoryList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void addData(List<Category> listCategory) {
        this.listCategory.addAll(listCategory);
        notifyDataSetChanged();
    }

    public List<Integer> getSelectedCategoryList() {
        return selectedCategoryList;
    }

    public Integer getSelectedCategoryAt(int position) {
        return selectedCategoryList.get(position);
    }
    @NonNull
    @Override
    public CheckBoxCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_filter, parent, false);
        return new CheckBoxCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckBoxCategoryViewHolder holder, int position) {
        Category category = listCategory.get(position);
        holder.checkbox.setText(category.getName());

        holder.checkbox.setOnCheckedChangeListener((compoundButton, b) -> {
            selectedCategoryList.remove(category.getId());
            if (b) {
                selectedCategoryList.add(category.getId());
            }
            if(onItemActionListener != null) {
                onItemActionListener.onCheckBoxCheckedChanged(category.getId(), b);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(listCategory != null) {
            return listCategory.size();
        }
        return 0;
    }

    public class CheckBoxCategoryViewHolder  extends RecyclerView.ViewHolder {
        private View itemView;
        private CheckBox checkbox;

        public CheckBoxCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            checkbox = itemView.findViewById(R.id.checkbox_category);

        }
    }


}

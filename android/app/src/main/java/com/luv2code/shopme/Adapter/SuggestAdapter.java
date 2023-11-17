package com.luv2code.shopme.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luv2code.shopme.R;

import java.util.List;

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.SuggestViewHolder> {
    private List<String> listSuggest;
    private OnItemActionListener onItemActionListener;

    public interface OnItemActionListener {
        void onClick(String suggest);
    }

    public SuggestAdapter(List<String> listSuggest, OnItemActionListener onItemActionListener) {
        this.listSuggest = listSuggest;
        this.onItemActionListener = onItemActionListener;
    }

    @NonNull
    @Override
    public SuggestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggest, parent, false);
        return new SuggestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestViewHolder holder, int position) {
        String suggest = listSuggest.get(position);
        holder.tvName.setText(suggest);
        holder.layoutSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemActionListener != null) {
                    onItemActionListener.onClick(suggest);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if(listSuggest != null) {
            return listSuggest.size();
        }
        return 0;
    }

    public class SuggestViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView tvName;
        private RelativeLayout layoutSuggest;

        public SuggestViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            layoutSuggest = itemView.findViewById(R.id.layout_suggest);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}

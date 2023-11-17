package com.luv2code.shopme.Adapter;


import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Cart;
import com.luv2code.shopme.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Cart> cartList;
    private final List<Cart> selectedCartList;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private OnItemActionListener onItemActionListener;

    public interface OnItemActionListener {
        void onClick(Cart cart);

        void onCheckBoxCheckedChanged(Cart cart);

        void onButtonMinusClick(Cart cart);

        void onButtonPlusClick(Cart cart);

        void onButtonDeleteClick(Cart cart);

        void onEditTextQuantityLostFocus(Cart cart, EditText editText);

        void onCartListDataChanged();
    }

    public void setOnItemClickListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    public List<Cart> getSelectedCartList() {
        return selectedCartList;
    }


    public int getSelectedProductCount() {
        return selectedCartList.size();
    }

    public CartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
        this.selectedCartList = new ArrayList<>();
        notifyDataSetChanged();
        if (onItemActionListener != null) {
            onItemActionListener.onCartListDataChanged();
        }
    }

    public void setData(List<Cart> cartList) {
        this.cartList.clear();
        this.cartList.addAll(cartList);
        List<Cart> list = new ArrayList<>();
        for (int i = 0; i < cartList.size(); ++i) {
            for (int j = 0; j < selectedCartList.size(); ++j) {
                if (Objects.equals(cartList.get(i).getId(), selectedCartList.get(j).getId())) {
                    list.add(cartList.get(i));
                    break;
                }
            }
        }
        selectedCartList.clear();
        selectedCartList.addAll(list);
        notifyDataSetChanged();
        if (onItemActionListener != null) {
            onItemActionListener.onCartListDataChanged();
        }
    }



    public int getPosition(int productId) {
        for (int i = 0; i < cartList.size(); ++i) {
            if (cartList.get(i).getProduct().getId() == productId) {
                return i;
            }
        }
        return -1;
    }

    private double getCartItemPayment(Cart cart) {
        Integer discount = cart.getProduct().getDiscount();
        if(discount != null && discount > 0) {
            Double priceAfterDiscount = cart.getProduct().getPriceAfterDiscount();
            return priceAfterDiscount * cart.getQuantity();
        }
        else {
            Double price = cart.getProduct().getPrice();
            return price * cart.getQuantity();
        }
    }

    public double getSelectedCartItemTotalPayment() {
        double total = 0;
        for (Cart cart : selectedCartList) {
            total += getCartItemPayment(cart);
        }
        return total;
    }

    public void addItem(Cart cart) {
        cartList.add(cart);
        notifyDataSetChanged();
        if (onItemActionListener != null) {
            onItemActionListener.onCartListDataChanged();
        }
    }

    public void updateItem(Cart cart) {
        int position = getPosition(cart.getProduct().getId()); // vi tri cart hien tai
        if (position == -1) { // Neu chua co thi them
            addItem(cart);
        } else {
            Cart oldCart = cartList.get(position);
            cartList.set(position, cart);
            // Update selectedCartList
            if (selectedCartList.contains(oldCart)) {
                selectedCartList.remove(oldCart);
                selectedCartList.add(cart);
            }
            notifyDataSetChanged();
            if (onItemActionListener != null) {
                onItemActionListener.onCartListDataChanged();
            }
        }
    }

    public void removeItem(Cart cart) {
        int position = getPosition(cart.getProduct().getId());
        if (position == -1) return;
        Cart oldCart = cartList.get(position);
        cartList.remove(position);
        if (selectedCartList.contains(oldCart)) {
            selectedCartList.remove(oldCart);
        }
        notifyDataSetChanged();
        if (onItemActionListener != null) {
            onItemActionListener.onCartListDataChanged();
        }
    }

    public void setCheckedAllItems(boolean isChecked) {
        selectedCartList.clear();
        if (isChecked) {
            selectedCartList.addAll(cartList);
        }
        notifyDataSetChanged();
    }

    public boolean isAllCartItemSelected() {
        return selectedCartList.containsAll(cartList);
    }

    public boolean hasSelectedProduct() {
        return selectedCartList.size() > 0;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        if (cart == null) return;
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(cart.getId()));

        Picasso.get().load( cart.getProduct().getImage()).into(holder.imgProduct);
        holder.tvName.setText(cart.getProduct().getName());
        holder.tvPrice.setText(String.format("%s đ", App.formatNumberMoney(cart.getProduct().getPrice())));

        Integer discount = cart.getProduct().getDiscount();
        Double priceAfterDiscount = cart.getProduct().getPriceAfterDiscount();
        if (discount != null && discount > 0 && priceAfterDiscount != null) {
            holder.tvDiscount.setVisibility(View.VISIBLE);

            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvDiscount.setText(String.format("-%s%%", discount));

            holder.tvPrice.setText(String.format("%s đ", App.formatNumberMoney(cart.getProduct().getPrice())));
            holder.tvPriceAfterDiscount.setText(String.format("%s đ",App.formatNumberMoney(priceAfterDiscount)));
        }
        else {
            holder.tvDiscount.setVisibility(View.INVISIBLE);
            holder.tvPriceAfterDiscount.setText(String.format("%s đ", App.formatNumberMoney(cart.getProduct().getPrice())));
            holder.tvPrice.setVisibility(View.GONE);
        }

        holder.edtQuantity.setText(String.valueOf(cart.getQuantity()));


        holder.cbItem.setOnCheckedChangeListener((compoundButton, b) -> {
            selectedCartList.remove(cart);
            if (b) {
                selectedCartList.add(cart);
            }
            if (onItemActionListener != null) {
                onItemActionListener.onCheckBoxCheckedChanged(cart);
            }
        });
        holder.cbItem.setChecked(selectedCartList.contains(cart));

        holder.btnMinus.setOnClickListener(view -> {
            if (onItemActionListener != null) {
                onItemActionListener.onButtonMinusClick(cart);
            }
        });

        holder.btnPlus.setOnClickListener(view -> {
            if (onItemActionListener != null) {
                onItemActionListener.onButtonPlusClick(cart);
            }
        });

        holder.edtQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    holder.edtQuantity.clearFocus();
                    return true;
                }
                return false;
            }
        });

        holder.edtQuantity.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (onItemActionListener != null) {
                    onItemActionListener.onEditTextQuantityLostFocus(cart, holder.edtQuantity);
                }
            }
        });


        holder.btnDelete.setOnClickListener(view -> {
            if (onItemActionListener != null) {
                onItemActionListener.onButtonDeleteClick(cart);
            }
        });

        holder.tvName.setOnClickListener(view -> {
            if (onItemActionListener != null) {
                onItemActionListener.onClick(cart);
            }
        });

        holder.imgProduct.setOnClickListener(view -> {
            if (onItemActionListener != null) {
                onItemActionListener.onClick(cart);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cartList != null) {
            return cartList.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private CheckBox cbItem;
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvPrice, tvPriceAfterDiscount, tvDiscount;
        private EditText edtQuantity;
        private AppCompatButton btnMinus, btnPlus, btnDelete;
        private SwipeRevealLayout swipeRevealLayout;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            cbItem = itemView.findViewById(R.id.cb_item);
            imgProduct = itemView.findViewById(R.id.img_product_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvPriceAfterDiscount = itemView.findViewById(R.id.tv_price_after_discount);
            edtQuantity = itemView.findViewById(R.id.edt_quantity);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            tvDiscount = itemView.findViewById(R.id.tv_discount);

            swipeRevealLayout = itemView.findViewById(R.id.swiper_reveal_layout);

            itemView.setFocusable(true);
            itemView.setFocusableInTouchMode(true);
        }
    }
}

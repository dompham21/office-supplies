package com.luv2code.shopme.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.luv2code.shopme.Activity.CartActivity;
import com.luv2code.shopme.Activity.CheckoutActivity;
import com.luv2code.shopme.Activity.DetailActivity;
import com.luv2code.shopme.Activity.MainActivity;
import com.luv2code.shopme.Adapter.CartAdapter;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Cart;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.Utils.LocalStorage;
import com.luv2code.shopme.ViewModel.CartViewModel;

public class CartFragment extends Fragment {
    private RecyclerView rcvCart;
    private CheckBox cbAllItems;
    private View view;
    private TextView tvTotalPayment;
    private AppCompatButton btnBuy;
    private CartViewModel cartViewModel;

    LocalStorage localStorage;


    public CartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        setControls();
        setEvents();
        return view;
    }

    private void setControls() {
        cbAllItems = view.findViewById(R.id.cb_all_items);
        tvTotalPayment = view.findViewById(R.id.tv_total_payment);
        btnBuy = view.findViewById(R.id.btn_buy);


        rcvCart = view.findViewById(R.id.rcv_cart);
        rcvCart.setHasFixedSize(true);
        rcvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvCart.setFocusable(false);
        rcvCart.setNestedScrollingEnabled(false);
        localStorage = new LocalStorage(App.getContext());

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

    }

    private void setEvents() {
        updateInfoNumberSelected();

        rcvCart.setAdapter(App.cartAdapter);
        cbAllItems.setOnClickListener(view1 -> {
            App.cartAdapter.setCheckedAllItems(cbAllItems.isChecked());
        });

        btnBuy.setOnClickListener(view1 -> {

            if (App.cartAdapter.hasSelectedProduct()) {
                Intent intent = new Intent(getContext(), CheckoutActivity.class);
                startActivity(intent);
            }
            else {
                CustomToast.toastCenterTransparent(view1.getContext(),"Please select items you want to buy first!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        cartViewModel.getDeleteCartData().observe(getViewLifecycleOwner(), object -> {
            if (object == null) {
                CustomToast.makeText(getContext(), "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                Cart cart = object.getData();
                App.cartAdapter.removeItem(cart);
                if(App.cartAdapter.getItemCount() <= 0) {
                    CartBlankFragment cartBlankFragmentEmpty = new CartBlankFragment()
                            .setTitle(getString(R.string.cart_empty))
                            .setDescription(getString(R.string.cart_empty_description))
                            .setBtnText(getString(R.string.cart_empty_button))
                            .setType(CartBlankFragment.BLANK);

                    ((CartActivity) getActivity()).replaceFragment(cartBlankFragmentEmpty);
                }
            }
            else {
                CustomToast.makeText(getContext(), message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        cartViewModel.getUpdateCartData().observe(getViewLifecycleOwner(), object -> {
            if (object == null) {
                CustomToast.makeText(getContext(), "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                Cart cart = object.getData();
                App.cartAdapter.updateItem(cart);
            }
            else {
                CustomToast.makeText(getContext(), message, CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
            }
        });

        App.cartAdapter.setOnItemClickListener(new CartAdapter.OnItemActionListener() {
            @Override
            public void onClick(Cart cart) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(App.PRODUCT_ID, cart.getProduct().getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onCheckBoxCheckedChanged(Cart cart) {
                updateInfoNumberSelected();
            }

            @Override
            public void onButtonMinusClick(Cart cart) {
                if (cart.getQuantity() <= 1) {
                    showAlertDialogRemoveClicked(cart);
                }
                else {
                    cartViewModel.updateCart(App.getAuthorization(), cart.getProduct().getId(), cart.getQuantity() - 1);
                }
            }

            @Override
            public void onButtonPlusClick(Cart cart) {
                cartViewModel.updateCart(App.getAuthorization(), cart.getProduct().getId(), cart.getQuantity() + 1);
            }

            @Override
            public void onButtonDeleteClick(Cart cart) {
                showAlertDialogRemoveClicked(cart);
            }

            @Override
            public void onEditTextQuantityLostFocus(Cart cart, EditText editText) {
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                int quantity;
                try {
                    quantity = Integer.parseInt(editText.getText().toString());
                }
                catch (NumberFormatException ex) {
                    quantity = 1;
                }
                if (quantity <= 0) {
                    quantity = 1;
                }

                cartViewModel.updateCart(App.getAuthorization(), cart.getProduct().getId(), quantity);
            }

            @Override
            public void onCartListDataChanged() {
                updateInfoNumberSelected();
            }
        });

    }

    private void updateInfoNumberSelected() {
        cbAllItems.setChecked(App.cartAdapter.isAllCartItemSelected());

        tvTotalPayment.setText(String.format("%s đ", App.formatNumberMoney(App.cartAdapter.getSelectedCartItemTotalPayment())));
        btnBuy.setText(String.format("Buy (%s)", App.cartAdapter.getSelectedProductCount()));
    }


    public void showAlertDialogRemoveClicked(Cart cart) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.remove_product_from_cart));

        // add the buttons
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String accessToken = "Bearer ".concat(localStorage.getKeyAccessToken());
                cartViewModel.deleteCart(accessToken, cart.getId());

            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));

    }

    @Override
    public void onResume() {
        super.onResume();
        updateInfoNumberSelected();
    }


}
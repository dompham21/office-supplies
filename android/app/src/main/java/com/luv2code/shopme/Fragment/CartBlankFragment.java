package com.luv2code.shopme.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luv2code.shopme.Activity.LoginActivity;
import com.luv2code.shopme.Activity.MainActivity;
import com.luv2code.shopme.R;

public class CartBlankFragment extends Fragment {
    private Toolbar toolbar;

    public interface OnActionListener {
        void onActionButtonClick();
    }

    private View view;
    private TextView tvTitle, tvDescription;
    private AppCompatButton btnAction;

    private String title = "", description = "";
    private String btnText;
    private int type;

    public static int BLANK = 0;
    public static int NOT_LOGIN = 1;




    public CartBlankFragment() {
    }

    public CartBlankFragment(String title, String description, String btnText) {
        this.title = title;
        this.description = description;
        this.btnText = btnText;
    }

    public CartBlankFragment setTitle(String title) {
        this.title = title;
        return this;
    }

    public CartBlankFragment setDescription(String description) {
        this.description = description;
        return this;
    }

    public CartBlankFragment setBtnText(String btnText) {
        this.btnText = btnText;
        return this;
    }

    public CartBlankFragment setType(int type) {
        this.type = type;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_cart_blank, container, false);
        setControls();
        return view;
    }

    private void setControls() {

        tvTitle = view.findViewById(R.id.tv_title_cart);
        tvDescription = view.findViewById(R.id.tv_description_cart);
        btnAction = view.findViewById(R.id.btn_get_started_cart);

        tvTitle.setText(title);
        tvDescription.setText(description);

        btnAction.setText(btnText);

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type == BLANK) {
                    startActivity(new Intent(view.getContext(), MainActivity.class));
                }
                else if(type == NOT_LOGIN) {
                    startActivity(new Intent(view.getContext(), LoginActivity.class));
                }

            }
        });
    }
}
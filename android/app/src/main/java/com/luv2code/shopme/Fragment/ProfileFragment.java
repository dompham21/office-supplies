package com.luv2code.shopme.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luv2code.shopme.Activity.AddressActivity;
import com.luv2code.shopme.Activity.ChartActivity;
import com.luv2code.shopme.Activity.EditProfileActivity;
import com.luv2code.shopme.Activity.LoginActivity;
import com.luv2code.shopme.Activity.OrderActivity;
import com.luv2code.shopme.Activity.ReviewActivity;
import com.luv2code.shopme.Activity.SignUpActivity;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.OrderStatus;
import com.luv2code.shopme.Model.User;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.LocalStorage;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private View view;
    private AppCompatButton btnLogin, btnLogout, btnSignup;
    private CircleImageView imgAvatar;
    private TextView tvUsername;
    private ConstraintLayout layoutOrder, layoutReview, layoutAddress, layoutProfile, layoutTerms, layoutChart;
    private LocalStorage localStorage;
    private LinearLayout layoutWaitProcess, layoutWaitGrab, layoutDelivering, layoutSeeReview;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.red));
        }
        setControl();
        setEvent();


        return view;
    }

    private void setEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localStorage.logoutUser();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        layoutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.checkUserLogged()) {
                    Intent intent = new Intent(getContext(), AddressActivity.class);
                    startActivity(intent);
                }


            }
        });

        layoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.checkUserLogged()) {
                    Intent intent = new Intent(getContext(), EditProfileActivity.class);
                    startActivity(intent);
                }
            }
        });

        layoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.checkUserLogged()) {
                    OrderStatus orderStatus = OrderStatus.getOrderStatusById(4); //da giao
                    Intent intent = new Intent(getContext(), OrderActivity.class);
                    intent.putExtra(App.KEY_ORDER_STATUS, orderStatus);
                    startActivity(intent);
                }

            }
        });

        layoutWaitProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.checkUserLogged()) {
                    OrderStatus orderStatus = OrderStatus.getOrderStatusById(1); //cho xu ly
                    Intent intent = new Intent(getContext(), OrderActivity.class);
                    intent.putExtra(App.KEY_ORDER_STATUS, orderStatus);
                    startActivity(intent);
                }

            }
        });

        layoutWaitGrab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.checkUserLogged()) {
                    OrderStatus orderStatus = OrderStatus.getOrderStatusById(2); // cho lay hang
                    Intent intent = new Intent(getContext(), OrderActivity.class);
                    intent.putExtra(App.KEY_ORDER_STATUS, orderStatus);
                    startActivity(intent);
                }

            }
        });

        layoutDelivering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.checkUserLogged()) {
                    OrderStatus orderStatus = OrderStatus.getOrderStatusById(3); //dang giao hang
                    Intent intent = new Intent(getContext(), OrderActivity.class);
                    intent.putExtra(App.KEY_ORDER_STATUS, orderStatus);
                    startActivity(intent);
                }

            }
        });

        layoutReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.checkUserLogged()) {
                    Intent intent = new Intent(getContext(), ReviewActivity.class);
                    startActivity(intent);
                }

            }
        });

        layoutSeeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.checkUserLogged()) {
                    Intent intent = new Intent(getContext(), ReviewActivity.class);
                    startActivity(intent);
                }

            }
        });

        layoutChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.checkUserLogged()) {
                    Intent intent = new Intent(getContext(), ChartActivity.class);
                    startActivity(intent);
                }

            }
        });


    }

    private void setControl() {
        btnLogin = view.findViewById(R.id.btn_login);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnSignup = view.findViewById(R.id.btn_signup);
        layoutProfile = view.findViewById(R.id.layout_profile);
        layoutOrder = view.findViewById(R.id.layout_order);
        layoutReview = view.findViewById(R.id.layout_my_review);
        layoutAddress = view.findViewById(R.id.layout_address);
        layoutTerms = view.findViewById(R.id.layout_terms);
        imgAvatar = view.findViewById(R.id.img_avatar);
        tvUsername = view.findViewById(R.id.tv_username);
        layoutWaitProcess = view.findViewById(R.id.layout_wait_process);
        layoutWaitGrab = view.findViewById(R.id.layout_wait_grab);
        layoutDelivering = view.findViewById(R.id.layout_delivering);
        layoutSeeReview = view.findViewById(R.id.layout_see_review);
        layoutChart = view.findViewById(R.id.layout_chart);
        localStorage = new LocalStorage(App.getContext());
    }

    private void updateInfoUser() {
        if(App.checkUserLogged()) {
            String userString = localStorage.getUserLogin();

            Gson gson = new Gson();
            User user = gson.fromJson(userString, User.class);
            tvUsername.setText(user.getName());
            Picasso.get().load(user.getAvatar()).into(imgAvatar);
            tvUsername.setVisibility(View.VISIBLE);
            btnSignup.setVisibility(View.GONE);
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        }
        else {
            tvUsername.setVisibility(View.GONE);
            btnSignup.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
        }

    }

    @Override
    public void onResume() {
        updateInfoUser();
        super.onResume();
    }
}
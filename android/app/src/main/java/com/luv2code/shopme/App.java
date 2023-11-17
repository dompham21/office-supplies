package com.luv2code.shopme;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;

import com.luv2code.shopme.Adapter.CartAdapter;
import com.luv2code.shopme.Utils.LanguageManager;
import com.luv2code.shopme.Utils.LocalStorage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class App extends Application {
    private static LocalStorage localStorage;
    public static final String PRODUCT_ID = "product";
    public static final String ADDRESS = "address";
    public static final String KEY_SEARCH = "key_search";

    public static final String KEY_ORDER_STATUS = "order_status";
    public static final String KEY_ORDER_ID = "order_id";
    public static final String KEY_PRODUCT_REVIEW = "product_review";
    public static final String KEY_ORDER = "order";
    public static final String KEY_REVIEW = "review";
    public static final String KEY_FORGOT_PASSWORD = "forgot_password";

    public static final String KEY_EDIT_PROFILE = "edit_profile";
    public static final String KEY_AVATAR = "file";
    public static final String KEY_EMAIL_SEND = "email_send";
    public static final String KEY_OTP_VERIFY = "otp_verify";


    public static final String VALUE_EDIT_PHONE = "edit_phone";
    public static final String VALUE_EDIT_EMAIL = "edit_email";
    public static final String VALUE_FORGOT_PASSWORD = "value_forgot_password";

    public static CartAdapter cartAdapter = new CartAdapter(new ArrayList<>());

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        localStorage = new LocalStorage(getApplicationContext());

        LanguageManager languageManager = new LanguageManager(this);
        languageManager.updateResource(localStorage.getLanguage());

    }


    public static boolean checkUserLogged() {
        String accessToken = localStorage.getKeyAccessToken();
        if(accessToken == null) return false;
        else return true;
    }

    public static String formatNumberMoney(double number) {
        NumberFormat numberFormat = new DecimalFormat("###,###");

        return numberFormat.format(number);
    }


    public static String getAuthorization() {
        return "Bearer ".concat(localStorage.getKeyAccessToken());
    }




}

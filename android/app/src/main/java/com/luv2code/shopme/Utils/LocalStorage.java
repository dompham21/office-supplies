package com.luv2code.shopme.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    public static final String KEY_USER = "User";
    public static final String KEY_USER_ADDRESS = "user_address";
    private static final String KEY_ACCESS_TOKEN = "accessToken";
    private static final String KEY_REFRESH_TOKEN = "refreshToken";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String CART = "CART";
    private static final String ORDER = "ORDER";
    private static final String LANGUAGE = "en";

    private static LocalStorage instance = null;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;

    public LocalStorage(Context context) {
        sharedPreferences = context.getSharedPreferences("Preferences", 0);
    }

    public static LocalStorage getInstance(Context context) {
        if (instance == null) {
            synchronized (LocalStorage.class) {
                if (instance == null) {
                    instance = new LocalStorage(context);
                }
            }
        }
        return instance;
    }

    public void createUserLoginSession(String user, String accessToken, String refreshToken) {
        editor = sharedPreferences.edit();
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_USER, user);
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.putString(KEY_REFRESH_TOKEN, refreshToken);
        editor.commit();
    }

    public void setUser(String user) {
        editor = sharedPreferences.edit();
        editor.putString(KEY_USER, user);
        editor.commit();
    }


    public String getLanguage() {
        return sharedPreferences.getString(LANGUAGE, "en");
    }

    public void setLanguage(String lang) {
        editor = sharedPreferences.edit();
        editor.putString(LANGUAGE, lang);
        editor.commit();
    }


    public String getUserLogin() {
        return sharedPreferences.getString(KEY_USER, "");
    }

    public void logoutUser() {
        editor = sharedPreferences.edit();
        editor.remove(KEY_ACCESS_TOKEN);
        editor.remove(KEY_REFRESH_TOKEN);
        editor.remove(KEY_USER);
        editor.remove(IS_USER_LOGIN);
        editor.commit();
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(IS_USER_LOGIN, false);
    }

    public void setFirstimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public String getKeyAccessToken() {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

    public void setKeyAccessToken(String accessToken) {
        editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.commit();
    }

    public String getKeyRefreshToken() {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, null);
    }

    public void setKeyRefreshToken(String refreshToken) {
        editor = sharedPreferences.edit();
        editor.putString(KEY_REFRESH_TOKEN, refreshToken);
        editor.commit();
    }

}

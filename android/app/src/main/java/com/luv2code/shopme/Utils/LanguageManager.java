package com.luv2code.shopme.Utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {
    private Context ct;
    private LocalStorage localStorage;

    public LanguageManager(Context ct) {
        this.ct = ct;
        this.localStorage = new LocalStorage(ct);
    }

    public void updateResource(String code) {
        Locale locale = new Locale(code);
        locale.setDefault(locale);
        Resources resources = ct.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        localStorage.setLanguage(code);
    }
}

package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.luv2code.shopme.Fragment.BuyHistoryMonthFragment;
import com.luv2code.shopme.Fragment.BuyHistoryWeekFragment;
import com.luv2code.shopme.Fragment.BuyHistoryYearFragment;
import com.luv2code.shopme.R;

public class ChartActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private RadioGroup radioType;
    private BuyHistoryYearFragment buyHistoryYearFragment = new BuyHistoryYearFragment();
    private BuyHistoryMonthFragment buyHistoryMonthFragment = new BuyHistoryMonthFragment();
    private BuyHistoryWeekFragment buyHistoryWeekFragment = new BuyHistoryWeekFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        setControl();
        setEvent();

        replaceFragment(buyHistoryYearFragment);

    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.spending_statistics));

        radioType = findViewById(R.id.radio_type);
        RadioButton radioButton = findViewById(R.id.radio_year);
        radioButton.setChecked(true);

    }

    private void setEvent() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        radioType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_year: {

                        replaceFragment(buyHistoryYearFragment);
                        break;
                    }
                    case R.id.radio_month: {

                        replaceFragment(buyHistoryMonthFragment);
                        break;
                    }
                    case R.id.radio_week: {

                        replaceFragment(buyHistoryWeekFragment);
                        break;
                    }
                    default: {
                        break;
                    }

                }
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }



}
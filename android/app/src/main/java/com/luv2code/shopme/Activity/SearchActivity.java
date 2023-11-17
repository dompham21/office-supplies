package com.luv2code.shopme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.luv2code.shopme.Adapter.SuggestAdapter;
import com.luv2code.shopme.App;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.ViewModel.SearchViewModel;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private Context context = this;
    private Toolbar toolbar;
    private Handler handler = new Handler();
    private Runnable runnable;
    private SearchViewModel searchViewModel;
    private RecyclerView rcvSuggest;
    private RelativeLayout layoutSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setControl();
        setEvent();
    }

    private void setControl() {
        searchView = findViewById(R.id.search_view);
        searchView.requestFocus();

        toolbar = findViewById(R.id.toolbar);
        rcvSuggest = findViewById(R.id.rcv_suggest);
        layoutSubmit = findViewById(R.id.layout_submit);


        rcvSuggest.setHasFixedSize(true);
        rcvSuggest.setLayoutManager(new LinearLayoutManager(this));
        rcvSuggest.setFocusable(false);
        rcvSuggest.setNestedScrollingEnabled(false);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);


    }

    private void setEvent() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        layoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = searchView.getQuery().toString().trim();
                if(!keyword.isEmpty()) {
                    Intent intent = new Intent(context, AfterSearchActivity.class);
                    intent.putExtra(App.KEY_SEARCH, keyword);
                    startActivity(intent);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String keyword = s.trim();
                if(!keyword.isEmpty()) {
                    Intent intent = new Intent(context, AfterSearchActivity.class);
                    intent.putExtra(App.KEY_SEARCH, keyword);
                    startActivity(intent);
                    return true;
                }
                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                // Remove any existing callbacks to the handler
                handler.removeCallbacks(runnable);

                // Create a new Runnable to perform the search
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        String keyword = s.trim();
                        if(!keyword.isEmpty()) {
                            // Perform the search using newText
                            performSearch(s);
                        }
                    }
                };

                // Delay the search by 300 milliseconds
                handler.postDelayed(runnable, 300);

                return true;
            }
        });

        searchViewModel.getListSuggestData().observe(this, object -> {
            if (object == null) {
                CustomToast.makeText(this, "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                List<String> listSuggest = object.getData();
                System.out.println(listSuggest);
                SuggestAdapter suggestAdapter = new SuggestAdapter(listSuggest, new SuggestAdapter.OnItemActionListener() {
                    @Override
                    public void onClick(String suggest) {
                        Intent intent = new Intent(context, AfterSearchActivity.class);
                        intent.putExtra(App.KEY_SEARCH, suggest);
                        startActivity(intent);
                    }
                });

                rcvSuggest.setAdapter(suggestAdapter);
            }
            else if(result == 0 && message != null ) {
                CustomToast.makeText(this, message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });

    }

    private void performSearch(String keyword) {
        searchViewModel.getSuggest(keyword);
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
}
package com.admin.githubsearchdemo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.admin.githubsearchdemo.GitHubPojo.Item;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Top
    private TextView tvTop;
    private LinearLayout.LayoutParams layoutParams;

    // Search line
    private EditText etSearch;

    // RecycleView
    private RecyclerView rvListUsers;
    private LinearLayoutManager linearLayoutManager;
    private RVListUsersAdapter adapter;
    private List<Item> itemList;

    // MainActivityViewModel
    private MainActivityViewModel MainActivityViewModel;
    private LiveData<List<Item>> liveDataUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create top
        tvTop = findViewById(R.id.tvTop);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 25, 0,0);

        // Create itemList
        itemList = new ArrayList<>();

        // Create search line
        etSearch = findViewById(R.id.etSearch);
        etSearch.requestFocusFromTouch();

        // Create RecyclerView
        rvListUsers = findViewById(R.id.rvListUsers);
        linearLayoutManager = new LinearLayoutManager(this);
        rvListUsers.setLayoutManager(linearLayoutManager);
        adapter = new RVListUsersAdapter(getApplicationContext(), itemList);
        rvListUsers.setAdapter(adapter);

        // Create MainActivityViewModel
        MainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        // Observe for users/organizations
        liveDataUsers = MainActivityViewModel.getDataUsers();
        liveDataUsers.observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                itemList.clear();
                itemList.addAll(items);
                tvTop.setLayoutParams(layoutParams);
                adapter.notifyDataSetChanged();
            }
        });

        // On start
        dataRequest();

        // Search change listener
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 3) {
                    dataRequest();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    // Request
    private void dataRequest() {
        if (etSearch.getText().length() > 0) {
            MainActivityViewModel.searchUsers("search/users?q="
                    + etSearch.getText() + "+type:org");
        } else {
            MainActivityViewModel.searchUsers(null);
        }
    }

}

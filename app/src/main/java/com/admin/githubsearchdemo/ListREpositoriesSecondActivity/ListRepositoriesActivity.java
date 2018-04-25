package com.admin.githubsearchdemo.ListREpositoriesSecondActivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.admin.githubsearchdemo.GitHubPojo.Repos;
import com.admin.githubsearchdemo.R;
import java.util.ArrayList;
import java.util.List;

public class ListRepositoriesActivity extends AppCompatActivity {

    // Toolbar
    private Toolbar toolbar;
    private Button btnBack;
    private TextView tvTitle;
    private String title;
    private ProgressBar pbStatus;

    // RecycleView
    private RecyclerView rvListRepositories;
    private LinearLayoutManager linearLayoutManager;
    private RVListRepositoriesAdapter adapter;
    private List<Repos> reposList;

    // ListRepositoriesActivityViewModel
    private ListRepositoriesActivityViewModel listRepositoriesActivityViewModel;
    private LiveData<List<Repos>> liveDataRepositories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        // Create toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Create title
        tvTitle = findViewById(R.id.tvTitle);

        // Create string title
        title ="";

        // Create progressBar
        pbStatus = findViewById(R.id.pbStatus);

        // Create button back
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Create list data
        reposList = new ArrayList<>();

        // Create RecyclerView
        rvListRepositories = findViewById(R.id.rvListRepositories);
        linearLayoutManager = new LinearLayoutManager(this);
        rvListRepositories.setLayoutManager(linearLayoutManager);
        adapter = new RVListRepositoriesAdapter(reposList);
        rvListRepositories.setAdapter(adapter);

        // Create listRepositoriesActivityViewModel
        listRepositoriesActivityViewModel = ViewModelProviders.of(this)
                .get(ListRepositoriesActivityViewModel.class);


        // Observe for repositories
        liveDataRepositories = listRepositoriesActivityViewModel.getDataRepositories();
        liveDataRepositories.observe(this, new Observer<List<Repos>>() {
            @Override
            public void onChanged(@Nullable List<Repos> repos) {

                // Get response
                reposList.clear();
                reposList.addAll(repos);

                // Set title
                title = (String.valueOf(getIntent().getExtras().get("login")) + " repositories ("
                        + reposList.size() + ")");
                tvTitle.setText(title);

                // Refresh adapter
                adapter.notifyDataSetChanged();

                // Hide progress bar
                pbStatus.setVisibility(View.INVISIBLE);

            }
        });

        // Get data
        listRepositoriesActivityViewModel.searchRepositories(String.valueOf(getIntent()
                .getExtras().get("login")));

    }
}

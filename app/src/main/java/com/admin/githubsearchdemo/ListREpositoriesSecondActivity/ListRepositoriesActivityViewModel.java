package com.admin.githubsearchdemo.ListREpositoriesSecondActivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import com.admin.githubsearchdemo.GitHubApi;
import com.admin.githubsearchdemo.GitHubPojo.Repos;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListRepositoriesActivityViewModel extends AndroidViewModel {

    // Base URL
    private String URL = "https://api.github.com/";

    // Create retrofit
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

    // LiveData for repositories
    private MutableLiveData<List<Repos>> liveDataRepositories;


    public ListRepositoriesActivityViewModel(@NonNull Application application) {
        super(application);
    }

    // Create liveData for repositories
    public MutableLiveData<List<Repos>> getDataRepositories() {
        if (liveDataRepositories == null) {
            liveDataRepositories = new MutableLiveData<>();
        }
        return liveDataRepositories;
    }

    // Search repositories for users/organizations
    public void searchRepositories(String login) {
        Call<List<Repos>> call = gitHubApi.getRepositories(login);
        call.enqueue(new Callback<List<Repos>>() {
            @Override
            public void onResponse(Call<List<Repos>> call, Response<List<Repos>> response) {
                if (response != null && response.isSuccessful()) {
                    liveDataRepositories.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Repos>> call, Throwable t) {

            }
        });
    }



}

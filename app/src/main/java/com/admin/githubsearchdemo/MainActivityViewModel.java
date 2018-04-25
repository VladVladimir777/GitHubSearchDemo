package com.admin.githubsearchdemo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import com.admin.githubsearchdemo.DBCache.DBApi;
import com.admin.githubsearchdemo.GitHubPojo.Item;
import com.admin.githubsearchdemo.GitHubPojo.Users;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityViewModel extends AndroidViewModel {

    // DB cache
    private DBApi dbApi;

    // Base URL
    private String URL = "https://api.github.com/";

    // Create retrofit
    private Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
    private GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

    // LiveData for users/organizations
    private MutableLiveData<List<Item>> liveDataUsers;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        dbApi = DBApi.getInstance(application);
    }

    // Create liveData for users/organizations
    public MutableLiveData<List<Item>> getDataUsers() {
        if (liveDataUsers == null) {
            liveDataUsers = new MutableLiveData<>();
        }
        return liveDataUsers;
    }

    // Search users/organizations
    public void searchUsers(String url) {
        if (url == null) {
            liveDataUsers.postValue(dbApi.itemDao().getDataCache());
        } else {
            liveDataUsers.postValue(dbApi.itemDao().getDataCache());
            Call<Users> call = gitHubApi.getUsers(url);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if (response != null && response.isSuccessful()) {
                        if (response.body().getTotalCount() != 0) {
                            dbApi.itemDao().clearCache();
                            dbApi.itemDao().insertDataCache(response.body().getItems());
                            liveDataUsers.postValue(dbApi.itemDao().getDataCache());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                }
            });
        }
    }


}

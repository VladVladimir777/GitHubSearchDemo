package com.admin.githubsearchdemo;

import com.admin.githubsearchdemo.GitHubPojo.Users;
import com.admin.githubsearchdemo.GitHubPojo.Repos;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface GitHubApi {

    //Search users/organizations
    @GET()
    Call<Users> getUsers(@Url String url);

    //Search repositories
    @GET("users/{login}/repos")
    Call<List<Repos>> getRepositories(@Path("login")String login);

}

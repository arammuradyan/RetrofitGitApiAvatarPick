package com.example.retrofittest;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/search/repositories")
    Call<RepoArray> searchRepo(@Query("q") String search);


}

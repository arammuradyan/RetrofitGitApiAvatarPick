package com.example.retrofittest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RepoArray {

    @SerializedName("total_count")
     int totalCount=0;

    @SerializedName("items")
     List<RepoItem> repos;



}

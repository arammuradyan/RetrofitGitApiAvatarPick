package com.example.retrofittest;

import com.google.gson.annotations.SerializedName;


public class RepoItem {

    @SerializedName("name")
     String name;

    @SerializedName("full_name")
     String fullName;

    @SerializedName("owner")
    RepoOwner repoOwner;



}

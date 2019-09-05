package com.example.retrofittest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RepoClickListener{
Button search_btn;
RecyclerView recyclerView;
EditText search_et;
ProgressBar progressBar;
Retrofit retrofit;
RetrofitService retrofitService;
RepoAdapter repoAdapter;
List<RepoItem> repos=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_btn=findViewById(R.id.btn);
        search_et=findViewById(R.id.search_et);
        recyclerView=findViewById(R.id.recycler_view);
        progressBar=findViewById(R.id.pb);

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        retrofit=builder.build();

        retrofitService=retrofit.create(RetrofitService.class);


        repoAdapter=new RepoAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(repoAdapter);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText= search_et.getText().toString();
                if(!TextUtils.isEmpty(searchText)){
                search(searchText);
                }else {
                    Toast.makeText(MainActivity.this,"no input",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    void search(String searchText){
        progressBar.setVisibility(View.VISIBLE);
        retrofitService.searchRepo(searchText).enqueue(new Callback<RepoArray>() {
            @Override
            public void onResponse(
                    @NotNull Call<RepoArray> call,@NotNull Response<RepoArray> response) {
                recyclerView.scrollToPosition(0);
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body()!=null){
                    Log.d("tag","item count -> "+response.body().totalCount);
                    Log.d("tag","uri -> "+response.body().repos.get(1).repoOwner.avatarUri);
                    repoAdapter.setRepoList(response.body().repos);
                    repos.clear();
                    repos.addAll(response.body().repos);

                }

            }

            @Override
            public void onFailure(@NotNull Call<RepoArray> call,@NotNull Throwable t) {
            progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onRepoClick(int postion) {
     RepoItem repo=repos.get(postion);
        Intent intent=new Intent(this,PreviewActivity.class);
        intent.putExtra("name",repo.name);
        intent.putExtra("fullName",repo.fullName);
        intent.putExtra("uri",repo.repoOwner.avatarUri);
        startActivity(intent);
    }
}

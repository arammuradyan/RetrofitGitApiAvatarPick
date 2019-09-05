package com.example.retrofittest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PreviewActivity extends AppCompatActivity {
private TextView pr_name, pr_full_name;
private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        pr_name=findViewById(R.id.pr_name_tv);
        pr_full_name=findViewById(R.id.pr_full_name_tv);
        imageView=findViewById(R.id.img);

        Intent intent=getIntent();
        if(intent !=null){
            String name=intent.getStringExtra("name");
            String fullName=intent.getStringExtra("fullName");
            String imgUri=intent.getStringExtra("uri");
            Log.d("tag","uri " + imgUri);
            Uri uri=Uri.parse(imgUri);
            pr_name.setText(name);
            pr_full_name.setText(fullName);

            Picasso.get().load(uri).into(imageView);
        }
    }
}

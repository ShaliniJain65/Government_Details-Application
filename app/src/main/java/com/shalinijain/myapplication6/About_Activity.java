package com.shalinijain.myapplication6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class About_Activity extends AppCompatActivity {

    TextView textview1;
    TextView textview2;
    TextView textview3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_);
        textview1=findViewById(R.id.textView);

        textview2=findViewById(R.id.textView2);
        textview3=findViewById(R.id.textView3);
        Intent intent=getIntent();

        if(intent.hasExtra("knowgov"))
        {
            String name=intent.getStringExtra("knowgov");
            String author=intent.getStringExtra("author");
            String version=intent.getStringExtra("version");
            textview1.setText(name);
            textview2.setText(author);
            textview3.setText(version);
        }
    }
    }


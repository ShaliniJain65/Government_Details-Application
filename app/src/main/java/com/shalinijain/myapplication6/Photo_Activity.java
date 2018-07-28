package com.shalinijain.myapplication6;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Photo_Activity extends AppCompatActivity {

    TextView heading_view3;
    Government official_list3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_);
        heading_view3 = (TextView) findViewById(R.id.heading_view3);
        TextView name_view3 = (TextView) findViewById(R.id.party_view3);
        TextView office_view3 = (TextView) findViewById(R.id.nameof_view3);
        //TextView party3 = (TextView) findViewById(R.id.party_view3);
        final ImageView imageView3 = (ImageView) findViewById(R.id.imageView2);
        ScrollView scrollView=(ScrollView)findViewById(R.id.scrollView2);

        Intent intent1 = getIntent();
        if (intent1.hasExtra("heading")) {
            heading_view3.setText(intent1.getStringExtra("heading"));
        }
        if (intent1.hasExtra("officialobject2")) {
            official_list3 = (Government) intent1.getSerializableExtra("officialobject2");
            office_view3.setText(official_list3.getOffice_name());
            name_view3.setText(official_list3.getOfficial_name());
            if (official_list3.getParty().equalsIgnoreCase("Republican"))
            scrollView.setBackgroundColor(Color.RED);
                else if (official_list3.getParty().equalsIgnoreCase("Democratic") || official_list3.getParty().equalsIgnoreCase("Democrat"))
             scrollView.setBackgroundColor(Color.BLUE);
                else
            scrollView.setBackgroundColor(Color.BLACK);

        }

        if (official_list3 != null) {
            Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
// Here we try https if the http image attempt failed
                    final String changedUrl = official_list3.getUrlofphoto().replace("http:", "https:");
                    picasso.load(changedUrl)
                            .error(R.drawable.brokenimage)
                            .placeholder(R.drawable.placeholder)
                            .into(imageView3);
                }
            }).build();
            picasso.load(official_list3.getUrlofphoto())
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView3);
        } else {
            Picasso.with(this).load(official_list3.getUrlofphoto())
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.missingimage)
                    .into(imageView3);
        }

    }
}


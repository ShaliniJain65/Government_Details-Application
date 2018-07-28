package com.shalinijain.myapplication6;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class PersonActivity extends AppCompatActivity {
    TextView heading_view2;
    Government official_list2;
    ImageView person_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        heading_view2 = (TextView) findViewById(R.id.heading_view2);
        TextView name_view2 = (TextView) findViewById(R.id.offcie_view2);
        TextView office_view2 = (TextView) findViewById(R.id.name_view2);
        TextView party = (TextView) findViewById(R.id.party_view2);
        TextView address = (TextView) findViewById(R.id.addrresult_view2);
        TextView phoneno = (TextView) findViewById(R.id.phoneresult_view2);
        TextView emailadd = (TextView) findViewById(R.id.emailresult_view2);
        TextView webadd = (TextView) findViewById(R.id.websiteresult_view2);
        ScrollView scrollview = (ScrollView) findViewById(R.id.scrollView_person);
        ImageView google = (ImageView) findViewById(R.id.google_id);
        ImageView twitter = (ImageView) findViewById(R.id.twitter_id);
        ImageView facebook = (ImageView) findViewById(R.id.facebook_id);
        ImageView youtube = (ImageView) findViewById(R.id.youtube_id);
        person_image = (ImageView) findViewById(R.id.person_imageview);



        Intent intent = getIntent();
        if (intent.hasExtra("heading")) {
            heading_view2.setText(intent.getStringExtra("heading"));
        }
        if (intent.hasExtra("governmentobj")) {
            official_list2 = (Government) intent.getSerializableExtra("governmentobj");
            name_view2.setText(official_list2.getOffice_name());
            office_view2.setText(official_list2.getOfficial_name());
            party.setText("(" + official_list2.getParty() + ")");
            address.setText(official_list2.getAddress());
            phoneno.setText(official_list2.getContact_no());
            emailadd.setText(official_list2.getEmail_id());
            webadd.setText(official_list2.getUrlofweb());
        }

            if (official_list2.getParty().equalsIgnoreCase("Republican"))
                scrollview.setBackgroundColor(Color.RED);
            else if (official_list2.getParty().equalsIgnoreCase("Democratic") || official_list2.getParty().equalsIgnoreCase("Democrat") || official_list2.getParty().equalsIgnoreCase("Democratic Party"))
                scrollview.setBackgroundColor(Color.BLUE);
            else
                scrollview.setBackgroundColor(Color.BLACK);
            //If they do not have if then dont show the icon
            if (official_list2.getGoogleplus_channelid().equals("No Data Provided"))
                google.setVisibility(View.INVISIBLE);
            if (official_list2.getFacebook_channelid().equals("No Data Provided"))
                facebook.setVisibility(View.INVISIBLE);
            if (official_list2.getTwiter_channedlid().equals("No Data Provided"))
                twitter.setVisibility(View.INVISIBLE);
            if (official_list2.getYoutube_channeelid().equals("No Data Provided"))
                youtube.setVisibility(View.INVISIBLE);

            if (!address.getText().toString().equals("No Data Provided"))
                Linkify.addLinks(address, Linkify.MAP_ADDRESSES);
            if (!phoneno.getText().toString().equals("No Data Provided"))
                Linkify.addLinks(phoneno, Linkify.PHONE_NUMBERS);
            if (!emailadd.getText().toString().equals("No Data Provided"))
                Linkify.addLinks(emailadd, Linkify.EMAIL_ADDRESSES);
            if (!webadd.getText().toString().equals("No Data Provided"))
                Linkify.addLinks(webadd, Linkify.WEB_URLS);

            if (official_list2 != null) {
                if(!official_list2.getUrlofphoto().equals("")){
                Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
// Here we try https if the http image attempt failed
                        final String changedUrl = official_list2.getUrlofphoto().replace("http:", "https:");
                        picasso.load(changedUrl)
                                .error(R.drawable.brokenimage)
                                .placeholder(R.drawable.placeholder)
                                .into(person_image);
                    }
                }).build();
                picasso.load(official_list2.getUrlofphoto())
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.placeholder)
                        .into(person_image);
            } else {
                Picasso.with(this).load(R.drawable.missingimage)
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.missingimage)
                        .into(person_image);

            }
        }
    }


    public void openPhotoActivity(View v) {
        if(official_list2 != null) {
            if (!official_list2.getUrlofphoto().equals("")) {
                Intent it = new Intent(this, Photo_Activity.class);
                it.putExtra("heading", heading_view2.getText());
                it.putExtra("officialobject2", official_list2);
                startActivity(it);
            }
        }
    }

    public void googlePlusClicked(View v) {
        if (official_list2 == null || official_list2.getGoogleplus_channelid().equals(""))
            return;

        String name = official_list2.getGoogleplus_channelid();
        Intent intent1 = null;
        try {
            intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setClassName("com.google.android.apps.plus",
                    "com.google.android.apps.plus.phone.UrlGatewayActivity");
            intent1.putExtra("customAppUri", name);
            startActivity(intent1);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://plus.google.com/" + name)));
        }
    }

    public void youTubeClicked(View v) {
        if (official_list2 == null || official_list2.getYoutube_channeelid().equals(""))
            return;

        String name = official_list2.getYoutube_channeelid();
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + name)));
        }
    }

    public void twitterClicked(View v) {
        if (official_list2 == null || official_list2.getTwiter_channedlid().equals(""))
            return;

        Intent intent = null;
        String name = official_list2.getTwiter_channedlid();
        try {
            // get the Twitter app if possible
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
        }
        startActivity(intent);
    }

    public void facebookClicked(View v) {
        if (official_list2 == null || official_list2.getFacebook_channelid().equals(""))
            return;

        String FACEBOOK_URL = "https://www.facebook.com/" + official_list2.getFacebook_channelid();
        String urlToUse;
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                //urlToUse = "fb://page/" + channels.get("Facebook");
                urlToUse = "fb://page/" + official_list2.getFacebook_channelid();
            }
        } catch (PackageManager.NameNotFoundException e) {
            urlToUse = FACEBOOK_URL; //normal web url
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);

    }

    }





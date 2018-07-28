package com.shalinijain.myapplication6;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , View.OnLongClickListener {
    private static final String TAG = "MainActivity";
    private int MY_PERM_REQ_CODE = 9090;
    public String urlinput;
    public String displaytext="";
    private TextView location_view;
    private ArrayList<Government> governments = new ArrayList<>();
    private RecyclerView recyclerView;
    private GovernmentAdapter governmentAdapter;
    AsnycTask asyncTask;
    private Locator locator;
    private ArrayList<Government> resultarr= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location_view=(TextView)findViewById(R.id.address);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        governmentAdapter = new GovernmentAdapter(this, governments);
        recyclerView.setAdapter(governmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

      if(!doNetworkkCheck())
        {
            //Alert for network Connectivity
            Log.d(TAG, "onCreate: In network");
            location_view.setText("No Data For Location");
            android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(MainActivity.this);
            builder1.setMessage("Data cannot be accessed/loaded without an Internet Connection");
            builder1.setTitle("No Network Connection");
            android.app.AlertDialog dialog = builder1.create();
            dialog.show();
        }
        else{
          //Calling locator class for getting our current location
        locator = new Locator(this);
    }
}

    //Checking the internet connectivity
    private boolean doNetworkkCheck() {
        Boolean isConnect=false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            isConnect = true;
        } else {
            isConnect = false;
        }
        return isConnect;
    }

    //This method is called when you check location and ask user for the permission
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERM_REQ_CODE) {

            if (grantResults.length == 0) {
                Log.d(TAG, "onRequestPermissionsResult: Empty Grant Results");
                return;
            }
            //If you do have permission or awaiitng response from user
            for(int i=0 ; i < permissions.length;i++)
            {
                if(permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        locator.setUpLocationManager();
                        locator.determineLocation();
                        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                        Log.d(TAG, "onRequestPermissionsResult: No result in grant results array ");
                    }
                    else {
                        Toast.makeText(this, "Address cannot be acquired from provided latitude/longitude", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        }
    }

    //To call Async task with the dat from dialog
    public void doAsync(String zipcode)
    {
        displaytext=zipcode;
        AsnycTask asnycTask=new AsnycTask(this);
        asnycTask.execute(zipcode);
    }

    //This list is used to set the ArrayList governments and the resultarr is filled to set the views
    public void setOfficialList(Object[] finaldata) {
        if (finaldata == null) {
            location_view.setText("No Data Provided");
            governments.clear();
        }
        else{
        resultarr.clear();
        governments.clear();
        governments.addAll((ArrayList<Government>) finaldata[1]);
        resultarr.addAll((ArrayList) finaldata[1]);
        //Set the location in title
        location_view.setText(finaldata[0].toString());
    }
        governmentAdapter.notifyDataSetChanged();
    }

    //This is used for setting the heading of the Activities
    private String setHeading(String city, String state, String zip) {
        String city1 = city;
        if(!city1.equals("") && !state.equals(""))
            city1 += ", " + state + " " + zip;
        else
            city1 += state + " " + zip;
        return city1;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    //Fetches the location using GeoCoder
    public void doLocationWork(double latitude, double longitude) {

        Log.d(TAG, "doAddress: Lat: " + latitude + ", Lon: " + longitude);

        List<Address> addresses = null;
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                Log.d(TAG, "doAddress: Getting address now");
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                StringBuilder sb = new StringBuilder();
                      String zipcode=addresses.get(0).getPostalCode();
                        displaytext=addresses.get(0).getLocality()+" ,"+ addresses.get(0).getLocality() + " " + addresses.get(0).getPostalCode();
                        asyncTask=new AsnycTask(this);
                        asyncTask.execute(zipcode);
                Log.d(TAG, "doLocationWork: textset");
               // return sb.toString();

            } catch (IOException e) {
                Log.d(TAG, "doAddress: " + e.getMessage());
                Toast.makeText(this, "Address cannot be acquired from provided latitude/longitude", Toast.LENGTH_SHORT).show();
            }
           // Toast.makeText(this, "GeoCoder service is slow - please wait", Toast.LENGTH_SHORT).show();
        }
       // Toast.makeText(this, "GeoCoder service timed out - please try again", Toast.LENGTH_LONG).show();
    public void noLocationAvailable() {
        Toast.makeText(this, "No location providers were available", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.samplemenu, menu);
        return true;
    }

    @Override
    protected void onResume() {

        if (doNetworkkCheck())
        {
            if(locator==null)
            {
                locator=new Locator(this);
            }
        }
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Log.d(TAG, "onOptionsItemSelected: Search");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setGravity(Gravity.CENTER_HORIZONTAL);
                input.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                builder.setView(input);
                //urlinput = input.getText().toString();
                Log.d(TAG, "onOptionsItemSelected: "+ urlinput);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        urlinput = input.getText().toString();
                        doAsync(urlinput);
                      // asyncTask = new AsnycTask(MainActivity.this);
                      // asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urlinput.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                //builder.setMessage("");
                builder.setMessage("Enter a City,State or Zip Code:");
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.about:
                Log.d(TAG, "onOptionsItemSelected:About ");
                Intent intent = new Intent(this, About_Activity.class);
                intent.putExtra("knowgov", "Know Your Government");
                intent.putExtra("author", " ©  2018 ,  Shalini Jain");
                intent.putExtra("version", " Version 5.0 ");
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent= new Intent(this,PersonActivity.class);
        String heading=location_view.getText().toString();
        intent.putExtra("heading",heading);
        final int position=getRecyclerView().getChildLayoutPosition(view);
        intent.putExtra("governmentobj",resultarr.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View view) {
        onClick(view);
        return false;
    }

}


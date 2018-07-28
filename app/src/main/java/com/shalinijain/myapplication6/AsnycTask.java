package com.shalinijain.myapplication6;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Shalini on 01-04-2018.
 */

public class AsnycTask extends AsyncTask<String , Void, String> {
    private static final String TAG = "AsnycTask";

    private MainActivity mainActivity;
    private String webURL = " https://www.googleapis.com/civicinfo/v2/representatives";
    private ArrayList<Government> listofofficials;
    private Object[] finaldata;
    String city, postal;
    String heading;
    String apikey = "AIzaSyCVYERm2XSZX2gKwW4q6DmdwXGPLbpW83c";

    public AsnycTask(MainActivity ma) {
        mainActivity = ma;
    }

    @Override
    protected String doInBackground(String... strings) {
        postal = strings[0];
        Uri.Builder buildURL = Uri.parse(webURL).buildUpon();
        buildURL.appendQueryParameter("key", apikey);
        buildURL.appendQueryParameter("address", strings[0]);
        String urltouse = buildURL.build().toString();
        StringBuilder stringbuilder = new StringBuilder();
        try {
            URL url = new URL(urltouse);
            Log.d(TAG, "doInBackground: " + urltouse);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputsteam = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputsteam));

            String line;
            while ((line = reader.readLine()) != null) {
                stringbuilder.append(line).append("\n");
            }

        }
        catch(FileNotFoundException e)
        {
            Log.d(TAG, "doInBackground: FNF");
            e.printStackTrace();
            return "";
        }
        catch(Exception e) {
            Log.d(TAG, "doInBackground: Connection not Established");
            e.printStackTrace();
            return null;
        }
        return stringbuilder.toString();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: ");
        super.onPostExecute(s);
        if(fetchJsongovernmentData(s)) {
            finaldata = new Object[2];
            finaldata[0] = heading;
            finaldata[1] = listofofficials;
            mainActivity.setOfficialList(finaldata);
        }
        else
            mainActivity.setOfficialList(null);
        Log.d(TAG, "onPostExecute: " + s);

    }

    private boolean fetchJsongovernmentData(String s) {
        if (s == null) {
            Toast.makeText(mainActivity, "Civil Info Service is Unavailable", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(s.equals(""))
        {
            Toast.makeText(mainActivity, "No Data is Available", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            //Main Json object for entire string
            JSONObject jsonObject = new JSONObject(s);

            //"normalizedinput" is the jsonobject and its 3 attributes are required
            JSONObject normalizedInput = (JSONObject) jsonObject.getJSONObject("normalizedInput");
            String city = normalizedInput.getString("city");
            String state = normalizedInput.getString("state");
            String zip = normalizedInput.getString("zip");
            heading=setStringHeading(city, state, zip);

            //Json array offices and get its attribute name and officialindicesarray
            JSONArray officearray = (JSONArray) jsonObject.getJSONArray("offices");
            JSONArray OfficialsArray = jsonObject.getJSONArray("officials");
            listofofficials= new ArrayList<>();
            if (officearray.length() == 0) {
                //no data available
                Toast.makeText(mainActivity, "No Data is Available for the Specified Location", Toast.LENGTH_LONG).show();
            }
            for (int k = 0; k < officearray.length(); k++) {
                JSONObject jsonObject1 = (JSONObject) officearray.get(k);
                String office_name = jsonObject1.getString("name");
                JSONArray officilasarray = (JSONArray) jsonObject1.getJSONArray("officialIndices");
                if (officilasarray.length() == 0) {
                    Toast.makeText(mainActivity, "No data found for " + city + " officials", Toast.LENGTH_SHORT).show();
                }
                for (int l = 0; l < officilasarray.length(); l++) {
                    int index = officilasarray.getInt(l);
                    //JSONArray officials_array=(JSONArray)jsonObject.getJSONArray("officials");
                    JSONObject official_object = (JSONObject) OfficialsArray.get(index);
                    String person_name = official_object.getString("name");
                    String party = "Unknown";
                    if (official_object.has("party"))
                        party = official_object.getString("party");

                    String address_combine = "";
                    if (official_object.has("address"))
                    {
                        JSONArray address_array = (JSONArray) official_object.getJSONArray("address");
                        for (int m = 0; m < address_array.length(); m++) {
                            JSONObject jsonObject2 = (JSONObject) address_array.get(m);
                            if (jsonObject2.has("line1"))
                                address_combine = address_combine + jsonObject2.get("line1") + ",";
                            if (jsonObject2.has("line2"))
                                address_combine = address_combine + jsonObject2.get("line2") + ",";
                            if (jsonObject2.has("line3"))
                                address_combine = address_combine + jsonObject2.get("line3") + ",";
                            if (jsonObject2.has("city"))
                                address_combine = address_combine + jsonObject2.get("city") + ",";
                            if (jsonObject2.has("state"))
                                address_combine = address_combine + jsonObject2.get("state") + " ";
                            if (jsonObject2.has("zip"))
                                address_combine = address_combine + jsonObject2.get("zip") + "";
                            break;
                        }

                    }
                    String phone = "No Data Provided";
                    if (official_object.has("phones")) {
                        JSONArray p_off = (JSONArray) official_object.getJSONArray("phones");
                        phone = p_off.getString(0);
                    }
                    String weburl = "No Data Provided";
                    if (official_object.has("urls")) {
                        JSONArray url_json = official_object.getJSONArray("urls");
                        weburl = url_json.getString(0);
                    }
                    String urlemail = "No Data Provided";
                    if (official_object.has("emails")) {
                        JSONArray email_urlj = official_object.getJSONArray("emails");
                        urlemail = email_urlj.getString(0);
                    }

                    String pic_url = "";
                    if (official_object.has("photoUrl")) {
                        pic_url = official_object.getString("photoUrl");
                    }

                    String id_google = "No Data Provided";
                    String id_fb = "No Data Provided";
                    String id_twitter = "No Data Provided";
                    String id_youtube = "No Data Provided";

                    if(official_object.has("channels"))
                    {
                    JSONArray channel_json = official_object.getJSONArray("channels");
                    for (int o = 0; o < channel_json.length(); o++) {
                        JSONObject channel_object = (JSONObject) channel_json.get(o);
                        if (channel_object.has("type") && (channel_object.getString("type").equals("GooglePlus")))
                            id_google = channel_object.getString("id");
                        else if (channel_object.has("type") && (channel_object.getString("type").equals("Facebook")))
                            id_fb = channel_object.getString("id");
                        else if (channel_object.has("type") && (channel_object.getString("type").equals("Twitter")))
                            id_twitter = channel_object.getString("id");
                        else if (channel_object.has("type") && (channel_object.getString("type").equals("YouTube")))
                            id_youtube = channel_object.getString("id");
                    }
                }
                //Populate the list of government and save that data in list
                    Government objectforofficial= new Government();
                    objectforofficial.setParty(party);
                    objectforofficial.setAddress(address_combine);
                    objectforofficial.setContact_no(phone);
                    objectforofficial.setUrlofweb(weburl);
                    objectforofficial.setCity(city);
                    objectforofficial.setState(state);
                    objectforofficial.setZip(zip);
                    objectforofficial.setOfficial_name(person_name);
                    objectforofficial.setOffice_name(office_name);
                    objectforofficial.setEmail_id(urlemail);
                    objectforofficial.setUrlofphoto(pic_url);
                    objectforofficial.setGoogleplus_channelid(id_google);
                    objectforofficial.setFacebook_channelid(id_fb);
                    objectforofficial.setTwiter_channedlid(id_twitter);
                    objectforofficial.setYoutube_channeelid(id_youtube);
                    listofofficials.add(objectforofficial);
            }
        }
        }
        catch (Exception e) {

            Log.d(TAG, "parseJSONData: Error while parsing JSON data");
            e.printStackTrace();
            return false;

        }
        return true;
    }

    private String setStringHeading(String city, String state, String zip) {
        String city1 = city;
        if(!city1.equals("") && !state.equals(""))
            city1 += ", " + state + " " + zip;
        else
            city1 += state + " " + zip;
        return city1;
    }
    }

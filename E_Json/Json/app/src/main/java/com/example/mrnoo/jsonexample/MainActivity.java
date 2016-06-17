package com.example.mrnoo.jsonexample;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {

    private ProgressDialog pDialog;

    private static String url = "https://api.myjson.com/bins/35b33";

    private static final String TAG_CONTACTS = "contacts";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_PHONE_MOBILE = "mobile";
    private static final String TAG_PHONE_HOME = "home";
    private static final String TAG_PHONE_OFFICE = "office";

    JSONArray contacts = null;
    ArrayList<HashMap<String, String>> contactList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<HashMap<String, String>>();
        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }

            //update listview
            ListAdapter adapter = new SimpleAdapter(MainActivity.this,
                                                    contactList,
                                                    R.layout.list_item,
                                                    new String[] { TAG_NAME, TAG_EMAIL, TAG_PHONE_MOBILE },
                                                    new int[] { R.id.name, R.id.email, R.id.mobile });

            setListAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            // get all json
            String jsonString = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
            Log.d("JSON_GET: ", jsonString);
            if (jsonString != null){
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    // get json array
                    contacts = jsonObject.getJSONArray(TAG_CONTACTS);
                    // loop to get child element
                    for (int i = 0; i< contacts.length(); i++){
                        JSONObject child = contacts.getJSONObject(i);
                        String id = child.getString(TAG_ID);
                        String name = child.getString(TAG_NAME);
                        String email = child.getString(TAG_EMAIL);
                        String address = child.getString(TAG_ADDRESS);
                        String gender = child.getString(TAG_GENDER);

                        JSONObject phonenumber = child.getJSONObject(TAG_PHONE);
                        String mobile = phonenumber.getString(TAG_PHONE_MOBILE);
                        String home = phonenumber.getString(TAG_PHONE_HOME);
                        String office = phonenumber.getString(TAG_PHONE_OFFICE);

                        HashMap<String, String> contacts = new HashMap<String, String>();
                        contacts.put(TAG_ID, id);
                        contacts.put(TAG_NAME, name);
                        contacts.put(TAG_EMAIL, email);
                        contacts.put(TAG_ADDRESS, address);
                        contacts.put(TAG_GENDER, gender);

                        contacts.put(TAG_PHONE_MOBILE, mobile);
                        contacts.put(TAG_PHONE_HOME, home);
                        contacts.put(TAG_PHONE_OFFICE, office);

                        contactList.add(contacts);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Log.d("ERROR", "Could not get data from this url");
            }
            return null;
        }
    }
}

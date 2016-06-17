package com.example.mrnoo.gsonexample;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends ListActivity {

    // URL để lấy JSON
    private static String url = "http://api.androidhive.info/contacts/";

    // Tên các node JSON
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

    ArrayList<HashMap<String, String>> contactListMap;
    AsyncHttpClient client = new AsyncHttpClient();
    RequestParams params = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactListMap = new ArrayList<HashMap<String, String>>();

        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    JSONArray contacts = jsonObject.getJSONArray(TAG_CONTACTS);
                    for (int i = 0; i < contacts.length(); i++) {
                        String child = contacts.getString(i);
                        Gson gson = new GsonBuilder().create();
                        Contact contact = gson.fromJson(child, Contact.class);

                        HashMap<String, String> c = new HashMap<String, String>();
                        c.put(TAG_ID, contact.getId());
                        c.put(TAG_NAME, contact.getName());
                        c.put(TAG_EMAIL, contact.getEmail());
                        c.put(TAG_ADDRESS, contact.getAddress());
                        c.put(TAG_GENDER, contact.getGender());
                        Contact.Phone phone = contact.new Phone();
                        c.put(TAG_PHONE_HOME, phone.getHome());
                        c.put(TAG_PHONE_MOBILE, phone.getMobile());
                        c.put(TAG_PHONE_OFFICE, phone.getOffice());

                        contactListMap.add(c);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ListAdapter adapter = new SimpleAdapter(
                MainActivity.this, contactListMap,
                R.layout.list_item, new String[] { TAG_NAME, TAG_EMAIL,
                TAG_PHONE_MOBILE }, new int[] { R.id.name,
                R.id.email, R.id.mobile });
        setListAdapter(adapter);
    }
}

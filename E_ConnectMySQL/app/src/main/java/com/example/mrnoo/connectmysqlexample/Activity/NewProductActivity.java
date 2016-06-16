package com.example.mrnoo.connectmysqlexample.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mrnoo.connectmysqlexample.R;
import com.example.mrnoo.connectmysqlexample.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewProductActivity extends AppCompatActivity {

    private EditText etName, etPrice, etDesctiption;
    private Button addproduct;

    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();
    private static String url_create_product = "http://10.22.33.44/android_connect/create_product.php";
    private final static String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);


        etName = (EditText) findViewById(R.id.et_product_name);
        etPrice = (EditText) findViewById(R.id.et_price);
        etDesctiption = (EditText) findViewById(R.id.et_description);
        addproduct = (Button) findViewById(R.id.btn_create_product);


        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String price = etPrice.getText().toString();
                String description = etDesctiption.getText().toString();
                new CreateNewProduct().execute(name, price, description);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (pDialog != null && pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    class CreateNewProduct extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewProductActivity.this);
            pDialog.setIndeterminate(false);
            pDialog.setMessage("Please wait");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... args) {
            Log.d("CHECK", args[0]);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", args[0]));
            params.add(new BasicNameValuePair("price", args[1]));
            params.add(new BasicNameValuePair("description", args[2]));
            try {
                JSONObject json = jsonParser.makeHttpRequest(url_create_product, "POST", params);
                Log.d("create response: ", json.toString());
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1){
                    Intent it = new Intent(NewProductActivity.this, AllProductActivity.class);
                    startActivity(it);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}

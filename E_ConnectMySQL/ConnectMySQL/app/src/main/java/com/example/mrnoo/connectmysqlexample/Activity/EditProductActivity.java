package com.example.mrnoo.connectmysqlexample.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditProductActivity extends AppCompatActivity {

    EditText etName, etPrice, etDes;
    Button btnUpdate, btnDelete;

    private static final String url_update_product = "http://10.22.33.44/android_connect/update_product.php";
    private static final String url_delete_product = "http://10.22.33.44/android_connect/delete_product.php";
    private static final String url_get_product_detail = "http://10.22.33.44/android_connect/get_product_detail.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_DES = "description";
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;
    String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        etName = (EditText) findViewById(R.id.et_product_name_update);
        etPrice = (EditText) findViewById(R.id.et_price_update);
        etDes = (EditText) findViewById(R.id.et_description_update);
        btnUpdate = (Button) findViewById(R.id.btn_update_product);
        btnDelete = (Button) findViewById(R.id.btn_delete_product);

        Intent it = getIntent();
        pid = it.getStringExtra(TAG_PID);
        new GetProductDetails().execute();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String price = etPrice.getText().toString();
                String des = etDes.getText().toString();
                new SaveProductDetails().execute(name, price, des);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditProductActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new DeleteProduct().execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
    }

    class GetProductDetails extends AsyncTask<String, List<String>, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Please wait");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(List<String>... values) {
            super.onProgressUpdate(values);
            Log.d("VALUE:", values[0].toString());
            etName.setText(values[0].get(0));
            etPrice.setText(values[0].get(1));
            etDes.setText(values[0].get(2));
        }


        @Override
        protected String doInBackground(String... args) {
                    int success;
                    try {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        Log.d("ID", pid);
                        params.add(new BasicNameValuePair("pid", pid));
                        JSONObject json = jsonParser.makeHttpRequest(url_get_product_detail, "GET", params);
                        Log.d("Product detail: ", json.toString());
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1){
                            JSONArray productObject = json.getJSONArray(TAG_PRODUCT);
                            JSONObject product = productObject.getJSONObject(0);
                            List<String> listData = new ArrayList<>();
                            listData.add(product.getString(TAG_NAME));
                            listData.add(product.getString(TAG_PRICE));
                            listData.add(product.getString(TAG_DES));
                            publishProgress(listData);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            return null;
        }
    }

    class SaveProductDetails extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Please wait");
            pDialog.setIndeterminate(false);
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
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair(TAG_PID, pid));
                    params.add(new BasicNameValuePair(TAG_NAME, args[0]));
                    params.add(new BasicNameValuePair(TAG_PRICE, args[1]));
                    params.add(new BasicNameValuePair(TAG_DES, args[2]));
                    JSONObject json = jsonParser.makeHttpRequest(url_update_product, "POST", params);
                    try {
                        int success = json.getInt(TAG_SUCCESS);
                        if (success == 1){
                            Intent it = getIntent();
                            setResult(100, it);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            return null;
        }
    }

    class DeleteProduct extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Please wait");
            pDialog.setIndeterminate(false);
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
            int success;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_PID, pid));
            JSONObject json = jsonParser.makeHttpRequest(url_delete_product, "POST", params);
            Log.d("Delete product: ", json.toString());
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1){
                    Intent it = getIntent();
                    setResult(100, it);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

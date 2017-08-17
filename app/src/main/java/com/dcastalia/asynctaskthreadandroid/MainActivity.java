package com.dcastalia.asynctaskthreadandroid;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/** This example download a webpage using AsyncTask Thread.
 * Note : AsyncTask is used for small time operation in Android.
 */

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.TextView01);


    }

    public void onClick(View view) {
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{"http://www.vogella.com/index.html"});
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
             progressDialog = ProgressDialog.show(MainActivity.this, "","Downloading");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            // we use the OkHttp library from https://github.com/square/okhttp add 'com.squareup.okhttp3:okhttp:3.8.1' in
            // app build.gradle file

            try{
                OkHttpClient client = new OkHttpClient();
                Request request =
                        new Request.Builder()
                                .url(urls[0])
                                .build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return "Download failed";
        }

    @Override
    protected void onPostExecute(String result) {
        progressDialog.dismiss();
        textView.setText(result);

    }
}





}

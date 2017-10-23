package com.farid.www.myapplication;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Activity_adapter extends AsyncTaskLoader<String> {
    private String URL_Link;

    public Activity_adapter(Context context, String urllink){
        super(context);
        this.URL_Link = urllink;
    }


    @Override
    public String loadInBackground() {
        InputStream in;

        try {
            URL url = new URL(URL_Link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(20000);
            connection.setRequestMethod("GET");
            connection.connect();

            in = connection.getInputStream();

            BufferedReader buff = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line = buff.readLine()) != null) {
                sb.append(line + "\n");
            }

            buff.close();
            in.close();

            return sb.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Errors, try change protocol to http or https";
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}

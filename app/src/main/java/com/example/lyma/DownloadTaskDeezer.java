package com.example.lyma;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class DownloadTaskDeezer extends AsyncTask<String, Void, Void>
{
    @Override
    protected Void doInBackground(String... urls) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(urls[0])
                .get()
                .addHeader("x-rapidapi-key", "3d5fb8bcfdmsh50cc37d6627cc53p1a8849jsn6d91f1397f98")
                .addHeader("x-rapidapi-host", "deezerdevs-deezer.p.rapidapi.com")
                .build();

        try
        {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length() ; i++)
            {
                System.out.println(jsonArray.getJSONObject(i).getString("preview"));
                Lyrics.urlDeezer = jsonArray.getJSONObject(i).getString("preview");
            }
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

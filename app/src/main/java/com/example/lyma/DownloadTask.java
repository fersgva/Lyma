package com.example.lyma;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DownloadTask extends AsyncTask<String, Void, Void>
{
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Void doInBackground(String... urls)
    {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(urls[0])
                .get()
                .addHeader("x-rapidapi-key", "3d5fb8bcfdmsh50cc37d6627cc53p1a8849jsn6d91f1397f98")
                .addHeader("x-rapidapi-host", "genius.p.rapidapi.com")
                .build();

        try
        {

            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonData);

            JSONObject jsonObject2 = jsonObject.getJSONObject("response");

            JSONArray jsonArray = jsonObject2.getJSONArray("hits");

            for (int i = 0; i < jsonArray.length() ; i++)
            {
                JSONObject cancion = new JSONObject(jsonArray.getJSONObject(i).getString("result"));
                JSONObject artista = new JSONObject(cancion.getString("primary_artist"));
                System.out.println(cancion.getString("full_title"));
                Buscar.completoTitulo = cancion.getString("full_title");
                System.out.println(cancion.getString("title"));
                Buscar.tituloCancion = cancion.getString("title");
                System.out.println(artista.getString("name"));
                Buscar.artistaCancion = cancion.getString("name");
                System.out.println(cancion.getString("song_art_image_url"));
                Buscar.urlPortadaCancion = cancion.getString("song_art_image_url");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

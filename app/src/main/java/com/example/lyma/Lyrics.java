package com.example.lyma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Lyrics extends AppCompatActivity{
    ImageView foto;
    static ImageView play;
    SeekBar cancion;
    static MediaPlayer player;
    public static String urlDeezer;
    String nomArtista, nomCancion, urlLyrics, busquedaSong, urlPortada;
    int id;
    static EditText ponerLetras;
    DownloadTaskDeezer taskD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        foto = findViewById(R.id.imageViewPortada);
        play = findViewById(R.id.imageView_Play);
        cancion = findViewById(R.id.seekBar_Cancion);
        ponerLetras = findViewById(R.id.editTextLyrics);
        Class clasee = getIntent().getClass();
        System.out.println(clasee.getName());
        nomArtista = Buscar.artistaCancion;
        String nomArtistaURL = nomArtista;
        nomCancion = Buscar.tituloCancion;
        urlPortada = Buscar.urlPortadaCancion;
        Picasso.get().load(urlPortada).into(foto);

        Intent i = getIntent();
        id = i.getIntExtra("id",0);

        System.out.println("Se esta usando la biblioteca de " + id);

        player = new MediaPlayer();
        if(nomArtistaURL.contains(",")){
            nomArtistaURL = nomArtistaURL.substring(0, nomArtistaURL.indexOf(","));
        }
        else if(nomArtistaURL.contains("&"))
        {
           nomArtistaURL = nomArtistaURL.substring(0, nomArtistaURL.indexOf("&"));
        }

        nomArtistaURL = nomArtistaURL.replaceAll(" ", "");
        nomArtistaURL = nomArtistaURL.toLowerCase();
        nomArtistaURL = Normalizer.normalize(nomArtistaURL, Normalizer.Form.NFD);
        nomArtistaURL = nomArtistaURL.replaceAll("[^\\p{ASCII}]", "");

        String nomCancionURL = nomCancion;
        nomCancionURL = nomCancionURL.toLowerCase();
        nomCancionURL = nomCancionURL.replaceAll("[()!¡¿? '’/<>﹤﹥+*.#]", "");
        nomCancionURL = Normalizer.normalize(nomCancionURL, Normalizer.Form.NFD);
        nomCancionURL = nomCancionURL.replaceAll("[^\\p{ASCII}]", "");

        System.out.println(nomCancionURL);
        System.out.println(nomArtistaURL);
        urlLyrics = "https://www.azlyrics.com/lyrics/"+ nomArtistaURL + "/" + nomCancionURL + ".html";
        DownloadTaskLyrics task = new DownloadTaskLyrics();
        task.execute(urlLyrics);

        taskD = new DownloadTaskDeezer();
        busquedaSong = Buscar.tituloCancion + "%20" + Buscar.artistaCancion;

        //Ocultar el seekbar.
        play.setVisibility(View.INVISIBLE);
        taskD.execute("https://deezerdevs-deezer.p.rapidapi.com/search?q=" + busquedaSong);



        cancion.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                    player.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public static void prepararCancion()
    {
        player = new MediaPlayer();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                play.setVisibility(View.VISIBLE);
            }
        });
        try {
            player.setDataSource(urlDeezer);
            player.prepareAsync();


        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public void playCancion (View v)
    {

        if(!player.isPlaying()) {
            player.start();
            play.setImageResource(android.R.drawable.ic_media_pause);
        }
        else {
            player.pause();
            play.setImageResource(android.R.drawable.ic_media_play);
        }

        if(player.getDuration() < 15)
        {
            taskD.execute("https://deezerdevs-deezer.p.rapidapi.com/search?q=" + busquedaSong);
            DownloadTaskDeezer.posPreview += 1;
        }
        else
            DownloadTaskDeezer.posPreview = 0;

        cancion.setMax(player.getDuration());
        cancion.setProgress(player.getCurrentPosition());

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                cancion.setProgress(player.getCurrentPosition());
            }
        },0,1000);
    }

    public void onClickIrABuscar (View v)
    {
        player.pause();
        Intent i = new Intent(this, Buscar.class);
        i.putExtra("id" , id);
        startActivity(i);
    }
    public void onClickIrABiblioteca (View v)
    {
        player.pause();
        Intent i = new Intent(this, Biblioteca.class);
        i.putExtra("id" , id);
        startActivity(i);
    }

    public void onClickIrAPerfil (View v)
    {
        player.pause();
        Intent i = new Intent(this, Perfil.class);
        i.putExtra("id" , id);
        startActivity(i);
    }

}
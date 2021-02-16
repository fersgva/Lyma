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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class Lyrics extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
    ImageView foto , play , mas;
    SeekBar cancion;
    public static String urlDeezer;
    String nomArtista, nomCancion, urlLyrics, busqueda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        foto = findViewById(R.id.imageViewPortada);
        play = findViewById(R.id.imageView_Play);
        mas = findViewById(R.id.imageView_Fav);
        cancion = findViewById(R.id.seekBar_Cancion);

        nomArtista = Buscar.artistaCancion;
        nomCancion = Buscar.tituloCancion;
        nomArtista = nomArtista.toLowerCase();
        nomCancion = nomCancion.toLowerCase();
        urlLyrics = "https://www.azlyrics.com/lyrics/"+ nomArtista + "/" + nomCancion + ".html";
        DownloadTaskLyrics task = new DownloadTaskLyrics();
        task.execute(urlLyrics);

        DownloadTaskDeezer taskD = new DownloadTaskDeezer();
        busqueda = Buscar.tituloCancion + "%20" + Buscar.artistaCancion;
        taskD.execute("https://deezerdevs-deezer.p.rapidapi.com/search?q=" + busqueda);
    }
    public void playCancion (View v) {
        MediaPlayer player = new MediaPlayer();
        player.setOnPreparedListener(this);
        try {
            player.setDataSource(urlDeezer);
            player.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast t = Toast.makeText(this,
                "Espere un momento mientras se carga la canción",
                Toast.LENGTH_SHORT);
        t.show();
    }

    public void onPrepared(MediaPlayer mediaPlayer)
    {
        mediaPlayer.start();
    }

    public void onClickIrABuscar (View v)
    {
        Intent i = new Intent(this, Buscar.class);
        startActivity(i);
    }
    public void onClickIrABiblioteca (View v)
    {
        Intent i = new Intent(this, Biblioteca.class);
        startActivity(i);
    }

    public void onClickIrAPerfil (View v)
    {
        Intent i = new Intent(this, Perfil.class);
        startActivity(i);
    }
}
package com.example.lyma;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class Lyrics extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
    Button botonPlay;
    String urlDeezer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        //botonPlay = findViewById(R.id.button_play);

        urlDeezer = "LO DE LA API";
    }
    public void ejecutar (View v) {
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
}
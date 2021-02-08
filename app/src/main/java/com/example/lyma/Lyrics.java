package com.example.lyma;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class Lyrics extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
    String idVideo;
    Button botonPlay;
    String urlYT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        botonPlay = findViewById(R.id.button_play);

        idVideo = "aB52h93Bax0"; //CAMBIAR LUEGO
        urlYT = "https://www.youtube.com/watch?v=" + idVideo;
        //https://www.youtube.com/watch?v=aB52h93Bax0
    }
    public void ejecutar (View v) {
        MediaPlayer player = new MediaPlayer();
        player.setOnPreparedListener(this);
        try {
            player.setDataSource("https://cdn.kapwing.com/final_602106f723ef4b00442109cd_876474.mp3"); //urlYT
            player.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast t = Toast.makeText(this,
                "Espere un momento mientras se carga el mp3",
                Toast.LENGTH_SHORT);
        t.show();
    }

    public void onPrepared(MediaPlayer mediaPlayer)
    {
        mediaPlayer.start();
        if (mediaPlayer.isPlaying())
        {
            Log.i("hola", "FUNJCIOAN");
        }
    }
}
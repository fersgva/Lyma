package com.example.lyma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Buscar extends AppCompatActivity {

    static ArrayList<Cancion> cancionesBuscadas = new ArrayList<>(); //Minúscula la variable.
    ListView buscar;
    Button Boton_buscar;
    TextView textIntroducir;
    static Adaptador adaptador;
    String busqueda = " ";
    public static String tituloCancion, artistaCancion, urlPortadaCancion;
    static ImageView imagenPortada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        buscar = findViewById(R.id.lV_buscar);
        Boton_buscar = findViewById(R.id.buttonBuscarB);
        textIntroducir = findViewById(R.id.editTextBuscarB);
        imagenPortada = findViewById(R.id.fotoCancion);

        adaptador = new Adaptador(DownloadTask.cancionesBuscadasDT,this);
        buscar.setAdapter(adaptador);

        Intent i = getIntent();
        i.getStringExtra("usuario");

        buscar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Buscar.this, Lyrics.class);
                tituloCancion = cancionesBuscadas.get(position).titulo;
                artistaCancion = cancionesBuscadas.get(position).artistaNombre;
                urlPortadaCancion = cancionesBuscadas.get(position).urlFoto;
                startActivity(i);
            }
        });

        buscar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Biblioteca.Canciones.add(new Cancion(cancionesBuscadas.get(position).titulo,cancionesBuscadas.get(position).artistaNombre,cancionesBuscadas.get(position).urlFoto));
                Toast.makeText(Buscar.this, "Se ha añadido la canción a la biblioteca correctamente", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void onClickBuscarCancion (View v)
    {
        cancionesBuscadas.clear(); //Lo borramos si o si, desde luego.
        adaptador.notifyDataSetChanged();
        if (true) //textIntroducir.getText().toString().compareTo("") == 1
        {
            DownloadTask task = new DownloadTask();
            //System.out.println(task.CancionesBuscadasDT.size());
            busqueda = textIntroducir.getText().toString();
            busqueda = busqueda.replaceAll(" ", "%20");
            task.execute("https://genius.p.rapidapi.com/search?q=" + busqueda);

            //!!!!!!!!!    Después de lanzar la búsqueda, hay que esperar a que termine, voy a mover estas líneas a onPostExecute en "DownLoadTask".
        }
        else
        {
            Toast t = Toast.makeText(this,
                    "Introduzca un título...",
                    Toast.LENGTH_SHORT);
            t.show();
        }
    }


    public void onClickIrALyrics (View v)
    {
        Intent i = new Intent(this, Lyrics.class);
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

    public static void onTaskFinished(ArrayList<Cancion> cancionesBuscadasDT)
    {
        cancionesBuscadas = cancionesBuscadasDT;
        adaptador.notifyDataSetChanged();
    }

}
package com.example.lyma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Buscar extends AppCompatActivity {

    static ArrayList<Cancion> cancionesBuscadas = new ArrayList<>(); //Minúscula la variable.
    static ListView buscar;
    Button Boton_buscar;
    TextView textIntroducir;
    static Adaptador adaptador;
    String busqueda = " ";
    public static String tituloCancion, artistaCancion, urlPortadaCancion;
    static ImageView imagenPortada;
    static int id;

    static Gson gson;
    static SharedPreferences PreferenciasBiblioteca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        buscar = findViewById(R.id.lV_buscar);
        Boton_buscar = findViewById(R.id.buttonBuscarB);
        textIntroducir = findViewById(R.id.editTextBuscarB);
        imagenPortada = findViewById(R.id.fotoCancion);

        Intent i = getIntent();
        id = i.getIntExtra("id",0);
        System.out.println("Se está utilizando biblioteca de es BUSCAR: " + id);

        cancionesBuscadas.clear();

        gson = new Gson();
        PreferenciasBiblioteca = getSharedPreferences("com.example.lymas2" + id,MODE_PRIVATE);

        String datosCargados = PreferenciasBiblioteca.getString("Canciones", null);

        //Debido a que el tipo no es sólo un único objeto, si no una lista, debemos crear un tipo basado en esa lista.
        Type type = new TypeToken<ArrayList<Cancion>>(){}.getType();
        Biblioteca.Canciones = gson.fromJson(datosCargados, type);

        if(Biblioteca.Canciones == null)
            Biblioteca.Canciones = new ArrayList<>();


        adaptador = new Adaptador(DownloadTask.cancionesBuscadasDT,this);
        buscar.setAdapter(adaptador);

        buscar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Buscar.this, Lyrics.class);
                tituloCancion = cancionesBuscadas.get(position).titulo;
                artistaCancion = cancionesBuscadas.get(position).artistaNombre;
                urlPortadaCancion = cancionesBuscadas.get(position).urlFoto;
                i.putExtra("id",Buscar.id);
                startActivity(i);
            }
        });

        buscar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Biblioteca.Canciones.add(new Cancion(cancionesBuscadas.get(position).titulo,cancionesBuscadas.get(position).artistaNombre,cancionesBuscadas.get(position).urlFoto));


                //PROCESO DE GUARDADO CON LIBRERÍAS DE GSON.
                String CancioneEnString = gson.toJson(Biblioteca.Canciones);
                PreferenciasBiblioteca.edit().putString("Canciones", CancioneEnString).apply();

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
        if (tituloCancion != null) {
            Intent i = new Intent(this, Lyrics.class);
            i.putExtra("id" , id);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this, "Elige una canción antes de ir a Lyrics.", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickIrABiblioteca (View v)
    {
        Intent i = new Intent(this, Biblioteca.class);
        i.putExtra("id" , id);
        startActivity(i);
    }

    public void onClickIrAPerfil (View v)
    {
        Intent i = new Intent(this, Perfil.class);
        i.putExtra("id" , id);
        startActivity(i);
    }

    public static void onTaskFinished(ArrayList<Cancion> cancionesBuscadasDT)
    {
        cancionesBuscadas = cancionesBuscadasDT;
        adaptador.notifyDataSetChanged();
    }

}
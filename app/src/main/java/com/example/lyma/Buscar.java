package com.example.lyma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Buscar extends AppCompatActivity {

    static ArrayList<Cancion> cancionesBuscadas = new ArrayList<>(); //Minúscula la variable.
    ListView buscar;
    Button Boton_buscar;
    TextView textIntroducir;
    static Adaptador adaptador;
    String busqueda = " ";
    public static String tituloCancion, artistaCancion, urlPortadaCancion, completoTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        buscar = findViewById(R.id.lV_buscar);
        Boton_buscar = findViewById(R.id.buttonBuscarB);
        textIntroducir = findViewById(R.id.editTextBuscarB);

        adaptador = new Adaptador(DownloadTask.cancionesBuscadasDT,this);
        buscar.setAdapter(adaptador);

        Intent i = getIntent();
        i.getStringExtra("usuario");
    }

    public void onClickBuscarCancion (View v)
    {
        cancionesBuscadas.clear(); //Lo borramos si o si, desde luego.
        adaptador.notifyDataSetChanged();
        if (true)
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
        System.out.println("despues de oclick");
        for (int j = 0; j < cancionesBuscadas.size() ; j++)
        {
            System.out.println("AAAAAAAaa" + cancionesBuscadas.get(j).titulo);
            Log.i("despues", cancionesBuscadas.get(j).titulo );
        }

    }

}
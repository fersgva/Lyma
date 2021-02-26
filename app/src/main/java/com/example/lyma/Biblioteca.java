package com.example.lyma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Biblioteca extends AppCompatActivity {

    static ArrayList<Cancion> Canciones = new ArrayList<>();
    static ListView cancionesGuardadas;
    static AdaptadorBiblioteca adaptador;
    String tituloCancion,artistaCancion,urlPortadaCancion;
    Gson gson;
    SharedPreferences PreferenciasBiblioteca;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);
        cancionesGuardadas = findViewById(R.id.lV_biblioteca);

        Intent i = getIntent();
        id = i.getIntExtra("id",0);
        System.out.println("Se va a cargar biblioteca de: " + id);


        gson = new Gson();
        PreferenciasBiblioteca = getSharedPreferences("com.example.lymas2" + id,MODE_PRIVATE);

        String datosCargados = PreferenciasBiblioteca.getString("Canciones", null);

        //Debido a que el tipo no es sólo un único objeto, si no una lista, debemos crear un tipo basado en esa lista.
        Type type = new TypeToken<ArrayList<Cancion>>(){}.getType();
        Canciones = gson.fromJson(datosCargados, type);

        if (Canciones == null)
        {
            System.out.println("NO HAY DATOS PARA ESTE USUARIO");
            Canciones = new ArrayList<>();
        }

        adaptador = new AdaptadorBiblioteca(Canciones, this);

        cancionesGuardadas.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        cancionesGuardadas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(Biblioteca.this).setTitle("Quiere borrar esta cancion")
                        .setMessage("Estas seguro que quieres borrar esta cancion")
                        .setNegativeButton("NO",null)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Biblioteca.Canciones.remove(position);
                                String datosEnCadena = gson.toJson(Canciones);
                                PreferenciasBiblioteca.edit().putString("Canciones", datosEnCadena).apply();
                                adaptador.notifyDataSetChanged();

                            }
                        }).show();
                return true;
            }
        });

        cancionesGuardadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Biblioteca.this, Lyrics.class);
                tituloCancion = Canciones.get(position).titulo;
                Buscar.tituloCancion = tituloCancion;
                artistaCancion = Canciones.get(position).artistaNombre;
                Buscar.artistaCancion = artistaCancion;
                urlPortadaCancion = Canciones.get(position).urlFoto;
                Buscar.urlPortadaCancion = urlPortadaCancion;
                startActivity(i);
            }
        });
    }

    public void onClickIrALyrics (View v)
    {
        if (Buscar.tituloCancion != null) {
            Intent i = new Intent(this, Lyrics.class);
            i.putExtra("id" , id);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this, "Elige una canción antes de ir a Lyrics.", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickIrABuscar (View v)
    {
        Intent i = new Intent(this, Buscar.class);
        i.putExtra("id", id);
        startActivity(i);
    }

    public void onClickIrAPerfil (View v)
    {
        Intent i = new Intent(this, Perfil.class);
        i.putExtra("id" , id);
        startActivity(i);
    }
}
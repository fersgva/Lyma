package com.example.lyma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Biblioteca extends AppCompatActivity {

    static ArrayList<Cancion> Canciones = new ArrayList<>();
    static ListView cancionesGuardadas;
    static AdaptadorBiblioteca adaptador;
    String tituloCancion,artistaCancion,urlPortadaCancion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);
        cancionesGuardadas = findViewById(R.id.lV_biblioteca);

        adaptador = new AdaptadorBiblioteca(Canciones,this);
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
        Intent i = new Intent(this, Lyrics.class);
        startActivity(i);
    }
    public void onClickIrABuscar (View v)
    {
        Intent i = new Intent(this, Buscar.class);
        startActivity(i);
    }

    public void onClickIrAPerfil (View v)
    {
        Intent i = new Intent(this, Perfil.class);
        startActivity(i);
    }
}
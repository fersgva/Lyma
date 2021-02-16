package com.example.lyma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    ArrayList<Cancion> Canciones;
    Context context;
    ImageView añadir;

    public Adaptador(ArrayList<Cancion> canciones, Context context) {
        Canciones = canciones;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Canciones.size();    }

    @Override
    public Cancion getItem(int position) {
        return Canciones.get(position);    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Cancion Cancion = getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_adaptador, null);

        TextView TextoCancion = convertView.findViewById(R.id.textCancion);
        ImageView fotoCancion = convertView.findViewById(R.id.fotoCancion);
        añadir = convertView.findViewById(R.id.botonAnadir);

        TextoCancion.setText(Cancion.getTitulo());
        fotoCancion.setImageResource(Cancion.getFoto());

        return convertView;
    }

    public void onClick(View v){

        añadir.setImageResource(R.drawable.icon_corazon);

    }
}

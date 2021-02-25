package com.example.lyma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorBiblioteca extends BaseAdapter {

    ArrayList<Cancion> Canciones;
    Context context;

    public AdaptadorBiblioteca(ArrayList<Cancion> canciones, Context context) {
        this.Canciones = canciones;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Cancion Cancion = getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_adaptador, null);

        TextView TextoCancion = convertView.findViewById(R.id.textCancion);
        ImageView fotoCancion = convertView.findViewById(R.id.fotoCancion);

        Picasso.get().load(Biblioteca.Canciones.get(position).urlFoto).into(fotoCancion);
        TextoCancion.setText(Cancion.getTitulo());
        //fotoCancion.setImageResource(Cancion.getUrlFoto());


        return convertView;
    }

}

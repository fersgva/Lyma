package com.example.lyma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Perfil extends AppCompatActivity {

    ImageView foto;
    static String urlFoto;
    String userName;
    int posArray,id;
    TextView nombre , correo , contraseña , tvURL;
    Button eliminar , guardar, urlButton;
    Gson gson , gson1;
    static SharedPreferences preferenciasApp;
    static SharedPreferences preferenciasBilbioteca;
    ArrayList<usuarios> usuarios;
    ArrayList<Cancion> cancionEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        foto = findViewById(R.id.imageView10);
        nombre = findViewById(R.id.editTextNombreEP);
        correo = findViewById(R.id.editTextCorreoEEP);
        contraseña = findViewById(R.id.editTextContrasenaEP);
        eliminar = findViewById(R.id.buttonEliminarCuentaEP);
        guardar = findViewById(R.id.buttonGuardarCambiosEP);
        tvURL = findViewById(R.id.editTextNombreEPURL);
        urlButton = findViewById(R.id.buttonURLEP2);
        Intent in = getIntent();
        id = in.getIntExtra("id",0);

        //----------------------------------------------------USUARIOS----------------------------------------------------------------------
        gson = new Gson();
        preferenciasApp = getSharedPreferences("com.example.lymas",MODE_PRIVATE);

        //CARGADO DE DATOS.
        String datosCargados = preferenciasApp.getString("usuarios", null);

        //Debido a que el tipo no es sólo un único objeto, si no una lista, debemos crear un tipo basado en esa lista.
        Type type = new TypeToken<ArrayList<usuarios>>(){}.getType();
        usuarios = gson.fromJson(datosCargados, type);

        //---------------------------------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------Cargado de Canciones-----------------------------------------------------------
        gson1 = new Gson();
        preferenciasBilbioteca = getSharedPreferences("com.example.lymas2" + id,MODE_PRIVATE);

        //CARGADO DE DATOS.
        String datosCargados1 = preferenciasBilbioteca.getString("Canciones", null);

        //Debido a que el tipo no es sólo un único objeto, si no una lista, debemos crear un tipo basado en esa lista.
        Type type1 = new TypeToken<ArrayList<Cancion>>(){}.getType();
        cancionEliminar = gson.fromJson(datosCargados1, type1);
        //--------------------------------------------------------------------------------------------------------------------------------------



        for (int i = 0; i < usuarios.size(); i++) {

            if(usuarios.get(i).getId() == id){
                posArray = i;
                nombre.setText(usuarios.get(i).nombre);
                contraseña.setText(usuarios.get(i).contraseña);
                correo.setText(usuarios.get(i).correo);
                if (usuarios.get(i).imagen != null) {
                    tvURL.setText(usuarios.get(i).imagen);
                    onClicksubirFoto(null);
                }
            }
        }



    }
    public void onClickIrALyrics (View v)
    {
        if (Buscar.tituloCancion != null) {
        Intent i = new Intent(this, Lyrics.class);
        i.putExtra("id",id);
        startActivity(i);
        }
        else
        {
            Toast.makeText(this, "Elige una canción antes de ir a Lyrics", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickIrABuscar (View v)
    {
        Intent i = new Intent(this, Buscar.class);
        i.putExtra("id",id);
        startActivity(i);
    }
    public void onClickIrABiblioteca (View v)
    {
            Intent i = new Intent(this, Biblioteca.class);
            i.putExtra("id",id);
            startActivity(i);
    }
    public void onClicksubirFoto (View v)
    {
        urlFoto = tvURL.getText().toString();
        Picasso.get().load(urlFoto).into(foto);
        usuarios.get(posArray).setImagen(urlFoto);
    }
    public void onClickGuardarCambios (View v)
    {
        usuarios.get(posArray).setNombre(nombre.getText().toString());
        usuarios.get(posArray).setContraseña(contraseña.getText().toString());
        usuarios.get(posArray).setCorreo(correo.getText().toString());
        usuarios.get(posArray).setImagen(urlFoto);
        Toast.makeText(this, "Se han guardado los cambios correctamente", Toast.LENGTH_SHORT).show();
        String preferenciasEnString = gson.toJson(usuarios);
        preferenciasApp.edit().putString("usuarios", preferenciasEnString).apply();
/*
        String preferenciasEnString1 = gson.toJson(cancionesUsAnt);
        preferenciasBilbioteca.edit().putString("Canciones", preferenciasEnString1).apply();
        userName = usuarios.get(posArray).getNombre();*/
    }

    public void onClickEliminarcuenta (View v)
    {
        for (int i = 0; i < usuarios.size(); i++) {

            if(usuarios.get(i).getId() == id){

                //CrearCuenta.id = (usuarios.get(usuarios.size() - 1).getId()) + 1;
                usuarios.remove(i);

                if(cancionEliminar == null){

                    cancionEliminar.clear();

                }
                else{

                    cancionEliminar.clear();

                    if(Biblioteca.adaptador == null){



                    }else{

                        Biblioteca.adaptador.notifyDataSetChanged();

                    }


                }




                String preferenciasEnString = gson.toJson(usuarios);
                preferenciasApp.edit().putString("usuarios", preferenciasEnString).apply();



                String preferenciasBibliotecaEliminar = gson1.toJson(cancionEliminar);
                preferenciasBilbioteca.edit().putString("Canciones",preferenciasBibliotecaEliminar).apply();

                Toast.makeText(this, "Se ha eliminado tu cuenta", Toast.LENGTH_SHORT).show();
                Intent inten = new Intent(this,MainActivity.class);
                startActivity(inten);
            }

        }
    }

}
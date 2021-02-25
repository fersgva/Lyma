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
    int posArray;
    TextView nombre , correo , contraseña , tvURL;
    Button eliminar , guardar, urlButton;
    Gson gson;
    static SharedPreferences preferenciasApp;
    ArrayList<usuarios> usuarios;

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
        userName = in.getStringExtra("userName");

        gson = new Gson();
        preferenciasApp = getSharedPreferences("com.example.lymas2",MODE_PRIVATE);

        //CARGADO DE DATOS.
        String datosCargados = preferenciasApp.getString("usuarios", null);

        //Debido a que el tipo no es sólo un único objeto, si no una lista, debemos crear un tipo basado en esa lista.
        Type type = new TypeToken<ArrayList<usuarios>>(){}.getType();
        usuarios = gson.fromJson(datosCargados, type);

        for (int i = 0; i < usuarios.size(); i++) {

            if(usuarios.get(i).getNombre().compareTo(userName) == 0){
                posArray = i;
                nombre.setText(usuarios.get(i).nombre);
                contraseña.setText(usuarios.get(i).contraseña);
                correo.setText(usuarios.get(i).correo);
                tvURL.setText(usuarios.get(i).imagen);
            }
        }
        onClicksubirFoto(null);

    }
    public void onClickIrALyrics (View v)
    {
        if (Buscar.tituloCancion != null) {
        Intent i = new Intent(this, Lyrics.class);
        startActivity(i);
        }
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
    }

}
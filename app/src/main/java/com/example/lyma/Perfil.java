package com.example.lyma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {

    ImageView foto;
    TextView nombre , correo , contraseña , repetir;
    Button eliminar , guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        foto = findViewById(R.id.imageView10);
        nombre = findViewById(R.id.editTextNombreEP);
        correo = findViewById(R.id.editTextCorreoEEP);
        contraseña = findViewById(R.id.editTextContrasenaEP);
        repetir = findViewById(R.id.editTextREPContrasenaEP);
        eliminar = findViewById(R.id.buttonEliminarCuentaEP);
        guardar = findViewById(R.id.buttonGuardarCambiosEP);
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
    public void onClickIrABiblioteca (View v)
    {
        Intent i = new Intent(this, Biblioteca.class);
        startActivity(i);
    }

}
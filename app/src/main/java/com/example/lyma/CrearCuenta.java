package com.example.lyma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CrearCuenta extends AppCompatActivity {

    ArrayList<usuarios> usuarios;
    TextView usuario , correo , contraseña;
    Button crearCuenta;
    boolean iniciado = false;
    boolean existe = false;
    Gson gson;
    static SharedPreferences preferenciasApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        usuario = findViewById(R.id.editTextNombreEP);
        correo =findViewById(R.id.editTextCorreoEEP);
        contraseña = findViewById(R.id.editTextContrasenaEP);
        crearCuenta = findViewById(R.id.buttonCrearCuenta5);
        gson = new Gson();
        preferenciasApp = getSharedPreferences("com.example.lyma11",MODE_PRIVATE);

        //CARGADO DE DATOS.
        String datosCargados = preferenciasApp.getString("usuarios", null);

        //Debido a que el tipo no es sólo un único objeto, si no una lista, debemos crear un tipo basado en esa lista.
        Type type = new TypeToken<ArrayList<usuarios>>(){}.getType();
        usuarios = gson.fromJson(datosCargados, type);

    }
    public void onClickCrearCuenta (View v)
    {
        //preferenciasApp = getSharedPreferences("com.example.lyma_user" + numUsuario,MODE_PRIVATE);
        System.out.println("Clickado");
        if(usuarios == null)
        {
            usuarios = new ArrayList<>();
            System.out.println("Sin usuarios");
            usuarios.add(new usuarios(usuario.getText().toString(),contraseña.getText().toString(),correo.getText().toString(),R.drawable.lyma_logo));

            //PROCESO DE GUARDADO CON LIBRERÍAS DE GSON.
            String usuariosEnString = gson.toJson(usuarios);
            preferenciasApp.edit().putString("usuarios", usuariosEnString).apply();

            String usuario = correo.getText().toString();
            Intent i = new Intent(this,Buscar.class);
            i.putExtra("userName", usuario);
            startActivity(i);
        }else{
            System.out.println(usuarios.size());
            for (int j = 0; j < usuarios.size(); j++) {

                if(usuarios.get(j).getCorreo().compareTo(correo.getText().toString()) == 0){

                    existe = true;
                    Toast.makeText(this, "El correo coincide con otro usuario , por favor pon otro", Toast.LENGTH_SHORT).show();
                }

            }
            if(existe == false){
                usuarios.add(new usuarios(usuario.getText().toString(),contraseña.getText().toString(),correo.getText().toString(),R.drawable.lyma_logo));
                //PROCESO DE GUARDADO CON LIBRERÍAS DE GSON.
                String usuariosEnString = gson.toJson(usuarios);
                preferenciasApp.edit().putString("usuarios", usuariosEnString);

                Intent i = new Intent(this,Buscar.class);
                startActivity(i);
            }
        }
    }
}
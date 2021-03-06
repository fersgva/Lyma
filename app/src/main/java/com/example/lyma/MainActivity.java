package com.example.lyma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<usuarios> usuarios;
    TextView correo , contraseña;
    Button iniciar , crear;
    boolean iniciado = false;
    static SharedPreferences preferencesusuaio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        correo = findViewById(R.id.editTextCorreoEEP);
        contraseña =findViewById(R.id.editTextContrasenaEP);
        iniciar = findViewById(R.id.buttonIniciarSesion);
        crear = findViewById(R.id.buttonCrearCuenta);
        Log.i("PASO", "PASO");

    }

    public void onClickIniciarSesion (View v)
    {
        //--------------------PROCESO DE CARGADO.--------------------------//
        //ESTABÁIS CARGANDO LOS SHAREDPREFERENCES EN EL ONCREATE DE LA ACTIVITY.
        //Es decir, SÓLO se cargan los datos la PRIMERA VEZ que se abre esta activity.
        //Por lo tanto, si creo un usuario y luego doy atrás atrás atrás para intentar
        //hacer Log In con el nuevo usuario, no se volverán a cargar los preferences a no ser
        //que les carguemos AQUÍ, al hacer click en el botón.
        //Entiendo que con la biblioteca es lo mismo, pero no consigo acceder a la biblioteca
        //Intentadlo y mañana lo vemos.
        preferencesusuaio = getSharedPreferences("com.example.lymas",MODE_PRIVATE);
        String datosCargados = preferencesusuaio.getString("usuarios", null);

        Gson gson = new Gson();
        //Debido a que el tipo no es sólo un único objeto, si no una lista, debemos crear un tipo basado en esa lista.
        Type type = new TypeToken<ArrayList<usuarios>>(){}.getType();
        usuarios = gson.fromJson(datosCargados, type);

        if(usuarios == null) {
            usuarios = new ArrayList<>();

            //usuarios.get(i).

        }

            for (int i = 0; i < usuarios.size(); i++) {

                if(usuarios.get(i).getNombre().compareTo(correo.getText().toString()) == 0 && usuarios.get(i).getContraseña().compareTo(contraseña.getText().toString()) == 0){

                    iniciado = true;
                    Intent intento = new Intent(this,Buscar.class);
                    intento.putExtra("id" , usuarios.get(i).getId());
                    startActivity(intento);
                }
            }

            if(iniciado == false){
                Toast.makeText(this, "No puedes iniciar sesion , registrate", Toast.LENGTH_SHORT).show();
            }

    }
    public void onClickIrACrearCuenta (View v)
    {
        Intent i = new Intent(this, CrearCuenta.class);
        startActivity(i);
    }
}
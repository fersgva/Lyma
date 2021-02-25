package com.example.lyma;

import android.widget.TextView;

public class usuarios {

    String nombre , contraseña , correo, imagen;

    public usuarios(String nombre, String contraseña, String correo, String imagen) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

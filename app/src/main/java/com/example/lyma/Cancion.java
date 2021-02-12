package com.example.lyma;

import android.widget.ImageView;

public class Cancion {

    String titulo;
    int foto;
    boolean guardado;

    public Cancion(String titulo, int foto, boolean guardado) {
        this.titulo = titulo;
        this.foto = foto;
        this.guardado = guardado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }
}

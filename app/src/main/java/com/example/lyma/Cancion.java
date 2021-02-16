package com.example.lyma;

import android.widget.ImageView;

public class Cancion {

    String titulo, artistaNombre, urlFoto;

    public Cancion(String titulo, String artistaNombre, String urlFoto) {
        this.titulo = titulo;
        this.artistaNombre = artistaNombre;
        this.urlFoto = urlFoto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtistaNombre() {
        return artistaNombre;
    }

    public void setArtistaNombre(String artistaNombre) {
        this.artistaNombre = artistaNombre;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}

package com.example.ejercicio3;

import android.graphics.Bitmap;

public class Estudiante {
    public int  id;
    public String nombre;
    public String matricula;
    public String foto;
    public String ciudad;
    public String exprecion;
    public Bitmap image;

    public Estudiante(String nombre, String matricula, String foto, String ciudad,
                      String exprecion) {

        this.nombre = nombre;
        this.matricula = matricula;
        this.foto = foto;
        this.ciudad = ciudad;
        this.exprecion = exprecion;
        this.image = image;
    }

    public Estudiante(int id, String nombre, String matricula, String foto,
                      String ciudad, String exprecion) {

        this.id = id;
        this.nombre = nombre;
        this.matricula = matricula;
        this.foto = foto;
        this.ciudad = ciudad;
        this.exprecion = exprecion;
        this.image = image;


    }
}

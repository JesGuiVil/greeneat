package com.example.basedatos;

import android.graphics.Bitmap;

public class Producto {
    private long id;
    private String nombre;
    private String seccion;
    private double precio;
    private double iva;
    private double peso;
    private int stock;
    private String descripcion;
    private long idProveedor;
    private String imagen; // Cambiado de String a Bitmap para representar la imagen

    // Constructor
    public Producto(long id, String nombre, String seccion, double precio, double iva, double peso,
                    int stock, String descripcion, long idProveedor, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.seccion = seccion;
        this.precio = precio;
        this.iva = iva;
        this.peso = peso;
        this.stock = stock;
        this.descripcion = descripcion;
        this.idProveedor = idProveedor;
        this.imagen = imagen;
    }

    // Getters y setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getIVA() {
        return iva;
    }

    public void setIVA(double iva) {
        this.iva = iva;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

package com.example.GreenEat;

import java.util.List;

public class Pedido {
    private long idPedido;
    private long idUsuario;
    private String fechaPedido;
    private double costeTotal;

    private List<Producto> productosIncluidos;

    // Constructor
    public Pedido(long idPedido, long idUsuario, String fechaPedido, double costeTotal, List<Producto> productosIncluidos) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.fechaPedido = fechaPedido;
        this.costeTotal = costeTotal;
        this.productosIncluidos = productosIncluidos;
    }

    // Getters y setters

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public double getCosteTotal() {
        return costeTotal;
    }

    public void setCosteTotal(double costeTotal) {
        this.costeTotal = costeTotal;
    }
    public void setProductosIncluidos(List<Producto> productosIncluidos) {
        this.productosIncluidos = productosIncluidos;
    }

    // Método para obtener la lista de productos
    public List<Producto> getProductosIncluidos() {
        return productosIncluidos;
    }
}


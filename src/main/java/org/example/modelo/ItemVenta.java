package org.example.modelo;

public class ItemVenta {
    private int id;
    private int idVenta;
    private Celular celular;
    private int cantidad;
    private double subtotal;

    public ItemVenta() {
    }

    public ItemVenta(int id, int idVenta, Celular celular, int cantidad, double subtotal) {
        this.id = id;
        this.idVenta = idVenta;
        this.celular = celular;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public Celular getCelular() { return celular; }
    public void setCelular(Celular celular) { this.celular = celular; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return " - " + celular.getMarca() + " " + celular.getModelo() + " x" + cantidad + " (Subtotal: $" + subtotal + ")";
    }
}
package org.example.modelo;

import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int id;
    private int idCliente;
    private String fecha;
    private double total;
    private List<ItemVenta> items;

    public Venta() {
        this.items = new ArrayList<>();
    }

    public Venta(int id, int idCliente, String fecha, double total) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.total = total;
        this.items = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public List<ItemVenta> getItems() { return items; }
    public void setItems(List<ItemVenta> items) { this.items = items; }

    public void agregarItem(ItemVenta item) {
        this.items.add(item);
    }
}
package org.example.logica;

import org.example.modelo.ItemVenta;
import org.example.modelo.Venta;
import persistencia.VentaDAO;

public class GestorVentas {
    private final VentaDAO ventaDAO;
    private static final double PORCENTAJE_IVA = 0.19;

    public GestorVentas() {
        this.ventaDAO = new VentaDAO();
    }

    public boolean procesarVenta(Venta venta) {
        if (venta.getItems().isEmpty()) {
            System.out.println("Error: La venta no tiene productos asignados.");
            return false;
        }

        double subtotalNeto = 0.0;

        for (ItemVenta item : venta.getItems()) {
            if (item.getCantidad() > item.getCelular().getStock()) {
                System.out.println("Error de Stock: No hay suficientes unidades de "
                        + item.getCelular().getMarca() + " " + item.getCelular().getModelo()
                        + ". Solicitadas: " + item.getCantidad() + ", Disponibles: " + item.getCelular().getStock());
                return false;
            }

            double subtotalItem = item.getCelular().getPrecio() * item.getCantidad();
            item.setSubtotal(subtotalItem);

            subtotalNeto += subtotalItem;
        }

        double valorIva = subtotalNeto * PORCENTAJE_IVA;
        double totalConIva = subtotalNeto + valorIva;

        venta.setTotal(totalConIva);

        boolean exito = ventaDAO.registrarVenta(venta);

        if (exito) {

            System.out.println("       RESUMEN DE LA FACTURA        ");
            System.out.println("Subtotal: $" + String.format("%.2f", subtotalNeto));
            System.out.println("IVA (19%): $" + String.format("%.2f", valorIva));
            System.out.println("TOTAL A PAGAR: $" + String.format("%.2f", totalConIva));
        }

        return exito;
    }
}
package org.example.utilidades;

import org.example.modelo.Celular;
import org.example.modelo.ItemVenta;
import org.example.modelo.Venta;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteUtils {


    public static void mostrarStockBajo(List<Celular> catalogo) {
        System.out.println("\n--- CELULARES CON STOCK BAJO (< 5) ---");
        List<Celular> stockBajo = catalogo.stream()
                .filter(c -> c.getStock() < 5)
                .collect(Collectors.toList());
        if (stockBajo.isEmpty()) {
            System.out.println("Todos los celulares tienen buen nivel de stock.");
        } else {
            stockBajo.forEach(c -> System.out.println("ID: " + c.getId() + " | " + c.getMarca() + " " + c.getModelo() + " | Stock actual: " + c.getStock()));
        }
    }


    public static void mostrarTop3Vendidos(List<Venta> ventas, List<Celular> catalogo) {
        System.out.println("\n--- TOP 3 CELULARES MAS VENDIDOS ---");
        Map<Integer, Integer> ventasPorCelular = ventas.stream()
                .flatMap(v -> v.getItems().stream())
                .collect(Collectors.groupingBy(
                        item -> item.getCelular().getId(),
                        Collectors.summingInt(ItemVenta::getCantidad)
                ));
        if (ventasPorCelular.isEmpty()) {
            System.out.println("Aun no hay ventas registradas.");
            return;
        }


        ventasPorCelular.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .forEach(entry -> {
                    int idCelular = entry.getKey();
                    int cantidadVendida = entry.getValue();

                    String nombreCelular = catalogo.stream()
                            .filter(c -> c.getId() == idCelular)
                            .map(c -> c.getMarca() + " " + c.getModelo())
                            .findFirst()
                            .orElse("Celular Desconocido (ID: " + idCelular + ")");

                    System.out.println(nombreCelular + " -> " + cantidadVendida + " unidades vendidas.");
                });
    }


    public static void mostrarVentasPorMes(List<Venta> ventas) {
        System.out.println("\n--- INGRESOS TOTALES POR MES ---");
        Map<String, Double> ingresosPorMes = ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getFecha().substring(0, 7),
                        Collectors.summingDouble(Venta::getTotal)
                ));

        if (ingresosPorMes.isEmpty()) {
            System.out.println("No hay ingresos registrados.");
        } else {
            ingresosPorMes.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        System.out.println("Mes: " + entry.getKey() + " | Ingresos: $" + String.format("%.2f", entry.getValue()));
                    });
        }
    }


    public static void generarReporteTxt(List<Venta> ventas) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter("reporte_ventas.txt"))) {
            writer.println("--- reporte general de ventas ---");
            if (ventas.isEmpty()) {
                writer.println("no hay ventas registradas en el sistema");
            } else {
                ventas.forEach(v -> {
                    writer.println("factura id: " + v.getId() + " | cliente id: " + v.getIdCliente() + " | fecha: " + v.getFecha());
                    writer.println("total: $" + String.format("%.2f", v.getTotal()));
                    writer.println("--------------------------------");
                });
            }
            System.out.println("reporte_ventas.txt generado con exito");
        } catch (java.io.IOException e) {
            System.out.println("error al generar el archivoreporte_ventas.txt: " + e.getMessage());
        }
    }
}
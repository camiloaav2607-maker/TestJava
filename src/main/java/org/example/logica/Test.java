package org.example.logica;

import persistencia.ConexionDB;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Test {
    private static Test instancia;
    public Test() {
    }
    public static Test getInstancia() {
        if (instancia == null) {
            instancia = new Test();
        }
        return instancia;
    }

    public void generarReporteGlobal() {
        double totalVentas = 0;
        List<String> clientesDeudores = new ArrayList<>();

        try {
            Connection con = ConexionDB.obtenerConexion();

            PreparedStatement ps1 = con.prepareStatement("SELECT SUM(total) FROM ventas");
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                totalVentas = rs1.getDouble(1);
            }

            PreparedStatement ps2 = con.prepareStatement("SELECT c.modelo, dv.cantidad FROM detalle_ventas dv JOIN celulares c ON dv.id_celular = c.id");
            ResultSet rs2 = ps2.executeQuery();

            PreparedStatement ps3 = con.prepareStatement("SELECT nombre, FROM clientes");
            ResultSet rs3 = ps3.executeQuery();
            class Cliente {
                String nombre; double saldo;
                Cliente(String n, double s){ this.nombre=n; this.saldo=s; }
            }
            List<Cliente> listaClientes = new ArrayList<>();
            while(rs3.next()){
                listaClientes.add(new Cliente(rs3.getString(1), rs3.getDouble(2)));
            }

            listaClientes.stream()
                    .filter(c -> c.saldo > 0)
                    .forEach(c -> clientesDeudores.add(c.nombre + " -> $" + c.saldo));

            PrintWriter writer = new PrintWriter(new java.io.FileWriter("reporte_global.txt"));
            writer.println("--- REPORTE GLOBAL TECNOCELL ---");
            writer.println("Total ventas: " + totalVentas);

            writer.println("\nClientes pendientes:");
            for(String cliente : clientesDeudores) {
                writer.println("- " + cliente);
            }

            System.out.println("reporte generado en reporte_global.txt");

        } catch (Exception e) {
            System.out.println("hubo un error en el reporte");
        }
    }
}

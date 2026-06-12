
package org.example;

import org.example.logica.GestorCelulares;
import org.example.logica.GestorClientes;
import org.example.logica.GestorVentas;
import org.example.logica.Test;
import org.example.modelo.CategoriaGama;
import org.example.modelo.Celular;
import org.example.modelo.Cliente;
import org.example.modelo.ItemVenta;
import org.example.modelo.Venta;
import org.example.utilidades.ReporteUtils;
import persistencia.VentaDAO;

import java.util.List;
import java.util.Scanner;
import

public class Main {
    private static final GestorCelulares gestorCelulares = new GestorCelulares();
    private static final GestorClientes gestorClientes = new GestorClientes();
    private static final GestorVentas gestorVentas = new GestorVentas();
    private static final VentaDAO ventaDAO = new VentaDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("==== sistema tecnostore - menu ====");
            System.out.println("1. gestion de catalogo (celulares)");
            System.out.println("2. gestion de clientes");
            System.out.println("3. gestion de ventas");
            System.out.println("4. informes y reportes");
            System.out.println("5. Generar Reporte Global de Gestión (TXT)");
            System.out.println("6. salir del sistema");
            System.out.print("seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    menuCelulares();
                    break;
                case 2:
                    menuClientes();
                    break;
                case 3:
                    menuVentas();
                    break;
                case 4:
                    menuReportes();
                    break;
                case 5:
                    Test.getInstancia();
                    break;
                case 6:
                    System.out.println("saliendo del sistema tecnostore...");
                default:
                    System.out.println("opcion invalida");
            }
        } while (opcion != 6);
    }

    private static void menuCelulares() {
        int opcion;
        do {
            System.out.println("\n--- gestion de celulares ---");
            System.out.println("1. listar catalogo de celulares");
            System.out.println("2. registrar nuevo celular");
            System.out.println("3. actualizar celular");
            System.out.println("4. eliminar celular");
            System.out.println("5. volver al menu principal");
            System.out.print("seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- catalogo de celulares ---");
                    List<Celular> lista = gestorCelulares.obtenerCatalogo();
                    if (lista.isEmpty()) {
                        System.out.println("no hay celulares registrados");
                    } else {
                        lista.forEach(System.out::println);
                    }
                    break;
                case 2:
                    System.out.println("\n--- registrar celular ---");
                    System.out.print("marca: "); String marca = scanner.nextLine();
                    System.out.print("modelo: "); String modelo = scanner.nextLine();
                    System.out.print("sistema operativo: "); String so = scanner.nextLine();
                    System.out.print("gama (alta, media, baja): "); String gamaInput = scanner.nextLine().toUpperCase();
                    System.out.print("precio: "); double precio = scanner.nextDouble();
                    System.out.print("stock inicial: "); int stock = scanner.nextInt();
                    try {
                        CategoriaGama gama = CategoriaGama.valueOf(gamaInput);
                        gestorCelulares.registrarCelular(new Celular(0, marca, modelo, so, gama, precio, stock));
                    } catch (IllegalArgumentException e) {
                        System.out.println("error: gama no valida, use alta, media o baja");
                    }
                    break;
                case 3:
                    System.out.println("\n--- actualizar celular ---");
                    System.out.print("id del celular a modificar: "); int idMod = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("nueva marca: "); String nMarca = scanner.nextLine();
                    System.out.print("nuevo modelo: "); String nModelo = scanner.nextLine();
                    System.out.print("nuevo sistema operativo: "); String nSo = scanner.nextLine();
                    System.out.print("nueva gama (alta, media, baja): "); String nGamaInput = scanner.nextLine().toUpperCase();
                    System.out.print("nuevo precio: "); double nPrecio = scanner.nextDouble();
                    System.out.print("nuevo stock: "); int nStock = scanner.nextInt();
                    try {
                        CategoriaGama nGama = CategoriaGama.valueOf(nGamaInput);
                        gestorCelulares.actualizarCelular(new Celular(idMod, nMarca, nModelo, nSo, nGama, nPrecio, nStock));
                    } catch (IllegalArgumentException e) {
                        System.out.println("error: gama no valida");
                    }
                    break;
                case 4:
                    System.out.println("\n--- eliminar celular ---");
                    System.out.print("id del celular a eliminar: "); int idEli = scanner.nextInt();
                    gestorCelulares.eliminarCelular(idEli);
                    break;
            }
        } while (opcion != 5);
    }

    private static void menuClientes() {
        int opcion;
        do {
            System.out.println("\n--- gestion de clientes ---");
            System.out.println("1. listar clientes");
            System.out.println("2. registrar nuevo cliente");
            System.out.println("3. volver al menu principal");
            System.out.print("seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- listado de clientes ---");
                    List<Cliente> listaClientes = gestorClientes.obtenerClientes();
                    if (listaClientes.isEmpty()) {
                        System.out.println("no hay clientes registrados");
                    } else {
                        listaClientes.forEach(System.out::println);
                    }
                    break;
                case 2:
                    System.out.println("\n--- registrar cliente ---");
                    System.out.print("nombre completo: "); String nombre = scanner.nextLine();
                    System.out.print("documento de identificacion: "); String iden = scanner.nextLine();
                    System.out.print("correo electronico: "); String correo = scanner.nextLine();
                    System.out.print("telefono: "); String telefono = scanner.nextLine();

                    Cliente nuevoCliente = new Cliente(0, nombre, iden, correo, telefono);
                    gestorClientes.registrarCliente(nuevoCliente);
                    break;
            }
        } while (opcion != 3);
    }

    private static void menuVentas() {
        System.out.println("\n--- nuevo registro de venta ---");

        List<Cliente> clientes = gestorClientes.obtenerClientes();
        if (clientes.isEmpty()) {
            System.out.println("atencion: no hay clientes en la DB, registre uno primero");
            return;
        }
        System.out.println("clientes disponibles:");
        clientes.forEach(c -> System.out.println("id: " + c.getId() + " | nombre: " + c.getNombre() + " | cc: " + c.getIdentificacion()));

        System.out.print("ingrese el id del cliente que realiza la compra: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();

        Cliente clienteSeleccionado = clientes.stream()
                .filter(c -> c.getId() == idCliente)
                .findFirst()
                .orElse(null);

        if (clienteSeleccionado == null) {
            System.out.println("error: cliente no encontrado");
            return;
        }

        Venta nuevaVenta = new Venta(0, idCliente, "", 0.0);
        boolean seguirComprando = true;

        while (seguirComprando) {
            List<Celular> catalogo = gestorCelulares.obtenerCatalogo();
            if (catalogo.isEmpty()) {
                System.out.println("no hay celulares en el catalogo");
                break;
            }

            System.out.println("\ncatalogo disponible:");
            catalogo.forEach(c -> System.out.println("id: " + c.getId() + " | " + c.getMarca() + " " + c.getModelo() + " | precio: $" + c.getPrecio() + " | stock: " + c.getStock()));

            System.out.print("ingrese el id del celular a comprar: ");
            int idCelular = scanner.nextInt();

            Celular celularSel = catalogo.stream()
                    .filter(c -> c.getId() == idCelular)
                    .findFirst()
                    .orElse(null);

            if (celularSel == null) {
                System.out.println("error: id de celular no valido");
            } else if (celularSel.getStock() <= 0) {
                System.out.println("error: producto agotado");
            } else {
                System.out.print("cantidad a llevar: ");
                int cantidad = scanner.nextInt();
                scanner.nextLine();

                if (cantidad > celularSel.getStock()) {
                    System.out.println("error: solo hay " + celularSel.getStock() + " unidades disponibles");
                } else if (cantidad <= 0) {
                    System.out.println("error: la cantidad debe ser mayor a cero");
                } else {
                    ItemVenta item = new ItemVenta(0, 0, celularSel, cantidad, 0.0);
                    nuevaVenta.agregarItem(item);
                    System.out.println("producto anadido al carrito exitosamente");
                }
            }

            System.out.print("\n¿desea agregar otro producto a esta factura? (s/n): ");
            String respuesta = scanner.next();
            scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                seguirComprando = false;
            }
        }

        if (!nuevaVenta.getItems().isEmpty()) {
            System.out.println("\nprocesando transaccion");
            gestorVentas.procesarVenta(nuevaVenta);
        } else {
            System.out.println("venta cancelada, el carrito esta vacio");
        }
    }

    private static void menuReportes() {
        int opcion;
        do {
            System.out.println("\n--- submenu: informes y reportes ---");
            System.out.println("1. mostrar celulares con stock bajo (< 5)");
            System.out.println("2. top 3 celulares mas vendidos");
            System.out.println("3. ingresos totales por mes");
            System.out.println("4. generar archivo reporte_ventas.txt");
            System.out.println("5. volver al menu principal");
            System.out.print("seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    ReporteUtils.mostrarStockBajo(gestorCelulares.obtenerCatalogo());
                    break;
                case 2:
                    List<Venta> todasLasVentas = ventaDAO.obtenerTodasLasVentas();
                    List<Celular> catalogoActual = gestorCelulares.obtenerCatalogo();
                    ReporteUtils.mostrarTop3Vendidos(todasLasVentas, catalogoActual);
                    break;
                case 3:
                    ReporteUtils.mostrarVentasPorMes(ventaDAO.obtenerTodasLasVentas());
                    break;
                case 4:
                    System.out.println("generando reporte en archivo de texto...");
                    ReporteUtils.generarReporteTxt(ventaDAO.obtenerTodasLasVentas());
                    break;
                case 5:
                    System.out.println("volviendo al menu principal");
                    break;
                default:
                    System.out.println("opcion invalida");
            }
        } while (opcion != 5);
    }
}
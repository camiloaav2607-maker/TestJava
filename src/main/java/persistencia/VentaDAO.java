package persistencia;

import org.example.modelo.Celular;
import org.example.modelo.ItemVenta;
import org.example.modelo.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    public boolean registrarVenta(Venta venta) {
        String sqlVenta = "INSERT INTO ventas (id_cliente, total) VALUES (?, ?)";
        String sqlItem = "INSERT INTO detalle_ventas (id_venta, id_celular, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        String sqlUpdateStock = "UPDATE celulares SET stock = stock - ? WHERE id = ?";

        Connection con = null;
        boolean exito = false;

        try {
            con = ConexionDB.obtenerConexion();
            con.setAutoCommit(false);

            try (PreparedStatement psVenta = con.prepareStatement(sqlVenta, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                psVenta.setInt(1, venta.getIdCliente());
                psVenta.setDouble(2, venta.getTotal());
                psVenta.executeUpdate();

                try (ResultSet rs = psVenta.getGeneratedKeys()) {
                    if (rs.next()) {
                        venta.setId(rs.getInt(1));
                    }
                }
            }

            try (PreparedStatement psItem = con.prepareStatement(sqlItem);
                 PreparedStatement psUpdateStock = con.prepareStatement(sqlUpdateStock)) {

                for (ItemVenta item : venta.getItems()) {
                    psItem.setInt(1, venta.getId());
                    psItem.setInt(2, item.getCelular().getId());
                    psItem.setInt(3, item.getCantidad());
                    psItem.setDouble(4, item.getSubtotal());
                    psItem.addBatch();

                    psUpdateStock.setInt(1, item.getCantidad());
                    psUpdateStock.setInt(2, item.getCelular().getId());
                    psUpdateStock.addBatch();
                }

                psItem.executeBatch();
                psUpdateStock.executeBatch();
            }

            con.commit();
            System.out.println("transaccion completada y base de datos actualizada");
            exito = true;

        } catch (SQLException e) {
            System.out.println("error procesando la venta, revirtiendo cambios");
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("error haciendo rollback");
            }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("error cerrando la conexion");
            }
        }
        return exito;
    }

    public List<Venta> obtenerTodasLasVentas() {
        List<Venta> listaVentas = new ArrayList<>();
        String sqlVentas = "SELECT * FROM ventas";
        String sqlDetalles = "SELECT * FROM detalle_ventas WHERE id_venta = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement psVentas = con.prepareStatement(sqlVentas);
             ResultSet rsVentas = psVentas.executeQuery()) {

            while (rsVentas.next()) {
                Venta venta = new Venta();
                venta.setId(rsVentas.getInt("id"));
                venta.setIdCliente(rsVentas.getInt("id_cliente"));
                venta.setFecha(rsVentas.getTimestamp("fecha").toString());
                venta.setTotal(rsVentas.getDouble("total"));

                try (PreparedStatement psDetalles = con.prepareStatement(sqlDetalles)) {
                    psDetalles.setInt(1, venta.getId());
                    try (ResultSet rsDetalles = psDetalles.executeQuery()) {
                        while (rsDetalles.next()) {
                            ItemVenta item = new ItemVenta();
                            item.setId(rsDetalles.getInt("id"));
                            item.setIdVenta(rsDetalles.getInt("id_venta"));

                            Celular celularRef = new Celular();
                            celularRef.setId(rsDetalles.getInt("id_celular"));
                            item.setCelular(celularRef);

                            item.setCantidad(rsDetalles.getInt("cantidad"));
                            item.setSubtotal(rsDetalles.getDouble("subtotal"));

                            venta.agregarItem(item);
                        }
                    }
                }
                listaVentas.add(venta);
            }
        } catch (SQLException e) {
            System.out.println("error al obtener el historial de ventas");
        }
        return listaVentas;
    }
}
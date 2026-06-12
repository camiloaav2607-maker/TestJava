package persistencia;

import org.example.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void guardar(Producto producto) {
        String sql = "INSERT INTO productos (nombre, marca, precio, stock) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getMarca());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());

            ps.executeUpdate();
            System.out.println("Producto guardado en TecnoStore con exito");

        } catch (SQLException e) {
            System.err.println("Error al guardar el producto: " + e.getMessage());
        }
    }

    public List<Producto> listar() {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setMarca(rs.getString("marca"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));

                listaProductos.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar los productos: " + e.getMessage());
        }

        return listaProductos;
    }

    public void actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, marca = ?, precio = ?, stock = ? WHERE id = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getMarca());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());
            ps.setInt(5, producto.getId());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Producto actualizado con exito");
            } else {
                System.out.println("No se encontró ningún producto con el ID especificado.");
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar el producto: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Producto eliminado con éxito");
            } else {
                System.out.println(" No se encontró ningún producto con el ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println(" Error al eliminar el producto: " + e.getMessage());
        }
    }
}
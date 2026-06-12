package persistencia;

import org.example.modelo.Celular;
import org.example.modelo.CategoriaGama;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CelularDAO {

    public void guardar(Celular celular) {
        String sql = "INSERT INTO celulares (marca, modelo, sistema_operativo, gama, precio, stock) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, celular.getMarca());
            ps.setString(2, celular.getModelo());
            ps.setString(3, celular.getSistemaOperativo());
            ps.setString(4, celular.getGama().name());
            ps.setDouble(5, celular.getPrecio());
            ps.setInt(6, celular.getStock());
            ps.setInt(6, celular.getStock());

            ps.executeUpdate();
            System.out.println("Celular registrado");
        } catch (SQLException e) {
            System.err.println("Error al guardar celular: " + e.getMessage());
        }
    }

    public List<Celular> listar() {
        List<Celular> lista = new ArrayList<>();
        String sql = "SELECT * FROM celulares";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Celular c = new Celular();
                c.setId(rs.getInt("id"));
                c.setMarca(rs.getString("marca"));
                c.setModelo(rs.getString("modelo"));
                c.setSistemaOperativo(rs.getString("sistema_operativo"));
                // Convertimos el String de la BD de vuelta a nuestro Enum Java
                c.setGama(CategoriaGama.valueOf(rs.getString("gama").toUpperCase()));
                c.setPrecio(rs.getDouble("precio"));
                c.setStock(rs.getInt("stock"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar celulares: " + e.getMessage());
        }
        return lista;
    }

    public void actualizar(Celular celular) {
        String sql = "UPDATE celulares SET marca=?, modelo=?, sistema_operativo=?, gama=?, precio=?, stock=? WHERE id=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, celular.getMarca());
            ps.setString(2, celular.getModelo());
            ps.setString(3, celular.getSistemaOperativo());
            ps.setString(4, celular.getGama().name());
            ps.setDouble(5, celular.getPrecio());
            ps.setInt(6, celular.getStock());
            ps.setInt(7, celular.getId());

            ps.executeUpdate();
            System.out.println("Celular actualizado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar celular: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM celulares WHERE id=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Celular eliminado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar celular: " + e.getMessage());
        }
    }
}

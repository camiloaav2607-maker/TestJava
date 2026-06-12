package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private ConexionDB() { }
    private static Connection conexion;
    private static final String HOST = "mysql-1864721a-camiloaav2607-0dc7.b.aivencloud.com";
    private static final String PUERTO = "10002";
    private static final String BASE_DATOS = "TecnoStore";
    private static final String USUARIO = "avnadmin";
    private static final String PASSWORD = "AVNS_-Qo5GWNIm2W4s09e9Xg";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + BASE_DATOS + "?sslMode=REQUIRED";

    public static Connection obtenerConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("Conexión a la base de datos establecida con exito");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexion cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }
}



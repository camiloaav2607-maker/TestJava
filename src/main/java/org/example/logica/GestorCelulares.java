package org.example.logica;

import org.example.modelo.Celular;
import persistencia.CelularDAO;
import java.util.List;

public class GestorCelulares {
    private final CelularDAO celularDAO;

    public GestorCelulares() {
        this.celularDAO = new CelularDAO();
    }

    public boolean registrarCelular(Celular celular) {
        if (celular.getPrecio() <= 0) {
            System.out.println("Error de validación: El precio debe ser mayor a cero.");
            return false;
        }
        if (celular.getStock() < 0) {
            System.out.println("Error de validación: El stock no puede ser negativo.");
            return false;
        }

        celularDAO.guardar(celular);
        return true;
    }

    public List<Celular> obtenerCatalogo() {
        return celularDAO.listar();
    }

    public void actualizarCelular(Celular celular) {
        if (celular.getPrecio() <= 0 || celular.getStock() < 0) {
            System.out.println("Error: Datos inválidos para actualizar.");
            return;
        }
        celularDAO.actualizar(celular);
    }

    public void eliminarCelular(int id) {
        celularDAO.eliminar(id);
    }
}
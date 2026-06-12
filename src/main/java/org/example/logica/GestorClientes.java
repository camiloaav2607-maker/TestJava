package org.example.logica;

import org.example.modelo.Cliente;
import persistencia.ClienteDAO;
import java.util.List;
import java.util.regex.Pattern;

public class GestorClientes {
    private final ClienteDAO clienteDAO;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public GestorClientes() {
        this.clienteDAO = new ClienteDAO();
    }

    public boolean registrarCliente(Cliente cliente) {
        if (!validarCorreo(cliente.getCorreo())) {
            System.out.println("Error de validación: El formato del correo electrónico no es válido.");
            return false;
        }

        Cliente clienteExistente = clienteDAO.buscarPorIdentificacion(cliente.getIdentificacion());
        if (clienteExistente != null) {
            System.out.println("Error de validación: Ya existe un cliente registrado con la identificación: " + cliente.getIdentificacion());
            return false;
        }

        clienteDAO.guardar(cliente);
        return true;
    }

    public List<Cliente> obtenerClientes() {
        return clienteDAO.listar();
    }

    private boolean validarCorreo(String correo) {
        return Pattern.compile(EMAIL_REGEX).matcher(correo).matches();
    }
}
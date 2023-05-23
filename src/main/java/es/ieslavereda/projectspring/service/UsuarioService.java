package es.ieslavereda.projectspring.service;

import es.ieslavereda.projectspring.repository.UsuarioRepository;
import es.ieslavereda.projectspring.repository.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository repository;

    public List<Usuario> getAllUsuarios() throws SQLException {
        return repository.getAllUsuarios();
    }

    public Usuario addUsuario(Usuario usuario) throws SQLException {
        return repository.addUsuario(usuario);
    }

    public Usuario deleteUsuario(int id) throws SQLException {
        return repository.deleteUsuario(id);
    }

    public Usuario updateUsuario(Usuario usuario) throws SQLException {
        return repository.updateUsuario(usuario);
    }
}
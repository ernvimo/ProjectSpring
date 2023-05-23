package es.ieslavereda.projectspring.service;

import es.ieslavereda.projectspring.repository.OficioRepository;
import es.ieslavereda.projectspring.repository.UsuarioRepository;
import es.ieslavereda.projectspring.repository.model.Oficio;
import es.ieslavereda.projectspring.repository.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class OficioService {
    @Autowired
    public OficioRepository repository;

    public List<Oficio> getAllOficios() throws SQLException {
        return repository.getAllOficios();
    }
}

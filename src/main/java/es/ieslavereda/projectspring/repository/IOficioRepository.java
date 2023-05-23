package es.ieslavereda.projectspring.repository;

import es.ieslavereda.projectspring.repository.model.Oficio;
import es.ieslavereda.projectspring.repository.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface IOficioRepository {
    List<Oficio> getAllOficios() throws SQLException;
}

package es.ieslavereda.projectspring.repository;

import es.ieslavereda.projectspring.repository.model.MyDataSource;
import es.ieslavereda.projectspring.repository.model.Oficio;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OficioRepository implements IOficioRepository {
    @Override
    public List<Oficio> getAllOficios() throws SQLException {
        ArrayList<Oficio> oficios = new ArrayList<>();
        String query = "{ call obtener_oficios(?)}";

        try(Connection connection = MyDataSource.getMySQLDataSource().getConnection();
            CallableStatement cs = connection.prepareCall(query)){
            cs.setNull(1,0);
            ResultSet rs = cs.executeQuery();

            while(rs.next()){
                oficios.add(Oficio.builder().idOficio(rs.getInt(1)).descripcion(rs.getString(2)).imgUrl(rs.getString(4)).build());
            }
        }

        return oficios;
    }

}

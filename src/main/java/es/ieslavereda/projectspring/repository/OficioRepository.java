package es.ieslavereda.projectspring.repository;

import es.ieslavereda.projectspring.repository.model.MyDataSource;
import es.ieslavereda.projectspring.repository.model.Oficio;
import es.ieslavereda.projectspring.repository.model.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OficioRepository implements IOficioRepository {
    @Override
    public List<Oficio> getAllOficios() throws SQLException {
        ArrayList<Oficio> oficios = new ArrayList<>();
        String query = "SELECT * FROM Oficio";

        try(Connection connection = MyDataSource.getMySQLDataSource().getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query)){

            while(rs.next()){
                oficios.add(Oficio.builder().idOficio(rs.getInt(1)).descripcion(rs.getString(2)).imageurl(rs.getString(4)).build());
            }
        }

        return oficios;
    }

}

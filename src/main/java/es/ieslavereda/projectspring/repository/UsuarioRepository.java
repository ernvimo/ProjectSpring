package es.ieslavereda.projectspring.repository;

import es.ieslavereda.projectspring.repository.model.MyDataSource;
import es.ieslavereda.projectspring.repository.model.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository implements IUsuarioRepository {
//    @Override
//    public Usuario addUsuario(Usuario usuario) throws SQLException {
//        String query = "INSERT INTO Usuario VALUES(?,?,?,?)";
//        try (Connection connection = MyDataSource.getMySQLDataSource().getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query)
//        ) {
//            preparedStatement.setInt(1, usuario.getIdUsuario());
//            preparedStatement.setString(2, usuario.getNombre());
//            preparedStatement.setString(3, usuario.getApellidos());
//            preparedStatement.setInt(4, usuario.getOficio_idOficio());
//            preparedStatement.executeUpdate();
//            return usuario;
//        }
//    }

    @Override
    public Usuario addUsuario(Usuario usuario) throws SQLException {
        String sql = "{ CALL crear_usuario(?, ?, ?, ?, ?) }";

        try (Connection con = MyDataSource.getMySQLDataSource().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.registerOutParameter(1,Types.INTEGER);
            cs.setInt(2, usuario.getIdUsuario());
            cs.setString(3, usuario.getNombre());
            cs.setString(4, usuario.getApellidos());
            cs.setInt(5, usuario.getOficio_idOficio());
            cs.execute();
            cs.getInt(1);
            usuario = new Usuario(cs.getInt(1),usuario.getNombre(), usuario.getApellidos(), usuario.getOficio_idOficio());

        }
        return usuario;
    }

//    @Override
//    public Usuario updateUsuario(Usuario usuario) throws SQLException {
//        String query = "UPDATE Usuario set nombre = ?, apellidos = ?, Oficio_idOficio = ? where idUsuario = ?";
//
//        try (Connection connection = MyDataSource.getMySQLDataSource().getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query)
//        ) {
//            preparedStatement.setString(1, usuario.getNombre());
//            preparedStatement.setString(2, usuario.getApellidos());
//            preparedStatement.setInt(3, usuario.getOficio_idOficio());
//            preparedStatement.setInt(4, usuario.getIdUsuario());
//
//            preparedStatement.executeUpdate();
//            return usuario;
//        }
//    }

    @Override
    public Usuario updateUsuario(Usuario usuario) throws SQLException {
        String sql = "{ CALL actualizar_usuario(?, ?, ?, ?, ?) }";

        try (Connection con = MyDataSource.getMySQLDataSource().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, usuario.getIdUsuario());
            cs.setString(2, usuario.getNombre());
            cs.setString(3, usuario.getApellidos());
            cs.setInt(4, usuario.getOficio_idOficio());
            cs.execute();
        }

        return usuario;
    }

//    @Override
//    public boolean deleteUsuario(int id) throws SQLException {
//        boolean deletion = false;
//        String sql = " {? = call eliminar_usuario(?)}";
//
//        try (Connection con = MyDataSource.getMySQLDataSource().getConnection();
//             CallableStatement cs = con.prepareCall(sql)) {
//            cs.setInt(2, id);
//            cs.execute();
//            int borrados = cs.getInt(1);
//            System.out.println(borrados);
//            if (borrados == 1)
//                deletion = true;
//        }
//        return deletion;
//    }

//    @Override
//    public Usuario deleteUsuario(int id) throws SQLException {
//        Usuario usuario = null;
//        String sql = "{ ? = CALL eliminar_usuario(?) }";
//
//        try (Connection con = MyDataSource.getMySQLDataSource().getConnection();
//             CallableStatement cs = con.prepareCall(sql)) {
//            cs.registerOutParameter(1, Types.INTEGER);
//            cs.setInt(2, id);
//            cs.execute();
//            int borrados = cs.getInt(1);
//            if (borrados == 1) {
//                // Obtener el usuario eliminado de la base de datos si la eliminación fue exitosa
//                usuario = obtenerUsuarioPorId(id);
//            }
//        }
//
//        return usuario;
//    }

    @Override
    public Usuario deleteUsuario(int id) throws SQLException {
        Usuario usuario = obtenerUsuarioPorId(id);
        if (usuario != null) {
            String sql = "{ ? = CALL eliminar_usuario(?) }";

            try (Connection con = MyDataSource.getMySQLDataSource().getConnection();
                 CallableStatement cs = con.prepareCall(sql)) {
                cs.registerOutParameter(1, Types.INTEGER);
                cs.setInt(2, id);
                cs.execute();
                int borrados = cs.getInt(1);
                if (borrados != 1) {
                    usuario = null;
                }
            }
        }

        return usuario;
    }

//    public List<Usuario> getAllUsuarios() throws SQLException {
//        ArrayList<Usuario> usuariosDB = new ArrayList<>();
//        String query = "SELECT * FROM Usuario";
//
//        try(Connection connection = MyDataSource.getMySQLDataSource().getConnection();
//            Statement st = connection.createStatement();
//            ResultSet rs = st.executeQuery(query)){
//
//            while(rs.next()){
//                usuariosDB.add(Usuario.builder().idUsuario(rs.getInt(1)).nombre(rs.getString(2)).apellidos(rs.getString(3)).Oficio_idOficio(rs.getInt(4)).build());
//            }
//        }
//
//        return usuariosDB;
//    }

    @Override
    public List<Usuario> getAllUsuarios() throws SQLException {
        Usuario usuario;
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "{ CALL obtener_usuarios() }";

        try (Connection con = MyDataSource.getMySQLDataSource().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                usuario = Usuario.builder().idUsuario(rs.getInt(1)).nombre(rs.getString(2)).apellidos(rs.getString(3)).Oficio_idOficio(rs.getInt(4)).build();
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    private Usuario obtenerUsuarioPorId(int id) throws SQLException {
        Usuario usuario;
        String sql = "SELECT * FROM Usuario WHERE idUsuario = ?";
        try (Connection con = MyDataSource.getMySQLDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = Usuario.builder().idUsuario(rs.getInt(1)).nombre(rs.getString(2)).apellidos(rs.getString(3)).Oficio_idOficio(rs.getInt(4)).build();
                    return usuario;
                }
            }
        }
        return null;
    }
}
package es.ieslavereda.projectspring.repository;

import es.ieslavereda.projectspring.repository.model.MyDataSource;
import es.ieslavereda.projectspring.repository.model.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository implements IUsuarioRepository {
    @Override
    public Usuario addUsuario(Usuario usuario) throws SQLException {
        Usuario user;
        String sql = "{ CALL crear_usuario(?, ?, ?, ?, ?) }";

        try (Connection con = MyDataSource.getMySQLDataSource().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.registerOutParameter(1,Types.INTEGER);
            cs.setNull(2, Types.INTEGER);
            cs.setString(3, usuario.getNombre());
            cs.setString(4, usuario.getApellidos());
            cs.setInt(5, usuario.getIdOficio());
            cs.executeUpdate();

            usuario.setIdUsuario(cs.getInt(1));
            user = Usuario.builder().idUsuario(cs.getInt(1)).nombre(usuario.getNombre()).
                    apellidos(usuario.getApellidos()).idOficio(usuario.getIdOficio()).build();

        }
        return user;
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) throws SQLException {
        Usuario user;
        String sql = "{ ? = CALL actualizar_usuario(?, ?, ?, ?) }";

        try (Connection con = MyDataSource.getMySQLDataSource().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, usuario.getIdUsuario());
            cs.setString(3, usuario.getNombre());
            cs.setString(4, usuario.getApellidos());
            cs.setInt(5, usuario.getIdOficio());
            cs.execute();

            user = new Usuario(usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellidos(), usuario.getIdOficio());
        }

        return user;
    }

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

    @Override
    public List<Usuario> getAllUsuarios() throws SQLException {
        Usuario usuario;
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "{ CALL obtener_usuarios() }";

        try (Connection con = MyDataSource.getMySQLDataSource().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                usuario = Usuario.builder().idUsuario(rs.getInt(1)).nombre(rs.getString(2)).apellidos(rs.getString(3)).idOficio(rs.getInt(4)).build();
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    private Usuario obtenerUsuarioPorId(int id) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM Usuario WHERE idUsuario = ?";
        try (Connection con = MyDataSource.getMySQLDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = Usuario.builder().idUsuario(rs.getInt(1)).nombre(rs.getString(2)).apellidos(rs.getString(3)).idOficio(rs.getInt(4)).build();
                    return usuario;
                }
            }
        }
        return null;
    }
}
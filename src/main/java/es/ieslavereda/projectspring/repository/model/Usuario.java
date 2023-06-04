package es.ieslavereda.projectspring.repository.model;

import io.micrometer.common.lang.Nullable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Usuario implements Serializable {
    private int idUsuario;
    private String nombre;
    private String apellidos;
    private int idOficio;

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Usuario){
            Usuario u = (Usuario) obj;
            return idUsuario==u.idUsuario;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario);
    }
}
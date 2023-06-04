package es.ieslavereda.projectspring.repository.model;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Oficio implements Serializable {

    private int idOficio;
    private String descripcion;
    private String imgUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Oficio oficio = (Oficio) o;
        return idOficio == oficio.idOficio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOficio);
    }
}
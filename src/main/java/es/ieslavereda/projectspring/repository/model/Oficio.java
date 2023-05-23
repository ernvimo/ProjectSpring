package es.ieslavereda.projectspring.repository.model;

import lombok.*;

import java.sql.Blob;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class Oficio {

    private int idOficio;
    private String descripcion;
//    private Blob image;
    private String imageurl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Oficio oficio = (Oficio) o;
        return Objects.equals(descripcion, oficio.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcion);
    }
}
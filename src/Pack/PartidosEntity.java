package Pack;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Joakin on 17/02/2017.
 */
@Entity
@Table(name = "partidos", schema = "liga", catalog = "")
public class PartidosEntity {
    private int idPartido;
    private String resultado;
    private Date fecha;
    private String arbitro;
    private EquiposEntity equiposByElocal;
    private EquiposEntity equiposByEvisitante;

    @Id
    @Column(name = "id_partido", nullable = false)
    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    @Basic
    @Column(name = "resultado", nullable = true, length = 45)
    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Basic
    @Column(name = "fecha", nullable = true)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Basic
    @Column(name = "arbitro", nullable = true, length = 45)
    public String getArbitro() {
        return arbitro;
    }

    public void setArbitro(String arbitro) {
        this.arbitro = arbitro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartidosEntity that = (PartidosEntity) o;

        if (idPartido != that.idPartido) return false;
        if (resultado != null ? !resultado.equals(that.resultado) : that.resultado != null) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        if (arbitro != null ? !arbitro.equals(that.arbitro) : that.arbitro != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPartido;
        result = 31 * result + (resultado != null ? resultado.hashCode() : 0);
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (arbitro != null ? arbitro.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "elocal", referencedColumnName = "id_equipo", nullable = false)
    public EquiposEntity getEquiposByElocal() {
        return equiposByElocal;
    }

    public void setEquiposByElocal(EquiposEntity equiposByElocal) {
        this.equiposByElocal = equiposByElocal;
    }

    @ManyToOne
    @JoinColumn(name = "evisitante", referencedColumnName = "id_equipo", nullable = false)
    public EquiposEntity getEquiposByEvisitante() {
        return equiposByEvisitante;
    }

    public void setEquiposByEvisitante(EquiposEntity equiposByEvisitante) {
        this.equiposByEvisitante = equiposByEvisitante;
    }
}

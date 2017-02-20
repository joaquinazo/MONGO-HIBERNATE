package Pack;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Joakin on 17/02/2017.
 */
@Entity
@Table(name = "jugadores", schema = "liga")
public class JugadoresEntity {
    private int idJugador;
    private String nombre;
    private String apellido;
    private String puesto;
    private Integer idCapitan;
    private Timestamp fechaAlta;
    private Integer salario;
    private double altura;
    private EquiposEntity equiposByEquipo;

    @Id
    @Column(name = "id_jugador", nullable = false)
    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    @Basic
    @Column(name = "nombre", nullable = true, length = 45)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "apellido", nullable = true, length = 45)
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Basic
    @Column(name = "puesto", nullable = false, length = 45)
    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    @Basic
    @Column(name = "id_capitan", nullable = true)
    public Integer getIdCapitan() {
        return idCapitan;
    }

    public void setIdCapitan(Integer idCapitan) {
        this.idCapitan = idCapitan;
    }

    @Basic
    @Column(name = "fecha_alta", nullable = true)
    public Timestamp getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Timestamp fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Basic
    @Column(name = "salario", nullable = true)
    public Integer getSalario() {
        return salario;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }

    @Basic
    @Column(name = "altura", nullable = true, precision = 2)
    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JugadoresEntity that = (JugadoresEntity) o;

        if (idJugador != that.idJugador) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (apellido != null ? !apellido.equals(that.apellido) : that.apellido != null) return false;
        if (puesto != null ? !puesto.equals(that.puesto) : that.puesto != null) return false;
        if (idCapitan != null ? !idCapitan.equals(that.idCapitan) : that.idCapitan != null) return false;
        if (fechaAlta != null ? !fechaAlta.equals(that.fechaAlta) : that.fechaAlta != null) return false;
        if (salario != null ? !salario.equals(that.salario) : that.salario != null) return false;
        //if (altura != null ? !altura.equals(that.altura) : that.altura != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idJugador;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (apellido != null ? apellido.hashCode() : 0);
        result = 31 * result + (puesto != null ? puesto.hashCode() : 0);
        result = 31 * result + (idCapitan != null ? idCapitan.hashCode() : 0);
        result = 31 * result + (fechaAlta != null ? fechaAlta.hashCode() : 0);
        result = 31 * result + (salario != null ? salario.hashCode() : 0);
        //result = 31 * result + (altura != null ? altura.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "equipo", referencedColumnName = "id_equipo")
    public EquiposEntity getEquiposByEquipo() {
        return equiposByEquipo;
    }

    public void setEquiposByEquipo(EquiposEntity equiposByEquipo) {
        this.equiposByEquipo = equiposByEquipo;
    }
}

package com.elenajif.ligafutbol.base;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "jugadores", schema = "liga_futbol")
public class Jugador {
    private int id;
    private String nombre;
    private String apellidos;
    private int dorsal;
    private Date fechaNacimiento;
    private Equipo equipo;
    private Set<Partido> partidos;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "apellidos")
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Basic
    @Column(name = "dorsal")
    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    @Basic
    @Column(name = "fecha_nacimiento")
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Jugador jugador = (Jugador) o;

        if (id != jugador.id) return false;
        if (dorsal != jugador.dorsal) return false;
        if (nombre != null ? !nombre.equals(jugador.nombre) : jugador.nombre != null) return false;
        if (apellidos != null ? !apellidos.equals(jugador.apellidos) : jugador.apellidos != null) return false;
        if (fechaNacimiento != null ? !fechaNacimiento.equals(jugador.fechaNacimiento) : jugador.fechaNacimiento != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (apellidos != null ? apellidos.hashCode() : 0);
        result = 31 * result + (int) dorsal;
        result = 31 * result + (fechaNacimiento != null ? fechaNacimiento.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_equipo", referencedColumnName = "id")
    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @ManyToMany(mappedBy = "jugadores")
    public Set<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(Set<Partido> partidos) {
        this.partidos = partidos;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos + " - " + dorsal;
    }
}

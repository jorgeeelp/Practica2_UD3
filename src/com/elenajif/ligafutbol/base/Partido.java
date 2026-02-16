package com.elenajif.ligafutbol.base;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "partidos", schema = "liga_futbol")
public class Partido {
    private int id;
    private Timestamp fecha;
    private String arbitro;
    private String campo;
    private String incidencias;
    private Set<Equipo> equipos;
    private Set<Jugador> jugadores;

    public Partido(){
        equipos = new HashSet<Equipo>();
        jugadores = new HashSet<Jugador>();
    }

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
    @Column(name = "fecha")
    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    @Basic
    @Column(name = "arbitro")
    public String getArbitro() {
        return arbitro;
    }

    public void setArbitro(String arbitro) {
        this.arbitro = arbitro;
    }

    @Basic
    @Column(name = "campo")
    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    @Basic
    @Column(name = "incidencias")
    public String getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(String incidencias) {
        this.incidencias = incidencias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Partido partido = (Partido) o;

        if (id != partido.id) return false;
        if (fecha != null ? !fecha.equals(partido.fecha) : partido.fecha != null) return false;
        if (arbitro != null ? !arbitro.equals(partido.arbitro) : partido.arbitro != null) return false;
        if (campo != null ? !campo.equals(partido.campo) : partido.campo != null) return false;
        if (incidencias != null ? !incidencias.equals(partido.incidencias) : partido.incidencias != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (arbitro != null ? arbitro.hashCode() : 0);
        result = 31 * result + (campo != null ? campo.hashCode() : 0);
        result = 31 * result + (incidencias != null ? incidencias.hashCode() : 0);
        return result;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "equipo_partido", schema = "liga_futbol", joinColumns = @JoinColumn(name = "id_partido", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_equipo", referencedColumnName = "id", nullable = false))
    public Set<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(Set<Equipo> equipos) {
        this.equipos = equipos;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "jugador_partido", schema = "liga_futbol", joinColumns = @JoinColumn(name = "id_partido", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_jugador", referencedColumnName = "id", nullable = false))
    public Set<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(Set<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    @Override
    public String toString() {
        String equiposCadena = "";
        if(equipos.size() == 2) {
            for (Equipo equipo : equipos) {
                equiposCadena = equiposCadena + equipo.toString() + " - ";
            }
        }
        return id + ": " + equiposCadena +  fecha +
                " Arbitro: " + arbitro;
    }
}

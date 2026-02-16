package com.elenajif.ligafutbol.base;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "equipos", schema = "liga_futbol")
public class Equipo {
    private int id;
    private String nombre;
    private String patrocinador;
    private double presupuesto;
    private String categoria;
    private List<Jugador> jugadores;
    private Set<Partido> partidos;

    public Equipo(){
        jugadores = new ArrayList<Jugador>();
        partidos = new HashSet<Partido>();
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
    @Column(name = "nombre", unique = true)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "patrocinador")
    public String getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(String patrocinador) {
        this.patrocinador = patrocinador;
    }

    @Basic
    @Column(name = "presupuesto")
    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    @Basic
    @Column(name = "categoria")
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Equipo equipo = (Equipo) o;

        if (id != equipo.id) return false;
        if (Double.compare(equipo.presupuesto, presupuesto) != 0) return false;
        if (nombre != null ? !nombre.equals(equipo.nombre) : equipo.nombre != null) return false;
        if (patrocinador != null ? !patrocinador.equals(equipo.patrocinador) : equipo.patrocinador != null)
            return false;
        if (categoria != null ? !categoria.equals(equipo.categoria) : equipo.categoria != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (patrocinador != null ? patrocinador.hashCode() : 0);
        temp = Double.doubleToLongBits(presupuesto);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (categoria != null ? categoria.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "equipo")
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    @ManyToMany(mappedBy = "equipos")
    public Set<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(Set<Partido> partidos) {
        this.partidos = partidos;
    }

    @Override
    public String toString() {
        return nombre;
    }

}

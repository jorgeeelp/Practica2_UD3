package com.elenajif.ligafutbol.gui;

import com.elenajif.ligafutbol.base.Equipo;
import com.elenajif.ligafutbol.base.Partido;
import com.elenajif.ligafutbol.util.HibernateUtil;
import com.elenajif.ligafutbol.base.Jugador;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 *  Clase con todas las operaciones de Hibernate
 */

public class Modelo {
    public void conectar(){
        HibernateUtil.buildSessionFactory();
    }

    public void desconectar(){
        HibernateUtil.closeSessionFactory();
    }

    public List<Jugador> getJugadores(){
        return (List<Jugador>) HibernateUtil.getCurrentSession().createQuery("FROM Jugador").getResultList();
    }

    public void guardarJugador(Jugador jugador){
        Session sesion = HibernateUtil.getCurrentSession();
        sesion.beginTransaction();
        sesion.saveOrUpdate(jugador);
        sesion.getTransaction().commit();
    }

    public Jugador getJugador(int idJugador){
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Jugador WHERE id = :id");
        query.setParameter("id", idJugador);
        return (Jugador) query.getSingleResult();
    }

    public void borrarJugador(Jugador jugador){
        Session sesion = HibernateUtil.getCurrentSession();
        sesion.beginTransaction();
        sesion.delete(jugador);
        sesion.getTransaction().commit();
    }


    public List<Equipo> getEquipos() {
        return (List<Equipo>) HibernateUtil.getCurrentSession().createQuery("FROM Equipo").getResultList();
    }

    public void guardarEquipo(Equipo nuevoEquipo) {
        Session sesion = HibernateUtil.getCurrentSession();
        sesion.beginTransaction();
        sesion.saveOrUpdate(nuevoEquipo);
        sesion.getTransaction().commit();
    }

    public void borrarEquipo(Equipo equipoBorrado) {
        //En caso de que tenga borrado en cascada de equipos, y no quiera perder
        //a los jugadores, elimino el equipo de sus jugadores antes de borrarlo
        for (Jugador jugador : equipoBorrado.getJugadores()){
            jugador.setEquipo(null);
        }

        Session sesion = HibernateUtil.getCurrentSession();
        sesion.beginTransaction();
        sesion.delete(equipoBorrado);
        sesion.getTransaction().commit();
    }

    public void registrarJugadoresEnEquipo(Equipo equipoSeleccionado) {
        Session sesion = HibernateUtil.getCurrentSession();
        sesion.beginTransaction();
        //A cada jugador que tengo en la lista de jugadores, le asigno el propio equipo
        for(Jugador jugador : equipoSeleccionado.getJugadores()){
            jugador.setEquipo(equipoSeleccionado);
        }
        sesion.getTransaction().commit();
    }

    public void eliminarEquipoDeJugadores(List<Jugador> jugadoresSeleccionados) {
        Session sesion = HibernateUtil.getCurrentSession();
        sesion.beginTransaction();
        for(Jugador jugador : jugadoresSeleccionados){
            jugador.setEquipo(null);
        }
        sesion.getTransaction().commit();
    }

    public List<Partido> getPartidos() {
        return (List<Partido>) HibernateUtil.getCurrentSession().createQuery("FROM Partido").getResultList();
    }

    public void borrarPartido(Partido partidoBorrado) {
        Session sesion = HibernateUtil.getCurrentSession();
        sesion.beginTransaction();
        //Elimino el partido que no tiene jugadores ni equipos
        sesion.delete(partidoBorrado);
        sesion.getTransaction().commit();
    }

    public void guardarPartido(Partido partido) {
        Session sesion = HibernateUtil.getCurrentSession();
        sesion.beginTransaction();
        //Guardo el partido y se guardan automaticamente sus listas
        sesion.saveOrUpdate(partido);
        sesion.getTransaction().commit();
    }

}

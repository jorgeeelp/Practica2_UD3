package com.elenajif.ligafutbol.util;

import com.elenajif.ligafutbol.base.Jugador;

import javax.swing.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Profesor on 30/01/2018.
 */
public class Util {
    /*
        Mostrar mensaje de error con el mensaje indicado
     */
    public static void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje,"Error", JOptionPane.ERROR_MESSAGE);
    }

    /*
        Obtengo un objeto Jugador y creo una fila de la tabla de jugadores con sus datos
     */
    public static Object[] jugadorToRow(Jugador jugador){
        Object[] fila =  new Object[]{jugador.getId(), jugador.getNombre(), jugador.getApellidos(),
                jugador.getDorsal(), jugador.getFechaNacimiento(), jugador.getEquipo()};
        return fila;
    }

    /*
        Obtengo un String con el formato monetaroio de un double
        Añade el caracter de la divisa
     */
    public static String doubleToEuros(double cantidad){
        NumberFormat formateador = NumberFormat.getCurrencyInstance();
        return formateador.format(cantidad);
    }

    /*
        Obtengo un String con la cantidad numerica y devuelvo un double
     */
    public static double eurosToDouble(String cantidad) throws ParseException {
        try{
            return Double.parseDouble(cantidad);
        }catch(NumberFormatException e){
            NumberFormat formateador = NumberFormat.getCurrencyInstance();
            if(formateador.parse(cantidad)instanceof Long) {
                return formateador.parse(cantidad).longValue();
            }
            return formateador.parse(cantidad).doubleValue();
        }
    }

    /*
        Metodo que me devuelve una lista de jugadores con los jugadores que no estan en un modelList
     */
    public static List<Jugador> jugadoresNoContenidos(List<Jugador> list, DefaultListModel<Jugador> listModel){
        ArrayList listaNoContenidos = new ArrayList();
        for(Object object : list){
            if(!listModel.contains(object)) {
                listaNoContenidos.add(object);
            }
        }
        return listaNoContenidos;
    }
}
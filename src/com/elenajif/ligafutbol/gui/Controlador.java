package com.elenajif.ligafutbol.gui;

import com.elenajif.ligafutbol.base.Equipo;
import com.elenajif.ligafutbol.base.Jugador;
import com.elenajif.ligafutbol.base.Partido;
import com.elenajif.ligafutbol.util.HibernateUtil;
import com.elenajif.ligafutbol.util.Util;
import org.hibernate.HibernateException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

public class Controlador implements ActionListener, ChangeListener, ListSelectionListener, FocusListener, MouseListener, ItemListener{
    private VistaPrincipal vista;
    private Modelo modelo;

    public Controlador(VistaPrincipal vista, Modelo modelo){
        this.vista = vista;
        this.modelo = modelo;

        inicializar();
    }

    /*
        Carga los listeners, bloquea algunos botones de la seccion jugadores
        y lista los jugadores de la seccion jugadores
     */
    private void inicializar(){
        bloquearDesbloquearBotones(true);
        addActionListeners(this);
        addChangeListeners(this);
        addMouseListeners(this);
        addListSelectionListeners(this);
        addItemListeners(this);
        addFocusListeners(this);
        listarJugadores();
    }

    //Añado listener para seleccionar filas en la tabla seccion jugadores
    private void addMouseListeners(MouseListener controlador) {
        vista.tablaJugadores.addMouseListener(controlador);
        vista.listaPartidos.addMouseListener(controlador);
    }

    //Añado listener para que al pulsar sobre cada pestaña se carguen los datos referentes
    private void addChangeListeners(ChangeListener controlador) {
        vista.panelPestanas.addChangeListener(controlador);
    }

    //Añado los listener de todos los botones
    private void addActionListeners(ActionListener controlador) {
        vista.seleccionarEquipoBtn.addActionListener(controlador);
        vista.nuevoJugadorBtn.addActionListener(controlador);
        vista.modificarJugadorBtn.addActionListener(controlador);
        vista.borrarJugadorBtn.addActionListener(controlador);
        vista.nuevoEquipoBtn.addActionListener(controlador);
        vista.modificarEquipoBtn.addActionListener(controlador);
        vista.borrarEquipoBtn.addActionListener(controlador);
        vista.verPartidosButton.addActionListener(controlador);
        vista.ficharJugadoresBtn.addActionListener(controlador);
        vista.rescindirJugadoresBtn.addActionListener(controlador);
        vista.nuevoPartidoBtn.addActionListener(controlador);
        vista.modificarPartidoBtn.addActionListener(controlador);
        vista.borrarPartidoBtn.addActionListener(controlador);
        vista.addJugadoresLocalesBtn.addActionListener(controlador);
        vista.addJugadoresVisitanteBtn.addActionListener(controlador);
        vista.deleteJugadoresLocalesBtn.addActionListener(controlador);
        vista.deleteJugadoresVisitanteBtn.addActionListener(controlador);
        vista.mostrarGraficoBtn.addActionListener(controlador);
    }

    //Añado los listener de todas las listas sobre las que puedo seleccionar elementos
    private void addListSelectionListeners(ListSelectionListener controlador){
        vista.listaJugadoresEquipo.addListSelectionListener(controlador);
        vista.listaEquipos.addListSelectionListener(controlador);
        vista.listaJugadoresVisitantes.addListSelectionListener(controlador);
        vista.listaJugadoresLocales.addListSelectionListener(controlador);
        vista.listaPartidos.addListSelectionListener(controlador);
        vista.listaJugadoresLocales.addListSelectionListener(controlador);
        vista.listaJugadoresVisitantes.addListSelectionListener(controlador);
    }

    //Añado los listeners de las selecciones en los comboboxes de la seccion partido
    private void addItemListeners(ItemListener controlador){
        vista.cbEquipoLocal.addItemListener(controlador);
        vista.cbEquipoVisitante.addItemListener(controlador);
    }

    //Añado los listeners de de foco en los comboboxes de la seccion partido
    private void addFocusListeners(FocusListener controlador){
       vista.cbEquipoLocal.addFocusListener(controlador);
       vista.cbEquipoVisitante.addFocusListener(controlador);
    }


    /**
     *
     *
     * Metodos listeners de accion, seleccion, clic de raton, y foco
     *
     *
     */

     /*
        Listener de los eventos de boton
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        //Botones de la tab Jugadores
        if(vista.panelPestanas.getSelectedComponent() == vista.panelJugadores) {
            switch (actionCommand) {
                case "SeleccionarEquipo":

                    DialogoSeleccionEquipo.mostrarDialogo();
                    if(DialogoSeleccionEquipo.getEquipoSeleccionado() == DialogoSeleccionEquipo.SIN_EQUIPO){
                        vista.txtEquipoJugador.setText("");
                    }else{
                        vista.txtEquipoJugador.setText(DialogoSeleccionEquipo.getEquipoSeleccionado().toString());
                    }
                    break;
                case "NuevoJugador":
                    nuevoJugador();
                    break;
                case "BorrarJugador":
                    borrarJugador();
                    break;
                case "ModificarJugador":
                    modificarJugador();
                    break;
            }
            //Botones seccion Equipos
        }else if(vista.panelPestanas.getSelectedComponent() == vista.panelEquipos) {
            switch (actionCommand){
                case "NuevoEquipo":
                    nuevoEquipo();
                    break;
                case "ModificarEquipo":
                    modificarEquipo();
                    break;
                case "BorrarEquipo":
                    borrarEquipo();
                    break;
                case "VerPartidos":
                    break;
                case "FicharJugadores":
                    ficharJugadoresEquipo();
                    break;
                case "RescindirContratos":
                    rescindirJugadores();
                    break;
                case "MostrarGrafico":
                    mostrarGraficoJugadoresPorEquipo();
            }

            //Botones seccion Partidos
        }else if(vista.panelPestanas.getSelectedComponent() == vista.panelPartidos){
            switch (actionCommand){
                case "NuevoPartido":
                    nuevoPartido();
                    break;
                case "ModificarPartido":
                    modificarPartido();
                    break;
                case "BorrarPartido":
                    borrarPartido();
                    break;
                case "addConvocadosLocales":
                    addConvocadosLocales();
                    break;
                case "addConvocadosVisitantes":
                    addConvocadosVisitantes();
                    break;
                case "deleteJugadoresLocales":
                    desconvocarJugadoresLocales();
                    break;
                case "deleteJugadoresVisitantes":
                    desconvocarJugadoresVisitantes();
                    break;
            }
        }
    }

     /*
        Si pincho con el ratón sobre alguna fila de la tabla cargo los datos en los campos
        Si vuelvo pincho tambien sobre la lista de partidos, aunque esté seleccionada, la vuelvo a cargar
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        //Si hago clic sobre la tabla de Jugadores cuando está activada
        if(e.getSource() == vista.tablaJugadores && vista.tablaJugadores.isEnabled()){
            int filaSeleccionada = vista.tablaJugadores.getSelectedRow();

            String nombre = (String)vista.dtmJugadores.getValueAt(filaSeleccionada,1);
            String apellidos = (String)vista.dtmJugadores.getValueAt(filaSeleccionada,2);
            int dorsal = (int)vista.dtmJugadores.getValueAt(filaSeleccionada,3);
            Date fecha = (Date)vista.dtmJugadores.getValueAt(filaSeleccionada,4);
            Equipo equipo = (Equipo) vista.dtmJugadores.getValueAt(filaSeleccionada,5);

            //Muestro los datos del jugador en los campos
            mostrarDatosJugadorSeleccionado(nombre,apellidos,dorsal,fecha,equipo);
        }else if(e.getSource() == vista.listaPartidos && !vista.listaPartidos.isSelectionEmpty()){
            Partido partidoSeleccionado = vista.listaPartidos.getSelectedValue();
            cargarDatosPartidoSeleccionado(partidoSeleccionado);
        }
    }

    /*
        Se ejecuta cada vez que selecciono algo en un comboBox
        Lo que hace es listar los jugadores de esos equipos
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == vista.cbEquipoLocal && e.getStateChange() == ItemEvent.SELECTED) {
            listarConvocadosLocales();
        }else if(e.getSource() == vista.cbEquipoVisitante && e.getStateChange() == ItemEvent.SELECTED){
            listarConvocadosVisitantes();
        }
    }

    /*
        Se ejecuta cuando un combobox gana el foco (seleccionamos un combo)
        Carga los equipos en el comboBox que recibe el foco
     */
    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource() == vista.cbEquipoLocal) {
            //Cuando pincho en el combo, no despliego el menu
            vista.cbEquipoLocal.hidePopup();
            listarEquiposLocalesCBox();
            //Despues de cargar los datos, si despliego el menu
            vista.cbEquipoLocal.showPopup();
        }else if(e.getSource() == vista.cbEquipoVisitante){
            vista.cbEquipoVisitante.hidePopup();
            listarEquiposVisitantesCBox();
            vista.cbEquipoVisitante.showPopup();
        }
    }

    /*
        Si selecciono alguna otra pestaña cargo sus datos
        La pestaña Jugadores se carga por defecto al inicializar
     */
    @Override
    public void stateChanged(ChangeEvent e) {

        int panelSeleccionado = vista.panelPestanas.getSelectedIndex();
        switch (panelSeleccionado){
            case 0:
                limpiarCamposJugador();
                listarJugadores();
                break;
            case 1:
                limpiarCamposEquipo();
                listarEquipos();
                break;
            case 2:
                limpiarCamposPartido();
                listarPartidos();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()) {
            if (e.getSource() == vista.listaEquipos && !vista.listaEquipos.isSelectionEmpty()) {
                Equipo equipoSeleccionado = vista.listaEquipos.getSelectedValue();
                listarJugadoresEquipo(equipoSeleccionado);
                mostrarDatosEquipoSeleccionado(equipoSeleccionado);
            } else if (e.getSource() == vista.listaPartidos && !vista.listaPartidos.isSelectionEmpty()) {
                Partido partidoSeleccionado = vista.listaPartidos.getSelectedValue();
                cargarDatosPartidoSeleccionado(partidoSeleccionado);
            }
        }
    }

    /**
     *
     *
     *  Metodos de la seccion PARTIDOS
     *
     *
     */

    /*
        Cargos los partidos en la lista de la seccion partidos
     */
    private void listarPartidos() {
        List<Partido> listaPartidos = modelo.getPartidos();
        vista.dlmPartidos.clear();
        for(Partido partido: listaPartidos) {
            vista.dlmPartidos.addElement(partido);
        }
    }

    /*
        Relleno el comboBox de equipo local
     */
    private void listarEquiposLocalesCBox(){
        List<Equipo> listaEquipos = modelo.getEquipos();
        vista.dcbmEquipoLocal.removeAllElements();
        for(Equipo equipo : listaEquipos){
                vista.dcbmEquipoLocal.addElement(equipo);
        }
    }

    /*
        Relleno el comboBox de equipo visitante
    */
    private void listarEquiposVisitantesCBox(){
        List<Equipo> listaEquipos = modelo.getEquipos();
        vista.dcbmEquipoVisitante.removeAllElements();
        for(Equipo equipo : listaEquipos){
            vista.dcbmEquipoVisitante.addElement(equipo);
        }
    }

    /*
        Vacio los comboBoxes, los campos de texto y las listas
    */
    private void limpiarCamposPartido(){
        vista.dateTimePickerPartido.clear();
        vista.txtArbitroPartido.setText("");
        vista.txtCampoPartido.setText("");
        vista.txtAIndidencias.setText("");
        vista.dcbmEquipoVisitante.removeAllElements();
        vista.dcbmEquipoLocal.removeAllElements();
        vista.dlmJugadoresVisitantes.clear();
        vista.dlmJugadoresLocales.clear();
    }

    /*
        Carga los datos del partido en los campos de texto
        y carga los equipos del partido en los combos
     */
    private void cargarDatosPartidoSeleccionado(Partido partidoSeleccionado) {
        mostrarDatosPartidoSeleccionado(partidoSeleccionado);
        //Cargo los equipos en los combos si aun existen los dos
        if(partidoSeleccionado.getEquipos().size() == 2) {
            Iterator<Equipo> it = partidoSeleccionado.getEquipos().iterator();
            vista.dcbmEquipoLocal.setSelectedItem(it.next());
            vista.dcbmEquipoVisitante.setSelectedItem(it.next());
            //Cargo los jugadores de ambos equipos
            listarConvocadosLocales(partidoSeleccionado);
            listarConvocadosVisitantes(partidoSeleccionado);
        }
    }


    /*
        Carga los datos del partido en los campos de texto
     */
    private void mostrarDatosPartidoSeleccionado(Partido partido) {
        vista.dateTimePickerPartido.setDateTimePermissive(partido.getFecha().toLocalDateTime());
        vista.txtArbitroPartido.setText(partido.getArbitro());
        vista.txtCampoPartido.setText(partido.getCampo());
        vista.txtAIndidencias.setText(partido.getIncidencias());
    }

    /*
        Lista los jugadores del equipo seleccionado en el combobox local
     */
    private void listarConvocadosLocales(){
        List<Jugador> listaJugadores = ((Equipo)vista.dcbmEquipoLocal.getSelectedItem()).getJugadores();
        vista.dlmJugadoresLocales.removeAllElements();
        for(Jugador jugador : listaJugadores){

            vista.dlmJugadoresLocales.addElement(jugador);
        }
    }

    /*
     Lista los jugadores del equipo seleccionado en el combobox visitante
    */
    private void listarConvocadosVisitantes(){
        List<Jugador> listaJugadores = ((Equipo)vista.dcbmEquipoVisitante.getSelectedItem()).getJugadores();
        vista.dlmJugadoresVisitantes.removeAllElements();
        for(Jugador jugador : listaJugadores){
            vista.dlmJugadoresVisitantes.addElement(jugador);
        }
    }

    /*
        Mismos métodos que los anteriores pero muestran solo los jugadores que estaban en el partido
     */
    private void listarConvocadosVisitantes(Partido partidoSeleccionado) {
        List<Jugador> listaJugadores = ((Equipo)vista.dcbmEquipoVisitante.getSelectedItem()).getJugadores();
        vista.dlmJugadoresVisitantes.removeAllElements();
        for(Jugador jugador : listaJugadores){
            if(partidoSeleccionado.getJugadores().contains(jugador)) {
                vista.dlmJugadoresVisitantes.addElement(jugador);
            }
        }
    }

    /*
        Mismos métodos que los anteriores pero muestran solo los jugadores que estaban en el partido
    */
    private void listarConvocadosLocales(Partido partidoSeleccionado) {
        List<Jugador> listaJugadores = ((Equipo)vista.dcbmEquipoLocal.getSelectedItem()).getJugadores();
        vista.dlmJugadoresLocales.removeAllElements();
        for(Jugador jugador : listaJugadores){
            if(partidoSeleccionado.getJugadores().contains(jugador)) {
                vista.dlmJugadoresLocales.addElement(jugador);
            }
        }
    }

    /*
        Desconvoco los jugadores locales convocadod para partido
     */
    private void desconvocarJugadoresLocales() {
        if(!vista.listaJugadoresLocales.isSelectionEmpty()) {
            int inicio = vista.listaJugadoresLocales.getMinSelectionIndex();
            int fin = vista.listaJugadoresLocales.getMaxSelectionIndex();
            vista.dlmJugadoresLocales.removeRange(inicio, fin);
        }
    }

    /*
        Descomvoco los jugadores visitantes convocados para partido
    */
    private void desconvocarJugadoresVisitantes() {
        if(!vista.listaJugadoresVisitantes.isSelectionEmpty()) {
            int inicio = vista.listaJugadoresVisitantes.getMinSelectionIndex();
            int fin = vista.listaJugadoresVisitantes.getMaxSelectionIndex();
            vista.dlmJugadoresVisitantes.removeRange(inicio, fin);
        }
    }

    /*
        Añado a jugadores del equipo visitante que no estan convocados
        Los obtengo de un cuadro de dialogo y los añado a la lista de visitantes convocados
     */
    private void addConvocadosVisitantes() {
        System.out.println("entra");
        DialogoConvocarJugadores dialogo = new DialogoConvocarJugadores();
        Equipo equipoSeleccionado = (Equipo) vista.cbEquipoVisitante.getSelectedItem();
        //Si el equipo está seleccionado
        if(equipoSeleccionado != null) {
            //Obtengo los jugadores que no tiene convocados
            dialogo.mostrarJugadores(Util.jugadoresNoContenidos(
                    equipoSeleccionado.getJugadores(),vista.dlmJugadoresVisitantes));
            if (dialogo.getRespuesta() == DialogoConvocarJugadores.ACEPTAR) {
                for (Jugador jugadorConvocado : dialogo.getJugadoresConvocados()) {
                    vista.dlmJugadoresVisitantes.addElement(jugadorConvocado);
                }
            }
        }
    }

    /*
        Añado a jugadores del equipo local que no estan en la lista convocados
        Los obtengo de un cuadro de dialogo y los añado a la lista de locales convocados
     */
    private void addConvocadosLocales() {
        DialogoConvocarJugadores dialogo = new DialogoConvocarJugadores();
        Equipo equipoSeleccionado = (Equipo) vista.cbEquipoLocal.getSelectedItem();
        //Si el equipo está seleccionado
        if(equipoSeleccionado != null) {
            //Obtengo los jugadores que no tiene convocados
            dialogo.mostrarJugadores(Util.jugadoresNoContenidos(
                    equipoSeleccionado.getJugadores(),vista.dlmJugadoresLocales));
            if (dialogo.getRespuesta() == DialogoConvocarJugadores.ACEPTAR) {
                for (Jugador jugadorConvocado : dialogo.getJugadoresConvocados()) {
                    vista.dlmJugadoresLocales.addElement(jugadorConvocado);
                }
            }
        }
    }

    /*
        Obtengo el partido seleccionado de la lista y lo borro de la lista y de la bbdd
     */
    private void borrarPartido() {
        if(vista.listaPartidos.isSelectionEmpty()){
            Util.mensajeError("Debes seleccinar un partido para borrrar");
        }else{
            Partido partidoBorrado = vista.listaPartidos.getSelectedValue();
            //Elimino ya los equipos y los jugadores del partido
            //Para que la transaccion no los borre el cacada.
            partidoBorrado.getEquipos().clear();
            partidoBorrado.getJugadores().clear();
            //Lo elimino de la lista y lo borro en la base de datos
            vista.dlmPartidos.removeElement(partidoBorrado);
            modelo.borrarPartido(partidoBorrado);
            limpiarCamposPartido();
        }
    }

    /*
        Asigna a un partido los 4 campos de los cuadros de texto
     */
    private void setCamposTexto(Partido partido) {
        partido.setArbitro(vista.txtArbitroPartido.getText());
        partido.setFecha(Timestamp.valueOf(vista.dateTimePickerPartido.getDateTimePermissive()));
        partido.setCampo(vista.txtCampoPartido.getText());
        partido.setIncidencias(vista.txtAIndidencias.getText());
    }

    /*
        Creo un nuevo partido con todos los datos
        y lo añado a la lista y a la base de datos
     */
    private void nuevoPartido() {
        try {
            Partido partido = new Partido();
            asignarDatosANuevoPartido(partido);
            modelo.guardarPartido(partido);
            vista.dlmPartidos.addElement(partido);
            vista.listaPartidos.setSelectedValue(partido, true);
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
            Util.mensajeError("Se deben seleccionar dos equipos, y dintintos");
        } catch (HibernateException e1) {
            System.out.println(e1.getMessage());
            Util.mensajeError("Error al guardar el partido");
            listarPartidos();
        } catch (Exception e2) {
            System.out.println("Causa Error: " + e2.getMessage());
            Util.mensajeError("Error al al obtener los datos para el partido");
        }
    }

    /*
        Asigno todos los datos al partido
        campos de texto, equipos y jugadores
     */
    private void asignarDatosANuevoPartido(Partido partido) {
        setCamposTexto(partido);
        if (vista.dcbmEquipoLocal.getSelectedItem() == vista.dcbmEquipoVisitante.getSelectedItem()) {
            throw new UnsupportedOperationException();
        }
        //Añado los dos equipos
        partido.getEquipos().add((Equipo) vista.dcbmEquipoLocal.getSelectedItem());
        partido.getEquipos().add((Equipo) vista.cbEquipoVisitante.getSelectedItem());
        //Selecciono todos los jugadores de las 2 listas de jugadores
        vista.listaJugadoresLocales.setSelectionInterval(0, vista.dlmJugadoresLocales.getSize() - 1);
        vista.listaJugadoresVisitantes.setSelectionInterval(0, vista.dlmJugadoresVisitantes.getSize() - 1);
        //Añador los intervalos seleccionados por completo a la lista de jugadores del partido
        partido.getJugadores().addAll(vista.listaJugadoresLocales.getSelectedValuesList());
        partido.getJugadores().addAll(vista.listaJugadoresVisitantes.getSelectedValuesList());
        //Limpio las selecciones
        vista.listaJugadoresVisitantes.clearSelection();
        vista.listaJugadoresLocales.clearSelection();
    }

    private void modificarPartido() {
        if(vista.listaPartidos.isSelectionEmpty()){
            Util.mensajeError("Debes seleccionar un partido");
            return;
        }
        Partido partido = vista.listaPartidos.getSelectedValue();
        try {
            asignarDatosAPartidoModificado(partido);
            modelo.guardarPartido(partido);
        } catch (Exception e) {
            //Si ha ocurrido algún error guardando los datos del partido o guardando el partido
            System.out.println("Causa Error: " + e.getMessage());
            Util.mensajeError("Error al guardar el partido");
            //Vuelvo a mostrar y lo selecciono
            listarPartidos();
            vista.listaPartidos.setSelectedValue(partido,true);
        }
    }

    /*
        Obtengo los nuevos datos y los añador
     */
    private void asignarDatosAPartidoModificado(Partido partido) {
        setCamposTexto(partido);
        if (vista.dcbmEquipoLocal.getSelectedItem() == vista.dcbmEquipoVisitante.getSelectedItem()) {
            throw new UnsupportedOperationException();
        }
        //Elimino los equipos anteriores y añado los nuevos
        partido.getEquipos().clear();
        partido.getEquipos().add((Equipo) vista.dcbmEquipoLocal.getSelectedItem());
        partido.getEquipos().add((Equipo) vista.cbEquipoVisitante.getSelectedItem());
        //Selecciono todos los jugadores de las 2 listas de jugadores
        //Y elimino las listas anteriores
        vista.listaJugadoresLocales.setSelectionInterval(0, vista.dlmJugadoresLocales.getSize() - 1);
        vista.listaJugadoresVisitantes.setSelectionInterval(0, vista.dlmJugadoresVisitantes.getSize() - 1);
        //Añador los intervalos seleccionados por completo a la lista de jugadores del partido
        partido.getJugadores().clear();
        partido.getJugadores().addAll(vista.listaJugadoresLocales.getSelectedValuesList());
        partido.getJugadores().addAll(vista.listaJugadoresVisitantes.getSelectedValuesList());
        //Limpio las selecciones
        vista.listaJugadoresVisitantes.clearSelection();
        vista.listaJugadoresLocales.clearSelection();
    }

    /**
     *
     *
     * Metodos de la seccion EQUIPOS
     *
     *
     *
     */

    /*
        Cargo los equipos en la lista de la seccion equipos
     */
    private void listarEquipos() {
        List<Equipo> listaEquipos = modelo.getEquipos();
        vista.dlmEquipos.clear();
        for(Equipo equipo : listaEquipos) {
            vista.dlmEquipos.addElement(equipo);
        }
    }

    /*
        Listo los jugadores de un equipo seleccionado
     */
    private void listarJugadoresEquipo(Equipo equipoSeleccionado) {
        if(!vista.listaEquipos.isSelectionEmpty()) {
            List<Jugador> listaJugadores = equipoSeleccionado.getJugadores();
            vista.dlmJugadoresEquipo.clear();
            for (Jugador jugador : listaJugadores) {
                vista.dlmJugadoresEquipo.addElement(jugador);
            }
        }
    }
    /*
        Abro un dialogo para seleccionar jugadores que no estan en mi equipo
        Los añado al equipo seleccionado y lo guardo en la base de datos
     */
    private void ficharJugadoresEquipo() {
        if(vista.listaEquipos.isSelectionEmpty()){
            Util.mensajeError("Debe seleccionar un equipo para modificarlo");
        }else {
            Equipo equipoSeleccionado = vista.listaEquipos.getSelectedValue();
            DialogoMoverJugadores dialogo = new DialogoMoverJugadores(equipoSeleccionado,DialogoMoverJugadores.FICHAR);
            dialogo.mostrarDialogo();
            if(dialogo.getRespuesta() == DialogoMoverJugadores.ACEPTAR){
                //Añado directamente la lista de jugadores fichados a la lista de jugadores del equipo
                equipoSeleccionado.getJugadores().addAll(dialogo.getJugadoresSeleccionados());
                modelo.registrarJugadoresEnEquipo(equipoSeleccionado);
                listarJugadoresEquipo(equipoSeleccionado);
            }
        }
    }

    /*
        Selecciono todos los jugadores que no quiera en mi equipo y libero
     */
    private void rescindirJugadores() {
        if(vista.listaEquipos.isSelectionEmpty()){
            Util.mensajeError("Debe seleccionar un equipo para modificarlo");
        }else {
            Equipo equipoSeleccionado = vista.listaEquipos.getSelectedValue();
            DialogoMoverJugadores dialogo =
                    new DialogoMoverJugadores(equipoSeleccionado, DialogoMoverJugadores.RESCINDIR);
            dialogo.mostrarDialogo();
            if(dialogo.getRespuesta() == DialogoMoverJugadores.ACEPTAR){
                modelo.eliminarEquipoDeJugadores(dialogo.getJugadoresSeleccionados());
                listarJugadoresEquipo(equipoSeleccionado);
            }
        }
    }

    /*
        Borro el equipo seleccionado de la lista y de hibernate
     */
    private void borrarEquipo() {
        if(vista.listaEquipos.isSelectionEmpty()){
            Util.mensajeError("Debe seleccionar un equipo para modificarlo");
        }else {
            Equipo equipoBorrado = vista.listaEquipos.getSelectedValue();
            vista.dlmEquipos.removeElement(equipoBorrado);
            modelo.borrarEquipo(equipoBorrado);
            limpiarCamposEquipo();
        }
    }

    /*
        Obtengo los datos de los campos de texto para crear un equipo
        Lo guardo en la base de datos y lo añado a la lista
     */
    private void nuevoEquipo(){
        try {
            Equipo nuevoEquipo = new Equipo();
            asignarDatosAEquipo(nuevoEquipo);
            modelo.guardarEquipo(nuevoEquipo);
            //Anado equipo a la lista y lo selecciono
            vista.dlmEquipos.addElement(nuevoEquipo);
            vista.listaEquipos.setSelectedValue(nuevoEquipo,true);
        }catch (Exception e){
           gestionarExcepcionGuardarEquipo(e);
        }
    }

    /*
        Modifico el equipo seleccionado con los datos de los campos
     */
    private void modificarEquipo() {
        if(vista.listaEquipos.isSelectionEmpty()){
            Util.mensajeError("Debe seleccionar un equipo para modificarlo");
        }else{
            Equipo equipoModificado = vista.listaEquipos.getSelectedValue();
            try {
                asignarDatosAEquipo(equipoModificado);
                modelo.guardarEquipo(equipoModificado);
                //Ademas en lugar de recargar la lista, reemplzo el elemento viejo
                int posicionEnLista = vista.dlmEquipos.indexOf(equipoModificado);
                vista.dlmEquipos.set(posicionEnLista, equipoModificado);
            }catch(Exception e){
                gestionarExcepcionGuardarEquipo(e);
                vista.listaEquipos.setSelectedValue(equipoModificado,true);
            }
        }
    }

    /*
        Si recibo una HibernateException al guardar un equipo debo cerrar la sesion
        La vuelvo a abrir listando de nuevo a todos los equipos.
        Debo hacer esto ya que los equipos tienen una lista de jugadores que quiere tener cargada
     */
    private void gestionarExcepcionGuardarEquipo(Exception e) {
        Util.mensajeError("Error al crear un guardar datos de equipo");
        System.out.println("Causa del error: " + e.getMessage());
        if(e instanceof HibernateException){
            //En caso de excepcion de Hibernate vuelvo a listar todos los equipos
            HibernateUtil.getCurrentSession().close();
            listarEquipos();
        }
    }
    /*
        Coge los datos de los campos de texto y se los añade al equipo
        Lanza Excepcion si no puede parsear el presupuesto, con el €
     */
    private void asignarDatosAEquipo(Equipo nuevo) throws ParseException {
        nuevo.setNombre(vista.txtNombreEquipo.getText());
        nuevo.setPatrocinador(vista.txtPatrocinadoEquipo.getText());
        nuevo.setCategoria(vista.txtCategoriaEquipo.getText());
        if(vista.txtPresupuestoEquipo.getText().isEmpty()) {
            vista.txtPresupuestoEquipo.setText(Util.doubleToEuros(0));
        }
        nuevo.setPresupuesto(Util.eurosToDouble(vista.txtPresupuestoEquipo.getText()));
    }

    /*
        Muestro los datos de un equipo en los campos de texto
     */
    private void mostrarDatosEquipoSeleccionado(Equipo equipoSeleccionado) {
        vista.txtNombreEquipo.setText(equipoSeleccionado.getNombre());
        vista.txtPatrocinadoEquipo.setText(equipoSeleccionado.getPatrocinador());
        vista.txtPresupuestoEquipo.setText(Util.doubleToEuros(equipoSeleccionado.getPresupuesto()));
        vista.txtCategoriaEquipo.setText(equipoSeleccionado.getCategoria());
    }

    private void limpiarCamposEquipo(){
        vista.txtNombreEquipo.setText("");
        vista.txtPatrocinadoEquipo.setText("");
        vista.txtPresupuestoEquipo.setText("");
        vista.txtCategoriaEquipo.setText("");
        vista.dlmJugadoresEquipo.clear();
    }

    private void mostrarGraficoJugadoresPorEquipo(){
        DefaultPieDataset dataset = new DefaultPieDataset();
        for(Equipo equipo : modelo.getEquipos()){
            if(equipo.getJugadores() != null && equipo.getJugadores().size() != 0) {
                dataset.setValue(equipo.getNombre(), equipo.getJugadores().size());
            }

        }
        JFreeChart chart = ChartFactory.createPieChart("Jugadores por equipo", dataset, true, true, true);
        ChartPanel panel = new ChartPanel(chart);
        JDialog dialog = new JDialog();
        dialog.setContentPane(panel);
        dialog.setTitle("Grafico");
        dialog.setModal(true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }

    /**
     *
     *
     * Metodos de la seccion JUGADORES
     *
     *
     */

    /*
        Obtengo el id del jugador seleccionado en la tabla
        Obtengo el objeto de ese jugador de la base de datos
        Borro a dicho jugador
     */
    private void borrarJugador() {
        int filaSeleccionada = vista.tablaJugadores.getSelectedRow();
        if(filaSeleccionada != -1) {
            int idJugador = (int) vista.dtmJugadores.getValueAt(filaSeleccionada, 0);
            modelo.borrarJugador(modelo.getJugador(idJugador));
            vista.dtmJugadores.removeRow(filaSeleccionada);
            limpiarCamposJugador();
        }else{
            Util.mensajeError("Debe seleccionar algún jugador de la tabla");
        }
    }

    /*
        Gestiones oportunas para modificar los datos de un jugador seleccionado
        Debido al manejo de la tabla se deben hacer mas operaciones que con un JList
     */
    private void modificarJugador(){
        int filaSeleccionada = vista.tablaJugadores.getSelectedRow();
        //Si he seleccionado a un jugador de la tabla
        if(filaSeleccionada != -1) {
            boolean estadoBoton = vista.modificarJugadorBtn.isSelected();
            //Si presiono el boton modificar modifico el estado de los botones
            if(estadoBoton) {
                modoModificarJugador(true);
            }else{
                //Si he despresionado el boton Guardar jugador
                //Obtengo el objeto del jugador para cambiar sus datos
                int idJugador = (int) vista.dtmJugadores.getValueAt(filaSeleccionada, 0);
                Jugador jugadorModificado = modelo.getJugador(idJugador);
                try{
                    setDatosJugador(jugadorModificado);
                    //Añado el jugador a la base de datos
                    modelo.guardarJugador(jugadorModificado);
                    //Borro la fila antigua de la tabla
                    vista.dtmJugadores.removeRow(filaSeleccionada);
                    //Inserto una nueva fila en la tabla y la dejo seleccionada
                    vista.dtmJugadores.insertRow(filaSeleccionada, Util.jugadorToRow(jugadorModificado));
                    vista.tablaJugadores.setRowSelectionInterval(filaSeleccionada - 1, filaSeleccionada);
                    //Restablezco el estado de los botones
                    modoModificarJugador(false);
                }catch(Exception e){
                    //Mientras que haya excepciones en la insercion mantengo el estado del boton como pulsado
                    vista.modificarJugadorBtn.setSelected(true);
                    System.out.println("Causa del error: " + e.getMessage());
                    Util.mensajeError("Debe rellenar los datos de forma correcta para dar de alta un jugador");
                }
            }
        }else{
            vista.modificarJugadorBtn.setSelected(false);
            Util.mensajeError("Debe seleccionar algún jugador de la tabla");
        }
    }

    /*
        Cambio la interfaz dependiendo del estado del boton modificar jugador
     */
    private void modoModificarJugador(boolean estado){
        //Si el boton esta presionado, desactivo otros botones y cambio su nombre
        bloquearDesbloquearBotones(!estado);
        vista.nuevoJugadorBtn.setEnabled(!estado);
        vista.modificarJugadorBtn.setText(estado ?  "Guardar" :  "Modificar");
    }

    /*
        Hacemos las gestiones oportunas para dar de alta un jugador
        Debido al manejo de la tabla se deben hacer mas operaciones que con un JList
     */
    private void nuevoJugador() {
        boolean estadoBoton = vista.nuevoJugadorBtn.isSelected();
        //Si presiono boton NuevoJugador cambio el estado de los botones
        if(estadoBoton){
            modoNuevoJugador(true);
        }else{
            //Cuando despresiono el boton Guardar Nuevo Jugador
            //Mientras que no introduzca los datos requiridos y en formato correcto, capturo excepciones
            try {
                Jugador nuevoJugador = new Jugador();
                setDatosJugador(nuevoJugador);
                //Añado el jugador a la base de datos
                modelo.guardarJugador(nuevoJugador);
                //Añado esa fila a la tabla en lugar de recargar todos los datos con hibernate y la selecciono
                vista.dtmJugadores.addRow(Util.jugadorToRow(nuevoJugador));
                vista.tablaJugadores.setRowSelectionInterval(vista.tablaJugadores.getRowCount() - 2,
                        vista.tablaJugadores.getRowCount() - 1);
                //Restablezco el estado de los botones
                modoNuevoJugador(false);
            }catch (Exception e){
                //Mientras que haya excepciones en la insercion mantengo el estado del boton como presionado
                vista.nuevoJugadorBtn.setSelected(true);
                System.out.println("Causa del error: " + e.getMessage());
                Util.mensajeError("Debe rellenar los datos de forma correcta para dar de alta un jugador");
            }
        }
    }

    /*
        Bloqueo y limpio campos si quiero crear un nuevo jugador
     */
    private void modoNuevoJugador(boolean estado){
        //Si el botones esta presionado limpio y desactivo botones. Y viceversa
        if(estado){
            limpiarCamposJugador();
            vista.tablaJugadores.clearSelection();
        }

        bloquearDesbloquearBotones(!estado);
        vista.modificarJugadorBtn.setEnabled(!estado);
        vista.nuevoJugadorBtn.setText(estado ? "Guardar" : "Nuevo");
    }

    /*
    Cojo los datos de los campos de texto y se los asigno a un jugador
    */
    private void setDatosJugador(Jugador jugador) {
        jugador.setNombre(vista.txtNombreJugador.getText());
        jugador.setApellidos(vista.txtApellidosJugador.getText());
        jugador.setDorsal(Integer.parseInt(vista.txtDorsalJugador.getText()));
        jugador.setFechaNacimiento(Date.valueOf(vista.datePickerJugador.getDate()));
        //Si he seleccionado un equipo, o ninguno, tambien lo cojo
        jugador.setEquipo(DialogoSeleccionEquipo.getEquipoSeleccionado());
        //Despues de asignarselo al jugador, reseteo el dialogo
        DialogoSeleccionEquipo.resetEquipoSeleccionado();
    }

    /*
        Deshabilito algunos botones y habilito campos de texto
     */
    private void bloquearDesbloquearBotones(boolean estado){
        vista.txtNombreJugador.requestFocus(!estado);
        vista.txtApellidosJugador.setEditable(!estado);
        vista.txtNombreJugador.setEditable(!estado);
        vista.txtDorsalJugador.setEditable(!estado);
        vista.datePickerJugador.setEnabled(!estado);
        vista.seleccionarEquipoBtn.setEnabled(!estado);
        vista.tablaJugadores.setEnabled(estado);
        vista.borrarJugadorBtn.setEnabled(estado);
        vista.panelPestanas.setEnabled(estado);
    }

    /*
        Vacio los campos de texto con Nuevo Jugador
     */
    private void limpiarCamposJugador() {
        vista.txtNombreJugador.setText("");
        vista.txtApellidosJugador.setText("");
        vista.txtDorsalJugador.setText("");
        vista.datePickerJugador.clear();
        vista.txtEquipoJugador.setText("");
    }

    /*
        Listo los jugadores de la base de datos en una tabla
     */
    private void listarJugadores(){
        List<Jugador> listaJugadores = modelo.getJugadores();

        vista.dtmJugadores.setRowCount(0);
        for (Jugador jugador : listaJugadores){
            vista.dtmJugadores.addRow(new Object[]{jugador.getId(), jugador.getNombre(),jugador.getApellidos(),
                    jugador.getDorsal(), jugador.getFechaNacimiento(),jugador.getEquipo()});
        }
    }

    /*
        Muestro los datos del jugador en los campos de texto
     */
    private void mostrarDatosJugadorSeleccionado(String nombre, String apellidos, int dorsal, Date fechaNacimiento, Equipo equipo) {
        vista.txtNombreJugador.setText(nombre);
        vista.txtApellidosJugador.setText(apellidos);
        vista.txtDorsalJugador.setText(String.valueOf(dorsal));
        vista.datePickerJugador.setDate(fechaNacimiento.toLocalDate());
        if (equipo != null){
            vista.txtEquipoJugador.setText(equipo.getNombre());
        }else {
            vista.txtEquipoJugador.setText("");
        }
    }


    /**
     *  Metodos de la interface MouseListener y FocusListener que no necesito
     */

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void focusLost(FocusEvent e) {}
}

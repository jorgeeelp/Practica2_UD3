package com.elenajif.ligafutbol.gui;

import com.elenajif.ligafutbol.base.Equipo;
import com.elenajif.ligafutbol.base.Jugador;
import com.elenajif.ligafutbol.base.Partido;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by Profesor on 25/01/2018.
 */
public class VistaPrincipal {
    private JPanel panel1;
    JTabbedPane panelPestanas;
    JTextField txtDorsalJugador;
    JTextField txtApellidosJugador;
    JTextField txtNombreJugador;
    JTextField txtEquipoJugador;
    JTextField txtNombreEquipo;
    JTextField txtPatrocinadoEquipo;
    JTextField txtPresupuestoEquipo;
    JTextField txtCategoriaEquipo;
    JTextField txtArbitroPartido;
    JTextField txtCampoPartido;
    JTable tablaJugadores;
    JButton seleccionarEquipoBtn;
    JToggleButton nuevoJugadorBtn;
    JToggleButton modificarJugadorBtn;
    JButton borrarJugadorBtn;
    JList<Equipo> listaEquipos;
    JButton nuevoEquipoBtn;
    JButton modificarEquipoBtn;
    JButton borrarEquipoBtn;
    JButton verPartidosButton;
    JComboBox<Equipo> cbEquipoLocal;
    JComboBox<Equipo> cbEquipoVisitante;
    JList<Jugador> listaJugadoresLocales;
    JList<Jugador> listaJugadoresVisitantes;
    JPanel panelEquipos;
    DatePicker datePickerJugador;
    JPanel panelPartidos;
    JPanel panelJugadores;
    JList<Jugador> listaJugadoresEquipo;
    JButton ficharJugadoresBtn;
    JButton rescindirJugadoresBtn;
    JTextArea txtAIndidencias;
    JList<Partido> listaPartidos;
    JButton modificarPartidoBtn;
    JButton nuevoPartidoBtn;
    JButton borrarPartidoBtn;
    JButton deleteJugadoresLocalesBtn;
    JButton addJugadoresLocalesBtn;
    JButton deleteJugadoresVisitanteBtn;
    JButton addJugadoresVisitanteBtn;
    DateTimePicker dateTimePickerPartido;
    JButton mostrarGraficoBtn;
    DefaultTableModel dtmJugadores;
    DefaultListModel<Equipo> dlmEquipos;
    DefaultListModel<Jugador> dlmJugadoresLocales;
    DefaultListModel<Jugador> dlmJugadoresVisitantes;
    DefaultComboBoxModel<Equipo> dcbmEquipoLocal;
    DefaultComboBoxModel<Equipo> dcbmEquipoVisitante;
    DefaultListModel<Partido> dlmPartidos;
    DefaultListModel<Jugador> dlmJugadoresEquipo;

    public VistaPrincipal() {
        JFrame frame = new JFrame("VistaPrincipal");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        inicilizarTablaSeccionJugadores();
        inicilizarListasEquipos();
        inicializarCombosSeccionPartido();
        inicializarListasSeccionPartido();
    }

    private void inicilizarTablaSeccionJugadores(){
        //No permito que la tabla sea editable
         dtmJugadores = new DefaultTableModel(){
              @Override
              public boolean isCellEditable(int row, int column) {
                   return false;
              }
         };
        dtmJugadores.setColumnIdentifiers(new String[] {"ID", "Nombre", "Apellidos", "Dorsal", "Fecha Nacimiento", "Equipo"});
        tablaJugadores.setModel(dtmJugadores);
        //Permito seleccionar solo una linea
        tablaJugadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void inicilizarListasEquipos(){
        dlmEquipos = new DefaultListModel<>();
        listaEquipos.setModel(dlmEquipos);
        dlmJugadoresEquipo = new DefaultListModel<>();
        listaJugadoresEquipo.setModel(dlmJugadoresEquipo);
    }

    private void inicializarListasSeccionPartido(){
        dlmJugadoresLocales = new DefaultListModel<>();
        listaJugadoresLocales.setModel(dlmJugadoresLocales);
        dlmJugadoresVisitantes = new DefaultListModel<>();
        listaJugadoresVisitantes.setModel(dlmJugadoresVisitantes);
        dlmPartidos = new DefaultListModel<>();
        listaPartidos.setModel(dlmPartidos);
    }

    private void inicializarCombosSeccionPartido(){
        dcbmEquipoLocal = new DefaultComboBoxModel<>();
        cbEquipoLocal.setModel(dcbmEquipoLocal);
        dcbmEquipoVisitante =  new DefaultComboBoxModel<>();
        cbEquipoVisitante.setModel(dcbmEquipoVisitante);
    }

}

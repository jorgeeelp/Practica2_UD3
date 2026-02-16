package com.elenajif.ligafutbol.gui;

import com.elenajif.ligafutbol.base.Equipo;
import com.elenajif.ligafutbol.util.HibernateUtil;
import com.elenajif.ligafutbol.util.Util;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;


/**
 *  Clase para mostrar un cuadro de dialogo estático.
 *  Si selecciono un equipo queda almacenado
 *  Si lo quiero limpiar uso el método clear()
 */
public class DialogoSeleccionEquipo extends JDialog {
    public static final  Equipo SIN_EQUIPO = null;
    private static Equipo equipoSeleccionado;
    private static DialogoSeleccionEquipo dialogo;

    private JList<Equipo> list1;
    private DefaultListModel<Equipo> dlm;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public static Equipo getEquipoSeleccionado() {
        return equipoSeleccionado;
    }

    public static void resetEquipoSeleccionado(){
        equipoSeleccionado = SIN_EQUIPO;
    }

    private DialogoSeleccionEquipo() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private static void mostrarEquipos(){
        dialogo.dlm = new DefaultListModel<Equipo>();
        dialogo.list1.setModel(dialogo.dlm);

        ArrayList<Equipo> listaEquipos =
                (ArrayList<Equipo>) HibernateUtil.getCurrentSession().createQuery("FROM Equipo").getResultList();
        for (Equipo equipo : listaEquipos){
            dialogo.dlm.addElement(equipo);
        }
    }

    public static void mostrarDialogo(){
        dialogo = new DialogoSeleccionEquipo();
        mostrarEquipos();

        dialogo.pack();
        dialogo.setLocationRelativeTo(null);
        dialogo.setVisible(true);

    }

    private void onOK() {
        if(!list1.isSelectionEmpty()){
            equipoSeleccionado = (Equipo)list1.getSelectedValue();
            dispose();
        }else{
            Util.mensajeError("No ha seleccionado un equipo");
        }
    }

    private void onCancel() {
        equipoSeleccionado = SIN_EQUIPO;
        dispose();
    }
}

package com.elenajif.ligafutbol.gui;

import com.elenajif.ligafutbol.base.Jugador;
import com.elenajif.ligafutbol.util.Util;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class DialogoConvocarJugadores extends JDialog {
    public static final int ACEPTAR = 1;
    public static final int CANCELAR = 0;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList<Jugador> list1;
    private DefaultListModel<Jugador> dlm;
    private int respuesta;
    private List<Jugador> jugadoresConvocados;

    public int getRespuesta() {
        return respuesta;
    }

    public List<Jugador> getJugadoresConvocados() {
        return jugadoresConvocados;
    }

    public DialogoConvocarJugadores() {
        dlm = new DefaultListModel<Jugador>();
        list1.setModel(dlm);
        setTitle("Convocar Jugadores");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        jugadoresConvocados = list1.getSelectedValuesList();
        respuesta = ACEPTAR;
        dispose();
    }

    private void onCancel() {
        respuesta = CANCELAR;
        dispose();
    }

    public void mostrarJugadores(List<Jugador> listaJugadores){
        if(listaJugadores.isEmpty()){
            Util.mensajeError("Todos tus jugadores están convocados");
        }else {
            dlm.clear();
            for(Jugador jugador : listaJugadores){
                dlm.addElement(jugador);
            }
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }
}

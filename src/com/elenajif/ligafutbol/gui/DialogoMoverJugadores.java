package com.elenajif.ligafutbol.gui;

import com.elenajif.ligafutbol.base.Equipo;
import com.elenajif.ligafutbol.base.Jugador;
import com.elenajif.ligafutbol.util.HibernateUtil;
import com.elenajif.ligafutbol.util.Util;
import org.hibernate.query.Query;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

/**
 * Dialogo para fichar los jugadores que no tiene un equipo
 * O para rescindir los jugadores que si tiene.
 */
public class DialogoMoverJugadores extends JDialog {
    public static final int ACEPTAR = 1;
    public static final int CANCELAR = 0;
    public static final int FICHAR = 1;
    public static final int RESCINDIR = 0;

    private JPanel contentPane;
    private JButton ficharBtn;
    private JButton buttonCancel;
    private JList<Jugador> list1;
    private JLabel lblMensaje;
    private DefaultListModel<Jugador> dlm;
    private Equipo equipo;
    private int respuesta;
    private int modo;
    private List<Jugador> jugadoresSeleccionados;

    public int getRespuesta(){
        return respuesta;
    }

    public List<Jugador> getJugadoresSeleccionados() {
        return jugadoresSeleccionados;
    }

    public DialogoMoverJugadores(Equipo equipo, int modo) {
        this.equipo = equipo;
        this.respuesta = CANCELAR;
        this.modo = modo;
        cambiarTextoLabel();

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(ficharBtn);

        ficharBtn.addActionListener(new ActionListener() {
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
    /*
        Si el dialogo es para fichar o rescindir contratos lo indico mediante una etiqueta
     */
    private void cambiarTextoLabel() {
        if(this.modo == FICHAR){
            setTitle("Fichar Jugadores");
            lblMensaje.setText("Jugadores que puede fichar el equipo: " + equipo);
        }else{
            setTitle("Rescindir Contratos Jugadores");
            lblMensaje.setText("Contrados que puede rescindir el equipo: " + equipo);
            ficharBtn.setText("Rescindir Jugadores");
        }
    }

    private void onOK() {
        if(list1.isSelectionEmpty()){
            Util.mensajeError("No ha seleccionado a ningun jugador");
        }else{
            //Almaceno los jugadores seleccionado en una lista
            jugadoresSeleccionados =  list1.getSelectedValuesList();
            respuesta = ACEPTAR;
            dispose();
        }
    }

    private void onCancel() {
        respuesta = CANCELAR;
        dispose();
    }

    public void mostrarDialogo() {
        mostrarJugadores();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*
        Si el modo es FICHAR: carga los jugadores que no tengo en mi equipo.
        Si el modo es RESCINDIR: carga los jugadores que ya tengo en mi equipo.
     */
    private void mostrarJugadores() {
        dlm = new DefaultListModel<Jugador>();
        list1.setModel(dlm);
        List<Jugador> listaJugadores;
        if(this.modo == FICHAR) {
        /*
            La consulta más sencilla es: "FROM Jugador j WHERE j NOT IN :jugadores", siendo jugadores un parametro
            con la lista de jugadores del equipo equipo equipo.getJugadores()
            Pero da error cuando el equipo no tiene ningún jugador
        */
            Query query = HibernateUtil.getCurrentSession().createQuery(
                    "FROM Jugador j WHERE j.equipo != :equipo OR j.equipo IS NULL");
            query.setParameter("equipo", this.equipo);
            listaJugadores = query.getResultList();
        }else{
            listaJugadores = equipo.getJugadores();
        }
        for (Jugador jugador : listaJugadores){
            dlm.addElement(jugador);
        }
    }
}

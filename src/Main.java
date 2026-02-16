import com.elenajif.ligafutbol.gui.Controlador;
import com.elenajif.ligafutbol.gui.Modelo;
import com.elenajif.ligafutbol.gui.VistaPrincipal;


/**
 * Created by Profesor on 19/01/2018.
 */
public class Main {
    public static void main(final String[] args){
        Modelo modelo = new Modelo();
        modelo.conectar();
        VistaPrincipal vista = new VistaPrincipal();
        Controlador controlador = new Controlador(vista, modelo);
    }
}
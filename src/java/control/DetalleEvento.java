/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import DAO.EventoDAO;
import entidad.Evento;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author AnBoCa
 */
public class DetalleEvento implements Serializable{
    private EventoDAO eventoDAO;

    public DetalleEvento(){
        eventoDAO=new EventoDAO();
    }

    private int validarId(String id){
        Pattern patron=Pattern.compile("^\\d+");
        Matcher matcher=patron.matcher(id);
        if (matcher.find()){
            if ((matcher.end()-matcher.start())==id.length())
                return Integer.parseInt(id);
        }
        return 0;
    }

    public Evento getEvento(String id){
        //validar el id
        if (validarId(id)>0)
            return eventoDAO.findEvento(validarId(id));
        else
            return null;
    }
}

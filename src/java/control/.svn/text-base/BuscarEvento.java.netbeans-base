/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import DAO.EventoDAO;
import entidad.Evento;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author AnBoCa
 */
public class BuscarEvento extends Paginador implements Serializable{

    private EventoDAO eventoDAO;
    private String busqueda;
    private List eventos;

    public BuscarEvento(){
        super();
        eventoDAO=new EventoDAO();
        eventos=null;
    }

    /**
     * @return the busqueda
     */
    public String getBusqueda() {
        return busqueda;
    }

    /**
     * @param busqueda the busqueda to set
     */
    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    @Override
    public void reset(){
        super.reset();
        eventos=null;
    }

    public List<Evento> cargar(){
        if (eventos==null){
            eventos=eventoDAO.search(busqueda,0,super.getNres());
            super.setNreg(eventoDAO.getEventoCountBySearch(busqueda));
        }
        else
            eventos=eventoDAO.search(busqueda,super.getActReg(),super.getNres());
        return eventos;
    }
}

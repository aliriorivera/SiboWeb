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
 * @author UNAL
 */
public class ListarEventos extends Paginador implements Serializable{
    
    private EventoDAO eventoDAO;
    private String categoria;
    private List eventos;
    
    public ListarEventos(){
        super();
        eventoDAO=new EventoDAO();
        eventos=null;
    }

    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public void reset(){
        super.reset();
        eventos=null;
    }

    public List<Evento> cargar(){
        if (eventos==null){
            eventos=eventoDAO.readEventoByCat(categoria,0,super.getNres());
            super.setNreg(eventoDAO.getEventoCountByCat(categoria));
        }
        else
            eventos=eventoDAO.readEventoByCat(categoria,super.getActReg(),super.getNres());
        return eventos;
    }
}

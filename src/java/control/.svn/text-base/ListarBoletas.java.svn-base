/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import DAO.BoletaDAO;
import entidad.Boleta;
import java.util.List;

/**
 *
 * @author UNAL
 */
public class ListarBoletas extends Paginador{
    
    private BoletaDAO boletaDAO;
    private int cliente;
    private List boletas;

    public ListarBoletas(){
        super();
        boletaDAO=new BoletaDAO();
        boletas=null;
    }

    /**
     * @return the cliente
     */
    public int getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    @Override
    public void reset(){
        super.reset();
        boletas=null;
    }

    public List<Boleta> cargar(){
        if (boletas==null){
            boletas=boletaDAO.getBoletaByUser(cliente,0,super.getNres());
            super.setNreg(boletaDAO.getBoletaCountByUser(cliente));
        }
        else
            boletas=boletaDAO.getBoletaByUser(cliente,super.getActReg(),super.getNres());
        return boletas;
    }
}

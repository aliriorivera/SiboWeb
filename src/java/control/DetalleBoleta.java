/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import DAO.BoletaDAO;
import entidad.Boleta;
import java.io.Serializable;

/**
 *
 * @author AnBoCa
 */
public class DetalleBoleta implements Serializable{
    private BoletaDAO boletaDAO;

    public DetalleBoleta(){
        boletaDAO=new BoletaDAO();
    }
    public Boleta getBoletaById(int id){return boletaDAO.findBoleta(id);}
}

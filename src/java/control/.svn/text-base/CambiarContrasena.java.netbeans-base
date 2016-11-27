/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import DAO.ClienteDAO;
import DAO.exceptions.NonexistentEntityException;
import entidad.Cliente;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AnBoCa
 */
public class CambiarContrasena implements Serializable{
    private ClienteDAO clienteDAO;

    public CambiarContrasena(){
        clienteDAO=new ClienteDAO();
    }

    public boolean cambiarContrasena(Cliente cliente, String oldPassword, String newPassword){
        if (cliente.getContrasena().equals(oldPassword)){
            cliente.setContrasena(newPassword);
            System.out.println(cliente.getNombres());
            try {
                clienteDAO.edit(cliente);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CambiarContrasena.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (Exception ex) {
                Logger.getLogger(CambiarContrasena.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            return true;
        }
        return false;
    }
}

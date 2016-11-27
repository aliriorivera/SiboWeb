/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import DAO.CategoriaDAO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author UNAL
 */
public class ListarCategoria implements Serializable{
    
    private CategoriaDAO categoriaDAO;
    private String cat;

    public ListarCategoria(){
        categoriaDAO=new CategoriaDAO();
    }

    public ListarCategoria(String categoria){
        this.cat=categoria;
    }

    /**
     * @return the cat
     */
    public String getCat() {
        return cat;
    }

    /**
     * @param cat the cat to set
     */
    public void setCat(String cat) {
        this.cat = cat;
    }

    public List<String> getSubcatbyCat(){
        return categoriaDAO.readByCat(cat);
    }
}

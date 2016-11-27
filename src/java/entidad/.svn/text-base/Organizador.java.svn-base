/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author UNAL
 */
@Entity
@Table(name = "organizador")
@NamedQueries({@NamedQuery(name = "Organizador.findAll", query = "SELECT o FROM Organizador o"), @NamedQuery(name = "Organizador.findByNit", query = "SELECT o FROM Organizador o WHERE o.nit = :nit"), @NamedQuery(name = "Organizador.findByNombre", query = "SELECT o FROM Organizador o WHERE o.nombre = :nombre"), @NamedQuery(name = "Organizador.findByTelefono", query = "SELECT o FROM Organizador o WHERE o.telefono = :telefono")})
public class Organizador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nit")
    private Integer nit;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkOrganizador")
    private List<Evento> eventoList;

    public Organizador() {
    }

    public Organizador(Integer nit) {
        this.nit = nit;
    }

    public Organizador(Integer nit, String nombre, String telefono) {
        this.nit = nit;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Integer getNit() {
        return nit;
    }

    public void setNit(Integer nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nit != null ? nit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organizador)) {
            return false;
        }
        Organizador other = (Organizador) object;
        if ((this.nit == null && other.nit != null) || (this.nit != null && !this.nit.equals(other.nit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Organizador[nit=" + nit + "]";
    }

}

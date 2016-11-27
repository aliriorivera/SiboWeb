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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "escenario")
@NamedQueries({@NamedQuery(name = "Escenario.findAll", query = "SELECT e FROM Escenario e"), @NamedQuery(name = "Escenario.findById", query = "SELECT e FROM Escenario e WHERE e.id = :id"), @NamedQuery(name = "Escenario.findByNombre", query = "SELECT e FROM Escenario e WHERE e.nombre = :nombre"), @NamedQuery(name = "Escenario.findByCiudad", query = "SELECT e FROM Escenario e WHERE e.ciudad = :ciudad"), @NamedQuery(name = "Escenario.findByDireccion", query = "SELECT e FROM Escenario e WHERE e.direccion = :direccion"), @NamedQuery(name = "Escenario.findByTelefono", query = "SELECT e FROM Escenario e WHERE e.telefono = :telefono"), @NamedQuery(name = "Escenario.findByMapa", query = "SELECT e FROM Escenario e WHERE e.mapa = :mapa")})
public class Escenario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "ciudad")
    private String ciudad;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "mapa")
    private String mapa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkEscenario")
    private List<Evento> eventoList;

    public Escenario() {
    }

    public Escenario(Integer id) {
        this.id = id;
    }

    public Escenario(Integer id, String nombre, String ciudad, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMapa() {
        return mapa;
    }

    public void setMapa(String mapa) {
        this.mapa = mapa;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Escenario)) {
            return false;
        }
        Escenario other = (Escenario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Escenario[id=" + id + "]";
    }

}

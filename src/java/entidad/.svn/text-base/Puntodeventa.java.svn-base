/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "puntodeventa")
@NamedQueries({@NamedQuery(name = "Puntodeventa.findAll", query = "SELECT p FROM Puntodeventa p"), @NamedQuery(name = "Puntodeventa.findById", query = "SELECT p FROM Puntodeventa p WHERE p.id = :id"), @NamedQuery(name = "Puntodeventa.findByNombre", query = "SELECT p FROM Puntodeventa p WHERE p.nombre = :nombre"), @NamedQuery(name = "Puntodeventa.findByCiudad", query = "SELECT p FROM Puntodeventa p WHERE p.ciudad = :ciudad"), @NamedQuery(name = "Puntodeventa.findByDireccion", query = "SELECT p FROM Puntodeventa p WHERE p.direccion = :direccion"), @NamedQuery(name = "Puntodeventa.findByTelefono", query = "SELECT p FROM Puntodeventa p WHERE p.telefono = :telefono"), @NamedQuery(name = "Puntodeventa.findByHorario", query = "SELECT p FROM Puntodeventa p WHERE p.horario = :horario")})
public class Puntodeventa implements Serializable {
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
    @Basic(optional = false)
    @Column(name = "horario")
    private String horario;
    @OneToMany(mappedBy = "fkPuntodeventa")
    private List<Usuario> usuarioList;

    public Puntodeventa() {
    }

    public Puntodeventa(Integer id) {
        this.id = id;
    }

    public Puntodeventa(Integer id, String nombre, String ciudad, String direccion, String telefono, String horario) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horario = horario;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
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
        if (!(object instanceof Puntodeventa)) {
            return false;
        }
        Puntodeventa other = (Puntodeventa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Puntodeventa[id=" + id + "]";
    }

}

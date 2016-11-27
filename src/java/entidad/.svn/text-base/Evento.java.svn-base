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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author UNAL
 */
@Entity
@Table(name = "evento")
@NamedQueries({@NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e"), @NamedQuery(name = "Evento.findById", query = "SELECT e FROM Evento e WHERE e.id = :id"), @NamedQuery(name = "Evento.findByNombre", query = "SELECT e FROM Evento e WHERE e.nombre = :nombre"), @NamedQuery(name = "Evento.findByArtista", query = "SELECT e FROM Evento e WHERE e.artista = :artista"), @NamedQuery(name = "Evento.findByDescripcion", query = "SELECT e FROM Evento e WHERE e.descripcion = :descripcion"), @NamedQuery(name = "Evento.findByEstado", query = "SELECT e FROM Evento e WHERE e.estado = :estado"), @NamedQuery(name = "Evento.findByImagen", query = "SELECT e FROM Evento e WHERE e.imagen = :imagen")})
public class Evento implements Serializable {
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
    @Column(name = "artista")
    private String artista;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Column(name = "imagen")
    private String imagen;
    @JoinColumn(name = "fk_escenario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Escenario fkEscenario;
    @JoinColumn(name = "fk_categoria", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Categoria fkCategoria;
    @JoinColumn(name = "fk_organizador", referencedColumnName = "nit")
    @ManyToOne(optional = false)
    private Organizador fkOrganizador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkEvento")
    private List<Presentacion> presentacionList;

    public Evento() {
    }

    public Evento(Integer id) {
        this.id = id;
    }

    public Evento(Integer id, String nombre, String artista, String descripcion, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.artista = artista;
        this.descripcion = descripcion;
        this.estado = estado;
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

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Escenario getFkEscenario() {
        return fkEscenario;
    }

    public void setFkEscenario(Escenario fkEscenario) {
        this.fkEscenario = fkEscenario;
    }

    public Categoria getFkCategoria() {
        return fkCategoria;
    }

    public void setFkCategoria(Categoria fkCategoria) {
        this.fkCategoria = fkCategoria;
    }

    public Organizador getFkOrganizador() {
        return fkOrganizador;
    }

    public void setFkOrganizador(Organizador fkOrganizador) {
        this.fkOrganizador = fkOrganizador;
    }

    public List<Presentacion> getPresentacionList() {
        return presentacionList;
    }

    public void setPresentacionList(List<Presentacion> presentacionList) {
        this.presentacionList = presentacionList;
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
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Evento[id=" + id + "]";
    }

}

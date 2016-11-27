/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidad;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author UNAL
 */
@Entity
@Table(name = "presentacion")
@NamedQueries({@NamedQuery(name = "Presentacion.findAll", query = "SELECT p FROM Presentacion p"), @NamedQuery(name = "Presentacion.findById", query = "SELECT p FROM Presentacion p WHERE p.id = :id"), @NamedQuery(name = "Presentacion.findByFecha", query = "SELECT p FROM Presentacion p WHERE p.fecha = :fecha"), @NamedQuery(name = "Presentacion.findByHora", query = "SELECT p FROM Presentacion p WHERE p.hora = :hora")})
public class Presentacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkPresentacion")
    private List<Locacion> locacionList;
    @JoinColumn(name = "fk_evento", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Evento fkEvento;

    public Presentacion() {
    }

    public Presentacion(Integer id) {
        this.id = id;
    }

    public Presentacion(Integer id, Date fecha, Date hora) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public List<Locacion> getLocacionList() {
        return locacionList;
    }

    public void setLocacionList(List<Locacion> locacionList) {
        this.locacionList = locacionList;
    }

    public Evento getFkEvento() {
        return fkEvento;
    }

    public void setFkEvento(Evento fkEvento) {
        this.fkEvento = fkEvento;
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
        if (!(object instanceof Presentacion)) {
            return false;
        }
        Presentacion other = (Presentacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Presentacion[id=" + id + "]";
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author UNAL
 */
@Entity
@Table(name = "boleta")
@NamedQueries({@NamedQuery(name = "Boleta.findAll", query = "SELECT b FROM Boleta b"), @NamedQuery(name = "Boleta.findById", query = "SELECT b FROM Boleta b WHERE b.id = :id"), @NamedQuery(name = "Boleta.findByFechaEntrega", query = "SELECT b FROM Boleta b WHERE b.fechaEntrega = :fechaEntrega"), @NamedQuery(name = "Boleta.findByEstado", query = "SELECT b FROM Boleta b WHERE b.estado = :estado"), @NamedQuery(name = "Boleta.findByAbono", query = "SELECT b FROM Boleta b WHERE b.abono = :abono")})
public class Boleta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fechaEntrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Column(name = "abono")
    private Double abono;
    @JoinColumn(name = "fk_locacion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Locacion fkLocacion;
    @JoinColumn(name = "fk_registroventa", referencedColumnName = "id")
    @ManyToOne
    private Registroventa fkRegistroventa;

    public Boleta() {
    }

    public Boleta(Integer id) {
        this.id = id;
    }

    public Boleta(Integer id, Date fechaEntrega, String estado) {
        this.id = id;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getAbono() {
        return abono;
    }

    public void setAbono(Double abono) {
        this.abono = abono;
    }

    public Locacion getFkLocacion() {
        return fkLocacion;
    }

    public void setFkLocacion(Locacion fkLocacion) {
        this.fkLocacion = fkLocacion;
    }

    public Registroventa getFkRegistroventa() {
        return fkRegistroventa;
    }

    public void setFkRegistroventa(Registroventa fkRegistroventa) {
        this.fkRegistroventa = fkRegistroventa;
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
        if (!(object instanceof Boleta)) {
            return false;
        }
        Boleta other = (Boleta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Boleta[id=" + id + "]";
    }

}

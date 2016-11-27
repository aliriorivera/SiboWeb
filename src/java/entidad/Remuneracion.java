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
@Table(name = "remuneracion")
@NamedQueries({@NamedQuery(name = "Remuneracion.findAll", query = "SELECT r FROM Remuneracion r"), @NamedQuery(name = "Remuneracion.findById", query = "SELECT r FROM Remuneracion r WHERE r.id = :id"), @NamedQuery(name = "Remuneracion.findByFecha", query = "SELECT r FROM Remuneracion r WHERE r.fecha = :fecha"), @NamedQuery(name = "Remuneracion.findByDocumento", query = "SELECT r FROM Remuneracion r WHERE r.documento = :documento"), @NamedQuery(name = "Remuneracion.findByEvento", query = "SELECT r FROM Remuneracion r WHERE r.evento = :evento"), @NamedQuery(name = "Remuneracion.findByValor", query = "SELECT r FROM Remuneracion r WHERE r.valor = :valor")})
public class Remuneracion implements Serializable {
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
    @Column(name = "documento")
    private String documento;
    @Basic(optional = false)
    @Column(name = "evento")
    private String evento;
    @Basic(optional = false)
    @Column(name = "valor")
    private double valor;

    public Remuneracion() {
    }

    public Remuneracion(Integer id) {
        this.id = id;
    }

    public Remuneracion(Integer id, Date fecha, String documento, String evento, double valor) {
        this.id = id;
        this.fecha = fecha;
        this.documento = documento;
        this.evento = evento;
        this.valor = valor;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
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
        if (!(object instanceof Remuneracion)) {
            return false;
        }
        Remuneracion other = (Remuneracion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Remuneracion[id=" + id + "]";
    }

}

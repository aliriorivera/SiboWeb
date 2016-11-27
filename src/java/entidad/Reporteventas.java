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
@Table(name = "reporteventas")
@NamedQueries({@NamedQuery(name = "Reporteventas.findAll", query = "SELECT r FROM Reporteventas r"), @NamedQuery(name = "Reporteventas.findById", query = "SELECT r FROM Reporteventas r WHERE r.id = :id"), @NamedQuery(name = "Reporteventas.findByFechaReporte", query = "SELECT r FROM Reporteventas r WHERE r.fechaReporte = :fechaReporte"), @NamedQuery(name = "Reporteventas.findByEvento", query = "SELECT r FROM Reporteventas r WHERE r.evento = :evento"), @NamedQuery(name = "Reporteventas.findByFechaSolicitud", query = "SELECT r FROM Reporteventas r WHERE r.fechaSolicitud = :fechaSolicitud"), @NamedQuery(name = "Reporteventas.findByBoletasVendidas", query = "SELECT r FROM Reporteventas r WHERE r.boletasVendidas = :boletasVendidas"), @NamedQuery(name = "Reporteventas.findByBoletasReservadas", query = "SELECT r FROM Reporteventas r WHERE r.boletasReservadas = :boletasReservadas"), @NamedQuery(name = "Reporteventas.findByTotalVendido", query = "SELECT r FROM Reporteventas r WHERE r.totalVendido = :totalVendido"), @NamedQuery(name = "Reporteventas.findByBoletasnovendidas", query = "SELECT r FROM Reporteventas r WHERE r.boletasnovendidas = :boletasnovendidas"), @NamedQuery(name = "Reporteventas.findByTipodereporte", query = "SELECT r FROM Reporteventas r WHERE r.tipodereporte = :tipodereporte")})
public class Reporteventas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fechaReporte")
    @Temporal(TemporalType.DATE)
    private Date fechaReporte;
    @Column(name = "Evento")
    private String evento;
    @Column(name = "fechaSolicitud")
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud;
    @Basic(optional = false)
    @Column(name = "boletasVendidas")
    private int boletasVendidas;
    @Basic(optional = false)
    @Column(name = "boletasReservadas")
    private int boletasReservadas;
    @Basic(optional = false)
    @Column(name = "totalVendido")
    private double totalVendido;
    @Column(name = "boletasnovendidas")
    private Integer boletasnovendidas;
    @Basic(optional = false)
    @Column(name = "tipodereporte")
    private String tipodereporte;

    public Reporteventas() {
    }

    public Reporteventas(Integer id) {
        this.id = id;
    }

    public Reporteventas(Integer id, Date fechaReporte, int boletasVendidas, int boletasReservadas, double totalVendido, String tipodereporte) {
        this.id = id;
        this.fechaReporte = fechaReporte;
        this.boletasVendidas = boletasVendidas;
        this.boletasReservadas = boletasReservadas;
        this.totalVendido = totalVendido;
        this.tipodereporte = tipodereporte;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(Date fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public int getBoletasVendidas() {
        return boletasVendidas;
    }

    public void setBoletasVendidas(int boletasVendidas) {
        this.boletasVendidas = boletasVendidas;
    }

    public int getBoletasReservadas() {
        return boletasReservadas;
    }

    public void setBoletasReservadas(int boletasReservadas) {
        this.boletasReservadas = boletasReservadas;
    }

    public double getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(double totalVendido) {
        this.totalVendido = totalVendido;
    }

    public Integer getBoletasnovendidas() {
        return boletasnovendidas;
    }

    public void setBoletasnovendidas(Integer boletasnovendidas) {
        this.boletasnovendidas = boletasnovendidas;
    }

    public String getTipodereporte() {
        return tipodereporte;
    }

    public void setTipodereporte(String tipodereporte) {
        this.tipodereporte = tipodereporte;
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
        if (!(object instanceof Reporteventas)) {
            return false;
        }
        Reporteventas other = (Reporteventas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Reporteventas[id=" + id + "]";
    }

}

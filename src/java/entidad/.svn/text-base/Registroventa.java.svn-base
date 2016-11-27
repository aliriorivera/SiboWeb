/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author UNAL
 */
@Entity
@Table(name = "registroventa")
@NamedQueries({@NamedQuery(name = "Registroventa.findAll", query = "SELECT r FROM Registroventa r"), @NamedQuery(name = "Registroventa.findById", query = "SELECT r FROM Registroventa r WHERE r.id = :id"), @NamedQuery(name = "Registroventa.findByFecha", query = "SELECT r FROM Registroventa r WHERE r.fecha = :fecha"), @NamedQuery(name = "Registroventa.findByEvento", query = "SELECT r FROM Registroventa r WHERE r.evento = :evento"), @NamedQuery(name = "Registroventa.findByCantidadBoletas", query = "SELECT r FROM Registroventa r WHERE r.cantidadBoletas = :cantidadBoletas"), @NamedQuery(name = "Registroventa.findByDocumentoCliente", query = "SELECT r FROM Registroventa r WHERE r.documentoCliente = :documentoCliente"), @NamedQuery(name = "Registroventa.findByTotalVendido", query = "SELECT r FROM Registroventa r WHERE r.totalVendido = :totalVendido")})
public class Registroventa implements Serializable {
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
    @Column(name = "evento")
    private String evento;
    @Basic(optional = false)
    @Column(name = "cantidad_boletas")
    private String cantidadBoletas;
    @Column(name = "documento_cliente")
    private String documentoCliente;
    @Basic(optional = false)
    @Column(name = "totalVendido")
    private double totalVendido;
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario fkUsuario;
    @JoinColumn(name = "fk_cliente", referencedColumnName = "id")
    @ManyToOne
    private Cliente fkCliente;
    @OneToMany(mappedBy = "fkRegistroventa")
    private List<Boleta> boletaList;

    public Registroventa() {
    }

    public Registroventa(Integer id) {
        this.id = id;
    }

    public Registroventa(Integer id, Date fecha, String evento, String cantidadBoletas, double totalVendido) {
        this.id = id;
        this.fecha = fecha;
        this.evento = evento;
        this.cantidadBoletas = cantidadBoletas;
        this.totalVendido = totalVendido;
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

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getCantidadBoletas() {
        return cantidadBoletas;
    }

    public void setCantidadBoletas(String cantidadBoletas) {
        this.cantidadBoletas = cantidadBoletas;
    }

    public String getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(String documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public double getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(double totalVendido) {
        this.totalVendido = totalVendido;
    }

    public Usuario getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Usuario fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public Cliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Cliente fkCliente) {
        this.fkCliente = fkCliente;
    }

    public List<Boleta> getBoletaList() {
        return boletaList;
    }

    public void setBoletaList(List<Boleta> boletaList) {
        this.boletaList = boletaList;
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
        if (!(object instanceof Registroventa)) {
            return false;
        }
        Registroventa other = (Registroventa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Registroventa[id=" + id + "]";
    }

}

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
@Table(name = "locacion")
@NamedQueries({@NamedQuery(name = "Locacion.findAll", query = "SELECT l FROM Locacion l"), @NamedQuery(name = "Locacion.findById", query = "SELECT l FROM Locacion l WHERE l.id = :id"), @NamedQuery(name = "Locacion.findByNombre", query = "SELECT l FROM Locacion l WHERE l.nombre = :nombre"), @NamedQuery(name = "Locacion.findByCupo", query = "SELECT l FROM Locacion l WHERE l.cupo = :cupo"), @NamedQuery(name = "Locacion.findByVendidas", query = "SELECT l FROM Locacion l WHERE l.vendidas = :vendidas"), @NamedQuery(name = "Locacion.findByPrecio", query = "SELECT l FROM Locacion l WHERE l.precio = :precio")})
public class Locacion implements Serializable {
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
    @Column(name = "cupo")
    private int cupo;
    @Basic(optional = false)
    @Column(name = "vendidas")
    private int vendidas;
    @Basic(optional = false)
    @Column(name = "precio")
    private double precio;
    @JoinColumn(name = "fk_presentacion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Presentacion fkPresentacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkLocacion")
    private List<Boleta> boletaList;

    public Locacion() {
    }

    public Locacion(Integer id) {
        this.id = id;
    }

    public Locacion(Integer id, String nombre, int cupo, int vendidas, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.cupo = cupo;
        this.vendidas = vendidas;
        this.precio = precio;
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

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public int getVendidas() {
        return vendidas;
    }

    public void setVendidas(int vendidas) {
        this.vendidas = vendidas;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Presentacion getFkPresentacion() {
        return fkPresentacion;
    }

    public void setFkPresentacion(Presentacion fkPresentacion) {
        this.fkPresentacion = fkPresentacion;
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
        if (!(object instanceof Locacion)) {
            return false;
        }
        Locacion other = (Locacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Locacion[id=" + id + "]";
    }

}

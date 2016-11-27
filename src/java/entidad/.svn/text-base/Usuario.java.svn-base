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
@Table(name = "usuario")
@NamedQueries({@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"), @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"), @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario"), @NamedQuery(name = "Usuario.findByActivo", query = "SELECT u FROM Usuario u WHERE u.activo = :activo"), @NamedQuery(name = "Usuario.findByContrasena", query = "SELECT u FROM Usuario u WHERE u.contrasena = :contrasena"), @NamedQuery(name = "Usuario.findByNombres", query = "SELECT u FROM Usuario u WHERE u.nombres = :nombres"), @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos"), @NamedQuery(name = "Usuario.findByCedula", query = "SELECT u FROM Usuario u WHERE u.cedula = :cedula"), @NamedQuery(name = "Usuario.findByDireccion", query = "SELECT u FROM Usuario u WHERE u.direccion = :direccion"), @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @Basic(optional = false)
    @Column(name = "contrasena")
    private String contrasena;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "cedula")
    private String cedula;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @JoinColumn(name = "fk_puntodeventa", referencedColumnName = "id")
    @ManyToOne
    private Puntodeventa fkPuntodeventa;
    @JoinColumn(name = "fk_rol", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rol fkRol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkUsuario")
    private List<Registroventa> registroventaList;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, String usuario, boolean activo, String contrasena, String nombres, String apellidos, String cedula, String direccion) {
        this.id = id;
        this.usuario = usuario;
        this.activo = activo;
        this.contrasena = contrasena;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.direccion = direccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public Puntodeventa getFkPuntodeventa() {
        return fkPuntodeventa;
    }

    public void setFkPuntodeventa(Puntodeventa fkPuntodeventa) {
        this.fkPuntodeventa = fkPuntodeventa;
    }

    public Rol getFkRol() {
        return fkRol;
    }

    public void setFkRol(Rol fkRol) {
        this.fkRol = fkRol;
    }

    public List<Registroventa> getRegistroventaList() {
        return registroventaList;
    }

    public void setRegistroventaList(List<Registroventa> registroventaList) {
        this.registroventaList = registroventaList;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Usuario[id=" + id + "]";
    }

}

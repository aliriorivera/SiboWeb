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
@Table(name = "cliente")
@NamedQueries({@NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"), @NamedQuery(name = "Cliente.findById", query = "SELECT c FROM Cliente c WHERE c.id = :id"), @NamedQuery(name = "Cliente.findByUsuario", query = "SELECT c FROM Cliente c WHERE c.usuario = :usuario"), @NamedQuery(name = "Cliente.findByCodigoActivacion", query = "SELECT c FROM Cliente c WHERE c.codigoActivacion = :codigoActivacion"), @NamedQuery(name = "Cliente.findByActivo", query = "SELECT c FROM Cliente c WHERE c.activo = :activo"), @NamedQuery(name = "Cliente.findByContrasena", query = "SELECT c FROM Cliente c WHERE c.contrasena = :contrasena"), @NamedQuery(name = "Cliente.findByNombres", query = "SELECT c FROM Cliente c WHERE c.nombres = :nombres"), @NamedQuery(name = "Cliente.findByApellidos", query = "SELECT c FROM Cliente c WHERE c.apellidos = :apellidos"), @NamedQuery(name = "Cliente.findByTipodocumento", query = "SELECT c FROM Cliente c WHERE c.tipodocumento = :tipodocumento"), @NamedQuery(name = "Cliente.findByDocumento", query = "SELECT c FROM Cliente c WHERE c.documento = :documento"), @NamedQuery(name = "Cliente.findByEMail", query = "SELECT c FROM Cliente c WHERE c.eMail = :eMail"), @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono"), @NamedQuery(name = "Cliente.findByCiudad", query = "SELECT c FROM Cliente c WHERE c.ciudad = :ciudad"), @NamedQuery(name = "Cliente.findByDireccion", query = "SELECT c FROM Cliente c WHERE c.direccion = :direccion")})
public class Cliente implements Serializable {
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
    @Column(name = "codigo_activacion")
    private String codigoActivacion;
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
    @Column(name = "tipodocumento")
    private String tipodocumento;
    @Basic(optional = false)
    @Column(name = "documento")
    private String documento;
    @Basic(optional = false)
    @Column(name = "e_mail")
    private String eMail;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "ciudad")
    private String ciudad;
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(mappedBy = "fkCliente")
    private List<Registroventa> registroventaList;

    public Cliente() {
    }

    public Cliente(Integer id) {
        this.id = id;
    }

    public Cliente(Integer id, String usuario, String codigoActivacion, boolean activo, String contrasena, String nombres, String apellidos, String tipodocumento, String documento, String eMail, String telefono, String ciudad) {
        this.id = id;
        this.usuario = usuario;
        this.codigoActivacion = codigoActivacion;
        this.activo = activo;
        this.contrasena = contrasena;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipodocumento = tipodocumento;
        this.documento = documento;
        this.eMail = eMail;
        this.telefono = telefono;
        this.ciudad = ciudad;
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

    public String getCodigoActivacion() {
        return codigoActivacion;
    }

    public void setCodigoActivacion(String codigoActivacion) {
        this.codigoActivacion = codigoActivacion;
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

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Cliente[id=" + id + "]";
    }

}

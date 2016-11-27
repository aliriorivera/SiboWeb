/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import entidad.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import entidad.Puntodeventa;
import entidad.Rol;
import entidad.Registroventa;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UNAL
 */
public class UsuarioDAO {

    public UsuarioDAO() {
        emf = Persistence.createEntityManagerFactory("SIBOWebPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getRegistroventaList() == null) {
            usuario.setRegistroventaList(new ArrayList<Registroventa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puntodeventa fkPuntodeventa = usuario.getFkPuntodeventa();
            if (fkPuntodeventa != null) {
                fkPuntodeventa = em.getReference(fkPuntodeventa.getClass(), fkPuntodeventa.getId());
                usuario.setFkPuntodeventa(fkPuntodeventa);
            }
            Rol fkRol = usuario.getFkRol();
            if (fkRol != null) {
                fkRol = em.getReference(fkRol.getClass(), fkRol.getId());
                usuario.setFkRol(fkRol);
            }
            List<Registroventa> attachedRegistroventaList = new ArrayList<Registroventa>();
            for (Registroventa registroventaListRegistroventaToAttach : usuario.getRegistroventaList()) {
                registroventaListRegistroventaToAttach = em.getReference(registroventaListRegistroventaToAttach.getClass(), registroventaListRegistroventaToAttach.getId());
                attachedRegistroventaList.add(registroventaListRegistroventaToAttach);
            }
            usuario.setRegistroventaList(attachedRegistroventaList);
            em.persist(usuario);
            if (fkPuntodeventa != null) {
                fkPuntodeventa.getUsuarioList().add(usuario);
                fkPuntodeventa = em.merge(fkPuntodeventa);
            }
            if (fkRol != null) {
                fkRol.getUsuarioList().add(usuario);
                fkRol = em.merge(fkRol);
            }
            for (Registroventa registroventaListRegistroventa : usuario.getRegistroventaList()) {
                Usuario oldFkUsuarioOfRegistroventaListRegistroventa = registroventaListRegistroventa.getFkUsuario();
                registroventaListRegistroventa.setFkUsuario(usuario);
                registroventaListRegistroventa = em.merge(registroventaListRegistroventa);
                if (oldFkUsuarioOfRegistroventaListRegistroventa != null) {
                    oldFkUsuarioOfRegistroventaListRegistroventa.getRegistroventaList().remove(registroventaListRegistroventa);
                    oldFkUsuarioOfRegistroventaListRegistroventa = em.merge(oldFkUsuarioOfRegistroventaListRegistroventa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Puntodeventa fkPuntodeventaOld = persistentUsuario.getFkPuntodeventa();
            Puntodeventa fkPuntodeventaNew = usuario.getFkPuntodeventa();
            Rol fkRolOld = persistentUsuario.getFkRol();
            Rol fkRolNew = usuario.getFkRol();
            List<Registroventa> registroventaListOld = persistentUsuario.getRegistroventaList();
            List<Registroventa> registroventaListNew = usuario.getRegistroventaList();
            List<String> illegalOrphanMessages = null;
            for (Registroventa registroventaListOldRegistroventa : registroventaListOld) {
                if (!registroventaListNew.contains(registroventaListOldRegistroventa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Registroventa " + registroventaListOldRegistroventa + " since its fkUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkPuntodeventaNew != null) {
                fkPuntodeventaNew = em.getReference(fkPuntodeventaNew.getClass(), fkPuntodeventaNew.getId());
                usuario.setFkPuntodeventa(fkPuntodeventaNew);
            }
            if (fkRolNew != null) {
                fkRolNew = em.getReference(fkRolNew.getClass(), fkRolNew.getId());
                usuario.setFkRol(fkRolNew);
            }
            List<Registroventa> attachedRegistroventaListNew = new ArrayList<Registroventa>();
            for (Registroventa registroventaListNewRegistroventaToAttach : registroventaListNew) {
                registroventaListNewRegistroventaToAttach = em.getReference(registroventaListNewRegistroventaToAttach.getClass(), registroventaListNewRegistroventaToAttach.getId());
                attachedRegistroventaListNew.add(registroventaListNewRegistroventaToAttach);
            }
            registroventaListNew = attachedRegistroventaListNew;
            usuario.setRegistroventaList(registroventaListNew);
            usuario = em.merge(usuario);
            if (fkPuntodeventaOld != null && !fkPuntodeventaOld.equals(fkPuntodeventaNew)) {
                fkPuntodeventaOld.getUsuarioList().remove(usuario);
                fkPuntodeventaOld = em.merge(fkPuntodeventaOld);
            }
            if (fkPuntodeventaNew != null && !fkPuntodeventaNew.equals(fkPuntodeventaOld)) {
                fkPuntodeventaNew.getUsuarioList().add(usuario);
                fkPuntodeventaNew = em.merge(fkPuntodeventaNew);
            }
            if (fkRolOld != null && !fkRolOld.equals(fkRolNew)) {
                fkRolOld.getUsuarioList().remove(usuario);
                fkRolOld = em.merge(fkRolOld);
            }
            if (fkRolNew != null && !fkRolNew.equals(fkRolOld)) {
                fkRolNew.getUsuarioList().add(usuario);
                fkRolNew = em.merge(fkRolNew);
            }
            for (Registroventa registroventaListNewRegistroventa : registroventaListNew) {
                if (!registroventaListOld.contains(registroventaListNewRegistroventa)) {
                    Usuario oldFkUsuarioOfRegistroventaListNewRegistroventa = registroventaListNewRegistroventa.getFkUsuario();
                    registroventaListNewRegistroventa.setFkUsuario(usuario);
                    registroventaListNewRegistroventa = em.merge(registroventaListNewRegistroventa);
                    if (oldFkUsuarioOfRegistroventaListNewRegistroventa != null && !oldFkUsuarioOfRegistroventaListNewRegistroventa.equals(usuario)) {
                        oldFkUsuarioOfRegistroventaListNewRegistroventa.getRegistroventaList().remove(registroventaListNewRegistroventa);
                        oldFkUsuarioOfRegistroventaListNewRegistroventa = em.merge(oldFkUsuarioOfRegistroventaListNewRegistroventa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Registroventa> registroventaListOrphanCheck = usuario.getRegistroventaList();
            for (Registroventa registroventaListOrphanCheckRegistroventa : registroventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Registroventa " + registroventaListOrphanCheckRegistroventa + " in its registroventaList field has a non-nullable fkUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Puntodeventa fkPuntodeventa = usuario.getFkPuntodeventa();
            if (fkPuntodeventa != null) {
                fkPuntodeventa.getUsuarioList().remove(usuario);
                fkPuntodeventa = em.merge(fkPuntodeventa);
            }
            Rol fkRol = usuario.getFkRol();
            if (fkRol != null) {
                fkRol.getUsuarioList().remove(usuario);
                fkRol = em.merge(fkRol);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Usuario as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Usuario as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuarioByNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {
            return (Usuario)em.createQuery("SELECT u FROM Usuario u WHERE u.usuario=?1").setParameter(1, nombre).getResultList().get(0);
        } finally {
            em.close();
        }
    }
}

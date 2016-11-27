/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.NonexistentEntityException;
import entidad.Puntodeventa;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import entidad.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UNAL
 */
public class PuntodeventaDAO {

    public PuntodeventaDAO() {
        emf = Persistence.createEntityManagerFactory("SIBOWebPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Puntodeventa puntodeventa) {
        if (puntodeventa.getUsuarioList() == null) {
            puntodeventa.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : puntodeventa.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getId());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            puntodeventa.setUsuarioList(attachedUsuarioList);
            em.persist(puntodeventa);
            for (Usuario usuarioListUsuario : puntodeventa.getUsuarioList()) {
                Puntodeventa oldFkPuntodeventaOfUsuarioListUsuario = usuarioListUsuario.getFkPuntodeventa();
                usuarioListUsuario.setFkPuntodeventa(puntodeventa);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldFkPuntodeventaOfUsuarioListUsuario != null) {
                    oldFkPuntodeventaOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldFkPuntodeventaOfUsuarioListUsuario = em.merge(oldFkPuntodeventaOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Puntodeventa puntodeventa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puntodeventa persistentPuntodeventa = em.find(Puntodeventa.class, puntodeventa.getId());
            List<Usuario> usuarioListOld = persistentPuntodeventa.getUsuarioList();
            List<Usuario> usuarioListNew = puntodeventa.getUsuarioList();
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getId());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            puntodeventa.setUsuarioList(usuarioListNew);
            puntodeventa = em.merge(puntodeventa);
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.setFkPuntodeventa(null);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Puntodeventa oldFkPuntodeventaOfUsuarioListNewUsuario = usuarioListNewUsuario.getFkPuntodeventa();
                    usuarioListNewUsuario.setFkPuntodeventa(puntodeventa);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldFkPuntodeventaOfUsuarioListNewUsuario != null && !oldFkPuntodeventaOfUsuarioListNewUsuario.equals(puntodeventa)) {
                        oldFkPuntodeventaOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldFkPuntodeventaOfUsuarioListNewUsuario = em.merge(oldFkPuntodeventaOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = puntodeventa.getId();
                if (findPuntodeventa(id) == null) {
                    throw new NonexistentEntityException("The puntodeventa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puntodeventa puntodeventa;
            try {
                puntodeventa = em.getReference(Puntodeventa.class, id);
                puntodeventa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The puntodeventa with id " + id + " no longer exists.", enfe);
            }
            List<Usuario> usuarioList = puntodeventa.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.setFkPuntodeventa(null);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            em.remove(puntodeventa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Puntodeventa> findPuntodeventaEntities() {
        return findPuntodeventaEntities(true, -1, -1);
    }

    public List<Puntodeventa> findPuntodeventaEntities(int maxResults, int firstResult) {
        return findPuntodeventaEntities(false, maxResults, firstResult);
    }

    private List<Puntodeventa> findPuntodeventaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Puntodeventa as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Puntodeventa findPuntodeventa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Puntodeventa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPuntodeventaCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Puntodeventa as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

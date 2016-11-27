/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.NonexistentEntityException;
import entidad.Remuneracion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author UNAL
 */
public class RemuneracionDAO {

    public RemuneracionDAO() {
        emf = Persistence.createEntityManagerFactory("SIBOWebPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Remuneracion remuneracion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(remuneracion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Remuneracion remuneracion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            remuneracion = em.merge(remuneracion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = remuneracion.getId();
                if (findRemuneracion(id) == null) {
                    throw new NonexistentEntityException("The remuneracion with id " + id + " no longer exists.");
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
            Remuneracion remuneracion;
            try {
                remuneracion = em.getReference(Remuneracion.class, id);
                remuneracion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The remuneracion with id " + id + " no longer exists.", enfe);
            }
            em.remove(remuneracion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Remuneracion> findRemuneracionEntities() {
        return findRemuneracionEntities(true, -1, -1);
    }

    public List<Remuneracion> findRemuneracionEntities(int maxResults, int firstResult) {
        return findRemuneracionEntities(false, maxResults, firstResult);
    }

    private List<Remuneracion> findRemuneracionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Remuneracion as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Remuneracion findRemuneracion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Remuneracion.class, id);
        } finally {
            em.close();
        }
    }

    public int getRemuneracionCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Remuneracion as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

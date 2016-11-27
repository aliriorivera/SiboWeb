/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.NonexistentEntityException;
import entidad.Reporteventas;
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
public class ReporteventasDAO {

    public ReporteventasDAO() {
        emf = Persistence.createEntityManagerFactory("SIBOWebPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reporteventas reporteventas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(reporteventas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reporteventas reporteventas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            reporteventas = em.merge(reporteventas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reporteventas.getId();
                if (findReporteventas(id) == null) {
                    throw new NonexistentEntityException("The reporteventas with id " + id + " no longer exists.");
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
            Reporteventas reporteventas;
            try {
                reporteventas = em.getReference(Reporteventas.class, id);
                reporteventas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reporteventas with id " + id + " no longer exists.", enfe);
            }
            em.remove(reporteventas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reporteventas> findReporteventasEntities() {
        return findReporteventasEntities(true, -1, -1);
    }

    public List<Reporteventas> findReporteventasEntities(int maxResults, int firstResult) {
        return findReporteventasEntities(false, maxResults, firstResult);
    }

    private List<Reporteventas> findReporteventasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Reporteventas as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Reporteventas findReporteventas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reporteventas.class, id);
        } finally {
            em.close();
        }
    }

    public int getReporteventasCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Reporteventas as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

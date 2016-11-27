/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.NonexistentEntityException;
import entidad.Boleta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import entidad.Locacion;
import entidad.Registroventa;
import java.util.ArrayList;

/**
 *
 * @author UNAL
 */
public class BoletaDAO {

    public BoletaDAO() {
        emf = Persistence.createEntityManagerFactory("SIBOWebPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Boleta boleta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Locacion fkLocacion = boleta.getFkLocacion();
            if (fkLocacion != null) {
                fkLocacion = em.getReference(fkLocacion.getClass(), fkLocacion.getId());
                boleta.setFkLocacion(fkLocacion);
            }
            Registroventa fkRegistroventa = boleta.getFkRegistroventa();
            if (fkRegistroventa != null) {
                fkRegistroventa = em.getReference(fkRegistroventa.getClass(), fkRegistroventa.getId());
                boleta.setFkRegistroventa(fkRegistroventa);
            }
            em.persist(boleta);
            if (fkLocacion != null) {
                fkLocacion.getBoletaList().add(boleta);
                fkLocacion = em.merge(fkLocacion);
            }
            if (fkRegistroventa != null) {
                fkRegistroventa.getBoletaList().add(boleta);
                fkRegistroventa = em.merge(fkRegistroventa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Boleta boleta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Boleta persistentBoleta = em.find(Boleta.class, boleta.getId());
            Locacion fkLocacionOld = persistentBoleta.getFkLocacion();
            Locacion fkLocacionNew = boleta.getFkLocacion();
            Registroventa fkRegistroventaOld = persistentBoleta.getFkRegistroventa();
            Registroventa fkRegistroventaNew = boleta.getFkRegistroventa();
            if (fkLocacionNew != null) {
                fkLocacionNew = em.getReference(fkLocacionNew.getClass(), fkLocacionNew.getId());
                boleta.setFkLocacion(fkLocacionNew);
            }
            if (fkRegistroventaNew != null) {
                fkRegistroventaNew = em.getReference(fkRegistroventaNew.getClass(), fkRegistroventaNew.getId());
                boleta.setFkRegistroventa(fkRegistroventaNew);
            }
            boleta = em.merge(boleta);
            if (fkLocacionOld != null && !fkLocacionOld.equals(fkLocacionNew)) {
                fkLocacionOld.getBoletaList().remove(boleta);
                fkLocacionOld = em.merge(fkLocacionOld);
            }
            if (fkLocacionNew != null && !fkLocacionNew.equals(fkLocacionOld)) {
                fkLocacionNew.getBoletaList().add(boleta);
                fkLocacionNew = em.merge(fkLocacionNew);
            }
            if (fkRegistroventaOld != null && !fkRegistroventaOld.equals(fkRegistroventaNew)) {
                fkRegistroventaOld.getBoletaList().remove(boleta);
                fkRegistroventaOld = em.merge(fkRegistroventaOld);
            }
            if (fkRegistroventaNew != null && !fkRegistroventaNew.equals(fkRegistroventaOld)) {
                fkRegistroventaNew.getBoletaList().add(boleta);
                fkRegistroventaNew = em.merge(fkRegistroventaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = boleta.getId();
                if (findBoleta(id) == null) {
                    throw new NonexistentEntityException("The boleta with id " + id + " no longer exists.");
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
            Boleta boleta;
            try {
                boleta = em.getReference(Boleta.class, id);
                boleta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The boleta with id " + id + " no longer exists.", enfe);
            }
            Locacion fkLocacion = boleta.getFkLocacion();
            if (fkLocacion != null) {
                fkLocacion.getBoletaList().remove(boleta);
                fkLocacion = em.merge(fkLocacion);
            }
            Registroventa fkRegistroventa = boleta.getFkRegistroventa();
            if (fkRegistroventa != null) {
                fkRegistroventa.getBoletaList().remove(boleta);
                fkRegistroventa = em.merge(fkRegistroventa);
            }
            em.remove(boleta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Boleta> findBoletaEntities() {
        return findBoletaEntities(true, -1, -1);
    }

    public List<Boleta> findBoletaEntities(int maxResults, int firstResult) {
        return findBoletaEntities(false, maxResults, firstResult);
    }

    private List<Boleta> findBoletaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Boleta as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Boleta findBoleta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Boleta.class, id);
        } finally {
            em.close();
        }
    }

    public int getBoletaCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Boleta as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public int getBoletaCountByUser(int cliente) {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("SELECT count(c) FROM Boleta c JOIN c.fkRegistroventa Registroventa WHERE Registroventa.fkCliente.id=?1 AND c.estado='Reservada'").setParameter(1, cliente).getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Boleta> getBoletaByUser(int cliente, int first, int max){
        EntityManager em=emf.createEntityManager();
        List res=new ArrayList();
        Query q=em.createQuery("SELECT c FROM Boleta c JOIN c.fkRegistroventa Registroventa WHERE Registroventa.fkCliente.id=?1 AND c.estado='Reservada'").setParameter(1, cliente);
        q.setFirstResult(first);
        q.setMaxResults(max);
        res=q.getResultList();
        return res;
    }
}

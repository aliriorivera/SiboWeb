/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import entidad.Locacion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import entidad.Presentacion;
import entidad.Boleta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UNAL
 */
public class LocacionDAO {

    public LocacionDAO() {
        emf = Persistence.createEntityManagerFactory("SIBOWebPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Locacion locacion) {
        if (locacion.getBoletaList() == null) {
            locacion.setBoletaList(new ArrayList<Boleta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presentacion fkPresentacion = locacion.getFkPresentacion();
            if (fkPresentacion != null) {
                fkPresentacion = em.getReference(fkPresentacion.getClass(), fkPresentacion.getId());
                locacion.setFkPresentacion(fkPresentacion);
            }
            List<Boleta> attachedBoletaList = new ArrayList<Boleta>();
            for (Boleta boletaListBoletaToAttach : locacion.getBoletaList()) {
                boletaListBoletaToAttach = em.getReference(boletaListBoletaToAttach.getClass(), boletaListBoletaToAttach.getId());
                attachedBoletaList.add(boletaListBoletaToAttach);
            }
            locacion.setBoletaList(attachedBoletaList);
            em.persist(locacion);
            if (fkPresentacion != null) {
                fkPresentacion.getLocacionList().add(locacion);
                fkPresentacion = em.merge(fkPresentacion);
            }
            for (Boleta boletaListBoleta : locacion.getBoletaList()) {
                Locacion oldFkLocacionOfBoletaListBoleta = boletaListBoleta.getFkLocacion();
                boletaListBoleta.setFkLocacion(locacion);
                boletaListBoleta = em.merge(boletaListBoleta);
                if (oldFkLocacionOfBoletaListBoleta != null) {
                    oldFkLocacionOfBoletaListBoleta.getBoletaList().remove(boletaListBoleta);
                    oldFkLocacionOfBoletaListBoleta = em.merge(oldFkLocacionOfBoletaListBoleta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Locacion locacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Locacion persistentLocacion = em.find(Locacion.class, locacion.getId());
            Presentacion fkPresentacionOld = persistentLocacion.getFkPresentacion();
            Presentacion fkPresentacionNew = locacion.getFkPresentacion();
            List<Boleta> boletaListOld = persistentLocacion.getBoletaList();
            List<Boleta> boletaListNew = locacion.getBoletaList();
            List<String> illegalOrphanMessages = null;
            for (Boleta boletaListOldBoleta : boletaListOld) {
                if (!boletaListNew.contains(boletaListOldBoleta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Boleta " + boletaListOldBoleta + " since its fkLocacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkPresentacionNew != null) {
                fkPresentacionNew = em.getReference(fkPresentacionNew.getClass(), fkPresentacionNew.getId());
                locacion.setFkPresentacion(fkPresentacionNew);
            }
            List<Boleta> attachedBoletaListNew = new ArrayList<Boleta>();
            for (Boleta boletaListNewBoletaToAttach : boletaListNew) {
                boletaListNewBoletaToAttach = em.getReference(boletaListNewBoletaToAttach.getClass(), boletaListNewBoletaToAttach.getId());
                attachedBoletaListNew.add(boletaListNewBoletaToAttach);
            }
            boletaListNew = attachedBoletaListNew;
            locacion.setBoletaList(boletaListNew);
            locacion = em.merge(locacion);
            if (fkPresentacionOld != null && !fkPresentacionOld.equals(fkPresentacionNew)) {
                fkPresentacionOld.getLocacionList().remove(locacion);
                fkPresentacionOld = em.merge(fkPresentacionOld);
            }
            if (fkPresentacionNew != null && !fkPresentacionNew.equals(fkPresentacionOld)) {
                fkPresentacionNew.getLocacionList().add(locacion);
                fkPresentacionNew = em.merge(fkPresentacionNew);
            }
            for (Boleta boletaListNewBoleta : boletaListNew) {
                if (!boletaListOld.contains(boletaListNewBoleta)) {
                    Locacion oldFkLocacionOfBoletaListNewBoleta = boletaListNewBoleta.getFkLocacion();
                    boletaListNewBoleta.setFkLocacion(locacion);
                    boletaListNewBoleta = em.merge(boletaListNewBoleta);
                    if (oldFkLocacionOfBoletaListNewBoleta != null && !oldFkLocacionOfBoletaListNewBoleta.equals(locacion)) {
                        oldFkLocacionOfBoletaListNewBoleta.getBoletaList().remove(boletaListNewBoleta);
                        oldFkLocacionOfBoletaListNewBoleta = em.merge(oldFkLocacionOfBoletaListNewBoleta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = locacion.getId();
                if (findLocacion(id) == null) {
                    throw new NonexistentEntityException("The locacion with id " + id + " no longer exists.");
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
            Locacion locacion;
            try {
                locacion = em.getReference(Locacion.class, id);
                locacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The locacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Boleta> boletaListOrphanCheck = locacion.getBoletaList();
            for (Boleta boletaListOrphanCheckBoleta : boletaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Locacion (" + locacion + ") cannot be destroyed since the Boleta " + boletaListOrphanCheckBoleta + " in its boletaList field has a non-nullable fkLocacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Presentacion fkPresentacion = locacion.getFkPresentacion();
            if (fkPresentacion != null) {
                fkPresentacion.getLocacionList().remove(locacion);
                fkPresentacion = em.merge(fkPresentacion);
            }
            em.remove(locacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Locacion> findLocacionEntities() {
        return findLocacionEntities(true, -1, -1);
    }

    public List<Locacion> findLocacionEntities(int maxResults, int firstResult) {
        return findLocacionEntities(false, maxResults, firstResult);
    }

    private List<Locacion> findLocacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Locacion as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Locacion findLocacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Locacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocacionCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Locacion as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import entidad.Presentacion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import entidad.Evento;
import entidad.Locacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UNAL
 */
public class PresentacionDAO {

    public PresentacionDAO() {
        emf = Persistence.createEntityManagerFactory("SIBOWebPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Presentacion presentacion) {
        if (presentacion.getLocacionList() == null) {
            presentacion.setLocacionList(new ArrayList<Locacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evento fkEvento = presentacion.getFkEvento();
            if (fkEvento != null) {
                fkEvento = em.getReference(fkEvento.getClass(), fkEvento.getId());
                presentacion.setFkEvento(fkEvento);
            }
            List<Locacion> attachedLocacionList = new ArrayList<Locacion>();
            for (Locacion locacionListLocacionToAttach : presentacion.getLocacionList()) {
                locacionListLocacionToAttach = em.getReference(locacionListLocacionToAttach.getClass(), locacionListLocacionToAttach.getId());
                attachedLocacionList.add(locacionListLocacionToAttach);
            }
            presentacion.setLocacionList(attachedLocacionList);
            em.persist(presentacion);
            if (fkEvento != null) {
                fkEvento.getPresentacionList().add(presentacion);
                fkEvento = em.merge(fkEvento);
            }
            for (Locacion locacionListLocacion : presentacion.getLocacionList()) {
                Presentacion oldFkPresentacionOfLocacionListLocacion = locacionListLocacion.getFkPresentacion();
                locacionListLocacion.setFkPresentacion(presentacion);
                locacionListLocacion = em.merge(locacionListLocacion);
                if (oldFkPresentacionOfLocacionListLocacion != null) {
                    oldFkPresentacionOfLocacionListLocacion.getLocacionList().remove(locacionListLocacion);
                    oldFkPresentacionOfLocacionListLocacion = em.merge(oldFkPresentacionOfLocacionListLocacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Presentacion presentacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presentacion persistentPresentacion = em.find(Presentacion.class, presentacion.getId());
            Evento fkEventoOld = persistentPresentacion.getFkEvento();
            Evento fkEventoNew = presentacion.getFkEvento();
            List<Locacion> locacionListOld = persistentPresentacion.getLocacionList();
            List<Locacion> locacionListNew = presentacion.getLocacionList();
            List<String> illegalOrphanMessages = null;
            for (Locacion locacionListOldLocacion : locacionListOld) {
                if (!locacionListNew.contains(locacionListOldLocacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Locacion " + locacionListOldLocacion + " since its fkPresentacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkEventoNew != null) {
                fkEventoNew = em.getReference(fkEventoNew.getClass(), fkEventoNew.getId());
                presentacion.setFkEvento(fkEventoNew);
            }
            List<Locacion> attachedLocacionListNew = new ArrayList<Locacion>();
            for (Locacion locacionListNewLocacionToAttach : locacionListNew) {
                locacionListNewLocacionToAttach = em.getReference(locacionListNewLocacionToAttach.getClass(), locacionListNewLocacionToAttach.getId());
                attachedLocacionListNew.add(locacionListNewLocacionToAttach);
            }
            locacionListNew = attachedLocacionListNew;
            presentacion.setLocacionList(locacionListNew);
            presentacion = em.merge(presentacion);
            if (fkEventoOld != null && !fkEventoOld.equals(fkEventoNew)) {
                fkEventoOld.getPresentacionList().remove(presentacion);
                fkEventoOld = em.merge(fkEventoOld);
            }
            if (fkEventoNew != null && !fkEventoNew.equals(fkEventoOld)) {
                fkEventoNew.getPresentacionList().add(presentacion);
                fkEventoNew = em.merge(fkEventoNew);
            }
            for (Locacion locacionListNewLocacion : locacionListNew) {
                if (!locacionListOld.contains(locacionListNewLocacion)) {
                    Presentacion oldFkPresentacionOfLocacionListNewLocacion = locacionListNewLocacion.getFkPresentacion();
                    locacionListNewLocacion.setFkPresentacion(presentacion);
                    locacionListNewLocacion = em.merge(locacionListNewLocacion);
                    if (oldFkPresentacionOfLocacionListNewLocacion != null && !oldFkPresentacionOfLocacionListNewLocacion.equals(presentacion)) {
                        oldFkPresentacionOfLocacionListNewLocacion.getLocacionList().remove(locacionListNewLocacion);
                        oldFkPresentacionOfLocacionListNewLocacion = em.merge(oldFkPresentacionOfLocacionListNewLocacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = presentacion.getId();
                if (findPresentacion(id) == null) {
                    throw new NonexistentEntityException("The presentacion with id " + id + " no longer exists.");
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
            Presentacion presentacion;
            try {
                presentacion = em.getReference(Presentacion.class, id);
                presentacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The presentacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Locacion> locacionListOrphanCheck = presentacion.getLocacionList();
            for (Locacion locacionListOrphanCheckLocacion : locacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Presentacion (" + presentacion + ") cannot be destroyed since the Locacion " + locacionListOrphanCheckLocacion + " in its locacionList field has a non-nullable fkPresentacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Evento fkEvento = presentacion.getFkEvento();
            if (fkEvento != null) {
                fkEvento.getPresentacionList().remove(presentacion);
                fkEvento = em.merge(fkEvento);
            }
            em.remove(presentacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Presentacion> findPresentacionEntities() {
        return findPresentacionEntities(true, -1, -1);
    }

    public List<Presentacion> findPresentacionEntities(int maxResults, int firstResult) {
        return findPresentacionEntities(false, maxResults, firstResult);
    }

    private List<Presentacion> findPresentacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Presentacion as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Presentacion findPresentacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Presentacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPresentacionCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Presentacion as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

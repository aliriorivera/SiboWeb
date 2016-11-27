/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import entidad.Organizador;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import entidad.Evento;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UNAL
 */
public class OrganizadorDAO {

    public OrganizadorDAO() {
        emf = Persistence.createEntityManagerFactory("SIBOWebPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Organizador organizador) throws PreexistingEntityException, Exception {
        if (organizador.getEventoList() == null) {
            organizador.setEventoList(new ArrayList<Evento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Evento> attachedEventoList = new ArrayList<Evento>();
            for (Evento eventoListEventoToAttach : organizador.getEventoList()) {
                eventoListEventoToAttach = em.getReference(eventoListEventoToAttach.getClass(), eventoListEventoToAttach.getId());
                attachedEventoList.add(eventoListEventoToAttach);
            }
            organizador.setEventoList(attachedEventoList);
            em.persist(organizador);
            for (Evento eventoListEvento : organizador.getEventoList()) {
                Organizador oldFkOrganizadorOfEventoListEvento = eventoListEvento.getFkOrganizador();
                eventoListEvento.setFkOrganizador(organizador);
                eventoListEvento = em.merge(eventoListEvento);
                if (oldFkOrganizadorOfEventoListEvento != null) {
                    oldFkOrganizadorOfEventoListEvento.getEventoList().remove(eventoListEvento);
                    oldFkOrganizadorOfEventoListEvento = em.merge(oldFkOrganizadorOfEventoListEvento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrganizador(organizador.getNit()) != null) {
                throw new PreexistingEntityException("Organizador " + organizador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Organizador organizador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Organizador persistentOrganizador = em.find(Organizador.class, organizador.getNit());
            List<Evento> eventoListOld = persistentOrganizador.getEventoList();
            List<Evento> eventoListNew = organizador.getEventoList();
            List<String> illegalOrphanMessages = null;
            for (Evento eventoListOldEvento : eventoListOld) {
                if (!eventoListNew.contains(eventoListOldEvento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evento " + eventoListOldEvento + " since its fkOrganizador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Evento> attachedEventoListNew = new ArrayList<Evento>();
            for (Evento eventoListNewEventoToAttach : eventoListNew) {
                eventoListNewEventoToAttach = em.getReference(eventoListNewEventoToAttach.getClass(), eventoListNewEventoToAttach.getId());
                attachedEventoListNew.add(eventoListNewEventoToAttach);
            }
            eventoListNew = attachedEventoListNew;
            organizador.setEventoList(eventoListNew);
            organizador = em.merge(organizador);
            for (Evento eventoListNewEvento : eventoListNew) {
                if (!eventoListOld.contains(eventoListNewEvento)) {
                    Organizador oldFkOrganizadorOfEventoListNewEvento = eventoListNewEvento.getFkOrganizador();
                    eventoListNewEvento.setFkOrganizador(organizador);
                    eventoListNewEvento = em.merge(eventoListNewEvento);
                    if (oldFkOrganizadorOfEventoListNewEvento != null && !oldFkOrganizadorOfEventoListNewEvento.equals(organizador)) {
                        oldFkOrganizadorOfEventoListNewEvento.getEventoList().remove(eventoListNewEvento);
                        oldFkOrganizadorOfEventoListNewEvento = em.merge(oldFkOrganizadorOfEventoListNewEvento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = organizador.getNit();
                if (findOrganizador(id) == null) {
                    throw new NonexistentEntityException("The organizador with id " + id + " no longer exists.");
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
            Organizador organizador;
            try {
                organizador = em.getReference(Organizador.class, id);
                organizador.getNit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The organizador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Evento> eventoListOrphanCheck = organizador.getEventoList();
            for (Evento eventoListOrphanCheckEvento : eventoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Organizador (" + organizador + ") cannot be destroyed since the Evento " + eventoListOrphanCheckEvento + " in its eventoList field has a non-nullable fkOrganizador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(organizador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Organizador> findOrganizadorEntities() {
        return findOrganizadorEntities(true, -1, -1);
    }

    public List<Organizador> findOrganizadorEntities(int maxResults, int firstResult) {
        return findOrganizadorEntities(false, maxResults, firstResult);
    }

    private List<Organizador> findOrganizadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Organizador as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Organizador findOrganizador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Organizador.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrganizadorCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Organizador as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

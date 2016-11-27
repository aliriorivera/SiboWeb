/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import entidad.Escenario;
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
public class EscenarioDAO {

    public EscenarioDAO() {
        emf = Persistence.createEntityManagerFactory("SIBOWebPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Escenario escenario) {
        if (escenario.getEventoList() == null) {
            escenario.setEventoList(new ArrayList<Evento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Evento> attachedEventoList = new ArrayList<Evento>();
            for (Evento eventoListEventoToAttach : escenario.getEventoList()) {
                eventoListEventoToAttach = em.getReference(eventoListEventoToAttach.getClass(), eventoListEventoToAttach.getId());
                attachedEventoList.add(eventoListEventoToAttach);
            }
            escenario.setEventoList(attachedEventoList);
            em.persist(escenario);
            for (Evento eventoListEvento : escenario.getEventoList()) {
                Escenario oldFkEscenarioOfEventoListEvento = eventoListEvento.getFkEscenario();
                eventoListEvento.setFkEscenario(escenario);
                eventoListEvento = em.merge(eventoListEvento);
                if (oldFkEscenarioOfEventoListEvento != null) {
                    oldFkEscenarioOfEventoListEvento.getEventoList().remove(eventoListEvento);
                    oldFkEscenarioOfEventoListEvento = em.merge(oldFkEscenarioOfEventoListEvento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Escenario escenario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Escenario persistentEscenario = em.find(Escenario.class, escenario.getId());
            List<Evento> eventoListOld = persistentEscenario.getEventoList();
            List<Evento> eventoListNew = escenario.getEventoList();
            List<String> illegalOrphanMessages = null;
            for (Evento eventoListOldEvento : eventoListOld) {
                if (!eventoListNew.contains(eventoListOldEvento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evento " + eventoListOldEvento + " since its fkEscenario field is not nullable.");
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
            escenario.setEventoList(eventoListNew);
            escenario = em.merge(escenario);
            for (Evento eventoListNewEvento : eventoListNew) {
                if (!eventoListOld.contains(eventoListNewEvento)) {
                    Escenario oldFkEscenarioOfEventoListNewEvento = eventoListNewEvento.getFkEscenario();
                    eventoListNewEvento.setFkEscenario(escenario);
                    eventoListNewEvento = em.merge(eventoListNewEvento);
                    if (oldFkEscenarioOfEventoListNewEvento != null && !oldFkEscenarioOfEventoListNewEvento.equals(escenario)) {
                        oldFkEscenarioOfEventoListNewEvento.getEventoList().remove(eventoListNewEvento);
                        oldFkEscenarioOfEventoListNewEvento = em.merge(oldFkEscenarioOfEventoListNewEvento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = escenario.getId();
                if (findEscenario(id) == null) {
                    throw new NonexistentEntityException("The escenario with id " + id + " no longer exists.");
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
            Escenario escenario;
            try {
                escenario = em.getReference(Escenario.class, id);
                escenario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The escenario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Evento> eventoListOrphanCheck = escenario.getEventoList();
            for (Evento eventoListOrphanCheckEvento : eventoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Escenario (" + escenario + ") cannot be destroyed since the Evento " + eventoListOrphanCheckEvento + " in its eventoList field has a non-nullable fkEscenario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(escenario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Escenario> findEscenarioEntities() {
        return findEscenarioEntities(true, -1, -1);
    }

    public List<Escenario> findEscenarioEntities(int maxResults, int firstResult) {
        return findEscenarioEntities(false, maxResults, firstResult);
    }

    private List<Escenario> findEscenarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Escenario as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Escenario findEscenario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Escenario.class, id);
        } finally {
            em.close();
        }
    }

    public int getEscenarioCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Escenario as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

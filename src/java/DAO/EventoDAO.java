/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import entidad.Evento;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import entidad.Escenario;
import entidad.Categoria;
import entidad.Organizador;
import entidad.Presentacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UNAL
 */
public class EventoDAO {

    public EventoDAO() {
        emf = Persistence.createEntityManagerFactory("SIBOWebPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evento evento) {
        if (evento.getPresentacionList() == null) {
            evento.setPresentacionList(new ArrayList<Presentacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Escenario fkEscenario = evento.getFkEscenario();
            if (fkEscenario != null) {
                fkEscenario = em.getReference(fkEscenario.getClass(), fkEscenario.getId());
                evento.setFkEscenario(fkEscenario);
            }
            Categoria fkCategoria = evento.getFkCategoria();
            if (fkCategoria != null) {
                fkCategoria = em.getReference(fkCategoria.getClass(), fkCategoria.getId());
                evento.setFkCategoria(fkCategoria);
            }
            Organizador fkOrganizador = evento.getFkOrganizador();
            if (fkOrganizador != null) {
                fkOrganizador = em.getReference(fkOrganizador.getClass(), fkOrganizador.getNit());
                evento.setFkOrganizador(fkOrganizador);
            }
            List<Presentacion> attachedPresentacionList = new ArrayList<Presentacion>();
            for (Presentacion presentacionListPresentacionToAttach : evento.getPresentacionList()) {
                presentacionListPresentacionToAttach = em.getReference(presentacionListPresentacionToAttach.getClass(), presentacionListPresentacionToAttach.getId());
                attachedPresentacionList.add(presentacionListPresentacionToAttach);
            }
            evento.setPresentacionList(attachedPresentacionList);
            em.persist(evento);
            if (fkEscenario != null) {
                fkEscenario.getEventoList().add(evento);
                fkEscenario = em.merge(fkEscenario);
            }
            if (fkCategoria != null) {
                fkCategoria.getEventoList().add(evento);
                fkCategoria = em.merge(fkCategoria);
            }
            if (fkOrganizador != null) {
                fkOrganizador.getEventoList().add(evento);
                fkOrganizador = em.merge(fkOrganizador);
            }
            for (Presentacion presentacionListPresentacion : evento.getPresentacionList()) {
                Evento oldFkEventoOfPresentacionListPresentacion = presentacionListPresentacion.getFkEvento();
                presentacionListPresentacion.setFkEvento(evento);
                presentacionListPresentacion = em.merge(presentacionListPresentacion);
                if (oldFkEventoOfPresentacionListPresentacion != null) {
                    oldFkEventoOfPresentacionListPresentacion.getPresentacionList().remove(presentacionListPresentacion);
                    oldFkEventoOfPresentacionListPresentacion = em.merge(oldFkEventoOfPresentacionListPresentacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Evento evento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evento persistentEvento = em.find(Evento.class, evento.getId());
            Escenario fkEscenarioOld = persistentEvento.getFkEscenario();
            Escenario fkEscenarioNew = evento.getFkEscenario();
            Categoria fkCategoriaOld = persistentEvento.getFkCategoria();
            Categoria fkCategoriaNew = evento.getFkCategoria();
            Organizador fkOrganizadorOld = persistentEvento.getFkOrganizador();
            Organizador fkOrganizadorNew = evento.getFkOrganizador();
            List<Presentacion> presentacionListOld = persistentEvento.getPresentacionList();
            List<Presentacion> presentacionListNew = evento.getPresentacionList();
            List<String> illegalOrphanMessages = null;
            for (Presentacion presentacionListOldPresentacion : presentacionListOld) {
                if (!presentacionListNew.contains(presentacionListOldPresentacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Presentacion " + presentacionListOldPresentacion + " since its fkEvento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkEscenarioNew != null) {
                fkEscenarioNew = em.getReference(fkEscenarioNew.getClass(), fkEscenarioNew.getId());
                evento.setFkEscenario(fkEscenarioNew);
            }
            if (fkCategoriaNew != null) {
                fkCategoriaNew = em.getReference(fkCategoriaNew.getClass(), fkCategoriaNew.getId());
                evento.setFkCategoria(fkCategoriaNew);
            }
            if (fkOrganizadorNew != null) {
                fkOrganizadorNew = em.getReference(fkOrganizadorNew.getClass(), fkOrganizadorNew.getNit());
                evento.setFkOrganizador(fkOrganizadorNew);
            }
            List<Presentacion> attachedPresentacionListNew = new ArrayList<Presentacion>();
            for (Presentacion presentacionListNewPresentacionToAttach : presentacionListNew) {
                presentacionListNewPresentacionToAttach = em.getReference(presentacionListNewPresentacionToAttach.getClass(), presentacionListNewPresentacionToAttach.getId());
                attachedPresentacionListNew.add(presentacionListNewPresentacionToAttach);
            }
            presentacionListNew = attachedPresentacionListNew;
            evento.setPresentacionList(presentacionListNew);
            evento = em.merge(evento);
            if (fkEscenarioOld != null && !fkEscenarioOld.equals(fkEscenarioNew)) {
                fkEscenarioOld.getEventoList().remove(evento);
                fkEscenarioOld = em.merge(fkEscenarioOld);
            }
            if (fkEscenarioNew != null && !fkEscenarioNew.equals(fkEscenarioOld)) {
                fkEscenarioNew.getEventoList().add(evento);
                fkEscenarioNew = em.merge(fkEscenarioNew);
            }
            if (fkCategoriaOld != null && !fkCategoriaOld.equals(fkCategoriaNew)) {
                fkCategoriaOld.getEventoList().remove(evento);
                fkCategoriaOld = em.merge(fkCategoriaOld);
            }
            if (fkCategoriaNew != null && !fkCategoriaNew.equals(fkCategoriaOld)) {
                fkCategoriaNew.getEventoList().add(evento);
                fkCategoriaNew = em.merge(fkCategoriaNew);
            }
            if (fkOrganizadorOld != null && !fkOrganizadorOld.equals(fkOrganizadorNew)) {
                fkOrganizadorOld.getEventoList().remove(evento);
                fkOrganizadorOld = em.merge(fkOrganizadorOld);
            }
            if (fkOrganizadorNew != null && !fkOrganizadorNew.equals(fkOrganizadorOld)) {
                fkOrganizadorNew.getEventoList().add(evento);
                fkOrganizadorNew = em.merge(fkOrganizadorNew);
            }
            for (Presentacion presentacionListNewPresentacion : presentacionListNew) {
                if (!presentacionListOld.contains(presentacionListNewPresentacion)) {
                    Evento oldFkEventoOfPresentacionListNewPresentacion = presentacionListNewPresentacion.getFkEvento();
                    presentacionListNewPresentacion.setFkEvento(evento);
                    presentacionListNewPresentacion = em.merge(presentacionListNewPresentacion);
                    if (oldFkEventoOfPresentacionListNewPresentacion != null && !oldFkEventoOfPresentacionListNewPresentacion.equals(evento)) {
                        oldFkEventoOfPresentacionListNewPresentacion.getPresentacionList().remove(presentacionListNewPresentacion);
                        oldFkEventoOfPresentacionListNewPresentacion = em.merge(oldFkEventoOfPresentacionListNewPresentacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evento.getId();
                if (findEvento(id) == null) {
                    throw new NonexistentEntityException("The evento with id " + id + " no longer exists.");
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
            Evento evento;
            try {
                evento = em.getReference(Evento.class, id);
                evento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Presentacion> presentacionListOrphanCheck = evento.getPresentacionList();
            for (Presentacion presentacionListOrphanCheckPresentacion : presentacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Evento (" + evento + ") cannot be destroyed since the Presentacion " + presentacionListOrphanCheckPresentacion + " in its presentacionList field has a non-nullable fkEvento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Escenario fkEscenario = evento.getFkEscenario();
            if (fkEscenario != null) {
                fkEscenario.getEventoList().remove(evento);
                fkEscenario = em.merge(fkEscenario);
            }
            Categoria fkCategoria = evento.getFkCategoria();
            if (fkCategoria != null) {
                fkCategoria.getEventoList().remove(evento);
                fkCategoria = em.merge(fkCategoria);
            }
            Organizador fkOrganizador = evento.getFkOrganizador();
            if (fkOrganizador != null) {
                fkOrganizador.getEventoList().remove(evento);
                fkOrganizador = em.merge(fkOrganizador);
            }
            em.remove(evento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Evento> findEventoEntities() {
        return findEventoEntities(true, -1, -1);
    }

    public List<Evento> findEventoEntities(int maxResults, int firstResult) {
        return findEventoEntities(false, maxResults, firstResult);
    }

    private List<Evento> findEventoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Evento as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Evento findEvento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evento.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Evento as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public int getEventoCountByCat(String cat) {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("SELECT count(c) FROM Evento c WHERE (c.fkCategoria.nombre=?1 OR c.fkCategoria.fkCategoria.nombre=?1)").setParameter(1, cat).getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Evento> readEventoByCat(String cat, int first, int max){
        EntityManager em=emf.createEntityManager();
        List res=new ArrayList();
        Query q=em.createQuery("SELECT c FROM Evento c WHERE (c.fkCategoria.nombre=?1 OR c.fkCategoria.fkCategoria.nombre=?1)").setParameter(1, cat);
        q.setFirstResult(first);
        q.setMaxResults(max);
        res=q.getResultList();
        return res;
    }

    public int getEventoCountBySearch(String busqueda) {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("SELECT count(c) FROM Evento c WHERE (c.nombre LIKE ?1 OR c.artista LIKE ?1 OR c.descripcion LIKE ?1 OR c.fkEscenario.nombre LIKE ?1)").setParameter(1, busqueda).getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Evento> search(String busqueda, int first, int max){
        EntityManager em=emf.createEntityManager();
        List res=new ArrayList();
        Query q=em.createQuery("SELECT c FROM Evento c WHERE (c.nombre LIKE ?1 OR c.artista LIKE ?1 OR c.descripcion LIKE ?1 OR c.fkEscenario.nombre LIKE ?1)").setParameter(1, busqueda);
        q.setFirstResult(first);
        q.setMaxResults(max);
        res=q.getResultList();
        return res;
    }
}

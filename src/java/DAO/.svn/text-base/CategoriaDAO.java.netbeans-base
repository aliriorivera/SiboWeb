/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import entidad.Categoria;
import entidad.Evento;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UNAL
 */
public class CategoriaDAO {

    public CategoriaDAO() {
        emf = Persistence.createEntityManagerFactory("SIBOWebPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) {
        if (categoria.getEventoList() == null) {
            categoria.setEventoList(new ArrayList<Evento>());
        }
        if (categoria.getCategoriaList() == null) {
            categoria.setCategoriaList(new ArrayList<Categoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria fkCategoria = categoria.getFkCategoria();
            if (fkCategoria != null) {
                fkCategoria = em.getReference(fkCategoria.getClass(), fkCategoria.getId());
                categoria.setFkCategoria(fkCategoria);
            }
            List<Evento> attachedEventoList = new ArrayList<Evento>();
            for (Evento eventoListEventoToAttach : categoria.getEventoList()) {
                eventoListEventoToAttach = em.getReference(eventoListEventoToAttach.getClass(), eventoListEventoToAttach.getId());
                attachedEventoList.add(eventoListEventoToAttach);
            }
            categoria.setEventoList(attachedEventoList);
            List<Categoria> attachedCategoriaList = new ArrayList<Categoria>();
            for (Categoria categoriaListCategoriaToAttach : categoria.getCategoriaList()) {
                categoriaListCategoriaToAttach = em.getReference(categoriaListCategoriaToAttach.getClass(), categoriaListCategoriaToAttach.getId());
                attachedCategoriaList.add(categoriaListCategoriaToAttach);
            }
            categoria.setCategoriaList(attachedCategoriaList);
            em.persist(categoria);
            if (fkCategoria != null) {
                fkCategoria.getCategoriaList().add(categoria);
                fkCategoria = em.merge(fkCategoria);
            }
            for (Evento eventoListEvento : categoria.getEventoList()) {
                Categoria oldFkCategoriaOfEventoListEvento = eventoListEvento.getFkCategoria();
                eventoListEvento.setFkCategoria(categoria);
                eventoListEvento = em.merge(eventoListEvento);
                if (oldFkCategoriaOfEventoListEvento != null) {
                    oldFkCategoriaOfEventoListEvento.getEventoList().remove(eventoListEvento);
                    oldFkCategoriaOfEventoListEvento = em.merge(oldFkCategoriaOfEventoListEvento);
                }
            }
            for (Categoria categoriaListCategoria : categoria.getCategoriaList()) {
                Categoria oldFkCategoriaOfCategoriaListCategoria = categoriaListCategoria.getFkCategoria();
                categoriaListCategoria.setFkCategoria(categoria);
                categoriaListCategoria = em.merge(categoriaListCategoria);
                if (oldFkCategoriaOfCategoriaListCategoria != null) {
                    oldFkCategoriaOfCategoriaListCategoria.getCategoriaList().remove(categoriaListCategoria);
                    oldFkCategoriaOfCategoriaListCategoria = em.merge(oldFkCategoriaOfCategoriaListCategoria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoria categoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getId());
            Categoria fkCategoriaOld = persistentCategoria.getFkCategoria();
            Categoria fkCategoriaNew = categoria.getFkCategoria();
            List<Evento> eventoListOld = persistentCategoria.getEventoList();
            List<Evento> eventoListNew = categoria.getEventoList();
            List<Categoria> categoriaListOld = persistentCategoria.getCategoriaList();
            List<Categoria> categoriaListNew = categoria.getCategoriaList();
            List<String> illegalOrphanMessages = null;
            for (Evento eventoListOldEvento : eventoListOld) {
                if (!eventoListNew.contains(eventoListOldEvento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evento " + eventoListOldEvento + " since its fkCategoria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkCategoriaNew != null) {
                fkCategoriaNew = em.getReference(fkCategoriaNew.getClass(), fkCategoriaNew.getId());
                categoria.setFkCategoria(fkCategoriaNew);
            }
            List<Evento> attachedEventoListNew = new ArrayList<Evento>();
            for (Evento eventoListNewEventoToAttach : eventoListNew) {
                eventoListNewEventoToAttach = em.getReference(eventoListNewEventoToAttach.getClass(), eventoListNewEventoToAttach.getId());
                attachedEventoListNew.add(eventoListNewEventoToAttach);
            }
            eventoListNew = attachedEventoListNew;
            categoria.setEventoList(eventoListNew);
            List<Categoria> attachedCategoriaListNew = new ArrayList<Categoria>();
            for (Categoria categoriaListNewCategoriaToAttach : categoriaListNew) {
                categoriaListNewCategoriaToAttach = em.getReference(categoriaListNewCategoriaToAttach.getClass(), categoriaListNewCategoriaToAttach.getId());
                attachedCategoriaListNew.add(categoriaListNewCategoriaToAttach);
            }
            categoriaListNew = attachedCategoriaListNew;
            categoria.setCategoriaList(categoriaListNew);
            categoria = em.merge(categoria);
            if (fkCategoriaOld != null && !fkCategoriaOld.equals(fkCategoriaNew)) {
                fkCategoriaOld.getCategoriaList().remove(categoria);
                fkCategoriaOld = em.merge(fkCategoriaOld);
            }
            if (fkCategoriaNew != null && !fkCategoriaNew.equals(fkCategoriaOld)) {
                fkCategoriaNew.getCategoriaList().add(categoria);
                fkCategoriaNew = em.merge(fkCategoriaNew);
            }
            for (Evento eventoListNewEvento : eventoListNew) {
                if (!eventoListOld.contains(eventoListNewEvento)) {
                    Categoria oldFkCategoriaOfEventoListNewEvento = eventoListNewEvento.getFkCategoria();
                    eventoListNewEvento.setFkCategoria(categoria);
                    eventoListNewEvento = em.merge(eventoListNewEvento);
                    if (oldFkCategoriaOfEventoListNewEvento != null && !oldFkCategoriaOfEventoListNewEvento.equals(categoria)) {
                        oldFkCategoriaOfEventoListNewEvento.getEventoList().remove(eventoListNewEvento);
                        oldFkCategoriaOfEventoListNewEvento = em.merge(oldFkCategoriaOfEventoListNewEvento);
                    }
                }
            }
            for (Categoria categoriaListOldCategoria : categoriaListOld) {
                if (!categoriaListNew.contains(categoriaListOldCategoria)) {
                    categoriaListOldCategoria.setFkCategoria(null);
                    categoriaListOldCategoria = em.merge(categoriaListOldCategoria);
                }
            }
            for (Categoria categoriaListNewCategoria : categoriaListNew) {
                if (!categoriaListOld.contains(categoriaListNewCategoria)) {
                    Categoria oldFkCategoriaOfCategoriaListNewCategoria = categoriaListNewCategoria.getFkCategoria();
                    categoriaListNewCategoria.setFkCategoria(categoria);
                    categoriaListNewCategoria = em.merge(categoriaListNewCategoria);
                    if (oldFkCategoriaOfCategoriaListNewCategoria != null && !oldFkCategoriaOfCategoriaListNewCategoria.equals(categoria)) {
                        oldFkCategoriaOfCategoriaListNewCategoria.getCategoriaList().remove(categoriaListNewCategoria);
                        oldFkCategoriaOfCategoriaListNewCategoria = em.merge(oldFkCategoriaOfCategoriaListNewCategoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoria.getId();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
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
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Evento> eventoListOrphanCheck = categoria.getEventoList();
            for (Evento eventoListOrphanCheckEvento : eventoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the Evento " + eventoListOrphanCheckEvento + " in its eventoList field has a non-nullable fkCategoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categoria fkCategoria = categoria.getFkCategoria();
            if (fkCategoria != null) {
                fkCategoria.getCategoriaList().remove(categoria);
                fkCategoria = em.merge(fkCategoria);
            }
            List<Categoria> categoriaList = categoria.getCategoriaList();
            for (Categoria categoriaListCategoria : categoriaList) {
                categoriaListCategoria.setFkCategoria(null);
                categoriaListCategoria = em.merge(categoriaListCategoria);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Categoria as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Categoria findCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Categoria as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<String> readByCat(String cat){
        EntityManager em=emf.createEntityManager();
        List res=new ArrayList();
        res=em.createQuery("SELECT c.nombre FROM Categoria c WHERE c.fkCategoria.id=(SELECT d.id FROM Categoria d WHERE d.nombre=?1)").setParameter(1, cat).getResultList();
        return res;
    }
}

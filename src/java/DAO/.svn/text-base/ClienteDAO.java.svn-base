/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.NonexistentEntityException;
import entidad.Cliente;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import entidad.Registroventa;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UNAL
 */
public class ClienteDAO {

    public ClienteDAO() {
        emf = Persistence.createEntityManagerFactory("SIBOWebPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getRegistroventaList() == null) {
            cliente.setRegistroventaList(new ArrayList<Registroventa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Registroventa> attachedRegistroventaList = new ArrayList<Registroventa>();
            for (Registroventa registroventaListRegistroventaToAttach : cliente.getRegistroventaList()) {
                registroventaListRegistroventaToAttach = em.getReference(registroventaListRegistroventaToAttach.getClass(), registroventaListRegistroventaToAttach.getId());
                attachedRegistroventaList.add(registroventaListRegistroventaToAttach);
            }
            cliente.setRegistroventaList(attachedRegistroventaList);
            em.persist(cliente);
            for (Registroventa registroventaListRegistroventa : cliente.getRegistroventaList()) {
                Cliente oldFkClienteOfRegistroventaListRegistroventa = registroventaListRegistroventa.getFkCliente();
                registroventaListRegistroventa.setFkCliente(cliente);
                registroventaListRegistroventa = em.merge(registroventaListRegistroventa);
                if (oldFkClienteOfRegistroventaListRegistroventa != null) {
                    oldFkClienteOfRegistroventaListRegistroventa.getRegistroventaList().remove(registroventaListRegistroventa);
                    oldFkClienteOfRegistroventaListRegistroventa = em.merge(oldFkClienteOfRegistroventaListRegistroventa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            List<Registroventa> registroventaListOld = persistentCliente.getRegistroventaList();
            List<Registroventa> registroventaListNew = cliente.getRegistroventaList();
            List<Registroventa> attachedRegistroventaListNew = new ArrayList<Registroventa>();
            for (Registroventa registroventaListNewRegistroventaToAttach : registroventaListNew) {
                registroventaListNewRegistroventaToAttach = em.getReference(registroventaListNewRegistroventaToAttach.getClass(), registroventaListNewRegistroventaToAttach.getId());
                attachedRegistroventaListNew.add(registroventaListNewRegistroventaToAttach);
            }
            registroventaListNew = attachedRegistroventaListNew;
            cliente.setRegistroventaList(registroventaListNew);
            cliente = em.merge(cliente);
            for (Registroventa registroventaListOldRegistroventa : registroventaListOld) {
                if (!registroventaListNew.contains(registroventaListOldRegistroventa)) {
                    registroventaListOldRegistroventa.setFkCliente(null);
                    registroventaListOldRegistroventa = em.merge(registroventaListOldRegistroventa);
                }
            }
            for (Registroventa registroventaListNewRegistroventa : registroventaListNew) {
                if (!registroventaListOld.contains(registroventaListNewRegistroventa)) {
                    Cliente oldFkClienteOfRegistroventaListNewRegistroventa = registroventaListNewRegistroventa.getFkCliente();
                    registroventaListNewRegistroventa.setFkCliente(cliente);
                    registroventaListNewRegistroventa = em.merge(registroventaListNewRegistroventa);
                    if (oldFkClienteOfRegistroventaListNewRegistroventa != null && !oldFkClienteOfRegistroventaListNewRegistroventa.equals(cliente)) {
                        oldFkClienteOfRegistroventaListNewRegistroventa.getRegistroventaList().remove(registroventaListNewRegistroventa);
                        oldFkClienteOfRegistroventaListNewRegistroventa = em.merge(oldFkClienteOfRegistroventaListNewRegistroventa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<Registroventa> registroventaList = cliente.getRegistroventaList();
            for (Registroventa registroventaListRegistroventa : registroventaList) {
                registroventaListRegistroventa.setFkCliente(null);
                registroventaListRegistroventa = em.merge(registroventaListRegistroventa);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Cliente as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Cliente as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Cliente> readByUsername(String username){
        EntityManager em=emf.createEntityManager();
        List res=new ArrayList();
        res=em.createQuery("SELECT f FROM Cliente f WHERE f.usuario=?1").setParameter(1, username).getResultList();
        return res;
    }

    public List<Cliente> readByEmail(String email){
        EntityManager em=emf.createEntityManager();
        List res=new ArrayList();
        res=em.createQuery("SELECT f FROM Cliente f WHERE f.eMail=?1").setParameter(1, email).getResultList();
        return res;
    }
}

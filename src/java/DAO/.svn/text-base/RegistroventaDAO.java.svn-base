/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.NonexistentEntityException;
import entidad.Registroventa;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import entidad.Usuario;
import entidad.Cliente;
import entidad.Boleta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UNAL
 */
public class RegistroventaDAO {

    public RegistroventaDAO() {
        emf = Persistence.createEntityManagerFactory("SIBOWebPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Registroventa registroventa) {
        if (registroventa.getBoletaList() == null) {
            registroventa.setBoletaList(new ArrayList<Boleta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario fkUsuario = registroventa.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario = em.getReference(fkUsuario.getClass(), fkUsuario.getId());
                registroventa.setFkUsuario(fkUsuario);
            }
            Cliente fkCliente = registroventa.getFkCliente();
            if (fkCliente != null) {
                fkCliente = em.getReference(fkCliente.getClass(), fkCliente.getId());
                registroventa.setFkCliente(fkCliente);
            }
            List<Boleta> attachedBoletaList = new ArrayList<Boleta>();
            for (Boleta boletaListBoletaToAttach : registroventa.getBoletaList()) {
                boletaListBoletaToAttach = em.getReference(boletaListBoletaToAttach.getClass(), boletaListBoletaToAttach.getId());
                attachedBoletaList.add(boletaListBoletaToAttach);
            }
            registroventa.setBoletaList(attachedBoletaList);
            em.persist(registroventa);
            if (fkUsuario != null) {
                fkUsuario.getRegistroventaList().add(registroventa);
                fkUsuario = em.merge(fkUsuario);
            }
            if (fkCliente != null) {
                fkCliente.getRegistroventaList().add(registroventa);
                fkCliente = em.merge(fkCliente);
            }
            for (Boleta boletaListBoleta : registroventa.getBoletaList()) {
                Registroventa oldFkRegistroventaOfBoletaListBoleta = boletaListBoleta.getFkRegistroventa();
                boletaListBoleta.setFkRegistroventa(registroventa);
                boletaListBoleta = em.merge(boletaListBoleta);
                if (oldFkRegistroventaOfBoletaListBoleta != null) {
                    oldFkRegistroventaOfBoletaListBoleta.getBoletaList().remove(boletaListBoleta);
                    oldFkRegistroventaOfBoletaListBoleta = em.merge(oldFkRegistroventaOfBoletaListBoleta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Registroventa registroventa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Registroventa persistentRegistroventa = em.find(Registroventa.class, registroventa.getId());
            Usuario fkUsuarioOld = persistentRegistroventa.getFkUsuario();
            Usuario fkUsuarioNew = registroventa.getFkUsuario();
            Cliente fkClienteOld = persistentRegistroventa.getFkCliente();
            Cliente fkClienteNew = registroventa.getFkCliente();
            List<Boleta> boletaListOld = persistentRegistroventa.getBoletaList();
            List<Boleta> boletaListNew = registroventa.getBoletaList();
            if (fkUsuarioNew != null) {
                fkUsuarioNew = em.getReference(fkUsuarioNew.getClass(), fkUsuarioNew.getId());
                registroventa.setFkUsuario(fkUsuarioNew);
            }
            if (fkClienteNew != null) {
                fkClienteNew = em.getReference(fkClienteNew.getClass(), fkClienteNew.getId());
                registroventa.setFkCliente(fkClienteNew);
            }
            List<Boleta> attachedBoletaListNew = new ArrayList<Boleta>();
            for (Boleta boletaListNewBoletaToAttach : boletaListNew) {
                boletaListNewBoletaToAttach = em.getReference(boletaListNewBoletaToAttach.getClass(), boletaListNewBoletaToAttach.getId());
                attachedBoletaListNew.add(boletaListNewBoletaToAttach);
            }
            boletaListNew = attachedBoletaListNew;
            registroventa.setBoletaList(boletaListNew);
            registroventa = em.merge(registroventa);
            if (fkUsuarioOld != null && !fkUsuarioOld.equals(fkUsuarioNew)) {
                fkUsuarioOld.getRegistroventaList().remove(registroventa);
                fkUsuarioOld = em.merge(fkUsuarioOld);
            }
            if (fkUsuarioNew != null && !fkUsuarioNew.equals(fkUsuarioOld)) {
                fkUsuarioNew.getRegistroventaList().add(registroventa);
                fkUsuarioNew = em.merge(fkUsuarioNew);
            }
            if (fkClienteOld != null && !fkClienteOld.equals(fkClienteNew)) {
                fkClienteOld.getRegistroventaList().remove(registroventa);
                fkClienteOld = em.merge(fkClienteOld);
            }
            if (fkClienteNew != null && !fkClienteNew.equals(fkClienteOld)) {
                fkClienteNew.getRegistroventaList().add(registroventa);
                fkClienteNew = em.merge(fkClienteNew);
            }
            for (Boleta boletaListOldBoleta : boletaListOld) {
                if (!boletaListNew.contains(boletaListOldBoleta)) {
                    boletaListOldBoleta.setFkRegistroventa(null);
                    boletaListOldBoleta = em.merge(boletaListOldBoleta);
                }
            }
            for (Boleta boletaListNewBoleta : boletaListNew) {
                if (!boletaListOld.contains(boletaListNewBoleta)) {
                    Registroventa oldFkRegistroventaOfBoletaListNewBoleta = boletaListNewBoleta.getFkRegistroventa();
                    boletaListNewBoleta.setFkRegistroventa(registroventa);
                    boletaListNewBoleta = em.merge(boletaListNewBoleta);
                    if (oldFkRegistroventaOfBoletaListNewBoleta != null && !oldFkRegistroventaOfBoletaListNewBoleta.equals(registroventa)) {
                        oldFkRegistroventaOfBoletaListNewBoleta.getBoletaList().remove(boletaListNewBoleta);
                        oldFkRegistroventaOfBoletaListNewBoleta = em.merge(oldFkRegistroventaOfBoletaListNewBoleta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = registroventa.getId();
                if (findRegistroventa(id) == null) {
                    throw new NonexistentEntityException("The registroventa with id " + id + " no longer exists.");
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
            Registroventa registroventa;
            try {
                registroventa = em.getReference(Registroventa.class, id);
                registroventa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registroventa with id " + id + " no longer exists.", enfe);
            }
            Usuario fkUsuario = registroventa.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario.getRegistroventaList().remove(registroventa);
                fkUsuario = em.merge(fkUsuario);
            }
            Cliente fkCliente = registroventa.getFkCliente();
            if (fkCliente != null) {
                fkCliente.getRegistroventaList().remove(registroventa);
                fkCliente = em.merge(fkCliente);
            }
            List<Boleta> boletaList = registroventa.getBoletaList();
            for (Boleta boletaListBoleta : boletaList) {
                boletaListBoleta.setFkRegistroventa(null);
                boletaListBoleta = em.merge(boletaListBoleta);
            }
            em.remove(registroventa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Registroventa> findRegistroventaEntities() {
        return findRegistroventaEntities(true, -1, -1);
    }

    public List<Registroventa> findRegistroventaEntities(int maxResults, int firstResult) {
        return findRegistroventaEntities(false, maxResults, firstResult);
    }

    private List<Registroventa> findRegistroventaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Registroventa as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Registroventa findRegistroventa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Registroventa.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroventaCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Registroventa as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

package org.tgn.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.tgn.model.Medicine;

/**
 * DAO for Medicine
 */
@Stateless
public class MedicineDao
{
   @PersistenceContext(unitName = "tgn-services-persistence-unit")
   private EntityManager em;

   public void create(Medicine entity)
   {
      em.persist(entity);
   }

   public void deleteById(Long id)
   {
      Medicine entity = em.find(Medicine.class, id);
      if (entity != null)
      {
         em.remove(entity);
      }
   }

   public Medicine findById(Long id)
   {
      return em.find(Medicine.class, id);
   }

   public Medicine findByUpc(String upc)
   {
      TypedQuery<Medicine> findAllQuery = em.createQuery(
               "SELECT DISTINCT m FROM Medicine m ORDER BY m.upc", Medicine.class);
      return findAllQuery.getSingleResult();
   }

   public Medicine update(Medicine entity)
   {
      return em.merge(entity);
   }

   public List<Medicine> listAll(Integer startPosition, Integer maxResult)
   {
      TypedQuery<Medicine> findAllQuery = em.createQuery(
               "SELECT DISTINCT m FROM Medicine m ORDER BY m.id",
               Medicine.class);
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      return findAllQuery.getResultList();
   }
}

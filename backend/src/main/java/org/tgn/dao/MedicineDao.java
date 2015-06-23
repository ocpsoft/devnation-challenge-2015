package org.tgn.dao;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
               "SELECT DISTINCT m FROM Medicine m WHERE m.upc = :upc", Medicine.class);
      Medicine result = new Medicine();
      try
      {
         findAllQuery.setParameter("upc", upc);
         result = findAllQuery.getSingleResult();
      }
      catch (NoResultException e)
      {
         result.setUpc(upc);
         result.setName(UUID.randomUUID().toString());
         result.setPointValue(getRealPoints());
         // try
         // {
         // URLConnection connection = new URL(
         // "http://api.upcdatabase.org/json/1199a00f710cd4b3f0e79be87e0bae10/" + upc).openConnection();
         // Object content = connection.getContent();
         // result.setName(upc);
         // }
         // catch (Exception e2)
         // {
         // // dont care
         // }
         update(result);
      }
      return result;
   }

   private int getRealPoints()
   {
      Random rand = new Random();
      int result = rand.nextInt((30 - 5) + 1) + 5;
      return result;
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

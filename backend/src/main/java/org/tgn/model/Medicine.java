package org.tgn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Medicine implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @Column
   private String upc;

   @Column
   private String name;

   @Column
   private int pointValue;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   public String getUpc()
   {
      return upc;
   }

   public void setUpc(String upc)
   {
      this.upc = upc;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public int getPointValue()
   {
      return pointValue;
   }

   public void setPointValue(int points)
   {
      this.pointValue = points;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + pointValue;
      result = prime * result + ((upc == null) ? 0 : upc.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Medicine other = (Medicine) obj;
      if (name == null)
      {
         if (other.name != null)
            return false;
      }
      else if (!name.equals(other.name))
         return false;
      if (pointValue != other.pointValue)
         return false;
      if (upc == null)
      {
         if (other.upc != null)
            return false;
      }
      else if (!upc.equals(other.upc))
         return false;
      return true;
   }

}
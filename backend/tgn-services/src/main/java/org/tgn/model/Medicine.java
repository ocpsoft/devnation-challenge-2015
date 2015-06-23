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
   private String pointValue;

   @Column
   private String name;

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

   public String getPointValue()
   {
      return pointValue;
   }

   public void setPointValue(String pointValue)
   {
      this.pointValue = pointValue;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (!(obj instanceof Medicine))
      {
         return false;
      }
      Medicine other = (Medicine) obj;
      if (id != null)
      {
         if (!id.equals(other.id))
         {
            return false;
         }
      }
      return true;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (upc != null && !upc.trim().isEmpty())
         result += "upc: " + upc;
      if (pointValue != null && !pointValue.trim().isEmpty())
         result += ", pointValue: " + pointValue;
      if (name != null && !name.trim().isEmpty())
         result += ", name: " + name;
      return result;
   }
}
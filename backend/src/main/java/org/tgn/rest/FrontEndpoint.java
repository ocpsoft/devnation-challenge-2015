package org.tgn.rest;

import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.tgn.dao.MedicineDao;
import org.tgn.dao.UserDao;
import org.tgn.model.Medicine;
import org.tgn.model.User;

@Path("/frontend")
public class FrontEndpoint
{
   @Inject
   private MedicineDao medicineDao;
   @Inject
   private UserDao userDao;

   @GET
   @Path("/lookupUpc")
   @Produces({ "application/json" })
   public Medicine lookupUPC(@QueryParam("upc") String upc)
   {
      Medicine result;
      try
      {
         result = medicineDao.findByUpc(upc);
      }
      catch (NoResultException e)
      {
         result = new Medicine();
         result.setUpc(upc);
         result.setName(UUID.randomUUID().toString());
         result.setPointValue((int) Math.random());
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
         medicineDao.update(result);
      }
      return result;
   }

   @GET
   @Path("/checkPoints")
   @Produces({ "application/json" })
   public Response checkPoints(@QueryParam("auth") String username)
   {
      User user = userDao.findByUsername(username);
      String json = "{ \"points\" : \"" + user.getPoints() + "\"}";
      return Response.ok(json, MediaType.APPLICATION_JSON).build();
   }

   @GET
   @Path("/sendMedicine")
   @Produces({ "application/json" })
   public Response sendMedicineRequest(@QueryParam("auth") String username, @QueryParam("upc") String upc,
            @QueryParam("quantity") int quantity)
   {
      lookupUPC(upc);
      return Response.ok().build();
   }
}
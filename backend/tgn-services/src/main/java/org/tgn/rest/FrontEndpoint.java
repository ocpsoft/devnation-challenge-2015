package org.tgn.rest;

import javax.inject.Inject;
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
      return medicineDao.findByUpc(upc);
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
      return Response.ok().build();
   }
}
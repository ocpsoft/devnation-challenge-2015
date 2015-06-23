package org.tgn.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.tgn.dao.MedicineDao;
import org.tgn.dao.UserDao;
import org.tgn.model.User;

@Path("/backend")
public class BackEndpoint
{
   @Inject
   private MedicineDao medicineDao;
   @Inject
   private UserDao userDao;

   @GET
   @Path("awardPoints")
   public Response awardPoints(@QueryParam("auth") String username, @QueryParam("points") int points)
   {
      User user = userDao.findByUsername(username);
      user.setPoints(user.getPoints() + points);
      userDao.update(user);
      return Response.ok().build();
   }

   @GET
   @Path("denyRequest")
   public Response denyRequest(@QueryParam("auth") String username, @QueryParam("upc") String upc,
            @QueryParam("quantity") int quantity)
   {
      return Response.ok().build();
   }
}
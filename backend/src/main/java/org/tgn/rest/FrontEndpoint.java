package org.tgn.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.services.client.api.RemoteRestRuntimeEngineFactoryBuilderImpl;
import org.kie.services.client.api.RemoteRuntimeEngineFactory;
import org.kie.services.client.api.command.RemoteRuntimeEngine;
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
        Medicine medicine = lookupUPC(upc);
      
		URL deploymentUrl = null;
		try {
			deploymentUrl = new URL("http://dcbpms-lincolnbaxter.rhcloud.com/business-central");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		String deploymentId = "org.tgn:tgn-kjar:1.0.0";
		String user = "bpm-admin";
		String password = "wN-TngsS4PSX";
		RemoteRuntimeEngineFactory restSessionFactory = RemoteRestRuntimeEngineFactoryBuilderImpl.newBuilder()
				.addDeploymentId(deploymentId)
				.addUrl(deploymentUrl)
				.addUserName(user)
				.addPassword(password)
				.build();
		RemoteRuntimeEngine engine = restSessionFactory.newRuntimeEngine();
		KieSession ksession = engine.getKieSession();
		Map<String, Object> params = new HashMap<String, Object>(3);
		params.put("UPC", upc);
		params.put("quantity", quantity);
		params.put("name", medicine.getName());
		params.put("points", medicine.getPointValue());
		params.put("endpoint", "http://dcbackend-lincolnbaxter.rhcloud.com/rest/backend");
		params.put("user_auth", username);
		
		ProcessInstance pi =  ksession.startProcess("tgn-kjar.MedicineApproval", params);
      
        return Response.ok().build();
   }
}
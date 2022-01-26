package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import beans.Address;
import beans.Location;
import beans.Manager;
import beans.Restaurant;
import beans.RestaurantType;
import dao.impl.BuyerDAOImpl;
import dao.impl.ManagerDAOImpl;
import dao.impl.RestaurantDAOImpl;
import dao.impl.UserDAOImpl;
import dto.RestaurantDTO;

@Path("restaurant")
public class RestaurantService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	public RestaurantService() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("restaurantDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("restaurantDAO", new RestaurantDAOImpl(contextPath));
		}
		if(ctx.getAttribute("managerDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("managerDAO", new ManagerDAOImpl(contextPath));
		}
	}
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response createRestaurant(
			@FormDataParam("manager") String manager,
			@FormDataParam("resName") String resName,
			@FormDataParam("location") String location,
			@FormDataParam("type") String type,
			@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition cdh) throws URISyntaxException {
		//RestaurantDTO dto = jsonPart.getValueAs(RestaurantDTO.class);
		String uploadedFileLocation2 = ctx.getRealPath("/data/images");
		
		//System.out.println(uploadedFileLocation);
		System.out.println(uploadedFileLocation2);
		String ext = FilenameUtils.getExtension(cdh.getFileName());
		String fileName = UUID.randomUUID() + "." + ext;
		String uploadedFileLocation = ctx.getRealPath("/data/images/")+fileName;
		String imgPath = "http://localhost:8082/web/data/images/" + fileName;
		System.out.println(imgPath);
//		Location loc = parse(location);
		Location loc = new Location();
		Address add = new Address();
		add.setCity(location);
		loc.setAddress(add);
		RestaurantType t = getType(type);
		RestaurantDAOImpl restaurantDAO = (RestaurantDAOImpl) ctx.getAttribute("restaurantDAO");
		Restaurant restaurant = new Restaurant(resName,t, true, loc, imgPath);
		ManagerDAOImpl managerDAO = (ManagerDAOImpl) ctx.getAttribute("managerDAO");
		Manager man = managerDAO.findById(manager);
		man.setRestaurant(restaurant);
		boolean done = restaurantDAO.add(restaurant);
		managerDAO.update(man);
		if(done)
			writeToFile(fileInputStream, uploadedFileLocation);
		
		
		return Response.ok().entity("Restaurant created").build();
	}
	
	@GET
	@Path("/getRestaurants")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Restaurant> getRestaurants(){
		RestaurantDAOImpl resDAO = (RestaurantDAOImpl) ctx.getAttribute("restaurantDAO");
		Collection<Restaurant> restaurants = resDAO.findAll();
		Collection<Restaurant> working = new ArrayList<Restaurant>();
		Collection<Restaurant> closed = new ArrayList<Restaurant>();
		Collection<Restaurant> all = new ArrayList<Restaurant>();
		for(Restaurant r: restaurants) {
			if(r.getStatus()) {
				working.add(r);
			}
			else {
				closed.add(r);
			}
		}
		all.addAll(working);
		all.addAll(closed);
		return all;
	}
	
	
	@GET
	@Path("/getById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant getById(@PathParam("id") String id ){
		RestaurantDAOImpl resDAO = (RestaurantDAOImpl) ctx.getAttribute("restaurantDAO");
		Restaurant res = resDAO.findById(id);
		
		return res;
	}
	
	@GET
	@Path("/getClosedRestaurants")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Restaurant> getClosedRestaurants(){
		RestaurantDAOImpl resDAO = (RestaurantDAOImpl) ctx.getAttribute("restaurantDAO");
		Collection<Restaurant> restaurants = resDAO.findAll();
		Collection<Restaurant> closed = new ArrayList<Restaurant>();
		for(Restaurant r: restaurants) {
			if(!r.getStatus()) {
				closed.add(r);
			}
		}
		return closed;
	}
	
	@GET
	@Path("/getManagers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<String> getManagers(){
		ManagerDAOImpl managerDAO = (ManagerDAOImpl) ctx.getAttribute("managerDAO");
		Collection<Manager> managers = managerDAO.findAll();
		Collection<String> usernames = new ArrayList<String>();
		for(Manager m : managers) {
			System.out.println(m.getRestaurant());
//			System.out.println(m.getUsername());
			System.out.println(m.getRestaurant().getId() != null);
			System.out.println(m.getRestaurant().getId());
			if(m.getRestaurant().getId() == null) {
				System.out.println(m.getUsername());
				usernames.add(m.getUsername());
			}
		}
		
		return usernames;
	}
	
	private RestaurantType getType(String type) {
		if(type.equals("ITALIAN"))
			return RestaurantType.ITALIAN;
		else if(type.equals("CHINESE"))
			return RestaurantType.CHINESE;
		else if(type.equals("MEXICAN"))
			return RestaurantType.MEXICAN;
		else if(type.equals("BARBEQUE"))
			return RestaurantType.BARBEQUE;
		else if(type.equals("FAST FOOD"))
			return RestaurantType.FAST_FOOD;
		
		return RestaurantType.PIZZA;
		
	}
	
	private Location parse(String location) {
		Address add = new Address();
		Location loc = new Location();
		String[] parts = location.split(",");
		add.setStreetName(parts[0]);
		add.setNumber(parts[1]);
		add.setCity(parts[2]);
		add.setZipCode(parts[3]);
		loc.setAddress(add);
		return loc;
	}
	
	private void writeToFile(InputStream uploadedInputStream,
            String uploadedFileLocation) {
		try {
		OutputStream out = new FileOutputStream(new File(
		     uploadedFileLocation));
		int read = 0;
		byte[] bytes = new byte[1024];
		
		out = new FileOutputStream(new File(uploadedFileLocation));
		while ((read = uploadedInputStream.read(bytes)) != -1) {
		  out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	
}

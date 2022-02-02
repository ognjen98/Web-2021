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
import beans.Article;
import beans.ArticleType;
import beans.Location;
import beans.Manager;
import beans.QuantityType;
import beans.Restaurant;
import beans.RestaurantStatus;
import beans.RestaurantType;
import dao.impl.ArticleDAOImpl;
import dao.impl.BuyerDAOImpl;
import dao.impl.ManagerDAOImpl;
import dao.impl.RestaurantDAOImpl;
import dao.impl.UserDAOImpl;
import dto.RestaurantDTO;
import dto.SearchDTO;

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
		if(ctx.getAttribute("articleDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("articleDAO", new ArticleDAOImpl(contextPath));
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
		Location loc = parse(location);
//		Location loc = new Location();
//		Address add = new Address();
//		add.setCity(location);
//		loc.setAddress(add);
		RestaurantType t = getType(type);
		RestaurantDAOImpl restaurantDAO = (RestaurantDAOImpl) ctx.getAttribute("restaurantDAO");
		Restaurant restaurant = new Restaurant(resName,t, RestaurantStatus.OPENED, loc, imgPath, 0.0);
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
			if(r.getStatus().equals(RestaurantStatus.OPENED)) {
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
	
	
	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Restaurant> search(SearchDTO dto){
		RestaurantDAOImpl resDAO = (RestaurantDAOImpl) ctx.getAttribute("restaurantDAO");
		Collection<Restaurant> restaurants = resDAO.findAll();
		Collection<Restaurant> working = new ArrayList<Restaurant>();
		Collection<Restaurant> closed = new ArrayList<Restaurant>();
		Collection<Restaurant> all = new ArrayList<Restaurant>();
		Collection<Restaurant> searched = new ArrayList<Restaurant>();
		for(Restaurant res: new ArrayList<Restaurant>(restaurants)) {
			if(!dto.getName().equals("") && !res.getName().toLowerCase().contains(dto.getName().toLowerCase())) {
				restaurants.remove(res);
			}
			if(!dto.getLocation().equals("") && !res.getLocation().getAddress().getCity().toLowerCase().contains(dto.getLocation().toLowerCase())) {
				restaurants.remove(res);
			}
			if(!dto.getType().equals("") && !res.getType().toString().toLowerCase().contains(dto.getType().toLowerCase())) {
				restaurants.remove(res);
			}
			if(!dto.getStatus().equals("") && !res.getStatus().toString().toLowerCase().contains(dto.getStatus().toLowerCase())) {
				restaurants.remove(res);
			}
			if(!dto.getGrade().equals("") && res.getGrade() != Double.parseDouble(dto.getGrade())) {
				restaurants.remove(res);
			}
		}
		
		
		for(Restaurant r: restaurants) {
			if(r.getStatus().equals(RestaurantStatus.OPENED)) {
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
	
	
	
	@POST
	@Path("/addArticle")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addArticle(
			@FormDataParam("resId") String resId,
			@FormDataParam("artName") String artName,
			@FormDataParam("price") Double price,
			@FormDataParam("description") String description,
			@FormDataParam("quantity") String quantity,
			@FormDataParam("artType") String artType,
			@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition cdh) throws URISyntaxException{
            	
String uploadedFileLocation2 = ctx.getRealPath("/data/images");
		
		//System.out.println(uploadedFileLocation);
		System.out.println(uploadedFileLocation2);
		String ext = FilenameUtils.getExtension(cdh.getFileName());
		String fileName = UUID.randomUUID() + "." + ext;
		String uploadedFileLocation = ctx.getRealPath("/data/images/")+fileName;
		String imgPath = "http://localhost:8082/web/data/images/" + fileName;
		
		ArticleDAOImpl artDAO = (ArticleDAOImpl) ctx.getAttribute("articleDAO");
		RestaurantDAOImpl resDAO = (RestaurantDAOImpl) ctx.getAttribute("restaurantDAO");
		Restaurant res = resDAO.findById(resId);
		Restaurant restaurant = new Restaurant(res.getId(), res.getName(),res.getType(),res.getStatus(),res.getLocation(),res.getImage(), res.getGrade());
		ArticleType type = checkArticleType(artType);
		QuantityType qua = checkQuantity(quantity);
		Article art = new Article(artName, price, type, qua, description, imgPath);

		List<Article> articles= res.getArticles();
		articles.add(art);
		res.setArticles(articles);
		resDAO.update(res);
		art.setRestaurant(restaurant);
		boolean done = artDAO.add(art);
		
		
		if(done) {
			writeToFile(fileInputStream, uploadedFileLocation);
			
		}
		
		return Response.ok().entity("Article created").build();
    }
	
	
	private ArticleType checkArticleType(String type) {
		
		if(type.equalsIgnoreCase("food"))
			return ArticleType.FOOD;
		else if(type.equalsIgnoreCase("drink"))
			return ArticleType.DRINK;
		else
			return null;
	}
	
private QuantityType checkQuantity(String type) {
		
		if(type.equalsIgnoreCase("gr"))
			return QuantityType.GR;
		else if(type.equalsIgnoreCase("ml"))
			return QuantityType.ML;
		else
			return null;
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
	@Path("/getArticlesByRestaurant/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Article> getArticlesByRestaurant(@PathParam("id") String id ){
		RestaurantDAOImpl resDAO = (RestaurantDAOImpl) ctx.getAttribute("restaurantDAO");
		Restaurant res = resDAO.findById(id);
		List<Article> arts = res.getArticles();
		return arts;
	}
	
	
	
	@GET
	@Path("/getByManager/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant getByManager(@PathParam("username") String username ){
		RestaurantDAOImpl resDAO = (RestaurantDAOImpl) ctx.getAttribute("restaurantDAO");
		ManagerDAOImpl manDAO = (ManagerDAOImpl) ctx.getAttribute("managerDAO");
		Manager man = manDAO.findByUsername(username);
		Restaurant res = man.getRestaurant();
		
		return res;
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
		add.setStreetName(parts[0].trim());
		add.setNumber(parts[1].trim());
		add.setCity(parts[2].trim());
		add.setZipCode(parts[3].trim());
		loc.setAddress(add);
		loc.setLatitude(Double.parseDouble(parts[4]));
		loc.setLongitude(Double.parseDouble(parts[5]));
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

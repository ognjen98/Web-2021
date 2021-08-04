package service;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Buyer;
import beans.Role;
import beans.User;
import dao.impl.BuyerDAOImpl;
import dao.impl.UserDAOImpl;

@Path("/registration")
public class RegistrationService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	public RegistrationService() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("userDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDAO", new UserDAOImpl(contextPath));
		}
		if(ctx.getAttribute("buyerDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("buyerDAO", new BuyerDAOImpl(contextPath));
		}
	}
	
	@POST
	@Path("/reg")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(User user) {
		UserDAOImpl userDAO = (UserDAOImpl) ctx.getAttribute("userDAO");
		if(existsByUsername(user.getUsername())) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Korisnicko ime mora biti jedinstveno").build();
		}
		user.setRole(Role.BUYER);
		Buyer buyer = new Buyer(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getGender(), user.getRole());
		BuyerDAOImpl buyerDAO = (BuyerDAOImpl) ctx.getAttribute("buyerDAO");
		buyerDAO.add(buyer);
		userDAO.add(user);
		request.getSession().setAttribute("loggedInUser", buyer);
			
		return Response.status(Response.Status.OK).entity("Successfully registered").build();
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAll(){
		UserDAOImpl dao = (UserDAOImpl) ctx.getAttribute("userDAO");
		return dao.findAll();
	}
	
	
	private boolean existsByUsername(String username) {
		UserDAOImpl userDAO = (UserDAOImpl) ctx.getAttribute("userDAO");
		return userDAO.existsByUsername(username);
	}
	
}

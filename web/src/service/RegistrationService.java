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
import beans.BuyerType;
import beans.Manager;
import beans.Role;
import beans.Supplier;
import beans.TypeName;
import beans.User;
import dao.impl.BuyerDAOImpl;
import dao.impl.ManagerDAOImpl;
import dao.impl.SupplierDAOImpl;
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
		if(ctx.getAttribute("managerDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("managerDAO", new ManagerDAOImpl(contextPath));
		}
		if(ctx.getAttribute("supplierDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("supplierDAO", new SupplierDAOImpl(contextPath));
		}
	}
	
	@POST
	@Path("/reg")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(User user) {
		UserDAOImpl userDAO = (UserDAOImpl) ctx.getAttribute("userDAO");
		if(existsByUsername(user.getUsername())) {
			return Response.status(Response.Status.BAD_REQUEST).entity("User already exists").build();
		}
		user.setRole(Role.BUYER);
		userDAO.add(user);
		BuyerType bt = new BuyerType(TypeName.BRONZE, 0.0, 0);
		Buyer buyer = new Buyer(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getGender(), user.getDateOfBirth(), user.getRole(), 0.0, bt);
		BuyerDAOImpl buyerDAO = (BuyerDAOImpl) ctx.getAttribute("buyerDAO");
		buyerDAO.add(buyer);
		
		request.getSession().setAttribute("loggedInUser", buyer);
			
		return Response.status(Response.Status.OK).entity("Successfully registered").build();
	}
	
	@POST
	@Path("/createManagerSupplier")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createManagerSupplier(User user) {
		UserDAOImpl userDAO = (UserDAOImpl) ctx.getAttribute("userDAO");
		if(existsByUsername(user.getUsername())) {
			return Response.status(Response.Status.BAD_REQUEST).entity("User already exists").build();
		}
		
		if(user.getRole().equals(Role.MANAGER)) {
			Manager manager = new Manager(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getGender(), user.getDateOfBirth(), user.getRole());
			ManagerDAOImpl managerDAO = (ManagerDAOImpl) ctx.getAttribute("managerDAO");
			managerDAO.add(manager);
			userDAO.add(user);
			
				
			return Response.status(Response.Status.OK).entity("Manager successfully registered").build();
		}
		else if(user.getRole().equals(Role.SUPPLIER)) {
			Supplier supplier = new Supplier(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getGender(), user.getDateOfBirth(), user.getRole());
			SupplierDAOImpl supplierDAO = (SupplierDAOImpl) ctx.getAttribute("supplierDAO");
			supplierDAO.add(supplier);
			userDAO.add(user);
			
				
			return Response.status(Response.Status.OK).entity("Supplier successfully registered").build();
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("Couldn't create manager or supplier!").build();
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

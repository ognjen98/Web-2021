package service;



import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Buyer;
import beans.Manager;
import beans.Role;
import beans.Supplier;
import beans.User;
import dao.impl.BuyerDAOImpl;
import dao.impl.ManagerDAOImpl;
import dao.impl.SupplierDAOImpl;
import dao.impl.UserDAOImpl;
import dto.LoginDTO;

@Path("/login")
public class LoginService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	public LoginService() {}
	
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
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response login(LoginDTO loginDTO) {

		if (!userExists(loginDTO.getUsername())) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid username or password").build();

		}

		UserDAOImpl userDAO = (UserDAOImpl) ctx.getAttribute("userDAO");
		User user = userDAO.findByUsername(loginDTO.getUsername());

		if (!isPasswordMatch(user.getPassword(), loginDTO.getPassword())) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid username or password").build();
		}

		if (user.getRole() == Role.BUYER) {
			BuyerDAOImpl buyerDAO = (BuyerDAOImpl) ctx.getAttribute("buyerDAO");
			//NewCookie cookie = new NewCookie("name", "123");
			Buyer buyer = buyerDAO.findByUsername(loginDTO.getUsername());
			request.getSession(true).setAttribute("loggedInUser", buyer);

			return Response.ok().entity("buyer.html").build(); // treba proslediti pocetnu stranicu odgovarajuceg
																	// korisnika
		} 
		else if (user.getRole() == Role.ADMIN) {

			request.getSession().setAttribute("loggedInUser", user);
			return Response.ok().entity("admin.html").build();
		} 
		else if (user.getRole() == Role.MANAGER) {

			ManagerDAOImpl managerDAO = (ManagerDAOImpl) ctx.getAttribute("managerDAO");
			//NewCookie cookie = new NewCookie("name", "123");
			Manager manager = managerDAO.findByUsername(loginDTO.getUsername());
			
			request.getSession().setAttribute("loggedInUser", manager);
			return Response.ok().entity("manager.html").build();
		}
		else if (user.getRole() == Role.SUPPLIER) {

			SupplierDAOImpl supplierDAO = (SupplierDAOImpl) ctx.getAttribute("supplierDAO");
			//NewCookie cookie = new NewCookie("name", "123");
			Supplier supplier = supplierDAO.findByUsername(loginDTO.getUsername());
			
			request.getSession().setAttribute("loggedInUser", supplier);
			return Response.ok().entity("supplier.html").build();
		}
		return null;
	}
	
	@GET
	@Path("/logout")
	@Produces(MediaType.TEXT_HTML)
	public Response logout() {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("loggedInUser");
		if (session != null && session.getAttribute("loggedInUser") != null) {
			session.invalidate();
		}
		return Response.ok().entity("index.html").build();
	}

	@GET
	@Path("/loggedIn/user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isUserLoggedIn() {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");
		if (session != null && session.getAttribute("loggedInUser") != null) {
			return Response.ok().build();
		}
		String message = "{\"href\": \"errors/forbidden.html\"}";
		return Response.status(Response.Status.FORBIDDEN).entity(message).build();
	}

	private boolean userExists(String username) {
		UserDAOImpl dao = (UserDAOImpl) ctx.getAttribute("userDAO");
		return dao.existsByUsername(username);
	}

	private boolean isPasswordMatch(String userPassword, String loginDTOPassword) {
		return userPassword.toLowerCase().equals(loginDTOPassword.toLowerCase());
	}

	@GET
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	public User getLoggedInUser() {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");
		if (session != null && session.getAttribute("loggedInUser") != null) {
			return user;
		}
		return null;
	}
	
	
	@GET
	@Path("/loggedIn/admin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isAdminLoggedIn() {
		System.out.println("Usao");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");
		if (session != null && session.getAttribute("loggedInUser") != null && user.getRole() == Role.ADMIN) {
			return Response.ok().build();
		}
		String message = "{\"href\": \"errors/forbidden.html\"}";
		return Response.status(Response.Status.FORBIDDEN).entity(message).build();
	}
}

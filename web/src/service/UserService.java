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
import beans.Gender;
import beans.User;
import dao.impl.BuyerDAOImpl;
import dao.impl.UserDAOImpl;
import dto.GetInfoDTO;

@Path("user")
public class UserService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	public UserService() {
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
	
	@GET
	@Path("getInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public GetInfoDTO getPersonalInfo() {
		User u = getLoggedInUser();
		GetInfoDTO dto = new GetInfoDTO();
		dto.setUsername(u.getUsername());
		dto.setName(u.getName());
		dto.setSurname(u.getSurname());
		dto.setGender(u.getGender().toString());
		
		return dto;
	}
	
	@POST
	@Path("changeInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response changeInfo(GetInfoDTO dto) {
		
		UserDAOImpl userDAO = (UserDAOImpl) ctx.getAttribute("userDAO");
		BuyerDAOImpl buyerDAO = (BuyerDAOImpl) ctx.getAttribute("buyerDAO");
		User loggedUser = getLoggedInUser();
		System.out.println(loggedUser.getId());
		User u = userDAO.findById(loggedUser.getId().toString());
		Buyer b = buyerDAO.findById(loggedUser.getId().toString());
		System.out.println();
		if(userDAO.existsByUsername(dto.getUsername())) {
			if(dto.getUsername().equals(u.getUsername())) {
				u = setLoggedInUser(u, dto);
				b = (Buyer) setLoggedInUser(b, dto);
				userDAO.update(u);
				buyerDAO.update(b);
				return  Response.ok().entity("Personal info successfully changed").build();
			}
			return Response.status(Response.Status.BAD_REQUEST).entity("User with that username already exists!").build();
		}
		u = setLoggedInUser(u, dto);
		b = (Buyer) setLoggedInUser(b, dto);
		userDAO.update(u);
		buyerDAO.update(b);
		return  Response.ok().entity("Personal info successfully changed").build();
		
	}
	
	
	public User setLoggedInUser(User loggedUser, GetInfoDTO dto) {
		loggedUser.setUsername(dto.getUsername());
		loggedUser.setName(dto.getName());
		loggedUser.setSurname(dto.getSurname());
		if(dto.getGender().equals("1"))
			loggedUser.setGender(Gender.MALE);
		else if(dto.getGender().equals("2"))
			loggedUser.setGender(Gender.FEMALE);
		else
			loggedUser.setGender(Gender.OTHER);
		
		return loggedUser;
	}
	
	public User getLoggedInUser() {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");
		if (session != null && session.getAttribute("loggedInUser") != null) {
			return user;
		}
		return null;
	}
}

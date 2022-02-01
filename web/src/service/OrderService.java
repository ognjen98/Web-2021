package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Article;
import beans.ArticleType;
import beans.Basket;
import beans.BasketItem;
import beans.Buyer;
import beans.BuyerType;
import beans.Manager;
import beans.Order;
import beans.OrderStatus;
import beans.QuantityType;
import beans.Restaurant;
import beans.RestaurantStatus;
import beans.Role;
import beans.Supplier;
import beans.TypeName;
import beans.User;
import dao.impl.ArticleDAOImpl;
import dao.impl.BasketDAOImpl;
import dao.impl.BuyerDAOImpl;
import dao.impl.ManagerDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.RestaurantDAOImpl;
import dao.impl.SupplierDAOImpl;
import dao.impl.UserDAOImpl;
import dto.BasketDTO;
import dto.BasketItemDTO;
import dto.OrderDTO;

@Path("order")
public class OrderService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	public OrderService() {
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
		if(ctx.getAttribute("userDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDAO", new UserDAOImpl(contextPath));
		}
		if(ctx.getAttribute("managerDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("managerDAO", new ManagerDAOImpl(contextPath));
		}
		if(ctx.getAttribute("buyerDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("buyerDAO", new BuyerDAOImpl(contextPath));
		}
		if(ctx.getAttribute("orderDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("orderDAO", new OrderDAOImpl(contextPath));
		}
		if(ctx.getAttribute("basketDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("basketDAO", new BasketDAOImpl(contextPath));
		}
		if(ctx.getAttribute("supDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("supDAO", new SupplierDAOImpl(contextPath));
		}
	}
	
	
	@POST
	@Path("/createBasket")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createBasket(BasketDTO dto) {
		BasketDAOImpl baskDAO = (BasketDAOImpl) ctx.getAttribute("basketDAO");
		
		List<BasketItem> bil = new ArrayList<BasketItem>();
		double price = 0;
		for(BasketItemDTO item : dto.getItems()) {
			ArticleType at = checkArticleType(item.getType());
			QuantityType qt = checkQuantity(item.getqType());
			Article a = new Article(item.getName(),item.getPrice(),at, qt, item.getDescription(), item.getImage());
			BasketItem bi = new BasketItem(a, item.getQuantity());
			bil.add(bi);
			price += item.getPrice() * item.getQuantity();
		}
		
		BuyerDAOImpl buyDAO = (BuyerDAOImpl) ctx.getAttribute("buyerDAO");
		Buyer b = buyDAO.findByUsername(dto.getBuyer());
		Basket bask = new Basket(bil, b, price, dto.getResId());
		
		baskDAO.add(bask);
		return Response.ok().entity("Basket created").build();
		
	}
	
	
	
	@POST
	@Path("/createOrder")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrder(OrderDTO dto) {
		OrderDAOImpl ordDAO = (OrderDAOImpl) ctx.getAttribute("orderDAO");
		BasketDAOImpl baskDAO = (BasketDAOImpl) ctx.getAttribute("basketDAO");
		baskDAO.deleteById("1");
		RestaurantDAOImpl resDAO = (RestaurantDAOImpl) ctx.getAttribute("restaurantDAO");
		Restaurant res = resDAO.findById(dto.getRes());
		if(res.getStatus().equals(RestaurantStatus.CLOSED)) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Restoran je zatvoren").build();
		}
		List<BasketItem> bil = new ArrayList<BasketItem>();

		for(BasketItemDTO item : dto.getArticles()) {
			ArticleType at = checkArticleType(item.getType());
			QuantityType qt = checkQuantity(item.getqType());
			Article a = new Article(item.getName(),item.getPrice(),at, qt, item.getDescription(), item.getImage());
			BasketItem bi = new BasketItem(a, item.getQuantity());
			bil.add(bi);
			
		}
		String uniqueId = generateRandomString();
		BuyerDAOImpl buyDAO = (BuyerDAOImpl) ctx.getAttribute("buyerDAO");
		
		
		Buyer b = buyDAO.findByUsername(dto.getBuyer());
		Double points = b.getPoints();
		points += dto.getPrice()/1000 * 133;
		b.setPoints(points);
		if(points > 3000 && points <= 4000) {
			BuyerType bt = new BuyerType(TypeName.SILVER, 0.03, 3001);
			b.setBuyerType(bt);
		}
		else if(points > 4000) {
			BuyerType bt = new BuyerType(TypeName.GOLD, 0.05, 4001);
			b.setBuyerType(bt);
		}
		
		Buyer buy = new Buyer(b.getUsername(), b.getPassword(), b.getName(), b.getSurname(), b.getGender(), b.getDateOfBirth(), b.getRole(), b.getPoints(), b.getBuyerType());
		Order o = new Order(uniqueId, bil, res, new Date(), dto.getPrice(), buy, OrderStatus.PROCESSING);
		
		List<Order> lbi = b.getOrders();
		lbi.add(o);
		b.setOrders(lbi);
		buyDAO.update(b);
		
		
		
		ordDAO.add(o);
		
		return Response.ok().entity("Order created").build();
		
	}
	
	
	
	@GET
	@Path("getOrders/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getOrders(@PathParam("username") String username) {
		OrderDAOImpl orderDAO = (OrderDAOImpl) ctx.getAttribute("orderDAO");
		UserDAOImpl userDAO = (UserDAOImpl) ctx.getAttribute("userDAO");
		User u = userDAO.findByUsername(username);
		Collection<Order> orders = new ArrayList<Order>();
		Collection<Order> allOrders = orderDAO.findAll();
		
		if(u.getRole().equals(Role.BUYER)) {
			BuyerDAOImpl buyerDAO = (BuyerDAOImpl) ctx.getAttribute("buyerDAO");
			Buyer b = buyerDAO.findByUsername(username);
			orders = b.getOrders();
			
			for(Order o: orders) {
				if(o.getStatus().equals(OrderStatus.DELIVERED))
					orders.remove(o);
			}
		}
		if(u.getRole().equals(Role.MANAGER)) {
			ManagerDAOImpl managerDAO = (ManagerDAOImpl) ctx.getAttribute("managerDAO");
			Manager m = managerDAO.findByUsername(username);
			for(Order o : allOrders) {
				if(m.getRestaurant().equals(o.getRestaurant()))
					orders.add(o);
			}
		}
		if(u.getRole().equals(Role.SUPPLIER)) {
			SupplierDAOImpl supDAO = (SupplierDAOImpl) ctx.getAttribute("supDAO");
			Supplier s = supDAO.findByUsername(username);
			for(Order o : allOrders) {
				if(o.getStatus().equals(OrderStatus.AWAITING_SUPPLIER))
					orders.add(o);
			}
			orders.addAll(s.getOrders());
		}
		
		
		return orders;
	}
	
	
	@GET
	@Path("getBasket")
	@Produces(MediaType.APPLICATION_JSON)
	public Basket getBasket() {
		BasketDAOImpl baskDAO = (BasketDAOImpl) ctx.getAttribute("basketDAO");
		Basket b = baskDAO.findById("1");
		
		return b;
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
	
	
	public String generateRandomString() {
		 
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    System.out.println(generatedString);
	    return generatedString;
	}
	
}

package service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Article;
import beans.ArticleType;
import beans.Basket;
import beans.BasketItem;
import beans.Buyer;
import beans.QuantityType;
import dao.impl.ArticleDAOImpl;
import dao.impl.BasketDAOImpl;
import dao.impl.BuyerDAOImpl;
import dao.impl.ManagerDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.RestaurantDAOImpl;
import dto.BasketDTO;
import dto.BasketItemDTO;

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
	
}

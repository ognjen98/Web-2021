package beans;

import java.util.Date;
import java.util.List;

public class Order {

	private Integer id;
	private String uniqueId;
	private List<Article> articles;
	private Restaurant restaurant;
	private Date dateOfOrder;
	private Double price;
	private Buyer buyer;
	private OrderStatus status;
	
	public Order() {
		super();
	}

	public Order(Integer id, String uniqueId, List<Article> articles, Restaurant restaurant, Date dateOfOrder,
			Double price, Buyer buyer, OrderStatus status) {
		super();
		this.id = id;
		this.uniqueId = uniqueId;
		this.articles = articles;
		this.restaurant = restaurant;
		this.dateOfOrder = dateOfOrder;
		this.price = price;
		this.buyer = buyer;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Date getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
}

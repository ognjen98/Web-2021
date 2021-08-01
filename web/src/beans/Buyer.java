package beans;

import java.util.ArrayList;
import java.util.List;

public class Buyer extends User{

	private List<Order> orders;
	private Basket basket;
	private Integer points;
	private BuyerType buyerType;
	
	public Buyer() {
		super();
	}

	public Buyer(List<Order> orders, Basket basket, Integer points, BuyerType buyerType) {
		super();
		this.orders = orders;
		this.basket = basket;
		this.points = points;
		this.buyerType = buyerType;
	}
	
	public Buyer(String username, String password, String name, String surname, Gender gender, Role role) {
		super(username, password, name, surname, gender, role);
		this.orders = new ArrayList<Order>();
		this.basket = new Basket();
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public BuyerType getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(BuyerType buyerType) {
		this.buyerType = buyerType;
	}
	
	
}

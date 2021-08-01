package beans;

import java.util.List;

public class Supplier extends User{

	private List<Order> orders;
	
	public Supplier() {
		super();
	}

	public Supplier(List<Order> orders) {
		super();
		this.orders = orders;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
}

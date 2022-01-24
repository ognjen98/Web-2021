package beans;

import java.util.ArrayList;
import java.util.Date;
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
	
	public Supplier(String username, String password, String name, String surname, Gender gender, Date dateOfBirth, Role role) {
		super(username, password, name, surname, gender, dateOfBirth, role);
		this.orders = new ArrayList<>();
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
}

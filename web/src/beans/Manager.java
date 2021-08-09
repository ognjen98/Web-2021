package beans;

import java.util.ArrayList;

public class Manager extends User{

	private Restaurant restaurant;
	
	public Manager() {
		super();
	}

	public Manager(Restaurant restaurant) {
		super();
		this.restaurant = restaurant;
	}
	
	public Manager(String username, String password, String name, String surname, Gender gender, Role role) {
		super(username, password, name, surname, gender, role);
		this.restaurant = new Restaurant();
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	
}

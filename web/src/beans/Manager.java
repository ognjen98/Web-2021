package beans;

import java.util.ArrayList;
import java.util.Date;

public class Manager extends User{

	private Restaurant restaurant;
	
	public Manager() {
		super();
	}

	public Manager(Restaurant restaurant) {
		super();
		this.restaurant = restaurant;
	}
	
	public Manager(String username, String password, String name, String surname, Gender gender, Date dateOfBirth, Role role) {
		super(username, password, name, surname, gender, dateOfBirth, role);
		this.restaurant = new Restaurant();
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	
}

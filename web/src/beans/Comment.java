package beans;

public class Comment {

	private Buyer buyer;
	private Restaurant restaurant;
	private String text;
	private Grade grade;
	
	public Comment() {
		super();
	}

	public Comment(Buyer buyer, Restaurant restaurant, String text, Grade grade) {
		super();
		this.buyer = buyer;
		this.restaurant = restaurant;
		this.text = text;
		this.grade = grade;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	
}

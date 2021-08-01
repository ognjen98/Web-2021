package beans;

public class Article {

	private String id;
	private String name;
	private Double price;
	private ArticleType type;
	private Restaurant restaurant;
	private Integer quantity;
	private String description;
	private String image;
	
	public Article() {
		super();
	}

	public Article(String id, String name, double price, ArticleType type, Restaurant restaurant, Integer quantity,
			String description, String image) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.type = type;
		this.restaurant = restaurant;
		this.quantity = quantity;
		this.description = description;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public ArticleType getType() {
		return type;
	}

	public void setType(ArticleType type) {
		this.type = type;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}

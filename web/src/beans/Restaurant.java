package beans;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
	private Integer id;
	private String name;
	private RestaurantType type;
	private List<Article> articles;
	private RestaurantStatus status;
	private Location location;
	private String image;
	
	public Restaurant() {
		super();
	}

	public Restaurant(Integer id, String name, RestaurantType type, List<Article> articles, RestaurantStatus status, Location location,
			String image) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.articles = articles;
		this.status = status;
		this.location = location;
		this.image = image;
	}
	
	public Restaurant(String name, RestaurantType type, RestaurantStatus status, Location location,
			String image) {
		super();
		this.name = name;
		this.type = type;
		this.articles = new ArrayList<>();
		this.status = status;
		this.location = location;
		this.image = image;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RestaurantType getType() {
		return type;
	}

	public void setType(RestaurantType type) {
		this.type = type;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public RestaurantStatus getStatus() {
		return status;
	}

	public void setStatus(RestaurantStatus status) {
		this.status = status;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	

}

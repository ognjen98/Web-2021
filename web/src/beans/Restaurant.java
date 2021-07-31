package beans;

import java.util.List;

public class Restaurant {
	private String id;
	private String name;
	private RestaurantType type;
	private List<Article> articles;
	private Boolean status;
	private Location location;
	private String image;
	
	public Restaurant() {
		super();
	}

	public Restaurant(String id, String name, RestaurantType type, List<Article> articles, Boolean status, Location location,
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
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
package dto;

public class RestaurantDTO {
	private String name;
	private String type;
	private String logo;
	private String location;
	
	
	
	public RestaurantDTO(String name, String type, String logo, String location) {
		super();
		this.name = name;
		this.type = type;
		this.logo = logo;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	

}

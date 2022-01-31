package dto;

public class BasketItemDTO {

	private String name;
	private Double price;
	private String type;
	private String qType;
	private String description;
	private String image;
	private Integer quantity;
	
	public BasketItemDTO() {
		
	}

	public BasketItemDTO(String name, Double price, String type, String qType, String description, String image,
			Integer quantity) {
		super();
		this.name = name;
		this.price = price;
		this.type = type;
		this.qType = qType;
		this.description = description;
		this.image = image;
		this.quantity = quantity;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getqType() {
		return qType;
	}

	public void setqType(String qType) {
		this.qType = qType;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}

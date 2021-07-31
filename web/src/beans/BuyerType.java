package beans;

public class BuyerType {

	private TypeName type;
	private Double discount;
	private Integer points;
	
	public BuyerType() {}
	
	public BuyerType(TypeName type, Double discount, Integer points) {
		super();
		this.type = type;
		this.discount = discount;
		this.points = points;
	}

	public TypeName getType() {
		return type;
	}

	public void setType(TypeName type) {
		this.type = type;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
	
	
	
	
}

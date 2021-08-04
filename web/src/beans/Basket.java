package beans;


import java.util.List;

public class Basket {

	private String id;
	private List<BasketItem> items;
	private Buyer buyer;
	private Double price;
	
	public Basket() {
		super();
	}

	public Basket(String id, List<BasketItem> items, Buyer buyer, Double price) {
		super();
		this.id = id;
		this.items = items;
		this.buyer = buyer;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<BasketItem> getItems() {
		return items;
	}

	public void setItems(List<BasketItem> items) {
		this.items = items;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}

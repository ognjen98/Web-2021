package beans;


import java.util.List;

public class Basket {

	private Integer id;
	private List<BasketItem> items;
	private Buyer buyer;
	private Double price;
	private String resId;
	
	public Basket() {
		super();
	}

	public Basket(Integer id, List<BasketItem> items, Buyer buyer, Double price, String resId) {
		super();
		this.id = id;
		this.items = items;
		this.buyer = buyer;
		this.price = price;
		this.resId = resId;
	}
	
	public Basket(List<BasketItem> items, Buyer buyer, Double price, String resId) {
		super();

		this.items = items;
		this.buyer = buyer;
		this.price = price;
		this.resId = resId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}
	
	
}

package beans;

import java.util.HashMap;

public class Basket {

	private String id;
	private HashMap<Article, Integer> articles;
	private Buyer buyer;
	private Double price;
	
	public Basket() {
		super();
	}

	public Basket(String id, HashMap<Article, Integer> articles, Buyer buyer, Double price) {
		super();
		this.id = id;
		this.articles = articles;
		this.buyer = buyer;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<Article, Integer> getArticles() {
		return articles;
	}

	public void setArticles(HashMap<Article, Integer> articles) {
		this.articles = articles;
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

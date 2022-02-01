package dto;

import java.util.Date;
import java.util.List;

public class OrderDTO {

	private List<BasketItemDTO> articles;
	private String res;
	private Double price;
	private String buyer;
	
	public OrderDTO() {
		
	}

	public OrderDTO(List<BasketItemDTO> articles, String res, Double price, String buyer) {
		super();
		this.articles = articles;
		this.res = res;
		
		this.price = price;
		this.buyer = buyer;
	}

	public List<BasketItemDTO> getArticles() {
		return articles;
	}

	public void setArticles(List<BasketItemDTO> articles) {
		this.articles = articles;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	
	
}

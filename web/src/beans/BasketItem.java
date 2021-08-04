package beans;

public class BasketItem {
	
	private Article article;
	private Integer quantity;
	
	public BasketItem() {
		super();
	}

	public BasketItem(Article article, Integer quantity) {
		super();
		this.article = article;
		this.quantity = quantity;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	

}

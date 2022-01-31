package dto;

import java.util.List;

import beans.Buyer;

public class BasketDTO {

	private List<BasketItemDTO> items;
	
	private String buyer;
	private String resId;
	
	public BasketDTO() {
		
	}

	public BasketDTO(List<BasketItemDTO> items, String buyer, String resId) {
		super();
		this.items = items;
		
		this.buyer = buyer;
		this.resId = resId;
	}

	public List<BasketItemDTO> getItems() {
		return items;
	}

	public void setItems(List<BasketItemDTO> items) {
		this.items = items;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}
	
}

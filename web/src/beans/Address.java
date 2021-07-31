package beans;

public class Address {
	private String streetName;
	private String number;
	private String city;
	private String zipCode;
	
	public Address() {}

	public Address(String streetName, String number, String city, String zipCode) {
		super();
		this.streetName = streetName;
		this.number = number;
		this.city = city;
		this.zipCode = zipCode;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	

}

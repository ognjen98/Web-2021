package dto;

import beans.Gender;

public class GetInfoDTO {
	private String username;
	private String name;
	private String surname;
	private String gender;
	
	public GetInfoDTO() {
		super();
	}

	public GetInfoDTO(String username, String name, String surname, String gender) {
		super();
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	

}

package dto;

public class SearchDTO {
	
	private String name;
	private String location;
	private String status;
	private String type;
	private String grade;
	
	public SearchDTO() {}

	public SearchDTO(String name, String location, String status, String type, String grade) {
		super();
		this.name = name;
		this.location = location;
		this.status = status;
		this.type = type;
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	

}

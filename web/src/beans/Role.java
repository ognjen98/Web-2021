package beans;

public enum Role {

	ADMIN (1),
	MANAGER(2) ,
	SUPPLIER(3) ,
	BUYER (4);
	
	private final Integer roleNum;
	
	private Role(Integer number) {
		this.roleNum = number;
	}
}

package beans;

public enum Grade {
	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5);
	
	private final Integer grade;
	
	private Grade(Integer grade) {
		this.grade = grade;
	}
	
}

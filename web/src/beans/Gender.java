package beans;

public enum Gender {
	MALE("1"),
	FEMALE("2"),
	OTHER("3");
	
	public final String label;

    private Gender(String label) {
        this.label = label;
    }

}

package util;

public class Human {
	private String firstName;
	private String lastName;
	
	
	public Human(String fn,String ln) {
		this.firstName=fn;
		this.lastName=ln;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
}

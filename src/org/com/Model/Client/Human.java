package org.com.Model.Client;

import java.io.Serializable;

/**
 * Class Human
 *
 */
public class Human implements Serializable {
	private String firstName;
	private String lastName;
	
	
	public Human(String firstName,String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}

	@Override
	public String toString() {
		return "Human{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}
}

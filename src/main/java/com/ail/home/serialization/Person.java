package com.ail.home.serialization;

public class Person implements java.io.Serializable {
	private String firstName;
	private String lastName;
	private int age;
	private Person spouse;

	public Person(String fn, String ln, int a) {
		this.firstName = fn;
		this.lastName = ln;
		this.age = a;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String value) {
		firstName = value;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String value) {
		lastName = value;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int value) {
		age = value;
	}

	public Person getSpouse() {
		return spouse;
	}

	public void setSpouse(Person value) {
		spouse = value;
	}

	public String toString() {
		return "[Person: firstName=" + firstName +
				" lastName=" + lastName +
				" age=" + age +
				" spouse=" + spouse.getFirstName() +
				"]";
	}

}
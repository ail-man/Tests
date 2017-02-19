package com.ail.home.serialization;

public class Person implements java.io.Serializable {
	private String firstName;
	private String lastName;
	private int age;
	private Person spouse;
	private Gender gender;

	public Person(String fn, String ln, int a, Gender gender) {
		this.firstName = fn;
		this.lastName = ln;
		this.age = a;
		this.gender = gender;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String toString() {
		return "[Person: firstName=" + firstName +
				" lastName=" + lastName +
				" age=" + age +
				" spouse=" + spouse.getFirstName() +
				"]";
	}

	private enum Gender {
		MALE, FEMALE
	}

}
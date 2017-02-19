package com.ail.home.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private int age;
	private Gender gender;
	private Person spouse;

	public Person(String fn, String ln, int a, Gender gender) {
		this.firstName = fn;
		this.lastName = ln;
		this.age = a;
		this.gender = gender;
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		// "Encrypt"/obscure the sensitive data
		age = age >> 2;
		stream.defaultWriteObject();
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		// "Decrypt"/de-obscure the sensitive data
		age = age << 2;
	}

	private Object writeReplace() throws ObjectStreamException {
		return new PersonProxy(this);
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
				" gender=" + gender +
				"]";
	}

	public enum Gender {
		MALE, FEMALE
	}

	private class PersonProxy implements Serializable {
		private String data;

		private PersonProxy(Person orig) {
			data = orig.getFirstName() + "," + orig.getLastName() + "," + orig.getAge() + "," + orig.getGender();
			if (orig.getSpouse() != null) {
				Person spouse = orig.getSpouse();
				data = data + "," + spouse.getFirstName() + "," + spouse.getLastName() + "," + spouse.getAge() + "," + spouse.getGender();
			}
		}

		private Object readResolve() throws ObjectStreamException {
			String[] pieces = data.split(",");
			Person result = new Person(pieces[0], pieces[1], Integer.parseInt(pieces[2]), Gender.valueOf(pieces[3]));
			if (pieces.length > 4) {
				result.setSpouse(new Person(pieces[4], pieces[5], Integer.parseInt(pieces[6]), Gender.valueOf(pieces[7])));
				result.getSpouse().setSpouse(result);
			}
			return result;
		}
	}

}
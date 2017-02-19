package com.ail.home.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

public class SerializationTest {

	@Test
	public void serializeToDisk() {
		try {
			Person ted = new Person("Ted", "Neward", 39);
			Person charl = new Person("Charlotte", "Neward", 38);

			ted.setSpouse(charl);
			charl.setSpouse(ted);

			FileOutputStream fos = new FileOutputStream("target/tempdata.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(ted);
			oos.close();
		} catch (Exception ex) {
			fail("Exception thrown during test: " + ex.toString());
		}

		try {
			FileInputStream fis = new FileInputStream("target/tempdata.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Person ted = (Person) ois.readObject();
			ois.close();

			assertEquals(ted.getFirstName(), "Ted");
			assertEquals(ted.getSpouse().getFirstName(), "Charlotte");

			// Clean up the file
			//			new File("tempdata.ser").delete();
		} catch (Exception ex) {
			fail("Exception thrown during test: " + ex.toString());
		}
	}

}
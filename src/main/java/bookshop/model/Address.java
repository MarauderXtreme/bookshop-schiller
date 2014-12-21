package bookshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Address {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String street;
	private String streetnumber;
	private String plz;
	private String city;
	
	@Deprecated
	protected Address() {
	}
	
	/**
	 * Contructor of Address.
	 * @param street
	 * @param streetnumber
	 * @param plz
	 * @param city
	 */
	public Address(String street, String streetnumber, String plz, String city) {
		this.setStreet(street);
		this.setStreetnumber(streetnumber);
		this.setPlz(plz);
		this.setCity(city);
	}

	/**
	 * @return the streetname of the address
	 */
	public String getStreet() {
		return street;
	}
	
	/**
	 * Sets the streetname of the address to the given value.
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the streetnumber of the address
	 */
	public String getStreetnumber() {
		return streetnumber;
	}

	/**
	 * Sets the streetnumber of the address to the given value.
	 * @param streetnumber
	 */
	public void setStreetnumber(String streetnumber) {
		this.streetnumber = streetnumber;
	}

	/**
	 * @return the PLZ of the address
	 */
	public String getPlz() {
		return plz;
	}

	/**
	 * Sets the PLZ of the address to the given value.
	 * @param plz
	 */
	public void setPlz(String plz) {
		this.plz = plz;
	}

	/**
	 * @return the city of the address
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city of the address to the given value.
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * @return a string representing the full address
	 */
	public String toString() {
		return street + " " + streetnumber + ", " + plz + " " + city;
	}

}

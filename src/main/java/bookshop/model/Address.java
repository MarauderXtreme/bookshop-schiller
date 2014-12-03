package bookshop.model;

public class Address {
	
	private String street;
	private String streetnumber;
	private String plz;
	private String city;
	
	public Address(String street, String streetnumber, String plz, String city) {
		this.setStreet(street);
		this.setStreetnumber(streetnumber);
		this.setPlz(plz);
		this.setCity(city);
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetnumber() {
		return streetnumber;
	}

	public void setStreetnumber(String streetnumber) {
		this.streetnumber = streetnumber;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String toString() {
		return street + " " + streetnumber + ", " + plz + " " + city;
	}

}

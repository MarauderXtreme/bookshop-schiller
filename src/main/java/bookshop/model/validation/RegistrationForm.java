package bookshop.model.validation;

import org.hibernate.validator.constraints.NotEmpty;

import bookshop.model.Address;

public class RegistrationForm {

	@NotEmpty(message = "{RegistrationForm.username.NotEmpty}")
	private String username;
	
	@NotEmpty(message = "{RegistrationForm.firstname.NotEmpty}")
	private String firstname;
	
	@NotEmpty(message = "{RegistrationForm.lastname.NotEmpty}")
	private String lastname;

	@NotEmpty(message = "{RegistrationForm.email.NotEmpty}")
	private String email;
	
	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	private String password;
	
	@NotEmpty(message = "{RegistrationForm.street.NotEmpty}")
	private String street;
	
	@NotEmpty(message = "{RegistrationForm.streetnumber.NotEmpty}")
	private String streetnumber;
	
	@NotEmpty(message = "{RegistrationForm.plz.NotEmpty}")
	private String plz;
	
	@NotEmpty(message = "{RegistrationForm.city.NotEmpty}")
	private String city;
	
	/**
	 * @return the given user name from the registration form
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the user name of the registration form to the given value.
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * @return the given first name from the registration form
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * Sets the first name of the registration form to the given value.
	 * @param username
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * @return the given last name from the registration form
	 */
	public String getLastname() {
		return lastname;
	}
	
	/**
	 * Sets the last name of the registration form to the given value.
	 * @param username
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * @return the given email address from the registration form
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email address of the registration form to the given value.
	 * @param username
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the given password name from the registration form
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password of the registration form to the given value.
	 * @param username
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the given address from the registration form
	 */
	public Address getAddress() {
		return new Address(street, streetnumber, plz, city);
	}
	
	/**
	 * @return the given street name from the registration form
	 */
	public String getStreet() {
		return street;
	}
	
	/**
	 * Sets the street name of the registration form to the given value.
	 * @param username
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
	 * @return the given street number from the registration form
	 */
	public String getStreetnumber() {
		return streetnumber;
	}
	
	/**
	 * Sets the street number of the registration form to the given value.
	 * @param username
	 */
	public void setStreetnumber(String streetnumber) {
		this.streetnumber = streetnumber;
	}
	
	/**
	 * @return the given PLZ from the registration form
	 */
	public String getPlz() {
		return plz;
	}
	
	/**
	 * Sets the PLZ of the registration form to the given value.
	 * @param username
	 */
	public void setPlz(String plz) {
		this.plz = plz;
	}
	
	/**
	 * @return the given city from the registration form
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Sets the city of the registration form to the given value.
	 * @param username
	 */
	public void setCity(String city) {
		this.city = city;
	}
}

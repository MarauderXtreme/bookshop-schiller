package bookshop.model.validation;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import bookshop.model.Address;

public class RegistrationForm {
	
	@NotEmpty(message = "{RegistrationForm.username.NotEmpty}")
	@Length(max = 16, message = "{RegistrationForm.username.Length}")
	@Pattern(regexp="([A-ZÄÖÜa-zäöüß]{1}[A-ZÄÖÜa-zäöüß0-9]+){0,1}", message = "{RegistrationForm.username.Format}")
	private String username;
	
	@NotEmpty(message = "{RegistrationForm.firstname.NotEmpty}")
	@Length(max = 32, message = "{RegistrationForm.firstname.Length}")
	@Pattern(regexp="([A-ZÄÖÜ]{1}[a-zäöüß]+([\\-\\s]{1}[A-ZÄÖÜ]{1}[a-zäöüß]+){0,}){0,1}", message = "{RegistrationForm.firstname.Format}")
	private String firstname;
	
	@NotEmpty(message = "{RegistrationForm.lastname.NotEmpty}")
	@Length(max = 32, message = "{RegistrationForm.lastname.Length}")
  	@Pattern(regexp="((([A-ZÄÖÜa-zäöüß]{1}[a-zäöüß]+([\\-\\s]{1}[A-ZÄÖÜa-zäöüß]{1}[a-zäöüß]+){0,}){0,1}[\\-\\s]{0,1}){0,1}([A-ZÄÖÜ]{1}[a-zäöüß]+){1}){0,1}", message = "{RegistrationForm.lastname.Format}")
	private String lastname;
	
	@NotEmpty(message = "{RegistrationForm.email.NotEmpty}")
	@Length(max = 64, message = "{RegistrationForm.email.Length}")
	@Pattern(regexp="([A-Za-z0-9\\.\\_\\-]+@{1}[A-Za-z0-9\\.\\-]+[\\.]{1}[A-Za-z]{2,24}){0,1}", message = "{RegistrationForm.email.Format}")
	private String email;
	
	@Length(min = 8, max = 32, message = "{RegistrationForm.password.Length}")
	@Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[_\\W]).{8,32})", message = "{RegistrationForm.password.Format}")
	private String password;
	
	@NotEmpty(message = "{RegistrationForm.passwordRepeat.NotEmpty}")
	private String passwordRepeat = " ";
	
	@NotEmpty(message = "{RegistrationForm.street.NotEmpty}")
	@Length(max = 32, message = "{RegistrationForm.street.Length}")
	@Pattern(regexp="((([A-ZÄÖÜ]{1}[a-zäöüß]+([\\-\\s]{1}[A-ZÄÖÜa-zäöüß]{1}[a-zäöüß]+){0,}){0,1}[\\-\\s]{0,1}){0,1}([A-ZÄÖÜ]{1}[a-zäöüß]+){1}){0,1}", message = "{RegistrationForm.street.Format}")
	private String street;
	
	@NotEmpty(message = "{RegistrationForm.streetnumber.NotEmpty}")
	@Length(max = 5, message = "{RegistrationForm.streetnumber.Length}")
	@Pattern(regexp="([0-9]+[A-Za-z]{0,1}){0,1}", message = "{RegistrationForm.streetnumber.Format}")
	private String streetnumber;
	
	@Length(min = 5, max = 5, message = "{RegistrationForm.plz.Length}")
	@Pattern(regexp="([0-9]+){0,1}", message = "{RegistrationForm.plz.Format}")
	private String plz;
	
	@NotEmpty(message = "{RegistrationForm.city.NotEmpty}")
	@Length(max = 32, message = "{RegistrationForm.city.Length}")
	@Pattern(regexp="((([A-ZÄÖÜ]{1}[a-zäöüß]+([\\-\\s]{1}[A-ZÄÖÜa-zäöüß]{1}[a-zäöüß]+){0,}){0,1}[\\-\\s]{0,1}){0,1}([A-ZÄÖÜ]{1}[a-zäöüß]+){1}){0,1}", message = "{RegistrationForm.city.Format}")
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
	 * @return the given repeated password name from the registration form
	 */
	public String getPasswordRepeat() {
		return passwordRepeat;
	}
	
	/**
	 * Sets the repeated password of the registration form to the given value.
	 * @param username
	 */
	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
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

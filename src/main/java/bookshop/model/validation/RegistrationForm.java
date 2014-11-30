package bookshop.model.validation;

import org.hibernate.validator.constraints.NotEmpty;

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

	@NotEmpty(message = "{RegistrationForm.address.NotEmpty}")
	private String address;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
}

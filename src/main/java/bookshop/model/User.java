package bookshop.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.UserAccount;

public class User {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String email;
	
	private String address;
	
	@OneToOne
	private UserAccount userAccount;
	
	public User(UserAccount userAccount, String email, String address) {
		this.userAccount = userAccount;
		this.email = email;
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getEMail() {
		return email;
	}
	
	public void setEMail(String email) {
		this.email = email;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}
	
	public long getID() {
		return id;
	}

}

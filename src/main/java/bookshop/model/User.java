package bookshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.UserAccount;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String address;
	
	@OneToOne
	private UserAccount userAccount;
	
	@Deprecated
	protected User() {
	}
	
	public User(UserAccount userAccount, String address) {
		this.userAccount = userAccount;
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}
	
	public long getID() {
		return id;
	}

}

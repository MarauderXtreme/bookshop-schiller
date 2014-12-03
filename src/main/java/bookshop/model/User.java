package bookshop.model;

import javax.persistence.CascadeType;
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
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	
	@OneToOne
	private UserAccount userAccount;
	
	@Deprecated
	protected User() {
	}
	
	/**
	 * Constructor of User.
	 * @param userAccount
	 * @param address
	 */
	public User(UserAccount userAccount, Address address) {
		this.userAccount = userAccount;
		this.address = address; // this.address = address
	}
	
	/**
	 * @return the address of the user
	 */
	public String getAddress() {
		return address.toString();
	}

	/**
	 * Sets the address of the User.
	 * @param address
	 */
	public void setAddress(Address address) { // eigentlich: Address address
		this.address = address; // this.address = address
	}

	/**
	 * @return the user account of the user
	 */
	public UserAccount getUserAccount() {
		return userAccount;
	}
	
	/**
	 * @return the ID of the user
	 */
	public long getID() {
		return id;
	}

}

package bookshop.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.Role;
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
		this.address = address;
	}
	
	/**
	 * @return the address of the user
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets the address of the user to the given value.
	 * @param address
	 */
	public void setAddress(Address address) {
		this.address = address;
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
	public long getId() {
		return id;
	}
	
	/**
	 * @return a string including all roles of the user
	 */
	public String getRoles() {
		String roles = "";
		int k = 0;
		for (java.util.Iterator<Role> i = userAccount.getRoles().iterator(); i.hasNext();) {
			Role r = i.next();
			k = k + 1;
			if (k == 1) {
				roles = r.getName().substring(5);
			} else {
				roles = roles + ", " + r.getName().substring(5);
			}
		}
		return roles;
	}
	
	/**
	 * @return "ENABLED", if the user is enabled; "DISABLED", if the user is disabled
	 */
	public String getState() {
		if (userAccount.isEnabled()) {
			return "ENABLED";
		} else {
			return "DISABLED";
		}
	}

}

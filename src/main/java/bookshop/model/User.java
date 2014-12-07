package bookshop.model;

import java.util.List;

import javassist.bytecode.Descriptor.Iterator;

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
	public String getAddress() {
		return address.toString();
	}

	/**
	 * Sets the address of the User.
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
	public long getID() {
		return id;
	}
	
	/**
	 * @return a String including all roles of the User
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
				roles = roles + ", " + r.getName().substring(6);
			}
		}
		return roles;
	}
	
	public String getState() {
		if (userAccount.isEnabled()) {
			return "ENABLED";
		} else {
			return "DISABLED";
		}
	}

}

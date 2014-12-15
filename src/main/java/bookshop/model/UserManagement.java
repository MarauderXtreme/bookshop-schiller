package bookshop.model;

import java.util.ArrayList;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.salespointframework.useraccount.UserAccountIdentifier;

@Component
public class UserManagement {
	
	private final UserRepository userRepository;
	private final UserAccountManager userAccountManager;
	
	/**
	 * Constructor for the UserManagement.
	 * @param userRepository
	 * @param userAccountManager
	 */
	@Autowired
	public UserManagement(UserRepository userRepository, UserAccountManager userAccountManager) {

		this.userRepository = userRepository;
		this.userAccountManager = userAccountManager;
	}
	
	/**
	 * @return a List of all existing employees
	 */
	public ArrayList<User> getEmployees() {
		Iterable<User> users = userRepository.findAll();
		ArrayList<User> employees = new ArrayList<User>();
		for(User u : users) {
			Iterable<Role> roles = u.getUserAccount().getRoles();
			for(Role r: roles) {
				if (r.getName().equals("ROLE_EMPLOYEE")) {
					employees.add(u);
				}
			}
		}
		return employees;
	}
	
	/**
	 * @return a List of all existing customers
	 */
	public ArrayList<User> getCustomers() {
		Iterable<User> users = userRepository.findAll();
		ArrayList<User> customers = new ArrayList<User>();
		for(User u : users) {
			Iterable<Role> roles = u.getUserAccount().getRoles();
			for(Role r: roles) {
				if (r.getName().equals("ROLE_CUSTOMER")) {
					customers.add(u);
				}
			}
		}
		return customers;
	}
	
	/**
	 * Disables the given UserAccount, if it is enabled.
	 * @param userAccount
	 */
	public void disable(UserAccount userAccount) {
		if (userAccount.isEnabled()) {
			User user = userRepository.findByUserAccount(userAccount);
			userAccountManager.disable(user.getUserAccount().getIdentifier());
		}
	}
	
	/**
	 * Enables the given UserAccount, if it is disabled.
	 * @param userAccount
	 */
	public void enable(UserAccount userAccount) {
		if (!userAccount.isEnabled()) {
			User user = userRepository.findByUserAccount(userAccount);
			userAccountManager.enable(user.getUserAccount().getIdentifier());
		}
	}
	
	/**
	 * Adds the given role to the given userAccount.
	 * @param userAccount
	 * @param role
	 */
	public void addRole(UserAccount userAccount, Role role) {
		if (!userAccount.hasRole(role)) {
			userAccount.add(role);
		}
	}
	
	/**
	 * Removes the given role to the given userAccount.
	 * @param userAccount
	 * @param role
	 */
	public void removeRole(UserAccount userAccount, Role role) {
		if (userAccount.hasRole(role)) {
			userAccount.remove(role);
		}
	}
	
	/**
	 * Changes the email address of the given user account to the given email address value.
	 * @param userAccount
	 * @param email
	 */
	public void changeEmail(UserAccount userAccount, String email) {
		userAccount.setEmail(email);
	}
	
	/**
	 * Changes the first name of the given user account to the given first name value.
	 * @param userAccount
	 * @param firstname
	 */
	public void changeFirstname(UserAccount userAccount, String firstname) {
		userAccount.setFirstname(firstname);
	}
	
	/**
	 * Changes the last name of the given user account to the given last name value.
	 * @param userAccount
	 * @param lastname
	 */
	public void changeLastname(UserAccount userAccount, String lastname) {
		userAccount.setLastname(lastname);
	}
	
	/**
	 * Changes the address of the given user account to the given address value.
	 * @param userAccount
	 * @param address
	 */
	public void changeAddress(UserAccount userAccount, Address address) {
		User user = userRepository.findByUserAccount(userAccount);
		user.setAddress(address);
	}
	
	/**
	 * Changes the password of the given user account to the given password value.
	 * @param userAccount
	 * @param password
	 */
	public void changePassword(UserAccount userAccount, String password) {
		// fehlt
	}

}

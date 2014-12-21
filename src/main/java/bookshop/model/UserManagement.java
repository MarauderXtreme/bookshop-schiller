package bookshop.model;

import java.util.ArrayList;
import java.util.Iterator;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	 * Disables the given userAccount, if it is enabled.
	 * @param userAccount
	 * @return true if account was disabled and false if not
	 */
	public boolean disable(UserAccount userAccount) {
		
		if (userAccount.isEnabled()) {
			userAccountManager.disable(userAccount.getIdentifier());
			return true;
		}
		return false;
	}
	
	/**
	 * Enables the given userAccount, if it is disabled.
	 * @param userAccount
	 * @return true if account was enabled and false if not
	 */
	public boolean enable(UserAccount userAccount) {
		
		if (!userAccount.isEnabled()) {
			userAccountManager.enable(userAccount.getIdentifier());
			return true;
		}
		return false;
	}
	
	/**
	 * Adds the given role to the given userAccount.
	 * @param userAccount
	 * @param role
	 * @return true if role was added and false if not
	 */
	public boolean addRole(UserAccount userAccount, Role role) {

		if (!userAccount.hasRole(role)) {
			userAccount.add(role);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes the given role from the given userAccount.
	 * @param userAccount
	 * @param role
	 * @return true if role was removed and false if not
	 */
	public boolean removeRole(UserAccount userAccount, Role role) {
		
		int sizeRoles = 0;
		for (Iterator<Role> iteratorRoles = userAccount.getRoles().iterator(); iteratorRoles.hasNext();) {
			iteratorRoles.next();
			sizeRoles++;
		}
		if (sizeRoles <= 1) {
			return false;
		}
		
		if (role.equals(new Role("ROLE_ADMIN"))) {
			int numberAdmins = getNumberOfAdmins();
			if (numberAdmins <= 1) {
				return false;
			}
		}
			
		if (userAccount.hasRole(role)) {
			userAccount.remove(role);
			return true;
		}
		return false;
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
		User user = userRepository.findUserByUserAccount(userAccount);
		user.setAddress(address);
	}
	
	/**
	 * Changes the password of the given user account to the given password value.
	 * @param userAccount
	 * @param password
	 */
	public void changePassword(UserAccount userAccount, String password) {
		userAccountManager.changePassword(userAccount, password);
	}
	
	/**
	 * @return the number of administrators
	 */
	public int getNumberOfAdmins() {
		int numberAdmins = 0;
		for (Iterator<User> iteratorUsers = userRepository.findAll().iterator(); iteratorUsers.hasNext();) {
			for (Iterator<Role> iteratorRoles = iteratorUsers.next().getUserAccount().getRoles().iterator(); iteratorRoles.hasNext();) {
				if (iteratorRoles.next().equals(new Role("ROLE_ADMIN"))) {
					numberAdmins++;
				}
			}
		}
		return numberAdmins;
	}

}

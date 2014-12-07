package bookshop.model;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.salespointframework.useraccount.UserAccountIdentifier;

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
	 * Disables the given UserAccount, if it is enabled.
	 * @param userAccount
	 */
	public void disable(UserAccount userAccount) {
		if (userAccount.isEnabled()) {
			User user = userRepository.findByUserAccount(userAccount);
			userAccountManager.disable(new UserAccountIdentifier(String.valueOf(user.getID())));
		}
	}
	
	/**
	 * Enables the given UserAccount, if it is disabled.
	 * @param userAccount
	 */
	public void enable(UserAccount userAccount) {
		if (!userAccount.isEnabled()) {
			User user = userRepository.findByUserAccount(userAccount);
			userAccountManager.enable(new UserAccountIdentifier(String.valueOf(user.getID())));
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
	 * Changes the firstname of the given user account to the given firstname value.
	 * @param userAccount
	 * @param firstname
	 */
	public void changeFirstname(UserAccount userAccount, String firstname) {
		userAccount.setFirstname(firstname);
	}
	
	/**
	 * Changes the lastname of the given user account to the given lastname value.
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

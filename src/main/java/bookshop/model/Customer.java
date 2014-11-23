package bookshop.model;

import org.salespointframework.useraccount.UserAccount;

public class Customer extends User {

	public Customer(UserAccount userAccount, String address) {
		super(userAccount, address);
	}

}

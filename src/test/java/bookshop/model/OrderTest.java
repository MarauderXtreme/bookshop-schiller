package bookshop.model;

import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import bookshop.AbstractIntegrationTests;

public class OrderTest extends AbstractIntegrationTests{
	
	@Autowired OrderManager orderManager;
	@Autowired UserAccountManager userAccountManager;

	final Role customerRole = new Role("ROLE_CUSTOMER");
	UserAccount customerAccount = userAccountManager.create("admin", "123", customerRole);
	Order order = new Order(customerAccount);
	orderManager.
	
	assertTrue();
}

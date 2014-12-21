package bookshop.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import bookshop.AbstractIntegrationTests;

public class UserManagementTest extends AbstractIntegrationTests {

	@Autowired UserRepository userRepository;
	@Autowired UserAccountManager userAccountManager;
	@Autowired UserManagement userManagement;
	
	@Test
	public void testGetUserLists() {
		
		final Role adminRole = new Role("ROLE_ADMIN");
		final Role employeeRole = new Role("ROLE_EMPLOYEE");
		final Role customerRole = new Role("ROLE_CUSTOMER");
		
		UserAccount adminAccount = userAccountManager.create("admin", "123", employeeRole);
		adminAccount.setFirstname("Christoph");
		adminAccount.setLastname("Kepler");
		adminAccount.setEmail("chris.kepler@schiller.de");
		adminAccount.add(adminRole);
		
		UserAccount ua = userAccountManager.create("wurst", "123", customerRole);
		ua.setFirstname("Hans");
		ua.setLastname("Wurst");
		ua.setEmail("hans.wurst@web.de");
		
		assertTrue("Die Methode getEmployees() liefert eine unvollständige Liste.", containsUserAccount(userManagement.getEmployees(), adminAccount));
		assertFalse("Die Methode getEmployees() liefert eine Liste, die falsche User enthält.", containsUserAccount(userManagement.getEmployees(), ua));
		assertTrue("Die Methode getCustomers() liefert eine unvollständige Liste.", containsUserAccount(userManagement.getCustomers(), ua));
		assertFalse("Die Methode getCustomers() liefert eine Liste, die falsche User enthält.", containsUserAccount(userManagement.getCustomers(), adminAccount));
	}
	
	public boolean containsUserAccount(List<User> userList, UserAccount userAccount) {
		
		for (User u : userList) {
			if (u.getUserAccount().equals(userAccount)) {
				return true;
			}
		}
		return false;
	}

}
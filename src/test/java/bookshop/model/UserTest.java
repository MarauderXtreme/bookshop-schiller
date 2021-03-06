package bookshop.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import bookshop.AbstractIntegrationTests;

public class UserTest extends AbstractIntegrationTests {

	@Autowired UserAccountManager userAccountManager;
	
	@Test
	public void testStringGetters() {
		
		final Role adminRole = new Role("ROLE_ADMIN");
		final Role employeeRole = new Role("ROLE_EMPLOYEE");
		UserAccount adminAccount = userAccountManager.create("admin", "123", employeeRole);
		adminAccount.setFirstname("Christoph");
		adminAccount.setLastname("Kepler");
		adminAccount.setEmail("chris.kepler@schiller.de");
		adminAccount.add(adminRole);
		userAccountManager.save(adminAccount);
		
		Address adminAddress = new Address("Mommsenstraße", "13", "01187", "Dresden");
		User admin = new User(adminAccount, adminAddress);

		assertTrue("Die Methode getRoles() in User liefert einen falschen String.", admin.getRoles().equals("ADMIN, EMPLOYEE") || admin.getRoles().equals("EMPLOYEE, ADMIN"));
		assertEquals("Die Methode getState() in User liefert einen falschen String.", admin.getState(), "ENABLED");
		assertEquals("Die Methode toString() in Address liefert einen falschen String.", adminAddress.toString(), "Mommsenstraße 13, 01187 Dresden");

	}

}
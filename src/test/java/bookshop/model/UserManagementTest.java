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
	
	@Test
	public void testChangeState() {
		
		final Role customerRole = new Role("ROLE_CUSTOMER");
		
		UserAccount ua = userAccountManager.create("wurst", "123", customerRole);
		ua.setFirstname("Hans");
		ua.setLastname("Wurst");
		ua.setEmail("hans.wurst@web.de");
		
		assertTrue("Ein neuer UserAccount ist nicht standardmäßig aktiviert.", ua.isEnabled());
		
//		userManagement.disable(ua);
//
//		assertFalse("Die Methode disable() funtioniert nicht korrekt.", ua.isEnabled());
//		
//		userManagement.enable(ua);
//		
//		assertEquals("Die Methode enable() funtioniert nicht korrekt.", ua.isEnabled());
	}
	
	@Test
	public void testChangeRoles() {
		
		final Role adminRole = new Role("ROLE_ADMIN");
		final Role employeeRole = new Role("ROLE_EMPLOYEE");
		final Role customerRole = new Role("ROLE_CUSTOMER");
		
		UserAccount ua = userAccountManager.create("wurst", "123", customerRole);
		ua.setFirstname("Hans");
		ua.setLastname("Wurst");
		ua.setEmail("hans.wurst@web.de");
		
		assertEquals("Ein neuer UserAccount hat nicht standardmäßig, die ihm übergebene Rolle.", ua.getRoles().toString(), "[ROLE_CUSTOMER]");

		userManagement.addRole(ua, employeeRole);
		userManagement.addRole(ua, adminRole);
		
		assertEquals("Die Methode addRole() fügt die übergebene Rolle nicht korrekt hinzu.", ua.getRoles().toString(), "[ROLE_ADMIN, ROLE_CUSTOMER, ROLE_EMPLOYEE]");
		
		userManagement.addRole(ua, employeeRole);
		
		assertEquals("Die Methode addRole() führt etwas Falsches durch, wenn bereits existierende Rollen zum Nutzer hinzugefügt werden sollen.", ua.getRoles().toString(), "[ROLE_ADMIN, ROLE_CUSTOMER, ROLE_EMPLOYEE]");
		
		userManagement.removeRole(ua, customerRole);
		
		assertEquals("Die Methode removeRole() entfernt die übergebene Rolle nicht korrekt.", ua.getRoles().toString(), "[ROLE_ADMIN, ROLE_EMPLOYEE]");
		
		userManagement.removeRole(ua, customerRole);
		
		assertEquals("Die Methode removeRole() führt etwas Falsches durch, wenn nicht existierende Rollen vom Nutzer entfernt werden sollen.", ua.getRoles().toString(), "[ROLE_ADMIN, ROLE_EMPLOYEE]");
	}
	
	@Test
	public void testChangeProfile() {
		
	}
	
	@Test
	public void testGetNumberOfAdmins() {
		
	}
}
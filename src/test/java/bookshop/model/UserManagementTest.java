package bookshop.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.ObjectError;

import bookshop.AbstractIntegrationTests;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UserManagement.class)
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
		
		assertEquals("Die Methode removeRole() führt etwas Falsches durch, wenn die einzige Rolle des Nutzer entfernt werden soll.", ua.getRoles().toString(), "[ROLE_CUSTOMER]");
		
		userManagement.removeRole(ua, customerRole);

		userManagement.addRole(ua, employeeRole);
		userManagement.addRole(ua, adminRole);
		
		assertEquals("Die Methode addRole() fügt die übergebene Rolle nicht korrekt hinzu.", ua.getRoles().toString(), "[ROLE_ADMIN, ROLE_CUSTOMER, ROLE_EMPLOYEE]");
		
		userManagement.addRole(ua, employeeRole);
		
		assertEquals("Die Methode addRole() führt etwas Falsches durch, wenn eine bereits existierende Rolle zum Nutzer hinzugefügt werden soll.", ua.getRoles().toString(), "[ROLE_ADMIN, ROLE_CUSTOMER, ROLE_EMPLOYEE]");
		
		userManagement.removeRole(ua, customerRole);
		
		assertEquals("Die Methode removeRole() entfernt die übergebene Rolle nicht korrekt.", ua.getRoles().toString(), "[ROLE_ADMIN, ROLE_EMPLOYEE]");
		
		userManagement.removeRole(ua, customerRole);
		
		assertEquals("Die Methode removeRole() führt etwas Falsches durch, wenn eine nicht existierende Rolle vom Nutzer entfernt werden soll.", ua.getRoles().toString(), "[ROLE_ADMIN, ROLE_EMPLOYEE]");
	}
	
	@Test
	public void testChangeProfile() {
		
		final Role customerRole = new Role("ROLE_CUSTOMER");
		
		UserAccount ua = userAccountManager.create("wurst", "123", customerRole);
		ua.setFirstname("Hans");
		ua.setLastname("Wurst");
		ua.setEmail("hans.wurst@web.de");
		
		userManagement.changeEmail(ua, "test@email.de");
		
		assertEquals("Die Methode changeEmail() funktioniert nicht korrekt.", ua.getEmail(), "test@email.de");
		
		userManagement.changeFirstname(ua, "Heinz");
		
		assertEquals("Die Methode changeFirstname() funktioniert nicht korrekt.", ua.getFirstname(), "Heinz");
		
		userManagement.changeLastname(ua, "Würstchen");
		
		assertEquals("Die Methode changeLastname() funktioniert nicht korrekt.", ua.getLastname(), "Würstchen");
		
		userManagement.changePassword(ua, "1234");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		assertTrue("Die Methode changePassword() funktioniert nicht korrekt.", encoder.matches("1234", ua.getPassword().toString()));

//		userManagement.changeAddress(ua, new Address("Straße", "1", "12345", "Dresden"));
//		
//		assertEquals("Die Methode changeAddress() funktioniert nicht korrekt.", userRepository.findUserByUserAccount(ua).getAddress().toString(), "Straße 1, 12345, Dresden");
	}
	
	@Test
	public void testGetNumberOfAdmins() {
		
		assertEquals("Die Methode getNumberOfAdmins() funktioniert nicht korrekt.", userManagement.getNumberOfAdmins(), 1);
	}
}
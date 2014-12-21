package bookshop.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import bookshop.AbstractIntegrationTests;

public class UserRepositoryTest extends AbstractIntegrationTests {

	@Autowired UserRepository userRepository;
	@Autowired UserAccountManager userAccountManager;
	
	@Test
	public void testFindUserByUserAccount() {
		
		final Role adminRole = new Role("ROLE_ADMIN");
		final Role employeeRole = new Role("ROLE_EMPLOYEE");
		UserAccount adminAccount = userAccountManager.create("admin", "123", employeeRole);
		adminAccount.setFirstname("Christoph");
		adminAccount.setLastname("Kepler");
		adminAccount.setEmail("chris.kepler@schiller.de");
		adminAccount.add(adminRole);
		
		assertEquals("Die Methode findByUserAccount() der Klasse UserRepository liefert ein falsches Ergebnis. (UserAccount)", userRepository.findUserByUserAccount(adminAccount).getUserAccount(), adminAccount);
		assertEquals("Die Methode findByUserAccount() der Klasse UserRepository liefert ein falsches Ergebnis. (Address)", userRepository.findUserByUserAccount(adminAccount).getAddress().toString(), "Mommsenstraße 13, 01187 Dresden");
	}

}
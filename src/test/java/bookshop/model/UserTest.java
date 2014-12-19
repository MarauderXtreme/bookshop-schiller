package bookshop.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bookshop.AbstractIntegrationTests;
import bookshop.BookShopSchiller;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BookShopSchiller.class)
public class UserTest extends AbstractIntegrationTests {

	@Autowired User user;
	
	@Test
	public void testGetRoles() {
		
	}

}
package bookshop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bookshop.AbstractIntegrationTests;
import bookshop.BookShopSchiller;

public class UserControllerTest extends AbstractIntegrationTests {

	@Autowired UserController userController;
	
	@Test
	public void testGetRoles() {
		
	}

}
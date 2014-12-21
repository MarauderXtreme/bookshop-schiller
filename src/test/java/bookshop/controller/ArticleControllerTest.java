package bookshop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bookshop.AbstractIntegrationTests;
import bookshop.BookShopSchiller;

public class ArticleControllerTest extends AbstractIntegrationTests {

	@Autowired ArticleController articleController;
	
	@Test
	public void testArticleController() {
		//Functionalities are tested in Article and Category Tests
	}

}
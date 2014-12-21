package bookshop.model;

import static org.joda.money.CurrencyUnit.EUR;
import static org.junit.Assert.*;

import org.joda.money.Money;
import org.junit.Test;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import bookshop.AbstractIntegrationTests;
import bookshop.model.Article.ArticleId;

public class CategoryTest extends AbstractIntegrationTests {

	
	@Test
	public void testCategory() {
		
		Category category = new Category("Jupidu", ArticleId.BOOK);
		//...
		
		
		assertTrue("Die Methode getType() der Klasse Article liefert einen falschen Wert der Enumeration ArticleId.", category.getType().equals(ArticleId.BOOK));
		assertEquals("Die Methode getCategoriesList der Klasse Address liefert einen falschen String.", category.getCategoryName(), "Jupidu");
	}

}
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

public class CategoryManagementTest extends AbstractIntegrationTests {

	@Autowired
	CategoryManagement categories;

	
	@Test
	public void testCategories() {
		
		Category category = new Category("Jupidu", ArticleId.BOOK);
		
		categories.save(category);
		//...

		assertTrue("Die Methode getType() der Klasse Article liefert einen falschen Wert der Enumeration ArticleId.", categories.findOne(category.getId()).getType().equals(ArticleId.BOOK));
		assertEquals("Die Methode getCategoriesList der Klasse Address liefert einen falschen String.", categories.findOne(category.getId()).getCategoryName(), "Jupidu");
	}

}
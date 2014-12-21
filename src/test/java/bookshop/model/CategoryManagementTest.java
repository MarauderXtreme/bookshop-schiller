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

		Category category = new Category("Fantasy", ArticleId.DVD);

		categories.save(category);
		
		categories.delete(categories.findOne("CDPop"));
		
		assertFalse("Die Methode getType() des Objekts Category der Klasse CategoryManagement liefert einen falschen Wert der Enumeration ArticleId.", categories.findOne(category.getId()).getType().equals(ArticleId.BOOK) || categories.findOne(category.getId()).getType().equals(ArticleId.CD));
		assertEquals("Die Methode getCategoryName des Objekts Category der Klasse CategoryManagement liefert einen falschen String.", categories.findOne("DVDFantasy").getCategoryName(), "Fantasy");
		assertEquals("Die Methode getCategoryName des Objekts Category der Klasse CategoryManagement liefert einen falschen String.", categories.findOne("DVDHorror").getCategoryName(), "Horror");
		assertFalse("Die Methode exists() der Klasse CategoryManagement liefert eine falsche Category.", categories.exists("CDPop"));
	}

}
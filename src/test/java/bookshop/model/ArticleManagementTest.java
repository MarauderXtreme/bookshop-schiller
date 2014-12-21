package bookshop.model;

import static org.joda.money.CurrencyUnit.EUR;
import static org.junit.Assert.*;

import org.joda.money.Money;
import org.junit.Test;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import bookshop.AbstractIntegrationTests;
import bookshop.model.Article.ArticleId;

public class ArticleManagementTest extends AbstractIntegrationTests {

	@Autowired 
	ArticleManagement articleCatalog;
	@Autowired
	CategoryManagement categories;
	@Autowired
	Inventory<InventoryItem> inventory;


	
	@Test
	public void testArticleCatalogAdd() {
		
		Article article = new Article("Testbook", Money.of(EUR, 7.98), "Book for JUnit", "Philo", "0000000101010", ArticleId.CD, categories.findById("CDPop").get().getCategoryName(), "Flann O'Brien", "01.01.2015", Money.of(EUR, 0.99));
		
		assertFalse("Die Methode getType() des Objekts Article der Klasse ArticleManagement liefert einen falschen Wert der Enumeration ArticleId.", articleCatalog.exists(article.getIdentifier()));
		
		articleCatalog.save(article);
		InventoryItem item = new InventoryItem(article, Units.TEN);
		inventory.save(item);
		
		assertTrue("Die Methode getType() des Objekts Article der Klasse ArticleManagement liefert einen falschen Wert der Enumeration ArticleId.", articleCatalog.exists(article.getIdentifier()));

	}
	
	@Test
	public void testArticleCatalogDelete(){
		
		Article article = new Article("Testbook", Money.of(EUR, 7.98), "Book for JUnit", "Philo", "0000000101010", ArticleId.CD, categories.findById("CDPop").get().getCategoryName(), "Flann O'Brien", "01.01.2015", Money.of(EUR, 0.99));
		
		articleCatalog.save(article);
		assertTrue("Die Methode getType() des Objekts Article der Klasse ArticleManagement liefert einen falschen Wert der Enumeration ArticleId.", articleCatalog.exists(article.getIdentifier()));
		
		articleCatalog.delete(articleCatalog.findOne(article.getIdentifier()).get().getIdentifier());
		assertFalse("Die Methode getType() des Objekts Article der Klasse ArticleManagement liefert einen falschen Wert der Enumeration ArticleId.", articleCatalog.exists(article.getIdentifier()));

	}

}
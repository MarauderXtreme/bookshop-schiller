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

public class ArticleManagementTest extends AbstractIntegrationTests {

	@Autowired 
	ArticleManagement articleCatalog;
	@Autowired
	CategoryManagement categories;

	
	@Test
	public void testArticleCatalog() {
		
		Article article = new Article("Testbook", Money.of(EUR, 7.98), "Book for JUnit", "Philo", "0000000101010", ArticleId.BOOK, categories.findById("BOOKRatgeber").get().getCategoryName(), "Flann O'Brien", "01.01.2015", Money.of(EUR, 0.99));
		
		articleCatalog.save(article);

		assertTrue("Die Methode getType() des Objekts Article der Klasse ArticleManagement liefert einen falschen Wert der Enumeration ArticleId.", articleCatalog.findOne(article.getIdentifier()).get().getType().equals(ArticleId.BOOK));
		assertEquals("Die Methode getArtist() des Objekts Article der Klasse ArticleManagement liefert einen falschen String.", articleCatalog.findOne(article.getIdentifier()).get().getArtist(), "Flann O'Brien");

	}

}
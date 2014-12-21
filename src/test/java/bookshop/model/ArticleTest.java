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

public class ArticleTest extends AbstractIntegrationTests {

	@Autowired 
	ArticleManagement articleCatalog;
	@Autowired
	CategoryManagement categories;

	
	@Test
	public void testStringGetters() {
		
		Article article = new Article("Testbook", Money.of(EUR, 7.98), "Book for JUnit", "Philo", "0000000101010", ArticleId.BOOK, categories.findById("BOOKRatgeber").get().getCategoryName(), "Flann O'Brien", "01.01.2015", Money.of(EUR, 0.99));
		article.setArtist("Jambala");
		//...
		
		Category category = new Category("Jupidu", ArticleId.BOOK);
		article.addCategory(category.getCategoryName());
		article.removeCategory(categories.findById("BOOKRatgeber").get().getCategoryName());
		
		articleCatalog.save(article);

		assertTrue("Die Methode getType() der Klasse Article liefert einen falschen Wert der Enumeration ArticleId.", article.getType().equals(ArticleId.BOOK));
		assertEquals("Die Methode getCategoriesList der Klasse Address liefert einen falschen String.", article.getCategoryList(), "[Jupidu]");
	}

}
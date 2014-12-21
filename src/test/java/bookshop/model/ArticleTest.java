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
	CategoryManagement categories;
	
	@Test
	public void testArticleEdit() {
		
		Article article = new Article("Testbook", Money.of(EUR, 7.98), "Book for JUnit", "Philo", "0000000101010", ArticleId.DVD, categories.findById("DVDHorror").get().getCategoryName(), "Flann O'Brien", "01.01.2015", Money.of(EUR, 0.99));
		article.setArtist("Paul Nizon");
		article.setBeschreibung("Ein Buch voller wundervoller Langeweile");
		article.setName("Das Jahr der Liebe");
		article.setType(ArticleId.BOOK);
		article.setId("0000001010100");
		article.setImage("trostundrat.jpg");
		article.setPrice(Money.of(EUR, 9.99));
		article.setPublisher("Verlag!");
		article.setReleaseDate("19.08.1969");
		article.setStockPrice(Money.of(EUR, 2));
		
		Category category = new Category("Geschwafel", ArticleId.BOOK);
		article.addCategory(category.getCategoryName());
		article.removeCategory(categories.findById("DVDHorror").get().getCategoryName());
		
		assertTrue("Die Methode getType() der Klasse Article liefert einen falschen Wert der Enumeration ArticleId.", article.getType().equals(ArticleId.BOOK));
		assertFalse("Die Methode getName() der Klasse Article liefert einen falschen String.", article.getName().equals("Testbook"));
		assertFalse("Die Methode getReleaseDate() der Klasse Article liefert einen falschen String.", article.getReleaseDate().equals("19081969"));
		assertEquals("Die Methode getType() der Klasse Article liefert einen falschen Wert der Enumeration ArticleId.", article.getType(), category.getType());
		assertEquals("Die Methode getCategoriesList der Klasse Article liefert einen falschen String.", article.getCategoryList(), "[Geschwafel]");
		
		article.addCategory(categories.findOne("BOOKFiktion").getCategoryName());
		
		assertEquals("Die Methode getCategoriesList der Klasse Article liefert einen falschen String.", article.getCategoryList(), "[Fiktion, Geschwafel]");

		article.removeCategory(categories.findOne("BOOKFiktion").getCategoryName());
		
		assertFalse("Die Methode getCategoriesList der Klasse Article liefert einen falschen String.", article.getCategoryList().equals("[Fiktion, Geschwafel]"));
		assertEquals("Die Methode getCategoriesList der Klasse Article liefert einen falschen String.", article.getCategoryList(), "[Geschwafel]");
		
	}

}
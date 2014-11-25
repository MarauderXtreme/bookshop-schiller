package bookshop;

import static org.joda.money.CurrencyUnit.*;

import java.util.Arrays;

import org.joda.money.Money;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import bookshop.model.Article;
import bookshop.model.ArticleManagement;
import bookshop.model.Article.ArticleId;
import bookshop.model.Book;
import bookshop.model.User;
import bookshop.model.UserRepository;

@Component
public class BookShopSchillerDataInitializer implements DataInitializer {

	private final ArticleManagement articleCatalog;
	private final Inventory<InventoryItem> inventory;
	private final UserAccountManager userAccountManager;
	private final UserRepository userRepository;

	@Autowired
	public BookShopSchillerDataInitializer(UserRepository userRepository, Inventory<InventoryItem> inventory,
			UserAccountManager userAccountManager, ArticleManagement articleCatalog) {

		Assert.notNull(userRepository, "UserRepository must not be null!");
		Assert.notNull(inventory, "Inventory must not be null!");
		Assert.notNull(userAccountManager, "UserAccountManager must not be null!");
		Assert.notNull(articleCatalog, "VideoCatalog must not be null!");

		this.userRepository = userRepository;
		this.inventory = inventory;
		this.userAccountManager = userAccountManager;
		this.articleCatalog = articleCatalog;
	}

	@Override
	public void initialize() {

		initializeUsers(userAccountManager, userRepository);
		initializeCatalog(articleCatalog, inventory);
	}
	
	private void initializeCatalog(ArticleManagement articleCatalog, Inventory<InventoryItem> inventory) {

		/*if (articleCatalog.getArticleList().iterator().hasNext()) {
			return;
		}*/
		
		if(articleCatalog.findAll().iterator().hasNext()){
			return;
		}

		//articleCatalog.addArticle("Trost und Rat", new Book("Flann O'Brien","Ein Ratgeber der besonderen Art", "Trost und Rat", 123, ArticleId.BOOK));
		articleCatalog.save(new Article("Trost und Rat", Money.of(EUR, 9.99), "Ein Ratgeber der besonderen Art", "Flann O'Brien", 123, ArticleId.BOOK));

		// (｡◕‿◕｡)
		// Über alle eben hinzugefügten Discs iterieren und jeweils ein InventoryItem mit der Quantity 10 setzen
		// Das heißt: Von jeder Disc sind 10 Stück im Inventar.

		/*for (Article article : articleCatalog.getArticleList()) {
			InventoryItem inventoryItem = new InventoryItem(article, Units.TEN);
			inventory.save(inventoryItem);
		}*/
		
		for (Article article : articleCatalog.findAll()) {
			InventoryItem inventoryItem = new InventoryItem(article, Units.TEN);
			inventory.save(inventoryItem);
		}
		
	}

	private void initializeUsers(UserAccountManager userAccountManager, UserRepository userRepository) {

		if (userAccountManager.get(new UserAccountIdentifier("boss")).isPresent()) {
			return;
		}

		UserAccount adminAccount = userAccountManager.create("admin", "123", new Role("ROLE_ADMIN"));
		userAccountManager.save(adminAccount);
		
		UserAccount bossAccount = userAccountManager.create("boss", "123", new Role("ROLE_BOSS"));
		userAccountManager.save(bossAccount);
		
		final Role customerRole = new Role("ROLE_CUSTOMER");
		final Role employeeRole = new Role("ROLE_EMPLOYEE");
		// to do: add other manager roles
		
		UserAccount ua1 = userAccountManager.create("hans", "123", customerRole);
		userAccountManager.save(ua1);
		UserAccount ua2 = userAccountManager.create("dextermorgan", "123", customerRole);
		userAccountManager.save(ua2);
		UserAccount ua3 = userAccountManager.create("earlhickey", "123", customerRole);
		userAccountManager.save(ua3);
		UserAccount ua4 = userAccountManager.create("mclovinfogell", "123", customerRole);
		userAccountManager.save(ua4);
		
		UserAccount ua5 = userAccountManager.create("santamaria", "123", employeeRole);
		userAccountManager.save(ua5);
		UserAccount ua6 = userAccountManager.create("klaus", "123", employeeRole);
		userAccountManager.save(ua6);
		UserAccount ua7 = userAccountManager.create("santa-claus", "123", employeeRole);
		userAccountManager.save(ua7);

		User c1 = new User(ua1, "wurst");
		User c2 = new User(ua2, "Miami-Dade County");
		User c3 = new User(ua3, "Camden County - Motel");
		User c4 = new User(ua4, "Los Angeles");
		User e1 = new User(ua5, "Wilder Westen");
		User e2 = new User(ua6, "Zuhaus");
		User e3 = new User(ua7, "Nordpol");

		userRepository.save(Arrays.asList(c1, c2, c3, c4, e1, e2, e3));
		
	}
}

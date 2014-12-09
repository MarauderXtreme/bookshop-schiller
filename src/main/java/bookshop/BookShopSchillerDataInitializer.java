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

import bookshop.model.Address;
import bookshop.model.Article;
import bookshop.model.ArticleManagement;
import bookshop.model.Article.ArticleId;
import bookshop.model.User;
import bookshop.model.UserRepository;

@Component
public class BookShopSchillerDataInitializer implements DataInitializer {
	
	private final ArticleManagement articleCatalog;
	private final Inventory<InventoryItem> inventory;
	private final UserAccountManager userAccountManager;
	private final UserRepository userRepository;

	/**
	 * Constructor for BookShopDataInitializer.
	 * @param userRepository
	 * @param inventory
	 * @param userAccountManager
	 * @param articleCatalog
	 */
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

	/**
	 * Initialize all users and articles.
	 */
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

		articleCatalog.save(new Article("Bastard Operator from Hell", Money.of(EUR, 19.99), "Ultimativer Leitfaden für den Job an einem Helpdesk", "Simon Travaglia", "42", ArticleId.BOOK, "Action", "Flann O'Brien"));
		articleCatalog.save(new Article("Trost und Rat", Money.of(EUR, 9.99), "Ein Ratgeber der besonderen Art", "Flann O'Brien", "123", ArticleId.BOOK, "Action", "Flann O'Brien"));
		articleCatalog.save(new Article("50 Schatten des Grauens", Money.of(EUR, 7.98), "Horrorpersiflage des BEstsellers", "Chris Ragman", "124", ArticleId.BOOK, "Action", "Flann O'Brien"));
		articleCatalog.save(new Article("Der Doktor und seine Gefährten", Money.of(EUR, 14.99), "Das Begleitbuch zur Serie", "Sir Doctor from Tardis", "125", ArticleId.BOOK, "Action", "Flann O'Brien"));
		articleCatalog.save(new Article("Stargate - Kommando SG-1", Money.of(EUR, 17.95), "Kurzbeschreibungen der Episoden der ersten Staffel", "Wolfgang Hohlbein", "126", ArticleId.BOOK, "Action", "Flann O'Brien"));
		articleCatalog.save(new Article("Depedro", Money.of(EUR, 17.95), "Spanische Musik", "Spanish Records", "1263453rr", ArticleId.CD, "Ballade", "Flann O'Brien"));
		articleCatalog.save(new Article("Begotten", Money.of(EUR, 17.95), "Strange...", "Indie Records", "1263453rr", ArticleId.DVD, "Horror", "Flann O'Brien"));
		
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

	/**
	 * Create one administrator, one boss, some customers and some employees with the userAccountAccountManager.
	 * Add them to the userRepository.
	 * @param userAccountManager
	 * @param userRepository
	 */
	private void initializeUsers(UserAccountManager userAccountManager, UserRepository userRepository) {

		if (userAccountManager.get(new UserAccountIdentifier("boss")).isPresent()) {
			return;
		}

		final Role adminRole = new Role("ROLE_ADMIN");
		final Role bossRole = new Role("ROLE_BOSS");
		final Role employeeRole = new Role("ROLE_EMPLOYEE");
		final Role userManagerRole = new Role("ROLE_USERMANAGER");
		final Role customerRole = new Role("ROLE_CUSTOMER");
		
		UserAccount adminAccount = userAccountManager.create("admin", "123", adminRole);
		adminAccount.setFirstname("Christoph");
		adminAccount.setLastname("Kepler");
		adminAccount.setEmail("chris.kepler@schiller.de");
		userAccountManager.save(adminAccount);
		
		userRepository.save(new User(adminAccount, new Address("Mommsenstraße", "13", "01187", "Dresden")));
		
		UserAccount bossAccount = userAccountManager.create("boss", "123", bossRole);
		bossAccount.setFirstname("Philipp");
		bossAccount.setLastname("Waack");
		bossAccount.setEmail("philipp.waack@schiller.de");
		userAccountManager.save(bossAccount);
		
		userRepository.save(new User(bossAccount, new Address("Bergstraße", "64", "01187", "Dresden")));
		
		UserAccount ua1 = userAccountManager.create("assi", "123", employeeRole);
		ua1.setFirstname("Philipp");
		ua1.setLastname("Jäschke");
		ua1.setEmail("philipp.jaeschke@schiller.de");
		userAccountManager.save(ua1);
		User e1 = new User(ua1, new Address("Zellescher Weg", "18", "01187", "Dresden"));
		
		UserAccount ua2 = userAccountManager.create("tester", "123", employeeRole);
		ua2.setFirstname("Maximilian");
		ua2.setLastname("Dühr");
		ua2.setEmail("max.duehr@schiller.de");
		userAccountManager.save(ua2);
		User e2 = new User(ua2, new Address("Nöthnitzer Straße", "46", "01187", "Dresden"));
		
		UserAccount ua3 = userAccountManager.create("sekki", "123", employeeRole);
		ua3.setFirstname("Till");
		ua3.setLastname("Köhler");
		ua3.setEmail("till.koehler@schiller.de");
		ua3.add(userManagerRole);
		userAccountManager.save(ua3);
		User e3 = new User(ua3, new Address("Zellescher Weg", "12", "01187", "Dresden"));
		
		userRepository.save(Arrays.asList(e1, e2, e3));
		
		UserAccount ua4 = userAccountManager.create("wurst", "123", customerRole);
		ua4.setFirstname("Hans");
		ua4.setLastname("Wurst");
		ua4.setEmail("hans.wurst@web.de");
		userAccountManager.save(ua4);
		User c1 = new User(ua4, new Address("Wurstweg", "3b", "10405", "Berlin"));
		
		UserAccount ua5 = userAccountManager.create("dextermorgan", "123", customerRole);
		ua5.setFirstname("Rainer");
		ua5.setLastname("Zufall");
		ua5.setEmail("rainer.zufall@gmail.com");
		userAccountManager.save(ua5);
		User c2 = new User(ua5, new Address("Würfelallee", "6", "80995", "München"));
		
		UserAccount ua6 = userAccountManager.create("earlhickey", "123", customerRole);
		ua6.setFirstname("Mario");
		ua6.setLastname("Nette");
		ua6.setEmail("mario.nette@t-mobile.de");
		userAccountManager.save(ua6);
		User c3 = new User(ua6, new Address("Am Theater", "92", "50668", "Köln"));
		
		userRepository.save(Arrays.asList(c1, c2, c3));
		
	}
}

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
import bookshop.model.CalendarManagement;
import bookshop.model.Category;
import bookshop.model.CategoryManagement;
import bookshop.model.MyDate;
import bookshop.model.RoomManagement;
import bookshop.model.User;
import bookshop.model.UserRepository;

@Component
public class BookShopSchillerDataInitializer implements DataInitializer {
	
	private final ArticleManagement articleCatalog;
	private final Inventory<InventoryItem> inventory;
	private final UserAccountManager userAccountManager;
	private final UserRepository userRepository;
	private final CategoryManagement categories;

	/**
	 * Constructor for BookShopDataInitializer.
	 * @param userRepository
	 * @param inventory
	 * @param userAccountManager
	 * @param articleCatalog
	 */
	@Autowired
	public BookShopSchillerDataInitializer(UserRepository userRepository, Inventory<InventoryItem> inventory,
			UserAccountManager userAccountManager, ArticleManagement articleCatalog, CategoryManagement categories) {

		Assert.notNull(userRepository, "UserRepository must not be null!");
		Assert.notNull(inventory, "Inventory must not be null!");
		Assert.notNull(userAccountManager, "UserAccountManager must not be null!");
		Assert.notNull(articleCatalog, "ArticleCatalog must not be null!");
		Assert.notNull(categories, "CategoryManagement must not be null!");

		this.userRepository = userRepository;
		this.inventory = inventory;
		this.userAccountManager = userAccountManager;
		this.articleCatalog = articleCatalog;
		this.categories = categories;

		//RoomManagement.getInstance().addRoom("Computerraum","067");
		//CalendarManagement.getInstance().addEvent("Praesentation", new Date("26112014","1110"), RoomManagement.getInstance().getRoom("Computerraum"));
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
		
		
		if(articleCatalog.findAll().iterator().hasNext()){
			return;
		}
		
		
		RoomManagement.getInstance().addRoom("Computerraum","067","42");
		RoomManagement.getInstance().addRoom("Ratssaal","1335","1337");
		RoomManagement.getInstance().addRoom("ASCII","0123","21");
		RoomManagement.getInstance().addRoom("FSR-Buero","042","21");
		
		MyDate testdate = new MyDate("17122014","1910");
		testdate.getDay();
		testdate.getMonth();
		testdate.getYear();
		testdate.getHours();
		testdate.getMinutes();	
		
		CalendarManagement.getInstance().addEvent("Praesentation", new MyDate("26112014","1110"), RoomManagement.getInstance().getRoom("Computerraum"));
		CalendarManagement.getInstance().addEvent("FSR-Filmabend", new MyDate("27112014","1915"), RoomManagement.getInstance().getRoom("Ratssaal"));
		CalendarManagement.getInstance().addEvent("Tutorenbesprechung", new MyDate("03122011","1110"), RoomManagement.getInstance().getRoom("Computerraum"));
		CalendarManagement.getInstance().addEvent("Tutorenbesprechung", new MyDate("10122014","1110"), RoomManagement.getInstance().getRoom("Computerraum"));
		CalendarManagement.getInstance().addEvent("Gluehweinverkoestigung", new MyDate("18122014","0905"), RoomManagement.getInstance().getRoom("ASCII"));
		CalendarManagement.getInstance().addEvent("Weihnachtsfeier", new MyDate("22122014","1800"), RoomManagement.getInstance().getRoom("Ratssaal"));
		CalendarManagement.getInstance().addEvent("Weihnachtsfeier", new MyDate("22122014","1830"), RoomManagement.getInstance().getRoom("ASCII"));
		CalendarManagement.getInstance().addEvent("Praesentation", new MyDate("19011824","1110"), RoomManagement.getInstance().getRoom("Computerraum"));
		CalendarManagement.getInstance().addEvent("Lernen", new MyDate("23012015","1640"), RoomManagement.getInstance().getRoom("Computerraum"));
		CalendarManagement.getInstance().addEvent("Fasching", new MyDate("10102015","0920"), RoomManagement.getInstance().getRoom("Computerraum"));
		CalendarManagement.getInstance().addEvent("1. April", new MyDate("01042015","1111"), RoomManagement.getInstance().getRoom("FSR-Buero"));
		CalendarManagement.getInstance().addEvent("TestVorAllenBitches", new MyDate("01042011","1111"), RoomManagement.getInstance().getRoom("FSR-Buero"));
		CalendarManagement.getInstance().getCalendar().sortEvents();
		CalendarManagement.getInstance().getCalendar().showList();		
		
		
		Category unterhaltung = new Category("Unterhaltung", ArticleId.BOOK);
		Category ratgeber = new Category("Ratgeber", ArticleId.BOOK);
		Category fiktion = new Category("Fiktion", ArticleId.BOOK);
		Category sachbuch = new Category("Sachbuch", ArticleId.BOOK);

		Category pop = new Category("Pop", ArticleId.CD);
		Category rock = new Category("Rock", ArticleId.CD);
		Category electronic = new Category("Electronic", ArticleId.CD);
		Category hiphop = new Category("Hip Hop", ArticleId.CD);
		
		Category action = new Category("Action", ArticleId.DVD);
		Category horror = new Category("Horror", ArticleId.DVD);
		Category drama = new Category("Drama", ArticleId.DVD);


		
		categories.save(unterhaltung);
		categories.save(ratgeber);
		categories.save(fiktion);
		categories.save(sachbuch);
		
		categories.save(pop);
		categories.save(rock);
		categories.save(electronic);
		categories.save(hiphop);
		
		categories.save(horror);
		categories.save(action);
		categories.save(drama);
		
		System.out.println(categories.findById("BOOKFiktion").get().getCategoryName());
		
		Article v = new Article("Trost und Rat", Money.of(EUR, 9.99), "Ein Ratgeber der besonderen Art", "Flann O'Brien", "1234567890421", ArticleId.BOOK, categories.findById("BOOKFiktion").get().getCategoryName(), "Flann O'Brien", "trostundrat.jpg", "01.01.2015", Money.of(EUR, 0.99));		
		articleCatalog.save(v);
		articleCatalog.save(new Article("50 Schatten des Grauens", Money.of(EUR, 7.98), "Horrorpersiflage des Bestsellers", "Chris Ragman", "0000000000001", ArticleId.BOOK, categories.findById("BOOKRatgeber").get().getCategoryName(), "Flann O'Brien", "01.01.2015", Money.of(EUR, 0.99)));
		articleCatalog.save(new Article("Der Doktor und seine Gefährten", Money.of(EUR, 14.99), "Das Begleitbuch zur Serie", "Sir Doctor from Tardis", "0000000000002", ArticleId.BOOK, "Fiktion", "Flann O'Brien", "01.01.2015", Money.of(EUR, 0.99)));
		articleCatalog.save(new Article("Stargate - Kommando SG-1", Money.of(EUR, 17.95), "Kurzbeschreibungen der Episoden der ersten Staffel", "Wolfgang Hohlbein", "0000000000003", ArticleId.BOOK, categories.findById("BOOKUnterhaltung").get().getCategoryName(), "Flann O'Brien", "01.01.2015", Money.of(EUR, 0.99)));
		articleCatalog.save(new Article("Unterschiedlich!", Money.of(EUR, 17.95), "Kurzbeschreibungen der Episoden der ersten Staffel", "Wolfgang Hohlbein", "0000000000004", ArticleId.BOOK, categories.findById("BOOKUnterhaltung").get().getCategoryName(), "Flann O'Brien", "01.01.2015", Money.of(EUR, 0.99)));
		articleCatalog.save(new Article("Ladida", Money.of(EUR, 17.95), "Kurzbeschreibungen der Episoden der ersten Staffel", "Wolfgang Hohlbein", "0000000000005", ArticleId.BOOK, categories.findById("BOOKUnterhaltung").get().getCategoryName(), "Flann O'Brien", "01.01.2015", Money.of(EUR, 0.99)));
		articleCatalog.save(new Article("Dummdumm", Money.of(EUR, 17.95), "Kurzbeschreibungen der Episoden der ersten Staffel", "Wolfgang Hohlbein", "0000000000006", ArticleId.BOOK, categories.findById("BOOKUnterhaltung").get().getCategoryName(), "Flann O'Brien", "01.01.2015", Money.of(EUR, 0.99)));
		articleCatalog.save(new Article("Depedro", Money.of(EUR, 17.95), "Spanische Musik", "Spanish Records", "1263453rr", ArticleId.CD, categories.findById("CDPop").get().getCategoryName(), "Pedro", "01.01.2015", Money.of(EUR, 0.99)));
		articleCatalog.save(new Article("Begotten", Money.of(EUR, 17.95), "Strange...", "Indie Records", "1263453rr", ArticleId.DVD, categories.findById("DVDHorror").get().getCategoryName(), "God", "01.01.2015", Money.of(EUR, 0.99)));
		 
		
		
		
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
		final Role articleManagerRole = new Role("ROLE_ARTICLEMANAGER");
		final Role eventManagerRole = new Role("ROLE_EVENTMANAGER");
		final Role salesManagerRole = new Role("ROLE_SALESMANAGER");
		final Role customerRole = new Role("ROLE_CUSTOMER");
		
		UserAccount shopAccount = userAccountManager.create("shop", "123", employeeRole);
		shopAccount.setFirstname("Christoph");
		shopAccount.setLastname("Kepler");
		shopAccount.setEmail("chris.kepler@schiller.de");
		shopAccount.add(adminRole);
		userAccountManager.save(shopAccount);
		
		UserAccount adminAccount = userAccountManager.create("admin", "123", employeeRole);
		adminAccount.setFirstname("Christoph");
		adminAccount.setLastname("Kepler");
		adminAccount.setEmail("chris.kepler@schiller.de");
		adminAccount.add(adminRole);
		userAccountManager.save(adminAccount);
		
		userRepository.save(new User(adminAccount, new Address("Mommsenstraße", "13", "01187", "Dresden")));
		
		UserAccount bossAccount = userAccountManager.create("boss", "123", employeeRole);
		bossAccount.setFirstname("Philipp");
		bossAccount.setLastname("Waack");
		bossAccount.setEmail("philipp.waack@schiller.de");
		bossAccount.add(articleManagerRole);
		bossAccount.add(bossRole);
		userAccountManager.save(bossAccount);
		
		userRepository.save(new User(bossAccount, new Address("Bergstraße", "64", "01187", "Dresden")));
		
		UserAccount ua1 = userAccountManager.create("assi", "123", employeeRole);
		ua1.setFirstname("Philipp");
		ua1.setLastname("Jäschke");
		ua1.setEmail("philipp.jaeschke@schiller.de");
		ua1.add(salesManagerRole);
		userAccountManager.save(ua1);
		User e1 = new User(ua1, new Address("Zellescher Weg", "18", "01187", "Dresden"));
		
		UserAccount ua2 = userAccountManager.create("tester", "123", employeeRole);
		ua2.setFirstname("Maximilian");
		ua2.setLastname("Dühr");
		ua2.setEmail("max.duehr@schiller.de");
		ua2.add(eventManagerRole);
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

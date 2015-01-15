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
	
	/**
	 * Create some articles and events.
	 * @param articleCatalog
	 * @param inventory
	 */
	private void initializeCatalog(ArticleManagement articleCatalog, Inventory<InventoryItem> inventory) {
		
		
		if(articleCatalog.findAll().iterator().hasNext()){
			return;
		}
		
		
		RoomManagement.getInstance().addRoom("Großer Konferenzsaal","001","1337");
		RoomManagement.getInstance().addRoom("Kleiner Konferenzsaal","101","100");
		RoomManagement.getInstance().addRoom("Computerpool","007","42");
		RoomManagement.getInstance().addRoom("Raum 102","102","10");
		
		MyDate testdate = new MyDate("17122014","1910");
		testdate.getDay();
		testdate.getMonth();
		testdate.getYear();
		testdate.getHours();
		testdate.getMinutes();	
		
		CalendarManagement.getInstance().addEvent("Seminar - Wie schreibe ich mein eigenes Buch?", new MyDate("09012015","1000"), RoomManagement.getInstance().getRoom("Computerpool"),new MyDate("09012015","1200"));
		CalendarManagement.getInstance().addEvent("Buchvorstellung - 'Begotten'", new MyDate("07012015","1400"), RoomManagement.getInstance().getRoom("Großer Konferenzsaal"),new MyDate("07012014","1530"));
		CalendarManagement.getInstance().addEvent("Buchvorstellung - 'Trost und Rat'", new MyDate("14012015","1400"), RoomManagement.getInstance().getRoom("Kleiner Konferenzsaal"),new MyDate("14012014","1530"));
		CalendarManagement.getInstance().addEvent("Buchvorstellung - 'The Last Resort'", new MyDate("21012015","1400"), RoomManagement.getInstance().getRoom("Großer Konferenzsaal"),new MyDate("21012015","1530"));
		CalendarManagement.getInstance().addEvent("Buchvorstellung - 'Der Bro-Code'", new MyDate("28012015","1400"), RoomManagement.getInstance().getRoom("Großer Konferenzsaal"),new MyDate("28012015","1530"));
		CalendarManagement.getInstance().addEvent("Gesprächsrunde - Von Autor zu Autor", new MyDate("19012015","1800"), RoomManagement.getInstance().getRoom("Raum 102"),new MyDate("19012015","2200"));
		CalendarManagement.getInstance().getCalendar().sortEvents();
		
		//Categories
		
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
		
		//Books
		
		articleCatalog.save(new Article("Trost und Rat",
										Money.of(EUR, 9.99),
										"Ein Ratgeber der besonderen Art.		Unter dem Pseudonym Myles na gCopaleen spendet Flann O'Brien den Lesern seine Kolumne für die Irish Times Trost und Rat in vielen wesentlichen Fragen des Lebens. Ausgewählt und kongenial übersetzt wurden die Texte von Harry Rowohlt. «Flann O'Brien bringt sich voll ein - mit Kopf, Herz und Bauch - und nutzt jede komische Chance.»",
										"Diana",
										"1234567890421",
										ArticleId.BOOK,
										categories.findById("BOOKRatgeber").get().getCategoryName(),
										"Flann O'Brien",
										"trostundrat.jpg",
										"04.09.1998",
										Money.of(EUR, 0.99))	
		);
		
		articleCatalog.save(new Article("50 Schatten des Grauens",
										Money.of(EUR, 9.99),
										"Horrorpersiflage des Bestsellers.		Sie ist 81, Pflanzenkundestudentin und in der Liebe nicht allzu erfahren. Doch dann lernt Ana Stelldichein den armen und ebenso unverschämt egoistischen wie schleimigen Unternehmer Christian von Grauen bei einem Interview für ihre Uni-Zeitung Alles für die Pflanzen kennen. Und möchte ihn eigentlich schnellstmöglich wieder vergessen, denn die Begegnung mit ihm hat sie zutiefst verwirrt. Sosehr sie sich aber darum bemüht: Sie kommt von ihm nicht los. Er hat einfach einen zu verstörenden Eindruck voller Abscheu und Hass hinterlassen. Von Grauen führt Ana ein, oh ja sie haben richtig gehört, einführen, in eine dunkle, gefährliche Welt des Hasses – in eine Welt, vor der sie zurückschreckt und die sie doch mit unwiderstehlicher Kraft anzieht …",
										"Heyne",
										"0000000000001",
										ArticleId.BOOK,
										categories.findById("BOOKRatgeber").get().getCategoryName(),
										"H.P. Lovecraft",
										"Flying_Polyp.jpg",
										"11.01.2010",
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("Der Doktor und seine Gefährten",
										Money.of(EUR, 14.99),
										"Das Begleitbuch zur Serie.		er Doktor und Donna Noble sind zurück in London um Zeit mit Donnas Familie zu verbringen und von ihren Abenteuern zu berichten.",
										"Sir Doctor from Tardis",
										"0000000000002",
										ArticleId.BOOK,
										categories.findById("BOOKFiktion").get().getCategoryName(),
										"Sir Doctor from Tardis",
										"Dr_Who_(316350537).jpg",
										"05.11.2011",
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("Stargate - Kommando SG-1",
										Money.of(EUR, 17.95),
										"Kurzbeschreibungen der Episoden der ersten Staffel.		Im Jahr 1994 fing alles mit einem Kinofilm an. Wenige Jahre später ging STARGATE SG-1 in Serie. Zehn Jahre lang wurde die Serie produziert, mit der ein ganz eigenes Universum erschaffen wurde. STARGATE SG-1 ist ein Science-Fiction-Phänomen, das längst alle Konkurrenten mit seiner Langlebigkeit in den Schatten gestellt hat.",
										"Ullstein",
										"0000000000003",
										ArticleId.BOOK,
										categories.findById("BOOKUnterhaltung").get().getCategoryName(),
										"Wolfgang Hohlbein",
										"Stargate-color.png",
										"07.10.2010",
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("Bastard Operator from Hell Volume 1",
										Money.of(EUR, 13.37),
										"Haben Sie jemals im Helpdesk gearbeitet und sind verzweifelt. Hier kommt der ultimative Ratgeber. Lesen Sie, sie Sie am Besten mit Nutzern umgehen.",
										"Bastard International Inc.",
										"0000000001337",
										ArticleId.BOOK,
										categories.findById("BOOKRatgeber").get().getCategoryName(),
										"Simon Travaglia",
										"19504216_2b65c99b30_z.jpg",
										"22.04.2014",
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("The Hitchhiker's Guide to the Galaxy",
										Money.of(EUR, 42.42),
										"Don't panic		Für Arthur Dent ist es ein ganz normaler Donnerstag, bis sein Haus von Planierraupen niedergewalzt wird. Kurz darauf wird allerdings auch die gesamte Erde von einem vogonischen Bautrupp plattgemacht, weil sie einer Hyperraum-Umgehungsstraße weichen muss. Aber da hat sich Arthurs bester Freund schon längst als Alien entpuppt, und sie sausen durchs Weltall mit nichts als ihren Badetüchern und einem harmlos wirkenden Buch, auf dem in großen, freundlichen Buchstaben »KEINE PANIK« steht. Und dabei hat das Wochenende gerade erst angefangen…",
										"Galaxy Readings",
										"0000000000042",
										ArticleId.BOOK,
										categories.findById("BOOKFiktion").get().getCategoryName(),
										"Douglas Adams",
										"256px-The_Hitchhiker's_Guide_to_the_Galaxy,_english.svg.png",
										"21.12.2005",
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("Tarzan",
										Money.of(EUR, 5.59),
										"Die Abenteuer um den Jungen der von Affen großgezogen wurde.		Er ist eine Legende, eine epochale Ikone, die bis heute nichts von ihrer Faszination verloren hat: Tarzan gilt als einer der erfolgreichsten Helden der Popkultur mit über 15 Millionen verkauften Büchern und zahllosen Film- und Comicadaptionen.",
										"Heyne",
										"0000452978267",
										ArticleId.BOOK,
										categories.findById("BOOKUnterhaltung").get().getCategoryName(),
										"Edgar Rice Burroughs",
										"Jungle_tales_of_tarzan.jpg",
										"14.07.1987",
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("Warum Männer nicht zuhören und Frauen schlecht einparken",
										Money.of(EUR, 14.95),
										"Der ultimative Geschlechterleitfaden.		Allan und Barbara Pease werfen einen amüsanten Blick auf die kleinen, aber bedeutsamen Unterschiede zwischen Mann und Frau.",
										"Ullstein",
										"0000000230785",
										ArticleId.BOOK,
										categories.findById("BOOKSachbuch").get().getCategoryName(),
										"Allan & Barbara Pease",
										"index.jpeg",
										"04.09.2009",
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("Necronomicon",
										Money.of(EUR, 14.95),
										"Wenn Sie es lesen werden sie überrascht sein. 		Das Buch Necronomicon ist die wohl berühmteste Schöpfung des US-amerikanischen Autors H. P. Lovecraft, der Anfang des 20. Jahrhunderts lebte. Dieses fiktive Buch ist ein Teil des Cthulhu-Mythos, wobei es in die Horror- und Fantasy-Literatur eingegangen ist wie kaum ein anderes, und zahlreiche andere Autoren zu Geschichten um dieses Werk inspiriert hat.",
										"Hell Systems",
										"0000000000666",
										ArticleId.BOOK,
										categories.findById("BOOKFiktion").get().getCategoryName(),
										"Abdul Alhazred",
										"Necronomicon_prop.jpg",
										"02.05.1630",
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("Der Bro-Code",
										Money.of(EUR, 14.95),
										"Wie verhält sich ein Bro. Hier finden Sie die Antwort.		Ein Bro ist ein Kumpel, mit dem man durch dick und dünn geht, dem man alles anvertrauen kann und der immer zu einem hält. Für Barney sind seine Bros wichtiger als die große Liebe.",
										"Touchstone",
										"9781439110003",
										ArticleId.BOOK,
										categories.findById("BOOKSachbuch").get().getCategoryName(),
										"Barney Stinson",
										"3067456215_8eb021fd2e_o.jpg",
										"26.03.2012",
										Money.of(EUR, 0.99))
		);
		
		//CDs
		
		articleCatalog.save(new Article("Depedro", 
										Money.of(EUR, 13.30), 
										"Depedros Songs sind von lateinischen Wurzeln, Weltmusik, mexikanischer Musik, Reggae, Blues, Salsa beeinflusst. Den würdevoll dahinkriechenden Songs hört man das Flimmern der Hitze und den staubigen Wind an. Stärker als der Rock der amerikanischen Weite prägt ein mexikanischer Einschlag das Klangbild.", 
										"Labels P Pias Recordings", 
										"1935739274926", 
										ArticleId.CD, 
										categories.findById("CDPop").get().getCategoryName(), 
										"Depedro",
										"depedro.jpg",
										"22.11.2010", 
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("The Last Resort", 
										Money.of(EUR, 17.95), 
										"2003 trat Trentemøller mit der selbstbetitelten Trentemøller EP wieder in Erscheinung. Für das darauf enthaltene Stück Le Champagne erhielt er bei den Danish DJ Awards 2004 die Auszeichnung „Upfront Release Of The Year“. Seit dem Jahr 2005 veröffentlicht Trentemøller auf Steve Bugs Label Poker Flat Recordings. Bei Liveauftritten wird er weiterhin von DJ T.O.M. unterstützt.", 
										"Poker Flat", 
										"7473957604531", 
										ArticleId.CD, 
										categories.findById("CDElectronic").get().getCategoryName(), 
										"Trentemoller",
										"theLastResort.jpg",
										"06.10.2006", 
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("Icky Thump", 
										Money.of(EUR, 9.67), 
										"Icky Thump zeigt, dass sich unser Duo erneut weiterentwickelt hat, aber doch seinen musikalischen und stilistischen Maximen treu geblieben ist. Es ist ein satt produziertes, laut tönendes, modernes Album geworden, das aber bei jeder Gelegenheit seine Wurzeln im amerikanischen Folk offenlegt. Schon bei den markanten ersten Riffs des Openers wird dem Hörer klar, dass es jetzt auf eine ebenso White-Stripes-typische wie extravagante Reise geht.", 
										"Beggars Group", 
										"1047602049582", 
										ArticleId.CD, 
										categories.findById("CDRock").get().getCategoryName(), 
										"The White Stripes",
										"ickyThumb.png",
										"15.06.2007", 
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("Radical", 
										Money.of(EUR, 8.94), 
										"Debüt Produktion des Rap Kollektives Odd Future, besteht aus Tyler the Creator, Earl Sweatshirt, Domo Genesis, Jasper Dolphin, Taco Benett, Syd Tha Kid, Frank Ocean und Hodgy Beats.", 
										"Odd Future", 
										"1057394857682", 
										ArticleId.CD, 
										categories.findById("CDHip Hop").get().getCategoryName(), 
										"Odd Future",
										"radical.png",
										"20.03.2009", 
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("Der Puppenspieler von Mexiko", 
										Money.of(EUR, 9.95), 
										"Wir kennen Roberto Blanco eigentlich aufgrund seiner Schlager Hits wie Ein bisschen Spaß muss sein. Dass er ein ausgezeichneter Entertainer ist, ist ja hinreichend bekannt. Da kommt ihm natürlich seine langjährige musikalische Erfahrung zugute. Jetzt hat er sich mit Unterstützung von Detlef Engelhard (Termidor Musikverlag), der sich einen Namen mit großartigen Produktionen im Jazz Bereich – u.a. 7 Grammy Nominierungen – gemacht hat, an ein neues Projekt gewagt: den Swing. Wir lernen einen ganz anderen grandiosen Sänger kennen. Swing ist eigentlich seine heimliche Liebe – und dass Roberto den berühmten Swing Sängern in nichts nachsteht, stellt er hier eindrucksvoll unter Beweis.", 
										"Sony Music", 
										"6395839421009", 
										ArticleId.CD, 
										categories.findById("CDPop").get().getCategoryName(), 
										"Roberto Blanco",
										"puppenspielerVonMexiko.jpg",
										"14.02.2005", 
										Money.of(EUR, 0.99))
		);
		
		//DVDs
		
		articleCatalog.save(new Article("Begotten", 
										Money.of(EUR, 6.66), 
										"Zu Beginn des Films sieht man ein abgeschiedenes Haus, in dem ein in Roben gekleideter Gott lebt, der sich selbst ausweidet und dann stirbt. Aus dem toten Körper erhebt sich eine Frau, Mutter Erde, die den toten Gott wiedererweckt und sich mit seinem Samen selbst schwängert. Mutter Erde irrt daraufhin schwanger durch die Welt, bis sie schließlich ein Kind gebärt, das die Ausmaße eines ausgewachsenen Mannes hat.", 
										"Theatre Of Material", 
										"0986523547681", 
										ArticleId.DVD, 
										categories.findById("DVDHorror").get().getCategoryName(), 
										"E. Elias Merhige",
										"begotten.jpg",
										"01.11.1991", 
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("Hot Fuzz", 
										Money.of(EUR, 5.55), 
										"Angel ist der Vorzeigepolizist der Londoner Polizei. Er macht seinen Job so gut, dass neben ihm jeder seiner Kollegen wie ein Stümper aussieht. Um den Rest der Londoner Polizei wieder in ein besseres Licht zu rücken wird Angel in das friedliche Dörfchen Sandford versetzt. Wo es keine Verbrechen gibt, kann Angel sein perfektionistisches Können auch nicht zur Schau stellen. Doch nach einer Reihe merkwürdiger Unfälle wittert Angel, das etwas in dem Dorf nicht mit rechten Dingen zugeht. ", 
										"Koch Media GmbH", 
										"9384756655091", 
										ArticleId.DVD, 
										categories.findById("DVDAction").get().getCategoryName(), 
										"Edgar Wright, Simon Pegg",
										"hotFuzz.jpg",
										"18.07.2007", 
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("Jeepers Creepers", 
										Money.of(EUR, 4.97), 
										"Das Geschwisterpaar Trish and Darryl befindet sich auf einem einsamen Highway auf dem langen Weg nach Hause. Plötzlich werden sie von einem unheimlichen Truck bedrängt und können nur mit Mühe einen Unfall verhindern. Wenig später sehen sie den Truck am Wegesrand neben einer verfallenen Kirche wieder. Sie beobachten, wie der Fahrer auffällige Bündel in eine Erdröhre wirft - und dann exakt in ihre Richtung blickt. Abermals können Trish und Darryl den wütenden Attacken des Trucks knapp entgehen. Wie magisch angezogen kehren die Teenager zu der Kirche zurück. Als sie die Erdröhre untersuchen, verliert Darryl den Halt und stürzt in eine düstere Höhle. Was er dort entdeckt, lässt ihm den Atem stocken - und ist doch nur die Ouvertüre zu einer Sinfonie des Schreckens, in der Darryl und Trish die Hauptrollen spielen...", 
										"Koch Media GmbH", 
										"0984567382453", 
										ArticleId.DVD, 
										categories.findById("DVDHorror").get().getCategoryName(), 
										"Victor Salva",
										"jeepersCreepers.jpg",
										"15.03.2007", 
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("Requiem for a Dream", 
										Money.of(EUR, 8.99), 
										"Eine Schar von Verlierern: Harry ist ein kleiner Dealer in Brooklyn, immer auf der Suche nach dem nächsten Schuß und voller Hoffnung auf das große Geld. Er und sein Freund Tyrone strecken Heroin und sind auf dem Weg nach Florida, um es dort an den Mann zu bringen. Harrys Freundin Marion bleibt in New York. Sie ist schön, aus gutem Haus - und ebenfalls hoffnungslos süchtig. Mit dem Dealer Big Tim tauscht sie Sex gegen Dope. Harrys Mutter hingegen scheint einen Treffer gelandet zu haben: Sie ist als Kandidatin für ihre Lieblings-TV-Show eingeladen. Bis zu ihrem Auftritt möchte sie mit Hilfe von Appetitzüglern abnehmen. Doch durch ihren Pillenkonsum verliert sie völlig den Kontakt zur Realität...", 
										"Highlight", 
										"1234567890123", 
										ArticleId.DVD, 
										categories.findById("DVDDrama").get().getCategoryName(), 
										"Darren Aronofsky",
										"requiemForADream.jpg",
										"04.02.2003", 
										Money.of(EUR, 0.99))
		);
		
		articleCatalog.save(new Article("American Werewolf in London", 
										Money.of(EUR, 8.59), 
										"David (David Naughton) und Jack (Griffin Dunne) sind zwei amerikanische Studenten, die auf ihrem Europatrip durch das schottische Hochland trampen. Müde und durchfroren erreichen sie den kleinen Pub The Slaughtered Lamb. Dass die Leute in dieser Gegend ein wenig merkwürdig sind, haben die beiden schon festgestellt, aber die Bevölkerung in dem kleinen Nest benimmt sich ausgesprochen sonderbar. Nachdem man ihnen reichlich widerwillig eine Tasse Tee serviert hat, machen David und Jack sich schnellstmöglich wieder auf - nicht ohne noch eine letzte eindringliche Warnung mit auf den Weg zu bekommen: Bleibt auf der Straße und haltet euch vom Moor fern! und Hütet euch vor dem Mond.", 
										"Universal Pictures", 
										"02938475395678", 
										ArticleId.DVD, 
										categories.findById("DVDHorror").get().getCategoryName(), 
										"John Landis",
										"werewolfInLondon.jpg",
										"05.11.2009", 
										Money.of(EUR, 0.99))
		);
		
		
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

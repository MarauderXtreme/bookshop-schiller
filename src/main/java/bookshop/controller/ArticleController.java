package bookshop.controller;

import java.util.Optional;

import static org.joda.money.CurrencyUnit.*;
import org.joda.money.Money;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookshop.model.ArticleManagement;
import bookshop.model.Article;
import bookshop.model.Article.ArticleId;

@Controller
class ArticleController {
	private final ArticleManagement articleCatalog;
	private final Inventory<InventoryItem> inventory;
	private final MessageSourceAccessor messageSourceAccessor; 

	@Autowired
	public ArticleController(ArticleManagement articleCatalog, Inventory<InventoryItem> inventory, MessageSource messageSource) {

		this.articleCatalog = articleCatalog;
		this.inventory = inventory;
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}
	
	/*@RequestMapping("/all")
	public String books(ModelMap modelMap, String name) {

		modelMap.addAttribute("catalog", articleCatalog.findAll());
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "all";
	}*/
	
	//Initilize Catalog
	@RequestMapping("/articles")
	public String articles(ModelMap modelMap, String name) {

		modelMap.addAttribute("catalog", articleCatalog.findAll());
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "articles";
	}
	
	@RequestMapping("/books")
	public String books(ModelMap modelMap, String name) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.BOOK));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "books";
	}
	
	@RequestMapping("/cds")
	public String cds(ModelMap modelMap, String name) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.CD));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "cds";
	}
	
	@RequestMapping("/dvds")
	public String dvds(ModelMap modelMap, String name) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.DVD));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "dvds";
	}
	
	//Add Article
	@RequestMapping(value="/addBook", method=RequestMethod.POST)
	public String addBook(@RequestParam("titleArticle") String title,
	@RequestParam("beschreibungArticle") String beschreibung,
	@RequestParam("priceArticle") double price,
	@RequestParam("idArticle") String isbn,
	@RequestParam("publisherArticle") String publisher,
	@RequestParam("authorArticle") String author){
	
	Article article = new Article(title, Money.of(EUR, price), beschreibung,
			publisher, isbn, ArticleId.BOOK);
	
	article.setAuthor(author);
		
	articleCatalog.save(article);
	
	System.out.println(article.getAuthor());
	
	Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
	Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);

	 return "redirect:books";

	}

	@RequestMapping(value="/addCD", method=RequestMethod.POST)
	public String addCD(@RequestParam("titleArticle") String title,
	@RequestParam("beschreibungArticle") String beschreibung,
	@RequestParam("priceArticle") double price,
	@RequestParam("idArticle") String isbn,
	@RequestParam("publisherArticle") String publisher,
	@RequestParam("interpretArticle") String interpret){
	
	Article article = new Article(title, Money.of(EUR, price), beschreibung,
			publisher, isbn, ArticleId.CD);
	
	article.setInterpret(interpret);
		
	articleCatalog.save(article);
		
	Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
	Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);

	 return "redirect:cds";

	}
	
	@RequestMapping(value="/addDVD", method=RequestMethod.POST)
	public String addDVD(@RequestParam("titleArticle") String title,
	@RequestParam("beschreibungArticle") String beschreibung,
	@RequestParam("priceArticle") double price,
	@RequestParam("idArticle") String isbn,
	@RequestParam("publisherArticle") String publisher,
	@RequestParam("directorArticle") String director){
	
	Article article = new Article(title, Money.of(EUR, price), beschreibung,
			publisher, isbn, ArticleId.DVD);
	
	article.setDirector(director);
		
	articleCatalog.save(article);
		
	Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
	Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);

	 return "redirect:dvds";

	}
	
	//Delete Article
	@RequestMapping(value="/deleteBook", method=RequestMethod.POST)
	public String deleteBook(@RequestParam("article") Article article){
		//Test: Vor des Entfernens
		System.out.println(inventory.count());

		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		inventory.delete(item.get().getIdentifier());
		
		//Test: Nach des Entfernens
		System.out.println(inventory.count());
		
		articleCatalog.delete(article.getIdentifier());
		
		System.out.println(articleCatalog.count());

		//return "books";
		return "redirect:books";
	}
	
	@RequestMapping(value="/deleteCD", method=RequestMethod.POST)
	public String deleteCD(@RequestParam("article") Article article){
		//Test: Vor des Entfernens
		System.out.println(inventory.count());

		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		inventory.delete(item.get().getIdentifier());
		
		//Test: Nach des Entfernens
		System.out.println(inventory.count());
		
		articleCatalog.delete(article.getIdentifier());
		
		System.out.println(articleCatalog.count());

		//return "books";
		return "redirect:cds";
	}
	
	@RequestMapping(value="/deleteDVD", method=RequestMethod.POST)
	public String deleteDVD(@RequestParam("article") Article article){
		//Test: Vor des Entfernens
		System.out.println(inventory.count());

		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		inventory.delete(item.get().getIdentifier());
		
		//Test: Nach des Entfernens
		System.out.println(inventory.count());
		
		articleCatalog.delete(article.getIdentifier());
		
		System.out.println(articleCatalog.count());

		//return "books";
		return "redirect:dvds";
	}
	
	//Increase the Units of an Article
	@RequestMapping(value="/increase", method=RequestMethod.POST)
	public String increaseUnits(@RequestParam("article") Article article, Model model){
		
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		System.out.println("UNITS: " + item.get().getQuantity());
		item.get().increaseQuantity(Units.ONE);
		
		//Test: Erhöhung der Units
		System.out.println("UNITS: " + item.get().getQuantity());
		
		/*articleCatalog.findByName(article.getName());
		item.Units.add(Units.of(i));
		*/
		
		/*Quantity quantity = item.get().getQuantity();
		model.addAttribute("quantity", quantity);
		*/
		
		articleCatalog.save(article);
		
		return "redirect:detail/" + article.getIdentifier();
	}
	
	//Decrease the Units of an Article
	@RequestMapping(value="/decrease", method=RequestMethod.POST)
	public String decreaseUnits(@RequestParam("article") Article article, Model model){
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		item.get().decreaseQuantity(Units.ONE);
		
		//Test: Verringern der Units
		System.out.println("UNITS: " + item.get().getQuantity());
		
		articleCatalog.save(article);
		
		return "redirect:detail/" + article.getIdentifier();
	}
	
	//Initilize the Details of an Article
	@RequestMapping("/detail/{pid}")
	public String detail(@PathVariable("pid") Article article, Model model) {
		
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);

		model.addAttribute("book", article);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(Units.ZERO));

		return "detail";
	}
}

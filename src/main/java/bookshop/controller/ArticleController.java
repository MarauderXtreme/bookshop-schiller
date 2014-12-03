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
	
	@RequestMapping("/books")
	public String books(ModelMap modelMap, String name) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.BOOK));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "books";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@RequestParam("titleArticle") String title,
	@RequestParam("beschreibungArticle") String beschreibung,
	@RequestParam("priceArticle") double price,
	@RequestParam("idArticle") String isbn,
	@RequestParam("publisherArticle") String publisher){
	
	Article article = new Article(title, Money.of(EUR, price), beschreibung,
			publisher, isbn, ArticleId.BOOK);
		
	articleCatalog.save(article);
	
	Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
	Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);

	 return "redirect:books";

	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("article") Article article){
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
	
	@RequestMapping(value="/decrease", method=RequestMethod.POST)
	public String decreaseUnits(@RequestParam("article") Article article, Model model){
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		item.get().decreaseQuantity(Units.ONE);
		
		//Test: Verringern der Units
		System.out.println("UNITS: " + item.get().getQuantity());
		
		articleCatalog.save(article);
		
		return "redirect:detail/" + article.getIdentifier();
	}
	
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

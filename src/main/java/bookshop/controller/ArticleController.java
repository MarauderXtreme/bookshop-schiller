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
	 
	@RequestMapping("/books")
	public String books(ModelMap modelMap, String name) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.BOOK));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "books";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)

	public String add(@RequestParam("nameArticle") String name,
	@RequestParam("beschreibungArticle") String beschreibung,
	@RequestParam("titleArticle") String title){

	articleCatalog.save(new Article(name, Money.of(EUR, 9.99), beschreibung,
	title, 123, ArticleId.BOOK));

	 return "redirect:books";

	}
	
	@RequestMapping("/detail/{pid}")
	public String detail(@PathVariable("pid") Article article, Model model) {
		
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.ZERO);

		model.addAttribute("book", article);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(Units.ZERO));

		return "detail";
	}
}

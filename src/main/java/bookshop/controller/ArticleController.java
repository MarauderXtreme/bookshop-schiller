package bookshop.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import bookshop.model.CategoryManagement;

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
	
	@RequestMapping("/addbook")
	public String addBook(ModelMap modelMap, String name) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.DVD));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "addbook";
	}

	@RequestMapping("/addcd")
	public String addCd(ModelMap modelMap, String name) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.DVD));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "addcd";
	}
	
	@RequestMapping("/adddvd")
	public String addDvd(ModelMap modelMap, String name) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.DVD));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "adddvd";
	}
	
	//search Articles
	@RequestMapping("/searcharticles")
	public String searchArticles(ModelMap modelMap, @RequestParam("typeInput") int typeInput, @RequestParam("input") String input){

		if(typeInput == 1){
			modelMap.addAttribute("catalog", articleCatalog.findByName(input));
		}
		
		if(typeInput == 2){
			modelMap.addAttribute("catalog", articleCatalog.findByPublisher(input));
		}
		
		if(typeInput == 3){
			modelMap.addAttribute("catalog", articleCatalog.findById(input));
		}
		
		if(typeInput == 4){
			modelMap.addAttribute("catalog", articleCatalog.findByAuthor(input));
		}
		
		if(typeInput == 5){
			modelMap.addAttribute("catalog", articleCatalog.findByInterpret(input));
		}
			
		if(typeInput == 6){
			modelMap.addAttribute("catalog", articleCatalog.findByDirector(input));
		}
		
		if(typeInput == 7){
			
			/*Set<String> categoryList = new HashSet<String>();
			List<String> arr = new ArrayList<String>();
						
			while(articleCatalog.findAll().iterator().hasNext()){
				String[] bla = articleCatalog.findAll().iterator().next().getCategoryList().toString().split(", ");
				if(bla.equals(input)){
					
				}
					
			}
			
			String[] bla = arr.toString().split(", ");
			
			for(int i=0; i<bla.length; i++){
				categoryList.add(bla[i]);
			}
			
			while(articleCatalog.findAll().iterator().hasNext()){
				articleCatalog.findAll().iterator().next().getCategoryList();
			}
			
			modelMap.addAllAttribute("catalog", categories);*/
			
			modelMap.addAttribute("catalog", articleCatalog.findByCategory(input));
		}
			
		return "search";	//!? Wird nicht als URL angezeigt sondern "searchArticles"
		
	}
	
	//Add Article
	@RequestMapping(value="/addBook", method=RequestMethod.POST)
	public String addBook(@RequestParam("titleArticle") String title,
	@RequestParam("beschreibungArticle") String beschreibung,
	@RequestParam("priceArticle") double price,
	@RequestParam("idArticle") String isbn,
	@RequestParam("publisherArticle") String publisher,
	@RequestParam("authorArticle") String author,
	@RequestParam("categoryArticle") String category){
	
	Article article = new Article(title, Money.of(EUR, price), beschreibung,
			publisher, isbn, ArticleId.BOOK, category, author);
	
	//article.setAuthor(author);
		
	articleCatalog.save(article);
	
	//System.out.println(article.getAuthor());
	
	
	InventoryItem item = new InventoryItem(article, Units.TEN);
	inventory.save(item);
	
	//System.out.println("ITEM: " + item);
	
	//Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);
	
	

	 return "redirect:books";

	}

	@RequestMapping(value="/addCD", method=RequestMethod.POST)
	public String addCD(@RequestParam("titleArticle") String title,
	@RequestParam("beschreibungArticle") String beschreibung,
	@RequestParam("priceArticle") double price,
	@RequestParam("idArticle") String isbn,
	@RequestParam("publisherArticle") String publisher,
	@RequestParam("interpretArticle") String interpret,
	@RequestParam("categoryArticle") String category){
	
	Article article = new Article(title, Money.of(EUR, price), beschreibung,
			publisher, isbn, ArticleId.CD, category, interpret);
	
	//article.setInterpret(interpret);
		
	articleCatalog.save(article);
		
	//Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
	//Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);

	InventoryItem item = new InventoryItem(article, Units.TEN);
	inventory.save(item);
	
	 return "redirect:cds";

	}
	
	@RequestMapping(value="/addDVD", method=RequestMethod.POST)
	public String addDVD(@RequestParam("titleArticle") String title,
	@RequestParam("beschreibungArticle") String beschreibung,
	@RequestParam("priceArticle") double price,
	@RequestParam("idArticle") String isbn,
	@RequestParam("publisherArticle") String publisher,
	@RequestParam("directorArticle") String director,
	@RequestParam("categoryArticle") String category){
	
	Article article = new Article(title, Money.of(EUR, price), beschreibung,
			publisher, isbn, ArticleId.DVD, category, director);
	
	//article.setDirector(director);
		
	articleCatalog.save(article);
		
	//Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
	//Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);

	InventoryItem item = new InventoryItem(article, Units.TEN);
	inventory.save(item);
	
	 return "redirect:dvds";

	}
	
	//Delete Article
	@RequestMapping(value="/deleteArticle", method=RequestMethod.POST)
	public String deleteArticle(@RequestParam("article") Article article){

		if(article.getType()==ArticleId.BOOK){
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		inventory.delete(item.get().getIdentifier());
		
		articleCatalog.delete(article.getIdentifier());
		
		return "redirect:books";
		}
		else if(article.getType()==ArticleId.CD){
			Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
			inventory.delete(item.get().getIdentifier());
			
			articleCatalog.delete(article.getIdentifier());
			
			return "redirect:cds";
		}
		
		else {
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		inventory.delete(item.get().getIdentifier());
		
		articleCatalog.delete(article.getIdentifier());
		
		return "redirect:dvds";
		}
	}
	/*
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
	}*/
	
	
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
	
	@RequestMapping("/editarticle/{pid}")
	public String editarticle(@PathVariable("pid") Article article, Model model) {
		
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);

		model.addAttribute("book", article);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(Units.ZERO));

		return "editarticle";
	}

	//Change Informations of an Article
		@RequestMapping(value="/setNewTitle", method=RequestMethod.POST)
		public String setNewTitle(@RequestParam("article") Article article, @RequestParam("newname") String name, Model model){
			
			
			System.out.println("Title: " + article.getName());
			
			article.setName(name);
			
			articleCatalog.save(article);
			
			return "redirect:editarticle/" + article.getIdentifier();
		}
			
			@RequestMapping(value="/setNewPublisher", method=RequestMethod.POST)
			public String setNewPublisher(@RequestParam("article") Article article, @RequestParam("newpublisher") String publisher, Model model){
				
				
				System.out.println("Title: " + article.getName());
				
				article.setPublisher(publisher);
				
				articleCatalog.save(article);
				
				return "redirect:editarticle/" + article.getIdentifier();
			}
			
			@RequestMapping(value="/setNewDescription", method=RequestMethod.POST)
			public String setNewDescription(@RequestParam("article") Article article, @RequestParam("descriptiontext") String beschreibung){
				
				article.setBeschreibung(beschreibung);
				
				articleCatalog.save(article);
				
				return "redirect:editarticle/" + article.getIdentifier();
			}
			
			@RequestMapping(value="/setNewIsbn", method=RequestMethod.POST)
			public String setNewIsbn(@RequestParam("article") Article article, @RequestParam("newisbn") String isbn, Model model){
				
				
				System.out.println("Title: " + article.getName());
				
				article.setId(isbn);
				
				articleCatalog.save(article);
				
				return "redirect:editarticle/" + article.getIdentifier();
			}
			
			@RequestMapping(value="/setNewPrice", method=RequestMethod.POST)
			public String setNewPrice(@RequestParam("article") Article article, @RequestParam("newprice") double price, Model model){
				
				System.out.println("Title: " + article.getName());
				
				article.setPrice(Money.of(EUR, price));
				
				articleCatalog.save(article);
				
				return "redirect:editarticle/" + article.getIdentifier();
			}
			
			@RequestMapping(value="/setNewCategory", method=RequestMethod.POST)
			public String setNewCatgory(@RequestParam("article") Article article, @RequestParam("newcategory") String category, Model model){
				
				System.out.println("Title: " + article.getName());
				
				article.addCategory(category);
								
				articleCatalog.save(article);
				
				return "redirect:editarticle/" + article.getIdentifier();
			}
			
			@RequestMapping(value="/setNewImage", method=RequestMethod.POST)
			public String setNewImage(@RequestParam("article") Article article, @RequestParam("newimage") String image, Model model){
								
				article.setImage(image);
				
				articleCatalog.save(article);
				
				return "redirect:editarticle/" + article.getIdentifier();
			}
			
			//Set Specific Informations
			@RequestMapping(value="/setNewAuthor", method=RequestMethod.POST)
			public String setNewAutor(@RequestParam("article") Article article, @RequestParam("newauthor") String author, Model model){

				System.out.println("Autor: " + article.getAuthor());
							
				article.setAuthor(author);
				
				System.out.println("Autor: " + article.getAuthor());
				
				articleCatalog.save(article);
				
				return "redirect:editarticle/" + article.getIdentifier();
			}
			
			@RequestMapping(value="/setNewInterpret", method=RequestMethod.POST)
			public String setNewInterpret(@RequestParam("article") Article article, @RequestParam("newInterpret") String interpret, Model model){
							
				article.setInterpret(interpret);
				
				articleCatalog.save(article);
				
				return "redirect:editarticle/" + article.getIdentifier();
			}
			
			@RequestMapping(value="/setNewDirector", method=RequestMethod.POST)
			public String setNewDirector(@RequestParam("article") Article article, @RequestParam("newdirector") String director, Model model){
							
				article.setDirector(director);
				
				articleCatalog.save(article);
				
				return "redirect:editarticle/" + article.getIdentifier();
			}
			
			
		
		@RequestMapping(value="/increase", method=RequestMethod.POST)
		public String increaseUnits(@RequestParam("article") Article article, @RequestParam("addquan") long amount, Model model){
			
			Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
			System.out.println("ITEM: "+ item);
			
			System.out.println("UNITS: " + item.get().getQuantity());
			//item.get().increaseQuantity(Units.ONE);
			item.get().increaseQuantity(Units.of(amount));
			
			//Test: Erhöhung der Units
			System.out.println("UNITS: " + item.get().getQuantity());
			
			/*articleCatalog.findByName(article.getName());
			item.Units.add(Units.of(i));
			*/
			
			/*Quantity quantity = item.get().getQuantity();
			model.addAttribute("quantity", quantity);
			*/
			
			articleCatalog.save(article);
			
			return "redirect:editarticle/" + article.getIdentifier();
		}
		
		//Decrease the Units of an Article
		@RequestMapping(value="/decrease", method=RequestMethod.POST)
		public String decreaseUnits(@RequestParam("article") Article article, @RequestParam("removequan") long amount, Model model){
			
			Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
			item.get().decreaseQuantity(Units.of(amount));
			
			//Test: Verringern der Units
			System.out.println("UNITS: " + item.get().getQuantity());
			
			articleCatalog.save(article);
			
			return "redirect:editarticle/" + article.getIdentifier();
		}
		
		//Delete Information(Categories) of an Article
		@RequestMapping(value="/deletecategory", method=RequestMethod.POST)
		public String deleteCategory(@RequestParam("article") Article article, @RequestParam("categorytodelete") String category){
			
			article.removeCategory(category);
			
			articleCatalog.save(article);
			
			return "redirect:editarticle/" + article.getIdentifier();
		}
		
}

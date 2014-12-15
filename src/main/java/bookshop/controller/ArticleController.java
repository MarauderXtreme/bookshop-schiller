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
public class ArticleController {
	
	private final ArticleManagement articleCatalog;
	private final Inventory<InventoryItem> inventory;
	private final MessageSourceAccessor messageSourceAccessor; 

	/**
	 * Constructor for the ArticleController.
	 * @param articleCatalog
	 * @param inventory
	 * @param messageSource
	 */
	@Autowired
	public ArticleController(ArticleManagement articleCatalog, Inventory<InventoryItem> inventory, MessageSource messageSource) {
		this.articleCatalog = articleCatalog;
		this.inventory = inventory;
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}
	
	
	//Initilize Cataloglists
	
	
	/*
	@RequestMapping("/")
	public String news(ModelMap modelMap, String name) {
		
		Iterable<Article> b = articleCatalog.findAll();
		
		for(int i=0; i<=4; i++){
			b
		}

		modelMap.addAttribute("catalog", articleCatalog.findAll());
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "articles";
	}*/
	
	
	/**
	 * Maps a list of all articles to modelMap.
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/article")
	public String articles(ModelMap modelMap) {

		modelMap.addAttribute("catalog", articleCatalog.findAll());
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "articles";
	}
	
	/**
	 * Maps a list of all articles of type book to modelMap.
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/article/book")
	public String books(ModelMap modelMap) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.BOOK));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "books";
	}
	
	/**
	 * Maps a list of all articles of type cd to modelMap.
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/article/cd")
	public String cds(ModelMap modelMap) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.CD));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "cds";
	}
	
	/**
	 * Maps a list of all articles of type dvd to modelMap.
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/article/dvd")
	public String dvds(ModelMap modelMap) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.DVD));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "dvds";
	}
	
	/**
	 * Maps a list of all articles of type book to modelMap for the add book html.
	 * @param modelMap
	 * @return
	 */
	//@RequestMapping("/article/book/add")
	@RequestMapping("/article/addbook")
	public String addBook(ModelMap modelMap) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.BOOK));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "addbook";
	}

	/**
	 * Maps a list of all articles of type cd to modelMap for the add cd html.
	 * @param modelMap
	 * @return
	 */
	//@RequestMapping("/article/cd/add")
	@RequestMapping("/article/addcd")
	public String addCd(ModelMap modelMap) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.CD));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "addcd";
	}
	
	/**
	 * Maps a list of all articles of type dvd to modelMap for the add dvd html.
	 * @param modelMap
	 * @return
	 */
	//@RequestMapping("/article/dvd/add")
	@RequestMapping("/article/adddvd")
	public String addDvd(ModelMap modelMap) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.DVD));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "adddvd";
	}
	
	
	//search Articles
	
	/**
	 * Maps a list of all articles of a given attribute type and a given input to modelMap.
	 * @param modelMap
	 * @param inputType
	 * @param input
	 * @return
	 */
	//@RequestMapping(value="/article/search", method=RequestMethod.POST)
	@RequestMapping(value="/article/search", method=RequestMethod.POST)
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
			if(!(articleCatalog.findByAuthor(input)==null))
				modelMap.addAttribute("catalog", articleCatalog.findByAuthor(input));
			if(!(articleCatalog.findByInterpret(input)==null))
				modelMap.addAttribute("catalog", articleCatalog.findByInterpret(input));
			if(!(articleCatalog.findByDirector(input)==null))
				modelMap.addAttribute("catalog", articleCatalog.findByDirector(input));
		}
		/*
		if(typeInput == 5){
			modelMap.addAttribute("catalog", articleCatalog.findByInterpret(input));
		}
			
		if(typeInput == 6){
			modelMap.addAttribute("catalog", articleCatalog.findByDirector(input));
		}
		*/
		if(typeInput == 5){
			
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
			
		return "search";	//!? Wird nicht als URL angezeigt sondern "article/search"
		
	}
	
	
	//Add Article
	
	/**
	 * Adds a new article of type book with the given attributes to the catalog and the inventory.
	 * @param title
	 * @param description
	 * @param price
	 * @param isbn
	 * @param publisher
	 * @param author
	 * @param category
	 * @return
	 */
	@RequestMapping(value="/article/book/new", method=RequestMethod.POST)
	public String addBook(	@RequestParam("titleArticle") String title,
							@RequestParam("beschreibungArticle") String beschreibung,
							@RequestParam("priceArticle") double price,
							@RequestParam("idArticle") String isbn,
							@RequestParam("publisherArticle") String publisher,
							@RequestParam("authorArticle") String author,
							@RequestParam("categoryArticle") String category){
	
		Article article = new Article(	title,
										Money.of(EUR, price),
										beschreibung,
										publisher,
										isbn,
										ArticleId.BOOK,
										category,
										author,
										"01.01.2015",
										Money.of(EUR, 0.99)
										);
	
		//article.setAuthor(author);
			
		articleCatalog.save(article);
		
		//System.out.println(article.getAuthor());
		
		
		InventoryItem item = new InventoryItem(article, Units.TEN);
		inventory.save(item);
		
		//System.out.println("ITEM: " + item);
		
		//Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);
		
	

		return "redirect:/article/book";

	}

	/**
	 * Adds a new article of type cd with the given attributes to the catalog and the inventory.
	 * @param title
	 * @param description
	 * @param price
	 * @param isbn
	 * @param publisher
	 * @param interpret
	 * @param category
	 * @return
	 */
	@RequestMapping(value="/article/cd/new", method=RequestMethod.POST)
	public String addCD(@RequestParam("titleArticle") String title,
						@RequestParam("beschreibungArticle") String beschreibung,
						@RequestParam("priceArticle") double price,
						@RequestParam("idArticle") String isbn,
						@RequestParam("publisherArticle") String publisher,
						@RequestParam("interpretArticle") String interpret,
						@RequestParam("categoryArticle") String category){
	
	Article article = new Article(title, Money.of(EUR, price), beschreibung,
			publisher, isbn, ArticleId.CD, category, interpret, "01.01.2015", Money.of(EUR, 0.99));
	
	//article.setInterpret(interpret);
		
	articleCatalog.save(article);
		
	//Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
	//Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);

	InventoryItem item = new InventoryItem(article, Units.TEN);
	inventory.save(item);
	
	 return "redirect:/article/cd";

	}
	
	/**
	 * Adds a new article of type dvd with the given attributes to the catalog and the inventory.
	 * @param title
	 * @param description
	 * @param price
	 * @param isbn
	 * @param publisher
	 * @param director
	 * @param category
	 * @return
	 */
	@RequestMapping(value="/article/dvd/new", method=RequestMethod.POST)
	public String addDVD(@RequestParam("titleArticle") String title,
	@RequestParam("beschreibungArticle") String beschreibung,
	@RequestParam("priceArticle") double price,
	@RequestParam("idArticle") String isbn,
	@RequestParam("publisherArticle") String publisher,
	@RequestParam("directorArticle") String director,
	@RequestParam("categoryArticle") String category){
	
	Article article = new Article(title, Money.of(EUR, price), beschreibung,
			publisher, isbn, ArticleId.DVD, category, director, "01.01.2015", Money.of(EUR, 0.99));
	
	//article.setDirector(director);
		
	articleCatalog.save(article);
		
	//Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
	//Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);

	InventoryItem item = new InventoryItem(article, Units.TEN);
	inventory.save(item);
	
	 return "redirect:/article/dvd";

	}
	

	//Delete Article
	
	/**
	 * Deletes a given article from the inventory and catalog.
	 * @param article
	 * @return
	 */
	@RequestMapping(value="/article/delete", method=RequestMethod.POST)
	public String deleteArticle(@RequestParam("article") Article article){

		if(article.getType()==ArticleId.BOOK){
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		inventory.delete(item.get().getIdentifier());
		
		articleCatalog.delete(article.getIdentifier());
		
		return "redirect:/article/book";
		}
		else if(article.getType()==ArticleId.CD){
			Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
			inventory.delete(item.get().getIdentifier());
			
			articleCatalog.delete(article.getIdentifier());
			
			return "redirect:/article/cd";
		}
		
		else {
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		inventory.delete(item.get().getIdentifier());
		
		articleCatalog.delete(article.getIdentifier());
		
		return "redirect:/article/dvd";
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
	
	/**
	 * Maps a given article with its quantity to model of detail html.
	 * @param article
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail/{pid}")
	public String detail(@PathVariable("pid") Article article, Model model) {
		
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);

		model.addAttribute("book", article);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(Units.ZERO));

		return "detail";
	}
	
	/**
	 * Maps a given article with its quantity to model of editarticle html.
	 * @param article
	 * @param model
	 * @return
	 */
	//@RequestMapping("/article/{pid}/edit")
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
	
	/**
	 * Sets a given title to a given article and saves this article to the catalog again.
	 * @param article
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/article/set/title", method=RequestMethod.POST)
	public String setNewTitle(@RequestParam("article") Article article, @RequestParam("newname") String name){
			
			
			System.out.println("Title: " + article.getName());
			
			article.setName(name);
			
			articleCatalog.save(article);
			
			//return "redirect:article/" + article.getIdentifier() + "edit";
			return "redirect:/editarticle/" + article.getIdentifier();
	}
		
	/**
	 * Sets a given publisher to a given article and saves this article to the catalog again.
	 * @param article
	 * @param publisher
	 * @return
	 */
	@RequestMapping(value="/article/set/publisher", method=RequestMethod.POST)
	public String setNewPublisher(@RequestParam("article") Article article, @RequestParam("newpublisher") String publisher){
				
				
				System.out.println("Title: " + article.getName());
				
				article.setPublisher(publisher);
				
				articleCatalog.save(article);
				
				//return "redirect:article/" + article.getIdentifier() + "edit";
				return "redirect:/editarticle/" + article.getIdentifier();
			}
			
	/**
	 * Sets a given description to a given article and saves this article to the catalog again.
	 * @param article
	 * @param description
	 * @return
	 */
	@RequestMapping(value="/article/set/description", method=RequestMethod.POST)
	public String setNewDescription(@RequestParam("article") Article article, @RequestParam("descriptiontext") String beschreibung){
				
				article.setBeschreibung(beschreibung);
				
				articleCatalog.save(article);
				
				//return "redirect:article/" + article.getIdentifier() + "edit";
				return "redirect:/editarticle/" + article.getIdentifier();
			}
			
	/**
	 * Sets a given isbn to a given article and saves this article to the catalog again.
	 * @param isbn
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/article/set/isbn", method=RequestMethod.POST)
	public String setNewIsbn(@RequestParam("article") Article article, @RequestParam("newisbn") String isbn){
				
				
				System.out.println("Title: " + article.getName());
				
				article.setId(isbn);
				
				articleCatalog.save(article);
				
				//return "redirect:article/" + article.getIdentifier() + "edit";
				return "redirect:/editarticle/" + article.getIdentifier();
			}
			
	/**
	 * Sets a given price to a given article and saves this article to the catalog again.
	 * @param article
	 * @param price
	 * @return
	 */
	@RequestMapping(value="/article/set/price", method=RequestMethod.POST)
	public String setNewPrice(@RequestParam("article") Article article, @RequestParam("newprice") double price){
				
				System.out.println("Title: " + article.getName());
				
				article.setPrice(Money.of(EUR, price));
				
				articleCatalog.save(article);
				
				//return "redirect:article/" + article.getIdentifier() + "edit";
				return "redirect:/editarticle/" + article.getIdentifier();
			}
		
	/**
	 * Adds a given category to the Categorylist of a given article and saves this article to the catalog again.
	 * @param article
	 * @param category
	 * @return
	 */
	@RequestMapping(value="/article/set/category", method=RequestMethod.POST)
	public String setNewCatgory(@RequestParam("article") Article article, @RequestParam("newcategory") String category){
				
				System.out.println("Title: " + article.getName());
				
				article.addCategory(category);
								
				articleCatalog.save(article);
				
				//return "redirect:article/" + article.getIdentifier() + "edit";
				return "redirect:/editarticle/" + article.getIdentifier();
			}
			
	/**
	 * Sets a given image to a given article and saves this article to the catalog again.
	 * @param article
	 * @param image
	 * @return
	 */
	@RequestMapping(value="/article/set/image", method=RequestMethod.POST)
	public String setNewImage(@RequestParam("article") Article article, @RequestParam("newimage") String image){
								
				article.setImage(image);
				
				articleCatalog.save(article);
				
				//return "redirect:article/" + article.getIdentifier() + "edit";
				return "redirect:/editarticle/" + article.getIdentifier();
			}
	
	
	//Set Specific Informations
	
	/**
	 * Sets a given author to a given article and saves this article to the catalog again.
	 * @param article
	 * @param author
	 * @return
	 */
	@RequestMapping(value="/article/set/author", method=RequestMethod.POST)
	public String setNewAutor(@RequestParam("article") Article article, @RequestParam("newauthor") String author){

				System.out.println("Autor: " + article.getAuthor());
							
				article.setAuthor(author);
				
				System.out.println("Autor: " + article.getAuthor());
				
				articleCatalog.save(article);
				
				//return "redirect:article/" + article.getIdentifier() + "edit";
				return "redirect:/editarticle/" + article.getIdentifier();
	}
			
	/**
	 * Sets a given interpret to a given article and saves this article to the catalog again.
	 * @param article
	 * @param interpret
	 * @return
	 */
	@RequestMapping(value="/article/set/interpret", method=RequestMethod.POST)
	public String setNewInterpret(@RequestParam("article") Article article, @RequestParam("newInterpret") String interpret){
							
				article.setInterpret(interpret);
				
				articleCatalog.save(article);
				
				//return "redirect:article/" + article.getIdentifier() + "edit";
				return "redirect:/editarticle/" + article.getIdentifier();
	}
	
	/**
	 * Sets a given director to a given article and saves this article to the catalog again.
	 * @param article
	 * @param director
	 * @return
	 */	
	@RequestMapping(value="/article/set/director", method=RequestMethod.POST)
	public String setNewDirector(@RequestParam("article") Article article, @RequestParam("newdirector") String director){
							
				article.setDirector(director);
				
				articleCatalog.save(article);
				
				//return "redirect:article/" + article.getIdentifier() + "edit";
				return "redirect:/editarticle/" + article.getIdentifier();
	}
			
	/**
	 * Increases a given amount of a given article and saves this article to the catalog again.
	 * @param article
	 * @param amount
	 * @return
	 */
	@RequestMapping(value="/article/amount/increase", method=RequestMethod.POST)
	public String increaseUnits(@RequestParam("article") Article article, @RequestParam("addquan") long amount){
			
			Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
			
			item.get().increaseQuantity(Units.of(amount));

			/*articleCatalog.findByName(article.getName());
			item.Units.add(Units.of(i));
			*/
			
			/*Quantity quantity = item.get().getQuantity();
			model.addAttribute("quantity", quantity);
			*/
			
			articleCatalog.save(article);
			
			//return "redirect:article/" + article.getIdentifier() + "edit";
			return "redirect:/editarticle/" + article.getIdentifier();
		}
		
	/**
	 * Decreases a given amount of a given article and saves this article to the catalog again.
	 * @param article
	 * @param amount
	 * @return
	 */
	@RequestMapping(value="/article/amount/decrease", method=RequestMethod.POST)
	public String decreaseUnits(@RequestParam("article") Article article, @RequestParam("removequan") long amount){
			
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		
		item.get().decreaseQuantity(Units.of(amount));
			
			articleCatalog.save(article);
			
			//return "redirect:article/" + article.getIdentifier() + "edit";
			return "redirect:/editarticle/" + article.getIdentifier();
		}
		
	
	//Delete Information (Categories) of an Article
	
	/**
	 * Deletes a given category from the categorylist of a given article and saves this article to the catalog again.
	 * @param article
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/article/delete/category", method=RequestMethod.POST)
	public String deleteCategory(@RequestParam("article") Article article, @RequestParam("categorytodelete") String category){
			
			article.removeCategory(category);
			
			//article.removeCategory(CategoryManagement.getInstance().getCategory(article.getType(), category));
			
			articleCatalog.save(article);
			
			//return "redirect:article/" + article.getIdentifier() + "edit";
			return "redirect:/editarticle/" + article.getIdentifier();
		}
		
}

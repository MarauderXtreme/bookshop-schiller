package bookshop.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import static org.joda.money.CurrencyUnit.*;

import org.joda.money.Money;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookshop.model.ArticleManagement;
import bookshop.model.Article;
import bookshop.model.Article.ArticleId;
import bookshop.model.Category;
import bookshop.model.CategoryManagement;
import bookshop.model.validation.ArticleForm;
import bookshop.model.validation.CategoriesForm;
import bookshop.model.validation.EditArticleForm;
//import bookshop.model.CategoryManagement;
import bookshop.model.validation.RegistrationForm;

@Controller
public class ArticleController {
	
	private final ArticleManagement articleCatalog;
	private final Inventory<InventoryItem> inventory;
	private final CategoryManagement categories;
	private final MessageSourceAccessor messageSourceAccessor; 

	/**
	 * Constructor for the ArticleController.
	 * @param articleCatalog
	 * @param inventory
	 * @param messageSource
	 */
	@Autowired
	public ArticleController(ArticleManagement articleCatalog, Inventory<InventoryItem> inventory, CategoryManagement categories, MessageSource messageSource) {
		this.articleCatalog = articleCatalog;
		this.inventory = inventory;
		this.categories = categories;
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}
	
	
	//Initilize Cataloglists
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

		return "articles";
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

		return "articles";
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

		return "articles";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
	@RequestMapping("/editcategories")
	public String addCategories(ModelMap modelMap) {

		modelMap.addAttribute("categories", categories.findAll());
		modelMap.addAttribute("categoriesbook", categories.findByType(ArticleId.BOOK));
		modelMap.addAttribute("categoriescd", categories.findByType(ArticleId.CD));
		modelMap.addAttribute("categoriesdvd", categories.findByType(ArticleId.DVD));
		modelMap.addAttribute("categoriesform", new CategoriesForm());
		
		return "editcategories";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
	@RequestMapping(value="/editcategories/delete", method=RequestMethod.POST)
	public String deleteCategory(ModelMap modelMap, @RequestParam("categorytodelete") String category){
		
		if(category=="1"){}
		else
			categories.delete(categories.findByCategoryName(category));
		
		return "redirect:/editcategories";

	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
	@RequestMapping(value="/editcategories/add", method=RequestMethod.POST)
	public String addCategory(@ModelAttribute("categoriesform") @Valid CategoriesForm categoriesform, BindingResult result){
		
		if (result.hasErrors()) {
			System.out.println("haserrors");
			return "editcategories";
		}
		
		if(categoriesform.getType().equals("Buch")){
			Category cat = new Category(categoriesform.getCategory(), ArticleId.BOOK);
			categories.save(cat);
		}
		if(categoriesform.getType().equals("CD")){
			Category cat = new Category(categoriesform.getCategory(), ArticleId.CD);
			categories.save(cat);
		}
		if(categoriesform.getType().equals("DVD")){
			Category cat = new Category(categoriesform.getCategory(), ArticleId.DVD);
			categories.save(cat);
		}
		
		return "redirect:/editcategories";

	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
	@RequestMapping(value="/editcategories/edit", method=RequestMethod.POST)
	public String editCategory(ModelMap modelMap, @RequestParam("oldcategory") String oldcategory, @RequestParam("editcategory") String editcategory){
		
			if(categories.findById(oldcategory).get().getArticleId()==ArticleId.BOOK){
				categories.delete(oldcategory);
				categories.save(new Category(editcategory, ArticleId.BOOK));
				
				Iterable<Article> articles = articleCatalog.findByType(ArticleId.BOOK);
				for(Article art : articles){
					if(art.getCategoryList().contains(categories.findById(oldcategory).get().getCategoryName())){
						art.removeCategory(categories.findById(oldcategory).get().getCategoryName());
						art.addCategory(editcategory);
					}
				}
			}
			
			if(categories.findById(oldcategory).get().getArticleId()==ArticleId.CD){
				categories.delete(oldcategory);
				categories.save(new Category(editcategory, ArticleId.CD));
				
				Iterable<Article> articles = articleCatalog.findByType(ArticleId.CD);
				for(Article art : articles){
					Iterable<String> cats = art.getCategories();
					for(String cat : cats){
						if(cat.equals(categories.findById(oldcategory).get().getCategoryName()))
						art.removeCategory(categories.findById(oldcategory).get().getCategoryName());
						art.addCategory(editcategory);
					}
				}
			}
			
			if(categories.findById(oldcategory).get().getArticleId()==ArticleId.DVD){
				categories.delete(oldcategory);
				categories.save(new Category(editcategory, ArticleId.DVD));
				
				Iterable<Article> articles = articleCatalog.findByType(ArticleId.DVD);
				for(Article art : articles){
					if(art.getCategoryList().contains(categories.findById(oldcategory).get().getCategoryName())){
						art.removeCategory(categories.findById(oldcategory).get().getCategoryName());
						art.addCategory(editcategory);
					}
				}
			}			
		
		return "redirect:/editcategories";

	}
	
	
	/**
	 * Maps a list of all articles of type book to modelMap for the add book html.
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
	@RequestMapping("/article/book/add")
	public String addBook(ModelMap modelMap) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.BOOK));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.article.title"));
		modelMap.addAttribute("categories", categories.findByType(ArticleId.BOOK));
		modelMap.addAttribute("articleForm", new ArticleForm());
		
		return "addbook";
	}

	/**
	 * Maps a list of all articles of type cd to modelMap for the add cd html.
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
	@RequestMapping("/article/cd/add")
	public String addCd(ModelMap modelMap) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.CD));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));
		modelMap.addAttribute("categories", categories.findByType(ArticleId.CD));
		modelMap.addAttribute("articleForm", new ArticleForm());

		return "addcd";
	}
	
	/**
	 * Maps a list of all articles of type dvd to modelMap for the add dvd html.
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
	@RequestMapping("/article/dvd/add")
	public String addDvd(ModelMap modelMap) {

		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.DVD));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));
		modelMap.addAttribute("categories", categories.findByType(ArticleId.DVD));
		modelMap.addAttribute("articleForm", new ArticleForm());
		
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
	@RequestMapping(value="/article/search", method=RequestMethod.GET)
	public String searchArticles(ModelMap modelMap, @RequestParam("typeInput") int typeInput, @RequestParam("input") String input){

		List<Article> li = new LinkedList<Article>();
		
		
		Iterable<Article> articles = articleCatalog.findAll();
		
		if(typeInput == 1){
			for(Article a : articles){
				if(input.length()<=3){
					if(a.getName().startsWith(input))
						li.add(a);
				}
				else{
				if(a.getName().contains(input)){
					li.add(a);
				}
				}
			}
		}
		
		if(typeInput == 2){
			for(Article a : articles){
				if(input.length()<=3){
					if(a.getPublisher().startsWith(input))
						li.add(a);
				}
				else{
				if(a.getPublisher().contains(input)){
					li.add(a);
				}
				}
			}
			//modelMap.addAttribute("catalog", articleCatalog.findByPublisher(input));
		}
		
		if(typeInput == 3){
			for(Article a : articles){
				if(input.length()<=8){
					if(a.getId().startsWith(input))
						li.add(a);
				}
				else{
				if(a.getId().contains(input)){
					li.add(a);
				}
				}
			}
			//modelMap.addAttribute("catalog", articleCatalog.findById(input));
		}
		
		if(typeInput == 4){
			/*if(!(articleCatalog.findByAuthor(input)==null))
				modelMap.addAttribute("catalog", articleCatalog.findByAuthor(input));
			if(!(articleCatalog.findByInterpret(input)==null))
				modelMap.addAttribute("catalog", articleCatalog.findByInterpret(input));
			if(!(articleCatalog.findByDirector(input)==null))
				modelMap.addAttribute("catalog", articleCatalog.findByDirector(input));*/
			//modelMap.addAttribute("catalog", articleCatalog.findByArtist(input));
			
			for(Article a : articles){
				if(input.length()<=3){
					if(a.getArtist().startsWith(input))
						li.add(a);
				}
				else{
				if(a.getArtist().contains(input)){
					li.add(a);
				}
				}
			}
			
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
			
			for(Article a : articles){
				
				if(a.getCategoryList().contains(input)){
					li.add(a);
			
				}
			}
			
			//modelMap.addAttribute("catalog", articleCatalog.findByCategory(input));
		}
			
		modelMap.addAttribute("catalog", li);
		
		return "articles";
		
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
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
	@RequestMapping(value="/article/book/new", method=RequestMethod.POST)
	public String addBook(@ModelAttribute("articleForm") @Valid ArticleForm articleForm, BindingResult result, @RequestParam("newcategory") String category){
		
		System.out.println("start addbook");

		if (articleForm.getPrice()<0) {
			result.addError(new ObjectError("price", "Der Preis muss grosser als 0 sein"));
		}
		
		if (result.hasErrors()) {
			System.out.println("haserrors");
			return "addbook";
		}

		
		Article article = new Article(	articleForm.getName(),
										Money.of(EUR, articleForm.getPrice()),
										articleForm.getBeschreibung(),
										articleForm.getPublisher(),
										articleForm.getId(),
										ArticleId.BOOK,
										category,
										articleForm.getArtist(),
										articleForm.getImage(),
										"01.01.2015",
										Money.of(EUR, 0.99)
										);
	
		//article.setAuthor(author);
		
		System.out.println("book added");
			
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
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
	@RequestMapping(value="/article/cd/new", method=RequestMethod.POST)
	public String addCD(@ModelAttribute("articleForm") @Valid ArticleForm articleForm, BindingResult result, @RequestParam("newcategory") String category){
		
		if (articleForm.getPrice()<0) {
			result.addError(new ObjectError("price", "Der Preis muss grosser als 0 sein"));
		}
		
		if (result.hasErrors()) {
			System.out.println("haserrors");
			return "addcd";
		}

		
		Article article = new Article(	articleForm.getName(),
										Money.of(EUR, articleForm.getPrice()),
										articleForm.getBeschreibung(),
										articleForm.getPublisher(),
										articleForm.getId(),
										ArticleId.CD,
										category,
										articleForm.getArtist(),
										articleForm.getImage(),
										"01.01.2015",
										Money.of(EUR, 0.99)
										);
			
		articleCatalog.save(article);
				
		
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
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
	@RequestMapping(value="/article/dvd/new", method=RequestMethod.POST)
	public String addDVD(@ModelAttribute("articleForm") @Valid ArticleForm articleForm, BindingResult result, @RequestParam("newcategory") String category){
		
		if (articleForm.getPrice()<0) {
			result.addError(new ObjectError("price", "Der Preis muss grosser als 0 sein"));
		}
		
		if (result.hasErrors()) {
			System.out.println("haserrors");
			return "adddvd";
		}

		
		Article article = new Article(	articleForm.getName(),
										Money.of(EUR, articleForm.getPrice()),
										articleForm.getBeschreibung(),
										articleForm.getPublisher(),
										articleForm.getId(),
										ArticleId.DVD,
										category,
										articleForm.getArtist(),
										articleForm.getImage(),
										"01.01.2015",
										Money.of(EUR, 0.99)
										);		
			
		articleCatalog.save(article);		
		
		InventoryItem item = new InventoryItem(article, Units.TEN);
		inventory.save(item);
	
		return "redirect:/article/dvd";

	}
	

	//Delete Article
	
	@RequestMapping("/article/{pid}/delete/confirm")
	public String confirmDelete(@PathVariable("pid") Article article, Model model) {
		
		model.addAttribute("article", article);
		
		return "confirmdelete";
	}
	
	/**
	 * Deletes a given article from the inventory and catalog.
	 * @param article
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
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

		model.addAttribute("article", article);
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
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
	@RequestMapping("/article/{pid}/edit")
	public String editarticle(@PathVariable("pid") Article article, Model model) {
		
		//For quantity mapping
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.TEN);
		
		//List for categories of article - deleteCategories
		List<Category> addable = new LinkedList<Category>();
		
		Iterable<Category> allCats = categories.findByType(article.getType());
		
		for(Category cat : allCats){
			if(!(article.getCategoryList().contains(cat.getCategoryName()))){
				addable.add(cat);
			}
		}
		
		model.addAttribute("article", article);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(Units.ZERO));
		model.addAttribute("addable", addable);
		model.addAttribute("deleteable", article.getCategories());
		if(article.getType()==ArticleId.BOOK)
			{model.addAttribute("categories", categories.findByType(ArticleId.BOOK));}
		if(article.getType()==ArticleId.CD)
			{model.addAttribute("categories", categories.findByType(ArticleId.CD));}
		if(article.getType()==ArticleId.DVD)
			{model.addAttribute("categories", categories.findByType(ArticleId.DVD));}
		model.addAttribute("editArticleForm", new EditArticleForm());

		return "editarticle";
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
	@RequestMapping(value="/article/editinformation", method=RequestMethod.POST)
	public String editArticle(@ModelAttribute("editArticleForm") @Valid EditArticleForm articleForm, BindingResult result, @RequestParam("article") Article article, @RequestParam("categorytodelete") String delcategory, @RequestParam("newcategory") String newCategory) {
		
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		
		if (articleForm.getPrice()<0) {
			result.addError(new ObjectError("price", "Der Preis muss grosser als 0 sein"));
		}
		
		/*if (articleForm.getIncreaseAmount()<0) {
			result.addError(new ObjectError("increaseAmount", "Die Menge muss grosser als 0 sein"));
		}*/
		
		if (articleForm.getDecreaseAmount()<0) {
			result.addError(new ObjectError("decreaseAmount", "Die Menge muss grosser als 0 sein"));
		}
		
		if (result.hasErrors()) {
			System.out.println("haserrors");
			return "redirect:/article/" + article.getIdentifier() + "/edit";
		}
		
		if(articleForm.getName()==null){}
		else
		article.setName(articleForm.getName());
		

		articleForm.setPublisher(articleForm.getPublisher());
		
		if(articleForm.getBeschreibung()==null){}
		else
		article.setBeschreibung(articleForm.getBeschreibung());
		
		if(articleForm.getId()==null){}
		else
		article.setId(articleForm.getId());
		
		if(articleForm.getPrice()==0){}
		else
		article.setPrice(Money.of(EUR, articleForm.getPrice()));

		article.setImage(articleForm.getImage());
		
		article.setArtist(articleForm.getArtist());
		
		/*if(author==null){}
		else
		article.setAuthor(author);
		
		if(interpret==null){}
		else
		article.setInterpret(interpret);
		
		if(director==null){}
		else
		article.setDirector(director);
		*/
		
		if(newCategory=="1"){}
			else{
				article.addCategory(newCategory);
			}
				
		if(delcategory=="1"){}
			else{
				article.removeCategory(delcategory);
			}
		
		System.out.println(articleForm.getDecreaseAmount());

		item.get().decreaseQuantity(Units.of(articleForm.getDecreaseAmount()));
		
		
		articleCatalog.save(article);
		
		return "redirect:/detail/" + article.getIdentifier();
	}
	
	
	/**
	 * 
	 * Highly complex Article Edit Function
	 * 
	 * @param article
	 * @param name takes the new Name
	 * @param publisher takes the new publisher
	 * @param beschreibung takes the new description
	 * @param isbn takes the new isbn
	 * @param price takes the new price
	 * @param addcategory takes the category that should be added
	 * @param image takes at this stage the name of the image
	 * @param author takes the new author
	 * @param interpret takes the new interpret
	 * @param director takes the new director
	 * @param increase takes the value of unit increase
	 * @param decrease takes the value of decrease
	 * @param delcategory takes the category that sould be delted
	 * @return
	 */
	/*@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_ARTICLEMANAGER')")
	@RequestMapping(value="/article/editinformation", method=RequestMethod.POST)
	public String editArticle(	@RequestParam("article") Article article,
			 					@RequestParam("newname") String name,
			 					@RequestParam("newpublisher") String publisher,
			 					@RequestParam("descriptiontext") String beschreibung,
			 					@RequestParam("newisbn") String isbn,
			 					@RequestParam("newcategory") String addcategory,
			 					@RequestParam("newimage") String image,
			 					@RequestParam("newartist") String artist,
			 					@RequestParam("addquan") long increase,
			 					@RequestParam("removequan") long decrease,
			 					@RequestParam("categorytodelete") String delcategory) {
		
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		
		if(name==null){}
		else
		article.setName(name);
		
		if(publisher==null){}
		else
		article.setPublisher(publisher);
		
		if(beschreibung==null){}
		else
		article.setBeschreibung(beschreibung);
		
		if(isbn==null){}
		else
		article.setId(isbn);
		
		//article.setPrice(Money.of(EUR, price));
		
		if(addcategory==null){}
		else{
			if(addcategory=="1"){}
			else
				article.addCategory(addcategory);
		}
		
		if(image==null){}
		else
		article.setImage(image);
		
		if(artist==null){}
		else
			article.setArtist(artist);
		
		/*if(author==null){}
		else
		article.setAuthor(author);
		
		if(interpret==null){}
		else
		article.setInterpret(interpret);
		
		if(director==null){}
		else
		article.setDirector(director);
		
		if(delcategory==null){}
		else{
			if(delcategory=="1"){}
			else
				article.removeCategory(delcategory);
		}
		
		if(increase==0){}
		else
		item.get().increaseQuantity(Units.of(increase));
		
		if(decrease==0){}
		else
		item.get().increaseQuantity(Units.of(decrease));
		
		
		articleCatalog.save(article);
		
		return "redirect:/article/" + article.getIdentifier() + "/edit";
	}*/

	
//	//Change Informations of an Article
//	
//	/**
//	 * Sets a given title to a given article and saves this article to the catalog again.
//	 * @param article
//	 * @param name
//	 * @return
//	 */
//	@RequestMapping(value="/article/set/title", method=RequestMethod.POST)
//	public String setNewTitle(@RequestParam("article") Article article, @RequestParam("newname") String name){
//			
//			
//			System.out.println("Title: " + article.getName());
//			
//			article.setName(name);
//			
//			articleCatalog.save(article);
//			
//			//return "redirect:article/" + article.getIdentifier() + "edit";
//			return "redirect:/editarticle/" + article.getIdentifier();
//	}
//		
//	/**
//	 * Sets a given publisher to a given article and saves this article to the catalog again.
//	 * @param article
//	 * @param publisher
//	 * @return
//	 */
//	@RequestMapping(value="/article/set/publisher", method=RequestMethod.POST)
//	public String setNewPublisher(@RequestParam("article") Article article, @RequestParam("newpublisher") String publisher){
//				
//				
//				System.out.println("Title: " + article.getName());
//				
//				article.setPublisher(publisher);
//				
//				articleCatalog.save(article);
//				
//				//return "redirect:article/" + article.getIdentifier() + "edit";
//				return "redirect:/editarticle/" + article.getIdentifier();
//			}
//			
//	/**
//	 * Sets a given description to a given article and saves this article to the catalog again.
//	 * @param article
//	 * @param description
//	 * @return
//	 */
//	@RequestMapping(value="/article/set/description", method=RequestMethod.POST)
//	public String setNewDescription(@RequestParam("article") Article article, @RequestParam("descriptiontext") String beschreibung){
//				
//				article.setBeschreibung(beschreibung);
//				
//				articleCatalog.save(article);
//				
//				//return "redirect:article/" + article.getIdentifier() + "edit";
//				return "redirect:/editarticle/" + article.getIdentifier();
//			}
//			
//	/**
//	 * Sets a given isbn to a given article and saves this article to the catalog again.
//	 * @param isbn
//	 * @param name
//	 * @return
//	 */
//	@RequestMapping(value="/article/set/isbn", method=RequestMethod.POST)
//	public String setNewIsbn(@RequestParam("article") Article article, @RequestParam("newisbn") String isbn, Model model){
//				
//				
//				System.out.println("Title: " + article.getName());
//				
//				article.setId(isbn);
//				
//				articleCatalog.save(article);
//				
//				//return "redirect:article/" + article.getIdentifier() + "edit";
//				return "redirect:/editarticle/" + article.getIdentifier();
//			}
//			
//	/**
//	 * Sets a given price to a given article and saves this article to the catalog again.
//	 * @param article
//	 * @param price
//	 * @return
//	 */
//	@RequestMapping(value="/article/set/price", method=RequestMethod.POST)
//	public String setNewPrice(@RequestParam("article") Article article, @RequestParam("newprice") double price){
//				
//				System.out.println("Title: " + article.getName());
//				
//				article.setPrice(Money.of(EUR, price));
//				
//				articleCatalog.save(article);
//				
//				//return "redirect:article/" + article.getIdentifier() + "edit";
//				return "redirect:/editarticle/" + article.getIdentifier();
//			}
//		
//	/**
//	 * Adds a given category to the Categorylist of a given article and saves this article to the catalog again.
//	 * @param article
//	 * @param category
//	 * @return
//	 */
//	@RequestMapping(value="/article/set/category", method=RequestMethod.POST)
//	public String setNewCatgory(@RequestParam("article") Article article, @RequestParam("newcategory") String category){
//				
//				System.out.println("Title: " + article.getName());
//				
//				article.addCategory(category);
//								
//				articleCatalog.save(article);
//				
//				//return "redirect:article/" + article.getIdentifier() + "edit";
//				return "redirect:/editarticle/" + article.getIdentifier();
//			}
//			
//	/**
//	 * Sets a given image to a given article and saves this article to the catalog again.
//	 * @param article
//	 * @param image
//	 * @return
//	 */
//	@RequestMapping(value="/article/set/image", method=RequestMethod.POST)
//	public String setNewImage(@RequestParam("article") Article article, @RequestParam("newimage") String image){
//								
//				article.setImage(image);
//				
//				articleCatalog.save(article);
//				
//				//return "redirect:article/" + article.getIdentifier() + "edit";
//				return "redirect:/editarticle/" + article.getIdentifier();
//			}
//	
//	
//	//Set Specific Informations
//	
//	/**
//	 * Sets a given author to a given article and saves this article to the catalog again.
//	 * @param article
//	 * @param author
//	 * @return
//	 */
//	@RequestMapping(value="/article/set/author", method=RequestMethod.POST)
//	public String setNewAutor(@RequestParam("article") Article article, @RequestParam("newauthor") String author){
//
//				System.out.println("Autor: " + article.getAuthor());
//							
//				article.setAuthor(author);
//				
//				System.out.println("Autor: " + article.getAuthor());
//				
//				articleCatalog.save(article);
//				
//				//return "redirect:article/" + article.getIdentifier() + "edit";
//				return "redirect:/editarticle/" + article.getIdentifier();
//	}
//			
//	/**
//	 * Sets a given interpret to a given article and saves this article to the catalog again.
//	 * @param article
//	 * @param interpret
//	 * @return
//	 */
//	@RequestMapping(value="/article/set/interpret", method=RequestMethod.POST)
//	public String setNewInterpret(@RequestParam("article") Article article, @RequestParam("newInterpret") String interpret){
//							
//				article.setInterpret(interpret);
//				
//				articleCatalog.save(article);
//				
//				//return "redirect:article/" + article.getIdentifier() + "edit";
//				return "redirect:/editarticle/" + article.getIdentifier();
//	}
//	
//	/**
//	 * Sets a given director to a given article and saves this article to the catalog again.
//	 * @param article
//	 * @param director
//	 * @return
//	 */	
//	@RequestMapping(value="/article/set/director", method=RequestMethod.POST)
//	public String setNewDirector(@RequestParam("article") Article article, @RequestParam("newdirector") String director){
//							
//				article.setDirector(director);
//				
//				articleCatalog.save(article);
//				
//				//return "redirect:article/" + article.getIdentifier() + "edit";
//				return "redirect:/editarticle/" + article.getIdentifier();
//	}
//			
//	/**
//	 * Increases a given amount of a given article and saves this article to the catalog again.
//	 * @param article
//	 * @param amount
//	 * @return
//	 */
//	@RequestMapping(value="/article/amount/increase", method=RequestMethod.POST)
//	public String increaseUnits(@RequestParam("article") Article article, @RequestParam("addquan") long amount){
//			
//			Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
//			
//			item.get().increaseQuantity(Units.of(amount));
//
//			/*articleCatalog.findByName(article.getName());
//			item.Units.add(Units.of(i));
//			*/
//			
//			/*Quantity quantity = item.get().getQuantity();
//			model.addAttribute("quantity", quantity);
//			*/
//			
//			articleCatalog.save(article);
//			
//			//return "redirect:article/" + article.getIdentifier() + "edit";
//			return "redirect:/editarticle/" + article.getIdentifier();
//		}
//		
//	/**
//	 * Decreases a given amount of a given article and saves this article to the catalog again.
//	 * @param article
//	 * @param amount
//	 * @return
//	 */
//	@RequestMapping(value="/article/amount/decrease", method=RequestMethod.POST)
//	public String decreaseUnits(@RequestParam("article") Article article, @RequestParam("removequan") long amount){
//			
//		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
//		
//		item.get().decreaseQuantity(Units.of(amount));
//			
//			articleCatalog.save(article);
//			
//			//return "redirect:article/" + article.getIdentifier() + "edit";
//			return "redirect:/editarticle/" + article.getIdentifier();
//		}
//		
//	
//	//Delete Information (Categories) of an Article
//	
//	/**
//	 * Deletes a given category from the categorylist of a given article and saves this article to the catalog again.
//	 * @param article
//	 * @param name
//	 * @return
//	 */
//	@RequestMapping(value="/article/delete/category", method=RequestMethod.POST)
//	public String deleteCategory(@RequestParam("article") Article article, @RequestParam("categorytodelete") String category){
//			
//			article.removeCategory(category);
//			
//			//article.removeCategory(CategoryManagement.getInstance().getCategory(article.getType(), category));
//			
//			articleCatalog.save(article);
//			
//			//return "redirect:article/" + article.getIdentifier() + "edit";
//			return "redirect:/editarticle/" + article.getIdentifier();
//		}
		
}

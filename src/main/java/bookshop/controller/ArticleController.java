package bookshop.controller;

import java.util.Optional;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
//import org.salespointframework.time.BusinessTime;
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

/*
import videoshop.model.Comment;
import videoshop.model.Disc;
import videoshop.model.Disc.DiscType;
import videoshop.model.VideoCatalog;
*/

@Controller
class ArticleController {
	private final ArticleManagement articleCatalog;
	private final Inventory<InventoryItem> inventory;
	//private final BusinessTime businessTime;	//gibt Systemzeit an, brauch ich denke ich nicht
	//private double aveRating;	//brauch ich nicht, gibt kein rating

	// (｡◕‿◕｡)
	// Da wir nur ein Catalog.html-Template nutzen, aber dennoch den richtigen Titel auf der Seite haben wollen,
	// nutzen wir den MessageSourceAccessor um an die messsages.properties Werte zu kommen
	private final MessageSourceAccessor messageSourceAccessor; //dürfte wichtig sein

	@Autowired
	public ArticleController(ArticleManagement articleCatalog, Inventory<InventoryItem> inventory, MessageSource messageSource) {

		this.articleCatalog = articleCatalog;
		this.inventory = inventory;
		//this.businessTime = businessTime;
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}
	
	/*
	 
	 
	 
	 */
	
	//Habe Parameter name hinzugefügt und bei meinem catalog die methode findType durch searchArticles ersetzt, um mir alle articles mit dem namen auszugeben
	//message lass ich erstmal so, da thymeleaf über diese adressierung an die richtige stelle mappen müsste, ist eigentlich catalog.book.title
	@RequestMapping("/dvdCatalog")
	public String dvdCatalog(ModelMap modelMap, String name) {

		//modelMap.addAttribute("catalog", articleCatalog.searchArticle(name));
		modelMap.addAttribute("catalog", articleCatalog.findByType(ArticleId.BOOK));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "discCatalog";
	}

	//brauch ich nicht ich hab ja nur ein typ und zwar buch!
	/*
	@RequestMapping("/blurayCatalog")
	public String blurayCatalog(Model model) {

		model.addAttribute("catalog", videoCatalog.findByType(DiscType.BLURAY));
		model.addAttribute("title", messageSourceAccessor.getMessage("catalog.bluray.title"));

		return "discCatalog";
	}*/

	// (｡◕‿◕｡)
	// Befindet sich die angesurfte Url in der Form /foo/5 statt /foo?bar=5 so muss man @PathVariable benutzen
	// Lektüre: http://spring.io/blog/2009/03/08/rest-in-spring-3-mvc/
	
	//Disc durch article ersetzt, ich lasse mal die quantity drin, inventory brauche ich sogar!
	//Was hat es mit dem Identifier auf sich?!
	@RequestMapping("/detail/{pid}")
	public String detail(@PathVariable("pid") Article article, Model model) {
		
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.ZERO);

		model.addAttribute("disc", article);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(Units.ZERO));
		//model.addAttribute("averageRating", disc.getAveRating());

		return "detail";
	}
	
	/*@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@RequestParam()){
		
	}*/

	// (｡◕‿◕｡)
	// Der Katalog bzw die Datenbank "weiß" nicht, dass die Disc mit einem Kommentar versehen wurde,
	// deswegen wird die update-Methode aufgerufen

	//COMMENT/RATING brauchen wir nicht!
	/*@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public String comment(@RequestParam("pid") Disc disc, @RequestParam("comment") String comment,
			@RequestParam("rating") int rating) {

		disc.addComment(new Comment(comment, rating, businessTime.getTime()));
		
		
		aveRating = (aveRating+(double)rating)/disc.getCommentsSize();
		
		disc.setAveRating(aveRating);
		videoCatalog.save(disc);
		
		return "redirect:detail/" + disc.getIdentifier();
	}*/
	//@RequestMapping("detail")
	//public double getAverating(){
	//	return aveRating;
	//}
}

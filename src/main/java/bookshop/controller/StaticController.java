package bookshop.controller;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import bookshop.model.Article;
import bookshop.model.ArticleManagement;

@Controller
public class StaticController {
	
	ArticleManagement articleCatalog;
	
	@Autowired
	public StaticController(ArticleManagement articleCatalog) {
		this.articleCatalog = articleCatalog;
	}	
	
	/**
	 * Maps the index page.
	 */
	@RequestMapping({ "/", "/index" })
	public String index(ModelMap modelMap) {
		
		int promotion;
		
		Random rand = new Random();
		
		List<Article> random = new LinkedList<Article>();
		List<Article> promo = new LinkedList<Article>();
		
		Iterable<Article> articles = articleCatalog.findAll();
		for(Article art : articles){
			random.add(art);
		}
		
		Collections.shuffle(random);
		
		promotion = rand.nextInt(random.size()-1);
		promo.add(random.get(promotion));
		random.remove(promotion);

		modelMap.addAttribute("promo", promo);
		modelMap.addAttribute("random", random);
				
		return "index";
	}
	
	@RequestMapping("contact")
	public String contact(ModelMap modelMap) {
		return "contact";
	}
	
	@RequestMapping("history")
	public String history(ModelMap modelMap) {
		return "history";
	}
		
}

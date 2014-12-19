package bookshop.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import bookshop.model.Article;
import bookshop.model.Article.ArticleId;
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
		
		int promoNum;
		
		Random rand = new Random();
		
		List<Article> list = new LinkedList<Article>();
		
		Iterable<Article> articles = articleCatalog.findAll();
		for(Article art : articles){
			list.add(art);
		}
		
		Collections.shuffle(list);
		
		promoNum = rand.nextInt(list.size()-1);

		modelMap.addAttribute("promo", list.get(promoNum));
		list.remove(promoNum);
		modelMap.addAttribute("random", list);
				
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

package bookshop.controller;

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
		
		
		List<Article> list = new LinkedList<Article>();
		List<Article> li = new LinkedList<Article>();
		int promoNum;
		int randomNum;
		
		Iterable<Article> articles = articleCatalog.findByType(ArticleId.BOOK);
		for(Article art : articles){
			li.add(art);
		}
		
		Random rand = new Random();
		for(int i=0; i<6; i++){
			randomNum = rand.nextInt(li.size()-1);
			list.add(li.get(randomNum));
		}

		promoNum = rand.nextInt(li.size()-1);
		

		modelMap.addAttribute("promo", list.get(promoNum));
		list.remove(promoNum);
		modelMap.addAttribute("random", list);
		
		
		//modelMap.addAttribute(null);
		
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

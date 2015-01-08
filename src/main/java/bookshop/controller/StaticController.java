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
	 * 
	 * <p>Gets all articles, puts them in a list and shuffles this list</p>
	 * 
	 * @param random List&lt;Articles&gt;
	 * @return Randomized List
	 */
	private List<Article> getRandomizeArticles (List<Article> random) {
		
		Iterable<Article> articles = articleCatalog.findAll();
		for(Article art : articles){
			random.add(art);
		}
		
		Collections.shuffle(random);
		return random;
	}
	
	/**
	 * Maps the index page.
	 */
	@RequestMapping({ "/", "/index" })
	public String index(ModelMap modelMap) {
		
		int promotion;
		
		String promodescription;
		
		Random rand = new Random();
		
		List<Article> random = new LinkedList<Article>();
		Article promo;
		
		getRandomizeArticles(random);
		
		/**
		 * Catch every exception as long we have not tested the List Truncate
		 */
		try {
			if(random.size() > 19) {
				random = random.subList(0, 19);
			}
		} catch (Exception e) {
			getRandomizeArticles(random);
		}
		
		promotion = rand.nextInt(random.size()-1);
		promo = random.get(promotion);
		random.remove(promotion);
		
		promodescription = promo.getBeschreibung();
		promodescription = promodescription.substring(0, Math.min(promodescription.length(), 300));
		promodescription = promodescription + "...";
		promo.setBeschreibung(promodescription);

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

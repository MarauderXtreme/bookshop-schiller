package bookshop.model;

import java.util.HashMap;
import java.util.Map;

public class ArticleManagement {
	
	
	private ArticleManagement Instance;
	private CategoryManagement categoryManagement = new CategoryManagement();
	private Map<String, Article> articles;
	
	public ArticleManagement(){
		this.articles = new HashMap<String, Article>();
		
	}
	
	public CategoryManagement getCategoryManagement(){
		return this.categoryManagement;
	}
	
	public ArticleManagement getInstance(){
		return this.Instance;
	}
	
	public boolean addArticle(String title, Article article){
		if(articles.containsKey(title)){
			return false;
		}
		else{
			articles.put(title, article);
			return true;
		}
	}
	
	public boolean setArticleCategory(String title, Category category){
		if(!(articles.containsKey(title))){
			return false;
		}
		else{
			articles.get(title).setCategory(category);
			return true;
		}
	}
	
	public boolean removeArticle(String key){
		if(articles.containsKey(key)){
			articles.remove(key);
			return true;
		}
		else{
			return false;
		}
	}
	
	public Article changeArticle(Article article){
		return article;
	}
	
	public String viewArticle(String key){
		
		String information = "";
		
		if(articles.containsKey(key)){
			information = "Titel: " + articles.get(key).getTitle() + ", Beschreibung: " +  articles.get(key).getBeschreibung();
		}
		
		return information;
	}
	
	public Category getCategory(String key){
		return articles.get(key).getCategory();
	}
	
	public String searchArticle(String keyword){
		Map<String, Article> resultMap = new HashMap<String, Article>();
		String foundArticles = "";
		
		for(String key : articles.keySet()){
			if(key == keyword){
				resultMap.put(keyword, articles.get(key));
			}
		}
		for(String key : resultMap.keySet()){
			foundArticles += key;
		}
			
		
		return foundArticles;
	}
	
	public boolean reorderArticle(Article article){
		if(articles.containsValue(article)){
			return false;
		}
		else{
			articles.put(article.getTitle(), article);
			return true;
		}
	}
	
}

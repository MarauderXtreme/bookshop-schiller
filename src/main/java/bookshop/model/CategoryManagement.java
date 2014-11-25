package bookshop.model;

import java.util.HashMap;
import java.util.Map;
import bookshop.model.Article.ArticleId;


public class CategoryManagement {
	
	private Map<ArticleId, Category> categories;
	
	public CategoryManagement(){
		categories = new HashMap<ArticleId, Category>();
	}
	
	public boolean addCategory(ArticleId articleId, Category category){
		if(categories.containsKey(articleId)){
			return false;
		}
		else{
			categories.put(articleId, category);
			return true;
		}
	}
	
	public boolean removeCategory(Category category){
		if(!(categories.containsValue(category))){
		return false;
		}
		else{
			categories.remove(category.getArticleId(), category);
			return true;
		}
	}
	
	public boolean changeCategory(String name){
		return true;
	}
	
	public Category getCategory(ArticleId id){
		return categories.get(id);
	}
}

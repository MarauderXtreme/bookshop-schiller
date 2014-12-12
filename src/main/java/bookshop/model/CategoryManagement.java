package bookshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bookshop.model.Article.ArticleId;

public class CategoryManagement {
	
	private Map<ArticleId, ArrayList<String>> categories;
	
	private static CategoryManagement instance ;
	
	private CategoryManagement()
	{
		this.categories = new HashMap<ArticleId, ArrayList<String>>();
	}
	
	public static synchronized CategoryManagement getInstance()
	{
		if(instance == null)
		{
			instance = new CategoryManagement();
			System.out.println("Kalendermanager-Instanz erstellt");
		}
		System.out.println("Kalendermanager-Instanz zurückgeben");
		return CategoryManagement.instance;
	}
	
	//@Deprecated
	//protected CategoryManagement() {}
	
	//public CategoryManagement(){
	//	categories = new HashMap<ArticleId, ArrayList<String>>();
	//}
	
	public void addCategory(ArticleId articleType, String category){
		/*if(categories.){
			return false;
		}*/
		if(!(categories.containsKey(articleType))){
			ArrayList<String> li = new ArrayList<String>();
			li.add(category);
			categories.put(articleType, li);
		}
		else{
			categories.get(articleType).add(category);
		}
		
	}
	
	public boolean removeCategory(ArticleId articleType, String category){
		if(!(categories.containsKey(articleType))){
		return false;
		}
		else{
			categories.remove(articleType, categories.get(articleType).remove(category));
			return true;
		}
	}
	
	
	public String getCategory(ArticleId id, String category){
		return categories.get(id).get(categories.get(id).indexOf(category));
	}
	
	public String getCategoryList(){
		return categories.get(ArticleId.BOOK).toString();
	}
}

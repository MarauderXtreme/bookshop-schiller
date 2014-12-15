package bookshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import bookshop.model.Article.ArticleId;

//@Component
//public interface CategoryManagement extends CrudRepository<Category, Long>{
	
	//Iterable<Category> findById(String id);
	
	
	
	
	

	//private final Map<ArticleId, ArrayList<String>> categories;
	
	//private static CategoryManagement instance ;
	
	/**
	 * Constructor of CategoryManagement.
	 */
	//@Autowired
	/*private CategoryManagement()
	{
		this.categories = new HashMap<ArticleId, ArrayList<String>>();
		
		//initialize CategoryLists
		ArrayList<String> libook = new ArrayList<String>();
		ArrayList<String> licd = new ArrayList<String>();
		ArrayList<String> lidvd = new ArrayList<String>();
		this.categories.put(ArticleId.BOOK, libook);
		this.categories.put(ArticleId.CD, licd);
		this.categories.put(ArticleId.DVD, lidvd);
		//initilize Categories
		categories.get(ArticleId.BOOK).add("Ratgeber");
		categories.get(ArticleId.BOOK).add("Fiktion");
		categories.get(ArticleId.BOOK).add("Unterhaltung");
		categories.get(ArticleId.CD).add("Pop");
		categories.get(ArticleId.DVD).add("Horror");
		
	}*/
	
	/**
	 * @return static instance of the CategoryManagement
	 */
	/*public static synchronized CategoryManagement getInstance()
	{
		if(instance == null)
		{
			instance = new CategoryManagement();
			System.out.println("Kalendermanager-Instanz erstellt");
		}
		System.out.println("CategoryManagement-Instanz zurückgeben");
		return CategoryManagement.instance;
	}
	
	//@Deprecated
	//protected CategoryManagement() {}
	
	//public CategoryManagement(){
	//	categories = new HashMap<ArticleId, ArrayList<String>>();
	//}*/
	
	/**
	 * Adds the given category to the List of categories to the given Type of Article.
	 * @param articleType
	 * @param category
	 */
	/*public void addCategory(ArticleId articleType, String category){
		if(categories.){
			return false;
		}
		if(!(categories.containsKey(articleType))){
			ArrayList<String> li = new ArrayList<String>();
			li.add(category);
			categories.put(articleType, li);
		}
		else{
			categories.get(articleType).add(category);
		}
		
	}*/
	
	/**
	 * Removes the given category of the given type of article.
	 * @param articleType
	 * @param category
	 */
	/*public boolean removeCategory(ArticleId articleType, String category){
		if(!(categories.containsKey(articleType))){
		return false;
		}
		else{
			categories.remove(articleType, categories.get(articleType).remove(category));
			return true;
		}
	}
	*/
	/**
	 * @return a List of all existing employees
	 */
	/*public String getCategory(ArticleId id, String category){
		return categories.get(id).get(categories.get(id).indexOf(category));
	}*/
	
	/**
	 * @return a List of all existing categories of the type book
	 */
	/*public String getCategoryList(){
		return categories.get(ArticleId.BOOK).toString();
	}*/
//}

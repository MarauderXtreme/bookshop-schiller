package bookshop.model;

//import org.salespointframework.inventory.*;
//import org.salespointframework.catalog.Catalog;
//import org.salespointframework.catalog.ProductIdentifier;
/*
import videoshop.model.Disc;
import videoshop.model.Disc.DiscType;
*/
import org.salespointframework.catalog.Catalog;
import org.springframework.data.jpa.repository.Query;

import bookshop.model.Article.ArticleId;
/*import java.util.Optional;
import bookshop.model.Article.ArticleId;
*/



public interface ArticleManagement extends Catalog<Article> {

	/**
	 * Iterates a catalog of articles
	 * @param type
	 * @return a List of all existing articles of a specific article type
	 */
	Iterable<Article> findByType(ArticleId type);
		
	/**
	 * Iterates a catalog of articles
	 * @param publisher
	 * @return a List of all existing articles of a specific publisher
	 */
	Iterable<Article> findByPublisher(String publisher);
	
	/**
	 * Iterates a catalog of articles
	 * @param id
	 * @return a List of all existing articles of a specific product number
	 */
	Iterable<Article> findById(String id);
	
	/**
	 * Iterates a catalog of articles
	 * @param author
	 * @return a List of all existing articles of a specific author
	 */
	Iterable<Article> findByAuthor(String author);
	
	/**
	 * Iterates a catalog of articles
	 * @param interpret
	 * @return a List of all existing articles of a specific interpret
	 */
	Iterable<Article> findByInterpret(String interpret);
	
	/**
	 * Iterates a catalog of articles
	 * @param director
	 * @return a List of all existing articles of a specific director
	 */
	Iterable<Article> findByDirector(String director);
	
	/**
	 * Iterates a catalog of articles
	 * @param category
	 * @return a List of all existing articles of a specific category
	 */
	@Query(value="select p from #{#entityName} p where ?1 member of p.categories")
	Iterable<Article> findByCategory(String category);
}





/*
public class ArticleManagement{

	
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
	
	public Collection<Article> getArticleList(){
		return articles.values();
		
	}
/*
	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(ProductIdentifier arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Book arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends Book> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(ProductIdentifier arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Book> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> findAll(Iterable<ProductIdentifier> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Book> findOne(ProductIdentifier arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Book> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Book> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> findByCategory(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> findByName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
*/
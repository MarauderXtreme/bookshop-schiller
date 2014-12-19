package bookshop.model;

import org.salespointframework.catalog.Catalog;
import org.springframework.data.jpa.repository.Query;

import bookshop.model.Article.ArticleId;



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
	
	Iterable<Article> findByArtist(String artist);
	
	/**
	 * Iterates a catalog of articles
	 * @param category
	 * @return a List of all existing articles of a specific category
	 */
	@Query(value="select p from #{#entityName} p where ?1 member of p.categories")
	Iterable<Article> findByCategory(String category);
}
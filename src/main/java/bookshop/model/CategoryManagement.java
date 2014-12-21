package bookshop.model;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import bookshop.model.Article.ArticleId;

@Component
public interface CategoryManagement extends CrudRepository<Category, String>{
	
	/**
	 * Iterates a repository of categories
	 * @param id
	 * @return a Category of the individual Category Id
	 */
	Optional<Category> findById(String id);
	
	/**
	 * Iterates a repository of categories
	 * @param categoryname
	 * @return a List of all existing categories of a specific category name
	 */
	Iterable<Category> findByCategoryName(String categoryname);
	
	/**
	 * Iterates a repository of categories
	 * @param type
	 * @return a List of all existing categories of a specific article type
	 */
	Iterable<Category> findByType(ArticleId type);

}

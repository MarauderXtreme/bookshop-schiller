package bookshop.model;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import bookshop.model.Article.ArticleId;

@Component
public interface CategoryManagement extends CrudRepository<Category, String>{
	
	Optional<Category> findById(String id);
	
	Iterable<Category> findByCategoryName(String categoryname);
	
	Iterable<Category> findByType(ArticleId type);

}

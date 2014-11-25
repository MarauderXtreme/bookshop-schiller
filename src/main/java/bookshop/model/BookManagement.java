package bookshop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.salespointframework.catalog.ProductIdentifier;

import bookshop.model.Article.ArticleId;

public class BookManagement implements ArticleManagement{

	@Override
	public Iterable<Article> findByCategory(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Article> findByName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

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
	public void delete(Article arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends Article> arg0) {
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
	public Iterable<Article> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Article> findAll(Iterable<ProductIdentifier> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Article> findOne(ProductIdentifier arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Article> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Article> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Article> findByType(ArticleId type) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

/*
public class BookManagement extends ArticleManagement{
	private Map<String, Book> articles;

	public BookManagement() {
		super();
		articles = new HashMap<String, Book>();
	}
}
*/
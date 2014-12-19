package bookshop.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import bookshop.model.Article.ArticleId;


@Entity
public class Category {
	
	@Id
	private String id;
	private String categoryName;
	private ArticleId type;	
	
	
	public Category() {
		super();
	}

	public Category(String categoryName, ArticleId type) {
		super();
		setId(type, categoryName);
		this.categoryName = categoryName;
		this.type = type;
	}

	public String getCategoryName(){
		return categoryName;
	}
	
	public ArticleId getArticleId(){
		return type;
	}
	
	private void setId(ArticleId type, String categroryName) {
		id = type.toString() + categroryName;
	}
	
	public String getId() {
		return id;
	}

	public ArticleId getType() {
		return type;
	}

	public void setType(ArticleId type) {
		this.type = type;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}

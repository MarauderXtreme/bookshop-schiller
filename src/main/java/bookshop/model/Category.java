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
	
	/**
	 * Overloaded Constructor of Category, only for the crud repository Categorymanagement to build the datastructure for category.
	 */
	//@Deprecated
	public Category() {
		super();
	}

	/**
	 * Overloaded Constructor of Category, to build a new category.
	 * @param categoryName
	 * @param type
	 */
	public Category(String categoryName, ArticleId type) {
		super();
		setId(type, categoryName);
		this.categoryName = categoryName;
		this.type = type;
	}

	/**
	 * @return the name of the category
	 */
	public String getCategoryName(){
		return categoryName;
	}
	
	/**
	 * Sets the name of the publisher of the article.
	 * @param publisher
	 */
	private void setId(ArticleId type, String categroryName) {
		id = type.toString() + categroryName;
	}
	
	/**
	 * @return the individual id of the category of the form "ArticleIdcategoryname"
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the type of a article the category is related to.
	 */
	public ArticleId getType() {
		return type;
	}

	/**
	 * Sets the type of article the category is related to.
	 * @param type
	 */
	public void setType(ArticleId type) {
		this.type = type;
	}

	/**
	 * Sets the name of the category.
	 * @param categoryname
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}

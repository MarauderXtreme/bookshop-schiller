package bookshop.model;
import bookshop.model.Article.ArticleId;


public class Category {
	
	
	private String id;
	private String categoryName;
	private ArticleId type;
	
	public Category(String categoryName, ArticleId type){
		setID(type, categoryName);
	}
	
	public String getCategoryName(){
		return categoryName;
	}
	
	public ArticleId getArticleId(){
		return type;
	}
	
	private void setID(ArticleId type, String categroryName) {
		id = type.toString() + categroryName;
	}
	
	public String getID() {
		return id;
	}
	
}

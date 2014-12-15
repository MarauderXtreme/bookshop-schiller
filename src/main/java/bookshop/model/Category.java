package bookshop.model;
import bookshop.model.Article.ArticleId;


public class Category {
	
	private String categoryName;
	private ArticleId type;
	
	public Category(String categoryName, ArticleId type){
		
	}
	
	public String getCategoryName(){
		return categoryName;
	}
	
	public ArticleId getArticleId(){
		return type;
	}
	
}

package bookshop.model;

import org.salespointframework.catalog.Product;


	public abstract class Article extends Product{
		
		public static enum ArticleId {ALL, BOOK, DVD, CD};
		
		private String beschreibung;
		private String title;
		private int id;
		private ArticleId type;
		
		//!!! - Kategorie manuell in der Main neben Artikel definieren 
		//oder im Artikel direkt hinzufügen im Kostruktor
		private Category category;
		
		@SuppressWarnings("deprecation")//was ist das?!
		public Article(String beschreibung, String title, int id, ArticleId type){
			this.beschreibung = beschreibung;
			this.title = title;
			this.id = id;
			this.type = type;
		}
		
		public String getTitle(){
			return title;
		}
		
		public String getBeschreibung(){
			return beschreibung;
		}
		
		public void setCategory(Category category){
			this.category = category;
		}
		
		public Category getCategory(){
			return this.category;
		}
		
		public ArticleId getId(){
			return type;
		}

	}


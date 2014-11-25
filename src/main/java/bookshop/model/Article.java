package bookshop.model;

import javax.persistence.Entity;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Units;

@Entity
public class Article extends Product{
		
		public static enum ArticleId {ALL, BOOK, DVD, CD};
		
		private String beschreibung;
		private String title;
		private int id;
		private ArticleId type;
		
		//!!! - Kategorie manuell in der Main neben Artikel definieren 
		//oder im Artikel direkt hinzufügen im Kostruktor
		private Category category;
		
		@Deprecated
		protected Article() {}
		
		//@SuppressWarnings("deprecation")//was ist das?!
		public Article(String name, Money price, String beschreibung, String title, int id, ArticleId type){
			super(name, price, Units.METRIC);
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
		
		public ArticleId getType(){
			return type;
		}
		
		public int getId(){
			return id;
		}

	}


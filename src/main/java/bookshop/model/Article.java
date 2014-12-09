package bookshop.model;

import javax.persistence.Entity;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Units;

@Entity
public class Article extends Product{
		
		public static enum ArticleId {BOOK, DVD, CD};
		
		private String beschreibung;
		private String publisher;
		private String id;
		private ArticleId type;
		private String image;
		//spezifische Variablen
		private String author;
		private String interpret;
		private String director;
		
		
		//!!! - Kategorie manuell in der Main neben Artikel definieren 
		//oder im Artikel direkt hinzufügen im Kostruktor
		//private Category category;
		
		@Deprecated
		protected Article() {}
		
		//@SuppressWarnings("deprecation")//was ist das?!
		public Article(String title, Money price, String beschreibung, String publisher, String id, ArticleId type, String category, String artist){
			super(title, price, Units.METRIC);
			super.addCategory(category);
			this.beschreibung = beschreibung;
			this.publisher = publisher;
			this.id = id;
			this.type = type;
						
			
			if(type==ArticleId.BOOK){
				this.author = artist;
				this.image = "notAvaliableBook.jpg";
			}
			if(type==ArticleId.CD){
				this.interpret = artist;
				this.image = "notAvaliableCd.jpg";
			}
			if(type==ArticleId.DVD){
				this.director = artist;
				this.image = "notAvaliableDvd.png";
			}
		}
		
		//@SuppressWarnings("deprecation")//was ist das?!
		public Article(String title, Money price, String beschreibung, String publisher, String id, ArticleId type, String category, String artist, String image){
			super(title, price, Units.METRIC);
			super.addCategory(category);
			this.beschreibung = beschreibung;
			this.publisher = publisher;
			this.id = id;
			this.type = type;
			
			this.image = image;
			
			
		}
		
		public String getBeschreibung(){
			return beschreibung;
		}
		
		public void setBeschreibung(String beschreibung){
			this.beschreibung = beschreibung;
		}
		
		public String getPublisher(){
			return publisher;
		}
		
		public void setPublisher(String publisher){
			this.publisher = publisher;
		}
		
		/*
		public void setCategory(Category category){
			this.category = category;
		}
		
		public Category getCategory(){
			return this.category;
		}
		*/
		
		
		public ArticleId getType(){
			return type;
		}
		
		public void setType(ArticleId type){
			this.type = type;
		}
		
		public String getId(){
			return id;
		}
		
		public void setId(String id){
			this.id = id;
		}
		
		//Spezifische Variablen
		
		public String getAuthor(){
			return author;
		}
		
		public void setAuthor(String author){
			this.author = author;
		}
		
		public String getInterpret(){
			return interpret;
		}
		
		public void setInterpret(String interpret){
			this.interpret = interpret;
		}
		
		public String getDirector(){
			return director;
		}
		
		public void setDirector(String director){
			this.director = director;
		}
		
		public String getCategoryList(){
			
			Iterable<String> list = super.getCategories();
			
			return list.toString();
		}
		
		public String getImage(){
			return image;
		}
		
		public void setImage(String image){
			this.image = image;
		}

	}


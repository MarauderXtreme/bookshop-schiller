package bookshop.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.Role;

@Entity
public class Article extends Product{
		
		public static enum ArticleId {BOOK, DVD, CD};
		
		private String beschreibung;
		private String publisher;
		private String id;
		private ArticleId type;
		private String image;
		private String releaseDate;
		private Money stockPrice;
		//@OneToOne
		private String category;
		
		//spezifische Variablen
		private String artist;
		/*private String author;
		private String interpret;
		private String director;
		*/
		
		//!!! - Kategorie manuell in der Main neben Artikel definieren 
		//oder im Artikel direkt hinzufügen im Kostruktor
		//private Category category;
		
		@Deprecated
		protected Article() {}
		
		/**
		 * Overloaded Constructor of Article.
		 * @param title
		 * @param price
		 * @param description
		 * @param publisher
		 * @param id
		 * @param type
		 * @param category
		 * @param artist
		 */
		//@SuppressWarnings("deprecation")
		public Article(String title, Money price, String beschreibung, String publisher, String id, ArticleId type, String category, String artist, String releaseDate, Money stockPrice){
			super(title, price, Units.METRIC);
			super.addCategory(category);
			this.beschreibung = beschreibung;
			this.publisher = publisher;
			this.id = id;
			this.type = type;
			this.stockPrice = stockPrice;
			this.releaseDate = releaseDate;
			
			this.artist = artist;
			
			if(type==ArticleId.BOOK){
				//this.author = artist;
				this.image = "notAvaliableBook.jpg";
			}
			if(type==ArticleId.CD){
				//this.interpret = artist;
				this.image = "notAvaliableCd.jpg";
			}
			if(type==ArticleId.DVD){
				//this.director = artist;
				this.image = "notAvaliableDvd.png";
			}
		}

		/**
		 * Overloaded Constructor of Article.
		 * @param title
		 * @param price
		 * @param description
		 * @param publisher
		 * @param id
		 * @param type
		 * @param category
		 * @param artist
		 * @param image
		 */
		//@SuppressWarnings("deprecation")//was ist das?!
		public Article(String title, Money price, String beschreibung, String publisher, String id, ArticleId type, String category, String artist, String image,String releaseDate, Money stockPrice){
			super(title, price, Units.METRIC);
			super.addCategory(category);
			
			//this.category=category;
			
			this.beschreibung = beschreibung;
			this.publisher = publisher;
			this.id = id;
			this.type = type;
			this.artist = artist;
			this.stockPrice = stockPrice;
			this.releaseDate = releaseDate;
			
			
			this.image = image;
		}
		
		
		/**
		 * @return the description of the article
		 */
		public String getBeschreibung(){
			return beschreibung;
		}
		
		/**
		 * Sets the description of the article.
		 * @param description
		 */
		public void setBeschreibung(String beschreibung){
			this.beschreibung = beschreibung;
		}
		
		/**
		 * @return the name of the publisher of the article
		 */
		public String getPublisher(){
			return publisher;
		}
		
		/**
		 * Sets the name of the publisher of the article.
		 * @param publisher
		 */
		public void setPublisher(String publisher){
			this.publisher = publisher;
		}
		
		
		
		public void setCategory(String category){
			this.category = category;
			super.addCategory(category);
		}
		
		public String getCategory(){
			return this.category;
		}
		
		
		/**
		 * @return the type of the article
		 */
		public ArticleId getType(){
			return type;
		}
		
		/**
		 * Sets the type of the article.
		 * @param type
		 */
		public void setType(ArticleId type){
			this.type = type;
		}
		
		/**
		 * @return the productnumber of the article
		 */
		public String getId(){
			return id;
		}
		
		/**
		 * Sets the productnumber of the article.
		 * @param id
		 */
		public void setId(String id){
			this.id = id;
		}
		
		//Spezifische Variablen
		
		public String getArtist(){
			return artist;
		}
		
		public void setArtist(String artist){
			this.artist = artist;
		}
		
		/**
		 * @return the name of the author of the article
		 */
		//public String getAuthor(){
		//	return author;
		//}
		
		/**
		 * Sets the name of the author of the article.
		 * @param author
		 */
		//public void setAuthor(String author){
		//	this.author = author;
		//}
		
		/**
		 * @return the name of the interpret of the article
		 */
		//public String getInterpret(){
		//	return interpret;
		//}
		
		/**
		 * Sets the name of the interpret of the article.
		 * @param interpret
		 */
		//public void setInterpret(String interpret){
		//	this.interpret = interpret;
		//}
		
		/**
		 * @return the name of the director of the article
		 */
		//public String getDirector(){
		//	return director;
		//}
		
		/**
		 * Sets the name of the director of the article.
		 * @param director
		 */
		//public void setDirector(String director){
		//	this.director = director;
		//}
		
		/**
		 * @return a list of all categories of the article
		 */
		public String getCategoryList(){
			
			Iterable<String> list = super.getCategories();
			
			return list.toString();
		}
		
		/**
		 * @return the image of the article
		 */
		public String getImage(){
			return image;
		}
		
		/**
		 * Sets the filename of the image of the article.
		 * @param image
		 */
		public void setImage(String image){
			this.image = image;
		}

	}
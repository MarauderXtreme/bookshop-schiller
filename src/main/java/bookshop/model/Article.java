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
		private String releaseDate;
		private Money stockPrice;
		private String category;
		
		//spezifische Variablen
		private String artist;
		
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
				this.image = "notAvaliableBook.jpg";
			}
			if(type==ArticleId.CD){
				this.image = "notAvaliableCd.jpg";
			}
			if(type==ArticleId.DVD){
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
		public Article(String title, Money price, String beschreibung, String publisher, String id, ArticleId type, String category, String artist, String image,String releaseDate, Money stockPrice){
			super(title, price, Units.METRIC);
			super.addCategory(category);
						
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

		public String getReleaseDate() {
			return releaseDate;
		}

		public void setReleaseDate(String releaseDate) {
			this.releaseDate = releaseDate;
		}

		public Money getStockPrice() {
			return stockPrice;
		}

		public void setStockPrice(Money stockPrice) {
			this.stockPrice = stockPrice;
		}
		
		

	}
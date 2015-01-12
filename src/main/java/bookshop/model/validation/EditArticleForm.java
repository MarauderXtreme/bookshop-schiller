package bookshop.model.validation;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class EditArticleForm {


		
		@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		private String name;
		
		private double price;
		
		@Column(length=1000)
		@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		private String beschreibung;
		
		@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		private String publisher;
		
		//EAN-Code oder ISBN-Nr.
		@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		@Pattern(regexp="([0-9]{13})", message = "{ArticleForm.Id.Format}")
		private String id;
		
		
		@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		@Pattern(regexp="([A-Za-z0-9\\-\\_]+\\.(jpg|png|gif|jpeg|JPG|PNG|GIF|JPEG))", message = "{ArticleForm.Image.Format}")
		private String image;
				
		
		//spezifische Variablen
		@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		private String artist;
		
		private double stockPrice;
		
		@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		@Pattern(regexp="([0-3][0-9]\\.[0-1][0-9]\\.[0-2][0-9][0-9][0-9])", message = "{ArticleForm.Date.Format}")
		private String releaseDate;
		
		private String newcategory;
			
		
		//Getter and Setter
		
		/**
		 * @return the stockPrice from the edit article form
		 */
		public double getStockPrice() {
			return stockPrice;
		}

		/**
		 * Sets the price of the article the bookshop has to pay of the edit article form to the given value.
		 * @param stockPrice
		 */
		public void setStockPrice(double stockPrice) {
			this.stockPrice = stockPrice;
		}

		/**
		 * @return the releaseDate from the edit article form
		 */
		public String getReleaseDate() {
			return releaseDate;
		}

		/**
		 * Sets the release date of the edit article form to the given value.
		 * @param releaseDate
		 */
		public void setReleaseDate(String releaseDate) {
			this.releaseDate = releaseDate;
		}

		/**
		 * @return the title from the edit article form
		 */
		public String getName(){
			return name;
		}
		
		/**
		 * Sets the title of the edit article form to the given value.
		 * @param name
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * @return the price from the edit article form
		 */
		public double getPrice(){
			return price;
		}
		
		/**
		 * Sets the price of the edit article form to the given value.
		 * @param price
		 */
		public void setPrice(double price){
			this.price = price;
		}
		
		/**
		 * @return the description from the edit article form
		 */
		public String getBeschreibung(){
			return beschreibung;
		}
		
		/**
		 * Sets the description of the edit article form to the given value.
		 * @param beschreibung
		 */
		public void setBeschreibung(String beschreibung){
			this.beschreibung = beschreibung;
		}
		
		/**
		 * @return the name of the publisher from the edit article form
		 */
		public String getPublisher(){
			return publisher;
		}
		
		/**
		 * Sets the name of the publisher of the edit article form to the given value.
		 * @param publisher
		 */
		public void setPublisher(String publisher){
			this.publisher = publisher;
		}
		
		/**
		 * @return the type of a article from the edit article form
		 */
		public String getId(){
			return id;
		}
		
		/**
		 * Sets the type of the article of the edit article form to the given value.
		 * @param id
		 */
		public void setId(String id){
			this.id = id;
		}
		
		/**
		 * @return the name of the artist from the edit article form
		 */
		public String getArtist(){
			return artist;
		}
		
		/**
		 * Sets the name of the artist of the edit article form to the given value.
		 * @param artist
		 */
		public void setArtist(String artist){
			this.artist = artist;
		}

		/**
		 * @return the image path from the edit article form
		 */
		public String getImage(){
			return image;
		}
		
		/**
		 * Sets the image path of the edit article form to the given value.
		 * @param image
		 */
		public void setImage(String image){
			this.image = image;
		}
		
		/**
		 * @return the category from the edit article form
		 */
		public String getCategory() {
			return newcategory;
		}
		
		/**
		 * Sets the category name of the edit article form to the given value.
		 * @param newcategory
		 */
		public void setCategory(String newcategory) {
			this.newcategory = newcategory;
		}
			
}

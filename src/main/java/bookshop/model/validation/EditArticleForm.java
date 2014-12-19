package bookshop.model.validation;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class EditArticleForm {


		
		@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		private String name;
		
		//@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		//@Pattern(regexp="([0-9]+\\.{0,1}[0-9]*)", message = "{ArticleForm.Price.Format}")
		private double price;
		
		@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		private String beschreibung;
		
		@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		private String publisher;
		
		//EAN-Code oder ISBN-Nr.
		@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		//@Pattern(regexp="([0-9]{3}\\-[0-9]{1}\\-[0-9]{3}\\-[0-9]{5}\\-[0-9]{1})|([0-9]{1}\\-[0-9]{6}\\-[0-9]{6})", message = "{ArticleForm.Id.Format}")
		private String id;
		
		
		//@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		@Pattern(regexp="([A-Za-z0-9]+\\.(jpg|png|gif))", message = "{ArticleForm.Image.Format}")
		private String image;
				
		
		//spezifische Variablen
		@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		private String artist;
		
		private double stockPrice;
		
		@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
		@Pattern(regexp="([0-3][0-9]\\.[0-1][0-9]\\.[0-2][0-9][0-9][0-9])", message = "{ArticleForm.Date.Format}")
		private String releaseDate;
		
		private String newcategory;
			
		public double getStockPrice() {
			return stockPrice;
		}

		public void setStockPrice(double stockPrice) {
			this.stockPrice = stockPrice;
		}

		public String getReleaseDate() {
			return releaseDate;
		}

		public void setReleaseDate(String releaseDate) {
			this.releaseDate = releaseDate;
		}

		public String getName(){
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public double getPrice(){
			return price;
		}
		
		public void setPrice(double price){
			this.price = price;
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
		
		public String getId(){
			return id;
		}
		
		public void setId(String id){
			this.id = id;
		}
		
		public String getArtist(){
			return artist;
		}
		
		public void setArtist(String artist){
			this.artist = artist;
		}

		public String getImage(){
			return image;
		}
		
		public void setImage(String image){
			this.image = image;
		}
		
		public String getCategory() {
			return newcategory;
		}
		
		public void setCategory(String newcategory) {
			this.newcategory = newcategory;
		}
			
}

package bookshop.model.validation;


import javax.persistence.Column;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;


public class ArticleForm {

	
	
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
	
	
	//Setter and Getter
	
	/**
	 * @return the given stock price from the article form
	 */
	public double getStockPrice() {
		return stockPrice;
	}

	/**
	 * Sets the stock price of the article form to the given value.
	 * @param stockPrice
	 */
	public void setStockPrice(double stockPrice) {
		this.stockPrice = stockPrice;
	}

	/**
	 * @return the given release date from the article form
	 */
	public String getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Sets the release date of the article form to the given value.
	 * @param releaseDate
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * @return the given article title from the article form
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Sets the title of the article form to the given value.
	 * @param title
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the article price from the article form
	 */
	public double getPrice(){
		return price;
	}
	
	/**
	 * Sets the price of the article form to the given value.
	 * @param price
	 */
	public void setPrice(double price){
		this.price = price;
	}
	
	/**
	 * @return the given description from the article form
	 */
	public String getBeschreibung(){
		return beschreibung;
	}
	
	/**
	 * Sets the description of the article form to the given value.
	 * @param description
	 */
	public void setBeschreibung(String beschreibung){
		this.beschreibung = beschreibung;
	}
	
	/**
	 * @return the given publisher name from the article form
	 */
	public String getPublisher(){
		return publisher;
	}
	
	/**
	 * Sets the publisher of the article form to the given value.
	 * @param publisher
	 */
	public void setPublisher(String publisher){
		this.publisher = publisher;
	}
	
	/**
	 * @return the given Productnumber from the article form
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * Sets the Productnumber of the article form to the given value.
	 * @param Productnumber
	 */
	public void setId(String id){
		boolean set = false;
		
		// is isbn
		if(id.length() == 13){
			if(isIsbnValid(id)){
				this.id = id;
			}else{
				this.id = null;
			}
			
			set = true;
		}
		
		if(!set){
			this.id = id;
		}
	}
	
	//Spezifische Variablen

	/**
	 * @return the given author name from the article form
	 */
	public String getArtist(){
		return artist;
	}
	
	/**
	 * Sets the author of the article form to the given value.
	 * @param author
	 */
	public void setArtist(String artist){
		this.artist = artist;
	}

	/**
	 * @return the given filename from the article form
	 */
	public String getImage(){
		return image;
	}
	
	/**
	 * Sets the image of the article form to the given value.
	 * @param image
	 */
	public void setImage(String image){
		this.image = image;
	}
	
	/**
	 * check the idbn
	 * 
	 * @author Marcel Liebgott <marcelœmliebgott.de>
	 * @param isbn String
	 * @return boolean
	 * @throws Exception 
	 */
	private boolean isIsbnValid(String isbn){
		boolean valid = false;
		
		if(isbn != null){
			int x = 0, g = 0, u = 0, s1 = 0, s2 = 0;

	        // remove all '-'
	        isbn.replace("-", "");
	
	        // get the sum of even numbers
	        for(g = 0; g < 13; g += 2){
	            s1 += isbn.charAt(g);
	        }
	
	        // get the sum of odd numbers
	        for(u = 1; u < 13; u += 2){
	            s2 += isbn.charAt(u);
	        }
	
	        x = (10 - ((s1 + 3*(s2))) % 10);
	
	        if((x % 10) == 0){
	            return true;
	        }
	    }
		
		return valid;
	}
}

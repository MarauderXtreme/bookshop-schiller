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
	@Pattern(regexp="([A-Za-z0-9]+\\.(jpg|png|gif))", message = "{ArticleForm.Image.Format}")
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
		this.id = id;
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
	
}

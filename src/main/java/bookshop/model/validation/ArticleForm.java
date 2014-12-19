package bookshop.model.validation;


import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;


public class ArticleForm {

	
	
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
	
	@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	@Pattern(regexp="([A-Za-z0-9]+\\.(jpg|png|gif))", message = "{ArticleForm.Image.Format}")
	private String image;
	
	/*@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	@Pattern(regexp="([0-9]+)", message = "{ArticleForm.Amount.Format}")
	private int amount;
	*/
	
	//spezifische Variablen
	@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	private String artist;
	
	//@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	//@Pattern(regexp="([a-zA-Z]+)", message = "{ArticleForm.name.NotEmpty}")
	//private String category;
	
	//Setter and Getter
	
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
	
	public double getPrice(){
		return price;
	}
	
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
	
	/*public String getCategory(){
		return category;
	}
	
	public void setCategory(String category){
		this.category = category;
	}*/
	
	/**
	 * @return the given amount of article units from the article form
	 */
	/*public int getAmount(){
		return amount;
	}*/
	
	/**
	 * Sets the amount of the article form to the given value.
	 * @param amount
	 */
	/*public void setAmount(int amount){
		this.amount = amount;
	}*/
	
}

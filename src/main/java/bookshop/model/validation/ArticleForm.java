package bookshop.model.validation;

import org.salespointframework.quantity.Quantity;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import bookshop.model.Article.ArticleId;

public class ArticleForm {
	
	//All variables must be not empty	
	//Darf nur positive Zahl sein
	//double price;
	//Was für eine Nummer soll es sein?! Nur für Bücher oder Allg. ID
	//String ISBN;	
	//Symbole "." Sufix(jpg,png,...)
	//String image;	
	//Darf nicht negativ sein
	//Resultierender Amount darf nicht negativ sein
	//Quantity amount;
	
	
	
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
	
	/*@NotEmpty(message = "{addArticeForm.name.NotEmpty}")
	private ArticleId type;*/
	
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
	
	/*@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	private String author;
	*/
	/*@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	private String interpret;
	
	@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	private String director;
	*/
	@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	@Pattern(regexp="([a-zA-Z]+)", message = "{ArticleForm.name.NotEmpty}")
	private String category;
	
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
	
	/*
	public ArticleId getType(){
		return type;
	}
	
	public void setType(ArticleId type){
		this.type = type;
	}
	*/
	
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
	 * @return the given author name from the article form
	 */
	//public String getAuthor(){
	//	return author;
	//}
	
	/**
	 * Sets the author of the article form to the given value.
	 * @param author
	 */
	//public void setAuthor(String author){
	//	this.author = author;
	//}
	
	/**
	 * @return the given user interpret name the article form
	 */
	//public String getInterpret(){
	//	return interpret;
	//}
	
	/**
	 * Sets the interpret of the article form to the given value.
	 * @param interpret
	 */
	//public void setInterpret(String interpret){
	//	this.interpret = interpret;
	//}
	
	/**
	 * @return the given director name from the article form
	 */
	//public String getDirector(){
	//	return director;
	//}
	
	/**
	 * Sets the director of the article form to the given value.
	 * @param director
	 */
	//public void setDirector(String director){
	//	this.director = director;
	//}
	
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
	
	public String getCategory(){
		return category;
	}
	
	public void setCategory(String category){
		this.category = category;
	}
	
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

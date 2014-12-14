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
	
	@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	@Pattern(regexp="([0-9]+\\.{0,1}[0-9]*)", message = "{ArticleForm.Price.Format}")
	private String price;
	
	@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	private String beschreibung;
	
	@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	private String publisher;
	
	//EAN-Code oder ISBN-Nr.
	@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	@Pattern(regexp="([0-9]{3}\\-[0-9]{1}\\-[0-9]{3}\\-[0-9]{5}\\-[0-9]{1})|([0-9]{1}\\-[0-9]{6}\\-[0-9]{6})", message = "{ArticleForm.Id.Format}")
	private String id;
	
	/*@NotEmpty(message = "{addArticeForm.name.NotEmpty}")
	private ArticleId type;*/
	
	@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	@Pattern(regexp="([A-Za-z0-9]+\\.(jpg|png|gif))", message = "{ArticleForm.Image.Format}")
	private String image;
	
	@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	@Pattern(regexp="([0-9]+)", message = "{ArticleForm.Amount.Format}")
	private int amount;
	
	
	//spezifische Variablen
	@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	private String author;
	
	@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	private String interpret;
	
	@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
	private String director;
	
	//Setter and Getter
	
	public String getName(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	public ArticleId getType(){
		return type;
	}
	
	public void setType(ArticleId type){
		this.type = type;
	}
	*/
	
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
	
	public String getImage(){
		return image;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}

}

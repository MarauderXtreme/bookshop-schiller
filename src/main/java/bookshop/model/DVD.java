package bookshop.model;

import org.joda.money.Money;

public class DVD extends Article{
	
	private String regisseur;

	public DVD(String beschreibung, String title, int id, ArticleId type, Money price, String name) {
		super(beschreibung, price, title, name, id, type);
	}
	
	public String getRegisseur(){
		return this.regisseur;
	}
}

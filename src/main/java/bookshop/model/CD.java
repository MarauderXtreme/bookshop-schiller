package bookshop.model;

import org.joda.money.Money;

public class CD extends Article{
	private String interpret;

	public CD(String interpret, String beschreibung, String title, String id, ArticleId type, Money price, String name) {
		super(beschreibung, price, title, name, id, type);
		this.interpret = interpret;
	}

	public String getInterpret(){
		return this.interpret;
	}
}

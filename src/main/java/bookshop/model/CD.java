package bookshop.model;

public class CD extends Article{
	private String interpret;

	public CD(String interpret, String beschreibung, String title, int id, ArticleId type) {
		super(beschreibung, title, id, type);
		this.interpret = interpret;
	}

	public String getInterpret(){
		return this.interpret;
	}
}

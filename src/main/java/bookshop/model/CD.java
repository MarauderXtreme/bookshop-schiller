package bookshop.model;

public class CD extends Article{
	private String interpret;

	public CD(String interpret, String beschreibung, String title, int id) {
		super(beschreibung, title, id);
		this.interpret = interpret;
	}

	public String getInterpret(){
		return this.interpret;
	}
}

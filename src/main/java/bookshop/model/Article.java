package bookshop.model;

public class Article {

	private String beschreibung;
	private String title;
	private int id;
	
	public Article(String beschreibung, String title, int id){
		this.beschreibung = beschreibung;
		this.title = title;
		this.id = id;
	}
}

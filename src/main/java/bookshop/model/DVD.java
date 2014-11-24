package bookshop.model;

public class DVD extends Article{
	
	private String regisseur;

	public DVD(String beschreibung, String title, int id, ArticleId type) {
		super(beschreibung, title, id, type);
	}
	
	public String getRegisseur(){
		return this.regisseur;
	}
}

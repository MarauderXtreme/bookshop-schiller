package bookshop.model;

public class DVD extends Article{
	
	private String regisseur;

	public DVD(String beschreibung, String title, int id) {
		super(beschreibung, title, id);
	}
	
	public String getRegisseur(){
		return this.regisseur;
	}
}

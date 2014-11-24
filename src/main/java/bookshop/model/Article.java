package bookshop.model;

import org.salespointframework.catalog.Product;



	public abstract class Article extends Product{
		
		private String beschreibung;
		private String title;
		private int id;
		
		//!!! - Kategorie manuell in der Main neben Artikel definieren 
		//oder im Artikel direkt hinzufügen im Kostruktor
		private Category category;
		
		public Article(String beschreibung, String title, int id){
			this.beschreibung = beschreibung;
			this.title = title;
			this.id = id;
		}
		
		public String getTitle(){
			return title;
		}
		
		public String getBeschreibung(){
			return beschreibung;
		}
		
		public void setCategory(Category category){
			this.category = category;
		}
		
		public Category getCategory(){
			return this.category;
		}

	}


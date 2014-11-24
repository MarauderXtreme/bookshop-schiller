package bookshop.model;

public class Book extends Article{
		
		private String interpret;

		public Book(String interpret, String beschreibung, String title, int id, ArticleId type) {
			super(beschreibung, title, id, type);
			this.interpret = interpret;
		}

		public String getInterpret(){
			return this.interpret;
		}

	}

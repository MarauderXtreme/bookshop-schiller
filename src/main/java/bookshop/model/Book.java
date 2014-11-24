package bookshop.model;

public class Book extends Article{
		
		private String interpret;

		public Book(String interpret, String beschreibung, String title, int id) {
			super(beschreibung, title, id);
			this.interpret = interpret;
		}

		public String getInterpret(){
			return this.interpret;
		}

	}

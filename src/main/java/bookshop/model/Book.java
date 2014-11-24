package bookshop.model;

public class Book extends Article{
		
		private String author;

		public Book(String author, String beschreibung, String title, int id, ArticleId type) {
			super(beschreibung, title, id, type);
			this.author = author;
		}

		public String getAuthor(){
			return this.author;
		}

	}

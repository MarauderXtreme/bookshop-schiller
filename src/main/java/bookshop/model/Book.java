package bookshop.model;

import org.joda.money.Money;

public class Book extends Article{
		
		private String author;

		public Book(String author, String beschreibung, String title, int id, ArticleId type, Money price, String name) {
			super(beschreibung, price, title, name, id, type);
			this.author = author;
		}

		public String getAuthor(){
			return this.author;
		}

	}

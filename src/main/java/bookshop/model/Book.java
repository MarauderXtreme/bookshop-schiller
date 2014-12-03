package bookshop.model;

import org.joda.money.Money;

import bookshop.model.Article.ArticleId;

public class Book extends Article{
		
		private String author;

		public Book(String author, String title, Money price, String beschreibung, String publisher, String id, ArticleId type) {
			super(title, price, beschreibung, publisher, id, type);
			this.author = author;
		}

		public String getAuthor(){
			return this.author;
		}

	}

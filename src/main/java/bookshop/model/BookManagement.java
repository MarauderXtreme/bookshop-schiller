package bookshop.model;

import java.util.HashMap;
import java.util.Map;

public class BookManagement extends ArticleManagement{
	private Map<String, Book> articles;

	public BookManagement() {
		super();
		articles = new HashMap<String, Book>();
	}
}

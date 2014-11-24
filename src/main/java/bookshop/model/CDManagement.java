package bookshop.model;

import java.util.HashMap;
import java.util.Map;


public class CDManagement extends ArticleManagement{
	private Map<String, CD> articles;

	public CDManagement() {
		super();
		articles = new HashMap<String, CD>();
		}
}

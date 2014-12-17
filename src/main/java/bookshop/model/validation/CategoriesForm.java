package bookshop.model.validation;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class CategoriesForm {

			
			
			@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
			@Pattern(regexp="(Buch|CD|DVD)", message = "{ArticleForm.category.Format}")
			private String type;
			
			
			@NotEmpty(message = "{ArticleForm.name.NotEmpty}")
			//@Pattern(regexp="([a-zA-Z]+)", message = "{ArticleForm.name.NotEmpty}")
			private String category;
			
			//Setter and Getter
			
			public String getType(){
				return type;
			}
			
			public void setType(String type){
				this.type = type;
			}
			
			public String getCategory(){
				return category;
			}
	
			public void setCategory(String category){
				this.category = category;
			}
}

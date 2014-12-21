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
			
			/**
			 * @return the given type of a article related to a category from the categories form
			 */
			public String getType(){
				return type;
			}
			
			/**
			 * Sets the type of an article of the categories form to the given value.
			 * @param type
			 */
			public void setType(String type){
				this.type = type;
			}
			
			/**
			 * @return the given category name from the categories form
			 */
			public String getCategory(){
				return category;
			}
	
			/**
			 * Sets the category name of the categories form to the given value.
			 * @param category
			 */
			public void setCategory(String category){
				this.category = category;
			}
}

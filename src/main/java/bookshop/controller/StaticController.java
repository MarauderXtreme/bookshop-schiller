package bookshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {
	
	public StaticController() {}	
	
	
	@RequestMapping("contact")
	public String contact(ModelMap modelMap) {
		return "contact";
	}
	
	@RequestMapping("history")
	public String history(ModelMap modelMap) {
		return "history";
	}
		
}

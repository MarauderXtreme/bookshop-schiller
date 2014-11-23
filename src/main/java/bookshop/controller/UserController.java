package bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import bookshop.model.UserRepository;

public class UserController {
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserController(UserRepository userRepository) {

		this.userRepository = userRepository;
		
	}
	
	@RequestMapping("/customers")
	public String customers(ModelMap modelMap) {

		modelMap.addAttribute("customerList", userRepository.findAll());

		return "customers";
		
	}

}

package bookshop.controller;

import java.awt.List;

import org.salespointframework.useraccount.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import bookshop.model.User;
import bookshop.model.UserRepository;

public class UserController {
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserController(UserRepository userRepository) {

		this.userRepository = userRepository;
		
	}
	
	@RequestMapping("/users")
	public String users(ModelMap modelMap) {

		modelMap.addAttribute("userList", userRepository.findAll());

		return "users";
		
	}
	
	@RequestMapping("/customers")
	public String customers(ModelMap modelMap) {
		
		Iterable<User> users = userRepository.findAll();
		for(User u : users) {
			Iterable<Role> roles = u.getUserAccount().getRoles();
			if(roles.iterator(roles.equals(customerRole)))
		}
		users.iterator(users.getUserAccount().getRole());
		modelMap.addAttribute("customerList", userRepository.findAll());
		// nur Users mit Role Customer finden - wie geht das?

		return "customers";
		
	}

}

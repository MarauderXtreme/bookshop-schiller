package bookshop.controller;

import java.awt.List;
import java.util.ArrayList;

import org.salespointframework.useraccount.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import bookshop.model.User;
import bookshop.model.UserRepository;

@Controller
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
		ArrayList<User> customers = new ArrayList<User>();
		for(User u : users) {
			Iterable<Role> roles = u.getUserAccount().getRoles();
			for(Role r: roles) {
				if (r.getName().equals("ROLE_CUSTOMER")) {
					customers.add(u);
				}
			}
		}
		modelMap.addAttribute("customerList", customers);

		return "customers";
		
	}

}

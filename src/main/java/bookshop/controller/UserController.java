package bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import bookshop.model.CustomerRepository;

public class UserController {
	
	private final CustomerRepository customerRepository;
	
	@Autowired
	public UserController(CustomerRepository customerRepository) {

		this.customerRepository = customerRepository;
		
	}
	
	@RequestMapping("/customers")
	public String customers(ModelMap modelMap) {

		modelMap.addAttribute("customerList", customerRepository.findAll());

		return "customers";
		
	}

}

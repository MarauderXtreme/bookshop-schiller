package bookshop.controller;

import java.awt.List;
import java.util.ArrayList;

import javax.validation.Valid;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import bookshop.model.validation.RegistrationForm;
import bookshop.model.User;
import bookshop.model.UserRepository;

@Controller
public class UserController {
	
	private final UserRepository userRepository;
	private final UserAccountManager userAccountManager;
	
	/**
	 * Constructor for the UserController.
	 * @param userRepository
	 * @param userAccountManager
	 */
	@Autowired
	public UserController(UserRepository userRepository, UserAccountManager userAccountManager) {

		this.userRepository = userRepository;
		this.userAccountManager = userAccountManager;
	}
	
	/**
	 * Maps a list of all customers to modelMap.
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/customers")	
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
	
	/**
	 * Maps a list of all employees to modelMap.
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/employees")
	public String employees(ModelMap modelMap) {
		
		Iterable<User> users = userRepository.findAll();
		ArrayList<User> employees = new ArrayList<User>();
		for(User u : users) {
			Iterable<Role> roles = u.getUserAccount().getRoles();
			for(Role r: roles) {
				if (r.getName().equals("ROLE_EMPLOYEE") || r.getName().equals("ROLE_BOSS") || r.getName().equals("ROLE_ADMIN")) {
					employees.add(u);
				}
			}
		}
		modelMap.addAttribute("employeeList", employees);

		return "employees";	
	}
	
	/**
	 * Reads data from the registrationForm and registers a new employee.
	 * @param registrationForm
	 * @param result
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/register/new")
	public String registerNewEmployee(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm,
			BindingResult result) {

		if (result.hasErrors()) {
			return "register";
		}

		UserAccount userAccount = userAccountManager.create(registrationForm.getUsername(), registrationForm.getPassword(),
				new Role("ROLE_EMPLOYEE"));
		userAccount.setFirstname(registrationForm.getFirstname());
		userAccount.setLastname(registrationForm.getLastname());
		userAccount.setEmail(registrationForm.getEmail());
		userAccountManager.save(userAccount);

		User user = new User(userAccount, registrationForm.getAddress());
		userRepository.save(user);

		return "redirect:/";
	}
	
	/**
	 * Maps the registration form for employees to modelMap.
	 * @param modelMap
	 */
	@RequestMapping("/admin/register")
	public String registerEmployee(ModelMap modelMap) {
		modelMap.addAttribute("registrationForm", new RegistrationForm());
		return "registerEmployee";
	}
	
	/**
	 * Reads data from the registrationForm and registers a new customer.
	 * @param registrationForm
	 * @param result
	 * @return
	 */
	@RequestMapping("/user/register/new")
	public String registerNewCustomer(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm,
			BindingResult result) {

		if (result.hasErrors()) {
			return "register";
		}

		UserAccount userAccount = userAccountManager.create(registrationForm.getUsername(), registrationForm.getPassword(),
				new Role("ROLE_CUSTOMER"));
		userAccount.setFirstname(registrationForm.getFirstname());
		userAccount.setLastname(registrationForm.getLastname());
		userAccount.setEmail(registrationForm.getEmail());
		userAccountManager.save(userAccount);

		User user = new User(userAccount, registrationForm.getAddress());
		userRepository.save(user);

		return "redirect:/";
	}

	/**
	 * Maps the registration form for customers to modelMap.
	 * @param modelMap
	 */
	@RequestMapping("/user/register")
	public String register(ModelMap modelMap) {
		modelMap.addAttribute("registrationForm", new RegistrationForm());
		return "register";
	}
	
	/**
	 * Maps the index page.
	 */
	@RequestMapping({ "/", "/index" })
	public String index() {
		return "index";
	}

}

package bookshop.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
				if (r.getName().equals("ROLE_EMPLOYEE")) {
					employees.add(u);
				}
			}
		}
		modelMap.addAttribute("employeeList", employees);

		return "employees";	
	}
	
	/**
	 * Reads data from the registrationForm for administrators and registers a new user.
	 * @param registrationForm
	 * @param result
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/user/add/new")
	public String registerNewUser(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm,
			BindingResult result, @RequestParam("roleInput") String roleInput) {

		if (!registrationForm.getPasswordRepeat().equals(registrationForm.getPassword())) {
			result.addError(new ObjectError("password.noMatch", "Ihre eingegebenen Passwörter stimmen nicht überein!"));
		}
		
		Iterable<User> users = userRepository.findAll();
		
		for (User u : users) {
			if (u.getUserAccount().getIdentifier().getIdentifier().equals(registrationForm.getUsername())) {
				result.addError(new ObjectError("username.isUsed", "Ihre eingegebener Nutzername ist bereits vergeben!"));
			}
		}
		for (User u : users) {
			if (u.getUserAccount().getEmail().equals(registrationForm.getEmail())) {
				result.addError(new ObjectError("email.isUsed", "Ihre eingegebene E-Mail-Adresse ist bereits vergeben!"));
			}
		}
		
		if (result.hasErrors()) {
			return "registerUser";
		}
		
		UserAccount userAccount = userAccountManager.create(registrationForm.getUsername(), registrationForm.getPassword(),
				new Role(roleInput));
		userAccount.setFirstname(registrationForm.getFirstname());
		userAccount.setLastname(registrationForm.getLastname());
		userAccount.setEmail(registrationForm.getEmail());
		userAccountManager.save(userAccount);

		User user = new User(userAccount, registrationForm.getAddress());
		userRepository.save(user);

		return "redirect:/";
	}
	
	/**
	 * Maps the administrator registration form for users to modelMap.
	 * @param modelMap
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/user/add")
	public String registerUser(ModelMap modelMap) {
		modelMap.addAttribute("registrationForm", new RegistrationForm());
		return "registerUser";
	}
	
	/**
	 * Reads data from the registrationForm for an unregistered user and registers a new customer.
	 * @param registrationForm
	 * @param result
	 * @return
	 */
	@RequestMapping("/user/register/new")
	public String registerMe(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm,
			BindingResult result) {

		if (!registrationForm.getPasswordRepeat().equals(registrationForm.getPassword())) {
			result.addError(new ObjectError("password.noMatch", "Ihre eingegebenen Passwörter stimmen nicht überein!"));
		}
		
		Iterable<User> users = userRepository.findAll();
		
		for (User u : users) {
			if (u.getUserAccount().getIdentifier().getIdentifier().equals(registrationForm.getUsername())) {
				result.addError(new ObjectError("username.isUsed", "Ihre eingegebener Nutzername ist bereits vergeben!"));
			}
		}
		for (User u : users) {
			if (u.getUserAccount().getEmail().equals(registrationForm.getEmail())) {
				result.addError(new ObjectError("email.isUsed", "Ihre eingegebene E-Mail-Adresse ist bereits vergeben!"));
			}
		}
		
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
	 * Maps the registration form for an unregistered to modelMap.
	 * @param modelMap
	 */
	@RequestMapping("/user/register")
	public String register(ModelMap modelMap) {
		modelMap.addAttribute("registrationForm", new RegistrationForm());
		return "register";
	}
	
	 /**
	  * Maps the given user to modelMap.
	  * @param user
	  * @param modelMap
	  * @return
	  */
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_BOSS') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/user/profile/{pid}")
	public String profile(@PathVariable("pid") UserAccount userAccount, Model modelMap) {
		User user = userRepository.findByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		return "profile";
	}
	
	/**
	 * Maps the index page.
	 */
	@RequestMapping({ "/", "/index" })
	public String index() {
		return "index";
	}

}

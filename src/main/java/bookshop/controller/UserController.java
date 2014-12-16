package bookshop.controller;

import java.security.Principal;
import java.util.ArrayList;

import javax.validation.Valid;

import org.salespointframework.useraccount.Password;
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

import bookshop.model.validation.ProfileForm;
import bookshop.model.validation.RegistrationForm;
import bookshop.model.User;
import bookshop.model.UserManagement;
import bookshop.model.UserRepository;

@Controller
public class UserController {
	
	private final UserRepository userRepository;
	private final UserAccountManager userAccountManager;
	private final UserManagement userManagement;
	
	/**
	 * Constructor for the UserController.
	 * @param userRepository
	 * @param userAccountManager
	 */
	@Autowired
	public UserController(UserRepository userRepository, UserAccountManager userAccountManager, UserManagement userManagement) {

		this.userRepository = userRepository;
		this.userAccountManager = userAccountManager;
		this.userManagement = userManagement;
	}
	
	/**
	 * Maps a list of all customers to modelMap.
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/admin/customers")	
	public String customers(ModelMap modelMap) {
		
		ArrayList<User> customers = userManagement.getCustomers();
		modelMap.addAttribute("customerList", customers);

		return "customers";	
	}
	
	/**
	 * Maps a list of all employees to modelMap.
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/admin/employees")
	public String employees(ModelMap modelMap) {
		
		ArrayList<User> employees = userManagement.getEmployees();
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
			result.addError(new ObjectError("password.noMatch", "Die eingegebenen Passwörter stimmen nicht überein!"));
		}
		
		Iterable<User> users = userRepository.findAll();
		
		for (User u : users) {
			if (u.getUserAccount().getIdentifier().getIdentifier().equals(registrationForm.getUsername())) {
				result.addError(new ObjectError("username.isUsed", "Die eingegebener Nutzername ist bereits vergeben!"));
			}
		}
		for (User u : users) {
			if (u.getUserAccount().getEmail().equals(registrationForm.getEmail())) {
				result.addError(new ObjectError("email.isUsed", "Die eingegebene E-Mail-Adresse ist bereits vergeben!"));
			}
		}
		
		if (result.hasErrors()) {
			return "registeruser";
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
		return "registeruser";
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
			result.addError(new ObjectError("password.noMatch", "Die eingegebenen Passwörter stimmen nicht überein!"));
		}
		
		Iterable<User> users = userRepository.findAll();
		
		for (User u : users) {
			if (u.getUserAccount().getIdentifier().getIdentifier().equals(registrationForm.getUsername())) {
				result.addError(new ObjectError("username.isUsed", "Die eingegebener Nutzername ist bereits vergeben!"));
			}
		}
		for (User u : users) {
			if (u.getUserAccount().getEmail().equals(registrationForm.getEmail())) {
				result.addError(new ObjectError("email.isUsed", "Die eingegebene E-Mail-Adresse ist bereits vergeben!"));
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
	 * Maps the account settings of a special user to modelMap.
	 * @param userAccount
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/user/profile/{pid}/accountsettings")
	public String changeAccountsettings(@PathVariable("pid") UserAccount userAccount, ModelMap modelMap) {
		
		User user = userRepository.findByUserAccount(userAccount);
		modelMap.addAttribute(user);
		return "editaccountsettings";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/user/profile/{pid}/accountsettings/disable")
	public String disable(@PathVariable("pid") UserAccount userAccount, Model modelMap) {
		
		User user = userRepository.findByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		userManagement.disable(userAccount);
		userAccountManager.save(userAccount);
		return "profile";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/user/profile/{pid}/accountsettings/enable")
	public String enable(@PathVariable("pid") UserAccount userAccount, Model modelMap) {
		
		User user = userRepository.findByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		userManagement.enable(userAccount);
		userAccountManager.save(userAccount);
		return "profile";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/user/profile/{pid}/accountsettings/roles/add")
	public String addRole(@PathVariable("pid") UserAccount userAccount, Model modelMap, @RequestParam("roleInput") String roleInput) {
		
		User user = userRepository.findByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		userManagement.addRole(userAccount, new Role(roleInput));
		userAccountManager.save(userAccount);
		return "profile";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/user/profile/{pid}/accountsettings/roles/remove")
	public String removeRole(@PathVariable("pid") UserAccount userAccount, Model modelMap, @RequestParam("roleInput") String roleInput) {
		
		User user = userRepository.findByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		userManagement.removeRole(userAccount, new Role(roleInput));
		userAccountManager.save(userAccount);
		return "profile";
	}
	
	/**
	 * Reads data from the profileForm and changes profile data of a special user.
	 * @param userAccount
	 * @param profileForm
	 * @param result
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/user/profile/{pid}/change/edit")
	public String changeProfileEdit(@PathVariable("pid") UserAccount userAccount, @ModelAttribute("profileForm") @Valid ProfileForm profileForm,
			BindingResult result, ModelMap modelMap) {

		User user = userRepository.findByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("userAccount", userAccount);
		
		if (!profileForm.getPasswordRepeat().equals(profileForm.getPassword())) {
			result.addError(new ObjectError("password.noMatch", "Die eingegebenen Passwörter stimmen nicht überein!"));
		}
		
		//if (!userAccount.getPassword().equals(new Password(profileForm.getOldPassword()))) {
		//	result.addError(new ObjectError("password.old.error", "Das eingegebene alte Passwort ist nicht korrekt!"));
		//}
		
		Iterable<User> users = userRepository.findAll();	
		
		for (User u : users) {
			if (u.getUserAccount().getEmail().equals(profileForm.getEmail()) && !(u.getUserAccount().equals(userAccount))) {
				result.addError(new ObjectError("email.isUsed", "Die eingegebene E-Mail-Adresse ist bereits vergeben!"));
			}
		}
		
		if (result.hasErrors()) {
			return "editprofile";
		}
		
		userAccount.setFirstname(profileForm.getFirstname());
		userAccount.setLastname(profileForm.getLastname());
		userAccount.setEmail(profileForm.getEmail());
		userAccountManager.changePassword(userAccount, profileForm.getPassword());
		userAccountManager.save(userAccount);

		user.setAddress(profileForm.getAddress());
		userRepository.save(user);

		return "profile";
	}

	/**
	 * Maps the profile change form of a special user to modelMap.
	 * @param userAccount
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/user/profile/{pid}/change")
	public String changeProfile(@PathVariable("pid") UserAccount userAccount, ModelMap modelMap) {
		
		User user = userRepository.findByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("profileForm", new ProfileForm());
		return "editprofile";
	}

}

package bookshop.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

import javax.validation.Valid;

import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	 * Reads data from the registrationForm for administrators and registers a new user with the given role.
	 * @param registrationForm
	 * @param result
	 * @param role
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/user/add/new")
	public String registerNewUser(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm,
			BindingResult result, @RequestParam("roleInput") String role) {

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
				new Role(role));
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
	 * Reads data from the registrationForm for unregistered users and registers a new customer.
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
	 * Maps the registration form for unregistered users to modelMap.
	 * @param modelMap
	 */
	@RequestMapping("/user/register")
	public String register(ModelMap modelMap) {
		
		modelMap.addAttribute("registrationForm", new RegistrationForm());
		return "register";
	}
	
	/**
	 * Maps the given user and the current user to modelMap for the profile view.
	 * @param userAccount
	 * @param currentUserAccount
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_BOSS') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/user/profile/{pid}")
	public String profile(@PathVariable("pid") UserAccount userAccount, @LoggedIn Optional<UserAccount> currentUserAccount, Model modelMap) {
		
		User user = userRepository.findUserByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		User currentUser = userRepository.findUserByUserAccount(currentUserAccount.get());
		modelMap.addAttribute("currentUser", currentUser);
		return "profile";
	}
	
	/**
	 * Maps the current user to modelMap for the own profile view.
	 * @param userAccount
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER') || hasRole('ROLE_EMPLOYEE')")
	@RequestMapping("/user")
	public String myProfile(@LoggedIn Optional<UserAccount> userAccount, Model modelMap) {
		
		User user = userRepository.findUserByUserAccount(userAccount.get());
		modelMap.addAttribute("user", user);
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
	@PreAuthorize("hasRole('ROLE_ADMIN')|| hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/user/profile/{pid}/change/edit")
	public String changeProfileEdit(@PathVariable("pid") UserAccount userAccount, @ModelAttribute("profileForm") @Valid ProfileForm profileForm,
			BindingResult result, ModelMap modelMap) {

		User user = userRepository.findUserByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("userAccount", userAccount);
		
		if (!profileForm.getPasswordRepeat().equals(profileForm.getPassword())) {
			result.addError(new ObjectError("password.noMatch", "Die eingegebenen Passwörter stimmen nicht überein!"));
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		if (!encoder.matches(profileForm.getOldPassword(), userAccount.getPassword().toString())) {
			result.addError(new ObjectError("password.old.error", "Das eingegebene alte Passwort ist nicht korrekt!"));
		}
		
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
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USERMANAGER') || hasRole('ROLE_BOSS')")
	@RequestMapping("/user/profile/{pid}/change")
	public String changeProfile(@PathVariable("pid") UserAccount userAccount, @LoggedIn Optional<UserAccount> currentUserAccount, ModelMap modelMap) {
		
		User user = userRepository.findUserByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		User currentUser = userRepository.findUserByUserAccount(currentUserAccount.get());
		modelMap.addAttribute("currentUser", currentUser);
		
		if (userAccount.hasRole(new Role("ROLE_ADMIN")) && !currentUserAccount.get().hasRole(new Role("ROLE_ADMIN"))) {
			return "profile";
		}
		
		if (!userAccount.hasRole(new Role("ROLE_BOSS")) && !currentUserAccount.get().hasRole(new Role("ROLE_ADMIN")) && !currentUserAccount.get().hasRole(new Role("ROLE_USERMANAGER"))) {
			return "profile";
		}
		
		modelMap.addAttribute("profileForm", new ProfileForm());
		return "editprofile";
	}
	
	/**
	 * Reads data from the profileForm and changes profile data of the current user.
	 * @param userAccount
	 * @param profileForm
	 * @param result
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER') || hasRole('ROLE_EMPLOYEE')")
	@RequestMapping("/user/profile/change/edit")
	public String changeProfileEditOfMe(@LoggedIn Optional<UserAccount> userAccount, @ModelAttribute("profileForm") @Valid ProfileForm profileForm,
			BindingResult result, ModelMap modelMap) {

		User user = userRepository.findUserByUserAccount(userAccount.get());
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("userAccount", userAccount);
		
		if (!profileForm.getPasswordRepeat().equals(profileForm.getPassword())) {
			result.addError(new ObjectError("password.noMatch", "Die eingegebenen Passwörter stimmen nicht überein!"));
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		if (!encoder.matches(profileForm.getOldPassword(), userAccount.get().getPassword().toString())) {
			result.addError(new ObjectError("password.old.error", "Das eingegebene alte Passwort ist nicht korrekt!"));
		}
		
		Iterable<User> users = userRepository.findAll();	
		
		for (User u : users) {
			if (u.getUserAccount().getEmail().equals(profileForm.getEmail()) && !(u.getUserAccount().equals(userAccount))) {
				result.addError(new ObjectError("email.isUsed", "Die eingegebene E-Mail-Adresse ist bereits vergeben!"));
			}
		}
		
		if (result.hasErrors()) {
			return "editprofile";
		}
		
		userAccount.get().setFirstname(profileForm.getFirstname());
		userAccount.get().setLastname(profileForm.getLastname());
		userAccount.get().setEmail(profileForm.getEmail());
		userAccountManager.changePassword(userAccount.get(), profileForm.getPassword());
		userAccountManager.save(userAccount.get());

		user.setAddress(profileForm.getAddress());
		userRepository.save(user);

		return "profile";
	}

	/**
	 * Maps the profile change form of the current user to modelMap.
	 * @param userAccount
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER') || hasRole('ROLE_EMPLOYEE')")
	@RequestMapping("/user/profile/change")
	public String changeProfileOfMe(@LoggedIn Optional<UserAccount> userAccount, ModelMap modelMap) {
		
		User user = userRepository.findUserByUserAccount(userAccount.get());
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("profileForm", new ProfileForm());
		return "editprofile";
	}
	
	/**
	 * Maps the given user and the current user to modelMap for the account setting view.
	 * @param userAccount
	 * @param currentUserAccount
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/user/profile/{pid}/accountsettings")
	public String changeAccountsettings(@PathVariable("pid") UserAccount userAccount, @LoggedIn Optional<UserAccount> currentUserAccount, ModelMap modelMap) {
		
		User user = userRepository.findUserByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		User currentUser = userRepository.findUserByUserAccount(currentUserAccount.get());
		modelMap.addAttribute("currentUser", currentUser);
		
		if ((userAccount.hasRole(new Role("ROLE_ADMIN")) || userAccount.hasRole(new Role("ROLE_USERMANAGER"))) && !currentUserAccount.get().hasRole(new Role("ROLE_ADMIN"))) {
			return "profile";
		}
		
		int numberAdmins = userManagement.getNumberOfAdmins();
		modelMap.addAttribute("numberAdmins", numberAdmins);
		
		return "editaccountsettings";
	}
	
	/**
	 * Maps the given user and the current user to modelMap and disables the given userAccount.
	 * @param userAccount
	 * @param currentUserAccount
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/user/profile/{pid}/accountsettings/disable")
	public String disable(@PathVariable("pid") UserAccount userAccount, @LoggedIn Optional<UserAccount> currentUserAccount, Model modelMap) {
		
		User user = userRepository.findUserByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		User currentUser = userRepository.findUserByUserAccount(currentUserAccount.get());
		modelMap.addAttribute("currentUser", currentUser);
		
		if (userAccount.hasRole(new Role("ROLE_ADMIN"))) {
			return "profile";
		}
		
		if ((userAccount.hasRole(new Role("ROLE_ADMIN")) || userAccount.hasRole(new Role("ROLE_USERMANAGER"))) && !currentUserAccount.get().hasRole(new Role("ROLE_ADMIN"))) {
			return "profile";
		}
		
		userManagement.disable(userAccount);
		userAccountManager.save(userAccount);
		
		int numberAdmins = userManagement.getNumberOfAdmins();
		modelMap.addAttribute("numberAdmins", numberAdmins);
		
		return "editaccountsettings";
	}
	
	/**
	 * Maps the given user and the current user to modelMap and enables the given userAccount.
	 * @param userAccount
	 * @param currentUserAccount
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/user/profile/{pid}/accountsettings/enable")
	public String enable(@PathVariable("pid") UserAccount userAccount, @LoggedIn Optional<UserAccount> currentUserAccount, Model modelMap) {
		
		User user = userRepository.findUserByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		User currentUser = userRepository.findUserByUserAccount(currentUserAccount.get());
		modelMap.addAttribute("currentUser", currentUser);
		
		if ((userAccount.hasRole(new Role("ROLE_ADMIN")) || userAccount.hasRole(new Role("ROLE_USERMANAGER"))) && !currentUserAccount.get().hasRole(new Role("ROLE_ADMIN"))) {
			return "profile";
		}
		
		userManagement.enable(userAccount);
		userAccountManager.save(userAccount);
		
		int numberAdmins = userManagement.getNumberOfAdmins();
		modelMap.addAttribute("numberAdmins", numberAdmins);
		
		return "editaccountsettings";
	}
	
	/**
	 * Maps the given user and the current user to modelMap and adds the given role to the given userAccount.
	 * @param userAccount
	 * @param currentUserAccount
	 * @param modelMap
	 * @param roleInput
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/user/profile/{pid}/accountsettings/roles/add")
	public String addRole(@PathVariable("pid") UserAccount userAccount, @LoggedIn Optional<UserAccount> currentUserAccount, Model modelMap, @RequestParam("roleInput") String role) {
		
		User user = userRepository.findUserByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		User currentUser = userRepository.findUserByUserAccount(currentUserAccount.get());
		modelMap.addAttribute("currentUser", currentUser);
		
		if ((userAccount.hasRole(new Role("ROLE_ADMIN")) || userAccount.hasRole(new Role("ROLE_USERMANAGER"))) && !currentUserAccount.get().hasRole(new Role("ROLE_ADMIN"))) {
			return "profile";
		}
		
		userManagement.addRole(userAccount, new Role(role));
		userAccountManager.save(userAccount);
		
		int numberAdmins = userManagement.getNumberOfAdmins();
		modelMap.addAttribute("numberAdmins", numberAdmins);
		
		return "editaccountsettings";
	}
	
	/**
	 * Maps the given user and the current user to modelMap and removes the given role from the given userAccount.
	 * @param userAccount
	 * @param currentUserAccount
	 * @param modelMap
	 * @param roleInput
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USERMANAGER')")
	@RequestMapping("/user/profile/{pid}/accountsettings/roles/remove")
	public String removeRole(@PathVariable("pid") UserAccount userAccount, @LoggedIn Optional<UserAccount> currentUserAccount, Model modelMap, @RequestParam("roleInput") String roleInput) {
		
		User user = userRepository.findUserByUserAccount(userAccount);
		modelMap.addAttribute("user", user);
		User currentUser = userRepository.findUserByUserAccount(currentUserAccount.get());
		modelMap.addAttribute("currentUser", currentUser);
		
		if ((userAccount.hasRole(new Role("ROLE_ADMIN")) || userAccount.hasRole(new Role("ROLE_USERMANAGER"))) && !currentUserAccount.get().hasRole(new Role("ROLE_ADMIN"))) {
			return "profile";
		}
		
		userManagement.removeRole(userAccount, new Role(roleInput));
		userAccountManager.save(userAccount);
		
		int numberAdmins = userManagement.getNumberOfAdmins();
		modelMap.addAttribute("numberAdmins", numberAdmins);
		
		return "editaccountsettings";
	}
	
}

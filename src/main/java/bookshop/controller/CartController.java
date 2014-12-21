package bookshop.controller;

import static org.joda.money.CurrencyUnit.EUR;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookshop.model.Article;
import bookshop.model.PDFCreator;
import bookshop.model.Article.ArticleId;
import bookshop.model.OrderManagement;



@Controller
public class CartController {

	private final OrderManager<Order> orderManager;

	
	@Autowired
	public CartController(OrderManager<Order> orderManager) {
		
		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
	}
	
	/**
	 * Shows current state of your cart
	 * @param modelMap
	 * @param name
	 * @param session
	 * @return
	 */
	//@PreAuthorize("!hasRole('ROLE_BOSS') || !hasRole('ROLE_ADMIN') || !hasRole('ROLE_EMPLOYEE') || !hasRole('ROLE_SALESMANAGER') || !hasRole('ROLE_ARTICLEMANAGER') || !hasRole('ROLE_USERMANAGER') || !hasRole('ROLE_EVENTMANAGER')")
	@RequestMapping(value="/cart")
	public String cart(ModelMap modelMap, String name, HttpSession session){
		Cart cart = getCart(session);
		modelMap.addAttribute("price", cart.getPrice());
		return "cart";
	}
	
	/**
	 * Sets the current state of your cart to an order and create a pdf file
	 * @param session
	 * @param userAccount
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value="/cart/checkout", method = RequestMethod.POST)
	public String buy(HttpSession session, @LoggedIn Optional<UserAccount> userAccount){

		return userAccount.map(account -> {
			
				Order order = new Order(account, Cash.CASH);
				Cart cart = getCart(session);
				cart.addItemsTo(order);
				
				
				orderManager.add(order);
				cart.clear();
				PDFCreator pdf = new PDFCreator();
				
				HttpServletResponse response = null;
				HttpServletRequest request = null;
				pdf.pdfCreate(order, userAccount.get());
				return "redirect:/";
			}).orElse("redirect:/cart");
	}
	
	
	/**
	 * Add an article to your cart
	 * @param modelMap
	 * @param number
	 * @param article
	 * @param session
	 */
	
	//@PreAuthorize("!hasRole('ROLE_BOSS') || !hasRole('ROLE_ADMIN') || !hasRole('ROLE_EMPLOYEE') || !hasRole('ROLE_SALESMANAGER') || !hasRole('ROLE_ARTICLEMANAGER') || !hasRole('ROLE_USERMANAGER') || !hasRole('ROLE_EVENTMANAGER')")
	@RequestMapping(value="/cart", method = RequestMethod.POST)
	public String addArticleIntoCart(ModelMap modelMap, @RequestParam("number") int number, @RequestParam("article") Article article,
		HttpSession session){
		if(number <= 1 || number > 10){
			number = 1;
		}
		Quantity quantity = Units.of(number);
		Cart cart = getCart(session);
		OrderLine orderline = new OrderLine(article,quantity);
		/*if(quantity > article.){
			return "redirect:/cart";
		}*/
		
		cart.addOrUpdateItem(article, quantity);	
		switch (article.getType()) {
		case DVD:
			return "redirect:/article/dvd";
		case CD:
			return "redirect:/article/cd";
		case BOOK:
		default:
			return "redirect:/article/book";			
		}
	}
	
	/**
	 * Clears your entire card
	 * @param session
	 */
	//@PreAuthorize("!hasRole('ROLE_BOSS') || !hasRole('ROLE_ADMIN') || !hasRole('ROLE_EMPLOYEE') || !hasRole('ROLE_SALESMANAGER') || !hasRole('ROLE_ARTICLEMANAGER') || !hasRole('ROLE_USERMANAGER') || !hasRole('ROLE_EVENTMANAGER')")
	@RequestMapping(value = "/cart/clear")
	public String clear (HttpSession session){
		
		Cart cart = getCart(session);
		cart.clear();
		return "cart";
	}
	
	/**
	 * Delete a specific item from your card
	 * @param session
	 * @param id
	 */
	//@PreAuthorize("!hasRole('ROLE_BOSS') || !hasRole('ROLE_ADMIN') || !hasRole('ROLE_EMPLOYEE') || !hasRole('ROLE_SALESMANAGER') || !hasRole('ROLE_ARTICLEMANAGER') || !hasRole('ROLE_USERMANAGER') || !hasRole('ROLE_EVENTMANAGER')")
	@RequestMapping(value="/cart/delete", method = RequestMethod.POST)
	public String delete(HttpSession session, @RequestParam("test") String id){
		Cart cart = getCart(session);
		cart.removeItem(id);
		return "redirect:/cart";
	}
	
	/**
	 * get the current state of your card
	 * @param session
	 */
	//@PreAuthorize("!hasRole('ROLE_BOSS') || !hasRole('ROLE_ADMIN') || !hasRole('ROLE_EMPLOYEE') || !hasRole('ROLE_SALESMANAGER') || !hasRole('ROLE_ARTICLEMANAGER') || !hasRole('ROLE_USERMANAGER') || !hasRole('ROLE_EVENTMANAGER')")
	@ModelAttribute("cart")
	private Cart getCart(HttpSession session) {

		Cart cart = (Cart) session.getAttribute("cart");

		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}

		return cart;
	}
}

package bookshop.controller;

import static org.joda.money.CurrencyUnit.EUR;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookshop.model.Article;
import bookshop.model.PDFCreator;



@Controller
public class CartController {

	private final OrderManager<Order> orderManager;
	private final Inventory<InventoryItem> inventory;
	
	@Autowired
	public CartController(OrderManager<Order> orderManager, Inventory<InventoryItem> inventory) {
		
		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
		this.inventory = inventory;
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

		Order order = new Order(userAccount.get(), Cash.CASH);
		Cart cart = getCart(session);
		cart.addItemsTo(order);
		for(OrderLine orderLine : order.getOrderLines()){
			System.out.println(inventory.findByProductIdentifier(orderLine.getProductIdentifier()).get().getQuantity());
			inventory.findByProductIdentifier(orderLine.getProductIdentifier()).get().decreaseQuantity(orderLine.getQuantity());
			System.out.println(inventory.findByProductIdentifier(orderLine.getProductIdentifier()).get().getQuantity());
		}
		
		orderManager.add(order);
		cart.clear();
		PDFCreator pdf = new PDFCreator();
		pdf.pdfCreate(order, userAccount.get());
		
		/*
		return userAccount.map(account -> {
			
				Order order = new Order(account, Cash.CASH);
				Cart cart = getCart(session);
				cart.addItemsTo(order);
				
				
				orderManager.add(order);
				cart.clear();
				PDFCreator pdf = new PDFCreator();
				pdf.pdfCreate(order, userAccount.get());
				
				for(OrderLine orderLine : order.getOrderLines()){
					System.out.println(inventory.findByProductIdentifier(orderLine.getProductIdentifier()).get().getQuantity());
					inventory.findByProductIdentifier(orderLine.getProductIdentifier()).get().decreaseQuantity(orderLine.getQuantity());
					System.out.println(inventory.findByProductIdentifier(orderLine.getProductIdentifier()).get().getQuantity());
				}
				
				
				
				return "redirect:/";
			}).orElse("redirect:/cart");
		*/
		return "redirect:/";
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
		Quantity quantity1 = Units.of(number);
		if(number < 1  || quantity1.isGreaterThan(inventory.findByProductIdentifier(article.getIdentifier()).get().getQuantity())){
			number = 0;

		}
		Quantity quantity = Units.of(number);
		Cart cart = getCart(session);
		
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
	public String delete(HttpSession session, @RequestParam("delete") String id){
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

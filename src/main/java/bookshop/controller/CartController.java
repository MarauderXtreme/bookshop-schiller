package bookshop.controller;

import static org.joda.money.CurrencyUnit.EUR;

import java.util.Optional;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookshop.model.Article;
import bookshop.model.Article.ArticleId;
import bookshop.model.Book;



@Controller
public class CartController {

	private final OrderManager<Order> orderManager;
	private Article article = new Book("test", "test", "test", 1, ArticleId.BOOK, Money.of(EUR, 9.99), "text");
	
	
	@Autowired
	public CartController(OrderManager<Order> orderManager) {
		
		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
	}
	
	/*
	@RequestMapping(value = "/checkout")
	public String buy(HttpSession session, @LoggedIn Optional<UserAccount> userAccount) {

		return userAccount.map(account -> {

				Order order = new Order(account, Cash.CASH);
				Cart cart = getCart(session);
				cart.toOrder(order);

				orderManager.payOrder(order);
				orderManager.completeOrder(order);
				orderManager.add(order);
				
				cart.clear();

				return "redirect:/";
			}).orElse("redirect:/cart");
	}
	*/
	
	@RequestMapping(value="/checkout", method = RequestMethod.POST)
	public String buy(HttpSession session){
		
		return "redirect:cart";
	}
	
	@RequestMapping(value="/cart", method = RequestMethod.POST)
	public String addArticleIntoCart(@RequestParam("number") int number, @RequestParam("article") Article article,
		HttpSession session){
		
		if(number <= 1 || number > 10){
			number = 1;
		}
		
		Quantity quantity = Units.of(number);
		OrderLine orderLine = new OrderLine(article, quantity);
		Cart cart = getCart(session);
		cart.add(orderLine);
		
		return "cart";
	}
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	 public String getCart(){
		return "cart";
	}
	
	
	@RequestMapping(value = "/clear", method = RequestMethod.POST)
	public String clear (HttpSession session){
		
		Cart cart = getCart(session);
		cart.clear();	
		return "redirect:cart";
	}
	
	

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

package bookshop.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderIdentifier;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bookshop.model.Article;
import bookshop.model.PDFBuilder;
import bookshop.model.PDFCreator;
import bookshop.model.Statistic;


@Controller
public class OrderController {
	private final OrderManager<Order> orderManager;
	private final Inventory<InventoryItem> inventory;
	private final BusinessTime date;
	private Order orderPDF;
	
	@Autowired
	public OrderController(OrderManager<Order> orderManager, Inventory<InventoryItem> inventory, BusinessTime date){
		this.orderManager = orderManager;
		this.inventory = inventory;
		this.date = date;
	}
	
	/**
	 * Shows orders
	 * @param session
	 * @param modelMap
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN') || hasRole('ROLE_EMPLOYEE')")
	@RequestMapping("/admin/orders")
	public String getOrders(HttpSession session, ModelMap modelMap){
		
		modelMap.addAttribute("ordersOpen", orderManager.find(OrderStatus.OPEN));
		modelMap.addAttribute("ordersCompleted", orderManager.find(OrderStatus.COMPLETED));
		
		return "orders";
	}
	
	
	@RequestMapping("/user/downloadPDF")
	public ModelAndView gedOrderPDF(){
		//PDFBuilder pdf = null;
		//pdf.setData(userAccount.get());
		
		return new ModelAndView("pdfView", "orderspdf", orderPDF);
	}
	
	@RequestMapping(value = "/order/PDF", method = RequestMethod.GET)
	public String viewHome() {
		return "home";
	}
	
	/**
	 * Get Orders from logged in User
	 * @param modelMap
	 * @param userAccount
	 * @throws IOException 
	 * @throws DocumentException 
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value="/user/order")
	public String getMyOrders(ModelMap modelMap, @LoggedIn Optional<UserAccount> userAccount){
		
		modelMap.addAttribute("myOrders", orderManager.find(userAccount.get()));
		
		return "/myorders";
	}
	
	@PreAuthorize("hasRole('ROLE_CUSTOMER') || hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN') || hasRole('ROLE_SALESMANAGER')")
	@RequestMapping(value="/order/detail")
	public String getOrderDetails(ModelMap modelMap, @RequestParam("orderdetail") Order order){
		
		this.orderPDF = order;
		modelMap.addAttribute("curDir", System.getProperty("user.dir"));
		modelMap.addAttribute("detailordersID", order.getIdentifier().toString());
		modelMap.addAttribute("detailorders", order.getOrderLines());
		
		return "/ordersdetail";
	}
	
	/**
	 * Cancel order
	 * @param order
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value="/order/cancel")
	public String cancelOrder(@RequestParam("orderDelete") Order order){
		orderManager.cancelOrder(order);
		return "redirect:/user/order";
	}
	
	/**
	 * Complete an order
	 * @param order
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/order/complete")
	public String compledOrder(@RequestParam("orderComplete") Order order){
		orderManager.payOrder(order);
		orderManager.completeOrder(order);
		return "redirect:/admin/orders";
	}
	
	/**
	 * Shows stock
	 * @param session
	 * @param modelMap
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN') || hasRole('ROLE_SALESMANAGER')")
	@RequestMapping("/admin/stock")
	public String stock(HttpSession session, ModelMap modelMap){

		modelMap.addAttribute("stock", inventory.findAll());
		return "stock";
	}
	
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN') || hasRole('ROLE_SALESMANAGER')")
	@RequestMapping("/article/reorder")
	public String makeAnOrder(@RequestParam("number") int number, @RequestParam("article") Article article, @LoggedIn Optional<UserAccount> userAccount){
		
		Quantity quantity;		
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		quantity = new Quantity(number, item.get().getQuantity().getMetric(), item.get().getQuantity().getRoundingStrategy());
		
		//quantity = item.get().getQuantity();
		//quantity = quantity.divide(item.get().getQuantity());
		
		item.get().increaseQuantity(quantity);
		
		OrderLine orderLine = new OrderLine(article, quantity);
		System.out.println(userAccount.get().getIdentifier());
		
		
		Order order = new Order(userAccount.get(), Cash.CASH);
		order.add(orderLine);
		
		orderManager.payOrder(order);
		
		orderManager.add(order);
		return"redirect:/admin/stock";
	}
	
}

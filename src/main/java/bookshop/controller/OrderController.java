package bookshop.controller;

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
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookshop.model.Statistic;


@Controller
public class OrderController {
	private final OrderManager<Order> orderManager;
	private final Inventory<InventoryItem> inventory;
	private final BusinessTime date;
	
	@Autowired
	public OrderController(OrderManager<Order> orderManager, Inventory<InventoryItem> inventory, BusinessTime date){
		this.orderManager = orderManager;
		this.inventory = inventory;
		this.date = date;
	}
	
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/orders")
	public String orders(HttpSession session, ModelMap modelMap){
		
		modelMap.addAttribute("ordersCompleted", orderManager.find(OrderStatus.COMPLETED));
		
		return "orders";
	}
	
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value="/user/order")
	public String getOrders(ModelMap modelMap, @LoggedIn Optional<UserAccount> userAccount){
		modelMap.addAttribute("myOrders", orderManager.find(userAccount.get()));
		
		return "myorders";
	}
	
	@RequestMapping(value="/order/cancel")
	public String cancelOrder(@RequestParam("oderDelete") Order order){
		System.out.println(order);
		order.isCanceled();
		System.out.println(order.getOrderStatus());
		return "redirect:/user/order";
	}
	
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/stock")
	public String stock(HttpSession session, ModelMap modelMap){

		for(InventoryItem item : inventory.findAll()){
			
		}
		
		modelMap.addAttribute("stock", inventory.findAll());
		return "stock";
	}
	
	public String getMyOrders(){
		
		return "";
	}
}

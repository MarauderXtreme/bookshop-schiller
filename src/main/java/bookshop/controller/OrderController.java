package bookshop.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


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
		
		return "/orders";
	}
	/*
	@RequestMapping(value="/admin/orders", method = RequestMethod.GET)
	public String getOrders(ModelMap modelMap){
		
		return "redirect:/orders";
	}*/
	
	@RequestMapping("/admin/stock")
	public String stock(HttpSession session, ModelMap modelMap){
		modelMap.addAttribute("stock", inventory.findAll());
		return "/stock";
	}
	
	@RequestMapping("/admin/statictics")
	public String statistic(HttpSession session, ModelMap modelMap, @LoggedIn UserAccount userAccount){
		//LocalDateTime date;
		//date.getTime();
		LocalDateTime time = date.getTime();
		time.minusDays(7);		
		//modelMap.addAttribute("ordersLastWeek", orderManager.find(date.getTime(), time));
		Order ordersLastWeek = new Order(userAccount);
		
		/*
		for(Order order : orderManager.find(date.getTime(), time)){	
			for(OrderLine orderLine : order.getOrderLines()){
				ordersLastWeek.add(orderLine);
			}
		}	
		
		modelMap.addAttribute("ordersLastWeek", ordersLastWeek.getOrderLines());
		*/
		for(Order order : orderManager.find(OrderStatus.COMPLETED)){	
			for(OrderLine orderLine : order.getOrderLines()){
				ordersLastWeek.add(orderLine);
			}
		}	
		
		modelMap.addAttribute("ordersLastWeek", ordersLastWeek.getOrderLines());
		
		return "/statistics";
	}
	
	
	public String getMyOrders(){
		
		return "";
	}
}

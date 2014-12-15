package bookshop.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.catalog.ProductIdentifier;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		
		return "/orders";
	}
	
	@RequestMapping(value="/user/orders", method = RequestMethod.GET)
	public String getOrders(ModelMap modelMap,@PathVariable("pid") UserAccount userAccount){
			
		modelMap.addAttribute("myOrders", orderManager.find(userAccount));
		return "/myorders";
	}
	
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/stock")
	public String stock(HttpSession session, ModelMap modelMap){

		for(InventoryItem item : inventory.findAll()){
			
		}
		
		modelMap.addAttribute("stock", inventory.findAll());
		return "/stock";
	}
	/*
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/statictics")
	public String statistic(HttpSession session, ModelMap modelMap, @LoggedIn UserAccount userAccount){
		//LocalDateTime date;
		//date.getTime();
		LocalDateTime time = date.getTime();
		time.minusDays(7);		
		//modelMap.addAttribute("ordersLastWeek", orderManager.find(date.getTime(), time));
		//Order ordersLastWeek = new Order(userAccount);
		//List<Statistic> test = new ArrayList<Statistic>();
		//Statistic bla = new Statistic();
		InventoryItem localItem;
		
		for(InventoryItem item : statistic.findAll()){
			
			
			for(Order order : orderManager.find(OrderStatus.COMPLETED)){
				
				for(OrderLine orderLine : order.getOrderLines()){
					
					ProductIdentifier name1 = item.getProduct().getIdentifier();
					ProductIdentifier name2 = orderLine.getProductIdentifier();
					
					if(name1.equals(name2)== true){
						
					}
						
				}			
				
			}
		}
		
		
		/*
		List<Statistic> test = new ArrayList<Statistic>();
		List<InventoryItem> test = new ArrayList<InventoryItem>();
		
		for(InventoryItem item : inventory.findAll()){
			
			for(Order order : orderManager.find(OrderStatus.COMPLETED)){
				
				for(OrderLine orderLine : order.getOrderLines()){
					
					test.add(item);
					
					//statistic.updateArticleQuantity(orderLine.getQuantity());
				}
			}
		}
		
		*/
		/*
		for(Order order : orderManager.find(OrderStatus.COMPLETED)){
			
			for(OrderLine orderLine : order.getOrderLines()){
				
				ProductIdentifier name2 = orderLine.getProductIdentifier();
				
				
					
			}			
			
		}
		*/
		/*
		modelMap.addAttribute("statistic", inventory.findAll());
		
		//modelMap.addAttribute("ordersLastWeek", ordersLastWeek.getOrderLines());
		
		return "/statistics";
	}
	*/
	
	public String getMyOrders(){
		
		return "";
	}
}

package bookshop.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import bookshop.model.OrderManagement;

@Controller
public class StatisticController {
	
	private final OrderManager<Order> orderManager;
	private final Inventory<InventoryItem> inventory;
	
	@Autowired
	public StatisticController(OrderManager<Order> orderManager, Inventory<InventoryItem> inventory){
		this.inventory = inventory;
		this.orderManager = orderManager;
	}
	
	
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/statictics")
	public String statistic(HttpSession session, ModelMap modelMap, @LoggedIn UserAccount userAccount){
		//LocalDateTime date;
		//date.getTime();
		//LocalDateTime time = date.getTime();
		//time.minusDays(7);		
		//modelMap.addAttribute("ordersLastWeek", orderManager.find(date.getTime(), time));
		//Order ordersLastWeek = new Order(userAccount);
		//List<Statistic> test = new ArrayList<Statistic>();
		//Statistic bla = new Statistic();
		//InventoryItem localItem;
		
		Order statisticOrder = new Order(userAccount);
		
		for(InventoryItem item : inventory.findAll()){
			//Quantity test = item.getQuantity();
			//test.subtract(test);
			//item.decreaseQuantity(item.getQuantity());
			Quantity quantity = item.getQuantity();
			quantity = quantity.subtract(item.getQuantity());
			
			for(Order order : orderManager.find(OrderStatus.COMPLETED)){
				
				for(OrderLine orderLine : order.getOrderLines()){
					
					ProductIdentifier name1 = item.getProduct().getIdentifier();
					ProductIdentifier name2 = orderLine.getProductIdentifier();
					System.out.println();
					System.out.println(item.getProduct().getName());
					System.out.println(name1);
					System.out.println(orderLine.getProductName());
					System.out.println(name2);
					System.out.println(orderLine.getQuantity());
					
					if(name1.equals(name2)== true){
						//test.add(orderLine.getQuantity());
						quantity = quantity.add(orderLine.getQuantity());
						
						System.out.println(quantity);
					}
						
				}			
				
			}
			
			OrderLine orderLine = new OrderLine(item.getProduct(), quantity);
			statisticOrder.add(orderLine);
		}
		
		/*Cart cart = new Cart();
		
		for(Order order : orderManager.find(OrderStatus.COMPLETED)){
			
			for(OrderLine orderLine : order.getOrderLines()){
				OrderManagement orderManagement = orderLine;
				cart.addOrUpdateItem(orderManagement.getProduct(), orderLine.getQuantity());
					
			}			
			
		}
		*/
		
		modelMap.addAttribute("statistic", statisticOrder.getOrderLines());
		
		
		return "/statistics";
	}
}

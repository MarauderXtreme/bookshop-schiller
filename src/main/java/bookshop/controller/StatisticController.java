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
import org.salespointframework.time.BusinessTime;
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
	private final BusinessTime date;
	
	@Autowired
	public StatisticController(OrderManager<Order> orderManager, Inventory<InventoryItem> inventory, BusinessTime date){
		this.inventory = inventory;
		this.orderManager = orderManager;
		this.date = date;
	}
	
	/**
	 * 
	 * @param session
	 * @param modelMap
	 * @param userAccount
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/statictics")
	public String statistic(HttpSession session, ModelMap modelMap, @LoggedIn UserAccount userAccount){

		LocalDateTime time = date.getTime();
		time = time.minusDays(7);		

		
		Order statisticOrder = new Order(userAccount);
		Order gesOrder = new Order(userAccount);
		
		for(Order order : orderManager.find(OrderStatus.COMPLETED)){
			for(OrderLine orderLine : order.getOrderLines()){
				gesOrder.add(orderLine);
			}
		}
		modelMap.addAttribute("statisticPriceAll", gesOrder.getTotalPrice());
		
		for(InventoryItem item : inventory.findAll()){

			Quantity quantity = item.getQuantity();
			quantity = quantity.subtract(item.getQuantity());
			
			for(Order order : orderManager.find(time, date.getTime())){
				
				if(order.isCompleted()==true){
					for(OrderLine orderLine : order.getOrderLines()){
						
						ProductIdentifier name1 = item.getProduct().getIdentifier();
						ProductIdentifier name2 = orderLine.getProductIdentifier();
						
						
						if(name1.equals(name2)== true){
							quantity = quantity.add(orderLine.getQuantity());
						}
							
					}			
				}
			}
			
			OrderLine orderLine = new OrderLine(item.getProduct(), quantity);
			statisticOrder.add(orderLine);
		}
		
		
		modelMap.addAttribute("statistic", statisticOrder.getOrderLines());
		modelMap.addAttribute("statisticPrice", statisticOrder.getTotalPrice());
		
		return "/statistics";
	}
}

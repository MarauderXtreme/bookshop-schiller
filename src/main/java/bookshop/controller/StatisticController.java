package bookshop.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.joda.money.Money;
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
import bookshop.model.StatisticsManagement;

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
	 * Shows actual statistics
	 * @param session
	 * @param modelMap
	 * @param userAccount
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN') || hasRole('ROLE_SALESMANAGER')")
	@RequestMapping("/admin/statictics")
	public String statistic(HttpSession session, ModelMap modelMap, @LoggedIn Optional<UserAccount> userAccount){

		LocalDateTime time = date.getTime();
		time = time.minusDays(7);		
		
		Money profit;
		Money profittotal;
		
		Order statisticOrder = new Order(userAccount.get());
		Order gesOrderBuy = new Order(userAccount.get());
		Order gesOrderSell = new Order(userAccount.get());
		Order sellOrder = new Order(userAccount.get());
		
		StatisticsManagement sm = new StatisticsManagement(orderManager, inventory, date);
		
		gesOrderSell = sm.getGesOrdersSell(userAccount.get());

		gesOrderBuy = sm.getGesOrdersBuy(userAccount.get());
		
		for(InventoryItem item : inventory.findAll()){
			
			/*
			Quantity quantity = item.getQuantity();
			quantity = quantity.subtract(item.getQuantity());
			Quantity quantity1 = quantity;
			
			for(Order order : orderManager.find(time, date.getTime())){
				
				
				if(order.isPaid()==true){
				
				
					for(OrderLine orderLine : order.getOrderLines()){
						
						ProductIdentifier name1 = item.getProduct().getIdentifier();
						ProductIdentifier name2 = orderLine.getProductIdentifier();
						
						
						if(name1.equals(name2)== true){
								quantity = quantity.add(orderLine.getQuantity());
						}
							
					}	
				
				}else{
					if(order.isCompleted() == true){
						for(OrderLine orderLine : order.getOrderLines()){
							
							ProductIdentifier name1 = item.getProduct().getIdentifier();
							ProductIdentifier name2 = orderLine.getProductIdentifier();
							
							
							if(name1.equals(name2)== true){
									quantity1 = quantity1.add(orderLine.getQuantity());
								
							}
								
						}	
					}
				}
			}
			*/
			
			OrderLine orderLine = sm.getStatistic(true, item);
			OrderLine orderLine1 = sm.getStatistic(false, item);
			
			statisticOrder.add(orderLine);
			sellOrder.add(orderLine1);
		}
		
		profit = sellOrder.getTotalPrice().minus(statisticOrder.getTotalPrice());
		profittotal = profit;
		
		modelMap.addAttribute("profit", profit);
		modelMap.addAttribute("profittotal", profittotal);
		
		modelMap.addAttribute("statisticsell", statisticOrder.getOrderLines());
		modelMap.addAttribute("statisticPriceSell", statisticOrder.getTotalPrice());
		modelMap.addAttribute("statisticbuy", sellOrder.getOrderLines());
		modelMap.addAttribute("statisticPriceBuy", sellOrder.getTotalPrice());
		
		modelMap.addAttribute("statisticPriceSellAll", gesOrderBuy.getTotalPrice());		
		modelMap.addAttribute("statisticPriceBuyAll", gesOrderSell.getTotalPrice());
		
		return "/statistics";
	}
		
}

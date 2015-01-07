package bookshop.model;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Entity;
import javax.servlet.http.HttpSession;

import org.joda.money.Money;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;


public class StatisticsManagement {
	
	private final OrderManager<Order> orderManager;
	private final Inventory<InventoryItem> inventory;
	private final BusinessTime date;
	
	public StatisticsManagement(OrderManager<Order> orderManager, Inventory<InventoryItem> inventory, BusinessTime date){
		this.orderManager = orderManager;
		this.inventory = inventory;
		this.date = date;
	}
	
	public Order getGesOrdersSell(UserAccount userAccount){
		Order order1 = new Order(userAccount);
		
		for(Order order : orderManager.find(OrderStatus.COMPLETED)){
			for(OrderLine orderLine : order.getOrderLines()){
				order1.add(orderLine);
			}
		}
		return order1;
	}
	
	public Order getGesOrdersBuy(UserAccount userAccount){
		Order order1 = new Order(userAccount);
		
		for(Order order : orderManager.find(OrderStatus.PAID)){
			for(OrderLine orderLine : order.getOrderLines()){
				order1.add(orderLine);
			}
		}
		return order1;
	}
	
	public OrderLine getStatistic(boolean type, InventoryItem item){
		LocalDateTime time = date.getTime();
		time = time.minusDays(7);
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
	
		OrderLine returnOrderLineBuy = new OrderLine(item.getProduct(),quantity);
		OrderLine returnOrderLineSell = new OrderLine(item.getProduct(),quantity1);
		
		
	if(type == true){
		return returnOrderLineBuy;
	}else{
		return returnOrderLineSell;
	}
	}
	
	public OrderLine statisticOfReservation(String event){
		LocalDateTime time = date.getTime();
		time = time.minusDays(7);
		Quantity quantity = Units.of(0);
		
		for(Order order : orderManager.find(time, date.getTime())){

			if(order.isPaid()==true){
			
			
				for(OrderLine orderLine : order.getOrderLines()){
					
					String event2 = orderLine.getProductName();
					
					
					if(event.equals(event2)== true){
							quantity = quantity.add(orderLine.getQuantity());
					}
						
				}	
			
			}
		}
		
		
		return null;	
	}
}

package bookshop.model;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.ui.ModelMap;

public class OrderManagement {

	private final OrderManager<Order> orderManager;
	private final BusinessTime date;
	
	
	public OrderManagement(OrderManager<Order> orderManager, BusinessTime date){
		this.orderManager = orderManager;
		this.date = date;
	}
	
	/*
	public order getOrders(){
		Order order = orderManager.find(ord)
		
		return order;
	}*/
	
	public String update(Order order){
		order.getOrderLines();
		
		return "true";
	}
	
	public String statistic(HttpSession session, ModelMap modelMap, @LoggedIn UserAccount userAccount){
		//LocalDateTime date;
		//date.getTime();
		LocalDateTime time = date.getTime();
		time.minusDays(7);		
		//modelMap.addAttribute("ordersLastWeek", orderManager.find(date.getTime(), time));
		Order ordersLastWeek = new Order(userAccount);
		
		
		for(Order order : orderManager.find(date.getTime(), time)){	
			for(OrderLine orderLine : order.getOrderLines()){
				ordersLastWeek.add(orderLine);
			}
		}	
		
		modelMap.addAttribute("ordersLastWeek", ordersLastWeek.getOrderLines());
		
		return "/statistics";
	}
	
	public String getName(OrderLine orderLine){
		return orderLine.getProductName();
	}
	
}

package bookshop.model;

import static org.joda.money.CurrencyUnit.EUR;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
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
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public class OrderManagement extends OrderLine{

	private final OrderManager<Order> orderManager;
	private final Inventory<InventoryItem> inventory;
	
	/**
	 * Constructor for OrderManagement
	 * @param oderManager
	 * @param inventory
	 */
	@Autowired
	public OrderManagement(OrderManager<Order> orderManager, Inventory<InventoryItem> inventory) {

		this.orderManager = orderManager;
		this.inventory = inventory;
	}
	
	public void reservation(String title, Optional<UserAccount> userAccount){
		//Erstellung der Order
		//title soll der Name der Veranstallung sein
		Quantity quantity = Units.of(1);
		Product product = new Product(title, Money.of(EUR, 0.00), Units.METRIC);
		product.addCategory("reservation");
		OrderLine orderLine = new OrderLine(product, quantity);
		Order order = new Order(userAccount.get());
		orderManager.add(order);
		orderManager.payOrder(order);
		orderManager.completeOrder(order);
		System.out.println(orderLine.getProductName());
		
		/*
			Die bookSeat Methode bitte hier rein schreiben
		*/	
	}
	public ArrayList<Order> orders(String event){
		//Iterable<Order> result;
		event = "Computerraum";
		ArrayList<Order> list = new ArrayList<Order>();
		System.out.println(event);
		
		for(Order order : orderManager.find(OrderStatus.PAID)){
			//System.out.println(order);
			
			for(OrderLine orderLine : order.getOrderLines()){
				System.out.println(orderLine.getProductName());
				if(orderLine.getProductName().equals(event)){
					list.add(order);
					System.out.println(order);
				}
			}
		}

		return list;
		
	}
}

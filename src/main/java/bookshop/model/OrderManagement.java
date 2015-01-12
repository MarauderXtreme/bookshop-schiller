package bookshop.model;

import static org.joda.money.CurrencyUnit.EUR;

import java.util.ArrayList;
import java.util.Optional;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderManagement extends OrderLine{

	private final OrderManager<Order> orderManager;
	/**
	 * Constructor for OrderManagement
	 * @param oderManager
	 * 	 */
	
	@Autowired
	public OrderManagement(OrderManager<Order> orderManager) {
		this.orderManager = orderManager;
	}
	
	/**
	 * Create a reservation for given user
	 * @param title
	 * @param userAccount
	 * @param number
	 */
	public void reservation(String title, Optional<UserAccount> userAccount, int number){
		System.out.println(number);
		Quantity quantity = Units.of(number);
		Product product = new Product(title, Money.of(EUR, 5.00), Units.METRIC);
		OrderLine orderLine = new OrderLine(product, quantity);
		Order order = new Order(userAccount.get(), Cash.CASH);
		order.add(orderLine);
		orderManager.payOrder(order);
		orderManager.add(order);

	
	}
	
	/**
	 * Returns an arraylist of all reservations
	 * @param event
	 * @return ArrayList<Order>
	 */
	public ArrayList<Order> orders(String event){
		ArrayList<Order> list = new ArrayList<Order>();
		
		for(Order order : orderManager.find(OrderStatus.PAID)){
			
			for(OrderLine orderLine : order.getOrderLines()){
				if(orderLine.getProductName().equals(event)){
					list.add(order);
				}
			}
		}

		return list;
		
	}
}

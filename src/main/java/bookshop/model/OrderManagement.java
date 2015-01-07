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
import org.salespointframework.payment.Cash;
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
	/**
	 * Constructor for OrderManagement
	 * @param oderManager
	 * 	 */

	@Autowired
	public OrderManagement(OrderManager<Order> orderManager) {
		this.orderManager = orderManager;
	}
	
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

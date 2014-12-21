package bookshop.model;


import static org.joda.money.CurrencyUnit.EUR;
import static org.junit.Assert.*;

import java.util.Optional;

import org.joda.money.Money;
import org.junit.Test;
import org.salespointframework.catalog.ProductIdentifier;
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
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import bookshop.AbstractIntegrationTests;
import bookshop.model.Article.ArticleId;


public class OrderTest extends AbstractIntegrationTests{
	
	@Autowired OrderManager<Order> orderManager;
	@Autowired UserAccountManager userAccountManager;
	@Autowired Inventory<InventoryItem> inventory;
	@Autowired BusinessTime date;
	
	@Test
	public void testMyOrders(){
		
	UserAccountIdentifier userID = new UserAccountIdentifier("wurst");
	Optional<UserAccount> userAccount = userAccountManager.get(userID);	
	Order order = new Order(userAccount.get(), Cash.CASH);
	Order order2 = new Order(userAccount.get(), Cash.CASH);

	orderManager.add(order);
	orderManager.add(order2);
	
	String orderString1 = "test" + order.getIdentifier().toString();
	orderString1 += order2.getIdentifier().toString();
	String orderString2 = "test";
	
	for(Order doof : orderManager.find(userAccount.get())){
		orderString2 += doof.getIdentifier().toString();
	}
			
			
	assertEquals("Die Methode getMyOrders der Klasse OrderController liefert die richtigen Orders.", orderString1 , orderString2);	
	}
	
	
	@Test
	public void testCancelOrder(){
		UserAccountIdentifier userID = new UserAccountIdentifier("wurst");
		Optional<UserAccount> userAccount = userAccountManager.get(userID);
		Order order = new Order(userAccount.get(), Cash.CASH);
		Order order2 = new Order(userAccount.get(), Cash.CASH);

		
		orderManager.cancelOrder(order);
		orderManager.payOrder(order2);
		orderManager.completeOrder(order2);
		orderManager.cancelOrder(order2);

		
		assertEquals("Die Methode cancelOrders funktioniert",OrderStatus.CANCELLED,order.getOrderStatus());	
		assertFalse("Löschen einer versendeten Order funktioniert nicht", OrderStatus.CANCELLED.equals(order2.getOrderStatus()));
	}	
}

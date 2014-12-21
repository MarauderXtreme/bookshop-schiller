package bookshop.model;


import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import bookshop.AbstractIntegrationTests;


public class OrderTest extends AbstractIntegrationTests{
	
	@Autowired OrderManager<Order> orderManager;
	@Autowired UserAccountManager userAccountManager;
	//@Autowired Inventory<InventoryItem> inventory;
	
	@Test
	public void testMyOrders(){
		
	UserAccountIdentifier userID = new UserAccountIdentifier("wurst");
	UserAccountIdentifier userID2 = new UserAccountIdentifier("dextermorgan");
	Optional<UserAccount> userAccount = userAccountManager.get(userID);	
	Optional<UserAccount> userAccount2 = userAccountManager.get(userID2);	
	Order order = new Order(userAccount.get());
	Order order2 = new Order(userAccount.get());
	Order order3 = new Order(userAccount2.get());
	/*
	for(InventoryItem item : inventory.findAll()){
		OrderLine orderLine = new OrderLine(item.getProduct(),item.getQuantity());
		order.add(orderLine);
		order2.add(orderLine);
	}
	*/
	orderManager.add(order);
	orderManager.add(order3);
	orderManager.add(order2);
	
	String orderString1 = "test" + order.getIdentifier().toString();
	orderString1 += order2.getIdentifier().toString();
	String orderString2 = "test";
	
	for(Order doof : orderManager.find(userAccount.get())){
		orderString2 += doof.getIdentifier().toString();
	}
	
	System.out.println(orderString1);
	System.out.println(orderString2);
	
	
			
			
	assertEquals("Die Methode getMyOrders der Klasse OrderController liefert die richtigen Orders.", orderString1 , orderString2);	
	}
	
	@Test
	public void testCancelOrder(){
		UserAccountIdentifier userID = new UserAccountIdentifier("wurst");
		Optional<UserAccount> userAccount = userAccountManager.get(userID);
		Order order = new Order(userAccount.get());
		orderManager.cancelOrder(order);
		assertEquals("Die Methode cancelOrders funktioniert",OrderStatus.CANCELLED,order.getOrderStatus());	
	}
	
	
	
}

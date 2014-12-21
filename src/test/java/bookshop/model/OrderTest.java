package bookshop.model;

import static org.joda.money.CurrencyUnit.EUR;
import static org.junit.Assert.*;

import java.util.Optional;

import org.joda.money.Money;
import org.junit.Test;
import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.RoundingStrategy;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import bookshop.AbstractIntegrationTests;
import bookshop.model.Article.ArticleId;

public class OrderTest extends AbstractIntegrationTests{
	
	@Autowired OrderManager orderManager;
	@Autowired UserAccountManager userAccountManager;
	@Autowired Inventory<InventoryItem> inventory;
	
	@Test
	public void testMyOrders(){
	final Role customerRole = new Role("ROLE_CUSTOMER");
	UserAccount customerAccount = userAccountManager.create("admin", "123", customerRole);
	boolean test = false;
	Order order = new Order(customerAccount);
	Metric metric = new Metric("test","test","test");
	Quantity quantity;	
	Category fiktion = new Category("Fiktion", ArticleId.BOOK);
	Article v = new Article("Trost und Rat", Money.of(EUR, 9.99), "Ein Ratgeber der besonderen Art", "Flann O'Brien", "1234567890421", ArticleId.BOOK, "Fiktion", "Flann O'Brien", "trostundrat.jpg", "01.01.2015", Money.of(EUR, 0.99));		
	Optional<InventoryItem> item = inventory.findByProductIdentifier(v.getIdentifier());
	quantity = new Quantity(10, item.get().getQuantity().getMetric(), item.get().getQuantity().getRoundingStrategy());
	OrderLine orderLine = new OrderLine(v,quantity);
	order.add(orderLine);
	
	
	
	orderManager.add(order);
	orderManager.find(customerAccount);
	
	

	//assertTrue("Die Methode getMyOrders der Klasse OrderController liefert die falschen Orders.", test);
	}
}

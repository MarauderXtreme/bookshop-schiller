package bookshop.model;

import static org.joda.money.CurrencyUnit.EUR;
import static org.junit.Assert.*;

import java.util.Optional;

import org.joda.money.Money;
import org.junit.Test;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
//import org.salespointframework.order.OrderLineIdentifier;
import org.salespointframework.order.OrderManager;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import bookshop.AbstractIntegrationTests;
import bookshop.model.Article.ArticleId;

public class CartTest extends AbstractIntegrationTests{

	@Autowired Inventory<InventoryItem> inventory;
	@Autowired UserAccountManager userAccountManager;
	@Autowired OrderManager<Order> orderManager;
	
	@Test
	public void testAddArticleToCartAndToOrder(){
		
		int number = 1;
		Quantity quantity = Units.of(number);
		Quantity quantity1 = null;
		Article article = new Article("Testbook", Money.of(EUR, 7.98), "Book for JUnit", "Philo", "0000000101010", ArticleId.DVD, "test", "Flann O'Brien", "01.01.2015", Money.of(EUR, 0.99));
		Cart cart = new Cart();
		UserAccountIdentifier userID = new UserAccountIdentifier("wurst");
		Optional<UserAccount> userAccount = userAccountManager.get(userID);
		Order order = new Order(userAccount.get());
		String order1 = order.getIdentifier().toString();
		String order2 = null;
		orderManager.add(order);
		
		cart.addOrUpdateItem(article, quantity);
		cart.addItemsTo(order);		
		ProductIdentifier article1 = article.getIdentifier();
		ProductIdentifier article2 = null;
		
		
		for(OrderLine orderLine : order.getOrderLines()){
			article2 = orderLine.getProductIdentifier();
			quantity1 = orderLine.getQuantity();
		}
		
		for(Order test : orderManager.find(userAccount.get())){
			order2 = test.getIdentifier().toString();
		}
		
		assertEquals("Artikel wird in ordentlich in den Cart gelegt und in eine Order überführt", article1.toString(), article2.toString());
		assertEquals("Die Order wurde im OrderManager gespeichert",order1,order2);
	}
	
	@Test
	public void testDeleteArticleFromCart(){
		//Create Item
		
		int number = 1;
		CartItem cartItem;
		Quantity quantity = Units.of(number);
		Quantity quantity1 = null;
		Article article = new Article("Testbook", Money.of(EUR, 7.98), "Book for JUnit", "Philo", "0000000101010", ArticleId.DVD, "test", "Flann O'Brien", "01.01.2015", Money.of(EUR, 0.99));
		Cart cart = new Cart();
		UserAccountIdentifier userID = new UserAccountIdentifier("wurst");
		Optional<UserAccount> userAccount = userAccountManager.get(userID);
		Order order = new Order(userAccount.get());
				
		cartItem = cart.addOrUpdateItem(article, quantity);

		// now Delete Item (tested cart.removeItem)
		
		cart.removeItem(cartItem.getIdentifier());		
		
		assertTrue("Der Artikel wurde aus dem Warenkorb gelöscht",cart.isEmpty());
	}
	
	
}

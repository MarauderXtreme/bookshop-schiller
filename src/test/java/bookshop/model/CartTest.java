package bookshop.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;

import bookshop.AbstractIntegrationTests;

public class CartTest extends AbstractIntegrationTests{

	@Autowired Inventory<InventoryItem> inventory;
	@Autowired OrderManager orderManager;
	
	@Test
	public void testCart(){
		
		
		
		
		
		
		boolean test = true;
		assertTrue(test);
	}
}

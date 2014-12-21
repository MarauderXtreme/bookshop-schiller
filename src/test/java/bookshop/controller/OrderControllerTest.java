package bookshop.controller;

import org.junit.Test;
import org.salespointframework.order.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;

import bookshop.AbstractWebIntegrationTests;

public class OrderControllerTest extends AbstractWebIntegrationTests{
	
	@Autowired OrderManager orderManager;
	
	@Test
	public void getOrdersTest(){
		
	}
}

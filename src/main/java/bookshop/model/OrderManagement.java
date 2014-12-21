package bookshop.model;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public class OrderManagement extends OrderLine{

	private final OrderManager oderManager;
	private final Inventory<InventoryItem> inventory;
	
	/**
	 * Constructor for the UserManagement.
	 * @param userRepository
	 * @param userAccountManager
	 */
	/**
	 * Constructor for OrderManagement
	 * @param oderManager
	 * @param inventory
	 */
	@Autowired
	public OrderManagement(OrderManager oderManager, Inventory<InventoryItem> inventory) {

		this.oderManager = oderManager;
		this.inventory = inventory;
	}
	
}

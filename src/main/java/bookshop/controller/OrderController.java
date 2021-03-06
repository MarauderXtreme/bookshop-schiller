package bookshop.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookshop.model.Article;
import bookshop.model.CalendarManagement;
import bookshop.model.MyDate;
import bookshop.model.OrderManagement;
import bookshop.model.Room;
import bookshop.model.RoomManagement;
import bookshop.model.TupelKey;


@Controller
public class OrderController {
	private final OrderManager<Order> orderManager;
	private final Inventory<InventoryItem> inventory;
	private final BusinessTime date;
	
	/**
	 * Constructor for OrderController
	 * @param orderManager
	 * @param inventory
	 * @param date
	 */
	@Autowired
	public OrderController(OrderManager<Order> orderManager, Inventory<InventoryItem> inventory, BusinessTime date){
		this.orderManager = orderManager;
		this.inventory = inventory;
		this.date = date;
	}
	
	/**
	 * Shows orders
	 * @param session
	 * @param modelMap
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN') || hasRole('ROLE_EMPLOYEE')")
	@RequestMapping("/admin/orders")
	public String getOrders(ModelMap modelMap){
		
		modelMap.addAttribute("ordersOpen", orderManager.find(OrderStatus.OPEN));
		modelMap.addAttribute("ordersCompleted", orderManager.find(OrderStatus.COMPLETED));
		
		return "orders";
	}
	


	/**
	 * Get orders from logged in user
	 * @param modelMap
	 * @param userAccount
	 * @throws IOException 
	 * @throws DocumentException 
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value="/user/order")
	public String getMyOrders(ModelMap modelMap, @LoggedIn Optional<UserAccount> userAccount){
		
		modelMap.addAttribute("myOrders", orderManager.find(userAccount.get()));
		
		return "/myorders";
	}
	
	/**
	 * Shows a detailed order page
	 * @param modelMap
	 * @param order
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER') || hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN') || hasRole('ROLE_SALESMANAGER')")
	@RequestMapping(value="/order/detail")
	public String getOrderDetails(ModelMap modelMap, @RequestParam("orderdetail") Order order){
		
		String orderpath = order.getIdentifier().toString();
		orderpath = orderpath + ".pdf";
		modelMap.addAttribute("detailordersID", orderpath);
		modelMap.addAttribute("detailorders", order.getOrderLines());
		
		return "/ordersdetail";
	}
	
	/**
	 * Shows a detailed list for given reservation
	 * @param modelMap
	 * @param order
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER') || hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN') || hasRole('ROLE_SALESMANAGER')")
	@RequestMapping(value="/order/detailreservation")
	public String getReservationDetails(ModelMap modelMap, @RequestParam("orderdetail") Order order){

		modelMap.addAttribute("detailorders", order.getOrderLines());
		
		return "/reservationdetail";
	}
	/**
	 * Cancel order
	 * @param order
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value="/order/cancel")
	public String cancelOrder(@RequestParam("orderDelete") Order order){
		for(OrderLine orderLine : order.getOrderLines()){
			inventory.findByProductIdentifier(orderLine.getProductIdentifier()).get().increaseQuantity(orderLine.getQuantity());
		}
		orderManager.cancelOrder(order);
		return "redirect:/user/order";
	}
	
	/**
	 * Complete an order
	 * @param order
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/order/complete")
	public String compledOrder(@RequestParam("orderComplete") Order order){
		for(OrderLine orderLine : order.getOrderLines()){
			inventory.findByProductIdentifier(orderLine.getProductIdentifier()).get().increaseQuantity(orderLine.getQuantity());
		}
		orderManager.payOrder(order);
		orderManager.completeOrder(order);
		return "redirect:/admin/orders";
	}
	
	/**
	 * Shows stock
	 * @param session
	 * @param modelMap
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN') || hasRole('ROLE_SALESMANAGER')")
	@RequestMapping("/admin/stock")
	public String stock(ModelMap modelMap){

		modelMap.addAttribute("stock", inventory.findAll());
		return "stock";
	}
	
	/**
	 * Reorder given article
	 * @param number
	 * @param article
	 * @param userAccount
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN') || hasRole('ROLE_SALESMANAGER')")
	@RequestMapping("/article/reorder")
	public String makeAnOrder(@RequestParam("number") int number, @RequestParam("article") Article article, @LoggedIn Optional<UserAccount> userAccount){
		
		if(number < 0){
			number = 0;
		}
		
		Quantity quantity;		
		Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
		quantity = new Quantity(number, item.get().getQuantity().getMetric(), item.get().getQuantity().getRoundingStrategy());
			
		item.get().increaseQuantity(quantity);
		
		OrderLine orderLine = new OrderLine(article, quantity);		
		
		Order order = new Order(userAccount.get(), Cash.CASH);
		order.add(orderLine);
		
		orderManager.payOrder(order);	
		orderManager.add(order);
		
		return"redirect:/admin/stock";
	}
	
	/**
	 * Show all reservations for given event 
	 * @param modelMap
	 * @param event
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_BOSS') || hasRole('ROLE_EVENTMANAGER')")
	@RequestMapping("/calendar/reservations")
	public String showReservations(ModelMap modelMap, @RequestParam("eventID") String event){
		
		
		OrderManagement management = new OrderManagement(orderManager);
		modelMap.addAttribute("reservation", management.collectReservations(event));
		return "reservations";
	}
	
	/**
	 * Create a reservation for given event
	 * @param userAccount
	 * @param roomName
	 * @param date
	 * @param time
	 * @param event
	 * @param number
	 */
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@RequestMapping(value="/calendar/bookSeat", method=RequestMethod.POST)
	public String bookSeat(@LoggedIn Optional<UserAccount> userAccount, @RequestParam("eventRoomName")String roomName,@RequestParam("dateD")String date,@RequestParam("dateT")String time, @RequestParam("eventID") String event, @RequestParam("number") int number)
	{
		MyDate tempdate = new MyDate(date, time);
		OrderManagement management = new OrderManagement(orderManager);
		if(number > 0  && CalendarManagement.getInstance().getCalendar().getEvent(new TupelKey<Room, MyDate>(RoomManagement.getInstance().getRoom(roomName), tempdate)).increaseTakenSeats(number))
			{
			management.reservation(event, userAccount, number);
			}

		return "redirect:/calendar";
	}
}

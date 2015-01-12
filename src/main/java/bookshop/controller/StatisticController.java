package bookshop.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.joda.money.Money;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Cart;
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

import bookshop.model.CalendarManagement;
import bookshop.model.OrderManagement;
import bookshop.model.StatisticsManagement;

@Controller
public class StatisticController {
	
	private final OrderManager<Order> orderManager;
	private final Inventory<InventoryItem> inventory;
	private final BusinessTime date;
	
	/**
	 * Constructor for StatisticController
	 * @param orderManager
	 * @param inventory
	 * @param date
	 */
	@Autowired
	public StatisticController(OrderManager<Order> orderManager, Inventory<InventoryItem> inventory, BusinessTime date){
		this.inventory = inventory;
		this.orderManager = orderManager;
		this.date = date;
	}
	
	/**
	 * Shows actual statistics
	 * @param session
	 * @param modelMap
	 * @param userAccount
	 * 
	 */
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN') || hasRole('ROLE_SALESMANAGER')")
	@RequestMapping("/admin/statictics")
	public String statistic(HttpSession session, ModelMap modelMap, @LoggedIn Optional<UserAccount> userAccount){

		LocalDateTime time = date.getTime();
		time = time.minusDays(7);		
		
		Money profit;
		Money profittotal;
		Money sellWeek,sellAll,buyWeek,buyAll;
		
		Order statisticOrder = new Order(userAccount.get(), Cash.CASH);
		Order gesOrderBuy = new Order(userAccount.get());
		Order gesOrderSell = new Order(userAccount.get());
		Order sellOrder = new Order(userAccount.get(), Cash.CASH);
		Order reservations = new Order(userAccount.get());
		Order gesReservations = new Order(userAccount.get());
		
		StatisticsManagement sm = new StatisticsManagement(orderManager, inventory, date);
		
		gesOrderSell = sm.getGesOrdersSell(userAccount.get());

		gesOrderBuy = sm.getGesOrdersBuy(userAccount.get());
		
		for(InventoryItem item : inventory.findAll()){					
			
			OrderLine orderLine = sm.getStatistic(true, item);
			OrderLine orderLine1 = sm.getStatistic(false, item);
			
			statisticOrder.add(orderLine);
			sellOrder.add(orderLine1);
		}
		
		
		for(String event : CalendarManagement.getInstance().getCalendar().getAllEventIDs()){
			
			OrderLine orderLine = sm.statisticOfReservation(event);
			OrderLine orderLine1 = sm.getGesStatisticsOfReservation(event);
	
			reservations.add(orderLine);
			gesReservations.add(orderLine1);
		}
		
		
		sellWeek = sellOrder.getTotalPrice().plus(reservations.getTotalPrice());
		sellAll = gesOrderSell.getTotalPrice();
		buyWeek = statisticOrder.getTotalPrice();
		buyAll = sm.getProfit(gesOrderBuy);
		
		profit = sellOrder.getTotalPrice().minus(buyWeek).plus(reservations.getTotalPrice());
		profittotal = gesOrderSell.getTotalPrice().minus(buyAll);
		
		modelMap.addAttribute("profit", profit);
		modelMap.addAttribute("profittotal", profittotal);
		
		modelMap.addAttribute("statisticsell", statisticOrder.getOrderLines());
		modelMap.addAttribute("statisticPriceSell", buyWeek);
		modelMap.addAttribute("statisticbuy", sellOrder.getOrderLines());
		modelMap.addAttribute("statisticPriceBuy", sellWeek);
		
		modelMap.addAttribute("statisticPriceSellAll", buyAll);		
		modelMap.addAttribute("statisticPriceBuyAll", sellAll);
		
		modelMap.addAttribute("statisticsReservation", reservations.getOrderLines());
		return "/statistics";
	}
		
}

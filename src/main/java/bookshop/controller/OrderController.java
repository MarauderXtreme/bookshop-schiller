package bookshop.controller;

import javax.servlet.http.HttpSession;

import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bookshop.model.UserRepository;

@Controller
public class OrderController {
	private final OrderManager<Order> orderManager;
	private final UserRepository userRepository;
	
	@Autowired
	public OrderController(OrderManager<Order> orderManager, UserRepository userRepository){
		this.orderManager = orderManager;
		this.userRepository = userRepository;
	}
	
	@PreAuthorize("hasRole('ROLE_BOSS') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/orders")
	public String orders(HttpSession session, ModelMap modelMap){
		modelMap.addAttribute("ordersCompleted", orderManager.find(OrderStatus.COMPLETED));
		return "/orders";
	}
	/*
	@RequestMapping(value="/admin/orders", method = RequestMethod.GET)
	public String getOrders(ModelMap modelMap){
		
		return "redirect:/orders";
	}*/

}

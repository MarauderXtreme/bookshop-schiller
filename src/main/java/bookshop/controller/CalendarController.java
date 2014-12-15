package bookshop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookshop.model.CalendarManagement;
import bookshop.model.Date;
import bookshop.model.Event;
import bookshop.model.Room;

@Controller
public class CalendarController {
	
	@PreAuthorize("hasRole('ROLE_EVENTMANAGER') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/event/add")
	public String addEvent(Model model) {
		return "addevent";
	}
	
	@PreAuthorize("hasRole('ROLE_EVENTMANAGER') || hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/admin/event/new", method = RequestMethod.POST)
	public String add(@RequestParam("name") String name, @RequestParam("dated") String dated , @RequestParam("datet") String datet)
	{
		CalendarManagement.getInstance().addEvent(name, new Date(dated,datet), new Room("Lesesaal","123"));
		return "redirect:calendar";
	}
	
	
	@RequestMapping("/calendar")
	public String calendar(Date date, Event event, Model model) {

		model.addAttribute("event", event);
		model.addAttribute("date", date);
		model.addAttribute("eventList" , CalendarManagement.getInstance().getCalendar().getEventList());

		return "calendar";
	}

}

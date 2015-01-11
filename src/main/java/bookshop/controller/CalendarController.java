package bookshop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookshop.model.CalendarManagement;
import bookshop.model.MyDate;
import bookshop.model.Event;
import bookshop.model.OrderManagement;
import bookshop.model.Room;
import bookshop.model.RoomManagement;
import bookshop.model.TupelKey;

/**
 * 
 * @author Maximilian
 *
 */
@Controller
public class CalendarController {
	
	/**
	 * shows the add-event-interface
	 * @param model
	 * @return 
	 */
	@PreAuthorize("hasRole('ROLE_EVENTMANAGER') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/admin/event/add")
	public String addEvent(Model model) 
	{
		model.addAttribute("roonNameList",RoomManagement.getInstance().getAllRoomNames());
		model.addAttribute("roomList", RoomManagement.getInstance().getAllRooms());
		model.addAttribute("allEvents", CalendarManagement.getInstance().getCalendar().getSortedEvents());
		return "addevent";
	}
	
	/**
	 * adds an event to the calendar
	 * @param model
	 * @param name
	 * @param dated
	 * @param datet
	 * @param room
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_EVENTMANAGER') || hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/admin/event/new", method = RequestMethod.POST)
	public String add(Model model, @RequestParam("name") String name, @RequestParam("dated") String dated , @RequestParam("datet") String datet, @RequestParam("selectedRoom")String room)
	{
		String conDateD = CalendarManagement.getInstance().convertInputDate(dated);
		String conDateT = CalendarManagement.getInstance().convertInputTime(datet);
		CalendarManagement.getInstance().addEvent(name, new MyDate(conDateD,conDateT), new Room(RoomManagement.getInstance().getRoom(room).getName(),RoomManagement.getInstance().getRoom(room).getNumber(),Integer.parseInt(RoomManagement.getInstance().getRoom(room).getChairNum())), "20");
		model.addAttribute("allEvents", CalendarManagement.getInstance().getCalendar().getEventList());
		return "/addevent";
	}
	
	/**
	 * shows the calendar
	 * @param date
	 * @param event
	 * @param model
	 * @return
	 */
	@RequestMapping("/calendar")
	public String calendar(MyDate date, Event event, Model model)
	{

		model.addAttribute("event", event);
		model.addAttribute("date", date);
		model.addAttribute("eventList", CalendarManagement.getInstance().getCalendar().getFutureEvents());
		model.addAttribute("allEvents", CalendarManagement.getInstance().getCalendar().getSortedEvents());
		model.addAttribute("years", CalendarManagement.getInstance().getCalendar().generateYears());
		model.addAttribute("months", CalendarManagement.getInstance().getCalendar().generateMonths());
		model.addAttribute("days", CalendarManagement.getInstance().getCalendar().generateDays());

		return "/calendar";
	}
	/*
	@RequestMapping(value="/calendar/bookSeat", method=RequestMethod.POST)
	public String bookSeat(@RequestParam("eventRoomName")String roomName,@RequestParam("dateD")String date,@RequestParam("dateT")String time)
	{
		MyDate tempdate = new MyDate(date, time);
		if(CalendarManagement.getInstance().getCalendar().getEvent(new TupelKey<Room, MyDate>(RoomManagement.getInstance().getRoom(roomName), tempdate)).increaseTakenSeats())
			{
			System.out.println("true");
			}
		System.out.println("false");
		
		//OrderManagement management = new OrderManagement(null, null);
		
		return "redirect:/calendar";
	}
	*/
	
	/**
	 * removes an event from the calendar
	 * @param model
	 * @param eventName
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_EVENTMANAGER') || hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/admin/event/remove", method = RequestMethod.POST)
	public String deleteEvent(Model model, @RequestParam("name")String eventName)
	{		
		Event temp = CalendarManagement.getInstance().getCalendar().getEventByName(eventName);
		System.out.println("test");
		CalendarManagement.getInstance().removeEvent(new TupelKey<Room,MyDate>(temp.getRoom(),temp.getDate()));
		return"redirect:/admin/event/add";
	}
}

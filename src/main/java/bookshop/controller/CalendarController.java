package bookshop.controller;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bookshop.model.CalendarManagement;
import bookshop.model.Event;
import bookshop.model.MyDate;
import bookshop.model.Room;
import bookshop.model.RoomManagement;
import bookshop.model.TupelKey;
import bookshop.model.validation.EventForm;

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
		model.addAttribute("eventForm", new EventForm());
		model.addAttribute("roonNameList",RoomManagement.getInstance().getAllRoomNames());
		model.addAttribute("roomList", RoomManagement.getInstance().getAllRooms());
		model.addAttribute("allEvents", CalendarManagement.getInstance().getCalendar().getSortedEvents());
		model.addAttribute("eventList", CalendarManagement.getInstance().getCalendar().getFutureEvents());
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
	public String add(Model model,@ModelAttribute("eventForm") @Valid EventForm eventForm,BindingResult result, @RequestParam("name") String name, @RequestParam("dateD") String dated , @RequestParam("dateT") String datet,@RequestParam("dateTEnd")String endTime, @RequestParam("selectedRoom")String room)
	{
		model.addAttribute("eventList", CalendarManagement.getInstance().getCalendar().getFutureEvents());
		model.addAttribute("allEvents", CalendarManagement.getInstance().getCalendar().getSortedEvents());
		model.addAttribute("eventForm", new EventForm());
		model.addAttribute("roonNameList",RoomManagement.getInstance().getAllRoomNames());
		model.addAttribute("roomList", RoomManagement.getInstance().getAllRooms());
		model.addAttribute("allEvents", CalendarManagement.getInstance().getCalendar().getSortedEvents());
		String conDateD = CalendarManagement.getInstance().convertInputDate(dated);
		String conDateT = CalendarManagement.getInstance().convertInputTime(datet);
		MyDate end = new MyDate(conDateD,CalendarManagement.getInstance().convertInputTime(endTime));
		if(Integer.parseInt(CalendarManagement.getInstance().convertInputTime(eventForm.getDateT()))>Integer.parseInt(CalendarManagement.getInstance().convertInputTime(eventForm.getDateTEnd())))
		{
			result.addError(new ObjectError("dateTEnd","Das Ende des Events muss nach dem Anfang sein!"));
			System.out.println(result.getAllErrors());
			return "/addevent";
		}
		
		if(!CalendarManagement.getInstance().addEvent(name, new MyDate(conDateD,conDateT), new Room(RoomManagement.getInstance().getRoom(room).getName(),RoomManagement.getInstance().getRoom(room).getNumber(),Integer.parseInt(RoomManagement.getInstance().getRoom(room).getChairNum())), end))
		{
			result.addError(new ObjectError("event.roomtaken","Der Raum ist Belegt!"));
		}
		
		if(result.hasErrors())
		{
			System.out.println(result.getAllErrors());
			return "/addevent";
		}
		
		return "redirect:/admin/event/add";
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

		return "/calendar";
	}
	
	/**
	 * removes an event from the calendar
	 * @param model
	 * @param eventName
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_EVENTMANAGER') || hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/admin/event/remove", method = RequestMethod.POST)
	public String deleteEvent(Model model, @RequestParam("dated")String dated, @RequestParam("datet")String datet,@RequestParam("roomname")String roomname)
	{
		MyDate tempDate = new MyDate(dated,datet);
		TupelKey<Room,MyDate>key = new TupelKey<Room,MyDate>(RoomManagement.getInstance().getRoom(roomname),tempDate);
		Event temp = CalendarManagement.getInstance().getCalendar().getEvent(key);
		if(temp!=null)
		{
			CalendarManagement.getInstance().removeEvent(key);
		}
		return"redirect:/admin/event/add";
	}
}

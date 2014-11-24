package bookshop.model;

import java.util.HashMap;
import java.util.Map;

public class Calendar {
	
	private Map<Date, Event> eventSet;
	public Calendar()
	{
		this.eventSet = new HashMap<Date , Event>();
	}
	
	public String viewEventInformation()
	{
		return null;
	}
	
	public Event getEvent(Date date)
	{
		if (eventSet.containsKey(date))
		{
			return eventSet.get(date);
		}
		else {
			return null;
		}
	}
	
	public boolean addEvent(Event event)
	{
		if (!eventSet.containsKey(event.getDate()))
		{
			System.out.println("Event wurde erfolgreich hinzugef�gt: " + event.getName() + " " + event.getDate().getDate() + " " + event.getDate().getTime());
			eventSet.put(event.getDate(), event);
			System.out.println(eventSet.get(new Date(event.getDate().getDate() , event.getDate().getTime())));
			return true;
		} else {
			System.out.println("Termin ist bereits belegt: " + event.getDate().getDate()+" " + event.getDate().getTime());
			return false;
		}
	}
}

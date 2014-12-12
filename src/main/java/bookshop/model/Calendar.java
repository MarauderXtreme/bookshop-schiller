package bookshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Calendar {
	
	private Map<TupelKey<Room,Date>, Event> eventMap;
	public Calendar()
	{
		this.eventMap = new HashMap<TupelKey<Room,Date>, Event>();
	}
	
	public String viewEventInformation()
	{
		return null;
	}
	
	public Event getEvent(TupelKey<Room,Date> tk)
	{
		if (eventMap.containsKey(tk))
		{
			System.out.println("ALARM");
			return eventMap.get(tk);
		}
		else {
			return null;
		}
	}	
	
	public List<Event> getEventList()
	{
		return new ArrayList<Event>(eventMap.values());
	}
	
	public boolean addEvent(Event event)
	{
		if (!eventMap.containsKey(new TupelKey<Room,Date>(event.getRoom(), event.getDate())))
		{
			eventMap.put(new TupelKey<Room,Date>(event.getRoom(), event.getDate()), event);
			return true;
		} else {
			//System.out.println("Termin ist bereits belegt: " + event.getDate().getDate()+" " + event.getDate().getTime());
			return false;
		}
	}
}

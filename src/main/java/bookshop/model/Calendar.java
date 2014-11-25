package bookshop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


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
			
			return eventMap.get(tk);
		}
		else {
			return null;
		}
	}	
	
	public Set<Entry<TupelKey<Room, Date>, Event>> getEventSet()
	{
		return eventMap.entrySet();
	}
	
	public boolean addEvent(Event event)
	{
		if (!eventMap.containsKey(new TupelKey<Room,Date>(event.getRoom(), event.getDate())))
		{
			System.out.println("Termin ist Frei!");
			eventMap.put(new TupelKey<Room,Date>(event.getRoom(), event.getDate()), event);
			return true;
		} else {
			System.out.println("Termin ist bereits belegt: " + event.getDate().getDate()+" " + event.getDate().getTime());
			return false;
		}
	}
}

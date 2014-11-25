package bookshop.model;

import java.util.HashMap;
import java.util.Map;

public class Calendar {
	
	private Map<TupelKey<Room,Date>, Event> eventSet;
	public Calendar()
	{
		this.eventSet = new HashMap<TupelKey<Room,Date>, Event>();
	}
	
	public String viewEventInformation()
	{
		return null;
	}
	
	public Event getEvent(TupelKey<Room,Date> tk)
	{
		if (eventSet.containsKey(tk))
		{
			
			return eventSet.get(tk);
		}
		else {
			return null;
		}
	}	
	public boolean addEvent(Event event)
	{
		if (!eventSet.containsKey(new TupelKey<Room,Date>(event.getRoom(), event.getDate())))
		{
			System.out.println("Termin ist Frei!");
			eventSet.put(new TupelKey<Room,Date>(event.getRoom(), event.getDate()), event);
			return true;
		} else {
			System.out.println("Termin ist bereits belegt: " + event.getDate().getDate()+" " + event.getDate().getTime());
			return false;
		}
	}
}

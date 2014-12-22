package bookshop.model;

public class CalendarManagement {
	private static CalendarManagement instance ;
	private MyCalendar calendar;
	
	private CalendarManagement()
	{
		this.calendar = new MyCalendar();
	}
	
	/**
	 * Returns an instance of CalendarManagement, used for further operations regarding the calendar and events
	 * @param userRepository
	 * @param userAccountManager
	 */
	public static synchronized CalendarManagement getInstance()
	{
		if(instance == null)
		{
			instance = new CalendarManagement();
		}
		return CalendarManagement.instance;
	}
	
	/**
	 * Returns the name of the given Event
	 * @param event
	 */
	public String getEventName(Event event)
	{
		return event.getName();
	}
	
	public boolean changeEvent(String name)
	{
		return false;
	}
	
	/**
	 *Removes an event event from the eventlist.
	 * @param name
	 */
	public boolean removeEvent(TupelKey<Room,MyDate>tk)
	{
		return getCalendar().removeEvent(tk);
	}
	
	
	/**
	 * Gibt das Calendar-Ojekt zurueck, in welchem alle Events gespeichert werden
	 */
	public MyCalendar getCalendar()
	{
		return calendar;
	}
	
	/**
	 * Adds an event to the eventlist.
	 * @param name
	 * @param date
	 * @param room
	 */	
	public boolean addEvent (String name, MyDate date, Room room)
	{
		if(room != null)
		{
			if(calendar.getEvent(new TupelKey<Room,MyDate>(room, date)) == null)
			{
				//System.out.println("Event zum Kalender hinzugefuegt!");
				return calendar.addEvent(new Event(name,date, room));
			} else {
				return false;
			}
		}
		else
		{
			//System.out.println("Event belegt! Grund: ");
			return false;
		}
	}	

}

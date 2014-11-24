package bookshop.model;

public class CalendarManagement {/*
	private static CalendarManagement instance ;
	private Calendar calendar;
	
	private CalendarManagement()
	{
		this.calendar = new Calendar();
	}
	
	public static synchronized CalendarManagement getInstance()
	{
		if(instance == null)
		{
			instance = new CalendarManagement();
			System.out.println("Kalendermanager-Instanz erstellt");
		}
		System.out.println("Kalendermanager-Instanz zur�ckgeben");
		return CalendarManagement.instance;
	}
	
	public String viewCalendar()
	{
		return calendar.toString();
	}
	
	public String viewCalendarEvent(Event event)
	{
		return event.getName();
	}
	
	public boolean changeEvent(String name)
	{
		return false;
	}
	
	public boolean removeEvent(String name)
	{
		return false;
	}
	
	public boolean addEvent (String name, Date date, Room room)
	{
		if(room != null)
		{
			if(calendar.getEvent(date) == null)
			{
				System.out.println("Event wird hinzugef�gt!");
				return calendar.addEvent(new Event(name,date, room));
			} else {
				System.out.println("Termin ist belegt!");
				return false;
			}
		}
		else
		{
			System.out.println("Raum existiert nicht!");
			return false;
		}
	}*/	

}

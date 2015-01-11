package bookshop.model;

import java.util.Arrays;

import org.apache.activemq.filter.function.splitFunction;

/**
 * 
 * @author Maximilian
 *
 */

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
	public boolean addEvent (String name, MyDate date, Room room,String duration)
	{
		if(room != null)
		{
			if(calendar.getEvent(new TupelKey<Room,MyDate>(room, date)) == null)
			{
				//System.out.println("Event zum Kalender hinzugefuegt!");
				return calendar.addEvent(new Event(name,date, room,duration));
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
	
	/**
	 * converts the input date to the format that is needed for it to be stored
	 * @param input Date in format DD.MM.YYYY
	 * @return Date in format DDMMYYYY
	 */
	public String convertInputDate(String input)
	{
		String output = "";
		String[]split=new String[3];
		split[0]=input.substring(0, 2);
		split[1]=input.substring(3, 5);
		split[2]=input.substring(6,10);
		output=(split[0]+split[1]+split[2]);
		return output;
	}
	
	/**
	 * Converts an input time to the format that is needed for it to be stored
	 * @param input Time in format HH:mm
	 * @return Time in format HHmm
	 */
	public String convertInputTime(String input)
	{
		String output = "";
		String[]split=new String[2];
		split[0]=input.substring(0, 2);
		split[1]=input.substring(3, 5);
		output=(split[0]+split[1]);
		return output;
	}

}

package bookshop.model;


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
	public boolean addEvent (String name, MyDate startDate, Room room,MyDate endDate)
	{
		if(room != null)
		{
			if(calendar.getEvent(new TupelKey<Room,MyDate>(room, startDate)) == null)
			{
				return calendar.addEvent(new Event(name,startDate, room,endDate));
			} else {
				return false;
			}
		}
		else
		{
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
	
	public String calculateDurationInMinutes(String start, String end)
	{
		String duration;
		String startHours=start.substring(0, 2);
		String startMinutes=start.substring(3, 5);
		String endHours=end.substring(0, 2);
		String endMinutes=end.substring(3, 5);
		
		int startInMinutes = Integer.parseInt(startHours)*60 + Integer.parseInt(startMinutes);
		int endInMinutes = Integer.parseInt(endHours)*60 + Integer.parseInt(endMinutes);
		int occupationTime=endInMinutes-startInMinutes;
		duration=Integer.toString(occupationTime);
		return duration;
	}

}

package bookshop.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Maximilian
 *
 */

public class MyCalendar {
	
	private Map<TupelKey<Room,MyDate>, Event> eventMap;
	private List<Event> sortedEventList;
	
	/**
	 * initializes a new MyCalendar-object
	 */
	public MyCalendar()
	{
		this.eventMap = new HashMap<TupelKey<Room,MyDate>, Event>();
	}
	
	public String viewEventInformation()
	{
		return null;
	}
	
	/**
	 * 
	 * @param tk tupelkey-object that is used as key for the wanted event
	 * @return the event with the specified room and date or null when nothing found
	 */
	public Event getEvent(TupelKey<Room,MyDate> tk)
	{
		if (eventMap.containsKey(tk))
		{
			return eventMap.get(tk);
		}
		else {
			return null;
		}
	}	
	
	/**
	 * 
	 * @param tk TupelKey of the Event that shall be removed
	 * @return true when the event is successfully removed, false otherwhise
	 */
	public boolean removeEvent(TupelKey<Room,MyDate>tk)
	{
		System.out.println(""+tk.getRoom().getName());
		if (eventMap.containsKey(tk))
		{
			System.out.println(eventMap.size());
			eventMap.put(tk, null);
			eventMap.remove(tk);
			sortEvents();
			System.out.println("Event entfernt: "+tk.x.getName());
			System.out.println(eventMap.size());
			return true;
		}
		System.out.println("Event nicht entfernt");
		return false;
	}
	
	/**
	 * 
	 * @return all values from the eventList
	 */
	public List<Event> getEventList()
	{
		return new ArrayList<Event>(eventMap.values());
	}
	/**
	 * Adds an event to the eventList
	 * @param event event that is to be added
	 * @return true wenn event can be added, false when there already is an event in the same room at the same time
	 */
	public boolean addEvent(Event event)
	{
		if(eventMap.size()>0){
			Iterator<Event> ite = eventMap.values().iterator();
			while(ite.hasNext())
			{
				Event next = ite.next();
				if(event.getRoom().equals(next.getRoom()))
				{
					System.out.println(next.getName());
					int iteStart = Integer.parseInt(next.getStartDate().getHours())*60+Integer.parseInt(next.getStartDate().getMinutes());
					int iteEnd = Integer.parseInt(next.getEndDate().getHours())*60+Integer.parseInt(next.getEndDate().getMinutes());
					int eventStart = Integer.parseInt(event.getStartDate().getHours())*60+Integer.parseInt(event.getStartDate().getMinutes());
					int eventEnd = Integer.parseInt(event.getEndDate().getHours())*60+Integer.parseInt(event.getEndDate().getMinutes());
					if(next.getStartDateD().equals(event.getStartDateD()))
					{
						System.out.println("evStart: "+eventStart+","+	"iteStart: "+iteStart+","+"eventEnd: "+eventEnd+","+"iteEnd: "+eventEnd);
						if ( (eventStart>=iteStart&&eventStart<=iteEnd) || (eventEnd>=iteStart&&eventEnd<=iteEnd) || (eventStart<=iteStart&&eventEnd>=iteStart) )
						{
							return false;
						}
					}
				}
			}
		}
		eventMap.put(new TupelKey<Room,MyDate>(event.getRoom(), event.getStartDate()), event);
		return true;		
	}
	
	/**
	 * 
	 * @return a list of all eventIDs
	 */
	public List<String>getAllEventIDs()
	{
		List<String>result = new ArrayList<String>();
		List<Event> events = getSortedEvents();
		
		Iterator<Event> eventIte = events.iterator();
		
		while(eventIte.hasNext())
		{
			result.add(eventIte.next().getID());
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * @return a sorted List of all elements
	 */
	public List<Event> getSortedEvents()
	{
		//System.out.println(eventMap.size());
		if(eventMap.size()>0)
		{
			List<Event> eventList = getEventList();
			List<Event> tempSortedEventList = new ArrayList<Event>();
			Iterator<Event> itr = eventList.iterator();
			tempSortedEventList.add(itr.next());
			while(itr.hasNext())
			{
				Event tempev = (Event) itr.next();
				int year = Integer.parseInt(tempev.getStartDate().getYear());
				int month = Integer.parseInt(tempev.getStartDate().getMonth());
				int day = Integer.parseInt(tempev.getStartDate().getDay());
				int hour = Integer.parseInt(tempev.getStartDate().getHours());
				int minute = Integer.parseInt(tempev.getStartDate().getMinutes());
				
				for (int i=0; i<tempSortedEventList.size(); i++)
				{
					int compYear = Integer.parseInt(tempSortedEventList.get(i).getStartDate().getYear());
					int compMonth = Integer.parseInt(tempSortedEventList.get(i).getStartDate().getMonth());
					int compDay = Integer.parseInt(tempSortedEventList.get(i).getStartDate().getDay());
					int compHour = Integer.parseInt(tempSortedEventList.get(i).getStartDate().getHours());
					int compMinute = Integer.parseInt(tempSortedEventList.get(i).getStartDate().getMinutes());
				
					if((year < compYear) || (year == compYear && month < compMonth) || ((year == compYear && month == compMonth && day < compDay )) || (year == compYear && month == compMonth && day == compDay && hour < compHour) || (year == compYear && month == compMonth && day == compDay && hour == compHour && minute < compMinute))
					{
						tempSortedEventList.add(i, tempev);
						break;
					} else if(i == tempSortedEventList.size()-1)
					{
						tempSortedEventList.add(tempev);
						break;
					} 
				}
			}
			return tempSortedEventList;
		} else {
			return new ArrayList<Event>();
		}
	}
	
	/**
	 * sorts the eventList so that the oldest elements are on top
	 */
	public void sortEvents()
	{
		this.sortedEventList = getSortedEvents();
	}

	
	//Gibt alle Events eines gewählten Jahres zurück
	/**
	 * Searches for all events from the list that happened in the specified year
	 * @param year Specifies the year from which all elements shall be retrieved
	 * @return
	 */
	public List<Event> getEventsByYear(int year)
	{
		List<Event>byYear = new ArrayList<Event>();
		List<Event> sorted = getSortedEvents();
		for(int i=0; i<sorted.size(); i++)
		{
			if(Integer.parseInt(sorted.get(i).getStartDate().getYear())==year)
			{
				byYear.add(sorted.get(i));
			}
		}
		return byYear;
	}
	
	//Gibt für eine eingegebene Liste alle Events des angegebenen Monats zurück
	
	/**
	 * Gives all elements of a specific months, to be used in combination with getEventsByYear
	 * @param byYear List of Elements from a specific year
	 * @param month month that is to be shown
	 * @return a list with all elements that happen in the specified month
	 */
	public List<Event> getEventsByMonth(List<Event> byYear, int month)
	{
		List<Event>byMonth = new ArrayList<Event>();
		for(int i=0; i<byYear.size();i++)
		{
			if(Integer.parseInt(byYear.get(i).getStartDate().getMonth())==month);
			{
				byMonth.add(byYear.get(i));
			}
		}
		return byMonth;
	}
	
	/**
	 * 
	 * @return the current year
	 */
	public String getCurrentYear()
	{
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		return date.substring(6, 10);
	}
	
	/**
	 * 
	 * @return the current month
	 */
	public String getCurrentMonth()
	{
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		return date.substring(3, 5);
	}
	
	/**
	 * 
	 * @return the current day
	 */
	public String getCurrentDay()
	{
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		return date.substring(0,2);
	}
	
	/**
	 * Gets a List of all Future Events
	 * @return a List of Recent and Future events
	 */
	public List<Event> getFutureEvents()
	{
		List<Event> all = getSortedEvents();
		List<Event> future = new ArrayList<Event>();
		for(int i=0; i<all.size();i++)
		{
			if(!( Integer.parseInt(all.get(i).getStartDate().getYear()) < Integer.parseInt(getCurrentYear())) )
			{
				if(Integer.parseInt(all.get(i).getStartDate().getYear()) == Integer.parseInt(getCurrentYear()))
				{
					if(!(Integer.parseInt(all.get(i).getStartDate().getMonth()) < Integer.parseInt(getCurrentMonth())) )
					{
						future.add(all.get(i));
					}
				}
			}
			
		}
		
		return future;
	}
}

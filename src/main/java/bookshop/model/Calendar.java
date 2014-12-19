package bookshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class Calendar {
	
	private Map<TupelKey<Room,MyDate>, Event> eventMap;
	private List<Event> sortedEventList;
	public Calendar()
	{
		this.eventMap = new HashMap<TupelKey<Room,MyDate>, Event>();
	}
	
	public String viewEventInformation()
	{
		return null;
	}
	
	public Event getEvent(TupelKey<Room,MyDate> tk)
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
		if (!eventMap.containsKey(new TupelKey<Room,MyDate>(event.getRoom(), event.getDate())))
		{
			System.out.println("Termin ist Frei!");
			eventMap.put(new TupelKey<Room,MyDate>(event.getRoom(), event.getDate()), event);
			return true;
		} else {
			System.out.println("Termin ist bereits belegt: " + event.getDate().getDate()+" " + event.getDate().getTime());
			return false;
		}
	}
	
	public List<Event> getSortedEvents()
	{
		System.out.println(eventMap.size());
		List<Event> eventList = getEventList();
		List<Event> tempSortedEventList = new ArrayList<Event>();
		tempSortedEventList.add(eventList.get(0));
		Iterator<Event> itr = eventList.iterator();
		while(itr.hasNext())
		{
			Event tempev = (Event) itr.next();
			int year = Integer.parseInt(tempev.getDate().getYear());
			int month = Integer.parseInt(tempev.getDate().getMonth());
			int day = Integer.parseInt(tempev.getDate().getDay());
			int hour = Integer.parseInt(tempev.getDate().getHours());
			int minute = Integer.parseInt(tempev.getDate().getMinutes());
			
			for (int i=0; i<=tempSortedEventList.size() /*&& tempSortedEventList.size() < eventList.size()*/; i++)
			{
				int compYear = Integer.parseInt(tempSortedEventList.get(i).getDate().getYear());
				int compMonth = Integer.parseInt(tempSortedEventList.get(i).getDate().getMonth());
				int compDay = Integer.parseInt(tempSortedEventList.get(i).getDate().getDay());
				int compHour = Integer.parseInt(tempSortedEventList.get(i).getDate().getHours());
				int compMinute = Integer.parseInt(tempSortedEventList.get(i).getDate().getMinutes());
			
				if((year < compYear) || (year == compYear && month < compMonth) || ((year == compYear && month == compMonth && day < compDay )) || (year == compYear && month == compMonth && day == compDay && hour < compHour) || (year == compYear && month == compMonth && day == compDay && hour == compHour && minute < compMinute))
				{
					System.out.println("Event wird eingefuegt!");
					tempSortedEventList.add(i, tempev);
					break;
				} else if(i == tempSortedEventList.size()-1)
				{
					System.out.println("Event hinten ran geadded");
					tempSortedEventList.add(tempev);
					break;
				} 
			}
		}
		return tempSortedEventList;
	}
	
	public void sortEvents()
	{
		this.sortedEventList = getSortedEvents();
	}
	
	public void showList()
	{
		for(int i=0; i<sortedEventList.size(); i++)
		{
			System.out.println(sortedEventList.get(i).getName()+" Datum:"+" "+sortedEventList.get(i).getDateD());
		}
	} 
	
	//Gibt alle Events eines gewählten Jahres zurück
	public List<Event> getEventsByYear(int year)
	{
		List<Event>byYear = new ArrayList<Event>();
		List<Event> sorted = getSortedEvents();
		for(int i=0; i<sorted.size(); i++)
		{
			if(Integer.parseInt(sorted.get(i).getDate().getYear())==year)
			{
				byYear.add(sorted.get(i));
			}
		}
		return byYear;
	}
	
	//Gibt für eine eingegebene Liste alle Events des angegebenen Monats zurück
	public List<Event> getEventsByMonth(List<Event> byYear, int month)
	{
		List<Event>byMonth = new ArrayList<Event>();
		for(int i=0; i<byYear.size();i++)
		{
			if(Integer.parseInt(byYear.get(i).getDate().getMonth())==month);
			{
				byMonth.add(byYear.get(i));
			}
		}
		return byMonth;
	}
}

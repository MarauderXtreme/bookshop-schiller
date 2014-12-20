package bookshop.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class MyCalendar {
	
	private Map<TupelKey<Room,MyDate>, Event> eventMap;
	private List<Event> sortedEventList;
	public MyCalendar()
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
			eventMap.put(new TupelKey<Room,MyDate>(event.getRoom(), event.getDate()), event);
			return true;
		} else {
			return false;
		}
	}
	
	public List<Event> getSortedEvents()
	{
		//System.out.println(eventMap.size());
		List<Event> eventList = getEventList();
		List<Event> tempSortedEventList = new ArrayList<Event>();
		Iterator<Event> itr = eventList.iterator();
		tempSortedEventList.add(itr.next());
		while(itr.hasNext())
		{
			Event tempev = (Event) itr.next();
			int year = Integer.parseInt(tempev.getDate().getYear());
			int month = Integer.parseInt(tempev.getDate().getMonth());
			int day = Integer.parseInt(tempev.getDate().getDay());
			int hour = Integer.parseInt(tempev.getDate().getHours());
			int minute = Integer.parseInt(tempev.getDate().getMinutes());
			
			for (int i=0; i<tempSortedEventList.size(); i++)
			{
				int compYear = Integer.parseInt(tempSortedEventList.get(i).getDate().getYear());
				int compMonth = Integer.parseInt(tempSortedEventList.get(i).getDate().getMonth());
				int compDay = Integer.parseInt(tempSortedEventList.get(i).getDate().getDay());
				int compHour = Integer.parseInt(tempSortedEventList.get(i).getDate().getHours());
				int compMinute = Integer.parseInt(tempSortedEventList.get(i).getDate().getMinutes());
			
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
	}
	
	public void sortEvents()
	{
		this.sortedEventList = getSortedEvents();
	}
	
	public void showList()
	{
		for(int i=0; i<sortedEventList.size(); i++)
		{
			//System.out.println(sortedEventList.get(i).getName()+" Datum:"+" "+sortedEventList.get(i).getDateD());
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
	
	public String getCurrentYear()
	{
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		return date.substring(6, 10);
	}
	
	public String getCurrentMonth()
	{
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		return date.substring(3, 5);
	}
	
	public String getCurrentDay()
	{
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		return date.substring(0,2);
	}
	
	public List<Event> getFutureEvents()
	{
		List<Event> all = getSortedEvents();
		List<Event> future = new ArrayList<Event>();
		for(int i=0; i<all.size();i++)
		{
			if(!( Integer.parseInt(all.get(i).getDate().getYear()) < Integer.parseInt(getCurrentYear())) )
			{
				if(Integer.parseInt(all.get(i).getDate().getYear()) == Integer.parseInt(getCurrentYear()))
				{
					if(!(Integer.parseInt(all.get(i).getDate().getMonth()) < Integer.parseInt(getCurrentMonth())) )
					{
						future.add(all.get(i));
					}
				}
			}
			
		}
		
		return future;
	}
	
	public List<String>generateDays()
	{
		List<String> days = new ArrayList<String>();
		for(int i=0; i<31; i++)
		{
			days.add(""+i);
		}
		return days;
	}
	
	public List<String>generateMonths()
	{
		List<String> months = new ArrayList<String>();
		for(int i=0; i<12; i++)
		{
			months.add(""+i);
		}
		return months;
	}
	
	public List<String>generateYears()
	{
		List<String> years = new ArrayList<String>();
		for(int i=2000; i<2099; i++)
		{
			years.add(""+i);
			//System.out.println(""+i);
		}
		return years;
	}
}

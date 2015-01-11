package bookshop.model;

/**
 * 
 * @author Maximilian
 *
 */
public class MyDate extends Object{
	private String date;
	private String time;
	
	@Deprecated
	protected MyDate()
	{	
	}
	
	/**
	 * Initializes a new MyDatte object 
	 * @param d represents the date
	 * @param t represents the time
	 */
	public MyDate(String d , String t)
	{
		this.date = d;
		this.time = t;
	}
	
	/**
	 * 
	 * @return the date-parameter of the MyDate.object as string
	 */
	public String getDate()
	{
		return date;
	}
	
	/**
	 * 
	 * @return the time-parameter of the MyDate.object as string
	 */
	public String getTime()
	{
		return time;
	}
	
	/**
	 * extracts the day-value from the date-string
	 * @return day of the MyDate.object as string
	 */
	public String getDay()
	{
		return date.substring(0, 2);
	}
	
	/**
	 * 
	 * @return the month the event is happening
	 */
	public String getMonth()
	{
		return date.substring(3, 5);
	}
	
	/**
	 * 
	 * @return the year the event is happening in
	 */
	public String getYear()
	{
		return date.substring(4, 8);
	}
	
	/**
	 * 
	 * @return the hours at which the event starts
	 */
	public String getHours()
	{
		return time.substring(0, 2);
	}
	
	/**
	 * 
	 * @return the minutes at which the event does start
	 */
	public String getMinutes()
	{
		return time.substring(2	, 4);
	}
	
	/**
	 * 
	 * @return the time the event is happening
	 */
	public String getEventTime() {
		return (getWholeDate().split(" ")[1]);
	}
	
	/**
	 * 
	 * @return the whole date and time in the format dd.mm.yyyy HH:MM
	 */
	public String getWholeDate()
	{
		return (""+getDate().charAt(0) + getDate().charAt(1) + "."+getDate().charAt(2) + getDate().charAt(3) + "."+getDate().charAt(4)+getDate().charAt(5)+getDate().charAt(6)+getDate().charAt(7)+" "+getTime().charAt(0)+getTime().charAt(1)+":"+getTime().charAt(2)+getTime().charAt(3));
	}
	
	/**
	 * 
	 * @return the Date at which the event is happening
	 */
	public String getEventDate()
	{
		return getWholeDate().split(" ")[0];
	}
	
	public boolean equals(Object date) {
		
		//System.out.println("Datum wird ueberprüft");
	       if (!(date instanceof MyDate))
	       {
	    	   return false;
	       }

	        if (date == this)
	        {
	        	return true;
	        }

	        MyDate dateN = (MyDate) date;
	        
	        if((this.date==null&&dateN.date!=null) || (this.time==null&&dateN.time!=null) || (this.date != null&&dateN.date==null) || (this.time!=null&&dateN.time==null))
	        {
	        	return false;
	        }
	        
	        if(this.date.equals(dateN.date) && this.time.equals(dateN.time))
	        {
	        	//System.out.println("Datum ist gleich!");
	        	return true;
	        } else 
	        {
	        	//System.out.println("Datum ist unterschiedlich!");
	        	return false;
	        }
	    }

	public int hashCode()
	{
		String da = getDate().toString();
		String ti = getTime().toString();
		int ida = Integer.parseInt(da);
		int iti = Integer.parseInt(ti);
		return ((ida*7) + (iti* 13));
	}

}

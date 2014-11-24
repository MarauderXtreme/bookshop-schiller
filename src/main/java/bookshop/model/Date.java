package bookshop.model;

public class Date extends Object{
	private String date;
	private String time;
	
	public Date(String d , String t)
	{
		this.date = d;
		this.time = t;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getTime()
	{
		return time;
	}
	
	public boolean equals(Object date) {
	       if (!(date instanceof Date))
	       {
	    	   System.out.println("Kein Datum");
	    	   return false;
	       }

	        if (date == this)
	        {
	        	System.out.println("Gleiche Instanz der Klasse, return: true");
	        	return true;
	        }

	        Date dateN = (Date) date;
	        
	        if((this.date==null&&dateN.date!=null) || (this.time==null&&dateN.time!=null) || (this.date != null&&dateN.date==null) || (this.time!=null&&dateN.time==null))
	        {
	        	System.out.println("Ein Parameter ist NULL ! return: false");
	        	return false;
	        }
	        
	        if(this.date==dateN.date && this.time == dateN.time)
	        {
	        	System.out.println("Gleicher Key, sollte erkannt werden! return: true");
	        	return true;
	        } else 
	        {
	        	System.out.println("Unterschiedlicher Key! return: false");
	        	return false;
	        }
	    }
	
	public int hashCode()
	{
		String da = getDate().toString();
		String ti = getTime().toString();
		int ida = Integer.parseInt(da);
		int iti = Integer.parseInt(ti);
		//System.out.println("hashwert: "+((ida*7) + (iti* 13)));
		return ((ida*7) + (iti* 13));
	}

}

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
	    	  
	    	   return false;
	       }

	        if (date == this)
	        {
	        	
	        	return true;
	        }

	        Date dateN = (Date) date;
	        
	        if((this.date==null&&dateN.date!=null) || (this.time==null&&dateN.time!=null) || (this.date != null&&dateN.date==null) || (this.time!=null&&dateN.time==null))
	        {
	        	
	        	return false;
	        }
	        
	        if(this.date==dateN.date && this.time == dateN.time)
	        {
	        	
	        	return true;
	        } else 
	        {
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

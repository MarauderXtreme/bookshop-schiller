package bookshop.model;

public class Date extends Object{
	private String date;
	private String time;
	
	@Deprecated
	protected Date()
	{	
	}
	
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
	
	public String getEventTime() {
		return (getWholeDate().split(" ")[1]);
	}
	
	public String getWholeDate()
	{
		return (""+getDate().charAt(0) + getDate().charAt(1) + "."+getDate().charAt(2) + getDate().charAt(3) + "."+getDate().charAt(4)+getDate().charAt(5)+getDate().charAt(6)+getDate().charAt(7)+" "+getTime().charAt(0)+getTime().charAt(1)+":"+getTime().charAt(2)+getTime().charAt(3));
	}
	
	public String getEventDate()
	{
		return getWholeDate().split(" ")[0];
	}
	
	public boolean equals(Object date) {
		
		System.out.println("Datum wird ueberprüft");
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
	        
	        if(this.date.equals(dateN.date) && this.time.equals(dateN.time))
	        {
	        	System.out.println("Datum ist gleich!");
	        	return true;
	        } else 
	        {
	        	System.out.println("Datum ist unterschiedlich!");
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

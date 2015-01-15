package bookshop.model;

/**
 * 
 * @author Maximilian
 *
 * @param <X> First key
 * @param <Y> Second key
 */
public class TupelKey<X , Y>{
	public  Room x ;
	public  MyDate y;
	
	public TupelKey(Room r, MyDate d) {
		this.x=r;
		this.y=d;
	}
	
	public boolean equals(Object tk)
	{
		TupelKey<Room, MyDate> tuke = (TupelKey<Room, MyDate>)tk;
		Room ro = tuke.x;
		MyDate da = tuke.y;
		
		if (tuke ==this) {
			return true;
		}
		
		
		if(this.x.equals(ro) && this.y.equals(da)) {
			return true;
		} else {
		return false;
		}
	}
	
	public int hashCode()
	{		
		return (Integer.parseInt(y.getDate())+Integer.parseInt(y.getTime()) + (Integer.parseInt(x.getNumber()))+(x.getNumber().length()+3));
	}
	
	public Room getRoom()
	{
		return x;
	}
	
	public MyDate getDate()
	{
		return y;
	}
}

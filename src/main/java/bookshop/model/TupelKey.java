package bookshop.model;
public class TupelKey<X , Y>{
	public  Room x ;
	public  Date y;
	
	public TupelKey(Room r, Date d) {
		this.x=r;
		this.y=d;
	}
	
	public boolean equals(Object tk)
	{
		TupelKey<Room, Date> tuke = (TupelKey<Room, Date>)tk;
		//System.out.println("Tupel equals wird geprueft");
		Room ro = tuke.x;
		Date da = tuke.y;
		
		if (tuke ==this) {
			System.out.println("Gleiche instanz von TupelKey!");
			return true;
		}
		
		
		if(this.x.equals(ro) && this.y.equals(da)) {
			System.out.println("TupelKey ist gleich dem anderen");
			return true;
		} else {
			System.out.println("Neuer TupelKey");
		return false;
		}
	}
	
	public int hashCode()
	{		
		return (Integer.parseInt(y.getDate())+Integer.parseInt(y.getTime()) + (Integer.parseInt(x.getNumber()))+(x.getNumber().length()+3));
	}
}

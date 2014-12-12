package bookshop.model;

public class Room {
	private String name;
	private String number;
	
	@Deprecated
	protected Room()
	{	
	}
	
	public Room(String na, String nu)
	{
		this.name = na;
		this.number = nu;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getNumber()
	{
		return number;
	}
	
	public boolean equals(Room r) {
		
		System.out.println("Room equals wird geprueft");
		if(this.name.equals(r.getName())&& this.number.equals(r.getNumber())) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public int hashCode()
	{
		String nu = getNumber().toString();
		String na = getName().toString();
		
		int hash = (nu.length()*na.length()/13);
		return hash;
	}

}

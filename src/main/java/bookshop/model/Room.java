package bookshop.model;

public class Room {
	private String name;
	private String number;
	
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

}

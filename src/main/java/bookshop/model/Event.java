package bookshop.model;

public class Event extends Object {
	private String name;
	private MyDate date;
	private Room room;
	
	@Deprecated
	protected Event() {}
	
	public Event(String name, MyDate date, Room room)
	{
		this.name = name;
		this.date = date;
		this.room = room;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDateD()
	{
		return date.getDate();
	}
	
	public String getDateT()
	{
		return date.getTime();
	}
	
	public MyDate getDate()
	{
		return date;
	}
	
	public Room getRoom()
	{
		return room;
	}
}

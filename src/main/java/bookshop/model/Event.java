package bookshop.model;

public class Event extends Object {
	private String name;
	private Date date;
	private Room room;
	
	public Event(String name, Date date, Room room)
	{
		this.name = name;
		this.date = date;
		this.room = room;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public Room getRoom()
	{
		return room;
	}
}

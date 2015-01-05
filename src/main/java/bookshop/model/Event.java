package bookshop.model;

public class Event extends Object {
	private String name;
	private MyDate date;
	private Room room;
	private int takenSeats;
	private String eventID;
	
	@Deprecated
	protected Event() {}
	
	/**
	 * Initializes a new Event-objet with the given parameters
	 * @param name name of the event
	 * @param date date of the event
	 * @param room room where the event takes place
	 */
	public Event(String name, MyDate date, Room room)
	{
		this.name = name;
		this.date = date;
		this.room = room;
		this.takenSeats = 0;
		this.eventID = (name+" "+date.getDate()+date.getTime()+" "+room.getName()+room.getNumber());
	}
	
	/**
	 * 
	 * @return the name of the Event-object
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * 
	 * @return returns the date of the Event-object
	 */
	public String getDateD()
	{
		return date.getDate();
	}
	
	/**
	 * 
	 * @return the time of the Event-object
	 */
	public String getDateT()
	{
		return date.getTime();
	}
	
	/**
	 * 
	 * @return the MyDate-object assigned to the Event-object
	 */
	public MyDate getDate()
	{
		return date;
	}
	
	/**
	 * 
	 * @return the Room-object assigned to the Event-object
	 */
	public Room getRoom()
	{
		return room;
	}
	
	/**
	 * 
	 * @return the number of seats that have ben reserved
	 */
	public int getTakenSeats()
	{
		return takenSeats;
	}
	
	/**
	 * 
	 * @return the event specific ID, constructed from the Eventname, Date and Room
	 */
	public String getID()
	{
		return eventID;
	}
	
	/**
	 * Increases the amount of reserved seats for this event
	 * @return false if all seats are taken, true if not
	 */
	public boolean increaseTakenSeats()
	{
		if(Integer.parseInt(getRoom().getChairNum())>takenSeats)
		{
			takenSeats++;
			return true;
		}
		return false;
	}
}

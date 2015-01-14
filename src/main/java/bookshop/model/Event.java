package bookshop.model;

/**
 * 
 * @author Maximilian
 *
 */
public class Event extends Object {
	private String name;
	private MyDate startDate;
	private MyDate endDate;
	private Room reventRoom;
	private int takenSeats;
	private String eventID;
	private String duration;
	
	@Deprecated
	protected Event() {}
	
	/**
	 * Initializes a new Event-objet with the given parameters
	 * @param name name of the event
	 * @param date date of the event
	 * @param room room where the event takes place
	 */
	public Event(String name, MyDate startDate, Room room, MyDate endDate)
	{
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reventRoom = room;
		this.takenSeats = 0;
		this.eventID = (name+"("+startDate.getWholeDate()+" "+room.getName()+" "+room.getNumber()+")");
		this.duration=durationInMinutes(startDate, endDate);
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
	public String getStartDateD()
	{
		return startDate.getDate();
	}
	
	public String getDuration()
	{
		return duration;
	}
	
	/**
	 * 
	 * @return the time of the Event-object
	 */
	public String getStartDateT()
	{
		return startDate.getTime();
	}
	
	/**
	 * 
	 * @return the MyDate-object assigned to the Event-object
	 */
	public MyDate getStartDate()
	{
		return startDate;
	}
	
	public MyDate getEndDate()
	{
		return endDate;
	}
	
	/**
	 * 
	 * @return the Room-object assigned to the Event-object
	 */
	public Room getRoom()
	{
		return reventRoom;
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
	public boolean increaseTakenSeats(int number)
	{
		if(Integer.parseInt(getRoom().getChairNum())>=takenSeats+number)
		{
			takenSeats += number;
			return true;
		}
		return false;
	}
	
	/**
	 * Converts the duration into Minutes
	 * @param start MyDate-representation of the startdate
	 * @param end MyDate-representation of the enddate
	 * @return the duration in minutes
	 */
	private String durationInMinutes(MyDate start, MyDate end)
	{
		int startDay = Integer.parseInt(start.getDay());
		int endDay = Integer.parseInt(end.getDay());
		
		int startHour = Integer.parseInt(start.getHours());
		int endHour = Integer.parseInt(end.getHours());
		
		int startMinutes = Integer.parseInt(start.getMinutes());
		int endMinutes = Integer.parseInt(end.getMinutes());
		
		int beginningInMinutes = (startDay*24*60)+(startHour*60)+startMinutes;
		int endInMinutes = (endDay*24*60)+(endHour*60)+endMinutes;
		
		int duration = endInMinutes - beginningInMinutes;
		
		return Integer.toString(duration);
	}
}

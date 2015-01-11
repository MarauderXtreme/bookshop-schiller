package bookshop.model;

/**
 * 
 * @author Maximilian
 *
 */
public class Room {
	private String name;
	private String number;
	private int chairNum;
	
	@Deprecated
	protected Room()
	{	
	}
	
	/**
	 * Initializes a new Room-object
	 * @param na room name
	 * @param nu room number
	 * @param chairNum number of chairs in the room
	 */
	public Room(String na, String nu, int chairNum)
	{
		this.name = na;
		this.number = nu;
		this.chairNum = chairNum;
	}
	
	/**
	 * 
	 * @return the name parameter of the Room-object
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * 
	 * @return the number parameter of the Room-object
	 */
	public String getNumber()
	{
		return number;
	}
	
	/**
	 * 
	 * @return the chairnumber parameter of the Room-object
	 */
	public String getChairNum()
	{
		return Integer.toString(chairNum);
	}
	
	/**
	 * 
	 * @param r Room-object to compare with
	 * @return true when the Room-object either has the same name or roomnumber than the other (or both)
	 */
	public boolean equals(Room r) {
		
		//System.out.println("Room equals wird geprueft");
		if(this.name.equals(r.getName())|| this.number.equals(r.getNumber())) {
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
